<%@ WebHandler Language="C#" Class="mingtiandongli_ii_phy" %>

using System;
using System.Web;

/// <summary>
/// 明天动力，同步接入（使用深圳创世互动的外壳对接）
/// </summary>
public class mingtiandongli_ii_phy : n8wan.Public.Logical.BaseSPCallback
{
    /// <summary>
    /// 经过处理的参数信息
    /// </summary>
    string _linkId = null, _cpp, _msg;

    public override string GetParamValue(string Field)
    {
        switch (Field.ToLower())
        {
            //case "linkid":
            //    return _linkId;
            case "mobile":
                var m = Request["mobile"];
                if (string.IsNullOrEmpty(m))
                    m = Request["imei"];
                return m;
            case "msg":
                return _msg;
            case "cpparams":
                return _cpp;
        }
        return base.GetParamValue(Field);//super.GetParamValue
    }


    protected override bool OnInit()
    {
        var channelNum = Request["channelNum"];
        var appId = Request["appId"];
        var cpp = Request["cpparams"];
        if (string.IsNullOrEmpty(channelNum) || string.IsNullOrEmpty(cpp))
            return false;

        var price = int.Parse(Request["price"]) / 100;

        var i = cpp.LastIndexOf('#');
        if (i != -1)
        {
            _msg = cpp.Substring(0, i);
            _cpp = cpp.Substring(i + 1);
        }
        else
        {
            _cpp = cpp;
        }

        _msg = channelNum + appId + price.ToString("00") + cpp.Substring(0, 4);

        _linkId = string.Format("{0}{1}{2:00}{3}", channelNum, appId, price, _cpp);

        return true;

    }

}