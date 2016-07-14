using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace LightDataModel
{
    /// <summary>
    /// tbl_sp_trone数据模型
    /// </summary>
    public partial class tbl_sp_troneItem : Shotgun.Model.Logical.LightDataModel
    {
        /// <summary>
        /// 数据表字段列表对像
        /// </summary>
        public sealed class Fields
        {
            private Fields() { }
            #region 表字段名称
            /// <summary>
            /// SP总通道表
            /// </summary>
            public const string id = "id";
            ///<summary>
            ///主键
            ///</summary>
            public const string PrimaryKey = "id";


            public const string sp_id = "sp_id";
            /// <summary>
            /// 名称
            /// </summary>
            public const string name = "name";
            /// <summary>
            /// 运营商
            /// </summary>
            public const string @operator = "operator";
            /// <summary>
            /// 产品ID，默认是 短信-短信
            /// </summary>
            public const string product_id = "product_id";
            /// <summary>
            /// 业务类型，0为默认通道，1为包月，2为IVR
            /// </summary>
            public const string trone_type = "trone_type";
            /// <summary>
            /// 0对公周结；1对公双周结；2对公月结；3对私周结；4对私双周结；5对私月结
            /// </summary>
            public const string js_type = "js_type";
            /// <summary>
            /// 结算率
            /// </summary>
            public const string jiesuanlv = "jiesuanlv";
            /// <summary>
            /// 省份
            /// </summary>
            public const string provinces = "provinces";
            /// <summary>
            /// 日限
            /// </summary>
            public const string day_limit = "day_limit";
            /// <summary>
            /// 月限
            /// </summary>
            public const string month_limit = "month_limit";
            /// <summary>
            /// 用户日限
            /// </summary>
            public const string user_day_limit = "user_day_limit";
            /// <summary>
            /// 用户月限
            /// </summary>
            public const string user_month_limit = "user_month_limit";
            /// <summary>
            /// tbl_sp_trone_api.id
            /// </summary>
            public const string trone_api_id = "trone_api_id";
            /// <summary>
            /// 0停用1启用
            /// </summary>
            public const string status = "status";

            public const string create_date = "create_date";

            #endregion

        }
        #region 表字段变量定义

        private int _sp_id;
        /// <summary>
        /// 名称
        /// </summary>
        private string _name;
        /// <summary>
        /// 运营商
        /// </summary>
        private int _operator;
        /// <summary>
        /// 产品ID，默认是 短信-短信
        /// </summary>
        private int _product_id;
        /// <summary>
        /// 业务类型，0为默认通道，1为包月，2为IVR
        /// </summary>
        private short _trone_type;
        /// <summary>
        /// 0对公周结；1对公双周结；2对公月结；3对私周结；4对私双周结；5对私月结
        /// </summary>
        private int _js_type;
        /// <summary>
        /// 结算率
        /// </summary>
        private Decimal _jiesuanlv;
        /// <summary>
        /// 省份
        /// </summary>
        private string _provinces;
        /// <summary>
        /// 日限
        /// </summary>
        private Decimal _day_limit;
        /// <summary>
        /// 月限
        /// </summary>
        private Decimal _month_limit;
        /// <summary>
        /// 用户日限
        /// </summary>
        private Decimal _user_day_limit;
        /// <summary>
        /// 用户月限
        /// </summary>
        private Decimal _user_month_limit;
        /// <summary>
        /// tbl_sp_trone_api.id
        /// </summary>
        private int _trone_api_id;
        /// <summary>
        /// 0停用1启用
        /// </summary>
        private short _status;

        private DateTime _create_date;

        #endregion

        public override string IdentifyField { get { return identifyField; } }
        public override string TableName { get { return tableName; } }

        public static readonly string identifyField = "id";
        public static readonly string tableName = "tbl_sp_trone";

        /// <summary>
        /// SP总通道表
        /// </summary>
        public int id
        {
            get;
            set;
        }

        #region 表字段值存取

        public int sp_id
        {
            get { return this._sp_id; }
            set
            {
#if true && true
                RemoveNullFlag(Fields.sp_id);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.sp_id);
                else
                    RemoveNullFlag(Fields.sp_id);
#endif

                SetFieldHasUpdate(Fields.sp_id, this._sp_id, value);
                this._sp_id = value;
            }
        }
        /// <summary>
        /// 名称
        /// </summary>
        public string name
        {
            get { return this._name; }
            set
            {
#if false && true
				RemoveNullFlag(Fields.name);
#elif !false
                if (value == null)
                    SetNullFlag(Fields.name);
                else
                    RemoveNullFlag(Fields.name);
#endif

                SetFieldHasUpdate(Fields.name, this._name, value);
                this._name = value;
            }
        }
        /// <summary>
        /// 运营商
        /// </summary>
        public int @operator
        {
            get { return this._operator; }
            set
            {
#if true && true
                RemoveNullFlag(Fields.@operator);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.@operator);
                else
                    RemoveNullFlag(Fields.@operator);
#endif

                SetFieldHasUpdate(Fields.@operator, this._operator, value);
                this._operator = value;
            }
        }
        /// <summary>
        /// 产品ID，默认是 短信-短信
        /// </summary>
        public int product_id
        {
            get { return this._product_id; }
            set
            {
#if true && true
                RemoveNullFlag(Fields.product_id);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.product_id);
                else
                    RemoveNullFlag(Fields.product_id);
#endif

                SetFieldHasUpdate(Fields.product_id, this._product_id, value);
                this._product_id = value;
            }
        }
        /// <summary>
        /// 业务类型，0为默认通道，1为包月，2为IVR
        /// </summary>
        public short trone_type
        {
            get { return this._trone_type; }
            set
            {
#if true && true
                RemoveNullFlag(Fields.trone_type);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.trone_type);
                else
                    RemoveNullFlag(Fields.trone_type);
#endif

                SetFieldHasUpdate(Fields.trone_type, this._trone_type, value);
                this._trone_type = value;
            }
        }
        /// <summary>
        /// 0对公周结；1对公双周结；2对公月结；3对私周结；4对私双周结；5对私月结
        /// </summary>
        public int js_type
        {
            get { return this._js_type; }
            set
            {
#if true && true
                RemoveNullFlag(Fields.js_type);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.js_type);
                else
                    RemoveNullFlag(Fields.js_type);
#endif

                SetFieldHasUpdate(Fields.js_type, this._js_type, value);
                this._js_type = value;
            }
        }
        /// <summary>
        /// 结算率
        /// </summary>
        public Decimal jiesuanlv
        {
            get { return this._jiesuanlv; }
            set
            {
#if true && true
                RemoveNullFlag(Fields.jiesuanlv);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.jiesuanlv);
                else
                    RemoveNullFlag(Fields.jiesuanlv);
#endif

                SetFieldHasUpdate(Fields.jiesuanlv, this._jiesuanlv, value);
                this._jiesuanlv = value;
            }
        }
        /// <summary>
        /// 省份
        /// </summary>
        public string provinces
        {
            get { return this._provinces; }
            set
            {
#if false && true
				RemoveNullFlag(Fields.provinces);
#elif !false
                if (value == null)
                    SetNullFlag(Fields.provinces);
                else
                    RemoveNullFlag(Fields.provinces);
#endif

                SetFieldHasUpdate(Fields.provinces, this._provinces, value);
                this._provinces = value;
            }
        }
        /// <summary>
        /// 日限
        /// </summary>
        public Decimal day_limit
        {
            get { return this._day_limit; }
            set
            {
#if true && true
                RemoveNullFlag(Fields.day_limit);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.day_limit);
                else
                    RemoveNullFlag(Fields.day_limit);
#endif

                SetFieldHasUpdate(Fields.day_limit, this._day_limit, value);
                this._day_limit = value;
            }
        }
        /// <summary>
        /// 月限
        /// </summary>
        public Decimal month_limit
        {
            get { return this._month_limit; }
            set
            {
#if true && true
                RemoveNullFlag(Fields.month_limit);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.month_limit);
                else
                    RemoveNullFlag(Fields.month_limit);
#endif

                SetFieldHasUpdate(Fields.month_limit, this._month_limit, value);
                this._month_limit = value;
            }
        }
        /// <summary>
        /// 用户日限
        /// </summary>
        public Decimal user_day_limit
        {
            get { return this._user_day_limit; }
            set
            {
#if true && true
                RemoveNullFlag(Fields.user_day_limit);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.user_day_limit);
                else
                    RemoveNullFlag(Fields.user_day_limit);
#endif

                SetFieldHasUpdate(Fields.user_day_limit, this._user_day_limit, value);
                this._user_day_limit = value;
            }
        }
        /// <summary>
        /// 用户月限
        /// </summary>
        public Decimal user_month_limit
        {
            get { return this._user_month_limit; }
            set
            {
#if true && true
                RemoveNullFlag(Fields.user_month_limit);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.user_month_limit);
                else
                    RemoveNullFlag(Fields.user_month_limit);
#endif

                SetFieldHasUpdate(Fields.user_month_limit, this._user_month_limit, value);
                this._user_month_limit = value;
            }
        }
        /// <summary>
        /// tbl_sp_trone_api.id
        /// </summary>
        public int trone_api_id
        {
            get { return this._trone_api_id; }
            set
            {
#if true && true
                RemoveNullFlag(Fields.trone_api_id);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.trone_api_id);
                else
                    RemoveNullFlag(Fields.trone_api_id);
#endif

                SetFieldHasUpdate(Fields.trone_api_id, this._trone_api_id, value);
                this._trone_api_id = value;
            }
        }
        /// <summary>
        /// 0停用1启用
        /// </summary>
        public short status
        {
            get { return this._status; }
            set
            {
#if true && true
                RemoveNullFlag(Fields.status);
#elif !true
			    if (value == null)
                    SetNullFlag(Fields.status);
                else
                    RemoveNullFlag(Fields.status);
#endif

                SetFieldHasUpdate(Fields.status, this._status, value);
                this._status = value;
            }
        }

        public DateTime create_date
        {
            get { return this._create_date; }
            set
            {
#if true && true
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
			,"sp_id"
,"name"
,"operator"
,"product_id"
,"trone_type"
,"js_type"
,"jiesuanlv"
,"provinces"
,"day_limit"
,"month_limit"
,"user_day_limit"
,"user_month_limit"
,"trone_api_id"
,"status"
,"create_date"
};
        }
        public bool Issp_idNull() { return IsNull(Fields.sp_id); }

        public void Setsp_idNull() { SetNull(Fields.sp_id); }
        public bool IsnameNull() { return IsNull(Fields.name); }

        public void SetnameNull() { SetNull(Fields.name); }
        public bool IsoperatorNull() { return IsNull(Fields.@operator); }

        public void SetoperatorNull() { SetNull(Fields.@operator); }
        public bool Isproduct_idNull() { return IsNull(Fields.product_id); }

        public void Setproduct_idNull() { SetNull(Fields.product_id); }
        public bool Istrone_typeNull() { return IsNull(Fields.trone_type); }

        public void Settrone_typeNull() { SetNull(Fields.trone_type); }
        public bool Isjs_typeNull() { return IsNull(Fields.js_type); }

        public void Setjs_typeNull() { SetNull(Fields.js_type); }
        public bool IsjiesuanlvNull() { return IsNull(Fields.jiesuanlv); }

        public void SetjiesuanlvNull() { SetNull(Fields.jiesuanlv); }
        public bool IsprovincesNull() { return IsNull(Fields.provinces); }

        public void SetprovincesNull() { SetNull(Fields.provinces); }
        public bool Isday_limitNull() { return IsNull(Fields.day_limit); }

        public void Setday_limitNull() { SetNull(Fields.day_limit); }
        public bool Ismonth_limitNull() { return IsNull(Fields.month_limit); }

        public void Setmonth_limitNull() { SetNull(Fields.month_limit); }
        public bool Isuser_day_limitNull() { return IsNull(Fields.user_day_limit); }

        public void Setuser_day_limitNull() { SetNull(Fields.user_day_limit); }
        public bool Isuser_month_limitNull() { return IsNull(Fields.user_month_limit); }

        public void Setuser_month_limitNull() { SetNull(Fields.user_month_limit); }
        public bool Istrone_api_idNull() { return IsNull(Fields.trone_api_id); }

        public void Settrone_api_idNull() { SetNull(Fields.trone_api_id); }
        public bool IsstatusNull() { return IsNull(Fields.status); }

        public void SetstatusNull() { SetNull(Fields.status); }
        public bool Iscreate_dateNull() { return IsNull(Fields.create_date); }

        public void Setcreate_dateNull() { SetNull(Fields.create_date); }

        #endregion
        #region 静态方法
        /// <summary>
        /// 创建一个新的查询器
        /// </summary>
        public static Shotgun.Model.List.LightDataQueries<tbl_sp_troneItem> GetQueries(Shotgun.Database.IBaseDataClass2 dBase)
        {
            return new Shotgun.Model.List.LightDataQueries<tbl_sp_troneItem>(tableName, identifyField, dBase);
        }

        /// <summary>
        /// 创建一个新的查询器
        /// </summary>
        public static Shotgun.Model.List.LightDataQueries<tbl_sp_troneItem> GetQueries()
        {
            return new Shotgun.Model.List.LightDataQueries<tbl_sp_troneItem>(tableName, identifyField);
        }


        /// <summary>
        /// 根据主键查找指定的行,返回所有字段
        /// </summary>
        /// <param name="dBase"></param>
        /// <param name="id">主键值</param>
        /// <returns></returns>
        public static tbl_sp_troneItem GetRowById(Shotgun.Database.IBaseDataClass2 dBase, int id)
        {
            return GetRowById(dBase, id, null);
        }

        #endregion
    }
}