<%@ WebHandler Language="C#" Class="jj41" %>

using System;
using System.Web;

public class jj41 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        OrderInfo.apiExdata = OrderInfo.id.ToString("00000000000") + PayModel.appid;


        var url = "http://61.145.122.156:40004/Bill_ZXF?paycode=" + PayModel.paycode
            + "&imsi=" + OrderInfo.imsi
            + "&imei=" + OrderInfo.imei
            + "&data=" + OrderInfo.apiExdata
            + "&ip=" + OrderInfo.clientIp
            + "&channelid=" + PayModel.channelid;
        var html = GetHTML(url);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        if (html.Length < 20)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.msg = html;
        sms.port = "10658424";
        return sms;
   }

}