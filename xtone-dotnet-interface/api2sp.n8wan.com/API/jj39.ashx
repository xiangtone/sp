<%@ WebHandler Language="C#" Class="jj39" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;

public class jj39 : sdk_Request.Logical.APIRequestGet
{
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

        var city = getCityByImsi(OrderInfo.imsi);

        int pid = 0;
        switch (city.province_id)
        {
            case 3: pid = 12; break;//安徽
            case 1: pid = 1; break;//北京
            case 21: pid = 13; break;//福建
            case 11: pid = 27; break;//甘肃
            case 8: pid = 19; break;//广东
            case 10: pid = 20; break;//广西
            case 28: pid = 23; break;//贵州
            case 22: pid = 21; break;//海南
            case 17: pid = 3; break;//河北
            case 26: pid = 16; break;//河南
            case 16: pid = 8; break;//黑龙江
            case 4: pid = 17; break;//湖北
            case 25: pid = 18; break;//湖南
            case 12: pid = 7; break;//吉林
            case 2: pid = 10; break;//江苏
            case 23: pid = 14; break;//江西
            case 13: pid = 6; break;//辽宁
            case 14: pid = 5; break;//内蒙古
            case 29: pid = 29; break;//宁夏
            case 27: pid = 28; break;//青海
            case 6: pid = 15; break;//山东
            case 24: pid = 4; break;//山西
            case 20: pid = 26; break;//陕西
            case 7: pid = 9; break;//上海
            case 19: pid = 22; break;//四川
            case 5: pid = 2; break;//天津
            case 31: pid = 25; break;//西藏
            case 15: pid = 30; break;//新疆
            case 30: pid = 24; break;//云南
            case 9: pid = 11; break;//浙江
            case 18: pid = 31; break;//重庆
        }

        var data = "channelcode=" + PayModel.channelid
                    + "&consumeCode=" + PayModel.paycode
                    + "&imsi=" + OrderInfo.imsi
                    + "&imei=" + OrderInfo.imei
                    + "&province=" + pid.ToString("00")
                    + "&ext=" + OrderInfo.id.ToString("x");

        var url = "http://billing.nj8b.com:8082/cmcrack/cmreques.php";
        var html = PostHTML(url, data, 10000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jval = jobj["status"];
        if (jval == null || jval.Value<int>() != 0)
        {
            //1 参数不符合要求
            //2 找不到业务记录
            //3 业务关闭
            //4 找不到渠道记录
            //5 找不到游戏记录
            //6 找不到apk记录
            //7 找不到道具
            //8 找不到sdk信息
            //9 生成基地代码错误
            //10 超时：默认3秒
            var code = jval == null ? 10 : jval.Value<int>();
            switch (code)
            {
                case 1:
                case 2:
                    SetError(sdk_Request.Logical.API_ERROR.ERROR_PAY_POINT); break;
                default:
                    SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL); break;
            }
            return null;
        }
        var sms = new SP_SMS_2Feedback_Result();
        sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;
        jval = jobj["regcallBackDelay"];

        sms.Interval = jval == null ? 5 : jval.Value<int>() / 1000;

        sms.port = jobj["regport"].ToString();
        sms.msg = jobj["regmsg64"].ToString();
        sms.FeedBack = jobj["regcburl"].ToString();

        sms.port2 = jobj["orderport"].ToString();
        sms.msg2 = jobj["ordermsg64"].ToString();
        sms.FeedBack2 = jobj["ordercburl"].ToString();

        OrderInfo.apiExdata = jobj["cpparam"].ToString();
        OrderInfo.spLinkId = jobj["linkid"].ToString();
        return sms;


    }
}