<%@ WebHandler Language="C#" Class="jj5" %>

using System;
using System.Web;

/// <summary>
/// 
/// </summary>
public class jj5 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        OrderInfo.apiExdata = "api" + OrderInfo.id.ToString();

        string url = "http://114.215.174.42:8080/online/getall?imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei
            + "&linkId=" + PayModel.paycode + "&cpparam=" + OrderInfo.apiExdata + "&chid=" + PayModel.channelid;

        var html = this.GetHTML(url);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        if (!html.Contains("####"))
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, html);
            return null;
        }

        var ar = html.Split(new string[] { "####" }, 2, StringSplitOptions.RemoveEmptyEntries);
        if (ar.Length != 2)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, "unkonw format");
            return null;
        }
        return new sdk_Request.Model.SP_SMS_Result() { port = ar[0], msg = ar[1] };
    }
}