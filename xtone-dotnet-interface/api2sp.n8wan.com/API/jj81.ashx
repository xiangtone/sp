<%@ WebHandler Language="C#" Class="jj81" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
/// 幽幽---RDO
/// </summary>
public class jj81 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string url = "http://106.3.46.104/rdo/rdobycp/network/getmo_nw.php?codeid=" + PayModel.paycode + "&number=" + OrderInfo.mobile + "&ex="+OrderInfo.id ;
        var html = GetHTML(url, 10000, null);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        if (html != null && html != "ok")
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        OrderInfo.apiExdata = OrderInfo.id.ToString();
        return new sdk_Request.Model.SP_RESULT();
    }
  
}