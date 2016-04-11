<%@ WebHandler Language="C#" Class="jj15" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
/// 深圳博咔咔---rdo
/// </summary>
public class jj15 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        OrderInfo.apiExdata = string.Format("{0}{1}", PayModel.appid, OrderInfo.id);
        string url = "http://yys.astep.cn:8070/sms/request/content/rdo.htm?cpid=" + PayModel.channelid + "&phone=" + OrderInfo.mobile
            + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei + "&price=" + PayModel.paycode + "&cpparam=" + OrderInfo.apiExdata;

        OrderInfo.spLinkId = string.Format("{0}{1}", PayModel.channelid, OrderInfo.apiExdata);

        var html = GetHTML(url, 10000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        if ("0".Equals(html))
            return new sdk_Request.Model.SP_RESULT();

        var xml = new System.Xml.XmlDocument();
        try
        {
            xml.LoadXml(html);
        }
        catch (Exception ex)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, ex.Message);
            return null;
        }

        var node = xml.SelectSingleNode("/result/status");
        if (node == null)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }

        if ("0".Equals(node.InnerText))
            return new sdk_Request.Model.SP_RESULT();
        node = xml.SelectSingleNode("/result/describe");
        if (node == null)
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
        else
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, node.InnerText);
        return null;
    }

}