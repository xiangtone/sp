<%@ WebHandler Language="C#" Class="xml_demo" %>

using System;
using System.Web;
using System.Xml;

public class xml_demo : n8wan.Public.Logical.BaseSPCallback
{
    XmlDocument xml;
    XmlElement root;//xml根节点 
    protected override bool OnInit()
    {//加载xml
        if (Request.TotalBytes < 10)
            return false;//初始化失败

        xml = new XmlDocument();
        try
        {
            xml.Load(Request.InputStream);
        }
        catch
        {
            xml = null;
            return false;
        }
        return base.OnInit();//等效于super.OnInit();
    }


    public override string GetParamValue(string Field) //Field是后台配置的参数名,所有说明中有写"urlField"均通过此方法取值
    {
        if (root == null)
            return string.Empty;
        if (Field.ToLower() == "type")
            return root.Name; //返回根节点名称: mrRequest /moRequest
        foreach (XmlElement node in root) //相当于for(XmlElement node : root)
        {
            if (node.NodeType != XmlNodeType.Element)
                continue;//非普通元素,比如:XML的注释
            if (node.Name.Equals(Field, StringComparison.OrdinalIgnoreCase)) //节点名是否等于传入的Field
                return node.InnerText;
        }
        return base.GetParamValue(Field); //等效于super.GetParamValue(Field); 执行代码类似于Request.getParameter
        //接入xml代码,执行Request,正常情况下是没有内容返回,但,配置时使用了"VirutalPort" / "VirtualMsg"时则仍然需要调用本方法
    }

    protected override void WriteSuccess()//SP传入
    {
        WriteResult("0000");
        Response.Write("<!--OK-->");//输出xml注释内容,仅用于调试
    }

    protected override void WriteError(string msg)//SP传入的状态,为"失败",或是 linkid重复时,会调用这方法,输出错误信息给sp
    {

        WriteResult("0000");
        Response.Write("<!--" + msg + "-->");//输出xml注释内容,仅用于调试
    }

    void WriteResult(string status)
    {
        string outString = null;
        if (this.IsMo)
        {//Mo的响应
            outString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><moResponse><cpOrderNo></cpOrderNo><status>"
                + status + "</status><transactionId>" + GetLinkId() + "</transactionId></moResponse>";
        }
        else
        {//mr的响应
            outString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><mrResponse><status>"
                + status + "</status><transactionId>" + GetLinkId() + "</transactionId></mrResponse>";
        }

        Response.ContentType = "text/xml";
        Response.Write(outString);
    }


}