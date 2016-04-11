<%@ WebHandler Language="C#" Class="sanjiewan_digi" %>

using System;
using System.Web;
/// <summary>
/// 进化！数码宝贝！
/// </summary>
public class sanjiewan_digi : gzzyPublic.SPCallback.AutoMapCallback
{
    public override string GetParamValue(string Field)
    {
        switch (Field)
        {
            case "msg":
                var msg = Request["CPParam"];
                if (!string.IsNullOrEmpty(msg) && msg.Length > 4)
                    return msg.Substring(4);
                return null;
            case "servicecode":
                return Request["pid"];
            case "price":
                var p = Request["msg"];
                switch (p)
                {
                    case "30000919041001": return "10";//复活药剂
                    case "30000919041002": return "1000";//小?时间延长药剂
                    case "30000919041003": return "1200";//中?时间延长药剂
                    case "30000919041004": return "1500";//大?时间延长药剂
                    case "30000919041005": return "2000";//真?时间延长药剂
                }
                break;
        }
        return base.GetParamValue(Field);
    }
}