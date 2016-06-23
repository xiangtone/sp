<%@ WebHandler Language="C#" Class="jj31" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
///深圳梦想国度---华录视频
/// </summary>
public class jj31 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        string url = "http://115.28.88.192:8080/mmapi/pay.jsp?imsi=" + OrderInfo.imsi
                + "&imei=" + OrderInfo.imei
                + "&channel_id=" + PayModel.channelid
                + "&paycode=" + PayModel.paycode
                + "&app_id=" + PayModel.appid
                + "&ip=" + OrderInfo.clientIp;

        var html = GetHTML(url, 10000, null);
        var jobj = JObject.Parse(html);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        var jVal1 = jobj["result"];

        if (jVal1 != null && jVal1.Value<int>() == 0)
        {
            var sms = new sdk_Request.Model.SP_SMS_Result();
            sms.port = jobj["smsport"].Value<string>();
            sms.msg = jobj["smsmsg"].Value<string>();
            OrderInfo.spLinkId = jobj["sid"].Value<string>();
            //sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
            return sms;
        }
        SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
        return null;
    }

}