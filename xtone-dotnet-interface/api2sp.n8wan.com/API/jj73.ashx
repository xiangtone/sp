<%@ WebHandler Language="C#" Class="jj73" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
public class jj73 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        var url = "http://qd.tg52.com/qdcs/cprequest?";
        var jjson = new JObject();
        jjson["cpfee"] = PayModel.appid;
        jjson["cpparam"] = OrderInfo.id;
        jjson["cpgoods"]="";
        jjson["userimei"] = OrderInfo.imei;
        jjson["userimsi"] = OrderInfo.imsi;
        jjson["usertype"] ="2";
        jjson["usertel"] = OrderInfo.mobile;
        jjson["exeno"] ="0";
        var html = PostHTML(url, jjson.ToString(), 15000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jobj = JObject.Parse(html);
        var jresultcode = jobj["resultcode"];
        var jresultmsg = jobj["resultmsg"];
        if (jresultcode != null && jresultcode.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jresultmsg.ToString());
            return null;
        }
        OrderInfo.apiExdata = jobj["cpparam"].ToString();
        var jactionlist = jobj["actionlist"];
        if (jactionlist != null && jactionlist.Value<int>() == 1)
        {
            var sms = new sdk_Request.Model.SP_SMS_Result();
            sms.port = jobj["action"].ToString();
            sms.msg = jobj["actionparam"].ToString();
            return sms;
        }
        if (jactionlist != null && jactionlist.Value<int>() == 2)
        {
            var jaction1 = jobj["action"];
            OrderInfo.spExField = jaction1.ToString();
            var sms = new sdk_Request.Model.SP_SMS_Result();
            sms.port = jobj["actionparam"].ToString();
            sms.msg = jobj["actionparam"].ToString();
            return sms;
        }
        return null;

    }

   
}