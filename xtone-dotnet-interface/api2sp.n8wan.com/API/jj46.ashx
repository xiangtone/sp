<%@ WebHandler Language="C#" Class="jj46" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
/// 普乐
/// </summary>
public class jj46 : sdk_Request.Logical.APIRequestGet
{

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var vPhone = n8wan.Public.Library.GetPhoneByImsi(OrderInfo.imsi);
        var pro = dBase.ExecuteScalar("select name from tbl_province where id in(select province_id from tbl_city  where id in( select city_id from  tbl_phone_locate where  phone=" + vPhone + "))");
        if (pro == null) SetError(sdk_Request.Logical.API_ERROR.INNER_CONFIG_ERROR, "省份错误,PayCode:" + pro);
        switch (pro.ToString())
        {
            case "安徽": pro = "12"; break;
            case "北京": pro = "1"; break;
            case "福建": pro = "14"; break;
            case "甘肃": pro = "27"; break;
            case "广东": pro = "19"; break;
            case "广西": pro = "20"; break;
            case "贵州": pro = "24"; break;
            case "海南": pro = "21"; break;
            case "河北": pro = "3"; break;
            case "黑龙江": pro = "8"; break;
            case "湖北": pro = "17"; break;
            case "湖南": pro = "18"; break;
            case "吉林": pro = "7"; break;
            case "江苏": pro = "10"; break;
            case "江西": pro = "14"; break;
            case "辽宁": pro = "6"; break;
            case "内蒙古": pro = "5"; break;
            case "宁夏": pro = "30"; break;
            case "青海": pro = "29"; break;
            case "山东": pro = "15"; break;
            case "山西": pro = "4"; break;
            case "陕西": pro = "28"; break;
            case "上海": pro = "9"; break;
            case "四川": pro = "23"; break;
            case "天津": pro = "2"; break;
            case "西藏": pro = "26"; break;
            case "新疆": pro = "31"; break;
            case "云南": pro = "25"; break;
            case "浙江": pro = "11"; break;
            case "重庆": pro = "22"; break;
            case "香港": pro = "32"; break;
            case "澳门": pro = "33"; break;
            case "台湾": pro = "34"; break;
            default:
                SetError(sdk_Request.Logical.API_ERROR.INNER_CONFIG_ERROR, "省份错误");
                return null;
        }
        var url = "http://www.wosdk.cn/wosdk/wotaojinpay?channelId=" + PayModel.channelid + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei + "&province=" + pro + "&fee=" + PayModel.appid + "&mac=" + OrderInfo.extrData;
        var html = GetHTML(url);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = Newtonsoft.Json.Linq.JObject.Parse(html);
        var jrc = jobj["rc"];
        if (jrc == null && jrc.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        var sms = new sdk_Request.Model.SP_SMS_Feedback_Result();
        sms.msg = Convert.ToBase64String(System.Text.ASCIIEncoding.UTF8.GetBytes(jobj["sms"].Value<string>()));
        sms.port = jobj["num"].Value<string>();
        sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
        OrderInfo.spExField = jobj["tradeid"].Value<string>();
        sms.FeedBack = "http://www.wosdk.cn/wosdk/wotaojinpay2?tradeid=" + OrderInfo.spExField;

        return sms;

    }


}