﻿#if !DEBUG
#define DB_LOG_RECORD
#endif
using Shotgun.Model.Logical;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading;
using System.Web;
using System.Web.UI;

namespace n8wan.Public.Logical
{
    public partial class BaseSPCallback : Shotgun.PagePlus.SimpleHttpHandler<Shotgun.Database.MySqlDBClass>
    {
        const string C_VIRTUAL_PORT = "virtualport";
        const string C_VIRTUAL_MSG = "virtualmsg";
        const string C_IVR_TIME = "ivr_time";
        const string C_STATUS_KEYWORD = "status|DELIVRD|fail|success|succ|ok";//状态关键字检测


        /// <summary>
        /// url映射数据库字段(sql,url)
        /// 注意，key统一用小写
        /// </summary>
        Dictionary<string, string> U2DMap;

        LightDataModel.tbl_sp_api_urlItem api;
        /// <summary>
        /// 0:未知,1:MO,other:Mr
        /// </summary>
        private int _IsMo;
        private string _linkId;
        private LightDataModel.tbl_moItem _MoItem;
        private LightDataModel.tbl_mrItem _MrItem;
        private LightDataModel.tbl_request_logItem dblog;

        /// <summary>
        /// 保存正在处理的linkid，防止SP快速同步时，出重复数据
        /// </summary>
        static List<string> _linkidProcing = new List<string>();

        protected virtual bool OnInit() { return true; }

        /// <summary>
        /// 表示当前文件是否为物理文件
        /// </summary>
        private bool IsPhyFile { get; set; }

        public sealed override void BeginProcess()
        {

            var ua = Request.UserAgent;
            if (!string.IsNullOrEmpty(ua))
            {
                if (ua.Contains("Alibaba.Security.Heimdall"))
                    throw new System.Web.HttpException(410, "Not Welcome");
            }

            try
            {
                WriteDebug("Begin");
                if (!IsPhyFile && System.IO.File.Exists(Request.PhysicalPath))
                {///存在同名ashx文件，实为执行 ashx的方案
                    ExecuteFile();
                    return;
                }
                Shotgun.Database.IBaseDataPerformance dfp = (Shotgun.Database.IBaseDataPerformance)dBase;
                //dfp.EnableRecord = true;
#if DB_LOG_RECORD
                RecordRequest();
#endif

                if (!OnInit())
                {
                    WriteError("init fail/invalid data!");
                    return;
                }
                WriteDebug("LoadAPI", false);
                if (!LoadAPI())
                    throw new System.Web.HttpException(404, "Page Not Found");
                WriteDebug("LoadAPI", true);

#if DB_LOG_RECORD
                if (dblog != null)
                {
                    dblog.sp_api_url_id = api.id;
                    dblog.linkid = GetLinkId();
                }
#endif
                if (FastLinkCheck())
                {
                    WriteExisted();
                    return;
                }
                WriteDebug(string.Format("{0} in locked", _linkidProcing.Count));
                try
                {
                    StartPorcess();
                }
                finally
                {
                    RemoveFastLink();
                    //stwc.Stop();
                    //if (stwc.Elapsed.TotalSeconds > 2)
                    //{
                    //    //Shotgun.Library.SimpleLogRecord.WriteLog(Request.MapPath(string.Format("~/pushlog/sql_{0:yyyy-MM-dd}.log", DateTime.Now)), dfp.PerformanceReport());
                    //}

                    // WriteDebug(dfp.PerformanceReport());

                }

            }
            catch (Exception ex)
            {
                WriteDebug(ex.Message);
                throw;
            }
            finally
            {
#if DB_LOG_RECORD
                try
                {
                    if (dblog != null)
                        dBase.SaveData(dblog);
                }
                catch (System.Data.Common.DbException)
                {
                }
#endif
                FlushDebug();
            }

        }

        #region 提供快速linkid锁定处理(防止SP在短时间内重同步)
        private void RemoveFastLink()
        {
            lock (_linkidProcing)
            {
                var link = GetLinkId();
                if (string.IsNullOrEmpty(link))
                    return;
                _linkidProcing.Remove(link);
            }
        }

