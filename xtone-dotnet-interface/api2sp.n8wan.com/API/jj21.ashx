<%@ WebHandler Language="C#" Class="jj21" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
/// 鼎兆运 ---优酷游戏
/// </summary>
public class jj21 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
         string url = "http://cc.channel.3gshow.cn/common/req.ashx?imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei+"&mb=" + OrderInfo.mobile +
              "&cid="+PayModel.channelid + "&pid="+PayModel.appid + "&payCodeId="+PayModel.paycode + "&orderId=" + OrderInfo.id + "&cell=" + OrderInfo.cid + "&lac=" + OrderInfo.lac +
                 "&replymode=1"+"&responseType=json"+"&ip=" + OrderInfo.clientIp;

        var html = GetHTML(url, 10000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        
        var jobj = Newtonsoft.Json.Linq.JObject.Parse(html);
        var jVal = jobj["Status"];
        if (!"1000".Equals(jVal.Value<string>()))
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }

        OrderInfo.spLinkId =OrderInfo.id.ToString();
        var sms = new sdk_Request.Model.SP_SMS_Result();
        var Pay1 = ((JArray)jobj["Pay"])[0]; ;
        sms.port = Pay1["Num"].Value<string>();
        sms.msg = Pay1["Content"].Value<string>();

        return sms;
    }

}