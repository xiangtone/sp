<%@ WebHandler Language="C#" Class="jj74" %>

using System;
using System.Web;
/// <summary>
/// 咪咕包月
/// </summary>
public class jj74 : sdk_Request.Logical.APIRequestGet
{

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        var url = "http://182.92.204.31:8080/migu_12t/proc?action=sms_t2&confin=2&imsi=" + OrderInfo.imsi
                + "&imei=" + OrderInfo.imei
                + "&cpid=" + PayModel.channelid
                + "&serviceid=" + PayModel.paycode
                + "&mode=1&downloadtype=0";

        var html = GetHTML(url);
        var xml = new System.Xml.XmlDocument();
        xml.LoadXml(html);
        var el = xml.SelectSingleNode("/response/return_code");
        int retCode;
        int.TryParse(el.InnerText, out retCode);

        if (retCode != 0)
        {
            el = xml.SelectSingleNode("/response/return_desc");
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, el.InnerText);
            return null;
        }
        
        var sms = new sdk_Request.Model.SP_SMS_Result();
        el = xml.SelectSingleNode("/response/sms_add");

        sms.port = el.InnerText;
        el = xml.SelectSingleNode("/response/sms_msg");
        sms.msg = System.Text.ASCIIEncoding.Default.GetString(Convert.FromBase64String(el.InnerText));
        return sms;


    }
}