<%@ WebHandler Language="C#" Class="jj37" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
///鼎兆运---MM
/// </summary>
public class jj37 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var vIP = OrderInfo.clientIp;
        var city = getCityByImsi(OrderInfo.imsi);
        if (city != null)
        {
            switch (city.province_id)
            {
                case 1: vIP = "47.153.128."; break;//北京
                case 3: vIP = "218.22.9."; break; //安徽
                case 12: vIP = "222.168.146."; break; //吉林
                case 14: vIP = "218.21.128."; break;//内蒙古
                case 28: vIP = "221.13.21."; break;//贵州
                case 30: vIP = "116.52.147."; break;//云南
                case 31: vIP = "220.182.50."; break;//西藏
                case 24: vIP = "211.142.2."; break;//山西
                case 2: vIP = "58.67.180."; break; //江苏
                case 4: vIP = "202.103.9."; break;//湖北
                case 5: vIP = "221.239.19."; break;//天津
                case 6: vIP = "110.194.73."; break; //山东
                case 7: vIP = "58.246.76."; break;//上海
                case 8: vIP = "211.162.62."; break;//广东
                case 9: vIP = "124.160.75."; break;//浙江
                case 10: vIP = "219.159.235."; break; //广西
                case 11: vIP = "61.178.128."; break; //甘肃
                case 13: vIP = "218.25.21."; break; //辽宁
                case 15: vIP = "61.128.101."; break; //新疆
                case 16: vIP = "221.208.247."; break; //黑龙江
                case 17: vIP = "218.12.41."; break; //河北
                case 18: vIP = "222.182.90."; break; //重庆
                case 19: vIP = "218.88.220."; break; //四川
                case 20: vIP = "59.48.8."; break;//陕西
                case 21: vIP = "218.66.59."; break; //福建
                case 22: vIP = "58.66.194."; break;//海南
                case 23: vIP = "117.136.21."; break; //江西
                case 25: vIP = "220.168.56."; break; //湖南
                case 26: vIP = "218.28.191."; break;//河南
                case 27: vIP = "61.133.235."; break;//青海
                case 29: vIP = "222.75.147."; break;//宁夏
                case 32: //未知
                    break;

            }
            if (vIP.EndsWith("."))
            {
                vIP += (int.Parse(OrderInfo.imsi.Substring(10)) % 255).ToString();
            }
        }
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

        string url = "http://118.186.245.157/hy_work/interface/init.php?type=618" + "&siteid=" + PayModel.appid + "&codeid=" + PayModel.channelid + "&serial=" +OrderInfo.extrData +OrderInfo.id+
            "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei + "&ip=" + vIP+ "&province=" + pro;
        var html = GetHTML(url, 10000, null);
        if (html == null)
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = Newtonsoft.Json.Linq.JObject.Parse(html);
        var jVal = jobj["hRet"];
        if (jVal!=null && jVal.ToString() == "0")
        {
            var sms = new sdk_Request.Model.SP_SMS_Result();
            sms.port = jobj["Login"]["num"].Value<string>();
            sms.msg = jobj["Login"]["sms"].Value<string>();
            sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
            return sms;
        }
        SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
        return null;
        
    }
}