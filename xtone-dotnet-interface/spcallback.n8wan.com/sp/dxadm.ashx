<%@ WebHandler Language="C#" Class="dxadm" %>

using System;
using System.Web;
using System.Xml;

/// <summary>
/// 天翼爱动漫文化传媒有限公司 电信爱动漫
/// </summary>
public class dxadm : n8wan.Public.Logical.BaseSPCallback
{

    XmlElement root;
    protected override bool OnInit()
    {
        Response.ContentType = "text/xml";
        var xmlstr = Request["requestData"];
        if (xmlstr == null || xmlstr.Length < 10)
            return false;
        var xml = new XmlDocument();
        try
        {
            xml.LoadXml(xmlstr);
        }
        catch
        {
            return false;
        }
        root = xml.DocumentElement;

        return base.OnInit();
    }

    public override string GetParamValue(string Field)
    {
        if (root == null)
            return null;
        foreach (XmlNode node in root)
        {
            if (node.NodeType != XmlNodeType.Element)
                continue;
            if (node.Name.Equals(Field, StringComparison.OrdinalIgnoreCase))
            {
                if (Field.Equals("App_id", StringComparison.OrdinalIgnoreCase))
                    return System.Text.RegularExpressions.Regex.Replace(node.InnerText, @"[^\d]", "");
                return node.InnerText;
            }
        }
        return base.GetParamValue(Field);
    }

    protected override void WriteError(string msg)
    {
        Response.Write("<ResponseBody><Status>0</Status><Trade_no>" + GetLinkId() + "</Trade_no></ResponseBody>");
        Response.Write("<!--" + msg + "-->");

    }
    protected override void WriteSuccess()
    {
        Response.Write("<ResponseBody><Status>0</Status><Trade_no>" + GetLinkId() + "</Trade_no></ResponseBody>");
        Response.Write("<!--ok-->");
    }
}