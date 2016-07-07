<%@ WebHandler Language="C#" Class="jj35" %>

using System;
using System.Web;
using System.Web.Security;
using Newtonsoft.Json.Linq;

public class jj35 : sdk_Request.Logical.APIRequestGet
{
    const string skey = "ht0428";
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string url = "http://123.56.91.64:8888/igameunpay/req.jsp";
        var dict = new System.Collections.Generic.Dictionary<string, string>();
        var chnId = PayModel == null ? "szhaot" : PayModel.channelid;
        var paycode = PayModel == null ? "010" : PayModel.paycode;

        dict["merid"] = chnId; //PayModel.channelid;
        dict["goodsid"] = paycode;//PayModel.paycode;
        dict["mobileid"] = OrderInfo.mobile;
        dict["clientip"] = OrderInfo.clientIp;
        dict["orderid"] = OrderInfo.spLinkId = string.Format("{0}{1:0000000000}", chnId, OrderInfo.id);
        dict["orderdate"] = DateTime.Now.ToString("yyyyMMddHHmmss");
        dict["platType"] = "9";
        dict["returl"] = "http://baidu.com/";
        dict["merpriv"] = OrderInfo.spLinkId;
        dict["IMEI"] = OrderInfo.imei;
        dict["IMSI"] = OrderInfo.imsi;
        var val = string.Join(string.Empty, dict.Values);
        val += skey;
        var md5 = FormsAuthentication.HashPasswordForStoringInConfigFile(val, "md5");
        WriteLog("value:" + val + ",md5:" + md5);
        dict["sign"] = md5;
        System.Text.StringBuilder sb = new System.Text.StringBuilder();
        foreach (var kv in dict)
        {
            sb.AppendFormat("{0}={1}&", kv.Key, Server.UrlEncode(kv.Value));
        }
        sb.Length--;
        var html = PostHTML(url, sb.ToString());
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        var jobj = JObject.Parse(html);
        var jval = jobj["retCode"];
        if (jval == null || !jval.HasValues || jval.Value<int>() != 0)
        {
            jval = jobj["retMsg"];
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jval != null ? jval.Value<string>() : null);
            return null;
        }
        jval = jobj["paytype"];
        if ("YZM".Equals(jval.Value<string>(), StringComparison.OrdinalIgnoreCase))
            return new sdk_Request.Model.SP_RESULT();

        jval = jobj["called2"];
        sdk_Request.Model.SP_SMS_Result sms = null;
        if (jval != null && jval.HasValues)
        {
            var sms2 = new sdk_Request.Model.SP_2SMS_Result();
            sms2.port2 = jval.Value<string>();
            jval = jobj["mo2"];
            sms2.msg2 = jval.Value<string>();
            sms = sms2;
        }
        else
        {
            sms = new sdk_Request.Model.SP_SMS_Result();
        }
        sms.port = jobj["called"].Value<string>();
        sms.msg = jobj["mo"].Value<string>();
        return sms;
    }
    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        string url = "http://123.56.91.64:8888/igameunpay/submitvercode.jsp";

        var dict = new System.Collections.Generic.Dictionary<string, string>();
        dict["merid"] = PayModel.channelid;
        dict["orderid"] = OrderInfo.spLinkId;
        dict["orderdate"] = DateTime.Now.ToString("yyyyMMdd");
        dict["verifycode"] = OrderInfo.cpVerifyCode;

        var val = string.Join(string.Empty, dict.Values);
        val += skey;
        var md5 = FormsAuthentication.HashPasswordForStoringInConfigFile(val, "md5");
        WriteLog("value:" + val + ",md5:" + md5);
        dict["sign"] = md5;

        dict["sign"] = md5;
        System.Text.StringBuilder sb = new System.Text.StringBuilder();
        foreach (var kv in dict)
        {
            sb.AppendFormat("{0}={1}&", kv.Key, Server.UrlEncode(kv.Value));
        }
        sb.Length--;

        var html = PostHTML(url, sb.ToString());
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        var jobj = JObject.Parse(html);
        var jval = jobj["retCode"];
        if (jval == null || jval.Value<int>() != 0)
        {
            jval = jobj["retMsg"];
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jval != null ? jval.Value<string>() : null);
            return null;
        }
        return new sdk_Request.Model.SP_RESULT();

    }
}
 