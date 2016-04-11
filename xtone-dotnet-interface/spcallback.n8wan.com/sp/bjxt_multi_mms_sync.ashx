<%@ WebHandler Language="C#" Class="bjxt_multi_mms_sync" %>

using System;
using System.Web;
/// <summary>
/// 北京翔通，彩信同步，一次多条
/// </summary>
public class bjxt_multi_mms_sync : n8wan.Public.Logical.BaseSPCallback
{
    System.Xml.XmlElement root;
    System.Xml.XmlElement _curEl;
    string message_type;
    System.Text.StringBuilder sb;

    protected override bool OnInit()
    {

        if (Request.TotalBytes < 10)
            return false;
        var xml = new System.Xml.XmlDocument();
        try
        {
            xml.Load(Request.InputStream);
        }
        catch { return false; }

        root = xml.DocumentElement;
        message_type = root.Name;
        sb = new System.Text.StringBuilder();
        return base.OnInit();
    }


    protected override void StartPorcess()
    {

        System.Xml.XmlNodeList els;
        if (IsMo)
            els = root.SelectNodes("message");
        else
            els = root.SelectNodes("mms");

        foreach (var node in els)
        {
            Reset();
            _curEl = (System.Xml.XmlElement)node;
            base.StartPorcess();
        }

        Response.Write("0");

    }

    protected override void WriteError(string msg)
    {
        if (sb == null)
        {
            base.WriteError(msg);
            return;
        }
        sb.AppendLine(msg);
    }

    protected override void WriteSuccess()
    {
        if (sb == null)
        {
            base.WriteSuccess();
            return;
        }
        sb.AppendLine("OK");
    }

    public override string GetParamValue(string Field)
    {
        if ("message_type".Equals(Field, StringComparison.OrdinalIgnoreCase))
            return message_type;

        if (_curEl == null)
            return null;

        var node = _curEl.SelectSingleNode(Field);
        if (node == null)
            return base.GetParamValue(Field);

        if ("subject".Equals(Field, StringComparison.OrdinalIgnoreCase))
            return Server.UrlDecode(node.InnerText);
        return node.InnerText;
    }

}