<%@ WebHandler Language="C#" Class="mopai_eye_std" %>

using System;
using System.Web;

public class mopai_eye_std : gzzyPublic.SPCallback.AutoMapCallback
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
                    case "30000894958301": return "10";//激活游戏
                    case "30000894958302": return "100";//放大镜
                    case "30000894958303": return "1000";//时间静止器
                    case "30000894958304": return "1500";//拼图提示
                    case "30000894958305": return "2000";//大礼包
                    case "30000894958306": return "1";//大礼包
                }
                break;
        }
        return base.GetParamValue(Field);
    }

}