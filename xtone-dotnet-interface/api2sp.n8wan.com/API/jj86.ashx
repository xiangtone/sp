<%@ WebHandler Language="C#" Class="jj86" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
/// 仅适用于联网验证码
/// appid:type,siteid
/// paycode:codeid
/// serial:channelid (!开头表示固定)
/// </summary>
public class jj86 : sdk_Request.Logical.AsyncAPIRequestGet
{


    JObject ProcUrl(string url)
    {
        var html = GetHTML(url);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jcode = jobj["hRet"];
        if (jcode == null || jcode.Value<int>() == 0)
            return jobj;

        var err = "";
        switch (jcode.Value<int>())
        {
            case 101: err = "登录接收参数有误"; break;
            case 102: err = "登录imsi有误"; break;
            case 103: err = "登录游戏编号有误"; break;
            case 104: err = "登录渠道编号有误"; break;
            case 105: err = "登录道具编号有误"; break;
            case 106: err = "获取验证码失败"; break;
            case 119: err = "登录imei有误"; break;
            case 201: err = "计费接收参数有误"; break;
            case 202: err = "计费imsi有误"; break;
            case 203: err = "计费游戏编号有误"; break;
            case 204: err = "计费渠道编号有误"; break;
            case 205: err = "计费道具编号有误"; break;
            case 219: err = "计费imei有误"; break;
            case 206: err = "计费请求失败"; break;
            case 207: err = "单日代码总量达上限或道具未设上限"; break;
            case 120: err = "手机号为空"; break;
            case 121: err = "验证码短信或验证码端口号为空"; break;
            default:
                SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
                return null;
        }
        WriteLog(err);
        SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, err);
        return null;

    }



    protected override sdk_Request.Model.SP_RESULT AsyncGetVerifyCode()
    {
        var appids = PayModel.appid.Split(new char[] { ',' });
        if (!string.IsNullOrEmpty(PayModel.channelid) && PayModel.channelid.StartsWith("!"))
            OrderInfo.apiExdata = PayModel.channelid.Substring(1);
        else
            OrderInfo.apiExdata = string.Format("{0}{1:yyMM}{2:x}", PayModel.channelid, DateTime.Today, OrderInfo.id);

        var url = "http://ivas.iizhifu.com/init.php?i=0&type=" + appids[0]
            + "&siteid=" + appids[1]
            + "&codeid=" + PayModel.paycode
            + "&serial=" + OrderInfo.apiExdata
            + "&imsi=" + OrderInfo.imsi
            + "&imei=" + OrderInfo.imei
            + "&phone=" + OrderInfo.mobile
            + "&ip=" + OrderInfo.clientIp;
        if (!string.IsNullOrEmpty(OrderInfo.iccid))
            url += "&icc=" + OrderInfo.iccid;

        var jobj = ProcUrl(url);
        if (jobj == null)
            return null;
        //OrderInfo.apiExdata = PayModel.paycode + OrderInfo.id;
        var splinkid = jobj["orderid"];
        if (splinkid != null)
            OrderInfo.spLinkId = jobj["orderid"].Value<string>();
        return new sdk_Request.Model.SP_RESULT();
    }

    protected override sdk_Request.Model.SP_RESULT AsyncSubmitVerifyCode()
    {
        var appids = PayModel.appid.Split(new char[] { ',' });

        var url = "http://ivas.iizhifu.com/init.php?i=1&type=" + appids[0]
            + "&codeid=" + PayModel.paycode
            + "&siteid=" + appids[1]
            + "&serial=" + OrderInfo.apiExdata
            + "&imsi=" + OrderInfo.imsi
            + "&imei=" + OrderInfo.imei
            + "&phone=" + OrderInfo.mobile
            + "&vcode=" + OrderInfo.cpVerifyCode
            + "&ip=" + OrderInfo.clientIp
            + "&orderid=" + OrderInfo.spLinkId;
        if (!string.IsNullOrEmpty(OrderInfo.iccid))
            url += "&icc=" + OrderInfo.iccid;



        if (ProcUrl(url) == null)
            return null;

        return new sdk_Request.Model.SP_RESULT();
    }
}