<%@ WebHandler Language="C#" Class="jj19" %>

using System;
using System.Web;
using System.Runtime.Serialization;

/// <summary>
/// 对接自己撸的系统
/// </summary>
public class jj19 : sdk_Request.Logical.APIRequestGet
{
    [DataContract]
    class data
    {
        [DataMember]
        public string msg;
        [DataMember]
        public string port;
        [DataMember]
        public string msg2;
        [DataMember]
        public string port2;
        [DataMember]
        public string description;
        public sdk_Request.Logical.E_SMS_TYPE SMSType;
    }
    [DataContract]
    class result
    {
        [DataMember]
        public sdk_Request.Logical.API_ERROR status;
        [DataMember]
        public string orderNum;
        [DataMember]
        public data resultJson;
    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var url = "http://thread1.n8wan.com/request.jsp?paycode=" + PayModel.paycode
            + "&imei=" + OrderInfo.imei
            + "&imsi=" + OrderInfo.imsi
            + "&clientip=" + OrderInfo.clientIp
            + "&extra_params=" + OrderInfo.extraParams
            + "&phone=" + OrderInfo.mobile
            + "&params=" + OrderInfo.apiExdata;

        var html = GetHTML(url);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        var rlt = Shotgun.Library.Static.JsonParser<result>(html);
        if (rlt.status != sdk_Request.Logical.API_ERROR.OK)
        {
            if (rlt.resultJson == null)
                SetError(rlt.status);
            else
                SetError(rlt.status, rlt.resultJson.description);
            return null;
        }
        OrderInfo.spLinkId = rlt.orderNum;

        if (string.IsNullOrEmpty(rlt.resultJson.msg))
            return new sdk_Request.Model.SP_RESULT();
        sdk_Request.Model.SP_SMS_Result sms = null;
        if (string.IsNullOrEmpty(rlt.resultJson.msg2))
        { sms = new sdk_Request.Model.SP_SMS_Result(); }
        else
        {
            var sms2 = new sdk_Request.Model.SP_2SMS_Result();
            sms2.port2 = rlt.resultJson.port2;
            sms2.msg2 = rlt.resultJson.msg2;
            sms = sms2;
        }
        sms.SMSType = rlt.resultJson.SMSType;
        sms.port = rlt.resultJson.port;
        sms.msg = System.Text.ASCIIEncoding.UTF8.GetString(Convert.FromBase64String(rlt.resultJson.msg));
        return sms;
    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {

        var url = "http://thread.n8wan.com/vcode.jsp?vcode=" + OrderInfo.cpVerifyCode + "&ordernum=" + OrderInfo.spLinkId;
        var html = GetHTML(url);

        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        var rlt = Shotgun.Library.Static.JsonParser<result>(html);
        if (rlt.status != sdk_Request.Logical.API_ERROR.OK)
        {
            if (rlt.resultJson == null)
                SetError(rlt.status);
            else
                SetError(rlt.status, rlt.resultJson.description);
            return null;
        }

        return new sdk_Request.Model.SP_RESULT();
    }
}