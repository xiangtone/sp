<%@ WebHandler Language="C#" Class="shsy_dxtbdz" %>

using System;
using System.Web;

public class shsy_dxtbdz : n8wan.Public.Logical.BaseSPCallback
{
    protected override void FillAreaInfo(LightDataModel.tbl_mrItem m)
    {

        base.FillAreaInfo(m);
        if (m.province_id != 32)
            return;

        //var city = GetParamValue("areaname");
        var pro = GetParamValue("province");
        if (string.IsNullOrEmpty(pro) || "unkonw".Equals(pro))
            return;
        switch (pro)
        {
            case "安徽": m.province_id = 3; break;
            case "北京": m.province_id = 1; break;
            case "福建": m.province_id = 21; break;
            case "甘肃": m.province_id = 11; break;
            case "广东": m.province_id = 8; break;
            case "广西": m.province_id = 10; break;
            case "贵州": m.province_id = 28; break;
            case "海南": m.province_id = 22; break;
            case "河北": m.province_id = 17; break;
            case "河南": m.province_id = 26; break;
            case "黑龙江": m.province_id = 16; break;
            case "湖北": m.province_id = 4; break;
            case "湖南": m.province_id = 25; break;
            case "吉林": m.province_id = 12; break;
            case "江苏": m.province_id = 2; break;
            case "江西": m.province_id = 23; break;
            case "辽宁": m.province_id = 13; break;
            case "内蒙古": m.province_id = 14; break;
            case "宁夏": m.province_id = 29; break;
            case "青海": m.province_id = 27; break;
            case "山东": m.province_id = 6; break;
            case "山西": m.province_id = 24; break;
            case "陕西": m.province_id = 20; break;
            case "上海": m.province_id = 7; break;
            case "四川": m.province_id = 19; break;
            case "天津": m.province_id = 5; break;
            case "西藏": m.province_id = 31; break;
            case "新疆": m.province_id = 15; break;
            case "云南": m.province_id = 30; break;
            case "浙江": m.province_id = 9; break;
            case "重庆": m.province_id = 18; break;
        }


    }
}