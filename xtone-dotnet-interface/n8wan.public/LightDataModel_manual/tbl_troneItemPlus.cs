using n8wan.Public.Logical;
using Shotgun.Database;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;

namespace LightDataModel
{
    partial class tbl_troneItem
    {
        static StaticCache<tbl_troneItem, int> cache = new StaticCache<tbl_troneItem, int>() { Expired = new TimeSpan(0, 15, 0) };

        //static Dictionary<int, tbl_troneItem> trones;
        ///// <summary>
        ///// 缓存加载状态，0：未加载，1：加载中，2：加载完成
        ///// </summary>
        //static int cacheStatus = 0;
        //static DateTime cacheExpired;
        //static object locker = new object();

        /// <summary>
        /// 根据主键查找指定的行,返回指定字段
        /// </summary>
        /// <param name="dBase"></param>
        /// <param name="id">主键值</param>
        /// <param name="fields">返回字段</param>
        /// <returns></returns>
        public static tbl_troneItem GetRowById(Shotgun.Database.IBaseDataClass2 dBase, int id, string[] fields)
        {

            tbl_troneItem m = cache.GetDataByIdx(id);
            if (m != null)
                return m;
            else if (cache.Status != Static_Cache_Staus.AllLoad)
            {//缓存未生成时
                var q = GetQueries(dBase);
                m = q.GetRowById(id);
                cache.InsertItem(m);
            }
            return m;



            //var tTones = GetCache(false);
            //tbl_troneItem m = null;
            //if (tTones != null && tTones.ContainsKey(id))
            //    m = tTones[id];
            //else if (cacheStatus != 2)
            //{//缓存未生成时
            //    var q = GetQueries(dBase);
            //    m = q.GetRowById(id);
            //}
            //return m;
        }

        /// <summary>
        /// 根据 apiId和端口号查找所有通道 (缓存15分钟)
        /// </summary>
        /// <param name="apiId"></param>
        /// <param name="port"></param>
        /// <returns></returns>
        public static IEnumerable<tbl_troneItem> QueryTronesByPort(Shotgun.Database.IBaseDataClass2 dBase, int apiId, string port)
        {
            var tTrones = cache.GetCacheData(true);
            if (tTrones != null)
            {
                return from t in tTrones where t.sp_api_url_id == apiId && t.trone_num == port && t.status == 1 select t;
            }
            var csl = GetQueries(dBase);
            csl.Filter.AndFilters.Add(Fields.sp_api_url_id, apiId);
            csl.Filter.AndFilters.Add(Fields.trone_num, port);
            csl.Filter.AndFilters.Add(Fields.status, 1);

            csl.Fields = new string[] { Fields.id, Fields.trone_num, Fields.orders, Fields.is_dynamic, Fields.price, Fields.match_price, Fields.sp_trone_id };

            csl.PageSize = int.MaxValue;

            return csl.GetDataList();
        }


        ///// <summary>
        ///// 从数据库加载完整的数据
        ///// </summary>
        ///// <param name="s"></param>
        //private static void LoadCache(object s)
        //{
        //    int minId = 0;
        //    if (trones == null)
        //        trones = new Dictionary<int, tbl_troneItem>();
        //    else
        //        minId = trones.Values.Max(e => e.id);

        //    System.Diagnostics.Stopwatch st = new System.Diagnostics.Stopwatch();
        //    st.Start();
        //    using (var dBase = new DBDriver().CreateDBase())
        //    {
        //        var q = GetQueries(dBase);
        //        q.Filter.AndFilters.Add(tbl_troneItem.identifyField, minId, Shotgun.Model.Filter.EM_DataFiler_Operator.More);
        //        q.SortKey.Add(tbl_troneItem.identifyField, Shotgun.Model.Filter.EM_SortKeyWord.asc);
        //        q.PageSize = 2000;

        //        var count = q.TotalCount;
        //        var pageCount = count / q.PageSize + ((count % q.PageSize) > 0 ? 1 : 0);
        //        bool iDone = true;
        //        for (int p = 1; p <= pageCount; p++)
        //        {
        //            q.CurrentPage = p;
        //            try
        //            {
        //                var itmes = q.GetDataList();
        //                itmes.ForEach(e => trones[e.id] = e);
        //            }
        //            catch (System.Data.DataException)
        //            {
        //                iDone = false;
        //                break;
        //            }
        //        }
        //        cacheExpired = DateTime.Now.AddMinutes(15);
        //        cacheStatus = iDone ? 2 : 0;
        //        st.Stop();
        //        Shotgun.Library.SimpleLogRecord.WriteLog("load_cache",
        //            string.Format("threadId:{0} tbl_troneItem cache Elapsed {1:#,###}ms ,count {2}",
        //                System.Threading.Thread.CurrentThread.ManagedThreadId,
        //                st.ElapsedMilliseconds,
        //               trones.Count)
        //            );
        //    }
        //}

        ///// <summary>
        ///// 取得缓存数据
        ///// </summary>
        ///// <param name="iFull">是否请求完整版的数据，非完整版的可能程序正处理加载状态</param>
        ///// <returns></returns>
        //private static Dictionary<int, tbl_troneItem> GetCache(bool iFull)
        //{
        //    if (cacheStatus == 0)
        //    {
        //        lock (locker)
        //        {//防止并发重复查询
        //            if (cacheStatus == 0)
        //            {
        //                cacheStatus = 1;
        //                ThreadPool.QueueUserWorkItem(LoadCache);
        //            }
        //        }
        //    }

        //    if (trones == null)
        //        return null;
        //    if (cacheStatus == 2 && cacheExpired < DateTime.Now)
        //    {
        //        trones = null;
        //        cacheStatus = 0;
        //        Shotgun.Library.SimpleLogRecord.WriteLog("cached", "tbl_troneItem cache Expired,clear all");
        //    }
        //    if (iFull && cacheStatus != 2)
        //        return null;//请求完整版，但数据未加载完成
        //    return trones;

        //}

        /// <summary>
        /// 一些特殊的ID的含义描述
        /// </summary>
        /// <param name="errTroneId"></param>
        /// <returns></returns>
        public static string GetTroneErrMsg(int errTroneId)
        {
            switch (errTroneId)
            {
                case -1: return "没有匹配端口";
                case -2: return "没有匹配指令";
                case -3: return "发现同步状态";
                case 0: return "未指定";
            }
            if (errTroneId > 0)
                return "匹配成功";
            return string.Format("未知状态({0})", errTroneId.ToString());
        }

    }
}
