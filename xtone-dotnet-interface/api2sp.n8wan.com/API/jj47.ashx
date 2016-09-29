<%@ WebHandler Language="C#" Class="jj47" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
public class jj47 : sdk_Request.Logical.APIRequestGet
{

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        OrderInfo.apiExdata = string.Format("{0}_{1}", PayModel.appid, OrderInfo.id);
        var city = getCityByImsi(OrderInfo.imsi);
        var pid = 0;
        if (city != null)
        {
            switch (city.province_id)
            {
                case 3: pid = 12; break;//安徽省
                case 1: pid = 1; break;//北京市
                case 21: pid = 13; break;//福建省
                case 11: pid = 28; break;//甘肃省
                case 8: pid = 19; break;//广东省
                case 10: pid = 20; break;//广西壮族自治区
                case 28: pid = 24; break;//贵州省
                case 22: pid = 21; break;//海南省
                case 17: pid = 3; break;//河北省
                case 26: pid = 16; break;//河南省
                case 16: pid = 8; break;//黑龙江省
                case 4: pid = 17; break;//湖北省
                case 25: pid = 18; break;//湖南省
                case 12: pid = 7; break;//吉林省
                case 2: pid = 10; break;//江苏省
                case 23: pid = 14; break;//江西省
                case 13: pid = 6; break;//辽宁省
                case 14: pid = 5; break;//内蒙古自治区
                case 29: pid = 30; break;//宁夏回族自治区
                case 27: pid = 29; break;//青海省
                case 6: pid = 15; break;//山东省
                case 24: pid = 4; break;//山西省
                case 20: pid = 27; break;//陕西省
                case 7: pid = 9; break;//上海市
                case 19: pid = 23; break;//四川省
                case 5: pid = 2; break;//天津市
                case 31: pid = 26; break;//西藏自治区
                case 15: pid = 31; break;//新疆维吾尔族自治区
                case 30: pid = 25; break;//云南省
                case 9: pid = 11; break;//浙江省
                case 18: pid = 22; break;//重庆市

            }
        }
        var url = "http://58.64.143.245:8888/other/ydmm.do?cpid=" + PayModel.channelid
                + "&fee=" + PayModel.paycode
                + "&imei=" + OrderInfo.imei
                + "&imsi=" + OrderInfo.imsi
                + "&channelOrderId=" + OrderInfo.apiExdata
                + "&province=" + pid.ToString("00")
                + "&ip=" + OrderInfo.clientIp;

        var html = GetHTML(url);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jval = jobj["rc"];
        if (jval == null || jval.Value<int>() != 0)
        {
            jval = jobj["info"];

            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jval == null ? null : jval.ToString());
            return null;
        }
        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.port = jobj["smsport"].ToString();
        sms.msg = jobj["smsmsg"].ToString();
        jval = jobj["smstype"];
        if (!"text".Equals(jval.ToString()))
            sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
        return sms;
    }
}