<%@ WebHandler Language="C#" Class="jj98" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
/// 明天动力（北京）-视频
/// </summary>
public class jj98 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var url = "http://www.mpay-agent.com:10800/mtdl_api_code/CommandApiAction?imsi_params=" + OrderInfo.imsi + "&imei_params=" + OrderInfo.imei + "&channelNum=" + PayModel.channelid + "&appID=" + PayModel.paycode +
            "&price_params=" + PayModel.appid + "&cpParams=" + OrderInfo.id + "&provider=YD" + "&flag=1000";

        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jresult = jobj["result"];
        var jerrorCode = jobj["errorCode"];
        if (jresult != null && jresult.Value<int>() !=0)
        {
           var err = "";
           switch (jerrorCode.ToString())
            {
                case "1004": err = "渠道号错误"; break;
                case "1005": err = "价格错误"; break;
                case "1006": err = "无法通过所传入的ICCID、IMSI、IP、GPS地址等判断出省份,请检查请检查是否传入正确的ICCID、IMSI、IP、GPS等信息"; break;
                case "1013": err = "没有可以支持此请求金额和省份的代码"; break;
                case "1019": err = "运算异常"; break;
                case "1020": err = "没有分配合作伙伴申请的对应金额"; break;
                case "1021": err = "代码类型配置错误"; break;
                case "1022": err = "商户自定义扩展参数长度超过16位"; break;
                case "1023": err = "渠道表没有分配此参数"; break;
                case "1024": err = "运营商标识错误"; break;
                case "1025": err = "合作伙伴申请的金额和请求指令的金额不匹配"; break;
                default:
                    SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
                    return null;
            }
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, err);
            return null;
        }

        OrderInfo.spLinkId = jobj["orderId"].Value<string>();
        var jorder = jobj["order"];
        if (jorder.Value<int>() != 1)
        {
            var sms1 = new sdk_Request.Model.SP_2SMS_Result();
            sms1.port = jobj["port1"].Value<string>();
            sms1.msg = jobj["command1"].Value<string>();
            sms1.port2 = jobj["port2"].Value<string>();
            sms1.msg2 = jobj["command2"].Value<string>();
            return sms1;
        }
        var sms = new sdk_Request.Model.SP_2SMS_Result();
        sms.port = jobj["port1"].Value<string>();
        sms.msg = jobj["command1"].Value<string>();
        return sms;
    }
 
}