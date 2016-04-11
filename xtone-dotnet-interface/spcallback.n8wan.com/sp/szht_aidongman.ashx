<%@ WebHandler Language="C#" Class="szht_aidongman" %>

using System;
using System.Web;
using System.Runtime.Serialization;

/// <summary>
/// 深圳浩天 － 爱动漫
/// </summary>
public class szht_aidongman : gzzyPublic.SPCallback.AutoMapCallback
{
    public override string GetParamValue(string Field)
    {
        if ("status".Equals(Field, StringComparison.OrdinalIgnoreCase))
        {
            return string.Format("{0},{1}", base.GetParamValue("behavior"), base.GetParamValue(Field));
        }
        return base.GetParamValue(Field);
    }


}