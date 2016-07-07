<%@ WebHandler Language="C#" Class="jj79" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
/// 1982-pc暮光
/// </summary>
public class jj79 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        var url = "http://221.130.14.106:5050/pc_pay_web/pc_game.php?content=" + PayModel.paycode + "&mobile=" + OrderInfo.mobile;
        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        OrderInfo.spLinkId = html + "_1";
        return new sdk_Request.Model.SP_RESULT();
    }
    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        var url = "http://221.130.14.106:5050/pc_pay_web/pc_game.php?mobile=" + OrderInfo.mobile + "&content="+OrderInfo.cpVerifyCode;
        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        if (html == "200000" || html == "2000"||html=="1")
        {
            return new sdk_Request.Model.SP_RESULT();
        }
        SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
        return null;
    }
}