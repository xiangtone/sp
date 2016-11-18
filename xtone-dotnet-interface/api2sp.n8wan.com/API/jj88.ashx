<%@ WebHandler Language="C#" Class="jj86" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
/// <summary>
/// 掌付-rdo
/// </summary>
public class jj86 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        OrderInfo.apiExdata = string.Format("0{0}{1}{2:000000000000}", PayModel.appid, PayModel.channelid, OrderInfo.id);

        var url = "http://p.ylmob.com:9030/rdoHttpPay?content=" + PayModel.paycode + OrderInfo.apiExdata.Substring(1) + "&mobile=" + OrderInfo.mobile + "&sepcNumber=" + OrderInfo.id;
        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jcode = jobj["ResultCode"];
        if (jcode != null && jcode.Value<int>() != 200)
        {
            var err = "";
            switch (jcode.ToString())
            {

                case "200": err = "成功"; break;
                case "600099": err = "参数异常"; break;
                case "600098": err = "数据处理异常，下发订单失败"; break;
                case "600097": err = "重复提交，相同指令内容重复提交"; break;
                case "600096": err = "验证码错误"; break;
                default:
                    SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
                    return null;
            }
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, err);
            return null;
        }
        //OrderInfo.apiExdata = string.Format("0{0}{1}{3}{0}{1}{2}", PayModel.appid, PayModel.channelid, OrderInfo.id,"QA");
        return new sdk_Request.Model.SP_RESULT();
    }
    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        var url = "http://p.ylmob.com:9030/rdoconfirm?" + "&content=" + PayModel.paycode + OrderInfo.apiExdata.Substring(1) + "&answer=" + OrderInfo.cpVerifyCode + "&mobile=" + OrderInfo.mobile;
        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jcode = jobj["ResultCode"];
        if (jcode != null && jcode.Value<int>() != 200)
        {
            var err = "";
            switch (jcode.ToString())
            {

                case "200": err = "成功"; break;
                case "600099": err = "参数异常"; break;
                case "600098": err = "数据处理异常，下发订单失败"; break;
                case "600097": err = "重复提交，相同指令内容重复提交"; break;
                case "600096": err = "验证码错误"; break;
                default:
                    SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
                    return null;
            }
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, err);
            return null;
        }
        return new sdk_Request.Model.SP_RESULT();
    }
}