<%@ WebHandler Language="C#" Class="jj8" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;

public class jj8 : sdk_Request.Logical.APIRequestGet
{

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var url = "http://219.133.55.209/MrkjInfo/CMCCXM/GetMsg.aspx?imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei + "&ServiceIDUP=" + PayModel.paycode;

        var html = GetHTML(url);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        //{"code":"0005","message":"","port":"0","sms":"0"}
        //      {
        //    "MOURL": ",
        //    "ServiceIDUP": "MSP7564_0_SZHT",
        //    "accessNo": "1065842230",
        //    "outTradeNo": "",
        //    "resultCode": "0",
        //    "resultDescription": "success",
        //    "sms": "MDAwMDQ3Nzk4NlE1NjgvJTI5MjI5MDk0ZjAwYTc3NDNNSDBxNDIid113Zk1RTm91NXdBW2pPZnJycXEoPT0zPz1fNGIqKzRJXTEwNGk0MjApUm02MDFlNDU1MjJlTTAwMHwwSDAwMDAwVFJdYWhPfiRCcGdtQ1FFcjhCbVZac0lRMGR5Pg==",
        //    "transactionId": "1603011455220331"
        //}


        var jobj = Newtonsoft.Json.Linq.JObject.Parse(html);
        var jVal = jobj["code"];
        if (jVal != null && jVal.Value<int>() != 0)
        {
            var code = jVal.Value<int>();
            jVal = jobj["message"];
            var msg = jVal ?? jVal.Value<string>();

            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, string.Format("{0} {1}", code, msg));
            return null;
        }

        jVal = jobj["transactionId"];
        OrderInfo.spLinkId = jVal.Value<string>();
        var sms = new sdk_Request.Model.SP_SMS_Feedback_Result();
        sms.port = jobj["accessNo"].Value<string>();
        sms.msg =  System.Text.ASCIIEncoding.UTF8.GetString(Convert.FromBase64String(jobj["sms"].Value<string>()));
        sms.FeedBack = jobj["MOURL"].Value<string>();
        sms.Interval = 30;
        //sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;

        return sms;

    }
    

}