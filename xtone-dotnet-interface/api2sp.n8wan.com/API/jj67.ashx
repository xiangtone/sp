<%@ WebHandler Language="C#" Class="jj67" %>

using System;
using System.Web;

public class jj67 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var city = getCityByImsi(OrderInfo.imsi);
        if (city == null)
        {
            SetError(sdk_Request.Logical.API_ERROR.AREA_CLOSE);
            return null;
        }
        string pro = string.Empty;
        switch (city.province_id)
        {
            case 3: pro = "安徽"; break;
            case 1: pro = "北京"; break;
            case 21: pro = "福建"; break;
            case 11: pro = "甘肃"; break;
            case 8: pro = "广东"; break;
            case 10: pro = "广西"; break;
            case 28: pro = "贵州"; break;
            case 22: pro = "海南"; break;
            case 17: pro = "河北"; break;
            case 26: pro = "河南"; break;
            case 16: pro = "黑龙江"; break;
            case 4: pro = "湖北"; break;
            case 25: pro = "湖南"; break;
            case 12: pro = "吉林"; break;
            case 2: pro = "江苏"; break;
            case 23: pro = "江西"; break;
            case 13: pro = "辽宁"; break;
            case 14: pro = "内蒙"; break;
            case 29: pro = "宁夏"; break;
            case 27: pro = "青海"; break;
            case 6: pro = "山东"; break;
            case 24: pro = "山西"; break;
            case 20: pro = "陕西"; break;
            case 7: pro = "上海"; break;
            case 19: pro = "四川"; break;
            case 5: pro = "天津"; break;
            case 31: pro = "西藏"; break;
            case 15: pro = "新疆"; break;
            case 30: pro = "云南"; break;
            case 9: pro = "浙江"; break;
            case 18: pro = "重庆"; break;
        }
        OrderInfo.apiExdata = OrderInfo.id.ToString();
        var url = "http://df.quanmin-game.com/api/atbapi.php?ppid=" + PayModel.appid
            + "&imsi=" + OrderInfo.imsi
            + "&imei=" + OrderInfo.imei
            + "&prov=" + Server.UrlEncode(pro)
            + "&cpparam=" + OrderInfo.apiExdata
            + "&chid=" + PayModel.channelid
            + "&musicid=" + PayModel.paycode;

        var html = GetHTML(url);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var ar = html.Split(new string[] { "##" }, StringSplitOptions.None);
        var sms = new sdk_Request.Model.SP_SMS_Result();
        switch (ar[0])
        {
            case "text": break;
            case "data": sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data; break;
            case "513": SetError(sdk_Request.Logical.API_ERROR.AREA_CLOSE); return null;
            default: SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, ar[1]); return null;
        }
        sms.port = ar[1];
        sms.msg = sms.SMSType == sdk_Request.Logical.E_SMS_TYPE.Data ? ar[2] : System.Text.ASCIIEncoding.Default.GetString(Convert.FromBase64String(ar[2]));

        return sms;




    }
}