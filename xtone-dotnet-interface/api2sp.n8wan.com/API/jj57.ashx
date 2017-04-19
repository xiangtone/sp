﻿<%@ WebHandler Language="C#" Class="jj57" %>

using System;
using System.Web;
/// <summary>
///RDO 基地对接代码
/// </summary>
public class jj57 : sdk_Request.Logical.APIRequestGet
{
    //const string secKey = "youxue888";
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        OrderInfo.spLinkId = string.Format("ht_{0:yyyyMMddss}_{1}", DateTime.Now, OrderInfo.id);
        System.Collections.Generic.Dictionary<string, string> dic = new System.Collections.Generic.Dictionary<string, string>();
        /*
        var PayModel = new LightDataModel.tbl_trone_paycodeItem();
        PayModel.appid = "jy0001";
        PayModel.channelid = "J0540001";
        PayModel.paycode = "87000101";
         */
        string[] ar = PayModel.channelid.Split(new char[] { ',' }, 2, StringSplitOptions.RemoveEmptyEntries);
        string secKey = ar[1];
        string channelid = ar[0];

        dic["mcpid"] = PayModel.appid;
        dic["feeCode"] = PayModel.paycode;
        dic["orderNo"] = OrderInfo.spLinkId;
        dic["reqTime"] = DateTime.Now.ToString("yyyyMMddHHmmss");
        dic["key"] = secKey;

        //MD5(mcpid+feeCode+orderNo+reqTime+key)
        var s = string.Empty;
        foreach (var kv in dic)
        {
            s += kv.Value;
        }
        string url = "http://wap.cmread.com/rdo/order?mcpid=" + PayModel.appid
            + "&orderNo=" + OrderInfo.spLinkId
            + "&feeCode=" + PayModel.paycode
            + "&reqTime=" + dic["reqTime"]
            + "&sign=" + System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile(s, "md5")
            + "&layout=9"
            + "&cm=" + channelid;

        System.Xml.XmlDocument xml = new System.Xml.XmlDocument();
        var html = GetHTML(url);
        xml.LoadXml(html);

        var el = xml.SelectSingleNode("/Response/ResultCode");
        if (el.InnerText != "200")
        {
            el = xml.SelectSingleNode("/Response/ResultMsg");
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, el.InnerText);
            return null;
        }

        el = xml.SelectSingleNode("/Response/Order/Submit0/GetSMSVerifyCodeUrl");
        url = el.InnerText + "&msisdn=" + OrderInfo.mobile;
        html = GetHTML(url);
        xml.LoadXml(html);

        el = xml.SelectSingleNode("/Response/ResultCode");
        if (el.InnerText != "200")
        {
            el = xml.SelectSingleNode("/Response/ResultMsg");
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, el.InnerText);
            return null;
        }

        el = null;
        el = xml.SelectSingleNode("/Response/Order/Submit0/ButtonTag/SubmitUrl");
        url = el.InnerText;// +"&";// +OrderInfo.mobile;
        OrderInfo.spExField = url;
        return new sdk_Request.Model.SP_RESULT();

    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        var html = PostHTML(OrderInfo.spExField, "verifyCode=" + OrderInfo.cpVerifyCode);
        System.Xml.XmlDocument xml = new System.Xml.XmlDocument();
        xml.LoadXml(html);

        var el = xml.SelectSingleNode("/Response/ResultCode");
        var jResultCode = el.InnerText;
        if (jResultCode != "200")
        {
            el = xml.SelectSingleNode("/Response/ResultMsg");
            switch (jResultCode)
            {
                case "9998": SetError(sdk_Request.Logical.API_ERROR.VERIFY_CODE_ERROR, el.InnerText); break;
                case "9997": SetError(sdk_Request.Logical.API_ERROR.BLACK_USER, el.InnerText); break;
                case "90010": SetError(sdk_Request.Logical.API_ERROR.AREA_CLOSE, el.InnerText); break;
                    default:
                    SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, el.InnerText);
                    return null;
            }
            return null;

        }
        return new sdk_Request.Model.SP_RESULT();
    }
}