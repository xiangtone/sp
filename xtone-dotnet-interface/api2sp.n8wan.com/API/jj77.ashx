<%@ WebHandler Language="C#" Class="jj77" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
/// 1982-动漫DDO
/// </summary>
public class jj77 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var url = "http://120.27.146.14/excalibur/server/cdsm/A014/complete/pay.aspx?fee=" + PayModel.appid + "&imei=" + OrderInfo.imei + "&imsi=" + OrderInfo.imsi + "&userdata=" + 
            PayModel.paycode+"&ip="+OrderInfo.clientIp;

        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jstatus = jobj["status"];
        if (jstatus != null && jstatus.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        OrderInfo.spLinkId = jobj["orderid"].Value<string>();
        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.port = jobj["spnumber"].ToString();
        sms.msg = jobj["cmd"].ToString();
        SetError(sdk_Request.Logical.API_ERROR.OK);
        return sms;
    }
}