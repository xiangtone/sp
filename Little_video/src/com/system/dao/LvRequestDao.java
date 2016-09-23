
package com.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;

import com.system.database.JdbcControlLvLog;
import com.system.database.QueryCallBack;
import com.system.model.LvRequestModel;
import com.system.util.StringUtil;

public class LvRequestDao
{
	public LvRequestModel getRequestByOrderId(String orderId)
	{
		if (orderId.length() < 6)
			return null;
		String tab = getTableName(orderId);
		String sql = "select * from little_video_log." + tab + " where orderId="
				+ StringUtil.sqlEncode(orderId, true);

		QueryCallBack qcb = new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if (!rs.next())
					return null;
				LvRequestModel m = new LvRequestModel();
				m.setChannelId(rs.getInt("channel_id"));
				m.setCreateDate(rs.getDate("create_date"));
				m.setId(rs.getInt("id"));
				m.setImei(StringUtil.getString(rs.getString("imei"), ""));
				m.setOrderId(
						StringUtil.getString(rs.getString("order_id"), ""));
				m.setPayType(rs.getShort("pay_type"));
				m.setPrice(rs.getInt("price"));

				return m;
			}
		};

		JdbcControlLvLog db = new JdbcControlLvLog();

		return (LvRequestModel) db.query(sql, qcb);
	}

	public void InsertOrderId(LvRequestModel m)
	{
		String orderId = m.getOrderId();
		if (orderId.length() < 6)
			return;
		String tab = getTableName(orderId);
		String sql = "Insert into little_video_log." + tab
				+ "(imei,pay_type,orderid,channel_id,price) values(?,?,?,?,?)";
		HashMap<Integer, Object> map = new HashMap<Integer, Object>();
		int i = 0;
		map.put(++i, m.getImei());
		map.put(++i, m.getPayType());
		map.put(++i, m.getOrderId());
		map.put(++i, m.getChannelId());
		map.put(++i, m.getPrice());
		JdbcControlLvLog jdbc = new JdbcControlLvLog();
		m.setId(jdbc.insertWithGenKey(sql, map));
	}

	private static String getTableName(String orderId)
	{
		int m = Integer.parseInt(orderId.substring(0, 2));
		Calendar cal = Calendar.getInstance();
		int cMonth = cal.get(Calendar.MONTH) + 1;
		int cYear = cal.get(Calendar.YEAR);
		if (cMonth < m)
			cYear--;
		return String.format("tbl_rquest_%d%02d", cYear, m);
	}

}
