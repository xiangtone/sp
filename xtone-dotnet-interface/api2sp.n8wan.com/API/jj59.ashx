<%@ WebHandler Language="C#" Class="jj59" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;

public class jj59 : sdk_Request.Logical.APIRequestGet
{

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var phone = n8wan.Public.Library.GetPhoneByImsi(OrderInfo.imsi);
        if (string.IsNullOrEmpty(phone))
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, "IMSI 错误");
            return null;
        }
        phone += "0000";

        OrderInfo.apiExdata = PayModel.appid + (OrderInfo.id & 0xfFFff).ToString("x");
        if (OrderInfo.apiExdata.Length > 10)
            OrderInfo.apiExdata = OrderInfo.apiExdata.Substring(0, 10);

        var url = "http://182.150.6.35:8080/Pay12/API";
        var postData = "channel=" + PayModel.channelid
                    + "&imsi=" + OrderInfo.imsi
                    + "&iccid=" + (OrderInfo.iccid == null || OrderInfo.iccid.Length != 20 ? "" : OrderInfo.iccid)
                    + "&phone=" + (string.IsNullOrEmpty(OrderInfo.mobile) ? phone : OrderInfo.mobile)
                    + "&imei=" + OrderInfo.imei
                    + "&amount=" + PayModel.paycode
                    + "&ip=" + OrderInfo.clientIp
                    + "&extraData=" + OrderInfo.apiExdata;

        var html = PostHTML(url, postData);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }



        var jobj = JObject.Parse(html);
        var jval = jobj["retCode"];
        if (jval == null || jval.Value<int>() != 0)
        {
            jval = jobj["retMsg"];
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jval == null ? null : jval.ToString());
            return null;
        }
        jval = jobj["mo2"];
        sdk_Request.Model.SP_SMS_Result sms;
        if (jval != null && !string.IsNullOrEmpty(jval.ToString()))
        {
            sms = new sdk_Request.Model.SP_2SMS_Result()
            {
                msg2 = jval.ToString(),
                port2 = jobj["called2"].ToString(),
                interval = 5
            };
        }
        else
            sms = new sdk_Request.Model.SP_SMS_Result();

        sms.port = jobj["called"].ToString();
        sms.msg = jobj["mo"].ToString();

        return sms;

    }
}