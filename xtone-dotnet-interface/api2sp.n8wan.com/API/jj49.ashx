<%@ WebHandler Language="C#" Class="jj49" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;

public class jj49 : sdk_Request.Logical.APIRequestGet
{

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var url = "http://219.133.59.126:8089/Queue/GetSmsCode?excode=" + PayModel.paycode + "&type=3" + "&imei=" + OrderInfo.imei + "&imsi=" + OrderInfo.imsi + "&rstype=json";
                var html = GetHTML(url);
                if (string.IsNullOrEmpty(html))
                {
                    SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
                    return null;
                }
                var jobj = Newtonsoft.Json.Linq.JObject.Parse(html);
                var jrc = jobj["resCode"];
                if (jrc == null && jrc.Value<int>() != 0)
                {
                    SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
                    return null;
                }
                  var sms=new sdk_Request.Model.SP_2SMS_Result();
                  sms.msg = jobj["firstMsg"].Value<string>();
                  sms.port = jobj["firstPort"].Value<string>();
                  sms.msg2 = jobj["secondMsg"].Value<string>();
                  sms.port2 = jobj["secondPort"].Value<string>();
                  sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
                  return sms;

        }

    
}