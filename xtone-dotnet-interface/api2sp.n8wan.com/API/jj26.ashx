<%@ WebHandler Language="C#" Class="jj26" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
///深圳脉动---咪咕音乐
/// </summary>
public class jj26 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        string url = "http://i.thorassist.com/init.php?type=644" + "&siteid=" + PayModel.channelid + "&codeid=" + PayModel.appid + "&serial=" + OrderInfo.id + "&imsi=" + OrderInfo.imsi + "&imei="
            + OrderInfo.imei + "&ib=0";
        var html = GetHTML(url, 10000, null);
        var jobj = JObject.Parse(html);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jVal1 = jobj["hRet"];
        var jVal = jobj["Login"];
        var jVal2 = jobj["Fee"];
        if (jVal != null)
        {

            if (jVal1 != null && jVal1.Value<int>() == 0)
            {
                var sms2 = new sdk_Request.Model.SP_2SMS_Result();
                sms2.port = jVal["num"].Value<string>();
                sms2.msg = (string)jVal["sms"];
                sms2.port2 = jVal["num"].Value<string>();
                sms2.msg2 = jVal["sms"].Value<string>();
                return sms2;
            }
        }
        else
        {
            if (jVal1 != null && jVal1.Value<int>() == 0)
            {
                var sms = new sdk_Request.Model.SP_SMS_Result();
                sms.port = jVal2["num"].Value<string>();
                sms.msg = jVal2["sms"].Value<string>();
                sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
                return sms;
            }
        }
        SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
        return null;
    }

}