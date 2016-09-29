<%@ WebHandler Language="C#" Class="jj85" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;

public class jj85 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        var city = getCityByImsi(OrderInfo.imsi);
        var pid = 0;
        switch (city.province_id)
        {
            case 3: pid = 12; break;
            case 1: pid = 1; break;
            case 21: pid = 13; break;
            case 11: pid = 28; break;
            case 8: pid = 19; break;
            case 10: pid = 20; break;
            case 28: pid = 24; break;
            case 22: pid = 21; break;
            case 17: pid = 3; break;
            case 26: pid = 16; break;
            case 16: pid = 8; break;
            case 4: pid = 17; break;
            case 25: pid = 18; break;
            case 12: pid = 7; break;
            case 2: pid = 10; break;
            case 23: pid = 14; break;
            case 13: pid = 6; break;
            case 14: pid = 5; break;
            case 29: pid = 30; break;
            case 27: pid = 29; break;
            case 6: pid = 15; break;
            case 24: pid = 4; break;
            case 20: pid = 27; break;
            case 7: pid = 9; break;
            case 19: pid = 23; break;
            case 5: pid = 2; break;
            case 31: pid = 26; break;
            case 15: pid = 31; break;
            case 30: pid = 25; break;
            case 9: pid = 11; break;
            case 18: pid = 22; break;

        }

        OrderInfo.spExField = string.Format("api{0}", OrderInfo.id);

        var url = "http://118.186.245.157/hy_work/interface/init.php?type=" + PayModel.appid
            + "&i=0&siteid=" + PayModel.channelid + "&codeid=" + PayModel.paycode
            + "&phone=" + OrderInfo.mobile + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei
            + "&serial=" + OrderInfo.spExField + "&province=" + pid.ToString() + "&ip=" + OrderInfo.clientIp;



        var html = GetHTML(url);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        var jobj = JObject.Parse(html);
        var jval = jobj["hRet"];
        if (jval.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        jval = jobj["orderid"];
        if (jval != null)
        {
            OrderInfo.spLinkId = jval.ToString();
        }
        jval = jobj["fee"];
        if (jval == null)//验证码形式
            return new sdk_Request.Model.SP_RESULT();
        jobj = (JObject)jval;
        jval = jobj["num"];
        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.port = jval.ToString();
        jval = jobj["sms"];
        sms.msg = jval.ToString();
        if (!sms.msg.Contains("#"))
        {
            try
            {
                Convert.FromBase64String(sms.msg);
                sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
            }
            catch { }
        }

        return sms;


    }
    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        var url = "http://118.186.245.157/hy_work/interface/init.php?type=" + PayModel.appid
            + "&i=1&siteid=" + PayModel.channelid + "&codeid=" + PayModel.paycode
            + "&phone=" + OrderInfo.mobile + "&serial=" + OrderInfo.spExField + "&v=" + OrderInfo.cpVerifyCode
            + "&orderid=" + OrderInfo.spLinkId + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei;

        var html = GetHTML(url);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        var jobj = JObject.Parse(html);
        var jval = jobj["hRet"];
        if (jval.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }

        return new sdk_Request.Model.SP_RESULT();
    }

}