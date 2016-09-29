<%@ WebHandler Language="C#" Class="jj84" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
/// 沐尚-wo+
/// </summary>
public class jj84 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        var url = "http://114.55.140.89:8080/CUCC/SmsPay/Pay?payCode=" + PayModel.paycode + "&mobile=" + OrderInfo.mobile + "&callbackUrl=" + PayModel.channelid + "&transmissionData=" + OrderInfo.id;
        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jcode = jobj["code"];
        var jmessage = jobj["message"].Value<string>();
        if(jcode!=null&&jcode.Value<int>()!=0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jmessage);
            return null; 
        }
        OrderInfo.apiExdata =OrderInfo.id.ToString();
        OrderInfo.spLinkId = jobj["result"]["orderId"].Value<string>();
        return new sdk_Request.Model.SP_RESULT();
    }
    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        var url = "http://114.55.140.89:8080/CUCC/SmsPay/ConfirmAuthCode?orderId=" + OrderInfo.spLinkId + "&authCode=" + OrderInfo.cpVerifyCode;
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