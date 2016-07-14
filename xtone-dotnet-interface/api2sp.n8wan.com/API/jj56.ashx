<%@ WebHandler Language="C#" Class="jj56" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
/// 卓羿文化-MM
/// </summary>
public class jj56 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string url = "http://112.74.111.56:9039/gamesit/puburl?channelid=" + PayModel.channelid + "&type=15" + "&imei=" + OrderInfo.imei + "&imsi=" + OrderInfo.imsi +
            "&fee=" + PayModel.appid + "&cpparam="+PayModel.paycode + (OrderInfo.id & 0xfFFff).ToString("x") + "&ip=" + OrderInfo.clientIp + "&bsc_lac=" + OrderInfo.lac + "&bsc_cid=" + OrderInfo.cid + "&appname=" 
            +Server.UrlEncode("ABC")+ "&subject=" + Server.UrlEncode("DEF");
        var html = GetHTML(url, 28000, null);
        var jobj = JObject.Parse(html);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jval = jobj["resultCode"];
        var jmessage = jobj["message"];
        if (jval != null && jval.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jmessage.ToString());
            return null;
        }
        OrderInfo.apiExdata = PayModel.paycode + (OrderInfo.id & 0xfFFff).ToString("x");
        if (OrderInfo.apiExdata.Length > 10)
            OrderInfo.apiExdata = OrderInfo.apiExdata.Substring(0, 10);

        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.port = jobj["accessNo"].Value<string>();
        sms.msg = jobj["sms"].Value<string>();
        sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
        return sms;
        
    }
  
}