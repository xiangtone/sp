<%@ WebHandler Language="C#" Class="jj76" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
/// 鼎兆运MM---大唐-GMEG赛事
/// </summary>
public class jj76 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var url = "http://www.mpay-agent.com:10800/mtdl_api_code/CommandApiAction?mobile=" + OrderInfo.mobile + "&channelNum=" + PayModel.channelid + "&subChannelNum="+ "&appID=" +PayModel.paycode +
            "&price_params="+ PayModel.appid + "&cpParams=" + OrderInfo.id + "&provider=YD" + "&flag=1000";

        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jresult = jobj["result"];
        var jerrorCode = jobj["errorCode"];
        if (jresult != null && jresult.Value<int>() != 2)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jresult.ToString());
            return null;
        }
        OrderInfo.apiExdata ="10"+ OrderInfo.id.ToString();
        OrderInfo.spLinkId = jobj["orderId"].Value<string>();
        OrderInfo.spExField = jobj["urlAddress"].Value<string>();
        SetError(sdk_Request.Logical.API_ERROR.OK);
        return null;
    }
    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        string url = OrderInfo.spExField + "&veifyCode=" + OrderInfo.cpVerifyCode;
        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        return new sdk_Request.Model.SP_RESULT();
    }
    
    
}