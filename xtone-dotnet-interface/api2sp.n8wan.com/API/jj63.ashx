<%@ WebHandler Language="C#" Class="jj63" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
/// 口袋龙-血族国度
/// </summary>
public class jj63 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string url = "http://42.96.169.232:8081/logic/jdGameSms?imei=" + OrderInfo.imei + "&imsi=" + OrderInfo.imsi + "&phone=" + OrderInfo.mobile + "&ssid=" + PayModel.paycode+
            "&cpparam=" + OrderInfo.apiExdata + "&cpid=" + PayModel.channelid + "&mobile=" + OrderInfo.mobile + "&iccid=" + OrderInfo.iccid + "&returnType=1000" + "&methodType=0" +
            "&encrypt=2";
        var html = GetHTML(url, 10000, null);
        var jobj = JObject.Parse(html);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jval = jobj["result"];
        var jorderId = jobj["orderId"];
        if (jval != null && jval.Value<int>() != 0)
        {
            var describe = "";
            switch (jval.Value<int>())
            {
                case -1: describe = "非法请求、渠道被禁用"; break;
                case -2: describe = "渠道的请求金额到上限"; break;
                case -3: describe = "无匹配的计费指令"; break;
                case -4: describe = "到用户上限"; break;
                case -5: describe = "请求的必填参数不正确"; break;
                case -6: describe = "无该运营商的api使用权限"; break;
                case -7: describe = "请求异常（ip、imsi等参数非法）"; break;
                case -10: describe = "服务器返回异常"; break;
            }
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, describe);
            return null;
        }
        OrderInfo.spExField = jorderId.Value<string>(); ;
        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.port = jobj["port"].Value<string>();
        sms.msg = jobj["command"].Value<string>();
        return sms;

    }

}