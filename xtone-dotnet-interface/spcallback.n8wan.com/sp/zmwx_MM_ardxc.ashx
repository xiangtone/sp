<%@ WebHandler Language="C#" Class="zmwx_MM_zcd" %>

using System;
using System.Web;
using System.Xml;

/// <summary>
/// 北京翔通 - 皇家厨院
/// </summary>
public class zmwx_MM_zcd : gzzyPublic.SPCallback.AutoMapCallback
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
            return string.Empty;
        if (Field.Equals("status"))
        {
            var t = GetParamValue("OrderID");
            if (t == null || t.StartsWith("00000"))
                return "fail";
            return "ok";
        }

        foreach (XmlElement node in root)
        {
            if (node.NodeType != XmlNodeType.Element)
                continue;
            if (node.Name.Equals(Field, StringComparison.OrdinalIgnoreCase))
                return node.InnerText;
        }
        return base.GetParamValue(Field);
    }

    protected override void WriteSuccess()
    {
        WriteResult(0);
        Response.Write("<!--OK -->");
    }

    protected override void WriteError(string msg)
    {
        //425
        WriteResult(0);
        Response.Write("<!--" + msg + " -->");
    }


    void WriteResult(int code)
    {
        var xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SyncAppOrderResp xmlns=\"http://www.monternet.com/dsmp/schemas/\"><TransactionID>"
            + GetParamValue("OrderId") + "</TransactionID>"
            + "<MsgType>SyncAppOrderResp</MsgType><Version>1.0.0</Version><hRet>"
            + code.ToString() + "</hRet></SyncAppOrderResp>";
        Response.ContentType = "text/xml";
        Response.Write(xml);
    }
}