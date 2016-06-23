<%@ WebHandler Language="C#" Class="jj15" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
using System.Text.RegularExpressions;
/// <summary>
/// 广州瑶品 ---咪咕包月
/// </summary>
public class jj15 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        OrderInfo.apiExdata ="0267" + OrderInfo.id.ToString();
        string url = "http://114.215.207.119:8888/crack/charge.do?type="+PayModel.channelid+"&imei=" + OrderInfo.imei + "&imsi=" + OrderInfo.imsi+"&chargeid="+PayModel.appid+"&extdata="
            + OrderInfo.apiExdata;
        var html = GetHTML(url, 10000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = Newtonsoft.Json.Linq.JObject.Parse(html);
        var jVal = jobj["state"].ToString();
        var jVal1 = jobj["desc"].ToString();
        var sms = new sdk_Request.Model.SP_SMS_Result();
        if (jVal.ToString() == "0")
        {
            sms.port = jobj["port"].Value<string>();
            sms.msg = jobj["msg"].Value<string>();
            return sms;
        }
        else
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT,jVal1);
        }
        return null;
    }
}