<%@ WebHandler Language="C#" Class="jj10" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
/// <summary>
/// 苏州微派
/// </summary>
public class jj10 : sdk_Request.Logical.APIRequestGet
{
    [DataContract]
    class step1
    {
        [DataMember]
        public int errorCode;
        [DataMember]
        public string errorDesc;

        [DataMember]
        public int resultCode;

        [DataMember]
        public string resultMsg;
        [DataMember]
        public string orderid;
    }



    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string url = string.Format("http://api.wiipay.cn/api.do?sig=" + this.PayModel.appid + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei
             +"&mscCode="+this.PayModel.channelid+"&cpparam="+OrderInfo.apiExdata+"&ip="+OrderInfo.ip+"&tel="+OrderInfo.mobile+"&price="+ PayModel.paycode);
        
        var html = GetHTML(url);
        
         JObject jobj = JObject.Parse(html);
         var jVal = jobj["status"];
         if (jVal == null || jVal.ToString()!= "success")
        {
            jVal = jobj["resultMsg"];
            if (jVal != null)
                SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jVal.Value<string>());
            else
                SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
         var sms = new sdk_Request.Model.SP_SMS_Result();
         sms.port = jobj["smsPort"].Value<string>();
         sms.msg = jobj["smsContent"].Value<string>();
         OrderInfo.spLinkId = jobj["orderNo"].Value<string>();
         //if (this.PayModel.appid == "1")
         sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;

         sms.status = sdk_Request.Logical.API_ERROR.OK;
         return sms;

     }
}