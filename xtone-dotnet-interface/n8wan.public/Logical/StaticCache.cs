using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;

namespace n8wan.Public.Logical
{
    public enum Static_Cache_Staus
    {
        Idel,
        Loading,
        AllLoad
    }

    public abstract class StaticCache
    {
        static List<StaticCache> _allCache;

        static Timer timerChecker = null;

        public static void ClearAllCache()
        {
            if (_allCache == null)
                return;
            var all = _allCache.ToArray();

            foreach (var c in all)
            {
                c.ClearCache();
            }
        }

        public abstract void ClearCache();


        protected void Add(StaticCache sc)
        {
            if (sc == null)
                return;
            if (_allCache == null)
                _allCache = new List<StaticCache>();

            lock (_allCache)
            {
                if (timerChecker == null)
                    timerChecker = new Timer(CheckAction, null, 6 * 1000, 120 * 1000);

                if (_allCache.Contains(sc))
                    return;
                _allCache.Add(sc);
            }
        }

        private static void CheckAction(object state)
        {

            var cs = GetAllCache();
            if (cs == null || cs.Length == 0)
                return;
            foreach (var c in cs)
            {
                if (c.IsExpired())
                {
                    c.ClearCache();
                }
            }
            lock (_allCache)
            {
                if (_allCache.Count == 0)
                {
                    timerChecker.Dispose();
                    timerChecker = null;
                }
            }

        }

        protected abstract bool IsExpired();

        protected static StaticCache[] GetAllCache()
        {
            if (_allCache == null)
                return null;
            return _allCache.ToArray();
        }

        internal void Remove(StaticCache staticCache)
        {
            if (_allCache == null)
                return;
            _allCache.Remove(staticCache);
        }
    }

    /// <summary>
    /// 静态变量缓存-带过期时间
    /// </summary>
    /// <typeparam name="T"></typeparam>
    /// <typeparam name="IDX"></typeparam>
    public class StaticCache<T, IDX> : StaticCache where T : Shotgun.Model.Logical.LightDataModel, new()
    {
        Dictionary<IDX, T> _data;

        /// <summary>
        /// 数据加载状态
        /// </summary>
        Static_Cache_Staus _satus;
        /// <summary>
        /// 数据实际过期时间
        /// </summary>
        DateTime _expired;
        private string _tabName;
        private string _idField;
        /// <summary>
        /// 缓存内存主索引，默认使用数据库主键
        /// </summary>
        private string _indexField;
        /// <summary>
        /// 数据失效时通知外部代码进行数据处理
        /// </summary>
        private Action<IEnumerable<T>> _onExpired;

        public StaticCache()
            : this(null)
        {
        }

        /// <summary>
        /// 创建一个指主键的索引的缓存器
        /// </summary>
        /// <param name="IdxField">缓存内存主索引，默认使用数据库主键，注意数据索引键值的唯一性</param>
        public StaticCache(string IdxField)
        {
            this.Expired = new TimeSpan(24, 24, 24);
            T m = new T();
            this._idField = m.IdentifyField;
            this._tabName = m.TableName;
            if (string.IsNullOrEmpty(IdxField))
                this._indexField = this._idField;
            else
                this._indexField = IdxField;
        }

        /// <summary>
        /// 数据缓存有效期时长
        /// </summary>
        public TimeSpan Expired { get; set; }

        public String TableName { get { return _tabName; } }
        public String IdField { get { return _idField; } }
        public Static_Cache_Staus Status { get { return _satus; } }

        private void LoadFreshData()
        {
            if (_satus != Static_Cache_Staus.Idel || IsManualLoad)
                return;
            lock (this)
            {
                if (_satus != Static_Cache_Staus.Idel)
                    return;
                _satus = Static_Cache_Staus.Loading;
                ThreadPool.QueueUserWorkItem(this.LoadData);
            }
        }

        void LoadData(object s)
        {
            int minId = 0;
            if (_data == null)
                _data = new Dictionary<IDX, T>();
            else if (_data.Count > 0)
                minId = _data.Values.Min(e => (int)e[_idField]);

            var q = new Shotgun.Model.List.LightDataQueries<T>(_tabName, _idField, null, new T().Schema);
            if (minId > 0)
                q.Filter.AndFilters.Add(_idField, minId, Shotgun.Model.Filter.EM_DataFiler_Operator.More);
            q.SortKey.Add(_idField, Shotgun.Model.Filter.EM_SortKeyWord.asc);
            q.PageSize = 1000;
            q.dBase = CreateDBase();
            System.Diagnostics.Stopwatch st = new System.Diagnostics.Stopwatch();
            st.Start();
            base.Add(this);
            try
            {
                var RowCount = q.TotalCount;
                var PageCount = RowCount / q.PageSize + (RowCount % q.PageSize == 0 ? 0 : 1);
                for (var i = 1; i <= PageCount; i++)
                {
                    q.CurrentPage = i;
                    var items = q.GetDataList();
                    items.ForEach(e => _data[(IDX)e[_indexField]] = e);
                }
                this._expired = DateTime.Now.Add(this.Expired);
                this._satus = Static_Cache_Staus.AllLoad;

            }
            catch (System.Data.DataException ex)
            {
                WriteLog(ex.Message);
                _satus = Static_Cache_Staus.Idel;
                this._expired = DateTime.Now.Add(this.Expired);
            }
            finally
            {
                IDisposable dp = (IDisposable)q.dBase;
                if (dp != null)
                    dp.Dispose();
            }
            WriteLog(true, (int)st.ElapsedMilliseconds, _data.Count);

        }

