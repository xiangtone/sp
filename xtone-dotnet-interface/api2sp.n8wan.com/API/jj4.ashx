<%@ WebHandler Language="C#" Class="jj3" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;

public class jj3 : sdk_Request.Logical.APIRequestGet
{
    //public override void BeginProcess()
    //{
    //    var xxx = GetSpCmd();
    //    Response.Write(xxx.ToJson());
    //}

    [DataContract]
    class spResult
    {
        [DataMember]
        public string imsi;// "460001234576655",
        [DataMember]
        public string phoneNumber;// null,
        [DataMember]
        public string sequenceid;// "019EA374-33ED-449C-A8DA-9B902C3DCA0D",
        [DataMember]
        public string sms0data;// "BUB@T|0      9ud88@d2f@A@8318L74PC43@96886809@8788578477@5@494257156252608@91457@5665468328894",
        [DataMember]
        public string sms0data64;// "QlVCQFR8MCAgICAgIDl1ZDg4QGQyZkBBQDgzMThMNzRQQzQzQDk2ODg2ODA5QDg3ODg1Nzg0NzdANUA0OTQyNTcxNTYyNTI2MDhAOTE0NTdANTY2NTQ2ODMyODg5NA==",
        [DataMember]
        public string sms0to;// "10658422",
        [DataMember]
        public string sms1data64;// "OnA6b0gsKzlqLVYuXDp0O0A2QDw7MjZ+OitSOm9HUFkvPzJiVUgvOU8zNlczMnBmM2omcDpYWyVWW04zK0VfKjwzQF46ei06OjE6OndZKEYzd202MndlTDM6PTo6M1twPFczaDFVJW5efWM8dVpAVnJURiBVVy9KTjI2ZUBrOg==",
        [DataMember]
        public string sms1to;// "1065889923",
        [DataMember]
        public string sms2data64;// "57OW5p6c57KJ56KO5aSn5biI",
        [DataMember]
        public string sms2from;// "1065889955",
        [DataMember]
        public string sms3data64;// "54K55pWw",
        [DataMember]
        public string sms3from;// "10086",
        [DataMember]
        public string status_code;// "0",
        [DataMember]
        public string userId;// null,
        [DataMember]
        public string valid_time;// "2014-12-31 16:45:04.0"
        [DataMember]
        public string message;// "2014-12-31 16:45:04.0"
    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string price = null;
        switch (PayModel.paycode)
        {
            case "006083523001": price = "200"; break;
            case "006083523002": price = "400"; break;
            case "006083523003": price = "600"; break;
            case "006083523004": price = "800"; break;
            case "006083523005": price = "1200"; break;
            case "006083523006": price = "2000"; break;
            case "006083523007": price = "100"; break;
            case "006083523008": price = "1000"; break;
            case "006083523009": price = "1800"; break;
            case "006083523010": price = "10"; break;
            default:
                SetError(sdk_Request.Logical.API_ERROR.INNER_CONFIG_ERROR, "计费点配置错误,PayCode:" + PayModel.paycode);
                return null;
        }
        string cpparam = "eapi" + OrderInfo.id.ToString("000000000");

        var vPhone = n8wan.Public.Library.GetPhoneByImsi(OrderInfo.imsi);
        var pro = dBase.ExecuteScalar("select name from tbl_province where id in(select province_id from tbl_city  where id in( select city_id from  tbl_phone_locate where  phone=" + vPhone + "))");

        if (pro != null)
        {
            var s = Shotgun.Library.PinYinConverter.GetFirst((string)pro).ToLower();
            if (s.Length > 2)
                cpparam += s.Substring(0, 2);
            else
                cpparam += s;
        }
        OrderInfo.apiExdata = cpparam;


        string url = "http://120.24.80.212/GameServer/requestChargeInfo?imsi=" + OrderInfo.imsi + "&game_id=" + PayModel.appid + "&channel_id=" + PayModel.channelid
            + "&keyid=kqdgdxxxk&price=" + price + "&goodsId=" + PayModel.paycode + "&cpparam=" + cpparam;
        WriteLog(url);
        var html = GetHTML(url);
        WriteLog(html);
        var rlt = Shotgun.Library.Static.JsonParser<spResult>(html);
        if (rlt.status_code != "0")
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, rlt.message);
            return null;
        }


        var sms = new sdk_Request.Model.SP_2SMS_Result();
        sms.port = rlt.sms0to;
        sms.msg = rlt.sms0data;
        sms.port2 = rlt.sms1to;
        sms.msg2 = System.Text.ASCIIEncoding.UTF8.GetString(Convert.FromBase64String(rlt.sms1data64));
        sms.interval = 20;

        return sms;
    }
}