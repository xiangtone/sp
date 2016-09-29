<%@ WebHandler Language="C#" Class="jj25" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
///湖南朝晖----api
/// </summary>
public class jj25 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        string url = "http://115.28.254.133:8888/cmdmapi/Charge?cpid=839" + "&pid=" + PayModel.channelid + "&chid=" + PayModel.paycode + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei + "&mobile=" + OrderInfo.mobile + "&=Xiaomi_MI" +
            "&os=4.2.2" + "&ip=" + OrderInfo.clientIp + "&cpparam=" + OrderInfo.extrData;
        var html = GetHTML(url, 10000, null);
        if (html == null)
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = Newtonsoft.Json.Linq.JObject.Parse(html);
        var jVal = jobj["result"];
        OrderInfo.spLinkId = jobj["orderid"].ToString();
        if (jVal!=null && jVal.ToString() == "0")
        {
           
            
            var sms = new sdk_Request.Model.SP_SMS_Result();
            sms.port = jobj["smsport"].Value<string>();
            sms.msg = jobj["sms"].Value<string>();
            sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
            return sms;
        }
        SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
        return null;
        
    }
}