        /// <summary>
        /// 检查数据是否过期，如果过期激发重新加载，并丢弃过期数据
        /// </summary>
        private Dictionary<IDX, T> CheckExpired()
        {
            if (_data == null)
            {
                LoadFreshData();
                return null;
            }
            if (DateTime.Now > _expired)
            {
                ClearCache();
                LoadFreshData();
                return null;
            }
            return _data;

        }

        public IEnumerable<T> GetCacheData(bool iFull)
        {
            var tData = CheckExpired();
            if (tData == null)
                return null;
            if (iFull && _satus != Static_Cache_Staus.AllLoad)
                return null;
            return tData.Values;
        }

        /// <summary>
        /// 根据主索引，快速查找数据 （非完整数据）
        /// </summary>
        /// <param name="idx"></param>
        /// <returns></returns>
        public T GetDataByIdx(IDX idx)
        {
            if (_data == null)
                return null;
            var tData = CheckExpired();

            if (tData == null || !tData.ContainsKey(idx))
                return null;

            return tData[idx];
        }

        /// <summary>
        /// 找查数据（非完整数据）
        /// </summary>
        /// <param name="func"></param>
        /// <returns></returns>
        public T FindFirstData(Func<T, bool> func)
        {
            var tData = CheckExpired();
            if (tData == null)
                return null;
            try
            {
                return tData.Values.First(func);
            }
            catch
            {
                return null;
            }

        }

        private Shotgun.Database.IBaseDataClass2 CreateDBase()
        {
            return new Shotgun.Database.DBDriver().CreateDBase();
        }

        /// <summary>
        /// 从外部插入单条数据，通常发生在，数据缓存成功之后，新增的数据
        /// 注意Fields要全部读取的，否则在二次取出使用时可能出现问题
        /// </summary>
        /// <param name="data"></param>
        public void InsertItem(T data)
        {
            if (data == null)
                return;
            Dictionary<IDX, T> dt;
            if (_satus == Static_Cache_Staus.Idel)
            {
                dt = this._data;
                if (dt == null)
                {
                    dt = this._data = new Dictionary<IDX, T>();
                    this._expired = DateTime.Now.Add(this.Expired);
                }
            }
            else
            {
                dt = CheckExpired();
                if (dt == null || _satus != Static_Cache_Staus.AllLoad)
                    return;
            }
            base.Add(this);

            dt[(IDX)data[this._indexField]] = data;
            WriteLog(true, 0, 1);
        }

        void WriteLog(bool iAdd, int elapsedMs, int count)
        {
            string msg = string.Format("{0} cache, count {1}, elapsed {2}ms",
                (iAdd ? "add" : "remove"), count, elapsedMs);
            WriteLog(msg);
        }

        void WriteLog(string msg)
        {
            Shotgun.Library.SimpleLogRecord.WriteLog("static_cache", _tabName + " " + msg);
        }

        /// <summary>
        /// 清除缓存数据
        /// </summary>
        [STAThread]
        public override void ClearCache()
        {
            OnExpired();
            _data = null;
            _satus = Static_Cache_Staus.Idel;
            base.Remove(this);
            WriteLog(false, 0, 0);
        }

        private void OnExpired()
        {
            if (_onExpired == null)
                return;

            try
            {
                _onExpired(_data.Values);
            }
#if !DEBUG
            catch(Exception ex)
            {
                WriteLog(ex.ToString());
            }
#endif
            finally { }
        }

        public void SetExpriedProc(Action<IEnumerable<T>> func)
        {
            _onExpired = func;
        }

        /// <summary>
        /// 是否以手动方式加载代码（不会自动执行LoadData）
        /// </summary>
        public bool IsManualLoad { get; set; }

        /// <summary>
        /// 检查数据是否过期，不会激发重新加载操作
        /// </summary>
        /// <returns></returns>
        protected override bool IsExpired()
        {
            if (_data == null)
                return true;

            if (DateTime.Now > _expired)
                return true;

            return false;
        }
    }
}
