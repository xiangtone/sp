<%@ WebHandler Language="C#" Class="jj10" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
/// <summary>
/// 苏州微派 - 短信形式尚未支持
/// </summary>
public class jj10 : sdk_Request.Logical.APIRequestGet
{

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        OrderInfo.apiExdata = OrderInfo.id.ToString();

        string url = string.Format("http://api.wiipay.cn/api.do?sig=" + this.PayModel.appid + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei
             + "&mscCode=" + this.PayModel.channelid + "&cpparam=" + OrderInfo.apiExdata + "&ip=" + OrderInfo.ip + "&tel=" + OrderInfo.mobile + "&price=" + PayModel.paycode);

        var html = GetHTML(url, 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        JObject jobj = JObject.Parse(html);
        var jVal = jobj["result"];
        if (jVal == null || jVal.ToString() != "success")
        {
            jVal = jobj["resultMsg"];
            if (jVal != null)
                SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jVal.Value<string>());
            else
                SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }

        var jarr = (JArray)jobj["smsList"];
        if (jarr != null && jarr.Count != 0)
        {//短信形式支持代码
            SetError(sdk_Request.Logical.API_ERROR.INNER_CONFIG_ERROR);
            return null;

        }

        jVal = jobj["nextUrl"];
        if (jVal == null || string.IsNullOrEmpty(jVal.Value<string>()))
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }


        var nextParam = jobj["nextParam"];
        var data = new System.Text.StringBuilder();
        data.Append(jVal.ToString());
        data.Append("?");
        foreach (var item in nextParam)
        {
            var jp = (JProperty)item;

            data.AppendFormat("{0}=", jp.Name);
            jVal = nextParam[jp.Name];
            data.AppendFormat("{0}&", jVal.ToString());

        }
        //data.AppendFormat("f=", jobj["nextCode"]);
        data.AppendFormat("verifyCode=");

        OrderInfo.spExField = data.ToString();
        OrderInfo.spLinkId = nextParam["orderNo"].ToString();

        return new sdk_Request.Model.SP_RESULT();
    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        var url = OrderInfo.spExField;
        url += OrderInfo.cpVerifyCode;
        var html = GetHTML(url, 15000, null);
        
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        
        
        var jobj = JObject.Parse(html);

        var jVal = jobj["result"];
        if (jVal == null || jVal.ToString() != "success")
        {
            jVal = jobj["resultMsg"];
            if (jVal != null)
                SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jVal.Value<string>());
            else
                SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }

        return new sdk_Request.Model.SP_RESULT();

    }
}