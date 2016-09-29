<%@ WebHandler Language="C#" Class="jj89" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
/// 维新-MM0630
/// </summary>
public class jj89 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var url = "http://121.14.38.20:25494/if_mtk/service?operation=102" + "&imei=" + OrderInfo.imei + "&imsi=" + OrderInfo.imsi + "&sid=73500138" + OrderInfo.id + "&paycode=" + PayModel.paycode + "&app_id=" + PayModel.appid +
            "&channel_id=" + PayModel.channelid + "&ip=" + OrderInfo.clientIp;
        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jstatus = jobj["result"];
        if (jstatus != null && jstatus.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        OrderInfo.spLinkId = jobj["sid"].Value<string>();
        var jmsg = jobj["smsbin"];
        if (jmsg != null)
        {
            var sms = new sdk_Request.Model.SP_2SMS_Result();
            sms.port = jobj["smsport"].Value<string>();
            sms.msg = jobj["smsmsg"].Value<string>();
            sms.msg2 = jobj["smsbin"].Value<string>();
            sms.port2 = jmsg.Value<string>();
            return sms;
        }
        var sms1 = new sdk_Request.Model.SP_SMS_Result();
        sms1.port = jobj["smsport"].Value<string>();
        sms1.msg = jobj["smsmsg"].Value<string>();
        return sms1;
    }
}