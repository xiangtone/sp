using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace LightDataModel
{
    partial class tbl_cityItem
    {
        static Dictionary<int, tbl_cityItem> citys;
        /// <summary>
        /// 根据主键查找指定的行,返回指定字段(持续缓存)
        /// </summary>
        /// <param name="dBase"></param>
        /// <param name="id">主键值</param>
        /// <param name="fields">返回字段</param>
        /// <returns></returns>
        public static tbl_cityItem GetRowById(Shotgun.Database.IBaseDataClass2 dBase, int id, string[] fields)
        {
            if (citys == null)
            {
                var q = GetQueries(dBase);
                q.PageSize = int.MaxValue;
                var data = q.GetDataList();
                citys = new Dictionary<int, tbl_cityItem>();
                data.ForEach(e => citys.Add(e.id, e));
            }
            return citys[id];
        }
    }
}
