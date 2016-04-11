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
import com.system.model.CpSpTroneRateModel;
import com.system.util.StringUtil;

public class CpSpTroneRateDao
{
	public Map<String, Object> loadCpSpTroneRate(String keyWord,int pageIndex)
	{
		String sql = "select " + Constant.CONSTANT_REPLACE_STRING + " FROM daily_config.`tbl_cp_trone_rate` a ";
		
		sql += " LEFT JOIN daily_config.`tbl_cp` b ON a.`cp_id` = b.`id`";
		sql += " LEFT JOIN daily_config.`tbl_sp_trone` c ON a.`sp_trone_id` = c.`id`";
		sql += " LEFT JOIN daily_config.`tbl_sp` d ON c.`sp_id` = d.`id`";
		sql += " WHERE 1=1";
		
		if(!StringUtil.isNullOrEmpty(keyWord))
		{
			sql += " AND (b.`short_name` LIKE '%" + keyWord + "%' OR b.`full_name` LIKE '%" + keyWord + "%' ";
			sql += " OR c.`name` LIKE '%" + keyWord + "%' OR d.`short_name` LIKE '%" + keyWord + "%' OR d.`full_name` LIKE '%" + keyWord + "%')";
		}
		
		String limit = " limit "  + Constant.PAGE_SIZE*(pageIndex-1) + "," + Constant.PAGE_SIZE;
		
		sql += " order by a.id desc ";
		
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
		
		map.put("list", control.query(sql.replace(Constant.CONSTANT_REPLACE_STRING, " a.*,b.`short_name` cp_name,c.`name` sp_trone_name,d.`id` sp_id,d.`short_name` sp_name ") + limit, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<CpSpTroneRateModel> list = new ArrayList<CpSpTroneRateModel>();
				
				while(rs.next())
				{
					CpSpTroneRateModel model = new CpSpTroneRateModel();
					
					model.setId(rs.getInt("id"));
					model.setCpId(rs.getInt("cp_id"));
					model.setSpTroneId(rs.getInt("sp_trone_id"));
					model.setRate(rs.getFloat("rate"));
					model.setSpName(StringUtil.getString(rs.getString("sp_name"), ""));
					model.setCpName(StringUtil.getString(rs.getString("cp_name"), ""));
					model.setSpTroneName(StringUtil.getString(rs.getString("sp_trone_name"), ""));
					model.setDayLimit(rs.getFloat("day_limit"));
					model.setMonthLimit(rs.getFloat("month_limit"));
					list.add(model);
				}
				
				return list;
			}
		}));
		
		return map;
	}
	
	public CpSpTroneRateModel loadCpSpTroneRateById(int id)
	{
		String sql = "select a.*,b.`short_name` cp_name,c.`name` sp_trone_name,d.`id` sp_id,d.`short_name` sp_name FROM daily_config.`tbl_cp_trone_rate` a ";
		
		sql += " LEFT JOIN daily_config.`tbl_cp` b ON a.`cp_id` = b.`id`";
		sql += " LEFT JOIN daily_config.`tbl_sp_trone` c ON a.`sp_trone_id` = c.`id`";
		sql += " LEFT JOIN daily_config.`tbl_sp` d ON c.`sp_id` = d.`id`";
		sql += " WHERE a.id = " + id;
		
		return (CpSpTroneRateModel)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if(rs.next())
				{
					CpSpTroneRateModel model = new CpSpTroneRateModel();
					
					model.setId(rs.getInt("id"));
					model.setCpId(rs.getInt("cp_id"));
					model.setSpTroneId(rs.getInt("sp_trone_id"));
					model.setRate(rs.getFloat("rate"));
					model.setSpName(StringUtil.getString(rs.getString("sp_name"), ""));
					model.setCpName(StringUtil.getString(rs.getString("cp_name"), ""));
					model.setSpTroneName(StringUtil.getString(rs.getString("sp_trone_name"), ""));
					model.setDayLimit(rs.getFloat("day_limit"));
					model.setMonthLimit(rs.getFloat("month_limit"));
					
					return model;
				}
				return null;
			}
		});
	}
	
	public void addCpSpTroneRate(CpSpTroneRateModel model)
	{
		String sql = "insert into daily_config.tbl_cp_trone_rate(cp_id,sp_trone_id,rate) values(?,?,?)";
		
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(1, model.getCpId());
		map.put(2, model.getSpTroneId());
		map.put(3, model.getRate());
		
		new JdbcControl().execute(sql,map);
	}
	
	public void updateCpSpTroneRate(CpSpTroneRateModel model)
	{
		String sql = "udpate daily_config.tbl_cp_trone_rate set cp_id = ?,sp_trone_id = ?,rate = ? where id = ?";
		
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(1, model.getCpId());
		map.put(2, model.getSpTroneId());
		map.put(3, model.getRate());
		map.put(4, model.getId());
		
		new JdbcControl().execute(sql,map);
	}
	
	public void updateCpSpTroneLimit(CpSpTroneRateModel model)
	{
		String sql = "update daily_config.tbl_cp_trone_rate set day_limit = ? , month_limit = ? , rate = ? where id = ?";
		
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(1, model.getDayLimit());
		map.put(2, model.getMonthLimit());
		map.put(3, model.getRate());
		map.put(4, model.getId());
		
		new JdbcControl().execute(sql,map);
	}
	
	public void updateCpSpTroneRate(int id,float rate)
	{
		String sql = "update daily_config.tbl_cp_trone_rate set rate = ? where id = ? ";
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(1,rate);
		map.put(2, id);
		new JdbcControl().execute(sql,map);
	}
	
	public void delCpSpTroneRate(int id)
	{
		String sql = "delete from daily_config.tbl_cp_trone_rate where id = " + id;
		new JdbcControl().execute(sql);
	}
	
	public void syncUnAddCpSpTroneRate()
	{
		String sql = "INSERT INTO daily_config.`tbl_cp_trone_rate` (cp_id,sp_trone_id,rate)";
		sql += " SELECT a.cp_id,a.sp_trone_id,0.00 FROM (";
		sql += " SELECT d.id cp_id,c.id sp_trone_id";
		sql += " FROM daily_config.`tbl_trone_order` a";
		sql += " LEFT JOIN daily_config.`tbl_trone` b ON a.`trone_id` = b.`id`";
		sql += " LEFT JOIN daily_config.`tbl_sp_trone` c ON b.`sp_trone_id` = c.`id`";
		sql += " LEFT JOIN daily_config.`tbl_cp` d ON a.`cp_id` = d.`id`";
		sql += " WHERE d.id <> 34";
		sql += " GROUP BY c.id,d.id";
		sql += " ORDER BY a.id ASC ) a";
		sql += " LEFT JOIN daily_config.`tbl_cp_trone_rate` b ON a.cp_id = b.`cp_id` AND a.sp_trone_id = b.`sp_trone_id`";
		sql += " WHERE b.id IS NULL AND a.sp_trone_id IS NOT NULL AND a.cp_id IS NOT NULL;";
		new JdbcControl().execute(sql);
	}
	
}
