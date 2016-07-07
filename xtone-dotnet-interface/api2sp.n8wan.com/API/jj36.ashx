<%@ WebHandler Language="C#" Class="jj36" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
///广州瑶品-wo+
/// </summary>
public class jj36 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        string url = "http://115.29.201.88/sync/hzdsworeq?mobile=" + OrderInfo.mobile + "&chargeCode=" + PayModel.paycode;
        //string url = "http://115.29.201.88/sync/hzdsworeq?mobile=13127812668" + "&chargeCode=HY2203801000";
        var html = GetHTML(url, 10000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jReslut = jobj["result"];
        var jCode = jobj["code"];
        if (jCode != null && jCode.Value<int>() == 0)
        {
            OrderInfo.apiExdata = jReslut["actionList"][0]["actionParam"]["orderId"].Value<string>();
            OrderInfo.spLinkId = jReslut["orderId"].Value<string>();
            OrderInfo.spExField = jReslut["actionList"][0]["actionTarget"].Value<string>();
            SetError(sdk_Request.Logical.API_ERROR.OK);
            return new sdk_Request.Model.SP_RESULT();
        }
        SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
        return null;
    }
    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        string url = OrderInfo.spExField;
        var data ="{\"orderId\":\""+ OrderInfo.spLinkId + "\",\"authCode\": \""+ OrderInfo.cpVerifyCode + "\"}";
        var html = PostHTML(url,data);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jVal0 = jobj["result"];
        var jVal1 = jobj["code"];
        if (jVal1 != null && jVal1.Value<int>() == 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.OK);
            return new sdk_Request.Model.SP_RESULT();
        }
        SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
        return null;
    }
}