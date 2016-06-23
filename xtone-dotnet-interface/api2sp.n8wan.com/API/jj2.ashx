<%@ WebHandler Language="C#" Class="jj2" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;

public class jj2 : sdk_Request.Logical.APIRequestGet
{

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string url = "http://115.28.252.98:8080/paysmsgatewayV2/GetSmsInfoGatewayListener?pid=" + this.PayModel.paycode
            + "&sid=" + this.PayModel.appid + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei
            + "&ip=" + OrderInfo.clientIp + "&bsc_lac=" + OrderInfo.lac + "&bsc_cid=" + OrderInfo.cid;


        var html = GetHTML(url);


        JObject jobj = JObject.Parse(html);
        var jVal = jobj["resultcode"];
        if (jVal == null || jVal.Value<int>() != 0)
        {
            jVal = jobj["resultdescription"];
            if (jVal != null)
                SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jVal.Value<string>());
            else
                SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.port = jobj["accessNo"].Value<string>();
        sms.msg = jobj["sms"].Value<string>();
        OrderInfo.spLinkId = jobj["orderid"].Value<string>();
        //if (this.PayModel.appid == "1")
        sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;

        sms.status = sdk_Request.Logical.API_ERROR.OK;
        return sms;


    }
}