        private bool FastLinkCheck()
        {
            lock (_linkidProcing)
            {
                var linkid = GetLinkId();
                if (string.IsNullOrEmpty(linkid))
                    return false;
                if (_linkidProcing.Contains(linkid))
                    return true;
                _linkidProcing.Add(linkid);
                return false;
            }
        }
        #endregion

        /// <summary>
        /// 重置变量数据，多次同步模式时需要使用
        /// </summary>
        /// <returns></returns>
        protected virtual void Reset()
        {
            _IsMo = 0;
            _linkId = null;
            _MoItem = null;
            _MrItem = null;
        }

        protected virtual void StartPorcess()
        {
            WriteDebug("StartPorcess", false);
            LightDataModel.tbl_troneItem trone = null;
            var isms = LoadItem();
            if (isms == null)
            {
                WriteError("linkid not found");
                return;
            }
            if (!IsNew)
            {//update 不同步重复数据
                WriteExisted();
                return;
            }
            WriteDebug("ItemNotExisted", false);
            isms.ip = Shotgun.Library.Static.GetUserHostAddress();
            //isms.orgUrl = GetQuery();
            isms.recdate = DateTime.Now;
            isms.sp_api_url_id = API_URL_Config_Id;
            isms.sp_id = api.sp_id;
            //isms.API_Config_Id = API_Config_Id;

            //isms.linkid= //已经在LoadItem中赋值


            string err;
            if (IsMo)
            {//MO
                if (!string.IsNullOrEmpty(api.MoPrice))
                    isms.price = GetFee(api.MoPrice);

                err = InsertMO();
                MoCopyToMr();
            }
            else
            {//MR
                if (!string.IsNullOrEmpty(api.MrPrice))
                    isms.price = GetFee(api.MrPrice);
                MoCopyToMr();
                err = InsertMR();
            }
            if (string.IsNullOrEmpty(isms.ori_trone) && U2DMap.ContainsKey("ori_trone"))
            {//虚似端口
                if (C_VIRTUAL_PORT.Equals(U2DMap["ori_trone"], StringComparison.OrdinalIgnoreCase))
                    isms.ori_trone = string.Format("3{0:00000}", API_URL_Config_Id);
            }
            if (string.IsNullOrEmpty(isms.ori_order) && U2DMap.ContainsKey("ori_order"))
            {//虚似指令
                if (C_VIRTUAL_MSG.Equals(U2DMap["ori_order"], StringComparison.OrdinalIgnoreCase))
                    isms.ori_order = string.Format("ht_3{0:00000}_{1}", API_URL_Config_Id, isms.price);
            }

            if (!U2DMap.ContainsKey("mmc"))
                isms.mcc = "460";//没有设置mmc动作时，写入默认值

            WriteDebug("ValueCollected");


            if (isms.trone_id == 0)
            {
                if (!IsMo && string.IsNullOrEmpty(api.MrStatus))//MR 安全检查
                {//MR 数据，未配置状态检查时，进行“状态”关键字检查，以防万一
                    if (CheckStatusKeywords())
                        isms.trone_id = -3;//存在“状态”关键字
                }
                try
                {
                    if (isms.trone_id == 0)
                        trone = FillToneId(dBase, isms);
                }
#if !DEBUG
                catch (Exception ex)
                {
                    WriteDebug("TroneMathcFail:" + ex.Message);
                }
#endif
                finally { }
            }
            WriteDebug("TroneIdMatched");


            if (!string.IsNullOrEmpty(err))
            {
                WriteError(err);
                return;
            }
            try
            {
                if (_MoItem != null)
                    dBase.SaveData(_MoItem);

                if (_MrItem != null)
                {
                    if (_MoItem != null)
                    {
                        _MrItem.mo_id = _MoItem.id;
                        _MrItem.mo_table = _MoItem.TableName;
                        if (_MrItem.trone_id <= 0)
                            _MrItem.trone_id = _MoItem.trone_id;
                    }
                    _MrItem.IsMatch = _MrItem.trone_id > 0;
                    dBase.SaveData(_MrItem);
                }
            }
            catch (System.Data.Common.DbException)
            {
                WriteError("database busy");
                return;
            }
            WriteSuccess();
            WriteDebug("CoreProcessed", true);
            var db3 = (Shotgun.Database.IBaseDataPerformance)dBase;
            db3.EnableRecord = true;
            if (_MrItem != null)
            {//同步新的MR记录
                if (_MrItem.cp_id == 0 || _MrItem.cp_id == 34)
                    DoPush(trone);
            }
            WriteDebug(db3.PerformanceReport());
            WriteDebug("ALL done", true);
        }



