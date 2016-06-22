<%@ WebHandler Language="C#" Class="pc_web_game" %>

using System;
using System.Web;
using System.Collections.Generic;
using System.Text.RegularExpressions;
using System.Linq;

public class pc_web_game : sdk_Request.Logical.APIRequestGet
{
    const string url = "http://g.10086.cn/pay/open/index";
    string CHANNELID;//= "41356000";
    string APP;//= "fsz";
    string key;//= "99f8151b2230803a5092249bfb54b20a";
    string callback;
    const string FORMAT = "xml";

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        if (!InitKey())
        {
            SetError(sdk_Request.Logical.API_ERROR.ERROR_PAY_POINT, "InitKeys错误");
            return null;
        }

        var sessionKey = GetSessionKey();
        if (string.IsNullOrEmpty(sessionKey))
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, "getSessionKey fail!");
            return null;
        }

        var dic = new Dictionary<string, string>();
        dic["app"] = APP;
        dic["method"] = "applyforpurchase";
        dic["tel"] = OrderInfo.mobile;
        dic["consumecode"] = PayModel.paycode;//道具ID

        dic["salechannelid"] = CHANNELID;
        dic["time"] = null;
        dic["sessionkey"] = sessionKey;
        dic["format"] = FORMAT;
        var data = HashSign(dic);
        var html = PostHTML(url, data);

        var mc = Regex.Match(html, "<resultCode>(.+?)</resultCode>");
        var code = int.Parse(mc.Groups[1].Value);
        sdk_Request.Logical.API_ERROR err;
        switch (code)
        {
            case 200000:
            case 2000:
                mc = Regex.Match(html, "<orderid>(.+?)</orderid>");
                OrderInfo.spLinkId = mc.Groups[1].Value;
                return new sdk_Request.Model.SP_RESULT();//成功
            case 200002: err = sdk_Request.Logical.API_ERROR.BLACK_USER; break;
            default: err = sdk_Request.Logical.API_ERROR.GET_CMD_FAIL; break;

        }

        mc = Regex.Match(html, "<resultMsg>(.+?)</resultMsg>");
        SetError(err, mc.Success ? mc.Groups[1].Value : null);
        return null;

    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        if (!InitKey())
        {
            SetError(sdk_Request.Logical.API_ERROR.ERROR_PAY_POINT, "InitKeys错误");
            return null;
        }

        var sessionKey = GetSessionKey();

        var dic = new Dictionary<string, string>();
        dic["app"] = APP;
        dic["method"] = "confirmpurchase";
        dic["verifycode"] = OrderInfo.cpVerifyCode;
        dic["orderid"] = OrderInfo.spLinkId;
        dic["time"] = null;
        dic["sessionkey"] = sessionKey;
        dic["format"] = FORMAT;
        var data = HashSign(dic);

        //wc.Encoding = System.Text.ASCIIEncoding.UTF8;
        //wc.Headers["Content-Type"] = "application/x-www-form-urlencoded;";
        // var html = Regex.Unescape(wc.UploadString(url, data));

        var html = PostHTML(url, data);
        var mc = Regex.Match(html, "<resultCode>(.+?)</resultCode>");
        var code = int.Parse(mc.Groups[1].Value);
        sdk_Request.Logical.API_ERROR err;
        switch (code)
        {
            case 200000:
            case 2000:
                SuccessFeedback();
                return new sdk_Request.Model.SP_RESULT();//成功
            case 200068: err = sdk_Request.Logical.API_ERROR.VERIFY_CODE_ERROR; break;
            default: err = sdk_Request.Logical.API_ERROR.GET_CMD_FAIL; break;

        }

        mc = Regex.Match(html, "<resultMsg>(.+?)</resultMsg>");

        SetError(err, mc.Success ? mc.Groups[1].Value : null);
        return null;

    }


    bool InitKey()
    {
        string[] keys = PayModel.appid.Split(new char[] { ',' }, 2);
        if (keys.Length != 2)
            return false;
        APP = keys[0];
        key = keys[1];

        keys = PayModel.channelid.Split(new char[] { ',' }, 2);
        if (keys.Length != 2)
            return false;

        CHANNELID = keys[0];
        callback = keys[1];
        return true;
    }

    void SuccessFeedback()
    {
        var data = new System.Text.StringBuilder(callback);
        data.Append(callback.Contains('?') ? '&' : '?');
        data.AppendFormat("mobile={0}&", OrderInfo.mobile);
        data.AppendFormat("orderId={0}&", OrderInfo.spLinkId);
        data.AppendFormat("appId={0}&", APP);
        data.AppendFormat("channelId={0}&", CHANNELID);
        data.AppendFormat("codeId={0}", PayModel.paycode);

        GetHTML(data.ToString(), 1000, null);
    }



    string GetSessionKey()
    {
        var dic = new Dictionary<string, string>();
        dic["app"] = APP;
        dic["method"] = "getsessionkey";
        dic["time"] = null;
        dic["format"] = FORMAT;
        var data = HashSign(dic);
        var html = PostHTML(url, data);
        var mc = Regex.Match(html, "<sessionkey>(.+?)</sessionkey>");
        if (!mc.Success)
            return null;
        return mc.Groups[1].Value;

        //using (var wc = new System.Net.WebClient())
        //{
        //    wc.Encoding = System.Text.ASCIIEncoding.UTF8;
        //    wc.Headers["Content-Type"] = "application/x-www-form-urlencoded;";
        //    var html = Regex.Unescape(wc.UploadString(url, data));
        //    Console.WriteLine(html);
        //    var mc = Regex.Match(html, "<sessionkey>(.+?)</sessionkey>");
        //    if (!mc.Success)
        //        return null;
        //    return mc.Groups[1].Value;
        //}
    }

    private string HashSign(Dictionary<string, string> dic)
    {
        var sb = new System.Text.StringBuilder();
        var igKeys = new string[] { "hash", "format", "salechannelid" };

        if (string.IsNullOrEmpty(dic["time"]))
            dic["time"] = ((DateTime.Now.ToUniversalTime().Ticks - 621355968000000000) / 10000000).ToString();

        foreach (var kv in dic)
        {
            var value = kv.Value;
            if (igKeys.Contains(kv.Key))
                continue;
            sb.AppendFormat("{0}={1}&", kv.Key, value);
        }
        sb.AppendFormat("key={0}", key);

        var val = sb.ToString();
        Console.WriteLine("hashString:" + val);

        var md5 = System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile(val, "MD5").ToLower();
        dic["hash"] = md5;
        sb.Clear();

        foreach (var kv in dic)
        {
            var value = kv.Value;
            sb.AppendFormat("{0}={1}&", kv.Key, value);
        }
        sb.Length--;

        return sb.ToString();

    }
}