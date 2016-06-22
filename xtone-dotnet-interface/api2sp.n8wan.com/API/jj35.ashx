<%@ WebHandler Language="C#" Class="jj35" %>

using System;
using System.Web;
using System.Web.Security;
using Newtonsoft.Json.Linq;

/// <summary>
/// 成都次世代
/// </summary>
public class jj35 : sdk_Request.Logical.APIRequestGet
{
    const string skey = "ht0428";
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string url = "http://123.56.91.64:8888/igameunpay/req.jsp";
        var dict = new System.Collections.Generic.Dictionary<string, string>();
        var chnId = PayModel == null ? "szhaot" : PayModel.channelid;
        var paycode = PayModel == null ? "010" : PayModel.paycode;

        dict["merid"] = chnId; //PayModel.channelid;
        dict["goodsid"] = paycode;//PayModel.paycode;
        dict["mobileid"] = OrderInfo.mobile;
        dict["clientip"] = OrderInfo.clientIp;
        dict["orderid"] = OrderInfo.spLinkId = string.Format("{0}{1:0000000000}", chnId, OrderInfo.id);
        dict["orderdate"] = DateTime.Now.ToString("yyyyMMddHHmmss");
        dict["platType"] = "9";
        dict["returl"] = "http://baidu.com/";
        dict["merpriv"] = OrderInfo.spLinkId;
        dict["provcode"] = getProvcode();
        dict["iccid"] = OrderInfo.iccid;
        dict["IMEI"] = OrderInfo.imei;
        dict["IMSI"] = OrderInfo.imsi;
        var val = string.Join(string.Empty, dict.Values);
        val += skey;
        var md5 = FormsAuthentication.HashPasswordForStoringInConfigFile(val, "md5");
        WriteLog("value:" + val + ",md5:" + md5);
        dict["sign"] = md5;
        System.Text.StringBuilder sb = new System.Text.StringBuilder();
        foreach (var kv in dict)
        {
            sb.AppendFormat("{0}={1}&", kv.Key, Server.UrlEncode(kv.Value));
        }
        sb.Length--;
        var html = PostHTML(url, sb.ToString());
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        var jobj = JObject.Parse(html);
        var jval = jobj["retCode"];
        if (jval == null || !"0000".Equals(jval.ToString()))
        {
            jval = jobj["retMsg"];
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jval != null ? jval.Value<string>() : null);
            return null;
        }
        jval = jobj["paytype"];
        if ("YZM".Equals(jval.Value<string>(), StringComparison.OrdinalIgnoreCase))
            return new sdk_Request.Model.SP_RESULT();

        jval = jobj["called2"];
        sdk_Request.Model.SP_SMS_Result sms = null;
        if (jval != null && !string.IsNullOrEmpty(jval.ToString()))
        {
            sms = new sdk_Request.Model.SP_2SMS_Result()
            {
                port2 = jval.Value<string>(),
                msg2 = jobj["mo2"].Value<string>(),
                interval = 5
            };
        }
        else
        {
            sms = new sdk_Request.Model.SP_SMS_Result();
        }
        sms.port = jobj["called"].Value<string>();
        sms.msg = jobj["mo"].Value<string>();
        return sms;
    }
    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        string url = "http://123.56.91.64:8888/igameunpay/submitvercode.jsp";

        var dict = new System.Collections.Generic.Dictionary<string, string>();
        dict["merid"] = PayModel.channelid;
        dict["orderid"] = OrderInfo.spLinkId;
        dict["orderdate"] = DateTime.Now.ToString("yyyyMMdd");
        dict["verifycode"] = OrderInfo.cpVerifyCode;

        var val = string.Join(string.Empty, dict.Values);
        val += skey;
        var md5 = FormsAuthentication.HashPasswordForStoringInConfigFile(val, "md5");
        WriteLog("value:" + val + ",md5:" + md5);
        dict["sign"] = md5;

        dict["sign"] = md5;
        System.Text.StringBuilder sb = new System.Text.StringBuilder();
        foreach (var kv in dict)
        {
            sb.AppendFormat("{0}={1}&", kv.Key, Server.UrlEncode(kv.Value));
        }
        sb.Length--;

        var html = PostHTML(url, sb.ToString());
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }

        var jobj = JObject.Parse(html);
        var jval = jobj["retCode"];
        if (jval == null || jval.Value<int>() != 0)
        {
            jval = jobj["retMsg"];
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jval != null ? jval.Value<string>() : null);
            return null;
        }
        return new sdk_Request.Model.SP_RESULT();

    }

    string getProvcode()
    {
        var city = getCityByImsi(OrderInfo.imsi);
        if (city == null)
            return string.Empty;
        switch (city.province_id)
        {
            case 3: return "551";//安徽
            case 1: return "010";//北京
            case 21: return "591";//福建
            case 11: return "931";//甘肃
            case 8: return "020";//广东
            case 10: return "771";//广西
            case 28: return "851";//贵州
            case 22: return "898";//海南
            case 17: return "311";//河北
            case 26: return "371";//河南
            case 16: return "451";//黑龙江
            case 4: return "027";//湖北
            case 25: return "731";//湖南
            case 12: return "431";//吉林
            case 2: return "025";//江苏
            case 23: return "791";//江西
            case 13: return "024";//辽宁
            case 14: return "471";//内蒙古
            case 29: return "951";//宁夏
            case 27: return "971";//青海
            case 6: return "531";//山东
            case 24: return "351";//山西
            case 20: return "029";//陕西
            case 7: return "021";//上海
            case 19: return "028";//四川
            case 5: return "022";//天津
            case 31: return "891";//西藏
            case 15: return "991";//新疆
            case 30: return "871";//云南
            case 9: return "571";//浙江
            case 18: return "023";//重庆
        }
        return string.Empty;
    }
}
 