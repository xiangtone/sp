<%@ WebHandler Language="C#" Class="gzyr_wo_phy" %>

using System;
using System.Web;

public class gzyr_wo_phy : n8wan.Public.Logical.BaseSPCallback
{
    public override string GetParamValue(string Field)
    {
        switch (Field.ToLower())
        {
            case "msg":
                var m = GetLinkId();
                var c = int.Parse(m.Substring(7, 1));
                return ((char)('A' + c)).ToString() + base.GetParamValue("fee");
            case "cpparam":
                var t = GetLinkId();
                return t.Substring(t.Length - 14, 14);
        }
        return base.GetParamValue(Field);
    }
}