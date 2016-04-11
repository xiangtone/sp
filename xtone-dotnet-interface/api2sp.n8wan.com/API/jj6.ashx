<%@ WebHandler Language="C#" Class="jj6" %>

using System;
using System.Web;
using System.Runtime.Serialization;
/// <summary>
/// 简易 一次 HTTP模式接入
/// </summary>
public class jj6 : sdk_Request.Logical.APIRequestGet
{
    [DataContract]
    class spRlt
    {
        [DataMember]
        public int status;
        [DataMember]
        public string resultMsg;
        [DataMember]
        public spRltItem[] noteChannels;
    }

    [DataContract]
    class spRltItem
    {
        [DataMember]
        public string sendCmd;// "4N6GvwzYItu+KZ3BsbiOH6G/ffoM37BOqtXR5IT+Vqa61S4CySL8SOvwNGw3Yg2bmiv05+FkFHpPeijTJJxP5g07k/NAbYuPxEburroYdCOfAMKg1etXDGs1v1znPi9/9C1Q5CXPvss=",
        [DataMember]
        public string sendNumber;// "10655198003",
        [DataMember]
        public string sendCmd2;// "4N6GvwzYItu+KZ3BsbiOH6G/ffoM37BOqtXR5IT+Vqa61S4CySL8SOvwNGw3Yg2bmiv05+FkFHpPeijTJJxP5g07k/NAbYuPxEburroYdCOfAMKg1etXDGs1v1znPi9/9C1Q5CXPvss=",
        [DataMember]
        public string sendNumber2;// "10655198003",


        [DataMember]
        public string serviceType;// "CG_492_SHIJI_600",
        [DataMember]
        public string realCode;// "",
        [DataMember]
        public string money;// 600,
        [DataMember]
        public string sequence;// "2016020214580171392",
        [DataMember]
        public string directCmd;// "0",
        [DataMember]
        public string isSync;// "1",
        [DataMember]
        public int secondConfirm;// "0",
        [DataMember]
        public string gameName;// "6",
        [DataMember]
        public int sendType;// "0",
        [DataMember]
        public string extData;// ""  
    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        const string url = "http://sms.ejamad.com/interfaceAction";
        string[] extrs = null;
        if (!string.IsNullOrEmpty(OrderInfo.extraParams))
            extrs = OrderInfo.extraParams.Split(new char[] { ',' }, 2, StringSplitOptions.RemoveEmptyEntries);

        string data = "{\"operation\":\"2\",\"reqChannel\":{\"ipAddr\":\"" + OrderInfo.clientIp
            + "\",\"appId\":\"" + PayModel.appid + "\",\"channelId\":\"" + PayModel.channelid + "\",\"imei\":\"" + OrderInfo.imei + "\",\"money\":" + PayModel.paycode
            + ",\"imsi\":\"" + OrderInfo.imsi + "\",\"orderId\":\"api_" + PayModel.paycode + "\"";
        if (extrs != null)
        {
            data += ",\"gameName\":\"" + extrs[0] + "\"";
            if (data.Length == 2)
                data += ",\"feeName\":\"" + extrs[1] + "\"";
        }
        if (!string.IsNullOrEmpty(OrderInfo.mobile))
            data += ",\"mobile\":\"" + OrderInfo.mobile + "\"";

        data += "},\"version\":\"1.0.0\"}";

        var html = PostHTML(url, data);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        var rlt = Shotgun.Library.Static.JsonParser<spRlt>(html);
        if (rlt.status != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, rlt.resultMsg);
            return null;
        }

        if (rlt.noteChannels == null || rlt.noteChannels.Length != 1)
        {
            SetError(sdk_Request.Logical.API_ERROR.UNKONW_RESULT);
            return null;
        }

        sdk_Request.Model.SP_SMS_Result sms = null;
        var item = rlt.noteChannels[0];
        OrderInfo.spLinkId = item.sequence;
        if (string.IsNullOrEmpty(item.sendCmd2) || string.IsNullOrEmpty(item.sendNumber2))
        {

            sms = new sdk_Request.Model.SP_SMS_Result();
        }
        else
        {
            sms = new sdk_Request.Model.SP_2SMS_Result()
            {
                port2 = item.sendNumber2,
                msg2 = item.sendCmd2,
                interval = 5
            };
        }
        sms.port = item.sendNumber;
        sms.msg = item.sendCmd;

        if (item.sendType == 1)
            sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
        return sms;
    }
}