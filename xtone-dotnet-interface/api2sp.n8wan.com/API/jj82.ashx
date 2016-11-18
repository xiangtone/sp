<%@ WebHandler Language="C#" Class="jj82" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;

public class jj82 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        OrderInfo.apiExdata = string.Format("api_{0}", OrderInfo.id);

        var url = "http://121.14.38.20:25494/if_mtk/service?operation=102&imei=" + OrderInfo.imei
                + "&imsi=" + OrderInfo.imsi
                + "&sid=" + OrderInfo.apiExdata
                + "&paycode=" + PayModel.paycode
                + "&app_id=" + PayModel.appid
                + "&channel_id=" + PayModel.channelid
                + "&ip=" + OrderInfo.clientIp;
        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jval = jobj["result"];
        if (jval == null || jval.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        OrderInfo.spLinkId = jobj["tradeid"].Value<string>();

        var sms = new sdk_Request.Model.SP_SMS_Result();

        sms.port = jobj["smsport"].Value<string>();

        jval = jobj["smsmsg"];
        if (jval == null || string.IsNullOrEmpty(jval.ToString()))
        {
            jval = jobj["smsbin"];
            sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
        }
        sms.msg = jval.ToString();
        if (sms.SMSType != sdk_Request.Logical.E_SMS_TYPE.Data)
            sms.msg = System.Text.ASCIIEncoding.Default.GetString(Convert.FromBase64String(sms.msg));

        return sms;


    }
}