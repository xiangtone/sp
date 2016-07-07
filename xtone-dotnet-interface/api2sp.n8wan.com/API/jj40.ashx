<%@ WebHandler Language="C#" Class="jj40" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
/// <summary>
/// 微新--沃商店
/// </summary>

public class jj40 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        var url = "http://120.27.146.14/excalibur/server/" + PayModel.channelid + "/woshop/first/pay.aspx?mobile=" + OrderInfo.mobile
                + "&price=" + PayModel.paycode
                + "&userdata=" + PayModel.appid
                + "&imsi=" + OrderInfo.imsi
                + "&imei=" + OrderInfo.imei;

        var html = GetHTML(url,1500,null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jObj = JObject.Parse(html);
        var jVal = jObj["status"];
        if (jVal == null || jVal.Value<int>() != 0)
        {
            int code = jVal == null ? -1 : jVal.Value<int>();
            jVal = jObj["message"];
            var msg = jVal == null ? null : jVal.ToString();
            switch (code)
            {
                case 13: SetError(sdk_Request.Logical.API_ERROR.AREA_CLOSE, msg); break;
                //case 99: SetError(sdk_Request.Logical.API_ERROR.VERIFY_CODE_ERROR, msg); break;
                default: SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, msg); break;
            }
            return null;
        }
        jVal = jObj["orderid"];
        OrderInfo.spLinkId = jVal.ToString();
        return new sdk_Request.Model.SP_RESULT();
    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        var url = "http://120.27.146.14/excalibur/server/" + PayModel.channelid
            + "/woshop/complete/pay.aspx?orderid=" + OrderInfo.spLinkId
            + "&code=" + OrderInfo.cpVerifyCode;

        var html = GetHTML(url);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        var jObj = JObject.Parse(html);
        var jVal = jObj["status"];
        if (jVal == null || jVal.Value<int>() != 0)
        {
            int code = jVal == null ? -1 : jVal.Value<int>();
            jVal = jObj["message"];
            var msg = jVal == null ? null : jVal.ToString();
            switch (code)
            {
                //case 13: SetError(sdk_Request.Logical.API_ERROR.AREA_CLOSE, msg); break;
                case 99: SetError(sdk_Request.Logical.API_ERROR.VERIFY_CODE_ERROR, msg); break;
                default: SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, msg); break;
            }
            return null;
        }
        return new sdk_Request.Model.SP_RESULT();
    }
}