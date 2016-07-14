<%@ WebHandler Language="C#" Class="jj70" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
public class jj70 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        OrderInfo.apiExdata = string.Format("api_{0}", OrderInfo.id);

        var url = "http://qd.tg52.com/qdcs/cprequest?";
        var jjson = new JObject();
        jjson["cpfee"] = PayModel.appid;
        jjson["cpparam"] = OrderInfo.apiExdata;
        jjson["cpgoods"] = "";
        jjson["userimei"] = OrderInfo.imei;
        jjson["userimsi"] = OrderInfo.imsi;
        jjson["usertype"] = "2";
        jjson["usertel"] = OrderInfo.mobile;
        jjson["exeno"] = "0";
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
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jresultmsg == null ? null : jresultmsg.ToString());
            return null;
        }
        //OrderInfo.apiExdata = jobj["cpparam"].ToString();
        var jactionlist = (JArray)jobj["actionlist"];
        if (jactionlist == null || jactionlist.Count == 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.UNKONW_RESULT);
            return null;
        }

        sdk_Request.Model.SP_SMS_Result sms;
        if (jactionlist.Count == 2)
        {
            sms = new sdk_Request.Model.SP_2SMS_Result()
            {
                port2 = jactionlist[1]["action"].ToString(),
                msg2 = Convert.ToBase64String(System.Text.ASCIIEncoding.Default.GetBytes(jactionlist[1]["actionparam"].ToString()))
            };
        }
        else
            sms = new sdk_Request.Model.SP_SMS_Result();
        sms.port = jactionlist[0]["action"].ToString();
        sms.msg = Convert.ToBase64String(System.Text.ASCIIEncoding.Default.GetBytes(jactionlist[0]["actionparam"].ToString()));
        sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Data;

        return sms;

    }


}