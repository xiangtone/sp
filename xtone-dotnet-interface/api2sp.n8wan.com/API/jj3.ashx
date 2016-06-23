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

        int pid = 1;
        var city = getCityByImsi(OrderInfo.imsi);

        if (city != null)
        {
            switch (city.province_id)
            {
                case 3: pid = 12; break;
                case 1: pid = 33; break;
                case 21: pid = 1; break;
                case 11: pid = 13; break;
                case 8: pid = 28; break;
                case 10: pid = 19; break;
                case 28: pid = 20; break;
                case 22: pid = 24; break;
                case 17: pid = 21; break;
                case 26: pid = 3; break;
                case 16: pid = 16; break;
                case 4: pid = 8; break;
                case 25: pid = 17; break;
                case 12: pid = 18; break;
                case 2: pid = 7; break;
                case 23: pid = 10; break;
                case 13: pid = 14; break;
                case 14: pid = 6; break;
                case 29: pid = 5; break;
                case 27: pid = 30; break;
                case 6: pid = 29; break;
                case 24: pid = 15; break;
                case 20: pid = 4; break;
                case 7: pid = 27; break;
                case 19: pid = 9; break;
                case 5: pid = 23; break;
                case 32: pid = 2; break;
                case 31: pid = 26; break;
                case 15: pid = 31; break;
                case 30: pid = 25; break;
                case 9: pid = 11; break;
                case 18: pid = 22; break;
            }
        }
        OrderInfo.apiExdata = string.Format("api_{0}_{1}_{2:X}", PayModel.channelid, PayModel.paycode, this.OrderInfo.id);

        string url = "http://14.17.74.121:9900/sppayv2.do?cpid=" + PayModel.channelid
                + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei
                + "&fee=" + PayModel.paycode
                + "&channelOrderId=" + OrderInfo.apiExdata
                + "&province=" + pid.ToString()
                + "&operator=1&ip=" + this.OrderInfo.clientIp
                + "&phoneType=mi3s"
                + "&brand=XiaoMi"
                + "&osbuild=19";


        var html = GetHTML(url);

        var jobj = JObject.Parse(html);
        var jval = jobj["result"];
        var code = jval == null ? -1 : jval.Value<int>();
        if (code != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        OrderInfo.spLinkId = jobj["sid"].ToString();
        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.msg = jobj["smsmsg"].Value<string>();
        sms.port = jobj["smsport"].Value<string>();
        jval = jobj["codetype"];
        if (jval.Value<int>() == 2)
        {
            sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
        }
        else
        {
            sms.msg = System.Text.ASCIIEncoding.UTF8.GetString(Convert.FromBase64String(sms.msg));
        }
        return sms;

    }
}