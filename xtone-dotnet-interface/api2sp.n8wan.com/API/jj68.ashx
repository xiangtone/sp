<%@ WebHandler Language="C#" Class="jj68" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
public class jj68 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var chn = PayModel.paycode.Split(new char[] { ',' });

        if (chn.Length > 1)
            OrderInfo.apiExdata = string.Format("{0}{1}{2:yyMM}{3}", chn[1], OrderInfo.troneId, DateTime.Today, OrderInfo.id);
        else
            OrderInfo.apiExdata = string.Format("{0}{1:yyMM}{2}", OrderInfo.troneId, DateTime.Today, OrderInfo.id);

        var url = "http://112.74.111.56:9080/owngateway/yzm/getcode?fee=" + PayModel.appid
                + "&imsi=" + OrderInfo.imsi
                + "&ip=" + OrderInfo.clientIp
                + "&cpparam=" + OrderInfo.apiExdata
                + "&mobile=" + OrderInfo.mobile
                + "&appname=xiaomiexing"
                + "&subject=goumaidaoju"
                + "&type=" + chn[0]
                + "&channelid=" + PayModel.channelid;

        var html = GetHTML(url, 1500, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jObj = JObject.Parse(html);
        if (jObj["flag"] != null && jObj["flag"].Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        OrderInfo.spLinkId = jObj["linkid"].ToString();
        return new sdk_Request.Model.SP_RESULT();

    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        var url = "http://112.74.111.56:9080/owngateway/yzm/submitcode?"
            + "linkid=" + OrderInfo.spLinkId
            + "&verifycode=" + OrderInfo.cpVerifyCode;

        var html = GetHTML(url);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jObj = JObject.Parse(html);
        var jval = jObj["flag"];
        if (jval != null && jval.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        return new sdk_Request.Model.SP_RESULT();
    }
}