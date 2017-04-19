<%@ WebHandler Language="C#" Class="jj80" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
/// paycode :paycode
/// channelid:transmissionData透传开头
/// </summary>
public class jj80 : sdk_Request.Logical.AsyncAPIRequestGet
{
    /// <summary>
    /// channelid:透传固定[,回调地址]
    /// paycode:paycode
    /// </summary>
    /// <returns></returns>
    protected override sdk_Request.Model.SP_RESULT AsyncGetVerifyCode()
    {

        var chn = PayModel.channelid.Split(new char[] { ',' });
        string callback = null;
        if (chn.Length == 2)
            callback = chn[1];

        OrderInfo.apiExdata = string.Format("{0}{1:yyMM}{2}", chn[0], DateTime.Today, OrderInfo.id);

        var url = "http://114.55.140.89:8080/CMCC/SmsPay/Pay?payCode=" + PayModel.paycode
            + "&imsi=" + OrderInfo.imsi
            + "&imei=" + OrderInfo.imei
            + "&iccid=" + OrderInfo.iccid
            + "&mobile=" + OrderInfo.mobile
            + "&ip=" + OrderInfo.clientIp
            + "&transmissionData=" + OrderInfo.apiExdata;
        if (!string.IsNullOrEmpty(callback))
            url += "&callbackUrl=" + Server.UrlEncode(callback);

        var html = GetHTML(url);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jcode = jobj["code"];
        var jmessage = jobj["message"].Value<string>();
        if (jcode != null && jcode.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jmessage);
            return null;
        }
        OrderInfo.apiExdata = OrderInfo.id.ToString();
        OrderInfo.spLinkId = jobj["result"]["orderId"].Value<string>();
        return new sdk_Request.Model.SP_RESULT();
    }

    protected override sdk_Request.Model.SP_RESULT AsyncSubmitVerifyCode()
    {
        var url = "http://114.55.140.89:8080/CMCC/SmsPay/ConfirmAuthCode?orderId=" + OrderInfo.spLinkId + "&authCode=" + OrderInfo.cpVerifyCode;
        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jcode = jobj["code"];
        var jmessage = jobj["message"].Value<string>();
        if (jcode != null && jcode.Value<int>() != 0)
        {
            if (jcode.Value<int>() == 1)
            {
                jmessage.Contains("不开放的省份");
                var err = "计费失败：不开放的省份";
                SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, err);
                return null;
            }
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jmessage);
            return null;
        }
        return new sdk_Request.Model.SP_RESULT();
    }
}