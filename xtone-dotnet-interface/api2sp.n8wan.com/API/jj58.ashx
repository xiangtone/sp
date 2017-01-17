<%@ WebHandler Language="C#" Class="jj58" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
/// 掌付-视频
/// </summary>
public class jj58 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        var cid = PayModel.channelid.Split(new char[] { ',' });
        if (cid.Length == 2)
            OrderInfo.apiExdata = string.Format("{0}{1:yyMM}{2}", cid[1], DateTime.Today, OrderInfo.id);
        else
            OrderInfo.apiExdata = "0278" + PayModel.appid + PayModel.paycode + OrderInfo.id;

        //OrderInfo.apiExdata = string.Format();

        string url = "http://101.201.148.234/hiwork_jffz_v20/interface/init.php?i=0&t=" + PayModel.paycode + "&sid=" + PayModel.appid + "&cid=" + cid[0] +
            "&serial=" + OrderInfo.apiExdata + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei + "&phone=" + OrderInfo.mobile;

        var html = GetHTML(url, 10000, null);
        var jobj = JObject.Parse(html);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jval = jobj["hRet"];
        if (jval != null && jval.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        OrderInfo.spLinkId = jobj["orderid"].Value<string>();
        //OrderInfo.apiExdata = "0278" + PayModel.appid + PayModel.paycode + OrderInfo.id;

        return new sdk_Request.Model.SP_RESULT();

    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        var cid = PayModel.channelid.Split(new char[] { ',' });

        string url = "http://101.201.148.234/hiwork_jffz_v20/interface/init.php?i=1&t=" + PayModel.paycode + "&sid=" + PayModel.appid + "&cid=" + cid[0]
            + "&serial=" + OrderInfo.apiExdata + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei + "&phone=" + OrderInfo.mobile
            + "&vcode=" + OrderInfo.cpVerifyCode + "&orderid=" + OrderInfo.spLinkId;
        var html = GetHTML(url);
        var jobj = JObject.Parse(html);
        var jval = jobj["hRet"];
        if (jval.Value<string>() == "0")
            return new sdk_Request.Model.SP_RESULT();
        SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
        return null;
    }

}