        private bool CheckStatusKeywords()
        {
            var rx = new Regex("\\b(" + C_STATUS_KEYWORD + ")\\b", RegexOptions.IgnoreCase);
            var qs = Request.Url.Query;
            if (Request.HttpMethod == "POST")
            {//获取POST数据
                var l = Request.TotalBytes;
                if (l > 5)
                {
                    Request.InputStream.Seek(0, System.IO.SeekOrigin.Begin);
                    if (l > 2048)
                        l = 2048;
                    var bin = new byte[l];
                    Request.InputStream.Read(bin, 0, l);
                    qs += "|" + ASCIIEncoding.ASCII.GetString(bin);
                }
            }
            return rx.IsMatch(qs);
        }

#if DB_LOG_RECORD
        private void RecordRequest()
        {
            dblog = new LightDataModel.tbl_request_logItem();
            dblog.createTime = DateTime.Now;
            dblog.date = dblog.createTime;
            dblog.is_post = Request.HttpMethod == "POST";
            if (api != null)
                dblog.sp_api_url_id = api.id;
            dblog.url = Request.Url.PathAndQuery;
            if (dblog.is_post)
            {
                var bin = new byte[Request.TotalBytes];
                Request.InputStream.Read(bin, 0, bin.Length);
                Request.InputStream.Seek(0, System.IO.SeekOrigin.Begin);
                dblog.data = Request.ContentEncoding.GetString(bin);
            }
        }
#endif

        /// <summary>
        ///  Request
        /// </summary>
        /// <returns></returns>
        public virtual string GetParamValue(string Field)
        {
            var t = Request[Field];
            if (t == null)
            {
                if (Field.ToLower() == "virtualport")
                    return string.Format("3{0:00000}", API_URL_Config_Id);
            }
            return t;
        }

        private void ExecuteFile()
        {
            var context = System.Web.HttpContext.Current;
            IHttpHandler handler = PageParser.GetCompiledPageInstance(
                      Request.Path,
                     Request.PhysicalPath, context);

            if (handler is BaseSPCallback)
            {
                var amc = (BaseSPCallback)handler;
                amc.IsPhyFile = true;
                if (!amc.IsPhyFile)
                    throw new Exception("配置错误，继承AutoMapCallback的ashx必须将IsPhyFile设置为true");
            }

            context.Handler = handler;
            try
            {
                handler.ProcessRequest(context);
            }
            catch (System.Threading.ThreadAbortException) { }
            finally
            {
                if (handler is IDisposable)
                    ((IDisposable)handler).Dispose();
            }
        }

        /// <summary>
        /// 填充手机号归属地信息
        /// </summary>
        /// <param name="m"></param>
        protected virtual void FillAreaInfo(LightDataModel.tbl_mrItem m)
        {
            var num = m.mobile;
            m.city_id = 416;
            m.province_id = 32;
            if (num == null)
                return;

            int spNum = 0;
            if (num.Length == 11 && num.StartsWith("1"))//传统手机
                int.TryParse(num.Substring(0, 7), out spNum);
            else if (num.Length != 15) //非手机号 非IMSI
            {
                num = m.imsi;
                if (string.IsNullOrEmpty(num) || num.Length != 15)
                    return;
            } //else 长为15

            if (spNum == 0)
            {
                if (num.Length == 15 && num.StartsWith("460"))//IMSI
                {
                    var t = Public.Library.GetPhoneByImsi(num);
                    if (string.IsNullOrEmpty(t) || t.Length != 7)
                        return;
                    spNum = int.Parse(t);
                }
                else
                    return;
            }



            var cityInfo = LightDataModel.tbl_phone_locateItem.GetRowByMobile(dBase, spNum);
            if (cityInfo == null)
                return;
            m.city_id = cityInfo.id;
            m.province_id = cityInfo.province_id;
            //m.province_id=
        }

