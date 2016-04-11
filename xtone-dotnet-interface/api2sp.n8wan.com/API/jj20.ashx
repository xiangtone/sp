<%@ WebHandler Language="C#" Class="jj20" %>

using System;
using System.Web;

public class jj20 : sdk_Request.Logical.APIRequestGet
{

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string url = "http://121.52.208.188:3001/WoApi20Code";
        OrderInfo.apiExdata = "api" + OrderInfo.id.ToString();
        string xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><request><gameId>"
            + PayModel.paycode + "</gameId><mobile>"
            + OrderInfo.mobile + "</mobile><extData>"
            + OrderInfo.apiExdata + "</extData></request>";

        var html = PostHTML(url, xml);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var doc = new System.Xml.XmlDocument();
        doc.LoadXml(html);
        var root = doc.DocumentElement;

        var node = root.SelectSingleNode("state");
        if (node == null)
        {
            SetError(sdk_Request.Logical.API_ERROR.UNKONW_RESULT);
            return null;
        }
        if (node.InnerText != "0")
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        node = root.SelectSingleNode("linkId");
        OrderInfo.spLinkId = node.InnerText;
        return new sdk_Request.Model.SP_RESULT();
    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {
        string url = "http://121.52.208.188:3001/WoApi20Pay";
        string xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><request><code>" + OrderInfo.cpVerifyCode + "</code><linkId>" + OrderInfo.spLinkId + "</linkId></request>";

        var html = PostHTML(url, xml);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var doc = new System.Xml.XmlDocument();
        doc.LoadXml(html);
        var root = doc.DocumentElement;

        var node = root.SelectSingleNode("state");
        if (node == null)
        {
            SetError(sdk_Request.Logical.API_ERROR.UNKONW_RESULT);
            return null;
        }
        if (node.InnerText != "0")
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }

        return new sdk_Request.Model.SP_RESULT();
    }

}