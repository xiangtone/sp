<%@ WebHandler Language="C#" Class="jj24" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;

public class jj24 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string url = "http://serv.newmobo.com:4002/api/unspv1a/post";
        string json = "{\"api\": \"" + PayModel.appid + "\",\"ppid\": \"" + PayModel.paycode
                + "\",\"imsi\": \"" + OrderInfo.imsi + "\",\"imei\": \"" + OrderInfo.imei
                + "\",\"cpparam\": \"" + OrderInfo.apiExdata + "\",\"autotime\": 30}";
        json = "{\"api\": \"mgdm-certk\",\"ppid\": \"PPMHCMHOTA_011\",\"imsi\": \"460078429218339\",\"imei\": \"866568022922909\",\"cpparam\": \"\",\"autotime\": 30}";
        var html = DownloadHTML(url, json, 0, null, "application/json");
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = Newtonsoft.Json.Linq.JObject.Parse(html);

        var jVal = jobj["status"];
        if (jVal == null || jVal.Value<int>() != 100)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }

        jVal = jobj["certsms"];
        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.port = "1065842230";
        sms.msg = jVal.Value<string>();
        return sms;

    }

}