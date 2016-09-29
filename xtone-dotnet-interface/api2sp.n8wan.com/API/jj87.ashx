<%@ WebHandler Language="C#" Class="jj87" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
/// 智卓创新-小额
/// </summary>
public class jj87 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var url = "http://exapi.0755mi.com/smspay/order?" + "merid=" + PayModel.paycode + "&amount=" + PayModel.appid + "&imei=" + "&iccid=" + OrderInfo.iccid + OrderInfo.imei + "&imsi=" + OrderInfo.imsi + "&expand=" + OrderInfo.id +
            "&clientip=" + OrderInfo.clientIp;
        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jresult = jobj["retcode"];
        var jresultmsg = jobj["retmsg"];
        if (jresult != null && jresult.Value<int>() != 0000)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jresultmsg.ToString());
            return null;
        }
        OrderInfo.apiExdata = OrderInfo.id.ToString();
        var jmsg = jobj["msg3"];
        if (jmsg != null)
        {
            var sms = new sdk_Request.Model.SP_2SMS_Result();
            sms.port = jobj["msg1"].Value<string>();
            sms.msg = jobj["msg2"].Value<string>();
            sms.msg2 = jobj["msg4"].Value<string>();
            sms.port2 = jmsg.Value<string>();
            return sms;
        }
        var sms1 = new sdk_Request.Model.SP_SMS_Result();
        sms1.port = jobj["data"]["msg1"].Value<string>();
        sms1.msg = jobj["data"]["msg2"].Value<string>();
        return sms1;
    }


}