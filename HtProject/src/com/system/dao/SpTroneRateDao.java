package com.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.constant.Constant;
import com.system.database.JdbcControl;
import com.system.database.QueryCallBack;
import com.system.model.SpTroneRateModel;
import com.system.util.StringUtil;

public class SpTroneRateDao
{
	public Map<String,Object> loadSpTroneRate(String keyWord,int pageIndex)
	{
		String sql = "select " + Constant.CONSTANT_REPLACE_STRING + " FROM daily_config.tbl_sp_trone_rate a  LEFT JOIN daily_config.`tbl_sp_trone` b ON a.`sp_trone_id` = b.`id` LEFT JOIN daily_config.tbl_sp c ON b.`sp_id` = c.id WHERE 1=1";
		
		if(!StringUtil.isNullOrEmpty(keyWord))
		{
			sql += " AND (b.`name` LIKE '%" + keyWord + "%' OR c.`short_name` LIKE '%" + keyWord + "%' OR c.`full_name` LIKE '%" + keyWord + "%')";
		}
		
		String limit = " limit "  + Constant.PAGE_SIZE*(pageIndex-1) + "," + Constant.PAGE_SIZE;
		
		sql += " order by a.id desc,b.id desc,a.start_date desc ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		JdbcControl control = new JdbcControl();
		
		map.put("rows",control.query(sql.replace(Constant.CONSTANT_REPLACE_STRING, "count(*)"), new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if(rs.next())
					return rs.getInt(1);
				
				return 0;
			}
		}));
		
		map.put("list", control.query(sql.replace(Constant.CONSTANT_REPLACE_STRING, " a.*,b.`id` sp_trone_id,b.`name` sp_trone_name,c.id sp_id,c.`short_name` sp_name ,b.`jiesuanlv` ori_rate") + limit, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<SpTroneRateModel> list = new ArrayList<SpTroneRateModel>();
				
				while(rs.next())
				{
					SpTroneRateModel model = new SpTroneRateModel();
					
					model.setId(rs.getInt("id"));
					model.setSpId(rs.getInt("sp_id"));
					model.setSpTroneId(rs.getInt("sp_trone_id"));
					model.setRate(rs.getFloat("rate"));
					model.setDefaultRate(rs.getFloat("ori_rate"));
					model.setSpName(StringUtil.getString(rs.getString("sp_name"), ""));
					model.setSpTroneName(StringUtil.getString(rs.getString("sp_trone_name"), ""));
					model.setRemark(StringUtil.getString(rs.getString("remark"), ""));
					model.setStartDate(StringUtil.getString(rs.getString("start_date"), ""));
					model.setEndDate(StringUtil.getString(rs.getString("end_date"), ""));
					
					list.add(model);
				}
				
				return list;
			}
		}));
		
		return map;
	}
	
	public void addSpTroneRate(SpTroneRateModel model)
	{
		String sql = "insert into daily_config.tbl_sp_trone_rate(sp_trone_id,start_date,end_date,rate,remark) values(?,?,?,?,?)";
		
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(1, model.getSpTroneId());
		map.put(2, model.getStartDate());
		map.put(3, model.getEndDate());
		map.put(4, model.getRate());
		map.put(5, model.getRemark());
		
		new JdbcControl().execute(sql, map);
	}
	
	public void updateSpTroneRate(SpTroneRateModel model)
	{
		String sql = "update daily_config.tbl_sp_trone_rate set rate = ?,remark = ? where id = ?";
		
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(1, model.getRate());
		map.put(2, model.getRemark());
		map.put(3, model.getId());
		
		new JdbcControl().execute(sql, map);
	}
	
	public void delSpTroneRate(SpTroneRateModel model)
	{
		String sql = "delete from daily_config.tbl_sp_trone_rate where id = " + model.getId();
		new JdbcControl().execute(sql); 
	}
	
	public SpTroneRateModel loadSpTroneRateById(int id)
	{
		String sql = "select a.*,b.`id` sp_trone_id,b.`name` sp_trone_name,c.id sp_id,c.`short_name` sp_name ,b.`jiesuanlv` ori_rate FROM daily_config.tbl_sp_trone_rate a  LEFT JOIN daily_config.`tbl_sp_trone` b ON a.`sp_trone_id` = b.`id` LEFT JOIN daily_config.tbl_sp c ON b.`sp_id` = c.id WHERE a.id =" + id;
		
		return (SpTroneRateModel)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if(rs.next())
				{
					SpTroneRateModel model = new SpTroneRateModel();
					
					model.setId(rs.getInt("id"));
					model.setSpId(rs.getInt("sp_id"));
					model.setSpTroneId(rs.getInt("sp_trone_id"));
					model.setRate(rs.getFloat("rate"));
					model.setDefaultRate(rs.getFloat("ori_rate"));
					model.setSpName(StringUtil.getString(rs.getString("sp_name"), ""));
					model.setSpTroneName(StringUtil.getString(rs.getString("sp_trone_name"), ""));
					model.setRemark(StringUtil.getString(rs.getString("remark"), ""));
					model.setStartDate(StringUtil.getString(rs.getString("start_date"), ""));
					model.setEndDate(StringUtil.getString(rs.getString("end_date"), ""));
					
					return model;
				}
				return null;
			}
		});
	}
	
	
	public boolean isRateDateCross(int spTroneId,String startDate,String endDate)
	{
		String sql = "SELECT count(*) FROM daily_config.tbl_sp_trone_rate	WHERE sp_trone_id = "
				+ spTroneId + " AND ( ('" + startDate + "' >= start_date AND '"
				+ startDate + "' <= end_date) OR('" + endDate
				+ "' >= start_date AND '" + endDate + "' <= end_date) OR('"
				+ startDate + "' <= start_date AND '" + endDate
				+ "' >= end_date) );";
		
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
	
	public static void main(String[] args)
	{
		new SpTroneRateDao().isRateDateCross(1, "2016-04-03", "2016-04-22");
	}
	
	
}
