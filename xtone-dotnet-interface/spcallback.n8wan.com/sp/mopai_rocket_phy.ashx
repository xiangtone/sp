<%@ WebHandler Language="C#" Class="mopai_sms_std" %>

using System;
using System.Web;

public class mopai_sms_std : gzzyPublic.SPCallback.AutoMapCallback
{

    public override string GetParamValue(string Field)
    {
        switch (Field)
        {
            case "msg":
                var msg = Request["CPParam"];
                if (!string.IsNullOrEmpty(msg) || msg.Length > 4)
                    return msg.Substring(4);
                return null;
            case "servicecode":
                var sc = Request["CPParam"];
                if (!string.IsNullOrEmpty(sc) || sc.Length > 4)
                    return sc.Substring(0, 4);
                return null;
            case "price":
                var p = Request["msg"];
                switch (p)
                {
                    case "30000914242402": return "100";
                    case "30000914242403": return "200";
                    case "30000914242404": return "400";
                    case "30000914242405": return "600";
                    case "30000914242406": return "800";
                    case "30000914242407": return "1000";
                    case "30000914242408": return "1500";
                    case "30000914242409": return "2000";
                }
                break;

        }
        return base.GetParamValue(Field);
    }

}