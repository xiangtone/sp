<%@ WebHandler Language="C#" Class="jj91" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
///指悦无限--联通小沃
/// </summary>
public class jj91 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        var url = "http://59.153.151.147/dmfp/sp/get_dxwo.php?cp_id=" + PayModel.paycode + "&cp_fee=" + PayModel.appid + "&cp_imsi=" + OrderInfo.imsi + "&cp_imei=" + OrderInfo.imei + "&cp_ip="
            + OrderInfo.clientIp + "&mobile=" + OrderInfo.mobile + "&ip=" + OrderInfo.clientIp + "&cp_param=" +"0"+OrderInfo.id;
        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var ars = html.Split(new string[] { "#" }, StringSplitOptions.RemoveEmptyEntries);
        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.port = ars[1];
        sms.msg = ars[2];
        return sms;
    }
   
}