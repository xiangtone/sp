<%@ WebHandler Language="C#" Class="jj43" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
///麦广---动漫
/// </summary>
public class jj43 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var userAgent = OrderInfo.extraParams;
        if (string.IsNullOrEmpty(userAgent))
            userAgent = getUA(OrderInfo.imei);
        var usergetos = getos(OrderInfo.imei);
        string url = "http://115.28.254.133:8888/cmdmapi/Charge?"
             + "chid=" + PayModel.paycode
             + "&cpid=" + PayModel.appid
             + "&pid=" + PayModel.channelid
             + "&cpparam=" + OrderInfo.id
             + "&imsi=" + OrderInfo.imsi
             + "&imei=" + OrderInfo.imei
             + "&ua=" + HttpUtility.UrlEncode(userAgent)
             + "&os=" + usergetos
             + "&ip=" + OrderInfo.clientIp;
        var html = GetHTML(url, 10000, null);
        var jobj = JObject.Parse(html);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        //截取
        // var ars = html.Split(new string[] { "#" }, StringSplitOptions.RemoveEmptyEntries);
        //var sms = new sdk_Request.Model.SP_SMS_Result();
        //sms.port = ars[0];
        //sms.msg = ars[1];
        var jval = jobj["result"];
        if (jval != null && jval.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        var sms = new sdk_Request.Model.SP_SMS_Result();
        OrderInfo.apiExdata = jobj["orderid"].Value<string>();
        sms.port = jobj["smsport"].Value<string>();
        sms.msg = jobj["sms"].Value<string>();
        sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
        return sms;
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