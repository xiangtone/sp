<%@ WebHandler Language="C#" Class="jj27" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
/// 瑶品_MM
/// </summary>
public class jj27 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        OrderInfo.apiExdata = "api_" + OrderInfo.id.ToString();
        string fee = null;
        switch (PayModel.paycode)
        {
            case "1063": fee = "100"; break;
            case "1064": fee = "200"; break;
            case "1065": fee = "400"; break;
            case "1066": fee = "600"; break;
            case "1067": fee = "800"; break;
            case "1068": fee = "1000"; break;
            default:
                SetError(sdk_Request.Logical.API_ERROR.ERROR_PAY_POINT);
                return null;
        }

        var vPhone = n8wan.Public.Library.GetPhoneByImsi(OrderInfo.imsi);
        if (string.IsNullOrEmpty(vPhone))
        {
            SetError(sdk_Request.Logical.API_ERROR.AREA_CLOSE);
            return null;
        }
        var pro = dBase.ExecuteScalar("select name from tbl_province where id in(select province_id from tbl_city  where id in( select city_id from  tbl_phone_locate where  phone=" + vPhone + "))");


        var url = "http://112.74.66.88:8088/center/getCommand.ashx?appId=" + PayModel.appid
             + "&channelId=" + PayModel.channelid
             + "&appFeeId=" + PayModel.paycode
             + "&fee=" + fee
             + "&extra=" + OrderInfo.apiExdata
             + "&imei=" + OrderInfo.imei
             + "&imsi=" + OrderInfo.imsi
             + "&os_model=" + (string.IsNullOrEmpty(OrderInfo.userAgent) ? "Lenovo+A808t" : OrderInfo.userAgent)
             + "&os_info=" + (string.IsNullOrEmpty(OrderInfo.sdkVersion) ? "4.2.3" : OrderInfo.sdkVersion)
             + "&net_info=" + (string.IsNullOrEmpty(OrderInfo.netType) ? "wifi" : OrderInfo.netType)
             + "&client_ip=" + OrderInfo.clientIp
             + "&province=" + Server.UrlEncode((string)pro);

        var html = GetHTML(url);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        var jobj = JObject.Parse(html);

        var jval = jobj["resultCode"];
        if (jval == null || jval.Value<int>() != 0)
        {
            if (jval == null)
                SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            else
            {
                switch (jval.Value<int>())
                {
                    case 1005://资费为空
                    case 3000://无支持通道
                    case 3001://通道无对应金额
                        SetError(sdk_Request.Logical.API_ERROR.ERROR_PAY_POINT); break;
                    case 1006://黑名单
                    case 1008://黑名单2
                        SetError(sdk_Request.Logical.API_ERROR.BLACK_USER); break;
                    case 1007://当前省份已主动屏蔽
                        SetError(sdk_Request.Logical.API_ERROR.AREA_CLOSE); break;
                    default:
                        SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL); break;
                }
            }
            return null;
        }

        //jval = jobj["type"];
        //if (jval.Value<int>() == 1)
        //{
        //    jval
        //}
        bool IsBase64 = jobj["smstype"].ToString() == "0";
        jval = jobj["init_sms"];
        if (jval == null || string.IsNullOrEmpty(jval.Value<string>()))
        {
            var sms = new sdk_Request.Model.SP_SMS_Result();
            sms.port = jobj["port"].Value<string>();
            sms.msg = jobj["cmd"].Value<string>();
            if (IsBase64)
            {
                sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
            }
            return sms;

        }
        else
        {
            var sms2 = new sdk_Request.Model.SP_2SMS_Result();
            sms2.port = jobj["init_sms_number"].ToString();
            sms2.msg = (string)jobj["init_sms"];
            sms2.port2 = jobj["port"].Value<string>();
            sms2.msg2 = jobj["cmd"].Value<string>();
            if (IsBase64)
            {
                sms2.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
            }

            return sms2;
        }




    }
}
