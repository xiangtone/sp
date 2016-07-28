﻿<%@ WebHandler Language="C#" Class="jj96" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
///龙玲-mm
/// </summary>
public class jj96 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string url = "http://pay.sdk.new.5isy.com/center/getCommand.ashx?partnerId=2031"
                + "&appId=2127"
                + "&channelId=3236"
                 + "&appFeeId=" + PayModel.paycode
                  + "&fee=" + PayModel.appid
                + "&phone=" + OrderInfo.mobile
                + "&imei=" + OrderInfo.imei
                + "&imsi=" + OrderInfo.imsi
                + "&os_info=" + (string.IsNullOrEmpty(OrderInfo.sdkVersion) ? "4.1" : OrderInfo.sdkVersion)
                + "&os_model=XM3"
                + "&net_info=" + OrderInfo.netType
                + "&extra="+OrderInfo.id
                + "&timestamp=" + DateTime.Now.ToString("yyyyMMddHHmmss")
                + "&client_ip=" + OrderInfo.clientIp;
        var html = GetHTML(url, 10000, null);
        var jobj = JObject.Parse(html);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jVal = jobj["resultCode"];
        if (jVal != null && jVal.Value<int>() != 0)
        {
            var err = "";
            switch (jVal.ToString())
            {
                case "0000": err = "成功获取指令"; break;
                case "1001": err = "应用ID为空"; break;
                case "1002": err = "渠道ID为空"; break;
                case "1004": err = "计费点ID为空"; break;
                case "1005": err = "资费为空"; break;
                case "1006": err = "黑名单"; break;
                case "1007": err = "省份主动屏蔽"; break;
                case "1008": err = "黑名单"; break;
                case "1009": err = "请求间隔过短"; break;
                case "3000": err = "没有可用指令"; break;
                default:
                    SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
                    return null;
            }
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, err);
            return null;
        }

        //36735976DM10031000_DM10031000
        OrderInfo.spLinkId =jobj["orderid"].Value<string>();
        OrderInfo.apiExdata = OrderInfo.id.ToString();
        var jinit_sms = jobj["init_sms"];
        if (jinit_sms != null)
        {
            var sms = new sdk_Request.Model.SP_2SMS_Result();
            sms.port = jobj["init_sms_number"].Value<string>();
            sms.msg = jobj["init_sms"].Value<string>();
            sms.port2 = jobj["port"].Value<string>();
            sms.msg2 = jobj["cmd"].Value<string>();
            return sms;
        }
        var sms1 = new sdk_Request.Model.SP_SMS_Result();
        sms1.port = jobj["port"].Value<string>();
        sms1.msg = jobj["cmd"].Value<string>();
        return sms1;
    }
}