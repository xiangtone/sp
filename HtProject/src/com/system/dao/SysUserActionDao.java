package com.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.system.cache.RightConfigCacheMgr;
import com.system.database.ConnectionCallBack;
import com.system.database.JdbcControl;

public class SysUserActionDao
{
	public void recordUserAction(String sql)
	{
		int userId = RightConfigCacheMgr.threadPolls.get(Thread.currentThread().getId());
		
		final String addsql = "INSERT INTO daily_log.`tbl_user_action_log`(user_id,execsql) value (" + userId + ",'" + sql.replaceAll("'", "''") + "')";
		
		new JdbcControl().getConnection(new ConnectionCallBack()
		{
			@Override
			public void onConnectionCallBack(Statement stmt, ResultSet rs)
					throws SQLException
			{
				stmt.execute(addsql);
			}
		});
	}
}
