<%@ WebHandler Language="C#" Class="jj28" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
///深圳榕元---CRI手机电视
/// </summary>
public class jj28 : sdk_Request.Logical.APIRequestGet
{
   
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var vPhone = n8wan.Public.Library.GetPhoneByImsi(OrderInfo.imsi);
        var pro = dBase.ExecuteScalar("select name from tbl_province where id in(select province_id from tbl_city  where id in( select city_id from  tbl_phone_locate where  phone=" + vPhone + "))");
        if (pro == null) SetError(sdk_Request.Logical.API_ERROR.INNER_CONFIG_ERROR, "省份错误,PayCode:" + pro);
        switch (pro.ToString())
        {
            case "安徽": pro = "12"; break;
            case "北京": pro = "01"; break;
            case "福建": pro = "14"; break;
            case "甘肃": pro = "27"; break;
            case "广东": pro = "19"; break;
            case "广西": pro = "20"; break;
            case "贵州": pro = "23"; break;
            case "海南": pro = "21"; break;
            case "河北": pro = "03"; break;
            case "黑龙江": pro = "08"; break;
            case "湖北": pro = "17"; break;
            case "湖南": pro = "18"; break;
            case "吉林": pro = "07"; break;
            case "江苏": pro = "10"; break;
            case "江西": pro = "14"; break;
            case "辽宁": pro = "06"; break;
            case "内蒙古": pro = "05"; break;
            case "宁夏": pro = "29"; break;
            case "青海": pro = "28"; break;
            case "山东": pro = "15"; break;
            case "山西": pro = "04"; break;
            case "陕西": pro = "26"; break;
            case "上海": pro = "09"; break;
            case "四川": pro = "22"; break;
            case "天津": pro = "02"; break;
            case "西藏": pro = "25"; break;
            case "新疆": pro = "30"; break;
            case "云南": pro = "24"; break;
            case "浙江": pro = "11"; break;
            case "重庆": pro = "31"; break;
            default:
                SetError(sdk_Request.Logical.API_ERROR.INNER_CONFIG_ERROR, "省份错误");
                return null;
        }
        string url = "http://video.yiqiao580.com:9800/crack/video/paysms.do?ptid=75100" + "&itemId=2002218100" + "&itemPrice=1000" + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei + "&cpparam="+PayModel.paycode+OrderInfo.apiExdata
            + "&noid=10151070" + "&ctid=611596901" + "&provid=" + pro + "&version=1.0.2";
        var html = GetHTML(url, 10000, null);
        var jobj = JObject.Parse(html);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jVal = jobj["result"];
        string pottf = jobj["port"].Value<string>();
        string smsf = jobj["sms"].Value<string>();
        string[] splitport= pottf.Split('&');

        string[] splitsms= smsf.Split('&');
        if (jVal != null && jVal.Value<int>() == 0)
            {
                var sms2 = new sdk_Request.Model.SP_2SMS_Result();
                sms2.port = splitport[0].ToString();
                sms2.msg = splitsms[0].ToString();
                sms2.port2 = splitport[1].ToString();
                sms2.msg2 = splitsms[1].ToString(); 
                OrderInfo.spLinkId = jobj["orderno"].Value<string>();
                sms2.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
                return sms2;
            }
        SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
        return null;
    }
}