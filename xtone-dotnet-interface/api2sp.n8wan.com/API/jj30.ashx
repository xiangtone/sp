<%@ WebHandler Language="C#" Class="jj30" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
///深圳梦想国度---华录视频
/// </summary>
public class jj30 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        string url = "http://61.160.251.130:5151/interface/309?mobile=" + OrderInfo.mobile + "&cpparam=" + PayModel.paycode + OrderInfo.id + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei+
           "&iccid="+OrderInfo.iccid+"&ip="+OrderInfo.clientIp;
        var html = GetHTML(url, 10000, null);
        var jobj = JObject.Parse(html);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jVal1 = jobj["ResultCode"];
        var jarr=(JArray)jobj["MoSms"];
        if (jVal1.Value<int>() !=0)  SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT); 
        if (jarr.Count != 0 && jarr.Count == 1)
        {
            var jVal = jarr[0];
            var sms = new sdk_Request.Model.SP_SMS_Result();
            sms.port = jVal["PayChannel"].Value<string>();
            sms.msg = jVal["MoSmsMsg"].Value<string>();
            sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
            return sms;
        }
        if (jarr.Count != 0 && jarr.Count == 2)
        {
            var jVal = jarr[0];
            var jVal2 = jarr[1];
            var sms2 = new sdk_Request.Model.SP_2SMS_Result();
            sms2.port = jVal["PayChannel"].Value<string>();
            sms2.msg = jVal["MoSmsMsg"].Value<string>();
            sms2.port2 = jVal2["PayChannel"].Value<string>();
            sms2.msg2 = jVal2["MoSmsMsg"].Value<string>();
            sms2.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
            return sms2;
        }
 
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        
        
    }

}