using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace LightDataModel
{
    /// <summary>
    /// tbl_tmp_mr数据模型
    /// </summary>
    public partial class tbl_tmp_mrItem : Shotgun.Model.Logical.LightDataModel
    {
        /// <summary>
        /// 数据表字段列表对像
        /// </summary>
        public sealed class Fields
        {
            private Fields() { }
            #region 表字段名称

            public const string id = "id";
            ///<summary>
            ///主键
            ///</summary>
            public const string PrimaryKey = "id";

            /// <summary>
            /// IMEI
            /// </summary>
            public const string imei = "imei";
            /// <summary>
            /// IMSI
            /// </summary>
            public const string imsi = "imsi";
            /// <summary>
            /// MOBILE
            /// </summary>
            public const string mobile = "mobile";
            /// <summary>
            /// 国家码
            /// </summary>
            public const string mcc = "mcc";
            /// <summary>
            /// 省份id
            /// </summary>
            public const string province_id = "province_id";
            /// <summary>
            /// 城市id
            /// </summary>
            public const string city_id = "city_id";
            /// <summary>
            /// 通道ID
            /// </summary>
            public const string sp_url_id = "sp_url_id";
            /// <summary>
            /// 通道指令id
            /// </summary>
            public const string trone_order_id = "trone_order_id";
            /// <summary>
            /// 原始通道
            /// </summary>
            public const string ori_trone = "ori_trone";
            /// <summary>
            /// 原始指令
            /// </summary>
            public const string ori_order = "ori_order";
            /// <summary>
            /// linkid
            /// </summary>
            public const string linkid = "linkid";
            /// <summary>
            /// CP透参参数
            /// </summary>
            public const string cp_param = "cp_param";
            /// <summary>
            /// 传SP传过来的数据
            /// </summary>
            public const string service_code = "service_code";
            /// <summary>
            /// 只作存储，真正计费采用通道费用，生成虚拟指令时需要使用
            /// </summary>
            public const string price = "price";
            /// <summary>
            /// IP
            /// </summary>
            public const string ip = "ip";
            /// <summary>
            /// 下行状态码
            /// </summary>
            public const string status = "status";
            /// <summary>
            /// 同步给CP标识，默认1为需要同步，0为不需要同步，这个数据是读取表tbl_hold_config进行分析的
            /// </summary>
            public const string syn_flag = "syn_flag";
            /// <summary>
            /// 下行日期
            /// </summary>
            public const string mr_date = "mr_date";
            /// <summary>
            /// 入库时间
            /// </summary>
            public const string create_date = "create_date";

            #endregion

        }
        #region 表字段变量定义
        /// <summary>
        /// IMEI
        /// </summary>
        private string _imei;
        /// <summary>
        /// IMSI
        /// </summary>
        private string _imsi;
        /// <summary>
        /// MOBILE
        /// </summary>
        private string _mobile;
        /// <summary>
        /// 国家码
        /// </summary>
        private string _mcc;
        /// <summary>
        /// 省份id
        /// </summary>
        private int _province_id;
        /// <summary>
        /// 城市id
        /// </summary>
        private int _city_id;
        /// <summary>
        /// 通道ID
        /// </summary>
        private int _sp_url_id;
        /// <summary>
        /// 通道指令id
        /// </summary>
        private int _trone_order_id;
        /// <summary>
        /// 原始通道
        /// </summary>
        private string _ori_trone;
        /// <summary>
        /// 原始指令
        /// </summary>
        private string _ori_order;
        /// <summary>
        /// linkid
        /// </summary>
        private string _linkid;
        /// <summary>
        /// CP透参参数
        /// </summary>
        private string _cp_param;
        /// <summary>
        /// 传SP传过来的数据
        /// </summary>
        private string _service_code;
        /// <summary>
        /// 只作存储，真正计费采用通道费用，生成虚拟指令时需要使用
        /// </summary>
        private int _price;
        /// <summary>
        /// IP
        /// </summary>
        private string _ip;
        /// <summary>
        /// 下行状态码
        /// </summary>
        private string _status;
        /// <summary>
        /// 同步给CP标识，默认1为需要同步，0为不需要同步，这个数据是读取表tbl_hold_config进行分析的
        /// </summary>
        private int _syn_flag;
        /// <summary>
        /// 下行日期
        /// </summary>
        private DateTime _mr_date;
        /// <summary>
        /// 入库时间
        /// </summary>
        private DateTime _create_date;

        #endregion

        public override string IdentifyField { get { return identifyField; } }
        public override string TableName { get { return tableName; } }

        public static readonly string identifyField = "id";
        public static readonly string tableName = "tbl_tmp_mr";


        public int id
        {
            get;
            set;
        }

        #region 表字段值存取
        /// <summary>
        /// IMEI
        /// </summary>
        public string imei
        {
            get { return this._imei; }
            set
            {
#if false && true
				RemoveNullFlag(Fields.imei);
#elif !false
                if (value == null)
                    SetNullFlag(Fields.imei);
                else
                    RemoveNullFlag(Fields.imei);
#endif

                SetFieldHasUpdate(Fields.imei, this._imei, value);
                this._imei = value;
            }
        }
        /// <summary>
        /// IMSI
        /// </summary>
        public string imsi
        {
            get { return this._imsi; }
            set
            {
#if false && true
				RemoveNullFlag(Fields.imsi);
#elif !false
                if (value == null)
                    SetNullFlag(Fields.imsi);
                else
                    RemoveNullFlag(Fields.imsi);
#endif

                SetFieldHasUpdate(Fields.imsi, this._imsi, value);
                this._imsi = value;
            }
        }
        /// <summary>
        /// MOBILE
        /// </summary>
        public string mobile
        {
            get { return this._mobile; }
            set
            {
#if false && true
				RemoveNullFlag(Fields.mobile);
#elif !false
                if (value == null)
                    SetNullFlag(Fields.mobile);
                else
                    RemoveNullFlag(Fields.mobile);
#endif

                SetFieldHasUpdate(Fields.mobile, this._mobile, value);
                this._mobile = value;
            }
        }
        /// <summary>
        /// 国家码
        /// </summary>
        public string mcc
        {
            get { return this._mcc; }
            set
            {
#if false && true
				RemoveNullFlag(Fields.mcc);
#elif !false
                if (value == null)
                    SetNullFlag(Fields.mcc);
                else
                    RemoveNullFlag(Fields.mcc);
#endif

                SetFieldHasUpdate(Fields.mcc, this._mcc, value);
                this._mcc = value;
            }
        }
        /// <summary>
        /// 省份id
        /// </summary>
        public int province_id
        {
            get { return this._province_id; }
            set
            {
#if true && true
                RemoveNullFlag(Fields.province_id);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.province_id);
                else
                    RemoveNullFlag(Fields.province_id);
#endif

                SetFieldHasUpdate(Fields.province_id, this._province_id, value);
                this._province_id = value;
            }
        }
        /// <summary>
        /// 城市id
        /// </summary>
        public int city_id
        {
            get { return this._city_id; }
            set
            {
#if true && true
                RemoveNullFlag(Fields.city_id);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.city_id);
                else
                    RemoveNullFlag(Fields.city_id);
#endif

                SetFieldHasUpdate(Fields.city_id, this._city_id, value);
                this._city_id = value;
            }
        }
        /// <summary>
        /// 通道ID
        /// </summary>
        public int sp_url_id
        {
            get { return this._sp_url_id; }
            set
            {
#if true && true
                RemoveNullFlag(Fields.sp_url_id);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.sp_url_id);
                else
                    RemoveNullFlag(Fields.sp_url_id);
#endif

                SetFieldHasUpdate(Fields.sp_url_id, this._sp_url_id, value);
                this._sp_url_id = value;
            }
        }
        /// <summary>
        /// 通道指令id
        /// </summary>
        public int trone_order_id
        {
            get { return this._trone_order_id; }
            set
            {
#if true && true
                RemoveNullFlag(Fields.trone_order_id);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.trone_order_id);
                else
                    RemoveNullFlag(Fields.trone_order_id);
#endif

                SetFieldHasUpdate(Fields.trone_order_id, this._trone_order_id, value);
                this._trone_order_id = value;
            }
        }
        /// <summary>
        /// 原始通道
        /// </summary>
        public string ori_trone
        {
            get { return this._ori_trone; }
            set
            {
#if false && true
				RemoveNullFlag(Fields.ori_trone);
#elif !false
                if (value == null)
                    SetNullFlag(Fields.ori_trone);
                else
                    RemoveNullFlag(Fields.ori_trone);
#endif

                SetFieldHasUpdate(Fields.ori_trone, this._ori_trone, value);
                this._ori_trone = value;
            }
        }
        /// <summary>
        /// 原始指令
        /// </summary>
        public string ori_order
        {
            get { return this._ori_order; }
            set
            {
#if false && true
				RemoveNullFlag(Fields.ori_order);
#elif !false
                if (value == null)
                    SetNullFlag(Fields.ori_order);
                else
                    RemoveNullFlag(Fields.ori_order);
#endif

                SetFieldHasUpdate(Fields.ori_order, this._ori_order, value);
                this._ori_order = value;
            }
        }
        /// <summary>
        /// linkid
        /// </summary>
        public string linkid
        {
            get { return this._linkid; }
            set
            {
#if false && true
				RemoveNullFlag(Fields.linkid);
#elif !false
                if (value == null)
                    SetNullFlag(Fields.linkid);
                else
                    RemoveNullFlag(Fields.linkid);
#endif

                SetFieldHasUpdate(Fields.linkid, this._linkid, value);
                this._linkid = value;
            }
        }
        /// <summary>
        /// CP透参参数
        /// </summary>
        public string cp_param
        {
            get { return this._cp_param; }
            set
            {
#if false && true
				RemoveNullFlag(Fields.cp_param);
#elif !false
                if (value == null)
                    SetNullFlag(Fields.cp_param);
                else
                    RemoveNullFlag(Fields.cp_param);
#endif

                SetFieldHasUpdate(Fields.cp_param, this._cp_param, value);
                this._cp_param = value;
            }
        }
        /// <summary>
        /// 传SP传过来的数据
        /// </summary>
        public string service_code
        {
            get { return this._service_code; }
            set
            {
#if false && true
				RemoveNullFlag(Fields.service_code);
#elif !false
                if (value == null)
                    SetNullFlag(Fields.service_code);
                else
                    RemoveNullFlag(Fields.service_code);
#endif

                SetFieldHasUpdate(Fields.service_code, this._service_code, value);
                this._service_code = value;
            }
        }
        /// <summary>
        /// 只作存储，真正计费采用通道费用，生成虚拟指令时需要使用
        /// </summary>
        public int price
        {
            get { return this._price; }
            set
            {
#if true && true
                RemoveNullFlag(Fields.price);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.price);
                else
                    RemoveNullFlag(Fields.price);
#endif

                SetFieldHasUpdate(Fields.price, this._price, value);
                this._price = value;
            }
        }
        /// <summary>
        /// IP
        /// </summary>
        public string ip
        {
            get { return this._ip; }
            set
            {
#if false && true
				RemoveNullFlag(Fields.ip);
#elif !false
                if (value == null)
                    SetNullFlag(Fields.ip);
                else
                    RemoveNullFlag(Fields.ip);
#endif

                SetFieldHasUpdate(Fields.ip, this._ip, value);
                this._ip = value;
            }
        }
        /// <summary>
        /// 下行状态码
        /// </summary>
        public string status
        {
            get { return this._status; }
            set
            {
#if false && true
				RemoveNullFlag(Fields.status);
#elif !false
                if (value == null)
                    SetNullFlag(Fields.status);
                else
                    RemoveNullFlag(Fields.status);
#endif

                SetFieldHasUpdate(Fields.status, this._status, value);
                this._status = value;
            }
        }
        /// <summary>
        /// 同步给CP标识，默认1为需要同步，0为不需要同步，这个数据是读取表tbl_hold_config进行分析的
        /// </summary>
        public int syn_flag
        {
            get { return this._syn_flag; }
            set
            {
#if true && true
                RemoveNullFlag(Fields.syn_flag);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.syn_flag);
                else
                    RemoveNullFlag(Fields.syn_flag);
#endif

                SetFieldHasUpdate(Fields.syn_flag, this._syn_flag, value);
                this._syn_flag = value;
            }
        }
        /// <summary>
        /// 下行日期
        /// </summary>
        public DateTime mr_date
        {
            get { return this._mr_date; }
            set
            {
#if true && true
                RemoveNullFlag(Fields.mr_date);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.mr_date);
                else
                    RemoveNullFlag(Fields.mr_date);
#endif

                SetFieldHasUpdate(Fields.mr_date, this._mr_date, value);
                this._mr_date = value;
            }
        }
        /// <summary>
        /// 入库时间
        /// </summary>
        public DateTime create_date
        {
            get { return this._create_date; }
            set
            {
#if true && false
				RemoveNullFlag(Fields.create_date);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.create_date);
                else
                    RemoveNullFlag(Fields.create_date);
#endif

                SetFieldHasUpdate(Fields.create_date, this._create_date, value);
                this._create_date = value;
            }
        }

        #endregion
        #region 空值相关方法
        protected override string[] GetNullableFields()
        {
            return new string[]{null
			,"imei"
,"imsi"
,"mobile"
,"mcc"
,"province_id"
,"city_id"
,"sp_url_id"
,"trone_order_id"
,"ori_trone"
,"ori_order"
,"linkid"
,"cp_param"
,"service_code"
,"price"
,"ip"
,"status"
,"syn_flag"
,"mr_date"
};
        }
        public bool IsimeiNull() { return IsNull(Fields.imei); }

        public void SetimeiNull() { SetNull(Fields.imei); }
        public bool IsimsiNull() { return IsNull(Fields.imsi); }

        public void SetimsiNull() { SetNull(Fields.imsi); }
        public bool IsmobileNull() { return IsNull(Fields.mobile); }

        public void SetmobileNull() { SetNull(Fields.mobile); }
        public bool IsmccNull() { return IsNull(Fields.mcc); }

        public void SetmccNull() { SetNull(Fields.mcc); }
        public bool Isprovince_idNull() { return IsNull(Fields.province_id); }

        public void Setprovince_idNull() { SetNull(Fields.province_id); }
        public bool Iscity_idNull() { return IsNull(Fields.city_id); }

        public void Setcity_idNull() { SetNull(Fields.city_id); }
        public bool Issp_url_idNull() { return IsNull(Fields.sp_url_id); }

        public void Setsp_url_idNull() { SetNull(Fields.sp_url_id); }
        public bool Istrone_order_idNull() { return IsNull(Fields.trone_order_id); }

        public void Settrone_order_idNull() { SetNull(Fields.trone_order_id); }
        public bool Isori_troneNull() { return IsNull(Fields.ori_trone); }

        public void Setori_troneNull() { SetNull(Fields.ori_trone); }
        public bool Isori_orderNull() { return IsNull(Fields.ori_order); }

        public void Setori_orderNull() { SetNull(Fields.ori_order); }
        public bool IslinkidNull() { return IsNull(Fields.linkid); }

        public void SetlinkidNull() { SetNull(Fields.linkid); }
        public bool Iscp_paramNull() { return IsNull(Fields.cp_param); }

        public void Setcp_paramNull() { SetNull(Fields.cp_param); }
        public bool Isservice_codeNull() { return IsNull(Fields.service_code); }

        public void Setservice_codeNull() { SetNull(Fields.service_code); }
        public bool IspriceNull() { return IsNull(Fields.price); }

        public void SetpriceNull() { SetNull(Fields.price); }
        public bool IsipNull() { return IsNull(Fields.ip); }

        public void SetipNull() { SetNull(Fields.ip); }
        public bool IsstatusNull() { return IsNull(Fields.status); }

        public void SetstatusNull() { SetNull(Fields.status); }
        public bool Issyn_flagNull() { return IsNull(Fields.syn_flag); }

        public void Setsyn_flagNull() { SetNull(Fields.syn_flag); }
        public bool Ismr_dateNull() { return IsNull(Fields.mr_date); }

        public void Setmr_dateNull() { SetNull(Fields.mr_date); }

        #endregion
        #region 静态方法
        /// <summary>
        /// 创建一个新的查询器
        /// </summary>
        public static Shotgun.Model.List.LightDataQueries<tbl_tmp_mrItem> GetQueries(Shotgun.Database.IBaseDataClass2 dBase)
        {
            return new Shotgun.Model.List.LightDataQueries<tbl_tmp_mrItem>(tableName, identifyField, dBase);
        }

        /// <summary>
        /// 创建一个新的查询器
        /// </summary>
        public static Shotgun.Model.List.LightDataQueries<tbl_tmp_mrItem> GetQueries()
        {
            return new Shotgun.Model.List.LightDataQueries<tbl_tmp_mrItem>(tableName, identifyField);
        }
        /// <summary>
        /// 根据主键查找指定的行,返回指定字段
        /// </summary>
        /// <param name="dBase"></param>
        /// <param name="id">主键值</param>
        /// <param name="fields">返回字段</param>
        /// <returns></returns>
        public static tbl_tmp_mrItem GetRowById(Shotgun.Database.IBaseDataClass2 dBase, int id, string[] fields)
        {
            var q = GetQueries(dBase);
            q.Fields = fields;
            q.Filter.AndFilters.Add(identifyField, id);
            return q.GetRowByFilters();
        }

        /// <summary>
        /// 根据主键查找指定的行,返回所有字段
        /// </summary>
        /// <param name="dBase"></param>
        /// <param name="id">主键值</param>
        /// <returns></returns>
        public static tbl_tmp_mrItem GetRowById(Shotgun.Database.IBaseDataClass2 dBase, int id)
        {
            return GetRowById(dBase, id, null);
        }

        #endregion
    }
}