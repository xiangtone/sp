using LightDataModel;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;
using System.Threading;

namespace n8wan.Public.Logical
{

    /// <summary>
    /// 更新通道日月限数据
    /// </summary>
    public class TroneDayLimit : Shotgun.Model.Logical.Logical
    {
        static string pushUrl;
        static TroneDayLimit()
        {
            pushUrl = ConfigurationManager.AppSettings["TroneDayLimit"];
            if (string.IsNullOrEmpty(pushUrl))
                return;
            if (pushUrl.Contains("?"))
                pushUrl += "&";
            else
                pushUrl += "?";

        }
        public static void UpdateDayLimit(Shotgun.Database.IBaseDataClass2 dBase, int spTroneId, int cpId, decimal amount)
        {
            var m = LightDataModel.tbl_day_month_limitItem.GetOrCreateItem(dBase, spTroneId, cpId);
            m.cur_day_amount += amount;
            PushDayLimit(spTroneId, cpId, amount);
            try
            {
                dBase.SaveData(m);
            }
#if !DEBUG
            catch
            {
            }
#endif
            finally { }


        }

        private static void PushDayLimit(int spTroneId, int cpId, decimal amount)
        {
            if (string.IsNullOrEmpty(pushUrl))
                return;

            var url = string.Format("{3}sptroneid={0}&cpid={1}&money={2}", spTroneId, cpId, amount, pushUrl);
            ThreadPool.QueueUserWorkItem(SendData, url);

        }

        private static void SendData(object state)
        {
            string url = (string)state;
            var web = System.Net.WebRequest.Create(url);
            web.Timeout = 1000;
            try
            {
                var rsp = web.GetResponse();
                rsp.Close();
            }
            catch { }
        }
    }
}