        private void DoPush(LightDataModel.tbl_troneItem trone)
        {
            //throw new NotImplementedException();
            if (!_MrItem.IsMatch || trone == null)
                return;
            var logFile = Server.MapPath(string.Format("~/PushLog/{0:yyyyMMdd}.log", DateTime.Today));

            var apiPush = new HTAPIPusher()
            {
                dBase = dBase,
                Trone = trone,
                LogFile = logFile
            };

            if (apiPush.LoadCPAPI())
            {
                try
                {
                    apiPush.PushObject = _MrItem;
                    if (apiPush.DoPush())
                        return;
                }
#if !DEBUG
                catch (Exception ex)
                {
                    Shotgun.Library.SimpleLogRecord.WriteLog(Request.MapPath("~/log/api_push_error.log"), ex.ToString());
                }
#endif
                finally
                {
                }
            }


            var cp = new AutoMapPush();
            cp.dBase = dBase;
            cp.Trone = trone;
            //cp.UnionUserId = -1;
            cp.LogFile = logFile;

            if (!cp.LoadCPAPI())
                return;

            cp.PushObject = _MrItem;
            cp.DoPush();

        }

        int GetFee(string field)
        {
            if (string.IsNullOrEmpty(field))
                return 0;
            var s = field.Split(new char[] { ',' }, 3, StringSplitOptions.RemoveEmptyEntries);
            string val = GetParamValue(s[0]);
            if (string.IsNullOrEmpty(val))
                return 0;
            string type;
            if (s.Length == 1)
                type = "0";
            else
                type = s[1];
            int ret;
            if (type == "0")//单位：分
            {
                if (int.TryParse(val, out ret))
                    return ret;
                return -1;
            }
            else if (type == "1")//单位：元
            {
                float y;
                if (float.TryParse(val, out y))
                {
                    y *= 100;
                    return (int)Math.Round(y, 0);
                }
                return -1;
            }
            else if (type == "3")//单位：角
            {
                float y;
                if (float.TryParse(val, out y))
                {
                    y *= 10;
                    return (int)Math.Round(y, 0);
                }
                return -1;
            }
            if (type != "2" || s.Length == 2)
                return -1;
            //复杂的模式2，匹对模式
            var exp = s[2].Split(new char[] { ',' });
            foreach (var e in exp)
            {
                var ar = e.Split(new char[] { ':' }, 2, StringSplitOptions.RemoveEmptyEntries);
                if (ar.Length != 2)
                    return -1;
                var rx = Library.GetRegex(ar[0]);
                if (rx.IsMatch(val))
                {
                    if (int.TryParse(ar[1], out ret))
                        return ret;
                    return -1;
                }
            }
            return 0;
        }


        private void MoCopyToMr()
        {
            if (_MrItem == null || _MoItem == null)
                return;
            if (string.IsNullOrEmpty(api.MoToMr))
                return;
            var fields = api.MoToMr.Split(new char[] { ',' }, StringSplitOptions.RemoveEmptyEntries);
            foreach (var f in fields)
            {
                string field;
                switch (f)
                {
                    case "msg": field = "ori_order"; break;
                    case "port": field = "ori_trone"; break;
                    case "servicecode": field = "service_code"; break;
                    default: field = f; break;
                }


                _MrItem[field] = _MoItem[field];
            }
        }

        private string InsertMR()
        {
            if (string.IsNullOrEmpty(api.MrFidldMap))
                return null;

            //var fields = api.MrFidldMap.Split(new char[] { ',' }, StringSplitOptions.RemoveEmptyEntries);
            UrlFieldsMap(api.MrFidldMap);

            foreach (var kv in U2DMap)
            {
                if (C_IVR_TIME.Equals(kv.Key, StringComparison.OrdinalIgnoreCase))
                    _MrItem.ivr_time = int.Parse(GetParamValue(kv.Value));
                else
                    _MrItem[kv.Key] = GetParamValue(kv.Value);
            }
            FillAreaInfo(_MrItem);
            if (string.IsNullOrEmpty(api.MrStatus))
                return null;

            if (!U2DMap.ContainsKey("status"))
                return string.Format("\"status\" field not configured");

            if (_MrItem.status == null)
                return string.Format("\"{0}\" null", U2DMap["status"]);

            var rx = Library.GetRegex(api.MrStatus);
            if (!rx.IsMatch(_MrItem.status))
                return string.Format("\"{0}\" \"{1}\" unaccpated", U2DMap["status"], _MrItem.status);

            return null;

        }

