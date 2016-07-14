<%@ WebHandler Language="C#" Class="jj51" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;

public class jj51 : sdk_Request.Logical.APIRequestGet
{

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        OrderInfo.apiExdata = OrderInfo.id.ToString();
        var name = string.Empty;
        var fee = 0;
        switch (PayModel.paycode)
        {
            case "151208157494": name = "高级金币大礼包"; fee = 800; break;
            case "151208157495": name = "初级金币大礼包"; fee = 400; break;
            case "151208157496": name = "体力上限"; fee = 400; break;
            case "151208157497": name = "肥料大礼包"; fee = 200; break;
            case "151208157498": name = "超值大礼包"; fee = 200; break;
            case "151208157499": name = "手套"; fee = 200; break;
            case "151208157500": name = "铲车"; fee = 400; break;
            case "151208157501": name = "魔法棒"; fee = 400; break;
            case "151208157502": name = "炸弹"; fee = 600; break;
            default: SetError(sdk_Request.Logical.API_ERROR.ERROR_PAY_POINT); return null;
        }


        var url = "http://wostore.yiqiao580.com:9900/crack/wostore/feesms.do?ptid=" + PayModel.appid
            + "&imsi=" + OrderInfo.imsi
            + "&imei=" + OrderInfo.imei
            + "&paycode=" + PayModel.paycode
            + "&feename=" + Server.UrlEncode(name)
            + "&payfee=" + fee.ToString()
            + "&cpparam=" + OrderInfo.apiExdata;

        var html = GetHTML(url);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        //{"result":0,"sid":"4054","sms":"base64","port":"10655198018","req":null,"youshu":null,"orderno":"647668470232557965324486"}
        var jObj = JObject.Parse(html);
        var jval = jObj["result"];
        if (jval == null || jval.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        jval = jObj["orderno"];
        if (jval != null)
            OrderInfo.spLinkId = jObj["orderno"].ToString();
        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
        sms.msg = Convert.ToBase64String(System.Text.ASCIIEncoding.UTF8.GetBytes(jObj["sms"].ToString()));
        sms.port = jObj["port"].ToString();
        return sms;
    }
}