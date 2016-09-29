<%@ WebHandler Language="C#" Class="jj23" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
/// 广州瑶品--rdo
/// </summary>
public class jj23 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        OrderInfo.spLinkId = "T109" + OrderInfo.id;
        string url = "http://s.smilegames.cn:88/api/GetVerifyCode?order_id=" + OrderInfo.spLinkId + "&channel_id=" + PayModel.appid + "&product_id=" + PayModel.channelid + "&price=" + PayModel.paycode +
        "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei + "&phone=" + OrderInfo.mobile + "&version=100" + "&timestamp=" + DateTime.Now.ToString("yyyyMMddHHmmss");
        var html = GetHTML(url, 10000, null);
        if (html == null)
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = Newtonsoft.Json.Linq.JObject.Parse(html);
        var jVal = jobj["result"];
        if (jVal!=null && jVal.ToString() == "success")
        {
           
            return new sdk_Request.Model.SP_RESULT();
        }
        SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
        return null;
        
    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        string url = "http://s.smilegames.cn:88/api/SendVerifyCode?" + "order_id =" + OrderInfo.spLinkId + "&channel_id=" + PayModel.appid + "&product_id=" + PayModel.channelid +
           "&price=" + PayModel.paycode + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei + "&phone=" + OrderInfo.mobile +"&timestamp=" + DateTime.Now.ToString("yyyyMMddHHmmss")+"&version=100" + "&verify_code=" + OrderInfo.cpVerifyCode;
        var html = GetHTML(url, 10000, null);
        if (html == null)
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = Newtonsoft.Json.Linq.JObject.Parse(html);
        var jVal = jobj["result"];
        if (jVal != null && jVal.ToString() == "success")
        {
            return new sdk_Request.Model.SP_RESULT();
        }
        return null;
    }

}