        private string InsertMO()
        {

            if (string.IsNullOrEmpty(api.MoFieldMap))
                return null;

            UrlFieldsMap(api.MoFieldMap);
            foreach (var kv in U2DMap)
            {
                //if(C_IVR_TIME.Equals(kv.Key, StringComparison.OrdinalIgnoreCase))
                //    _MoItem.ivt
                //else
                _MoItem[kv.Key] = GetParamValue(kv.Value);
            }

            return null;

            //MO不再检查 status
            //if (string.IsNullOrEmpty(api.MoStatus))
            //    return null;
            //var rx = new Regex(api.MrStatus, RegexOptions.IgnoreCase);
            //if (rx.IsMatch(_MoItem.status))
            //    return null;
            //return string.Format("status \"{0}\" unaccpated", _MoItem.status);
        }


        void UrlFieldsMap(string ptr)
        {
            var fields = ptr.Split(new char[] { ',' }, StringSplitOptions.RemoveEmptyEntries);
            var sp = new char[] { ':' };
            U2DMap = new Dictionary<string, string>();
            foreach (var f in fields)
            {
                var ar = f.Split(sp, 2, StringSplitOptions.RemoveEmptyEntries);
                string url = ar[0];
                string sql;
                if (ar.Length == 1)
                    sql = url;
                else
                    sql = ar[1];
                switch (sql.ToLower())
                {
                    case "msg": sql = "ori_order"; break;
                    case "port": sql = "ori_trone"; break;
                    case "servicecode": sql = "service_code"; break;
                    case "cpparam": sql = "cp_param"; break;
                }
                U2DMap[sql.ToLower()] = url;
            }
        }


        #region 信息输出
        protected virtual void WriteSuccess()
        {
            if (!string.IsNullOrEmpty(api.MsgOutput))
            {
                var ar = api.MsgOutput.Split(new char[] { '/' }, StringSplitOptions.RemoveEmptyEntries);
                Response.Write(ar[0]);
            }
            else
                Response.Write("ok");
        }
        private void WriteExisted()
        {
            if (string.IsNullOrEmpty(api.MsgOutput))
            {
                WriteError("linkid existed");
                return;
            }
            var ar = api.MsgOutput.Split(new char[] { '/' }, StringSplitOptions.RemoveEmptyEntries);
            if (ar.Length == 3)
                Response.Write(ar[2]);
            else
                WriteError("linkid existed");
        }
        protected virtual void WriteError(string msg)
        {
            string pfx = "error";
            if (api != null && !string.IsNullOrEmpty(api.MsgOutput))
            {
                var ar = api.MsgOutput.Split(new char[] { '/' }, StringSplitOptions.RemoveEmptyEntries);
                if (ar.Length > 2)
                    pfx = ar[1];
            }
            Response.Write(pfx);
            if (string.IsNullOrEmpty(msg))
                return;
            Response.Write(' ');
            Response.Write(msg);
        }
        #endregion

