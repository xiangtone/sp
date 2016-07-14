<%@ WebHandler Language="C#" Class="jj66" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
public class jj66 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var mobile = OrderInfo.mobile;
        if (string.IsNullOrEmpty(mobile))
        {
            mobile = n8wan.Public.Library.GetPhoneByImsi(OrderInfo.imsi);
            if (!string.IsNullOrEmpty(mobile))
                mobile += "0000";
        }

        var url = PayModel.channelid + "?cn=" + PayModel.paycode // "http://120.76.138.93:16504/qz/sms
        + "&imsi=" + OrderInfo.imsi
        + "&imei=" + OrderInfo.imei
        + "&price=" + PayModel.appid
        + "&billno=" + OrderInfo.id
        + "&cip=" + OrderInfo.clientIp
        + "&mobile=" + mobile;

        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }


        if (html.Contains("country limit full"))
        {
            SetError(sdk_Request.Logical.API_ERROR.SP_TRONE_DAY_OVER_LIMIT, "通道量满");
            return null;
        }
        if (html.Contains("province limit full"))
        {
            SetError(sdk_Request.Logical.API_ERROR.SP_TRONE_PROVINCE_OVER_DAY_LIMIT, "通道省份量满");
            return null;
        }

        if (html.Contains("province"))
        {

            var error = "error:省份错误";
            SetError(sdk_Request.Logical.API_ERROR.AREA_CLOSE, error.ToString());
            return null;
        }
        
        if (html.ToString().Length < 100)
        {
            var error = "error:请求失败";
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, error.ToString());
            return null;
        }

        var jobj = JArray.Parse(html);
        OrderInfo.apiExdata = OrderInfo.id.ToString();
        OrderInfo.spLinkId = OrderInfo.id.ToString();

        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.port = jobj[0]["address"].Value<string>();
        sms.msg = jobj[0]["SMS"].Value<string>();
        return sms;


    }
}