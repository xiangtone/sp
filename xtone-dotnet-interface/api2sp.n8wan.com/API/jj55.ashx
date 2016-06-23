<%@ WebHandler Language="C#" Class="jj55" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
/// 榕元-MM
/// </summary>
public class jj55 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var userAgent = OrderInfo.extraParams;
        if (string.IsNullOrEmpty(userAgent))
            userAgent = getUA(OrderInfo.imei);
        var usergetos = getos(OrderInfo.imei);
        string url = "http://120.27.146.14/excalibur/server/" + PayModel.channelid + "/lqmm/complete/pay.aspx?fee=" + PayModel.appid + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei +
            "&userdata="+PayModel.paycode+OrderInfo.apiExdata+"&ip="+OrderInfo.clientIp+"&os=" + usergetos + "&model=" + HttpUtility.UrlEncode(userAgent);
        var html = GetHTML(url, 10000, null);
        var jobj = JObject.Parse(html);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jval = jobj["status"];
        if (jval != null && jval.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        var jnum = jobj["num"];
        var jtype = jobj["type"];
        OrderInfo.spExField = PayModel.paycode + OrderInfo.apiExdata;
        if (jnum != null && jnum.Value<int>() != 1)
        {
            if (jnum != null && jnum.Value<int>() != 2)
            {
                var sms = new sdk_Request.Model.SP_2SMS_Result();
                sms.port = jobj["initSpnumber"].Value<string>();
                sms.msg = jobj["initCmd"].Value<string>();
                sms.port2 = jobj["spnumber"].Value<string>();
                sms.msg2 = jobj["cmd"].Value<string>();
                sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
                return sms;
            }
            var sms1 = new sdk_Request.Model.SP_2SMS_Result();
            sms1.port = jobj["initSpnumber"].Value<string>();
            sms1.msg = jobj["initCmd"].Value<string>();
            sms1.port2 = jobj["spnumber"].Value<string>();
            sms1.msg2 = jobj["cmd"].Value<string>();
            return sms1;
        }
        if (jnum != null && jnum.Value<int>() != 2)
        {
            var sms2 = new sdk_Request.Model.SP_SMS_Result();
            sms2.port = jobj["spnumber"].Value<string>();
            sms2.msg = jobj["cmd"].Value<string>();
            sms2.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
            return sms2;
        }
        var sms3 = new sdk_Request.Model.SP_SMS_Result();
        sms3.port = jobj["spnumber"].Value<string>();
        sms3.msg = jobj["cmd"].Value<string>();
        return sms3;
        
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