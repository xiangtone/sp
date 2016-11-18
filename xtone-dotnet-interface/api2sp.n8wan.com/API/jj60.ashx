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
        //var vPhone = n8wan.Public.Library.GetPhoneByImsi(OrderInfo.imsi);
        //var pro = dBase.ExecuteScalar("select name from tbl_province where id in(select province_id from tbl_city  where id in( select city_id from  tbl_phone_locate where  phone=" + vPhone + "))");

        var city = getCityByImsi(OrderInfo.imsi);


        if (city == null)
        {
            SetError(sdk_Request.Logical.API_ERROR.INNER_CONFIG_ERROR, "未知省份");
            return null;
        }
        int pid;
        switch (city.province_id)
        {
            case 3: pid = 9; break;
            case 1: pid = 4; break;
            case 21: pid = 24; break;
            case 11: pid = 23; break;
            case 8: pid = 12; break;
            case 10: pid = 13; break;
            case 28: pid = 26; break;
            case 22: pid = 20; break;
            case 17: pid = 17; break;
            case 26: pid = 18; break;
            case 16: pid = 31; break;
            case 4: pid = 21; break;
            case 25: pid = 22; break;
            case 12: pid = 5; break;
            case 2: pid = 15; break;
            case 23: pid = 16; break;
            case 13: pid = 27; break;
            case 14: pid = 3; break;
            case 29: pid = 8; break;
            case 27: pid = 30; break;
            case 6: pid = 10; break;
            case 24: pid = 11; break;
            case 20: pid = 29; break;
            case 7: pid = 1; break;
            case 19: pid = 6; break;
            case 5: pid = 7; break;
            case 31: pid = 25; break;
            case 15: pid = 14; break;
            case 30: pid = 2; break;
            case 9: pid = 19; break;
            case 18: pid = 28; break;
            default:
                SetError(sdk_Request.Logical.API_ERROR.INNER_CONFIG_ERROR, "省份错误");
                return null;
        }
        string url = "http://120.55.84.18:8888/channel/MutualWithUnite?imei=" + OrderInfo.imei
            + "&imsi=" + OrderInfo.imsi + "&phone=" + OrderInfo.mobile + "&operator=CMCC" + "&pro=" + pid.ToString()
            + "&ip=" + OrderInfo.clientIp + "&cpid=" + PayModel.paycode + "&ditch=" + "&info1="
            + "&fee=" + PayModel.appid + "&appName=XY" + "&payCode=123" + "&gameNo=1";
        var html = GetHTML(url, 15000, null);
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
        OrderInfo.spLinkId = jorderId.Value<string>();
        if (jnetWorkingType != null && jnetWorkingType.Value<int>() != 2)
        {
            var sms = new sdk_Request.Model.SP_SMS_Result();
            sms.port = jobj["port"].Value<string>();
            sms.msg = jobj["command"].Value<string>();
            return sms;
        }
        var smsd = new sdk_Request.Model.SP_SMS_Result();
        smsd.port = jobj["port"].Value<string>();
        smsd.msg = jobj["command"].Value<string>();
        smsd.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
        return smsd;


    }

}