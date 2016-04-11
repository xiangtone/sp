<%@ WebHandler Language="C#" Class="jj1" %>

using System;
using System.Web;
using System.Runtime.Serialization;

public class jj1 : sdk_Request.Logical.APIRequestGet
{
    [DataContract]
    class result
    {
        [DataMember]
        public int status;
        [DataMember]
        public string sequence;
        [DataMember]
        public string sendCmd;
        [DataMember]
        public string sendNumber;
        [DataMember]
        public string resultMsg;
    }
    
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {

        return new sdk_Request.Model.SP_SMS_Result()
        {
            port = "10690325010253",
            msg = "dg119014Z"+OrderInfo.id,
            status= sdk_Request.Logical.API_ERROR.OK
        };

        //string url = "http://sms.ejamad.com/interfaceAction";
        //var post = "{\"operation\":\"2\",\"reqChannel\":{\"ipAddr\":\"" + OrderInfo.clientIp
        //    + "\" ,\"appId\":\"" + PayModel.appid + "\",\"imei\":\"" + OrderInfo.imei + "\",\"money\":" + OrderInfo.price
        //    + ",\"imsi\":\"" + OrderInfo.imsi + "\",\"orderId\":\"api" + OrderInfo.id + "\",\"mobile\":\""+ OrderInfo.mobile
        //    +"\"},\"version\":\"1.0.0\"}";
        //WriteLog(post);
        //using (var wc = new System.Net.WebClient())
        //{
        //    wc.Encoding = System.Text.ASCIIEncoding.UTF8;
        //    var html = wc.UploadString(url, post);
        //    WriteLog(html);
        //    html = "{\"status\":\"0\",\"noteChannels\":[{\"serviceType\":\"CG_512_BAIRONG_200\",\"realCode\":\"\",\"money\":200,\"sequence\":\"2016011917210170212\",\"directCmd\":\"1\",\"isSync\":\"1\",\"secondConfirm\":\"0\",\"gameName\":\"6\",\"sendType\":\"0\",\"extData\":\"\"}]}";
        //    var rlt = Shotgun.Library.Static.JsonParser<result>(html);
        //    if (rlt.status != 0)
        //    {
        //        SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, rlt.resultMsg);
        //        return null;
        //    }
        //    OrderInfo.spLinkId = rlt.sequence;
        //    return new sdk_Request.Model.SP_SMS_Result()
        //    {
        //        port = rlt.sendNumber,
        //        msg = rlt.sendCmd,
        //        status = sdk_Request.Logical.API_ERROR.OK
        //    };
        //}
    }

}