package com.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.system.database.JdbcLvControl;
import com.system.database.QueryCallBack;
import com.system.model.LvMrModel;
import com.system.util.StringUtil;

public class LvMrDao {
	/**
	 * lv单日数据
	 * @param tableName
	 * @param date
	 * @param payType
	 * @param userId
	 * @return
	 */
	
	public Map<String, Object> getLvMrTodayData(String tableName,String date,int payType,int userId)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		String query = "";
		if(payType>=0){
			query+=" and mr.pay_type="+payType;
		}
		
		String sql="SELECT mr.*,lc.id as lvid,lc.appkey as lvappkey,lc.hold_percent,lc.user_id,lc.create_date as lv_create_date FROM little_video_log.tbl_mr_"+tableName+" mr LEFT JOIN daily_config.tbl_lv_channel lc ON mr.channel=lc.channel "
				+ "where 1=1 "
				+ "and mr.status=1 "
				+ "and mr.create_date>='"+date+" 00:00:00' and mr.create_date<='"+date+" 23:59:59' "
				+ "and lc.user_id="+userId;
		
		
		sql+=query;
		
		JdbcLvControl control = new JdbcLvControl();
		
		final List<Object> datalist = new ArrayList<Object>();
		
		map.put("list", control.query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<LvMrModel> list = new ArrayList<LvMrModel>();
				float dayAmount=0; 
				while(rs.next())
				{
					LvMrModel model = new LvMrModel();
					model.setOrderId(StringUtil.getString(rs.getString("orderid"), ""));
					model.setPayType(rs.getInt("pay_type"));
					model.setPrice(rs.getInt("price"));
					model.setCreateDate(StringUtil.getString(rs.getString("create_date"), ""));
					dayAmount+=rs.getInt("price");
					list.add(model);
				}
				dayAmount=dayAmount/100;
				datalist.add(dayAmount);
				return list;
			}
		}));
		map.put("dayAmount", datalist.get(0));
		return map;
	}
	/**
	 * Lv多日数据，按日期分组
	 * @param tableName
	 * @param date
	 * @param payType
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getLvMrDaysData(String tableName,String startDate,String endDate,int payType,int userId)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		String query = "";
		if(payType>=0){
			query+=" and mr.pay_type="+payType;
		}
		
		String sql="SELECT SUM(mr.price) as day_total,DATE_FORMAT(mr.create_date,'%Y-%m-%d') AS day_date FROM little_video_log.tbl_mr_"+tableName+" mr "
				+ "LEFT JOIN daily_config.tbl_lv_channel lc ON mr.channel=lc.channel "
				+ "WHERE 1=1 AND mr.status=1 "
				+ "AND lc.user_id="+userId;
		sql+=query;
		sql+=" AND mr.create_date>='"+startDate+" 00:00:00' AND mr.create_date<='"+endDate+" 23:59:59'";
		sql+=" GROUP BY DATE_FORMAT(mr.create_date,'%Y-%m-%d')";
		
		JdbcLvControl control = new JdbcLvControl();
		
		final List<Object> datalist = new ArrayList<Object>();
		
		map.put("list", control.query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<LvMrModel> list = new ArrayList<LvMrModel>();
				float allAmount=0; 
				while(rs.next())
				{
					LvMrModel model = new LvMrModel();
					model.setDayAmount(rs.getFloat("day_total")/100);
					model.setDateDay(StringUtil.getString(rs.getString("day_date"),""));
					allAmount+=model.getDayAmount();
					list.add(model);
				}
				datalist.add(allAmount);
				return list;
			}
		}));
		map.put("allAmount", datalist.get(0));
		return map;
	}
}
