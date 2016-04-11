<%@ WebHandler Language="C#" Class="fc_pc_xml" %>

using System;
using System.Web;
using System.Xml;

/// <summary>
/// 安徽英凰 - pc 同步代码
/// </summary>
public class fc_pc_xml : n8wan.Public.Logical.BaseSPCallback
{
    XmlElement root;

    protected override bool OnInit()
    {
        if (Request.InputStream.Length < 10)
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
            if (node.Name.Equals(Field, StringComparison.OrdinalIgnoreCase))
            {
                //if (!Field.Equals("cpparam", StringComparison.OrdinalIgnoreCase) || node.InnerText.Length<=3)
                return node.InnerText;
                //return node.InnerText.Substring(1, node.Value.Length - 3);
            }
        }

        return base.GetParamValue(Field);
    }

}