<%@ WebHandler Language="C#" Class="jj61" %>

using System;
using System.Web;

/// <summary>
/// 次世代-音乐包月
/// </summary>
public class jj61 : sdk_Request.Logical.APIRequestGet
{

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        OrderInfo.apiExdata = string.Format("szhaot{0}", OrderInfo.id);
        var sid = "58d2e66845a89464e14cfb7cdcc0dee32be304ad";
        var chid = PayModel.channelid;
        var s = chid.Split(new char[] { ',' });

        if (s.Length > 1)
        {
            chid = s[0];
            sid = s[1];
        }


        var url = "http://115.159.74.129:8000/o/mgreqapi/" + sid + "/?"
                + "imei=" + OrderInfo.imei
                + "&imsi=" + OrderInfo.imsi
                + "&music_id=" + PayModel.appid
                + "&item_price=" + PayModel.paycode
                + "&channel_id=" + chid
                + "&cpparam=" + OrderInfo.apiExdata
                + "&music_type=2&sdcid=&start_time="
                + "&iccid=" + OrderInfo.iccid
                + "&ip=" + OrderInfo.clientIp
                + "&excode=" + OrderInfo.apiExdata;

        var html = GetHTML(url);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        var xml = new System.Xml.XmlDocument();
        xml.LoadXml(html);

        var node = xml.SelectSingleNode("/response/content_sid");
        if (node == null)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        OrderInfo.spLinkId = node.InnerText;

        var music = xml.SelectSingleNode("/response/music");
        var migu = xml.SelectSingleNode("/response/migu");
        sdk_Request.Model.SP_SMS_Result sms;
        if (music != null && migu != null)
        {
            sms = new sdk_Request.Model.SP_2SMS_Result()
            {
                port2 = migu.SelectSingleNode("sms_num").InnerText,
                msg2 = System.Text.ASCIIEncoding.Default.GetString(Convert.FromBase64String(migu.SelectSingleNode("sms").InnerText))
            };
        }
        else
        {
            sms = new sdk_Request.Model.SP_SMS_Result();
            if (music == null)
                music = migu;
        }

        sms.port = music.SelectSingleNode("sms_num").InnerText;
        sms.msg = System.Text.ASCIIEncoding.Default.GetString(Convert.FromBase64String(music.SelectSingleNode("sms").InnerText));

        return sms;

    }
}