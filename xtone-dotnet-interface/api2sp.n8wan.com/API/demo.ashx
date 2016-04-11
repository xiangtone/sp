<%@ WebHandler Language="C#" Class="demo" %>

using System;
using System.Web;

public class demo : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        // this.PayModel; 这个是后台配置的计费点
        //this.OrderInfo  这个对应于tbl_api_order ,主要是渠道的请求信息，如imsi,imei,ip,mobile等

        //sp 数据回传保存
        // this.OrderInfo.apiExdata 这个是我们传给SP的透传内容 (赋值后自动存储)
        // this.OrderInfo.spLinkId 请求时指令，SP回传的订单号 (赋值后自动存储)
        // this.OrderInfo.spExField 扩展的SP，回传存储，为多次HTTP预留 (赋值后自动存储)

        //this.OrderInfo.port 上行端口 无需赋值，根据反回值，自动处理 
        //this.OrderInfo.msg 上行指令 无需赋值，根据反回值，自动处理 

        var sms = new sdk_Request.Model.SP_SMS_Result();//表示单短信
        sms.port = "10086";
        sms.msg = "YE";
        sms.SMSType = sdk_Request.Logical.E_SMS_TYPE.Text; //短信类型，普通短信或是数据短信。默认值：普通短信
        //return sms;//返回一个一次短信的对像

        //一次http 两次短信的情况
        //var sms2 = new sdk_Request.Model.SP_2SMS_Result();//表示一次http，需要送两条短信方式
        //sms2.port = "10086";
        //sms2.msg = "YE";
        //sms2.port2 = "1008611";
        //sms2.msg2 = "0000";
        //sms2.interval = 3; //两条短信间隔时间(秒)
        //sms2.SMSType = sdk_Request.Logical.E_SMS_TYPE.Text; //短信类型，普通短信或是数据短信(使用base64传送)
        //return sms2;


        //如果SP错误，转发错误信息
        var sp_error = true;
        if (sp_error)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL); //无文字说明的错误
            //SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, "SP错误原因"); //有文字说明的错误
            //SetError(sdk_Request.Logical.API_ERROR.GATEWAY_TIMEOUT, "SP网关超时");
            return null;
        }


        //获取SP数据
        string html;
        try
        {
            html = GetHTML("http://baidu.com"); //自动日志记录 保存在站点根目录 splog中
           // html = PostHTML("http://baidu.com", "wd=aabc"); //自动日志记录 保存在站点根目录 splog中
        }
        catch (System.Net.WebException ex)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, ex.Message);
            return null;
        }

        return sms;


    }
}