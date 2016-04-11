<%@ WebHandler Language="C#" Class="bjdx_linkid_cx" %>

using System;
using System.Web;

public class bjdx_linkid_cx : n8wan.Public.Logical.BaseSPCallback
{

    public override string GetParamValue(string Field)
    {
        if (Field.Equals("linkid"))
        {


            var linkid = GetParamValue("mobile") + GetParamValue("chargeTime");
            linkid = linkid.Replace("-", "");
            linkid = linkid.Replace(" ", "");
            linkid = linkid.Replace(":", "");

            return linkid;
        }

        return base.GetParamValue(Field);
    }


}