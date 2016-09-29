<%@ WebHandler Language="C#" Class="jj53" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
///榕元---派拉蒙视频
/// </summary>
public class jj53 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var vPhone = n8wan.Public.Library.GetPhoneByImsi(OrderInfo.imsi);
        var pro = dBase.ExecuteScalar("select name from tbl_province where id in(select province_id from tbl_city  where id in( select city_id from  tbl_phone_locate where  phone=" + vPhone + "))");
        if (pro == null) SetError(sdk_Request.Logical.API_ERROR.INNER_CONFIG_ERROR, "省份错误,PayCode:" + pro);
        switch (pro.ToString())
        {
            case "安徽": pro = "ah"; break;
            case "北京": pro = "bj"; break;
            case "福建": pro = "fj"; break;
            case "甘肃": pro = "gs"; break;
            case "广东": pro = "gd"; break;
            case "广西": pro = "gx"; break;
            case "贵州": pro = "gz"; break;
            case "海南": pro = "hain"; break;
            case "河北": pro = "heb"; break;
            case "黑龙江": pro = "hlj"; break;
            case "湖北": pro = "hub"; break;
            case "湖南": pro = "hn"; break;
            case "吉林": pro = "jl"; break;
            case "江苏": pro = "js"; break;
            case "江西": pro = "jx"; break;
            case "辽宁": pro = "ln"; break;
            case "内蒙古": pro = "nmg"; break;
            case "宁夏": pro = "nx"; break;
            case "青海": pro = "qh"; break;
            case "山东": pro = "sd"; break;
            case "山西": pro = "sx"; break;
            case "陕西": pro = "shxi"; break;
            case "上海": pro = "sh"; break;
            case "四川": pro = "sc"; break;
            case "天津": pro = "tj"; break;
            case "西藏": pro = "xz"; break;
            case "新疆": pro = "xj"; break;
            case "云南": pro = "yn"; break;
            case "浙江": pro = "zj"; break;
            case "重庆": pro = "cq"; break;
            case "其他": pro = "oth"; break;
            default:
                SetError(sdk_Request.Logical.API_ERROR.INNER_CONFIG_ERROR, "省份错误");
                return null;
        }
        string url = "http://bysms.5151pay.com/GetDYSms.ashx?ua=xiaomi3s" + "&param=" + PayModel.appid + "&bizkey=" + PayModel.channelid +
            "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei + "&location=" + pro;
        var html = GetHTML(url, 10000, null);
        var jobj = JObject.Parse(html);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        var jVal1 = jobj["status"];
        var jdata = jobj["data"];
        if (jVal1 != null && jVal1.Value<int>() == 200)
        {
            OrderInfo.spExField = jdata["orderid"].Value<string>();
            var sms = new sdk_Request.Model.SP_2SMS_Result();
            sms.port = jdata["msgto"].ToString();
            sms.msg = jdata["msgtxt"].ToString();
            sms.port2 = jdata["msgto2"].ToString();
            sms.msg2 = jdata["msgtxt2"].ToString();
            SetError(sdk_Request.Logical.API_ERROR.OK);
            return sms;
        }
        SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
        return null;
    }
}