        /// <summary>
        /// 匹对指令
        /// </summary>
        /// <param name="dBase"></param>
        /// <param name="m"></param>
        /// <returns></returns>
        public static LightDataModel.tbl_troneItem FillToneId(Shotgun.Database.IBaseDataClass2 dBase, Logical.ISMS_DataItem m)
        {
            //var csl = LightDataModel.tbl_troneItem.GetQueries(dBase);
            //csl.Filter.AndFilters.Add(LightDataModel.tbl_troneItem.Fields.sp_api_url_id, m.sp_api_url_id);
            //csl.Filter.AndFilters.Add(LightDataModel.tbl_troneItem.Fields.trone_num, m.ori_trone);
            //csl.Filter.AndFilters.Add(LightDataModel.tbl_troneItem.Fields.status, 1);

            //csl.Fields = new string[] { LightDataModel.tbl_troneItem.Fields.id,LightDataModel.tbl_troneItem.Fields.trone_num,
            //    LightDataModel.tbl_troneItem.Fields.orders,LightDataModel.tbl_troneItem.Fields.is_dynamic,LightDataModel.tbl_troneItem.Fields.price,
            //    LightDataModel.tbl_troneItem.Fields.match_price,LightDataModel.tbl_troneItem.Fields.sp_trone_id};

            //csl.PageSize = int.MaxValue;

            //var cmds = csl.GetDataList();
            var cmds = LightDataModel.tbl_troneItem.QueryTronesByPort(dBase, m.sp_api_url_id, m.ori_trone);
            if (cmds == null && cmds.Count() == 0)
                return null;//没有可用通道
            var mMsg = m.ori_order;
            if (mMsg == null)
                mMsg = string.Empty;

            m.trone_id = -2;
            LightDataModel.tbl_troneItem trone = null;
            foreach (var cmd in cmds)
            {
                var cMsg = cmd.orders;
                if (string.IsNullOrEmpty(cMsg) && string.IsNullOrEmpty(mMsg))
                {
                    trone = cmd;
                    break;
                }
                if (cmd.match_price)
                {//无规则指令，直接匹配价格
                    if (cmd.price == (m.price / 100m))
                    {
                        trone = cmd;
                        break;
                    }
                }
                else if (cmd.is_dynamic)
                {//模糊指令
                    var rx = Library.GetRegex(cmd.orders);
                    if (rx.IsMatch(mMsg))
                    {
                        trone = cmd;
                        break;
                    }
                }
                else
                {//精确指令
                    if (mMsg.Equals(cMsg))
                    {
                        trone = cmd;
                        break;
                    }
                }
            }

            if (trone == null)
                return null;
            m.trone_id = trone.id;


            var sp_trone = LightDataModel.tbl_sp_troneItem.GetRowById(dBase, trone.sp_trone_id, new string[] { LightDataModel.tbl_sp_troneItem.Fields.trone_type });
            if (sp_trone == null)
                return null;
            m.trone_type = sp_trone.trone_type;
            return trone;//没有匹配通道
        }


        //private string GetQuery()
        //{
        //    var qs = Request.Url.Query;
        //    if (Request.HttpMethod == "GET" || Request.TotalBytes == 0)
        //        return qs;

        //    string ps = null;

        //    var bin = Request.BinaryRead(Request.TotalBytes);
        //    ps = ASCIIEncoding.UTF8.GetString(bin);
        //    if (string.IsNullOrEmpty(qs))
        //        qs = ps;
        //    else
        //        qs += "&" + ps;

        //    return qs;
        //}


        private Logical.ISMS_DataItem LoadItem()
        {
            var linkId = GetLinkId();
            if (string.IsNullOrEmpty(linkId))
                return null;
            if (!string.IsNullOrEmpty(api.MoCheck))
                _MoItem = GetMOItemByLinkId(linkId);
            _MrItem = GetMRItemByLinkId(linkId);

            if (IsMo)
            {
                if (_MoItem == null)
                {
                    IsNew = true;
                    _MoItem = new LightDataModel.tbl_moItem();
                    _MoItem.create_date = DateTime.Now;
                    _MoItem.linkid = linkId;
                }
                return _MoItem;
            }

            if (_MrItem == null)
            {
                IsNew = true;
                _MrItem = new LightDataModel.tbl_mrItem();
                _MrItem.create_date = DateTime.Now;
                _MrItem.linkid = linkId;
            }
            return _MrItem;
        }

        protected string GetLinkId()
        {
            string f = IsMo ? api.MoLink : api.MrLink;

            if (string.IsNullOrEmpty(f))
                throw new Exception(string.Format("请配置Link字段。IsMo:{0}", IsMo));
            if (_linkId != null)
                return _linkId;

            _linkId = GetParamValue(f);
            if (_linkId == null)
                _linkId = string.Empty;

            return _linkId;
        }

