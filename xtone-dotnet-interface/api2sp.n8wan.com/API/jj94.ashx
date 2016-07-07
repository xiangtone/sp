<%@ WebHandler Language="C#" Class="jj94" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
/// 次世代-世纪佳缘鲜花
/// </summary>
public class jj94 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        var url = "http://123.56.91.64:9090/reqbill/req.jsp?phone=" +OrderInfo.mobile+"&pid=" + PayModel.paycode+"&cpparam=" + PayModel.channelid+PayModel.paycode+OrderInfo.id;
        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jcode = jobj["retcode"];
        var jretmsg = jobj["retmsg"];
        if (jcode != null && !jcode.ToString().Equals( "000000"))
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jretmsg.ToString());
            return null;
        }
       
        OrderInfo.spLinkId = jobj["cpparam"].Value<string>();
        OrderInfo.spExField = PayModel.channelid + PayModel.paycode + OrderInfo.id;
        return new sdk_Request.Model.SP_RESULT();
            
    }
    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        var url = "http://123.56.91.64:9090/reqbill/reqcode.jsp?cpparam=" + OrderInfo.spLinkId + "&vercode="+ OrderInfo.cpVerifyCode;
        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jcode = jobj["retcode"];
        if (jcode != null && jcode.Value<int>() != 000000)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        return new sdk_Request.Model.SP_RESULT();
    }
}