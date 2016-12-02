<%@ WebHandler Language="C#" Class="test" %>

using System;
using System.Web;

public class test : IHttpHandler
{

    public void ProcessRequest(HttpContext context)
    {
        context.Response.ContentType = "text/plain";
        context.Response.Write(n8wan.Public.Logical.CityToPhone.GetVirtualPhone("30800138000", 52, 0));
    }

    public bool IsReusable
    {
        get
        {
            return false;
        }
    }

}