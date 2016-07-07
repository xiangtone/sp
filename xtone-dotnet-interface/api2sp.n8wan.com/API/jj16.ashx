<%@ WebHandler Language="C#" Class="jj15" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
using System.Text.RegularExpressions;

/// <summary>
/// 上海可酷 --- 视频包月
/// </summary>
public class jj15 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {


        var city = getCityByImsi(OrderInfo.imsi);
        string py = "oth";
        if (city != null)
        {
            switch (city.province_id)
            {
                case 3: py = "ah"; break;//安徽
                case 1: py = "bj"; break;//北京
                case 21: py = "fj"; break;//福建
                case 11: py = "gs"; break;//甘肃
                case 8: py = "gd"; break;//广东
                case 10: py = "gx"; break;//广西
                case 28: py = "gz"; break;//贵州
                case 22: py = "hain"; break;//海南
                case 17: py = "heb"; break;//河北
                case 26: py = "hen"; break;//河南
                case 16: py = "hlj"; break;//黑龙江
                case 4: py = "hub"; break;//湖北
                case 25: py = "hn"; break;//湖南
                case 12: py = "jl"; break;//吉林
                case 2: py = "js"; break;//江苏
                case 23: py = "jx"; break;//江西
                case 13: py = "ln"; break;//辽宁
                case 14: py = "nmg"; break;//内蒙古
                case 29: py = "nx"; break;//宁夏
                case 27: py = "qh"; break;//青海
                case 6: py = "sd"; break;//山东
                case 24: py = "sx"; break;//山西
                case 20: py = "shxi"; break;//陕西
                case 7: py = "sh"; break;//上海
                case 19: py = "sc"; break;//四川
                case 5: py = "tj"; break;//天津
                case 32: py = "oth"; break;//未知
                case 31: py = "xz"; break;//西藏
                case 15: py = "xj"; break;//新疆
                case 30: py = "yn"; break;//云南
                case 9: py = "zj"; break;//浙江
                case 18: py = "cq"; break;//重庆
            }
        }




        var userAgent = OrderInfo.userAgent;
        if (string.IsNullOrEmpty(userAgent))
            userAgent = getUA(OrderInfo.imei);

        string url = "http://139.196.53.146:8888/keku/video/1001/mg?content=" + PayModel.paycode
                    + "&imei=" + OrderInfo.imei
                    + "&imsi=" + OrderInfo.imsi
                    + "&iccid=" + (String.IsNullOrEmpty(OrderInfo.iccid) ? "null" : OrderInfo.iccid)
                    + "&ua=" + HttpUtility.UrlEncode(userAgent)
                    + "&location=" + py;

        var html = GetHTML(url, 10000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = Newtonsoft.Json.Linq.JObject.Parse(html);
        var status = jobj["status"].ToString();
        if (status != "0")
        {
            var jVal = jobj["msg"];
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jVal == null ? null : jVal.ToString());
            return null;
        }
        OrderInfo.spLinkId = jobj["tradeid"].ToString();
        var sms = new sdk_Request.Model.SP_2SMS_Result();
        sms.port = jobj["port"].Value<string>();
        sms.msg = System.Text.ASCIIEncoding.UTF8.GetString(Convert.FromBase64String(jobj["sms"].Value<string>()));
        sms.port2 = jobj["port2"].Value<string>();
        sms.msg2 = System.Text.ASCIIEncoding.UTF8.GetString(Convert.FromBase64String(jobj["sms2"].Value<string>()));
        //sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
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
        var m = Regex.Match(imei, @"\d{1,6}");
        int i = 0;
        if (m.Success)
        {
            i = int.Parse(m.Value);
        }

        return uas[i % uas.Length];
    }
}