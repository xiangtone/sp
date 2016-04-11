<%@ WebHandler Language="C#" Class="jj9" %>

using System;
using System.Web;
using Newtonsoft.Json.Linq;
using System.Runtime.Serialization;
/// <summary>
/// 广州瑶品 - 沃+
/// </summary>
public class jj9 : sdk_Request.Logical.APIRequestGet
{
    [DataContract]
    class step1
    {
        [DataMember]
        public int errorCode;
        [DataMember]
        public string errorDesc;

        [DataMember]
        public int resultCode;

        [DataMember]
        public string resultMsg;
        [DataMember]
        public string orderid;
    }

    //{"resultCode":200000,"resultMsg":"执行成功","orderid":"GZFT_201603031117229030","cpparam":"","appname":"消灭果冻-联通版","apptype":"uni_api","app":"3312"}

    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        switch (PayModel.channelid)
        {
            case "3192"://桌球消消
                OrderInfo.apiExdata = "0247" + OrderInfo.id.ToString();
                break;
            case "3312"://方块摆摆消
                OrderInfo.apiExdata = "0245" + OrderInfo.id.ToString();
                break;
            case "3399": //消灭彩塘
                OrderInfo.apiExdata = "0265" + OrderInfo.id.ToString();
                break;
            default:
                SetError(sdk_Request.Logical.API_ERROR.ERROR_PAY_POINT, "未知APPID：" + PayModel.appid);
                return null;
        }

        string url = string.Format("http://182.92.149.179/open_gate/web_game_fee.php?pid="
            + PayModel.appid
            + "&money=" + PayModel.paycode
            + "&app_special=" + PayModel.channelid
            + "&time=" + ((DateTime.Now.ToUniversalTime().Ticks - 621355968000000000) / 10000000).ToString()
            + "&tel=" + OrderInfo.mobile
            + "&imsi=" + OrderInfo.imsi)
            + "&cpparam=" + OrderInfo.apiExdata;

        var html = GetHTML(url);
        var rlt = ProcResult(html);
        if (rlt == null)
            return null;
        OrderInfo.spLinkId = rlt.orderid;
        return new sdk_Request.Model.SP_RESULT();
    }

    protected override sdk_Request.Model.SP_RESULT GetSpCmdStep2()
    {

        //var html = "";
        string url = "http://182.92.149.179/open_gate/web_game_callback.php?pid="
            + PayModel.appid
            + "&verifycode=" + OrderInfo.cpVerifyCode
            + "&orderid=" + OrderInfo.spLinkId
            + "&time=" + ((DateTime.Now.ToUniversalTime().Ticks - 621355968000000000) / 10000000).ToString();
        var html = GetHTML(url);
        var rlt = ProcResult(html);
        if (rlt == null)
            return null;
        return new sdk_Request.Model.SP_RESULT();

    }

    step1 ProcResult(string html)
    {
        if (string.IsNullOrEmpty(html))
        {
            SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT);
            return null;
        }
        var rlt = Shotgun.Library.Static.JsonParser<step1>(html);
        if (rlt.errorCode != 0)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, rlt.errorDesc);
            return null;
        }


        if (rlt.resultCode != 200000)
        {
            switch (rlt.resultCode)
            {
                case 910009:
                    SetError(sdk_Request.Logical.API_ERROR.VERIFY_CODE_ERROR, rlt.resultMsg);
                    return null;
                case 110401:
                case 110202:
                    SetError(sdk_Request.Logical.API_ERROR.AREA_CLOSE, rlt.resultMsg);
                    return null;
                case 110201:
                    SetError(sdk_Request.Logical.API_ERROR.BLACK_USER, rlt.resultMsg);
                    return null;
                default:
                    SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, rlt.resultMsg);
                    return null;
            }
        }
        return rlt;
    }

}
