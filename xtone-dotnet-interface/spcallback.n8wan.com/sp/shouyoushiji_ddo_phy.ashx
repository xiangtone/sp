<%@ WebHandler Language="C#" Class="shouyoushijin_ddo_phy" %>

using System;
using System.Web;
using System.Xml;

public class shouyoushijin_ddo_phy : n8wan.Public.Logical.BaseSPCallback
{
    XmlElement root;

    protected override bool OnInit()
    {
        if (Request.TotalBytes < 10)
            return false;
        var xml = new XmlDocument();
        try
        {
            xml.Load(Request.InputStream);
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
        foreach (XmlNode node in root.ChildNodes)
        {
            if (node.NodeType != XmlNodeType.Element)
                continue;
            if (node.Name.Equals(Field, StringComparison.InvariantCultureIgnoreCase))
            {
                return node.InnerText;
            }
        }
        return base.GetParamValue(Field);
    }

    protected override void WriteSuccess()
    {
        var xmlstr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SyncAppOrderResp><TransactionID>"
            + GetParamValue("TransactionID")
            + "</TransactionID><hRet>success</hRet><!-- OK --></SyncAppOrderResp>";
        Response.Write(xmlstr);
    }
    protected override void WriteError(string msg)
    {
        var xmlstr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SyncAppOrderResp><TransactionID>"
             + GetParamValue("TransactionID")
            + "</TransactionID><hRet>success</hRet><!-- "
            + msg + " --></SyncAppOrderResp>";
        Response.Write(xmlstr);
    }
}