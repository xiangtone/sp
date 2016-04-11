<%@ WebHandler Language="C#" Class="jj12" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
/// <summary>
/// 上海可酷 沃+
/// </summary>
public class jj12 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string url = string.Format("http://139.196.53.146:8888/keku/wo/monthpayment/getcode?" + "&content="+PayModel.paycode + "&phone="
            + OrderInfo.mobile);
        var html = GetHTML(url);
        var jobj = Newtonsoft.Json.Linq.JObject.Parse(html);
        var jVal = jobj["status"];
        if (jVal != null && jVal.Value<int>() != 0)
        {
                SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        var jValod = jobj["tradeid"];
        OrderInfo.spLinkId = jValod.ToString();
        return new sdk_Request.Model.SP_RESULT();
    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        string url = "http://139.196.53.146:8888/keku/wo/monthpayment /confirm?" + "&code=" + OrderInfo.cpVerifyCode + "&tradeid=" + OrderInfo.spLinkId;
        var html = GetHTML(url);
        var jobj = Newtonsoft.Json.Linq.JObject.Parse(html);
        var jVal = jobj["status"];
        if (jVal != null && jVal.Value<int>() != 0)
        {
            if (jVal.Value<int>() == 1)
                SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            else
                SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        return new sdk_Request.Model.SP_RESULT();
    }

}