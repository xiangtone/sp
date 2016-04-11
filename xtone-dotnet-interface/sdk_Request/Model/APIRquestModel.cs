using sdk_Request.Logical;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace sdk_Request.Model
{
    [DataContract]
    public class APIRquestModel
    {
        /// <summary>
        /// 传给SP的透传内容
        /// </summary>
        [DataMember]
        public String apiExdata;

        /// <summary>
        /// tbl_sp_trone_api
        /// </summary>
        [DataMember(Name = "apiOrderId")]
        public String tbl_sp_trone_api_id;
        [DataMember]
        public int cid;
        /// <summary>
        /// 用户的IP（渠道传入）
        /// </summary>
        [DataMember]
        public String clientIp;
        /// <summary>
        /// 用户传入的验证码
        /// </summary>
        [DataMember]
        public String cpVerifyCode;
        /// <summary>
        /// 渠道的透传内容
        /// </summary>
        [DataMember]
        public String extrData;
        [DataMember]
        public int id;
        [DataMember]
        public String imei;
        [DataMember]
        public String imsi;
        /// <summary>
        /// 调用者IP的，非渠道传入IP
        /// </summary>
        [DataMember]
        public String ip;
        /// <summary>
        /// 扣量伪装（尚未支持）
        /// </summary>
        [DataMember]
        public int isHidden;
        [DataMember]
        public int lac;
        [DataMember]
        public String mobile;
        /// <summary>
        /// 短信上行内容 （一般由系统填充）
        /// </summary>
        [DataMember]
        public String msg;
        [DataMember]
        public String netType;
        [DataMember]
        public String packageName;
        /// <summary>
        ///短信上行端口 （一般由系统填充）
        /// </summary>
        [DataMember]
        public String port;
        [DataMember]
        public String sdkVersion;
        /// <summary>
        /// 用户存储SP回应的其它二次数据
        /// </summary>
        [DataMember]
        public String spExField;
        /// <summary>
        /// SP的订单号
        /// </summary>
        [DataMember]
        public String spLinkId;
        [DataMember]
        public int troneId;
        /// <summary>
        /// 输出状态（一般由系统填充）
        /// </summary>
        [DataMember]
        public API_ERROR status;
        [DataMember(Name = "troneOrderId")]
        public String tbl_trone_order_id;
        /// <summary>
        /// 预留的SP需求字段，可根据业务要求渠道进行传参
        /// </summary>
        [DataMember]
        public String extraParams;
    }


}
