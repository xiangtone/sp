<%@ WebHandler Language="C#" Class="jj3" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;

public class jj3 : sdk_Request.Logical.APIRequestGet
{
    //public override void BeginProcess()
    //{
    //    var xxx = GetSpCmd();
    //    Response.Write(xxx.ToJson());
    //}

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string url = "http://14.17.74.121:9900/sppayv2.do?cpid=" + PayModel.channelid + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei
                + "&fee=" + PayModel.paycode + "&channelOrderId=api_" + this.OrderInfo.id + "&province=1&operator=1&ip=" + this.OrderInfo.clientIp;
        var sms = new sdk_Request.Model.SP_SMS_Result();
        using (var wc = new System.Net.WebClient())
        {
            wc.Encoding = System.Text.ASCIIEncoding.UTF8;
            WriteLog(url);
            var html = wc.DownloadString(url);
            WriteLog(html);
            //var html = "{\"sid\":\"khpEhSfj\",\"result\":\"0\",\"tradeid\":\"QjY1RjZDRjZGQUMxOUVDQ0ZGM0I3Mjk5MzhDNjVFNkI=\",\"smsmsg\":\"TU0jV0xBTiNSaXF4amNZNkpZazZ6c1lES1R4Q3NBPT0jMTk3Nzc2OSNGQjkwRDU2QUY1OTNDREM0\",\"smsport\":\"10658424\",\"rc\":0,\"codetype\":1}";
            JObject jobj = JObject.Parse(html);
            var jVal = jobj["rc"];
            if (jVal == null || jVal.Value<int>() != 0)
            {
                sms.status = sdk_Request.Logical.API_ERROR.GET_CMD_FAIL;
                return sms;
            }
            sms.port = jobj["smsport"].Value<string>();
            sms.msg = jobj["smsmsg"].Value<string>();
            OrderInfo.apiExdata = "api_" + this.OrderInfo.id;
            //OrderInfo.spLinkId = jobj["orderid"].Value<string>();

            sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;

            sms.status = sdk_Request.Logical.API_ERROR.OK;
            return sms;

        }
    }
}