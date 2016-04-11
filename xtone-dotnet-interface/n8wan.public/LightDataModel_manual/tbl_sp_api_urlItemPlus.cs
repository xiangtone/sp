using n8wan.Public.Logical;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace LightDataModel
{
    partial class tbl_sp_api_urlItem
    {
        static StaticCache<tbl_sp_api_urlItem, int> cache = new StaticCache<tbl_sp_api_urlItem, int>() { Expired = new TimeSpan(0, 15, 0) };

        public static tbl_sp_api_urlItem QueryById(Shotgun.Database.IBaseDataClass2 dBase, int id)
        {
            var m = cache.GetDataByIdx(id);
            if (m != null)
                return m.Disable ? null : m;

            var q = GetQueries(dBase);
            m = q.GetRowById(id);

            if (m == null)
                return null;
            cache.InsertItem(m);
            return m.Disable ? null : m;
        }

        public static tbl_sp_api_urlItem QueryByVirtualPage(Shotgun.Database.IBaseDataClass2 dBase, string virtualPage)
        {
            var m = cache.FindFirstData(e => virtualPage.Equals(e.virtual_page, StringComparison.OrdinalIgnoreCase));
            if (m != null)
                return m.Disable ? null : m;

            var q = GetQueries(dBase);
            q.Filter.AndFilters.Add(Fields.virtual_page, virtualPage);
            m = q.GetRowByFilters();
            if (m == null)
                return null;
            cache.InsertItem(m);
            return m.Disable ? null : m;
        }
    }
}
