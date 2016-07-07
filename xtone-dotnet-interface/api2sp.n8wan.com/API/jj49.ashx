<%@ WebHandler Language="C#" Class="jj49" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;

public class jj49 : sdk_Request.Logical.APIRequestGet
{

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var url = "http://219.133.59.126:8089/Queue/GetSmsCode?excode=" + PayModel.paycode + "&type=3" + "&imei=" + OrderInfo.imei + "&imsi=" + OrderInfo.imsi + "&rstype=json";
        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = Newtonsoft.Json.Linq.JObject.Parse(html);
        var jrc = jobj["resCode"];
        if (jrc != null && jrc.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        var sms = new sdk_Request.Model.SP_2SMS_Result();
        sms.msg=System.Text.ASCIIEncoding.Default.GetString(Convert.FromBase64String(jobj["firstMsg"].ToString()));
        sms.port = jobj["firstPort"].Value<string>();
        sms.msg2=System.Text.ASCIIEncoding.Default.GetString(Convert.FromBase64String(jobj["secondMsg"].ToString()));
        sms.port2 = jobj["secondPort"].Value<string>();
        return sms;

    }


}