<%@ WebHandler Language="C#" Class="yijianchenxian" %>

using System;
using System.Web;

/// <summary>
/// 一剑成仙PC页游接入
/// </summary>
public class yijianchenxian : Shotgun.PagePlus.SimpleHttpHandlerMsSQL, Shotgun.Model.Logical.ILogical
{
    System.Text.StringBuilder sb = null;

    public override void BeginProcess()
    {
        WriteLog(Request.Url.Query);
        var m = Request["method"];
        Func<bool> func = null;
        switch (m)
        {
            case "getvcode": func = GetVCode; break;
            case "submitvcode": func = SubmitVCode; break;
            default: SetError("未知方法"); break;
        }


        try { if (func != null) func(); }
        catch (Exception ex) { SetError(ex.Message); }
        WriteLog(ErrorMesage);

        if (IsSuccess)
            Response.Write("1," + ErrorMesage);
        else
            Response.Write("0," + ErrorMesage);

        Shotgun.Library.SimpleLogRecord.WriteLog(Request.MapPath(string.Format("~/log/{0:yyyyMMdd}_yijianchenxian.log", DateTime.Now)), sb.ToString());

    }
    bool SubmitVCode()
    {
        var orderId = Request["orderId"];
        var vCode = Request["vcode"];
        if (string.IsNullOrEmpty(orderId))
            return SetError("订单号不能为空");
        if (string.IsNullOrEmpty(vCode))
            return SetError("验证码不能为空");

        const string url = "http://120.24.168.134/YiJian/recharge/code.php";
        var data = "code=" + vCode;
        var bin = System.Text.ASCIIEncoding.UTF8.GetBytes(data);
        var web = (System.Net.HttpWebRequest)System.Net.WebRequest.Create(url);
        web.Timeout = 2000;
        web.Method = "POST";
        web.ServicePoint.Expect100Continue = false;
        web.ServicePoint.UseNagleAlgorithm = false;
        web.ContentType = "application/x-www-form-urlencoded; charset=UTF-8";
        web.ContentLength = bin.Length;
        web.AllowAutoRedirect = false;
        web.Headers.Add(System.Net.HttpRequestHeader.Cookie, "PHPSESSID=" + orderId);


        using (var stm = web.GetRequestStream())
        {
            stm.Write(bin, 0, bin.Length);
        }
        using (var rsp = (System.Net.HttpWebResponse)web.GetResponse())
        {
            using (var stm = rsp.GetResponseStream())
            {
                var rd = new System.IO.StreamReader(stm);
                var html = rd.ReadToEnd();
                WriteLog(html);
                if ("chargeGame()".Equals(html))
                    return SetSuccess("OK");
                var mc = System.Text.RegularExpressions.Regex.Match(html, "\"resultCode\":(\\d+)");
                if (mc.Success)
                    return SetError(mc.Groups[1].Value);
                
                mc = System.Text.RegularExpressions.Regex.Match(html, "\"resultCode\":\"(\\d+)");
                if (mc.Success)
                    return SetError(mc.Groups[1].Value);
                
                return SetError(html);
            }

        }
    }

    void WriteLog(string msg)
    {
        if (sb == null)
            sb = new System.Text.StringBuilder();
        else
            sb.Append("\t");
        sb.Append(msg);
    }

    bool GetVCode()
    {
        var cid = Request["cid"];
        var mb = Request["mobile"];
        var fee = Request["fee"];
        if (string.IsNullOrEmpty(fee))
            return SetError("计费点不能为空");
        if (string.IsNullOrEmpty(mb))
            return SetError("手机号不能为空");
        if (string.IsNullOrEmpty(cid))
            return SetError("渠道号不能为空");
        int f;
        int.TryParse(fee, out f);
        string payCode;
        switch (f)
        {
            case 100: payCode = "006086277001"; break;//100元宝
            case 200: payCode = "006086277002"; break;//200元宝
            case 400: payCode = "006086277003"; break;//400元宝
            case 600: payCode = "006086277004"; break;//600元宝
            case 800: payCode = "006086277005"; break;//800元宝
            case 1000: payCode = "006086277006"; break;//1000元宝
            case 1500: payCode = "006086277007"; break;//1500元宝
            case 2000: payCode = "006086277008"; break;//2000元宝
            case 3000: payCode = "006086277009"; break;//3000元宝
            case 10000: payCode = "006086277010"; break;//10000元宝
            default:
                return SetError("计费点错误");
        }

        const string url = "http://120.24.168.134/YiJian/recharge/index.php";//?consumecode=" + payCode + "&accountid=" + cid + "&serverid=1";
        var data = "tel=" + mb + "&consumecode=" + payCode + "&accountid=" + cid + "&serverid=1";
        var web = (System.Net.HttpWebRequest)System.Net.WebRequest.Create(url);
        web.Timeout = 5000;
        web.Method = "POST";
        web.ServicePoint.Expect100Continue = false;
        web.ServicePoint.UseNagleAlgorithm = false;
        web.ContentType = "application/x-www-form-urlencoded; charset=UTF-8";
        web.AllowAutoRedirect = false;
        var bin = System.Text.ASCIIEncoding.UTF8.GetBytes(data);
        using (var stm = web.GetRequestStream())
        {
            stm.Write(bin, 0, bin.Length);
        }
        using (var rsp = (System.Net.HttpWebResponse)web.GetResponse())
        {
            System.Text.RegularExpressions.Match mc;

            if (rsp.StatusCode != System.Net.HttpStatusCode.Found)
            {
                string html;
                using (var stm = rsp.GetResponseStream())
                {
                    var rd = new System.IO.StreamReader(stm);
                    html = rd.ReadToEnd();
                    mc = System.Text.RegularExpressions.Regex.Match(html, "\"resultCode\":\"(\\d+)\"");
                    if (mc.Success)
                    {
                        if (mc.Groups[1].Value == "200000")
                            return SetSuccess(mc.Groups[1].Value);
                        else
                            return SetError("error code:" + mc.Groups[1].Value);
                    }
                }
                return SetError("响应出错:" + html);
            }

            var cookie = rsp.Headers["Set-Cookie"];
            mc = System.Text.RegularExpressions.Regex.Match(cookie, "=(.+?);");
            if (mc.Success)
                return SetSuccess(mc.Groups[1].Value);
            return SetError("未知响应");
        }


    }

    public bool SetError(string err)
    {
        IsSuccess = false;
        ErrorMesage = err;
        return false;
    }
    public bool SetSuccess()
    {
        return SetSuccess("成功");
    }
    public bool SetSuccess(string msg)
    {
        ErrorMesage = msg;
        IsSuccess = true;
        return true;
    }


    public string ErrorMesage { get; set; }

    public bool IsSuccess { get; set; }


    public int lastUpdateCount
    {
        get { throw new NotImplementedException(); }
    }


    public new Shotgun.Database.IBaseDataClass2 dBase
    {
        get
        {
            throw new NotImplementedException();
        }
        set
        {
            throw new NotImplementedException();
        }
    }
}