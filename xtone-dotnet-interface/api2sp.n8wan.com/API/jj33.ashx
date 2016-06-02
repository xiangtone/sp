<%@ WebHandler Language="C#" Class="jj33" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
///上海摩派-动漫
/// </summary>
public class jj33 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        string feeId = null;
        switch (PayModel.paycode)
        {
            case "1000": feeId = "2973"; break;
            default: SetError(sdk_Request.Logical.API_ERROR.ERROR_PAY_POINT); return null;
        }
        var vIP = OrderInfo.clientIp;
        var city = getCityByImsi(OrderInfo.imsi);
        if (city != null)
        {
            switch (city.province_id)
            {
                case 1: vIP = "47.153.128."; break;//北京
                case 3: vIP = "218.22.9."; break; //安徽
                case 12: vIP = "222.168.146."; break; //吉林
                case 14: vIP = "218.21.128."; break;//内蒙古
                case 28: vIP = "221.13.21."; break;//贵州
                case 30: vIP = "116.52.147."; break;//云南
                case 31: vIP = "220.182.50."; break;//西藏
                case 24: vIP = "211.142.2."; break;//山西
                case 2: vIP = "114.212.0."; break;//江苏
                case 4: vIP = "61.136.128."; break; //湖北
                case 5: vIP = "202.113.16."; break;//天津
                case 6: vIP = "202.102.136."; break; //山东
                case 7: vIP = "202.136.208."; break;  //上海
                case 8: vIP = "202.116.128.0"; break; //广东
                case 9: vIP = "202.91.224."; break; //浙江
                case 10: vIP = "202.103.232.0"; break;  //广西
                case 11: vIP = "202.100.72.0"; break; //甘肃
                case 13: vIP = "202.118.64."; break; //辽宁
                case 15: vIP = "103.22.116."; break; //新疆
                case 16: vIP = "103.29.128."; break; //黑龙江
                case 17: vIP = "202.99.128."; break; //河北
                case 18: vIP = "219.151.128."; break; //重庆
                case 19: vIP = "60.255.0."; break; //四川
                case 20: vIP = "202.100.32."; break; //陕西
                case 21: vIP = "218.185.240."; break; //福建
                case 22: vIP = "221.199.224."; break; //海南
                case 23: vIP = "202.101.224."; break;  //江西
                case 25: vIP = "202.103.64."; break; //湖南
                case 26: vIP = "202.102.240."; break;  //河南
                case 27: vIP = "202.100.144."; break; //青海
                case 29: vIP = "221.199.32."; break; //宁夏
                case 32: vIP = ""; break; //未知
            }
            if (vIP.EndsWith("."))
            {
                vIP += (int.Parse(OrderInfo.imsi.Substring(10)) % 255).ToString();
            }
        }

        string url = "http://pay.sdk.new.5isy.com/center/getCommand.ashx?partnerId=2031"
                + "&channelId=" + PayModel.channelid
                + "&appId=" + PayModel.appid
                + "&phone=" + OrderInfo.mobile
                + "&fee=" + PayModel.paycode
                + "&appFeeId=" + feeId
                + "&imei=" + OrderInfo.imei
                + "&imsi=" + OrderInfo.imsi
                + "&os_info=" + (string.IsNullOrEmpty(OrderInfo.sdkVersion) ? "4.1" : OrderInfo.sdkVersion)
                + "&os_model=XM3"
                + "&net_info=" + OrderInfo.netType
                + "&extra=DM1003"// + OrderInfo.apiExdata
                + "&timestamp=" + DateTime.Now.ToString("yyyyMMddHHmmss")
                + "&client_ip=" + vIP;



        var html = GetHTML(url, 10000, null);
        var jobj = JObject.Parse(html);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jVal = jobj["resultCode"];
        var code = jVal == null ? -1 : jVal.Value<int>();
        switch (code)
        {
            case 0: break;
            case 3000: SetError(sdk_Request.Logical.API_ERROR.AREA_CLOSE); return null;
            default: SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, "unkonw code:" + code.ToString()); return null;
        }

        //36735976DM10031000_DM10031000
        OrderInfo.spLinkId = string.Format("{0}DM1003{1}_DM1003{1}", jobj["orderid"].Value<string>(), PayModel.paycode);
        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.port = jobj["port"].Value<string>();
        sms.msg = jobj["cmd"].Value<string>();
        jVal = jobj["isbase64"];

        if (jVal != null && jVal.Value<int>() != 0)
            sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
        else
        {
            if (sms.msg.StartsWith("MDAwMD"))
                sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
        }
        return sms;

    }
}