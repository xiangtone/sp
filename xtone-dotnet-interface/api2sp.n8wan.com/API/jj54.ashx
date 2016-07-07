<%@ WebHandler Language="C#" Class="jj54" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
/// 鸿运-乐视电视
/// </summary>
public class jj43 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string url = "http://58.64.143.245:8888/other/zwvideo.do?cpid=" + PayModel.appid + "&imsi="+OrderInfo.imsi+"&imei="+OrderInfo.imei+"&fee="+PayModel.paycode+"&ext="+OrderInfo.apiExdata;
        var html = GetHTML(url, 10000, null);
        var jobj = JObject.Parse(html);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jval = jobj["error"];
        if (jval != null && jval.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        var sms = new sdk_Request.Model.SP_2SMS_Result();
        sms.port = jobj["num1"].Value<string>();
        sms.msg = jobj["content1"].Value<string>();
        sms.port2 = jobj["num2"].Value<string>();
        sms.msg2 = jobj["content2"].Value<string>();
        sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
        return sms;
    }



}