        protected LightDataModel.tbl_moItem GetMOItemByLinkId(string linkId)
        {
            if (string.IsNullOrEmpty(api.MoCheck))
                return null;//仅MR模式

            var q = LightDataModel.tbl_moItem.GetQueries(dBase);

            q.Filter.AndFilters.Add(LightDataModel.tbl_moItem.Fields.linkid, linkId);
            q.Filter.AndFilters.Add(LightDataModel.tbl_moItem.Fields.sp_api_url_id, api.id);

            var today = DateTime.Today;
            for (var i = 0; i > -3; i--)
            {
                q.TableDate = today.AddMonths(i);
                if (q.TableDate.Year <= 2015 && q.TableDate.Month < 9)
                    continue;

                var m = q.GetRowByFilters();
                if (m != null)
                    return m;
            }
            return null;
        }

        protected LightDataModel.tbl_mrItem GetMRItemByLinkId(string linkId)
        {
            var q = LightDataModel.tbl_mrItem.GetQueries(dBase);
            q.Filter.AndFilters.Add(LightDataModel.tbl_mrItem.Fields.sp_api_url_id, this.api.id);
            q.Filter.AndFilters.Add(LightDataModel.tbl_mrItem.Fields.linkid, linkId);

            var today = DateTime.Today;
            for (var i = 0; i > -2; i--)//MR 早于MO 情况，回找1个月
            {
                q.TableDate = today.AddMonths(i);
                if (q.TableDate.Year <= 2015 && q.TableDate.Month < 9)
                    continue;

                var m = q.GetRowByFilters();
                if (m != null)
                    return m;
            }

            return null;
        }

        /// <summary>
        /// 用于标识的短信内容的
        /// </summary>
        public virtual int API_URL_Config_Id
        {
            get
            {
                return api.id;
            }
        }

        public System.Web.Caching.Cache Cache { get { return System.Web.HttpRuntime.Cache; } }

        public bool IsMo
        {
            get
            {
                if (_IsMo != 0)
                    return _IsMo == 1;
                if (string.IsNullOrEmpty(api.MoCheck))
                {
                    _IsMo = -1;
                    return false;
                }
                var ar = api.MoCheck.Split(new char[] { ':' }, 2, StringSplitOptions.RemoveEmptyEntries);
                var val = GetParamValue(ar[0]);
                if (ar.Length == 1)//只写字段名,表示,只要URL上出生了该参数,即视为MO
                {
                    _IsMo = val != null ? 1 : -1;
                }
                else
                {
                    if (val == null)
                        val = string.Empty;

                    var rx = new Regex(ar[1], RegexOptions.IgnoreCase);
                    _IsMo = rx.IsMatch(val) ? 1 : -1;
                }
                return _IsMo == 1;
            }
        }

        private bool LoadAPI()
        {
            var p = Request.Url.AbsolutePath;
            var mc = Regex.Match(p, @"/([^/]+)/([^/]+)\.ashx$", RegexOptions.IgnoreCase);
            if (!mc.Success)
                return false;

            //var l = LightDataModel.tbl_sp_api_urlItem.GetQueries(dBase);
            //l.Filter.AndFilters.Add(LightDataModel.tbl_sp_api_urlItem.Fields.Disable, false);
            if (Regex.IsMatch(mc.Groups[1].Value, "^\\d+$"))//指定了ID作为目录
            {
                int id = int.Parse(mc.Groups[1].Value);
                api = LightDataModel.tbl_sp_api_urlItem.QueryById(dBase, id);
                //l.Filter.AndFilters.Add(LightDataModel.tbl_sp_api_urlItem.Fields.id, id);
            }
            else
            {
                //l.Filter.AndFilters.Add(LightDataModel.tbl_sp_api_urlItem.Fields.virtual_page, mc.Groups[2].Value);
                api = LightDataModel.tbl_sp_api_urlItem.QueryByVirtualPage(dBase, mc.Groups[2].Value);
            }

            //api = l.GetRowByFilters();
            return api != null;
        }

        public bool IsNew { get; set; }
    }
}
