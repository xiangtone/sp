<%@ WebHandler Language="C#" Class="jj13" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
/// 广州百容的联通广东省网
/// </summary>
public class jj13 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string url = string.Format("http://121.14.17.76:5518/index.aspx?type=1" + "&phone=" + OrderInfo.mobile + "&price=" + PayModel.paycode + "&qudaoid=" + PayModel.appid + "&key=" + PayModel.channelid);
        var html = GetHTML(url, 10000, null);
        if (html == null)
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = Newtonsoft.Json.Linq.JObject.Parse(html);
        var jVal = jobj["status"].ToString();
        var jVaM = jobj["message"].ToString();
        if (jVal == "0")
        {
            var jValod = jVaM;
            OrderInfo.spLinkId = jValod.ToString();
            return new sdk_Request.Model.SP_RESULT();
        }
        sdk_Request.Logical.API_ERROR code = sdk_Request.Logical.API_ERROR.GET_CMD_FAIL;
        switch (jVal)
        {
            case "-1": code = sdk_Request.Logical.API_ERROR.VERIFY_CODE_ERROR; break;
            case "1": code = sdk_Request.Logical.API_ERROR.BLACK_USER; break;
            case "203": code = sdk_Request.Logical.API_ERROR.ERROR_PAY_POINT; break;
        }
        SetError(code, jVaM);
        return null;
        
    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        string url = "http://121.14.17.76:5518/index.aspx?type=2" + "&txid=" + OrderInfo.spLinkId + "&code=" + OrderInfo.cpVerifyCode;
        var html = GetHTML(url, 10000, null);
        if (html == null)
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = Newtonsoft.Json.Linq.JObject.Parse(html);
        sdk_Request.Logical.API_ERROR code = sdk_Request.Logical.API_ERROR.GET_CMD_FAIL;
        var jVal = jobj["status"].ToString();
        var jVaM = jobj["message"].ToString();
        if (jVal =="0") { return new sdk_Request.Model.SP_RESULT(); }
        switch (jVal)
        {
            case "-1":code = sdk_Request.Logical.API_ERROR.VERIFY_CODE_ERROR; break;
        }
        SetError(code, jVaM);
        return null;
    }

}