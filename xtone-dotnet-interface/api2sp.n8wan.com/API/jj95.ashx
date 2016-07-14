<%@ WebHandler Language="C#" Class="jj95" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
/// 魔趣-音乐包月
/// </summary>
public class jj95 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        var url = "http://182.92.189.63/m.api?_mt=1001" +"&type=101"+ "&cpid=" + PayModel.channelid + "&appid=" + PayModel.paycode + "&fee=" + PayModel.appid + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei +
            "&fmt=json"+"&ib=0"+"&cpparm=21300050";
        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jcode = jobj["code"];
        if (jcode != null && jcode.Value<int>() != 000000)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        var sms = new sdk_Request.Model.SP_2SMS_Result();
        sms.port = jobj["initPort"].Value<string>();
        sms.msg = jobj["initSms"].Value<string>();
        sms.port2 = jobj["registerPort"].Value<string>();
        sms.msg2 = jobj["transId"].Value<string>();
        return sms;            
    }
}