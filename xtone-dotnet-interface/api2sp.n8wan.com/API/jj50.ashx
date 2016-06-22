<%@ WebHandler Language="C#" Class="jj50" %>

using System;
using System.Web;
using System.Runtime.Serialization;
/// <summary>
/// 数通世纪 - 中投
/// </summary>
public class jj50 : sdk_Request.Logical.APIRequestGet
{
    [DataContract]
    public class resutl
    {
        [DataMember]
        public string id;
        [DataMember]
        public int result;
        [DataMember]
        public string payreporturl;
        [DataMember]
        public string loginport;
        [DataMember]
        public string paysms;
        [DataMember]
        public string loginsms;
        [DataMember]
        public string loginreporturl;
        [DataMember]
        public string billid;
        [DataMember]
        public string payport;
        [DataMember]
        public string resulttext;


    }

    [DataContract]
    public class SP_SMS_2Feedback_Result : sdk_Request.Model.SP_SMS_Feedback_Result
    {
        [DataMember]
        public string FeedBack2 { get; set; }
        [DataMember]
        public string port2 { get; set; }
        [DataMember]
        public string msg2 { get; set; }

    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        OrderInfo.apiExdata = string.Format("{0}{1}", PayModel.paycode, OrderInfo.id);
        var url = "http://123.59.165.142:18989/billPreTotalhold";
        var data = string.Format("channel={0}&flag={1}&imsi={2}&cpparam={3}", PayModel.channelid, PayModel.appid, OrderInfo.imsi, OrderInfo.apiExdata);
        //channel	String	否	10001
        //flag	String	否	1004
        //imsi	String	否	用户终端的imsi信息
        //cpparam	String	是	渠道自定义参数, 2D为保留字符

        var html = PostHTML(url, data, 10000, null);

        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        var rlt = Shotgun.Library.Static.JsonParser<resutl>(html);
        if (rlt.result != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, rlt.resulttext);
            return null;
        }


        var sms = new SP_SMS_2Feedback_Result();
        sms.port = rlt.loginport;
        sms.msg = System.Text.ASCIIEncoding.UTF8.GetString(Convert.FromBase64String(rlt.loginsms));
        sms.FeedBack = string.Format("{0}?id={1}&imsi={2}&sent=", rlt.loginreporturl, rlt.id, OrderInfo.imsi);
        sms.port2 = rlt.payport;
        sms.msg2 = System.Text.ASCIIEncoding.UTF8.GetString(Convert.FromBase64String(rlt.paysms));
        sms.FeedBack2 = string.Format("{0}?id={1}&imsi={2}&sent=", rlt.payreporturl, rlt.id, OrderInfo.imsi);
        sms.Interval = 5;
        //sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
        OrderInfo.spLinkId = rlt.billid;
        return sms;
    }
}