<%@ WebHandler Language="C#" Class="jj44" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
public class jj44 : sdk_Request.Logical.APIRequestGet
{



    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var url = "http://114.80.120.122:8082/wlpaysdk/apitjrdo/tuanjurdo?appid=" + PayModel.appid
                + "&imei=" + OrderInfo.imei
                + "&imsi=" + OrderInfo.imsi
                + "&price=" + PayModel.paycode
                + "&mobile=" + OrderInfo.mobile;

        var html = GetHTML(url, 1500, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        //{  "message" : "tuanjurdo-1请求成功",  "orderNo" : "1000000000026063",  "code" : "1"}
        var jObj = JObject.Parse(html);
        var jval = jObj["message"];
        if (jObj["code"].Value<int>() != 1)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jval == null ? null : Server.UrlDecode(jval.ToString()));
            return null;
        }
        OrderInfo.spLinkId = jObj["orderNo"].ToString();
        return new sdk_Request.Model.SP_RESULT();

    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        var url = "http://114.80.120.122:8082/wlpaysdk/apitjrdo/tuanjurdo?appid=" + PayModel.appid
            + "&imei=" + OrderInfo.imei
            + "&imsi=" + OrderInfo.imsi
            + "&price=" + PayModel.paycode
            + "&mobile=" + OrderInfo.mobile
            + "&orderNo=" + OrderInfo.spLinkId
            + "&code=" + OrderInfo.cpVerifyCode;

        var html = GetHTML(url);
        //{  "message" : "tuanjurdo-2计费失败201%E8%AE%A2%E5%8D%95%E5%AE%89%E5%85%A8%E7%A0%81%E9%94%99%E8%AF%AF",  "orderNo" : "1000000000003675",  "code" : "-1"}
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jObj = JObject.Parse(html);
        var jval = jObj["message"];
        if (jObj["code"].Value<int>() != 1)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jval == null ? null : Server.UrlDecode(jval.ToString()));
            return null;
        }
        //OrderInfo.spLinkId = jObj["orderNo"].ToString();
        return new sdk_Request.Model.SP_RESULT();
    }
}