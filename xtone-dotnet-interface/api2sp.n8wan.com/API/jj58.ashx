<%@ WebHandler Language="C#" Class="jj58" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
using Newtonsoft.Json;
/// <summary>
/// 掌付-视频
/// </summary>
public class jj58 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string url = "http://101.201.148.234/hiwork_jffz_v20/interface/init.php?t=" + PayModel.paycode + "&sid=" + PayModel.appid + "&cid=" + PayModel.channelid +
            "&serial=" +PayModel.appid+PayModel.paycode+ OrderInfo.id + "&imsi=" + OrderInfo.imsi + "&imei=" + OrderInfo.imei + "&phone=" + OrderInfo.mobile;
        var html = GetHTML(url, 10000, null);
        var jobj = JObject.Parse(html);
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var jval = jobj["hRet"];
        if (jval != null && jval.Value<int>() != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        OrderInfo.apiExdata ="0278"+ PayModel.appid + PayModel.paycode + OrderInfo.id;
        SetError(sdk_Request.Logical.API_ERROR.OK);
        return null;
        
    }
  
}