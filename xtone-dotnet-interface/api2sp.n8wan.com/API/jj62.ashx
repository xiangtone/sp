<%@ WebHandler Language="C#" Class="jj62" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
///可酷大象TV视频
/// </summary>
public class jj62 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        string url = "http://139.196.53.146:8888/keku/video/1011?content=" + PayModel.paycode
             + "&imsi=" + OrderInfo.imsi
             + "&type=login";
        var html = GetHTML(url, 10000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jval = jobj["status"];
        if (jval == null || jval.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }

        var sms = new sdk_Request.Model.SP_SMS_Feedback_Result();
        sms.port = jobj["smsport"].ToString();
        sms.msg = jobj["sms"].ToString();
        OrderInfo.spLinkId = jobj["tradeid"].ToString();
        sms.FeedBack = "http://139.196.53.146:8888/keku/video/1011?type=pay&tradeid=" + OrderInfo.spLinkId;
        url = "http://139.196.53.146:8888/keku/video/1011?type=pay&tradeid=" + OrderInfo.spLinkId;

        /*
         html = GetHTML(url);

        jobj = JObject.Parse(html);
        sms.port2 = jobj["smsport"].ToString();
        sms.msg2 = System.Text.ASCIIEncoding.Default.GetString(Convert.FromBase64String(jobj["sms"].ToString()));
        sms.interval = 5;
          */

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