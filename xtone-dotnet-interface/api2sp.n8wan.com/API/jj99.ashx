<%@ WebHandler Language="C#" Class="jj99" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
/// 可酷沃+
/// </summary>
public class jj99 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        var url = "http://139.196.53.146:8888/keku/wo/getsmscode?phone=" + OrderInfo.mobile
            + "&content="+PayModel.paycode;
        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var status = jobj["status"];
        if (status != null && status.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        OrderInfo.spLinkId = jobj["tradeid"].Value<string>();
        return new sdk_Request.Model.SP_RESULT();
            
    }
    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        var url = "http://139.196.53.146:8888/keku/wo/confirm?tradeid=" + OrderInfo.spLinkId + "&code=" + OrderInfo.cpVerifyCode;
        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jcode = jobj["status"];
        if (jcode != null && jcode.Value<int>()!= 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        return new sdk_Request.Model.SP_RESULT();
    }
}