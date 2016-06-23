<%@ WebHandler Language="C#" Class="jj60" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
/// 瑶品-mm
/// </summary>
public class jj60 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var vPhone = n8wan.Public.Library.GetPhoneByImsi(OrderInfo.imsi);
        var pro = dBase.ExecuteScalar("select name from tbl_province where id in(select province_id from tbl_city  where id in( select city_id from  tbl_phone_locate where  phone=" + vPhone + "))");
        if (pro == null) SetError(sdk_Request.Logical.API_ERROR.INNER_CONFIG_ERROR, "省份错误,PayCode:" + pro);
        switch (pro.ToString())
        {
            case "安徽": pro = "9"; break;
            case "北京": pro = "4"; break;
            case "福建": pro = "24"; break;
            case "甘肃": pro = "23"; break;
            case "广东": pro = "12"; break;
            case "广西": pro = "13"; break;
            case "贵州": pro = "26"; break;
            case "海南": pro = "20"; break;
            case "河北": pro = "17"; break;
            case "河南": pro = "18"; break;
            case "黑龙江": pro = "31"; break;
            case "湖北": pro = "21"; break;
            case "湖南": pro = "22"; break;
            case "吉林": pro = "5"; break;
            case "江苏": pro = "15"; break;
            case "江西": pro = "16"; break;
            case "辽宁": pro = "27"; break;
            case "内蒙古": pro = "3"; break;
            case "宁夏": pro = "8"; break;
            case "青海": pro = "30"; break;
            case "山东": pro = "10"; break;
            case "山西": pro = "11"; break;
            case "陕西": pro = "29"; break;
            case "上海": pro = "1"; break;
            case "四川": pro = "6"; break;
            case "天津": pro = "7"; break;
            case "西藏": pro = "25"; break;
            case "新疆": pro = "14"; break;
            case "云南": pro = "2"; break;
            case "浙江": pro = "19"; break;
            case "重庆": pro = "28"; break;
            default:
                SetError(sdk_Request.Logical.API_ERROR.INNER_CONFIG_ERROR, "省份错误");
                return null;
        }
        string url = "http://120.55.84.18:8888/channel/MutualWithUnite?imei=" + OrderInfo.imei + "&imsi=" + OrderInfo.imsi + "&phone=" + OrderInfo.mobile + "&operator=CMCC" + "&pro=" + pro +
            "&ip=" + OrderInfo.clientIp + "&cpid=" + PayModel.paycode + "&fee=" + PayModel.appid + "&appName=XY" + "&payCode=123" + "&gameNo=1";
        var html = GetHTML(url, 10000, null);
        var jobj = JObject.Parse(html);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jval = jobj["result"];
        var jorderId = jobj["orderId"];
        var jnetWorkingType = jobj["netWorkingType"];
        if (jval != null && jval.Value<int>() != 0)
        {
            var describe = "";
            switch (jval.Value<int>())
            {
                case -1: describe = "非法请求、渠道被禁用"; break;
                case -2: describe = "渠道的请求金额到上限"; break;
                case -3: describe = "无匹配的计费指令"; break;
                case -4: describe = "到用户上限"; break;
                case -5: describe = "请求的必填参数不正确"; break;
                case -6: describe = "无该运营商的api使用权限"; break;
                case -7: describe = "请求异常（ip、imsi等参数非法）"; break;
                case -10: describe = "服务器返回异常"; break;
            }
            WriteLog(describe);
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, describe);
            return null;
        }
        if(jnetWorkingType != null && jnetWorkingType.Value<int>() != 1)
        {
            OrderInfo.spExField = jorderId.Value<string>(); ;
            var sms = new sdk_Request.Model.SP_SMS_Result();
            sms.port = jobj["port"].Value<string>();
            sms.msg = jobj["command"].Value<string>();
            return sms;
        }
        OrderInfo.spExField = jorderId.Value<string>(); ;
        var smsd = new sdk_Request.Model.SP_SMS_Result();
        smsd.port = jobj["port"].Value<string>();
        smsd.msg = jobj["command"].Value<string>();
        smsd.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
        return smsd;
      

    }

}