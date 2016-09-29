<%@ WebHandler Language="C#" Class="jj86" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
/// 榕元-wo+-开心消消砰
/// </summary>
public class jj86 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        var url = "http://ivas.iizhifu.com/init.php?type=300" + "&siteid=166" + "&codeid=" + PayModel.channelid + "&serial=" + PayModel.paycode + OrderInfo.id + "&i=0" + "&phone=" + OrderInfo.mobile;
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

                case "201": err = "接收参数有误"; break;
                case "203": err = "游戏编号有误"; break;
                case "204": err = "渠道编号有误"; break;
                case "205": err = "道具编号有误"; break;
                case "106": err = "获取订单号失败"; break;
                case "206": err = "计费确认失败"; break;
                case "120": err = "手机号为空"; break;
                case "215": err = "计费确认时未带订单号或手机验证码"; break;
                case "207": err = "单日代码总量达上限或道具未设上限"; break;
                default:
                    SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
                    return null;
            }
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL,err);
            return null;
        }
        OrderInfo.apiExdata = PayModel.paycode + OrderInfo.id;
        OrderInfo.spLinkId =jobj["orderid"].Value<string>();
        return new sdk_Request.Model.SP_RESULT();
    }
    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        var url = "http://ivas.iizhifu.com/init.php?type=300" + "&siteid=166" + "&codeid=1040004" + "&i=1" + "&orderid=" + OrderInfo.spLinkId + "&vcode=" + OrderInfo.cpVerifyCode;
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

                case "201": err = "接收参数有误"; break;
                case "203": err = "游戏编号有误"; break;
                case "204": err = "渠道编号有误"; break;
                case "205": err = "道具编号有误"; break;
                case "106": err = "获取订单号失败"; break;
                case "206": err = "计费确认失败"; break;
                case "120": err = "手机号为空"; break;
                case "215": err = "计费确认时未带订单号或手机验证码"; break;
                case "207": err = "单日代码总量达上限或道具未设上限"; break;
                default:
                    SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
                    return null;
            }
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL,err);
            return null;
        }
        return new sdk_Request.Model.SP_RESULT();
    }
}