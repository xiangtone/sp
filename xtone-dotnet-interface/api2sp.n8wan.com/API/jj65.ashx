<%@ WebHandler Language="C#" Class="jj65" %>

using System;
using System.Web;

public class jj65 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        OrderInfo.apiExdata = string.Format("{0:0000000000}zyh0", OrderInfo.id);

        var url = "http://61.145.122.156:40004/Bill_ZXF?paycode=" + PayModel.paycode
            + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei
            + "&data=" + OrderInfo.apiExdata + "&ip=" + OrderInfo.clientIp + "&channelid=" + PayModel.channelid;

        var html = GetHTML(url);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.port = "10658424";
        sms.msg = html;
        return sms;

    }
}