<%@ WebHandler Language="C#" Class="jj66" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
public class jj66 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var url = "http://120.76.138.93:16504/qz/sms?cn=" + PayModel.paycode
        + "&imsi=" + OrderInfo.imsi
        + "&imei=" + OrderInfo.imei
        + "&price=" + PayModel.appid
        + "&billno=" + OrderInfo.id
        + "&cip=" + OrderInfo.clientIp;
        var html = GetHTML(url);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JArray.Parse(html);
        OrderInfo.apiExdata = OrderInfo.id.ToString();
        OrderInfo.spLinkId = OrderInfo.id.ToString();
        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.port = jobj[0]["address"].Value<string>();
        sms.msg = jobj[0]["SMS"].Value<string>();
        return sms;


    }
}