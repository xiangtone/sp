using n8wan.Public.Logical;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace LightDataModel
{
    partial class tbl_trone_orderItem : n8wan.Public.Logical.IHold_DataItem
    {
        static StaticCache<tbl_trone_orderItem, int> cache;

        static tbl_trone_orderItem()
        {
            cache = new StaticCache<tbl_trone_orderItem, int>();
#if DEBUG
            cache.Expired = new TimeSpan(0, 1, 0);
#else
            cache.Expired = new TimeSpan(0, 5, 0);
#endif
            cache.SetExpriedProc(OnCacheExpried);
        }

        private static void OnCacheExpried(IEnumerable<tbl_trone_orderItem> obj)
        {
            var dBase = new Shotgun.Database.DBDriver().CreateDBase();
            int c = 0;
            try
            {
                foreach (var item in obj)
                {
                    var iUpdate = item.SaveToDatabase(dBase);
                    if (iUpdate)
                    {
                        c++;
                        Shotgun.Library.SimpleLogRecord.WriteLog("Cache2Sql", string.Format("item:{0} , key:{1} updated", tableName, item.id));
                    }
                }
            }
            catch (Exception ex)
            {
                Shotgun.Library.SimpleLogRecord.WriteLog("Cache2Sql", string.Format("item:{0}Error:\n{1}", tableName, ex.ToString()));
            }
            finally
            {
                if (dBase == null)
                    dBase.Dispose();
                dBase = null;
            }
            Shotgun.Library.SimpleLogRecord.WriteLog("Cache2Sql", string.Format("item:{0} ,count:{1}", tableName, c));
        }

        public static IEnumerable<tbl_trone_orderItem> QueryByTroneIdWithCache(Shotgun.Database.IBaseDataClass2 dBase, int troneId)
        {
            var t = cache.GetCacheData(true);
            if (t != null)
                return from item in t where !item.disable && item.trone_id == troneId select item;

            var l = LightDataModel.tbl_trone_orderItem.GetQueries(dBase);
            l.Filter.AndFilters.Add(LightDataModel.tbl_trone_orderItem.Fields.trone_id, troneId);
            l.Filter.AndFilters.Add(LightDataModel.tbl_trone_orderItem.Fields.disable, 0);
            l.PageSize = int.MaxValue;
            return l.GetDataList();
        }

        //public static void InsertCache(tbl_trone_orderItem newItem)
        //{
        //    cache.InsertItem(newItem);
        //}

        protected override void OnSaved(bool isInsert)
        {
            if (isInsert)
                cache.InsertItem(this);
            base.OnSaved(isInsert);
            Shotgun.Library.SimpleLogRecord.WriteLog("Cache2Sql", string.Format("tbl_trone_orderItem.onsave,id:", this.id));
        }

        public static tbl_trone_orderItem GetRowByIdWithCache(Shotgun.Database.IBaseDataClass2 dBase, int id)
        {
            var m = cache.GetDataByIdx(id);
            if (m != null)
                return m;
            m = GetRowById(dBase, id, null);
            if (m != null)
                cache.InsertItem(m);
            return m;
        }

        public static tbl_trone_orderItem GetCacheUnkowOrder(int troneId)
        {
            var _data = cache.GetCacheData(false);
            if (_data == null)
                return null;
            foreach (var m in _data)
            {
                if (m.is_unknow && m.trone_id == troneId)
                    return m;
            }
            return null;
        }

    }
}
