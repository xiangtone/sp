<%@ WebHandler Language="C#" Class="jj78" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
/// 榕元-DDO
/// </summary>
public class jj78 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var url = "http://api.jiebasdk.com/smspay/pay?";
        var jjson = new JObject();
        jjson["mobile"] = OrderInfo.mobile;
        jjson["chargeCode"] = PayModel.paycode;
        jjson["callbackUrl"] = "http://61.160.251.130:5151/interface/323";
        jjson["transmissionData"] = PayModel.channelid + OrderInfo.id;
        var html = PostHTML(url, jjson.ToString(), 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jcode = jobj["code"];
        var jorderId = jobj["result"]["actionList"][0]["actionParam"]["orderId"];
        OrderInfo.spLinkId = jorderId.Value<string>();
        OrderInfo.apiExdata = PayModel.channelid + OrderInfo.id;
        OrderInfo.spExField = jobj["result"]["actionList"][0]["actionTarget"].Value<string>();
        if (jcode != null && jcode.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        return new sdk_Request.Model.SP_RESULT();
    }
    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        var url = OrderInfo.spExField;
        var jobj = new JObject();
        jobj["orderId"] = OrderInfo.spLinkId;
        jobj["authCode"] = OrderInfo.cpVerifyCode;
        var html = PostHTML(url, jobj.ToString(), 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jjobj = JObject.Parse(html);
        var jcode = jjobj["code"];
        if (jcode != null && jcode.Value<int>() != 0)
        {
            var jmsg = jobj["msg"].Value<string>();
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jmsg);
        }
         return new sdk_Request.Model.SP_RESULT();
    }
}