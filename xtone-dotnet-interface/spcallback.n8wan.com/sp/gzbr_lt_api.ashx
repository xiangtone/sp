<%@ WebHandler Language="C#" Class="gzbr_lt_api" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;

/// <summary>
/// 广州百容 广东联通代码
/// </summary>
public class gzbr_lt_api : n8wan.Public.Logical.BaseSPCallback
{

    JObject jObj;
    protected override bool OnInit()
    {
        System.IO.StreamReader stm = null;
        try
        {
            stm = new System.IO.StreamReader(Request.InputStream);
            var json = stm.ReadToEnd();
            jObj = JObject.Parse(json);
        }
        catch
        {
            return false;
        }
        finally
        {
            if (stm != null)
                stm.Dispose();
        }
        return base.OnInit();
    }
    public override string GetParamValue(string Field)
    {
        if (jObj != null)
        {
            var jval = jObj[Field];
            if (jval != null)
                return jval.ToString();
        }
        return base.GetParamValue(Field);
    }

}