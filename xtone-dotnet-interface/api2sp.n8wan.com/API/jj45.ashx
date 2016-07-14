<%@ WebHandler Language="C#" Class="jj45" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
///榕元---悦星工场
/// </summary>
public class jj45 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        string url = "http://123.57.144.101/cmsc/interface/huace/order1.jsp?channel=" + PayModel.channelid
            + "&mobile=" + OrderInfo.mobile
            + "&note=1000"
            + "&pc=" + PayModel.paycode;

        var html = GetHTML(url, 10000, null);
        var jobj = JObject.Parse(html);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
          
        var jVal1 = jobj["res_code"];
            if (jVal1 != null && jVal1.Value<int>() == 0)
            {
                OrderInfo.spExField=jobj["orderid"].ToString();
                SetError(sdk_Request.Logical.API_ERROR.OK);
                return new sdk_Request.Model.SP_RESULT();
            }
        SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
        return null;
    }
    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        string url = "http://123.57.144.101/cmsc/interface/huace/order2.jsp?orderid=" + OrderInfo.spExField + "&code="+OrderInfo.cpVerifyCode;
        var html = GetHTML(url, 10000, null);
        var jobj = JObject.Parse(html);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jVal1 = jobj["res_code"];
        if (jVal1 != null && jVal1.Value<int>() == 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.OK);
            return new sdk_Request.Model.SP_RESULT();
        }
        SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
        return null;
    }

}