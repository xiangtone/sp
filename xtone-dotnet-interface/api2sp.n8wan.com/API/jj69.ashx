<%@ WebHandler Language="C#" Class="jj69" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
public class jj69 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var userAgent = OrderInfo.extraParams;
        if (string.IsNullOrEmpty(userAgent))
            userAgent = getUA(OrderInfo.imei);
        var usergetos = getos(OrderInfo.imei);
        var url = "http://pay.sdk.new.5isy.com/center/getCommand.ashx?partnerId=2031"
                + "&appId=2127"
                + "&channelId=3188"
                + "&appFeeId=" + PayModel.paycode
                + "&fee=" + PayModel.appid
                + "&imsi=" + OrderInfo.imsi
                + "&imei=" + OrderInfo.imei
                + "&os_info=" + usergetos
                + "&os_model=" + HttpUtility.UrlEncode(userAgent)
                + "&net_info=WIFI"
                + "&extra=" + PayModel.channelid
                + "&timestamp=" + DateTime.Now.ToString("yyyyMMddHHmmss")
                + "&client_ip="+OrderInfo.clientIp;

        var html = GetHTML(url, 1500, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jObj = JObject.Parse(html);
        var jresultCode = jObj["resultCode"];
        if (jresultCode != null && jresultCode.Value<int>() != 000)
        {
            var describe = "";
            switch (jresultCode.Value<int>())
            {
                case 0000: describe = "成功获取指令"; break;
                case 1001: describe = "应用ID为空"; break;
                case 1002: describe = "渠道ID为空"; break;
                case 1004: describe = "计费点ID为空"; break;
                case 1005: describe = "资费为空"; break;
                case 1006: describe = "黑名单"; break;
                case 1007: describe = "省份主动屏蔽"; break;
                case 1008: describe = "黑名单"; break;
                case 1009: describe = "请求间隔过短"; break;
                case 3000: describe = "没有可用指令"; break;
            }
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, describe);
            return null;
        }

        var jtype = jObj["type"];
        var jinit_sms = jObj["init_sms"];
        var jsmstype = jObj["smstype"];
        if (jtype != null && jtype.Value<int>() == 1)
        {
            if (jinit_sms != null && !string.IsNullOrEmpty(jinit_sms.Value<string>()))
            {
                var sms = new sdk_Request.Model.SP_2SMS_Result();
                sms.port = jObj["init_sms_number"].ToString();
                sms.msg = jObj["init_sms"].ToString();
                if (jsmstype != null && jsmstype.Value<int>() == 1)
                {
                    sms.port2 = jObj["port"].ToString();
                    sms.msg2 = jObj["cmd"].ToString();
                    sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
                    return sms;
                }
                if (jsmstype != null && jsmstype.Value<int>() == 0)
                {
                    sms.port2 = jObj["port"].ToString();
                    sms.msg2 = System.Text.ASCIIEncoding.Default.GetString(Convert.FromBase64String(jObj["cmd"].ToString()));

                    return sms;
                }
            }
            else
            {
                var sms = new sdk_Request.Model.SP_SMS_Result();
                sms.port = jObj["port"].ToString();
                sms.msg = jObj["cmd"].ToString();
                return sms;
            }

        }
        SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
        return null;
    }
    string getUA(string imei)
    {
        string[] uas = new string[] {
            "GDDJ-09_CDB56","IS01_S3082","IS01_SA180","Docomo_HT-03A_DRD08","SonyEricssonSO-01B_R1EA029","generic_Donut",
            "SonyEricssonSO-01B_2.0.2.B.0.29","IS03_S9090","SC-02C_GINGERBREAD","INFOBAR_A01_S9081","001HT_GRI40",
            "SonyEricssonX10i_3.0.1.G.0.75","SonyEricssonIS11S_4.0.1.B.0.112","IS05_S9290","F-05D_F0001","T-01D_F0001",
            "MZ604_H.6.2-20","K1_HMJ37","AT100_HMJ37","Sony_Tablet_S","SC-01D_MASTER","AT1S0_HTJ85B","F-01D_F0001",
            "Sony_Tablet_S","A01SH_HTJ85B","Transformer_TF101_HTK75","Galaxy_Nexus_ITL41D","Galaxy_Nexus_JRO03H"
        };
        var m = System.Text.RegularExpressions.Regex.Match(imei, @"\d{,6}");
        int i = 0;
        if (m.Success)
        {
            i = int.Parse(m.Value);
        }

        return uas[i % uas.Length];
    }
    string getos(string imei)
    {
        string[] uaos = new string[] {
            "4.1.2","4.1.2","4.1.3","4.2","4.3","4.4","4.4.1","4.4.2","4.4.3"
        };
        var m = System.Text.RegularExpressions.Regex.Match(imei, @"\d{,6}");
        int i = 0;
        if (m.Success)
        {
            i = int.Parse(m.Value);
        }

        return uaos[i % uaos.Length];
    }
}