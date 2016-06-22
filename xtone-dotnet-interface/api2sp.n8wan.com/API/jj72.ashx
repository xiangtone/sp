<%@ WebHandler Language="C#" Class="jj72" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;


public class jj72 : sdk_Request.Logical.APIRequestGet
{

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        OrderInfo.apiExdata = string.Format("{0}{1}", PayModel.appid, OrderInfo.id);
        int pid = 0;
        var city = getCityByImsi(OrderInfo.imsi);
        if (city == null)
        {
            SetError(sdk_Request.Logical.API_ERROR.AREA_CLOSE);
            return null;
        }
        switch (city.province_id)
        {
            case 3: pid = 9; break;//安徽省
            case 1: pid = 4; break;//北京市
            case 21: pid = 24; break;//福建省
            case 11: pid = 23; break;//甘肃省
            case 8: pid = 12; break;//广东省
            case 10: pid = 13; break;//广西
            case 28: pid = 26; break;//贵州省
            case 22: pid = 20; break;//海南省
            case 17: pid = 17; break;//河北省
            case 26: pid = 18; break;//河南省
            case 16: pid = 31; break;//黑龙江
            case 4: pid = 21; break;//湖北省
            case 25: pid = 22; break;//湖南省
            case 12: pid = 5; break;//吉林省
            case 2: pid = 15; break;//江苏省
            case 23: pid = 16; break;//江西省
            case 13: pid = 27; break;//辽宁省
            case 14: pid = 3; break;//内蒙古
            case 29: pid = 8; break;//宁夏
            case 27: pid = 30; break;//青海省
            case 6: pid = 10; break;//山东省
            case 24: pid = 11; break;//山西省
            case 20: pid = 29; break;//陕西省
            case 7: pid = 1; break;//上海市
            case 19: pid = 6; break;//四川省
            case 5: pid = 7; break;//天津市
            case 31: pid = 25; break;//西藏
            case 15: pid = 14; break;//新疆
            case 30: pid = 2; break;//云南省
            case 9: pid = 19; break;//浙江省
            case 18: pid = 28; break;//重庆市

        }


        var url = "http://114.80.201.9:8089/xh181_sdk0612.php?imei=" + OrderInfo.imei
            + "&imsi=" + OrderInfo.imsi
            + "&operator=CMCC&pro=" + pid.ToString()
            + "&ip=" + OrderInfo.clientIp
            + "&appName=" + Server.UrlEncode("浩天游戏")
            + "&payCode=" + Server.UrlEncode("游戏道具")
            + "&channel=" + PayModel.channelid
            + "&param=" + OrderInfo.apiExdata;

        var html = GetHTML(url, 10 * 1000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jval = jobj["result"];
        if (jval == null || jval.Value<int>() != 0)
        {
            var code = jval == null ? 1 : jval.Value<int>();
            String msg = null;
            switch (code)
            {
                case -8: msg = "漫游IP"; break;
                case -4: msg = "取指令频率过高"; break;
                case -7: msg = "参数错误"; break;

            }


            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, msg);
            return null;
        }

        jval = jobj["orderId"];
        OrderInfo.spLinkId = jval.ToString();

        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.port = jobj["port"].ToString();
        sms.msg = jobj["command"].ToString();
        jval = jobj["netWorkingType"];
        if (jval != null && jval.Value<int>() == 2)
            sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
        return sms;

    }

}