<%@ WebHandler Language="C#" Class="jj90" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
///维新-沃商店0630
/// </summary>
public class jj90 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        var url = "http://ivas.iizhifu.com/init.php?type=" + PayModel.paycode + "&siteid=" + PayModel.appid + "&codeid=" + PayModel.channelid + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei
            + "&ib=0" + "&ip=" + OrderInfo.clientIp + "&ct=7c:1d:d9:dd:af:99" + "&serial=SKS"+"00"+ OrderInfo.id;
        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jcode = jobj["hRet"];
        if (jcode != null && jcode.Value<int>() != 0)
        {
            var err = "";
            switch (jcode.ToString())
            {

                case "0": err = "确认计费成功"; break;
                case "101": err = "接收参数有误"; break;
                case "102": err = "imsi格式有误"; break;
                case "103": err = "游戏编号有误"; break;
                case "104": err = "计费点有误"; break;
                case "105": err = "道具编号有误"; break;
                case "119": err = "imei格式有误"; break;
                case "206": err = "计费请求失败"; break;
                case "207": err = "单日代码总量达上限或道具未设上限"; break;
                case "120": err = "ip或mac地址有误"; break;
                default:
                    SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
                    return null;
            }
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL,err);
            return null;
        }
        OrderInfo.spLinkId = "SKS" + "00" + OrderInfo.id + OrderInfo.id;
        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.port = jobj["Login"]["num"].Value<string>();
        sms.msg = jobj["Login"]["sms"].Value<string>();
        return sms;
    }
   
}