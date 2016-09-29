<%@ WebHandler Language="C#" Class="jj93" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Linq;


public class jj93 : sdk_Request.Logical.APIRequestGet
{

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        OrderInfo.apiExdata = OrderInfo.id.ToString();

        var url = "http://123.57.23.150:8002/HttpAPI";
        var dic = new System.Collections.Generic.Dictionary<string, string>();
        dic["ver"] = "1.0";
        dic["channel"] = PayModel.channelid;
        dic["imsi"] = OrderInfo.imsi;
        dic["mobile"] = OrderInfo.mobile;
        dic["apName"] = "深圳浩天";
        dic["appName"] = "手机游戏";
        dic["chargeName"] = "游戏道具";
        dic["price"] = PayModel.paycode;
        dic["chargeType"] = "1";
        dic["timestamp"] = ((DateTime.Now.ToUniversalTime().Ticks - 621355968000000000) / 10000).ToString();
        dic["orderId"] = OrderInfo.apiExdata;
        var keys = from k in dic.Keys orderby k select k;
        var sb = new System.Text.StringBuilder();
        foreach (var k in keys)
        {
            sb.AppendFormat("{0}={1}&", k, Server.UrlEncode(dic[k]));
        }
        sb.Length--;

        var data = sb.ToString() + PayModel.appid;

        var sha = SHA1_Hash(data);

        WriteLog("data:" + data);
        WriteLog("sha:" + sha);

        data = string.Format("{0}&sig={1}", sb, sha);
        
        var html = PostHTML(url, data);

        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);

        var jval = jobj["resultCode"];
        if (jval == null || jval.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        var sms = new sdk_Request.Model.SP_SMS_Result();
        sms.port = jobj["smsNum"].ToString();
        sms.msg = jobj["smsContent"].ToString();
        return sms;


    }

    static public string SHA1_Hash(string str_sha1_in)
    {
        var sha1 = new System.Security.Cryptography.SHA1CryptoServiceProvider();
        byte[] bytes_sha1_in = System.Text.UTF8Encoding.Default.GetBytes(str_sha1_in);
        byte[] bytes_sha1_out = sha1.ComputeHash(bytes_sha1_in);
        string str_sha1_out = BitConverter.ToString(bytes_sha1_out);
        str_sha1_out = str_sha1_out.Replace("-", "");
        return str_sha1_out;
    }
}