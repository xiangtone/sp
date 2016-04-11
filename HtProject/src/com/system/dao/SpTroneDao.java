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
import com.system.model.SpTroneModel;
import com.system.util.StringUtil;

public class SpTroneDao
{
	public Map<String, Object> loadSpTroneList(int pageIndex,int spId,int userId,String spTroneName)
	{
		String query = " a.*,b.short_name,c.`name_cn`,d.id trone_api_id,d.name trone_api_name,e.nick_name commerce_name ";
		
		String sql = "SELECT " + Constant.CONSTANT_REPLACE_STRING;
		sql += " FROM daily_config.`tbl_sp_trone` a";
		sql += " LEFT JOIN daily_config.`tbl_sp` b ON a.`sp_id` = b.`id`";
		sql += " LEFT JOIN daily_config.`tbl_operator` c ON a.operator = c.`id` ";
		sql += " LEFT JOIN daily_config.tbl_sp_trone_api d on a.trone_api_id = d.id";
		sql += " LEFT JOIN daily_config.tbl_user e on b.commerce_user_id = e.id";
		sql += " where 1=1 ";
		
		if(spId>0)
		{
			sql +=  " and b.id =" + spId;
		}
		
		if(userId>0)
		{
			sql  += " and e.id = " + userId;
		}
		
		if(!StringUtil.isNullOrEmpty(spTroneName))
		{
			sql += " and (a.name like '%" + spTroneName + "%' or e.name like '%" + spTroneName + "%' )";
		}
		
		String limit = " limit "  + Constant.PAGE_SIZE*(pageIndex-1) + "," + Constant.PAGE_SIZE;
		
		//String orders = " order by  convert(b.short_name using gbk),convert(a.name using gbk) asc ";
		
		String orders = " order by  a.id desc ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("rows",new JdbcControl().query(sql.replace(Constant.CONSTANT_REPLACE_STRING, "count(*)"), new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if(rs.next())
					return rs.getInt(1);
				
				return 0;
			}
		}));
		
		map.put("list",new JdbcControl().query(sql.replace(Constant.CONSTANT_REPLACE_STRING, query) + orders + limit, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<SpTroneModel> list = new ArrayList<SpTroneModel>();
				
				while(rs.next())
				{
					SpTroneModel model = new SpTroneModel();
					
					model.setId(rs.getInt("id"));
					model.setSpId(rs.getInt("sp_id"));
					model.setSpName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setSpTroneName(StringUtil.getString(rs.getString("name"), ""));
					model.setOperator(rs.getInt("operator"));
					model.setJieSuanLv(rs.getFloat("jiesuanlv"));
					model.setOperatorName(StringUtil.getString(rs.getString("name_cn"), ""));
					model.setTroneType(rs.getInt("trone_type"));
					model.setStatus(rs.getInt("status"));
					model.setTroneApiId(rs.getInt("trone_api_id"));
					model.setCommerceUserName(StringUtil.getString(rs.getString("commerce_name"),""));
					model.setTroneApiName(StringUtil.getString(rs.getString("trone_api_name"), ""));
					model.setDayLimit(rs.getFloat("day_limit"));
					model.setMonthLimit(rs.getFloat("month_limit"));
					model.setUserDayLimit(rs.getFloat("user_day_limit"));
					model.setUserMonthLimit(rs.getFloat("user_month_limit"));
					
					list.add(model);
				}
				
				return list;
			}
		}));
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public List<SpTroneModel> loadSpTroneList()
	{
		String sql = "SELECT  a.*,b.short_name,c.`name_cn`,d.id trone_api_id,d.name trone_api_name   ";
		sql += " FROM daily_config.`tbl_sp_trone` a";
		sql += " LEFT JOIN daily_config.`tbl_sp` b ON a.`sp_id` = b.`id`";
		sql += " LEFT JOIN daily_config.`tbl_operator` c ON a.operator = c.`id`";
		sql += " LEFT JOIN daily_config.tbl_sp_trone_api d on a.trone_api_id = d.id";
		
		
		return (List<SpTroneModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<SpTroneModel> list = new ArrayList<SpTroneModel>();
				
				while(rs.next())
				{
					SpTroneModel model = new SpTroneModel();
					
					model.setId(rs.getInt("id"));
					model.setSpId(rs.getInt("sp_id"));
					model.setSpName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setSpTroneName(StringUtil.getString(rs.getString("name"), ""));
					model.setOperator(rs.getInt("operator"));
					model.setJieSuanLv(rs.getFloat("jiesuanlv"));
					model.setOperatorName(StringUtil.getString(rs.getString("name_cn"), ""));
					model.setTroneType(rs.getInt("trone_type"));
					model.setStatus(rs.getInt("status"));
					model.setTroneApiId(rs.getInt("trone_api_id"));
					model.setTroneApiName(StringUtil.getString(rs.getString("trone_api_name"), ""));
					model.setDayLimit(rs.getFloat("day_limit"));
					model.setMonthLimit(rs.getFloat("month_limit"));
					model.setUserDayLimit(rs.getFloat("user_day_limit"));
					model.setUserMonthLimit(rs.getFloat("user_month_limit"));
					
					list.add(model);
				}
				
				return list;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<SpTroneModel> loadSpTroneList(int spId)
	{
		String sql = "SELECT  a.*,b.short_name,c.`name_cn`,d.id trone_api_id,d.name trone_api_name   ";
		sql += " FROM daily_config.`tbl_sp_trone` a";
		sql += " LEFT JOIN daily_config.`tbl_sp` b ON a.`sp_id` = b.`id`";
		sql += " LEFT JOIN daily_config.`tbl_operator` c ON a.operator = c.`id`";
		sql += " LEFT JOIN daily_config.tbl_sp_trone_api d on a.trone_api_id = d.id";
		sql += " where b.id =" + spId;
		
		return (List<SpTroneModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<SpTroneModel> list = new ArrayList<SpTroneModel>();
				
				while(rs.next())
				{
					SpTroneModel model = new SpTroneModel();
					
					model.setId(rs.getInt("id"));
					model.setSpId(rs.getInt("sp_id"));
					model.setSpName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setSpTroneName(StringUtil.getString(rs.getString("name"), ""));
					model.setOperator(rs.getInt("operator"));
					model.setJieSuanLv(rs.getFloat("jiesuanlv"));
					model.setOperatorName(StringUtil.getString(rs.getString("name_cn"), ""));
					model.setTroneType(rs.getInt("trone_type"));
					model.setStatus(rs.getInt("status"));
					model.setTroneApiId(rs.getInt("trone_api_id"));
					model.setTroneApiName(StringUtil.getString(rs.getString("trone_api_name"), ""));
					model.setDayLimit(rs.getFloat("day_limit"));
					model.setMonthLimit(rs.getFloat("month_limit"));
					model.setUserDayLimit(rs.getFloat("user_day_limit"));
					model.setUserMonthLimit(rs.getFloat("user_month_limit"));
					
					list.add(model);
				}
				
				return list;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<SpTroneModel> loadCpTroneList(int userId)
	{
		String sql = " SELECT c.id,c.`name`,c.`trone_type`,c.operator ";
		sql += " FROM daily_config.`tbl_trone_order` a";
		sql += " LEFT JOIN daily_config.`tbl_trone` b ON a.`trone_id` = b.`id`";
		sql += " LEFT JOIN daily_config.`tbl_sp_trone` c ON b.`sp_trone_id` = c.`id`";
		sql += " LEFT JOIN daily_config.tbl_cp e ON a.`cp_id` = e.`id`";
		sql += " WHERE e.`user_id` = " + userId;
		sql += " GROUP BY c.id;";
		
		
		return (List<SpTroneModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<SpTroneModel> list = new ArrayList<SpTroneModel>();
				
				while(rs.next())
				{
					SpTroneModel model = new SpTroneModel();
					
					model.setId(rs.getInt("id"));
					//model.setSpId(rs.getInt("sp_id"));
					//model.setSpName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setSpTroneName(StringUtil.getString(rs.getString("name"), ""));
					model.setOperator(rs.getInt("operator"));
					//model.setJieSuanLv(rs.getFloat("jiesuanlv"));
					//model.setOperatorName(StringUtil.getString(rs.getString("name_cn"), ""));
					model.setTroneType(rs.getInt("trone_type"));
					
					list.add(model);
				}
				
				return list;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<SpTroneModel> loadTroneListByCpid(int cpId)
	{
		String sql = " SELECT c.id,c.`name`,c.`trone_type`,c.operator,c.provinces ";
		sql += " FROM daily_config.`tbl_trone_order` a";
		sql += " LEFT JOIN daily_config.`tbl_trone` b ON a.`trone_id` = b.`id`";
		sql += " LEFT JOIN daily_config.`tbl_sp_trone` c ON b.`sp_trone_id` = c.`id`";
		sql += " LEFT JOIN daily_config.tbl_cp e ON a.`cp_id` = e.`id`";
		sql += " WHERE e.`id` = " + cpId + " and c.trone_api_id > 0";
		sql += " GROUP BY c.id;";
		
		
		return (List<SpTroneModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<SpTroneModel> list = new ArrayList<SpTroneModel>();
				
				while(rs.next())
				{
					SpTroneModel model = new SpTroneModel();
					
					model.setId(rs.getInt("id"));
					//model.setSpId(rs.getInt("sp_id"));
					//model.setSpName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setSpTroneName(StringUtil.getString(rs.getString("name"), ""));
					model.setOperator(rs.getInt("operator"));
					//model.setJieSuanLv(rs.getFloat("jiesuanlv"));
					//model.setOperatorName(StringUtil.getString(rs.getString("name_cn"), ""));
					model.setTroneType(rs.getInt("trone_type"));
					model.setProvinces(StringUtil.getString(rs.getString("provinces"), ""));
					
					list.add(model);
				}
				
				return list;
			}
		});
	}
	
	public SpTroneModel loadSpTroneById(int id)
	{
		String sql = "SELECT  a.*,b.short_name,c.`name_cn`,d.id trone_api_id,d.name trone_api_name   ";
		sql += " FROM daily_config.`tbl_sp_trone` a";
		sql += " LEFT JOIN daily_config.`tbl_sp` b ON a.`sp_id` = b.`id`";
		sql += " LEFT JOIN daily_config.`tbl_operator` c ON a.operator = c.`id`";
		sql += " LEFT JOIN daily_config.tbl_sp_trone_api d on a.trone_api_id = d.id";
		sql += " WHERE a.id = " + id;
		
		return (SpTroneModel)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if(rs.next())
				{
					SpTroneModel model = new SpTroneModel();
					
					model.setId(rs.getInt("id"));
					model.setSpId(rs.getInt("sp_id"));
					model.setSpName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setSpTroneName(StringUtil.getString(rs.getString("name"), ""));
					model.setOperator(rs.getInt("operator"));
					model.setJieSuanLv(rs.getFloat("jiesuanlv"));
					model.setOperatorName(StringUtil.getString(rs.getString("name_cn"), ""));
					model.setProvinces(StringUtil.getString(rs.getString("provinces"), ""));
					model.setTroneType(rs.getInt("trone_type"));
					model.setStatus(rs.getInt("status"));
					model.setTroneApiId(rs.getInt("trone_api_id"));
					model.setTroneApiName(StringUtil.getString(rs.getString("trone_api_name"), ""));
					model.setDayLimit(rs.getFloat("day_limit"));
					model.setMonthLimit(rs.getFloat("month_limit"));
					model.setUserDayLimit(rs.getFloat("user_day_limit"));
					model.setUserMonthLimit(rs.getFloat("user_month_limit"));
					
					return model;
				}
				
				return null;
			}
		});
	}
	
	public boolean addSpTrone(SpTroneModel model)
	{
		String sql = "insert into daily_config.tbl_sp_trone(sp_id,name,operator,jiesuanlv,provinces,create_date,trone_type,trone_api_id,status,day_limit,month_limit,user_day_limit,user_month_limit) values("
				+ model.getSpId() + ",'" + model.getSpTroneName() + "',"
				+ model.getOperator() + "," + model.getJieSuanLv() + ",'"
				+ model.getProvinces() + "',now()," + model.getTroneType() + ","+ model.getTroneApiId() +","+ model.getStatus() +"," + model.getDayLimit() + "," 
				+ model.getMonthLimit() + "," + model.getUserDayLimit()  + "," +  model.getUserMonthLimit() + ")";
		return new JdbcControl().execute(sql);
	}
	
	public boolean updateSpTroneModel(SpTroneModel model)
	{
		String sql = "update daily_config.tbl_sp_trone set sp_id = "
				+ model.getSpId() + " ,name = '" + model.getSpTroneName()
				+ "',operator = " + model.getOperator() + ", jiesuanlv = "
				+ model.getJieSuanLv() + ",provinces = '" + model.getProvinces()
				+ "',trone_type = " + model.getTroneType() + ",trone_api_id = " 
				+ model.getTroneApiId() + ",status = " + model.getStatus() + ",day_limit=" + model.getDayLimit() + ",month_limit=" + model.getMonthLimit() + ",user_day_limit=" 
				+ model.getUserDayLimit() + ",user_month_limit=" + model.getUserMonthLimit() + " where id =" + model.getId();
		
		return new JdbcControl().execute(sql);
	}
	
	public boolean updateSpTroneRate(int id,float rate)
	{
		String sql = "update daily_config.tbl_sp_trone set jiesuanlv = " + rate + " where id = " + id;
		
		return new JdbcControl().execute(sql);				
	}
	
	public boolean delSpTrone(int id)
	{
		String sql = "delete from daily_config.tbl_sp_trone where id =" + id;
		return new JdbcControl().execute(sql);
	}
	
}
