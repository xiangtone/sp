<%@ WebHandler Language="C#" Class="jj7" %>

using System;
using System.Web;
/// <summary>
/// 星光动漫 SP- 接入
/// </summary>
public class jj7 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        OrderInfo.apiExdata = "10005" + OrderInfo.id; //生成透传内容，并保存

        string url = "http://www.iquxun.cn:9012/channel/mogu/receive_mo.ashx?"
            + "imsi=" + OrderInfo.imsi //用户IMSI
            + "&imei=" + OrderInfo.imei //用户IMEI
            + "&cpparam=" + OrderInfo.apiExdata //生成的透传内容
            + "&linkId=" + PayModel.paycode  //SP通道配置的Paycode
            + "&chid=" + PayModel.channelid //SP通道配置的channelid
            + "&pid=" + PayModel.appid;//SP通道配置的appid
        String html = null;

        html = GetHTML(url);

        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        if (html.Contains("error"))
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, html);//把SP响应的错误内容全部传给渠道
            return null;
        }
        var ars = html.Split(new string[] { "####" }, 2, StringSplitOptions.None);
        if (ars.Length != 2)
        {//可能回传的HTML里没有包括 ###
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.port = ars[0]; //上行端口
        sms.msg = ars[1];//上行指令
        return sms;

    }
}