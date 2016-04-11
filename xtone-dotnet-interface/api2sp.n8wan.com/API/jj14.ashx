<%@ WebHandler Language="C#" Class="jj14" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
/// 深圳脉动---同趣游戏
/// </summary>
public class jj14 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string url = "http://120.76.100.12/proc/InterfaceT/cp/xezf1.aspx?Mid=" + PayModel.appid + "&iccid=" + OrderInfo.extraParams + "&mobile=" + OrderInfo.mobile + "&msg="
            + PayModel.channelid + "&fee=" + PayModel.paycode + "&imei=" + OrderInfo.imei + "&imsi=" + OrderInfo.imsi + "&clientip=" + OrderInfo.clientIp;

        var html = GetHTML(url, 10000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        var jobj = Newtonsoft.Json.Linq.JObject.Parse(html);
        var jVal = jobj["retCode"];
        if (!"0000".Equals(jVal.Value<string>()))
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }

        sdk_Request.Model.SP_SMS_Result sms;
        jVal = jobj["mo2"];
        if (jVal == null || string.IsNullOrEmpty(jVal.Value<string>()))
        {
            sms = new sdk_Request.Model.SP_SMS_Result();
        }
        else
        {
            var sms2 = new sdk_Request.Model.SP_2SMS_Result();
            sms2.msg2 = jVal.Value<string>();
            sms2.port2 = jobj["called2"].Value<string>();
            sms2.interval = 5;
            sms = sms2;
        }
        sms.port = jobj["called"].Value<string>();
        sms.msg = jobj["mo"].Value<string>();

        return sms;
    }

}