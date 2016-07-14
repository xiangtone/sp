<%@ WebHandler Language="C#" Class="jj29" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
///深圳榕元---中投视频
/// </summary>
public class jj29 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        string url = "http://114.80.201.9:8089/xh161_video0422.php?channel=" + PayModel.channelid + "&serial=" + PayModel.paycode + OrderInfo.id + "&imsi=" + OrderInfo.imsi+"&imei="+OrderInfo.imei;
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
                sms2.port2 = jVal2["num"].Value<string>();
                sms2.msg2 = jVal2["sms"].Value<string>();
                sms2.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
                return sms2;
            }
        }
        SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
        return null;
    }

}