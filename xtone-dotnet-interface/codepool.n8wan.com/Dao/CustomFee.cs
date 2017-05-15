using NoSqlModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace n8wan.codepool.Dao
{
    /// <summary>
    /// 用户消息记录
    /// </summary>
    public class CustomFee
    {
        static n8wan.Public.Logical.StaticCache<CustomFeeModel, string> cache;

        static CustomFee()
        {
            cache = new Public.Logical.StaticCacheTimeline<CustomFeeModel, string>();
            cache.IsManualLoad = true;
            cache.Expired = new TimeSpan(1, 2, 3);
        }

        /// <summary>
        /// 计算用户的日月限信息
        /// </summary>
        /// <returns>始终不为空</returns>
        public static CustomLimitInfo QueryLimit(Shotgun.Database.IBaseDataClass2 dBase, int spTroneId, string customId)
        {
            var data = cache.GetCacheData(false);
            List<CustomFeeModel> cfm = null;
            if (data != null)
            {
                lock (cache.SyncRoot)
                    cfm = data.Where(e => e.SpToneId == spTroneId && customId.Equals(e.CustomId, StringComparison.OrdinalIgnoreCase)).ToList();
            }
            if (cfm == null || cfm.Count() == 0)
                cfm = LoadFromDBase(dBase, spTroneId, customId);
            var cli = new CustomLimitInfo() { CustomId = customId };
            var today = DateTime.Today;
            foreach (var cf in cfm)
            {
                if (cf.Date >= today)
                {
                    cli.DayCount += cf.Count;
                    cli.DayAmount += cf.TotalFee;
                }
                else
                {
                    cli.MonthCount += cf.Count;
                    cli.MonthAmount += cf.TotalFee;
                }
            }
            cli.MonthAmount += cli.DayAmount;
            cli.MonthCount += cli.MonthCount;
            return cli;

        }


        static List<CustomFeeModel> LoadFromDBase(Shotgun.Database.IBaseDataClass2 dBase, int spTroneId, string customId)
        {
            var trones = LightDataModel.tbl_troneItem.GetTroneIdsBySptroneId(dBase, spTroneId);

            if (trones.Count() == 0)
                return null;

            var sql = string.Format("SELECT trone_id,fee_date , count  FROM daily_log.tbl_custom_fee_count "
                       + " where custom_id='{0}' and trone_id in({1})"
                       + " group by trone_id, fee_date",
                       dBase.SqlEncode(customId), string.Join(",", trones.Select(e => e.id)));

            var cmd = dBase.Command();
            cmd.CommandText = sql;
            System.Data.IDataReader dr = null;
            List<CustomFeeModel> rlt = new List<CustomFeeModel>();

            try
            {
                dr = dBase.ExecuteReader(cmd);
                while (dr.Read())
                {
                    var m = new CustomFeeModel();
                    m.CustomId = customId;
                    m.SpToneId = spTroneId;
                    m.TroneId = dr.GetInt32(0);
                    m.Date = dr.GetDateTime(1);
                    m.Count = dr.GetInt32(2);
                    m.Fee = decimal.ToInt32(100 * trones.Single(e => e.id == m.TroneId).price);
                    rlt.Add(m);
                    cache.InsertItem(m);
                }
            }
            finally
            {
                if (dr != null)
                    dr.Dispose();
                cmd.Dispose();
            }
            if (rlt.Count == 0)
            {//表示该用户，日月限缓存已经建立
                var trone = trones.First();
                var m = new CustomFeeModel();
                m.CustomId = customId;
                m.SpToneId = spTroneId;
                m.TroneId = trone.id;
                m.Date = DateTime.Today;
                m.Count = 0;
                m.Fee = decimal.ToInt32(100 * trone.price);
                rlt.Add(m);
                cache.InsertItem(m);
            }
            return rlt;
        }


        public static void UpdateLimit(Shotgun.Database.IBaseDataClass2 dBase, int troneId, string customId, DateTime mrDate)
        {
            var key = CustomFeeModel.GetIdKey(customId, troneId, mrDate);
            var m = cache.GetDataByIdx(key);
            if (m != null)
            {
                lock (m)
                    m.Count++;
                return;
            }
            var trone = LightDataModel.tbl_troneItem.GetRowById(dBase, troneId);
            var spTrone = LightDataModel.tbl_sp_troneItem.GetRowById(dBase, trone.sp_trone_id);
            var data = cache.GetCacheData(false);
            bool iFound = false;
            lock (cache.SyncRoot)
            {
                foreach (var item in data)
                {
                    if (item.SpToneId == spTrone.id)
                    {
                        iFound = true;
                        break;
                    }
                }
            }
            if (iFound)//用户的缓存已经加载，但没有此通道计费情况
            {
                m = new CustomFeeModel();
                m.SpToneId = spTrone.id;
                m.TroneId = trone.id;
                m.Date = mrDate.Date;
                m.Count = 1;
                m.Fee = decimal.ToInt32(trone.price * 100);
                cache.InsertItem(m);
                return;
            }
            //无缓存数据时，无需任何处理
            //因为在通道计算时，无缓存数据时，会主动从数据库读取最新信息
            //LoadFromDBase(dBase, spTrone.id, customId);

        }
    }
}
