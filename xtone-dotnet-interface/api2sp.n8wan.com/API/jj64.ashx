<%@ WebHandler Language="C#" Class="jj64" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
/// 口袋龙-沃商店
/// </summary>
public class jj64 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string url = "http://video.yiqiao580.com:9900/crack/wostore/feesms.do?ptid=" + PayModel.channelid + "&imei" + OrderInfo.imei + "&imsi=" + OrderInfo.imsi + "&cpparam=" + PayModel.paycode + OrderInfo.id + "&feename=10000金币"
            + "&paycode=151014147761" + "&version=1.0.0" + "&payfee="+PayModel.appid;
        var html = GetHTML(url, 10000, null);
        var jobj = JObject.Parse(html);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jval = jobj["result"];
        var jorderno = jobj["orderno"];
        if (jval != null && jval.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        OrderInfo.spExField = jorderno.Value<string>();
        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.port = jobj["port"].Value<string>();
        sms.msg = jobj["sms"].Value<string>();
        sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
        return sms;

    }

}