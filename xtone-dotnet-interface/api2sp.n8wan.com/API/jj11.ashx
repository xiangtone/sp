<%@ WebHandler Language="C#" Class="jj11" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;

/// <summary>
/// 易简- 验证码模式
/// </summary>
public class jj11 : sdk_Request.Logical.APIRequestGet
{
    const string url = "http://sms.ejamad.com/interfaceAction";

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string[] extrs = null;
        if (!string.IsNullOrEmpty(OrderInfo.extraParams))
            extrs = OrderInfo.extraParams.Split(new char[] { ',' }, 2, StringSplitOptions.RemoveEmptyEntries);

        string data = "{\"operation\":\"2\",\"reqChannel\":{\"ipAddr\":\"" + OrderInfo.clientIp
            + "\",\"appId\":\"" + PayModel.appid + "\",\"channelId\":\"" + PayModel.channelid + "\",\"imei\":\"" + OrderInfo.imei + "\",\"money\":" + PayModel.paycode
            + ",\"imsi\":\"" + OrderInfo.imsi + "\",\"orderId\":\"api_" + PayModel.paycode + "\""
            + ",\"mobile\":\"" + OrderInfo.mobile + "\"";


        if (extrs != null)
        {
            data += ",\"gameName\":\"" + extrs[0] + "\"";
            if (data.Length == 2)
                data += ",\"feeName\":\"" + extrs[1] + "\"";
        }
        data += "},\"version\":\"1.0.0\"}";


        var html = PostHTML(url, data, 10 * 1000, "utf-8");
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jObj = JObject.Parse(html);
        var jVal = jObj["status"];
        if (jVal == null || jVal.Value<int>() != 0)
        {
            jVal = jObj["resultMsg"];
            if (jVal == null)
                SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            else
                SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jVal.Value<string>());
            return null;
        }
        try
        {
            jVal = jObj["noteChannels"];
            jObj = (JObject)((JArray)jVal)[0];
            jVal = jObj["sequence"];
            OrderInfo.spLinkId = jVal.Value<string>();
        }
        catch
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        return new sdk_Request.Model.SP_RESULT();

    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        var data = "{\"operation\":\"1\",\"syncOrder\":{\"ipAddr\":\"" + OrderInfo.clientIp
            + "\",\"appId\":\"" + PayModel.appid
            + "\",\"channelId\":\"" + PayModel.channelid
            + "\",\"sendStatus\":\"0\",\"serviceType\":\"3219\",\"VCode\":\"" + OrderInfo.cpVerifyCode
            + "\",\"sequence\":\"" + OrderInfo.spLinkId
            + "\",\"money\":" + PayModel.paycode + "},\"version\":\"1.0.0\"}";
        var html = PostHTML(url, data, 10 * 1000, "utf-8");
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        if (html.Contains("验证失败"))
        {
            SetError(sdk_Request.Logical.API_ERROR.VERIFY_CODE_ERROR);
            return null;
        }

        var jObj = JObject.Parse(html);
        var jVal = jObj["status"];
        if (jVal == null || int.Parse(jVal.Value<string>()) != 0)
        {
            jVal = jObj["resultMsg"];
            if (jVal == null)
                SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            else
                SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jVal.Value<string>());
            return null;
        }
        return new sdk_Request.Model.SP_RESULT();

    }
}