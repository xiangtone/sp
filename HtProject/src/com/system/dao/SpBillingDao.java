package com.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.system.database.JdbcControl;
import com.system.database.QueryCallBack;

public class SpBillingDao
{
	public boolean isSpBillingCross(int spId,int jsType,String startDate,String endDate)
	{
		String sql = "SELECT COUNT(*) FROM daily_config.`tbl_sp_billing` WHERE sp_id = " + spId + " AND js_type = " + jsType;
		sql += " AND (('" + startDate + "' >= start_date AND '" + startDate + "' <= end_date) OR('" + endDate + "' >= start_date AND '" 
				+ endDate + "' <= end_date) OR('" + startDate + "' <= start_date AND '" + endDate + "' >= end_date))";
		
		int rows = (Integer)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if(rs.next())
					return rs.getInt(1);
				
				return 0;
			}
		});
		
		return rows>0;
	}
}
