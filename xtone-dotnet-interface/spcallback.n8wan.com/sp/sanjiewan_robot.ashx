<%@ WebHandler Language="C#" Class="sanjiewan_robot" %>

using System;
using System.Web;
/// <summary>
/// 机器人地洞冒险
/// </summary>
public class sanjiewan_robot : n8wan.Public.Logical.BaseSPCallback
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
                    case "30000919040501": return "800";//血瓶购买（小）
                    case "30000919040502": return "1000";//血瓶购买（中）
                    case "30000919040503": return "1200";//血瓶购买（大）
                    case "30000919040504": return "1500";//积分购买（小）
                    case "30000919040505": return "2000";//积分购买（中）
                    case "30000919040506": return "3000";//积分购买（大）
                    case "30000919040507": return "10";//打赏作者
                }
                break;
        }
        return base.GetParamValue(Field);
    }
}