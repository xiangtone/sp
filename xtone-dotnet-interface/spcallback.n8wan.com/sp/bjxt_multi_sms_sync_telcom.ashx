<%@ WebHandler Language="C#" Class="bjxt_multi_sms_sync_telcom" %>

using System;
using System.Web;

public class bjxt_multi_sms_sync_telcom : n8wan.Public.Logical.BaseSPCallback
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
        if (string.IsNullOrEmpty(message_type))
            return false;
        sb = new System.Text.StringBuilder();
        return base.OnInit();
    }


    protected override void StartPorcess()
    {
        System.Xml.XmlNodeList els = null;
        if (IsMo)
            els = root.SelectNodes("mo");
        else
            els = root.SelectNodes("message");

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

        return node.InnerText;
    }

}