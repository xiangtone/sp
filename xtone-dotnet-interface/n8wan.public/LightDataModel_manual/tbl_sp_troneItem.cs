using n8wan.Public.Logical;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace LightDataModel
{
    partial class tbl_sp_troneItem
    {
        static StaticCache<tbl_sp_troneItem, int> _cache = new StaticCache<tbl_sp_troneItem, int>() { IsManualLoad = true, Expired = new TimeSpan(1, 0, 0) };

        /// <summary>
        /// 根据主键查找指定的行,返回指定字段
        /// </summary>
        /// <param name="dBase"></param>
        /// <param name="id">主键值</param>
        /// <param name="fields">返回字段</param>
        /// <returns></returns>
        public static tbl_sp_troneItem GetRowById(Shotgun.Database.IBaseDataClass2 dBase, int id, string[] fields)
        {
            var m = _cache.GetDataByIdx(id);
            if (m != null)
                return m;

            var q = GetQueries(dBase);
            //q.Fields = fields;
            q.Filter.AndFilters.Add(identifyField, id);
            m = q.GetRowByFilters();
            if (m != null)
                _cache.InsertItem(m);

            return m;
        }

    }
}
