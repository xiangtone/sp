<%@ WebHandler Language="C#" Class="shanghaiyouyou_rdo" %>

using System;
using System.Web;

public class shanghaiyouyou_rdo : n8wan.Public.Logical.BaseSPCallback
{
    public override string GetParamValue(string Field)
    {
        if ("codeid".Equals(Field, StringComparison.OrdinalIgnoreCase))
            return base.GetParamValue(Field) + base.GetParamValue("para");
        return base.GetParamValue(Field);
    }
}