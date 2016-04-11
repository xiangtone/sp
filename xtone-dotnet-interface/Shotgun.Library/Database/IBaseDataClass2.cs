﻿using System;
using System.Collections.Generic;
using System.Text;
using System.Data.SqlClient;
using System.Data;

namespace Shotgun.Database
{
    public interface IBaseDataClass2
    {

        IDbConnection Connection { get; }

        IDbTransaction Transaction { get; }
        /// <summary>
        /// 启动事务
        /// </summary>
        void BeginTransaction();

        /// <summary>
        /// 回滚事务
        /// </summary>
        void Rollback();

        /// <summary>
        /// 提交事务
        /// </summary>
        void Commit();
        /// <summary>
        /// 当前是否已经启动事务
        /// </summary>
        bool IsTransaction { get; }


        /// <summary>
        /// 创建一个sqlCommand实例
        /// 如须启事备请先开启
        /// </summary>
        /// <returns></returns>
        IDbCommand Command();

        /// <summary>
        /// 执行SQL并反回数控表
        /// </summary>
        /// <param name="sql"></param>
        /// <returns></returns>
        DataTable GetDataTable(string sql);

        void TableFill(string sql, DataTable table);

        /// <summary>
        /// 执行SQL语句，返回语句影响的条数
        /// </summary>
        /// <param name="sql"></param>
        /// <returns></returns>
        int ExecuteNonQuery(string sql);

        /// <summary>
        /// 执行SQL语句，返回首行首列的数据。
        /// </summary>
        /// <param name="sql"></param>
        /// <returns></returns>
        object ExecuteScalar(string sql);

        /// <summary>
        /// 快速数据保存方法
        /// </summary>
        /// <param name="DataRow">IUpatedataInfo 数据对像</param>
        /// <returns>false：存在一般性错误,如，无需更新数据</returns>
        bool SaveData(IUpatedataInfo DataRow);

        /// <summary>
        /// 永久删除主键指定的记录
        /// </summary>
        /// <param name="DataRow">IUpatedataInfo 数据对像</param>
        /// <returns>false：存在一般性错误,如，指的记录不存在</returns>
        bool DeleteData(IUpatedataInfo DataRow);


        string SqlEncode(string txt);
        string SqlEncode(System.Web.UI.ITextControl txtContrl);
        string FieldEncode(string txt);

        IDataReader ExecuteReader(IDbCommand cmd);

        string GetPageTSql(Shotgun.Model.List.IDBSQLHelper list);

    }
}
