<%@ WebHandler Language="C#" Class="jj92" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
///艾普维萨-国事视频
/// </summary>
public class jj92 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var userAgentv = getUAV(OrderInfo.imei);
        var userAgent = getUA(OrderInfo.imei);
        var url = "http://120.24.169.39:8080/miguvideo/getSms?CPParameter=" + PayModel.paycode +"_"+OrderInfo.id+ "&IMSI=" + OrderInfo.imsi + "&IMEI=" + OrderInfo.imei +"&ClientIP=" +OrderInfo.clientIp+
            "&ua="+userAgent+ "&video_ua="+userAgentv+"&iccid="+OrderInfo.cid+"&ib=0";
        var html = GetHTML(url, 15000, null);
        var jobj = JObject.Parse(html);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
             var jcode = jobj["ResultCode"];
             if (jcode != null && jcode.Value<int>() != 0)
             {
                 var err = "";
                 switch (jcode.ToString())
                 {

                     case "001": err = "透传参数错误"; break;
                     case "002": err = "系统未知错误"; break;
                     case "003": err = "网络错误"; break;
                     case "004": err = "业务暂停"; break;
                     case "201": err = "计费-接收参数有误"; break;
                     case "202": err = "计费-imsi 格式有误"; break;
                     case "219": err = "计费-imei 格式有误"; break;
                     case "204": err = "计费-渠道编号有误"; break;
                     case "205": err = "计费-道具编号有误"; break;
                     case "206": err = "计费-获取短信失败"; break;
                     case "207": err = "计费-单日代码总量达上限或道具未设 上限"; break;
                     default:
                         SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
                         return null;
                 }
                 SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, err);
                 return null;
             }
        var jmsg=jobj["MoSms"][1]["SmsCode"];
        if (jmsg != null)
        {
            var sms = new sdk_Request.Model.SP_2SMS_Result();
            sms.port = jobj["MoSms"][0]["Sm sNum"].Value<string>();
            sms.msg = jobj["MoSms"][0]["SmsCode"].Value<string>();
            sms.port2 = jobj["MoSms"][1]["Sm sNum"].Value<string>();
            sms.msg2 = jobj["MoSms"][1]["SmsCode"].Value<string>();
            return sms;
        
        }
        var sms1 = new sdk_Request.Model.SP_SMS_Result();
        sms1.port = jobj["MoSms"][0]["Sm sNum"].Value<string>(); 
        sms1.msg =  jobj["MoSms"][0]["SmsCode"].Value<string>();
        return sms1;
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
    string getUAV(string imei)
    {
        string[] uasv = new string[] {"Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1C28 Safari/419.3",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 2_0 like Mac OS X; ja-jp) AppleWebKit/525.18.1 (KHTML, like Gecko) Version/3.1.1 Mobile/5A347 Safari/52",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 2_0 like Mac OS X; ja-jp) AppleWebKit/525.18.1 (KHTML, like Gecko) Version/3.1.1 Mobile/5A345 Safari/525.20",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 2_0_1 like Mac OS X; ja-jp) AppleWebKit/525.18.1 (KHTML, like Gecko) Version/3.1.1 Mobile/5B108 Safari/525.20",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 2_1 like Mac OS X; ja-jp) AppleWebKit/525.18.1 (KHTML, like Gecko) Version/3.1.1 Mobile/5F136 Safari/525.20",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_1_3 like Mac OS X; ja-jp) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7E18 Safari/528.16",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_0_1 like Mac OS X; ja-jp) AppleWebKit/532.9 (KHTML, like Gecko) Version/4.0.5 Mobile/8A306 Safari/6531.22.7",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_0_2 like Mac OS X; ja-jp) AppleWebKit/532.9 (KHTML, like Gecko) Version/4.0.5 Mobile/8A400 Safari/6531.22.7",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_1 like Mac OS X; ja-jp) AppleWebKit/532.9 (KHTML, like Gecko) Version/4.0.5 Mobile/8B117 Safari/6531.22.7",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_2_1 like Mac OS X; ja-jp) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8C148 Safari/6533.18.5",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3 like Mac OS X; ja-jp) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8F190 Safari/6533.18.5",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_1 like Mac OS X; ja-jp) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8G4 Safari/6533.18.5",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_2 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8H7 Safari/6533.18.5",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_3 like Mac OS X; ja-jp) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_4 like Mac OS X; ja-jp) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8K2 Safari/6533.18.5",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_5 like Mac OS X; ja-jp) AppleWebKit/533.17.9 (KHTML, like Gecko) Mobile/8L1",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9A334 Safari/7534.48.3",
            "Mozilla/5.0 (iPhone; CPU iPhone OS 5_0_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9A405 Safari/7534.48.3",
            "Mozilla/5.0 (iPod; U; CPU like Mac OS X; en) AppleWebKit/420.1 (KHTML, like Gecko) Version/3.0 Mobile/3A100a Safari/419.3",
            "Mozilla/5.0 (iPod; U; CPU iPhone OS 2_1 like Mac OS X; ja-jp) AppleWebKit/525.18.1 (KHTML, like Gecko) Version/3.1.1 Mobile/5F137 Safari/525.20",
            "Mozilla/5.0 (iPod; U; CPU iPhone OS 4_1 like Mac OS X; ja-jp) AppleWebKit/532.9 (KHTML, like Gecko) Version/4.0.5 Mobile/8B118 Safari/6531.22.7",
            "Mozilla/5.0 (iPod; U; CPU iPhone OS 4_2_1 like Mac OS X; ja-jp) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8C148 Safari/6533.18.5",
            "Mozilla/5.0 (iPod; U; CPU iPhone OS 4_3_5 like Mac OS X; ja-jp) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8L1 Safari/6533.18.5",
            "Mozilla/5.0 (iPod; CPU iPhone OS 5_0_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A405 Safari/7534.48.3",
            "Mozilla/5.0 (Linux; U; Android 1.5; ja-jp; GDDJ-09 Build/CDB56) AppleWebKit/528.5+ (KHTML, like Gecko) Version/3.1.2 Mobile Safari/525.20.1",
            "Mozilla/5.0 (Linux; U; Android 1.6; ja-jp; IS01 Build/S3082) AppleWebKit/528.5+ (KHTML, like Gecko) Version/3.1.2 Mobile Safari/525.20.1",
            "Mozilla/5.0 (Linux; U; Android 1.6; ja-jp; IS01 Build/SA180) AppleWebKit/528.5+ (KHTML, like Gecko) Version/3.1.2 Mobile Safari/525.20.1",
            "Mozilla/5.0 (Linux; U; Android 1.6; ja-jp; Docomo HT-03A Build/DRD08) AppleWebKit/528.5+ (KHTML, like Gecko) Version/3.1.2 Mobile Safari/525.20.1",
            "Mozilla/5.0 (Linux; U; Android 1.6; ja-jp; SonyEricssonSO-01B Build/R1EA029) AppleWebKit/528.5+ (KHTML, like Gecko) Version/3.1.2 Mobile Safari/525.20.1",
            "Mozilla/5.0 (Linux; U; Android 1.6; ja-jp; generic Build/Donut) AppleWebKit/528.5+ (KHTML, like Gecko) Version/3.1.2 Mobile Safari/525.20.1",
            "Mozilla/5.0 (Linux; U; Android 2.1-update1; ja-jp; SonyEricssonSO-01B Build/2.0.2.B.0.29) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17",
            "Mozilla/5.0 (Linux; U; Android 2.2.1; ja-jp; Full Android Build/MASTER) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
            "Mozilla/5.0 (Linux; U; Android 2.2.1; ja-jp; IS03 Build/S9090) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
            "Mozilla/5.0 (Linux; U; Android 2.3.3; ja-jp; SC-02C Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
            "Mozilla/5.0 (Linux; U; Android 2.3.3; ja-jp; INFOBAR A01 Build/S9081) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
            "Mozilla/5.0 (Linux; U; Android 2.3.3; ja-jp; 001HT Build/GRI40) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
            "Mozilla/5.0 (Linux; U; Android 2.3.3; ja-jp; SonyEricssonX10i Build/3.0.1.G.0.75) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
            "Mozilla/5.0 (Linux; U; Android 2.3.4; ja-jp; SonyEricssonIS11S Build/4.0.1.B.0.112) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
            "Mozilla/5.0 (Linux; U; Android 2.3.4; ja-jp; IS05 Build/S9290) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
            "Mozilla/5.0 (Linux; U; Android 2.3.5; ja-jp; F-05D Build/F0001) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
            "Mozilla/5.0 (Linux; U; Android 2.3.5; ja-jp; T-01D Build/F0001) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
            "Mozilla/5.0 (Linux; U; Android 3.0.1; ja-jp; MZ604 Build/H.6.2-20) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13",
            "Mozilla/5.0 (Linux; U; Android 3.1; en-us; K1 Build/HMJ37) AppleWebKit/534.13(KHTML, like Gecko) Version/4.0 Safari/534.13",
            "Mozilla/5.0 (Linux; U; Android 3.1; ja-jp; AT100 Build/HMJ37) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13",
            "Mozilla/5.0 (Linux; U; Android 3.1; ja-jp; Sony Tablet S Build/THMAS10000) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13",
            "Mozilla/5.0 (Linux; U; Android 3.2; ja-jp; SC-01D Build/MASTER) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13",
            "Mozilla/5.0 (Linux; U; Android 3.2; ja-jp; AT1S0 Build/HTJ85B) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13",
            "Mozilla/5.0 (Linux; U; Android 3.2; ja-jp; F-01D Build/F0001) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13",
            "Mozilla/5.0 (Linux; U; Android 3.2; ja-jp; Sony Tablet S Build/THMAS11000) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13",
            "Mozilla/5.0 (Linux; U; Android 3.2; ja-jp; A01SH Build/HTJ85B) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Safari/533.1",
            "Mozilla/5.0 (Linux; U; Android 3.2.1; ja-jp; Transformer TF101 Build/HTK75) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13",
            "Mozilla/5.0 (Linux; U; Android 4.0.1; ja-jp; Galaxy Nexus Build/ITL41D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
            "Mozilla/5.0 (Linux; U; Android 4.1.1; ja-jp; Galaxy Nexus Build/JRO03H) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
            "Opera/9.80 (Android 2.3.3; Linux; Opera Mobi/ADR-1111101157; U; ja) Presto/2.9.201 Version/11.50",
            "Opera/9.80 (Android 3.2.1; Linux; Opera Tablet/ADR-1109081720; U; ja) Presto/2.8.149 Version/11.10",
            "Mozilla/5.0 (Android; Linux armv7l; rv:9.0) Gecko/20111216 Firefox/9.0 Fennec/9.0",
            "BlackBerry9000/4.6.0.294 Profile/MIDP-2.0 Configuration/CLDC-1.1 VendorID/220",
            "BlackBerry9300/5.0.0.1007 Profile/MIDP-2.1 Configuration/CLDC-1.1 VendorID/220",
            "BlackBerry9700/5.0.0.1014 Profile/MIDP-2.1 Configuration/CLDC-1.1 VendorID/220",
            "Mozilla/5.0 (BlackBerry; U; BlackBerry 9700; ja) AppleWebKit/534.8+ (KHTML, like Gecko) Version/6.0.0.570 Mobile Safari/534.8+",
            "Mozilla/5.0 (BlackBerry; U; BlackBerry 9780; ja) AppleWebKit/534.8+ (KHTML, like Gecko) Version/6.0.0.587 Mobile Safari/534.8+",
            "Mozilla/5.0 (BlackBerry; U; BlackBerry 9900; ja) AppleWebKit/534.11+ (KHTML, like Gecko) Version/7.1.0.74 Mobile Safari/534.11+",
            "Opera/9.80 (BlackBerry; Opera Mini/6.1.25376/26.958; U; en) Presto/2.8.119 Version/10.54",
            "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.2.149.27 Safari/525.13",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.19 (KHTML, like Gecko) Chrome/1.0.154.48 Safari/525.19",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/530.5 (KHTML, like Gecko) Chrome/2.0.172.33 Safari/530.5",
            "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US) AppleWebKit/532.0 (KHTML, like Gecko) Chrome/3.0.195.38 Safari/532.0",
            "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_3; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.55 Safari/533.4",
            "Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.63 Safari/534.3",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.55 Safari/534.3",
            "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.7 (KHTML, like Gecko) Chrome/7.0.517.43 Safari/534.7",
            "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US) AppleWebKit/534.10 (KHTML, like Gecko) Chrome/8.0.552.224 Safari/534.10 ChromePlus/1.5.2.0",
            "Mozilla/5.0 (en-us) AppleWebKit/534.14 (KHTML, like Gecko; Google Wireless Transcoder) Chrome/9.0.597 Safari/534.14",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.151 Safari/534.16",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.24 (KHTML, like Gecko) Chrome/11.0.696.71 Safari/534.24",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.24 (KHTML, like Gecko) Iron/11.0.700.2 Chrome/11.0.700.2 Safari/534.24",
            "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/534.24 (KHTML, like Gecko) Chrome/11.0.696.65 Safari/534.24",
            "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30 ChromePlus/1.6.3.1",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.112 Safari/534.30",
            "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.107 Safari/535.1",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.1 (KHTML, like Gecko) RockMelt/0.9.64.361 Chrome/13.0.782.218 Safari/535.1",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.220 Safari/535.1",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.202 Safari/535.1" };
        var m = System.Text.RegularExpressions.Regex.Match(imei, @"\d{,6}");
        int i = 0;
        if (m.Success)
        {
            i = int.Parse(m.Value);
        }

        return uasv[i % uasv.Length];
    }
   
}