<%@ WebHandler Language="C#" Class="yijian_rdo" %>

using System;
using System.Web;
using System.Runtime.Serialization;


public class yijian_rdo : gzzyPublic.SPCallback.AutoMapCallback
{
    [DataContract]
    class reuslt
    {
        [DataMember]
        public string orderId;// "123456789",
        [DataMember]
        public string sequence;// "234567890",
        [DataMember]
        public string appId;// "1234",
        [DataMember]
        public string imsi;// "1234567890123456",
        [DataMember]
        public string provinceId;// "01",
        [DataMember]
        public string channelId;// "000",
        [DataMember]
        public string serviceType;// "CG_245ZZL_100",
        [DataMember]
        public string money;// 1000,
        [DataMember]
        public int status;// 1,
        [DataMember]
        public string extData;// "some message",
        [DataMember]
        public string resultMsg;// "请求成功",
        [DataMember]
        public string feetime;// "20150801100530"
    }

    reuslt rlt;

    protected override bool OnInit()
    {

        if (Request.TotalBytes == 0)
            return false;
        rlt = Shotgun.Library.Static.JsonParser<reuslt>(Request.InputStream);
        if (rlt == null)
            return false;

        return base.OnInit();
    }

    public override string GetParamValue(string Field)
    {
        if (rlt == null)
            return null;
        switch (Field.ToLower())
        {
            case "appid": return rlt.appId;
            case "orderid": return rlt.orderId;
            case "channelid": return rlt.channelId;
            case "money": return rlt.money;
            case "imsi": return rlt.imsi;
            //case "imei": return rlt.imei;
            case "sequence": return rlt.sequence;
            case "provinceid": return rlt.provinceId;
            case "servicetype": return rlt.serviceType;
            case "extdata": return rlt.extData;
            case "status": return rlt.status.ToString();
            case "feetime": return rlt.feetime;
        }
        return base.GetParamValue(Field);
    }


}