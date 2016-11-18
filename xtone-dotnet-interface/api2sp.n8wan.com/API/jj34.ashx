<%@ WebHandler Language="C#" Class="jj34" %>

using System;
using System.Web;

public class jj34 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var url = "http://120.76.84.132/YiJian/recharge/index.php?";
        //var data = "tel=" + OrderInfo.mobile + "&consumecode=006086277001" + "&accountid=1234" + "&serverid=1";
        var data = "tel=" + OrderInfo.mobile + "&consumecode=" + PayModel.paycode + "&accountid=" + PayModel.appid + "&serverid=1";
        var html = TPostHtml(url, data);
        if ("20000".Equals(html))
        {
            html = TPostHtml("http://120.76.84.132/YiJian/recharge/code.php", string.Empty);
            var mc = System.Text.RegularExpressions.Regex.Match(html, "orderid=([0-9a-f]+)");
            if (mc.Success)
                OrderInfo.spLinkId = mc.Groups[1].Value;
            return new sdk_Request.Model.SP_RESULT();
        }
        SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, html);
        return null;
    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        var url = "http://120.76.84.132/YiJian/recharge/code.php?";
        //var data = "code=11111";
        var data = "code=" + OrderInfo.cpVerifyCode;
        var html = TPostHtml(url, data);

        if ("20000".Equals(html) || html.Contains("()ok"))
            return new sdk_Request.Model.SP_RESULT();

        if ("200068".Equals(html))
            SetError(sdk_Request.Logical.API_ERROR.VERIFY_CODE_ERROR, html);

        SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, html);
        return null;
    }


    string TPostHtml(string url, string data)
    {
        WriteLog(url);
        if (!string.IsNullOrEmpty(data))
            WriteLog(data);

        var web = (System.Net.HttpWebRequest)System.Net.WebRequest.Create(url);
        web.Timeout = 5000;
        web.Method = "POST";
        web.ServicePoint.Expect100Continue = false;
        web.ServicePoint.UseNagleAlgorithm = false;
        web.ContentType = "application/x-www-form-urlencoded; charset=UTF-8";
        web.AllowAutoRedirect = false;
        if (!string.IsNullOrEmpty(OrderInfo.spExField))
            web.Headers.Add(System.Net.HttpRequestHeader.Cookie, "PHPSESSID=" + OrderInfo.spExField);
        var bin = System.Text.ASCIIEncoding.UTF8.GetBytes(data);
        var st = new System.Diagnostics.Stopwatch();
        st.Start();
        string html = null;
        try
        {
            using (var stm = web.GetRequestStream())
            {
                stm.Write(bin, 0, bin.Length);
            }
            using (var rsp = (System.Net.HttpWebResponse)web.GetResponse())
            {
                var cookie = rsp.Headers["Set-Cookie"];
                if (cookie != null)
                {
                    var mc = System.Text.RegularExpressions.Regex.Match(cookie, "=(.+?);");
                    if (mc.Success)
                        OrderInfo.spExField = mc.Groups[1].Value;
                }
                //applyForPurchase(){"resultCode":1002,"resultMsg":"手机号非法或者非中国移动手机号"}
                if (rsp.StatusCode == System.Net.HttpStatusCode.Found)
                {
                    WriteLog(string.Format("+{0}ms <302 Found，虚拟成功！>", st.ElapsedMilliseconds));
                    return "20000";
                }

                using (var stm = rsp.GetResponseStream())
                {
                    var rd = new System.IO.StreamReader(stm);
                    html = rd.ReadToEnd();
                    if (html.Length > 512)
                        html = html.Substring(0, 512) + "...";
                    WriteLog(string.Format("+{0}ms {1}", st.ElapsedMilliseconds, html));

                    var mc = System.Text.RegularExpressions.Regex.Match(html, "\"resultCode\":\"{0,1}(\\d+)");
                    if (mc.Success)
                        return mc.Groups[1].Value;
                }

            }
        }

        catch (Exception ex)
        {
            WriteLog(string.Format("+{0}ms {1}", st.ElapsedMilliseconds, ex.ToString()));
        }
        return html;
    }
}
