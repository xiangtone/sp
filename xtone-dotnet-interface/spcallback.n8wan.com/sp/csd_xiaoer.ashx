<%@ WebHandler Language="C#" Class="csd_xiaoer" %>

using System;
using System.Web;
using System.Collections.Generic;
using System.Web.Security;

public class csd_xiaoer : n8wan.Public.Logical.BaseSPCallback
{
    const string sKey = "ht0428";
    protected override bool OnInit()
    {
        string[] keys = new string[]{"merid","goodsid","appid","channelid","orderid","orderdate",
                "transdate","mobileid","transtype","settledate","merpriv","retCode"};
        System.Text.StringBuilder sb = new System.Text.StringBuilder();
        foreach (var k in keys)
        {
            sb.Append(GetParamValue(k));
        }
        sb.Append(sKey);
        var md5 = FormsAuthentication.HashPasswordForStoringInConfigFile(sb.ToString(), "md5");
        return md5.Equals(GetParamValue("sign"), StringComparison.OrdinalIgnoreCase);
    }
    /*
    protected override void WriteSuccess()
    {
        writeMsg("ok");
    }
    protected override void WriteError(string msg)
    {
        writeMsg(msg);
    }

    void writeMsg(string msg)
    {
        string[] keys = new string[] { "merid", "goodsid", "orderid", "orderdate", "retCode", "retMsg" };
        var dict = new Dictionary<string, string>();
        foreach (string k in keys)
        {
            dict[k] = GetParamValue(k);
        }
        dict["retCode"] = "0000";
        var md5 = FormsAuthentication.HashPasswordForStoringInConfigFile(string.Join("", dict.Values) + sKey, "md5");
        dict["sign"] = md5;
        Response.Write("{");
        foreach (var kv in dict)
        {
            Response.Write(string.Format("\"{0}\":\"{1}\",", kv.Key, kv.Value));
        }
        Response.Write(string.Format("\"custom\":\"{0}\"}}", msg));

    }*/
}