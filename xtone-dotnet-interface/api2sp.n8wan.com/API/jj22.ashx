<%@ WebHandler Language="C#" Class="jj22" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
/// 天正亿科--wo+
/// </summary>
public class jj22 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string url = "http://27.50.136.66:8901/tsms/getvcode?imsi=" + OrderInfo.imsi + "&mobile=" + OrderInfo.mobile + "&p_price=" + PayModel.appid + "&channelid=" + PayModel.paycode;
        var html = GetHTML(url, 10000, null);
        if (html == null)
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = Newtonsoft.Json.Linq.JObject.Parse(html);
        var jVal = jobj["resultcode"].ToString();
        var jVaM = jobj["transactionid"];
        if (jVaM == null)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }

        var jVaM1 = jobj["orderid"].ToString();
        if (jVal == "0")
        {
            OrderInfo.spLinkId = jVaM.ToString();
            OrderInfo.spExField = jVaM1;
            return new sdk_Request.Model.SP_RESULT();
        }
        var jMsg = jobj["resultmsg"];
        //省份资费短缺
        if (jMsg == null)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        var msg = jMsg.ToString();
        if (msg.Contains("省份"))
            SetError(sdk_Request.Logical.API_ERROR.AREA_CLOSE, msg);
        else
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, msg);
        return null;

    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        string url = "http://27.50.136.66:8901/tsms/dopay?" + "transactionid=" + OrderInfo.spLinkId + "&orderid=" + OrderInfo.spExField + "&verifycode=" + OrderInfo.cpVerifyCode;
        var html = GetHTML(url, 10000, null);
        if (html == null)
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = Newtonsoft.Json.Linq.JObject.Parse(html);
        sdk_Request.Logical.API_ERROR code = sdk_Request.Logical.API_ERROR.GET_CMD_FAIL;
        var jVal = jobj["resultcode"].ToString();
        var jVaM = jobj["resultmsg"].ToString();
        if (jVal == "0") { return new sdk_Request.Model.SP_RESULT(); }
        switch (jVal)
        {
            case "910009": code = sdk_Request.Logical.API_ERROR.VERIFY_CODE_ERROR; break;
        }
        SetError(code, jVaM);
        return null;
    }

}