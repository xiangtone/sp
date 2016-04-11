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
import com.system.model.CpSpTroneSynModel;
import com.system.model.TroneOrderModel;
import com.system.util.StringUtil;

public class TroneOrderDao
{
	@SuppressWarnings("unchecked")
	public List<TroneOrderModel> loadTroneOrderListQiBa()
	{
		String sql  = "select a.*,b.short_name,c.sp_id,c.trone_name from daily_config.tbl_trone_order a left join daily_config.tbl_cp b on a.cp_id = b.id left join daily_config.tbl_trone c on a.trone_id = c.id order by b.id,c.sp_id asc";
		
		return (List<TroneOrderModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<TroneOrderModel> list = new ArrayList<TroneOrderModel>();
				
				while(rs.next())
				{
					TroneOrderModel model = new TroneOrderModel();
					
					model.setId(rs.getInt("id"));
					model.setTroneId(rs.getInt("trone_id"));
					model.setOrderNum(StringUtil.getString(rs.getString("order_num"), ""));
					model.setCpId(rs.getInt("cp_id"));
					model.setCpShortName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setOrderTroneName(StringUtil.getString(rs.getString("order_trone_name"), ""));
					model.setDynamic(rs.getInt("is_dynamic"));
					model.setPushUrlId(rs.getInt("push_url_id"));
					model.setDisable(rs.getInt("disable"));
					model.setIsUnKnow(rs.getInt("is_unknow"));
					model.setSpId(rs.getInt("sp_id"));
					model.setTroneName(StringUtil.getString(rs.getString("trone_name"), ""));
					
					list.add(model);
				}
				
				return list;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<TroneOrderModel> loadTroneOrderListBySpTroneId(int spTroneId)
	{
		String sql = "select a.*,b.short_name,c.sp_id,c.trone_name,c.price from daily_config.tbl_trone_order a "
				+ " left join daily_config.tbl_cp b on a.cp_id = b.id "
				+ " left join daily_config.tbl_trone c on a.trone_id = c.id "
				+ " LEFT JOIN daily_config.`tbl_sp_trone` d ON c.`sp_trone_id` = d.id "
				+ " WHERE b.id <> 34 and d.trone_type = 1 and d.id = "
				+ spTroneId + "  order by b.short_name asc";
		
		return (List<TroneOrderModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<TroneOrderModel> list = new ArrayList<TroneOrderModel>();
				
				while(rs.next())
				{
					TroneOrderModel model = new TroneOrderModel();
					
					model.setId(rs.getInt("id"));
					model.setTroneId(rs.getInt("trone_id"));
					model.setOrderNum(StringUtil.getString(rs.getString("order_num"), ""));
					model.setCpId(rs.getInt("cp_id"));
					model.setCpShortName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setOrderTroneName(StringUtil.getString(rs.getString("order_trone_name"), ""));
					model.setDynamic(rs.getInt("is_dynamic"));
					model.setPushUrlId(rs.getInt("push_url_id"));
					model.setDisable(rs.getInt("disable"));
					model.setIsUnKnow(rs.getInt("is_unknow"));
					model.setSpId(rs.getInt("sp_id"));
					model.setHoldPercent(rs.getInt("hold_percent"));
					model.setTroneName(StringUtil.getString(rs.getString("trone_name"), ""));
					model.setPrice(rs.getFloat("price"));
					model.setHoldAcount(rs.getInt("hold_start"));
					
					list.add(model);
				}
				
				return list;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<TroneOrderModel> loadTroneOrderListByTroneId(int troneId)
	{
		String sql = "select a.*,b.short_name,c.sp_id,c.trone_name,c.price from daily_config.tbl_trone_order a "
				+ " left join daily_config.tbl_cp b on a.cp_id = b.id "
				+ " left join daily_config.tbl_trone c on a.trone_id = c.id "
				+ " LEFT JOIN daily_config.`tbl_sp_trone` d ON c.`sp_trone_id` = d.id "
				+ " WHERE b.id <> 34 and c.id = "
				+ troneId + "  order by b.short_name asc";
		
		return (List<TroneOrderModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<TroneOrderModel> list = new ArrayList<TroneOrderModel>();
				
				while(rs.next())
				{
					TroneOrderModel model = new TroneOrderModel();
					
					model.setId(rs.getInt("id"));
					model.setTroneId(rs.getInt("trone_id"));
					model.setOrderNum(StringUtil.getString(rs.getString("order_num"), ""));
					model.setCpId(rs.getInt("cp_id"));
					model.setCpShortName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setOrderTroneName(StringUtil.getString(rs.getString("order_trone_name"), ""));
					model.setDynamic(rs.getInt("is_dynamic"));
					model.setPushUrlId(rs.getInt("push_url_id"));
					model.setDisable(rs.getInt("disable"));
					model.setIsUnKnow(rs.getInt("is_unknow"));
					model.setSpId(rs.getInt("sp_id"));
					model.setHoldPercent(rs.getInt("hold_percent"));
					model.setTroneName(StringUtil.getString(rs.getString("trone_name"), ""));
					model.setPrice(rs.getFloat("price"));
					model.setHoldAcount(rs.getInt("hold_start"));
					
					list.add(model);
				}
				
				return list;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<TroneOrderModel> loadTroneOrderList()
	{
		String sql  = "select a.*,b.short_name,c.sp_id,c.trone_name,c.price from daily_config.tbl_trone_order a left join daily_config.tbl_cp b on a.cp_id = b.id left join daily_config.tbl_trone c on a.trone_id = c.id  order by b.short_name asc";
		
		return (List<TroneOrderModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<TroneOrderModel> list = new ArrayList<TroneOrderModel>();
				
				while(rs.next())
				{
					TroneOrderModel model = new TroneOrderModel();
					
					model.setId(rs.getInt("id"));
					model.setTroneId(rs.getInt("trone_id"));
					model.setOrderNum(StringUtil.getString(rs.getString("order_num"), ""));
					model.setCpId(rs.getInt("cp_id"));
					model.setCpShortName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setOrderTroneName(StringUtil.getString(rs.getString("order_trone_name"), ""));
					model.setDynamic(rs.getInt("is_dynamic"));
					model.setPushUrlId(rs.getInt("push_url_id"));
					model.setDisable(rs.getInt("disable"));
					model.setIsUnKnow(rs.getInt("is_unknow"));
					model.setSpId(rs.getInt("sp_id"));
					model.setHoldPercent(rs.getInt("hold_percent"));
					model.setTroneName(StringUtil.getString(rs.getString("trone_name"), ""));
					
					list.add(model);
				}
				
				return list;
			}
		});
	}
	
	public Map<String, Object> loadTroneOrder(int spId,int spTroneId,int cpId, int status,int pageIndex,String keyWord)
	{
		String query = " b.sp_trone_id,c.`name` sp_trone_name,a.*, b.price,d.id sp_id, b.`trone_name`,d.`short_name` sp_name,e.`short_name` cp_name ";
		
		String sql = "select " + Constant.CONSTANT_REPLACE_STRING ;
		sql += " FROM daily_config.`tbl_trone_order` a";
		sql += " LEFT JOIN daily_config.`tbl_trone` b ON a.`trone_id` = b.`id`";
		sql += " LEFT JOIN daily_config.`tbl_sp_trone` c ON b.`sp_trone_id` = c.`id`";
		sql += " LEFT JOIN daily_config.`tbl_sp` d ON c.`sp_id` = d.`id`";
		sql += " LEFT JOIN daily_config.`tbl_cp` e ON a.`cp_id` = e.`id` WHERE 1=1 and e.id <> 34";
		
		String wheres = "";
		
		if(spId>0)
			wheres += " and d.id = " + spId;
		if(spTroneId>0)
			wheres += " and c.id = " + spTroneId;
		if(cpId>0)
			wheres += " and e.id = " + cpId;
		if(status>=0)
			wheres += " and a.disable = " + status;
		
		if(!StringUtil.isNullOrEmpty(keyWord))
		{
			wheres += " and (d.short_name like '%" + keyWord + "%' or d.full_name like '%" + keyWord 
					+ "%' or e.short_name like '%" + keyWord + "%' or e.full_name like '%" + keyWord 
					+ "%' or c.name like '%" + keyWord + "%' or b.orders like '%" + keyWord 
					+ "%' or b.trone_name like '%" + keyWord + "%' or b.trone_num like '%" 
					+ keyWord + "%' OR a.`order_num` LIKE '%"+ keyWord +"%')";
		}
		
		String limit = " limit "  + Constant.PAGE_SIZE*(pageIndex-1) + "," + Constant.PAGE_SIZE;
		
		String orders = " order by a.id desc ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		JdbcControl control = new JdbcControl();
		map.put("rows",control.query(sql.replace(Constant.CONSTANT_REPLACE_STRING, "count(*)") + wheres,new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if(rs.next())
					return rs.getInt(1);
				
				return 0;
			}
		}));
		
		map.put("list", control.query(sql.replace(Constant.CONSTANT_REPLACE_STRING, query) + wheres + orders + limit, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<TroneOrderModel> list = new ArrayList<TroneOrderModel>();
				while(rs.next())
				{
					TroneOrderModel model = new TroneOrderModel();
					
					model.setId(rs.getInt("id"));
					model.setSpId(rs.getInt("sp_id"));
					model.setTroneId(rs.getInt("trone_id"));
					model.setOrderNum(StringUtil.getString(rs.getString("order_num"), ""));
					model.setCpId(rs.getInt("cp_id"));
					model.setCpShortName(StringUtil.getString(rs.getString("cp_name"), ""));
					model.setOrderTroneName(StringUtil.getString(rs.getString("order_trone_name"), ""));
					model.setSpTroneName(StringUtil.getString(rs.getString("sp_trone_name"), ""));
					model.setSpShortName(StringUtil.getString(rs.getString("sp_name"), ""));
					model.setDynamic(rs.getInt("is_dynamic"));
					model.setPushUrlId(rs.getInt("push_url_id"));
					model.setDisable(rs.getInt("disable"));
					model.setIsUnKnow(rs.getInt("is_unknow"));
					model.setTroneName(StringUtil.getString(rs.getString("trone_name"), ""));
					model.setHoldPercent(rs.getInt("hold_percent"));
					model.setHoldAmount(rs.getFloat("hold_amount"));
					model.setIsHoldCustom(rs.getInt("hold_is_Custom"));
					model.setSpTroneId(rs.getInt("sp_trone_id"));
					model.setPrice(rs.getFloat("price"));
					model.setHoldAcount(rs.getInt("hold_start"));
					list.add(model);
				}
				return list;
			}
		}));
		
		return map;
	} 
	
	public TroneOrderModel getTroneOrderById(int id)
	{
		String sql = "select  b.sp_trone_id,c.`name` sp_trone_name,a.*, d.id sp_id, b.`trone_name`,d.`short_name` sp_name,e.`short_name` cp_name  ";
		sql += " FROM daily_config.`tbl_trone_order` a";
		sql += " LEFT JOIN daily_config.`tbl_trone` b ON a.`trone_id` = b.`id`";
		sql += " LEFT JOIN daily_config.`tbl_sp_trone` c ON b.`sp_trone_id` = c.`id`";
		sql += " LEFT JOIN daily_config.`tbl_sp` d ON c.`sp_id` = d.`id`";
		sql += " LEFT JOIN daily_config.`tbl_cp` e ON a.`cp_id` = e.`id` WHERE  a.id = " + id;
		
		return (TroneOrderModel)new JdbcControl().query(sql, new QueryCallBack()
		{
			
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if(rs.next())
				{
					TroneOrderModel model = new TroneOrderModel();
					
					model.setId(rs.getInt("id"));
					model.setSpId(rs.getInt("sp_id"));
					model.setTroneId(rs.getInt("trone_id"));
					model.setOrderNum(StringUtil.getString(rs.getString("order_num"), ""));
					model.setCpId(rs.getInt("cp_id"));
					model.setCpShortName(StringUtil.getString(rs.getString("cp_name"), ""));
					model.setOrderTroneName(StringUtil.getString(rs.getString("order_trone_name"), ""));
					model.setSpTroneName(StringUtil.getString(rs.getString("sp_trone_name"), ""));
					model.setSpShortName(StringUtil.getString(rs.getString("sp_name"), ""));
					model.setDynamic(rs.getInt("is_dynamic"));
					model.setPushUrlId(rs.getInt("push_url_id"));
					model.setDisable(rs.getInt("disable"));
					model.setIsUnKnow(rs.getInt("is_unknow"));
					model.setTroneName(StringUtil.getString(rs.getString("trone_name"), ""));
					model.setHoldPercent(rs.getInt("hold_percent"));
					model.setHoldAmount(rs.getFloat("hold_amount"));
					model.setIsHoldCustom(rs.getInt("hold_is_Custom"));
					model.setSpTroneId(rs.getInt("sp_trone_id"));
					model.setHoldAcount(rs.getInt("hold_start"));
					
					return model;
				}
				return null;
			}
		});
	}
	
	public boolean addTroneOrder(TroneOrderModel model)
	{
		
		String sql = "insert into daily_config.tbl_trone_order(trone_id,order_num,cp_id,order_trone_name,is_dynamic,push_url_id,disable,is_unknow,hold_percent,hold_amount,hold_is_custom,hold_start) values(" + model.getTroneId() + ",'"
				+ model.getOrderNum() + "'," + model.getCpId() + ",'"
				+ model.getOrderTroneName() + "'," + model.getDynamic() + ","
				+ model.getPushUrlId() + "," + model.getDisable() + ","
				+ model.getIsUnKnow() + "," + model.getHoldPercent() + ","
				+ model.getHoldAmount() + "," + model.getIsHoldCustom() + ","
				+ model.getHoldAcount() + ")";

		return new JdbcControl().execute(sql);
		
	}
	
	public boolean updateTroneOrder(TroneOrderModel model)
	{
		String sql = "update daily_config.tbl_trone_order set trone_id = " + model.getTroneId() + ", order_num = '"
				+ model.getOrderNum() + "',cp_id=" + model.getCpId()
				+ ",order_trone_name='" + model.getOrderTroneName()
				+ "',is_dynamic=" + model.getDynamic() + ",push_url_id="
				+ model.getPushUrlId() + ",disable=" + model.getDisable()
				+ ",is_unknow=" + model.getIsUnKnow() + ",hold_percent="
				+ model.getHoldPercent() + ",hold_amount=" + model.getHoldAmount() + ",hold_is_custom="
				+ model.getIsHoldCustom() + ",hold_start = " + model.getHoldAcount() + " where id = " + model.getId();
		
		return new JdbcControl().execute(sql);
	}
	
	//不要问我为什么这样处理这个状态，得问邓先生！
	@SuppressWarnings("unchecked")
	public List<TroneOrderModel> loadTroneOrderListByCpSpTroneId(int cpId,int spTroneId,int status)
	{
		String sql = "select a.*,b.short_name,c.sp_id,c.trone_name,c.price,d.status,d.name sp_trone_name,d.provinces  from daily_config.tbl_trone_order a "
				+ " left join daily_config.tbl_cp b on a.cp_id = b.id "
				+ " left join daily_config.tbl_trone c on a.trone_id = c.id "
				+ " LEFT JOIN daily_config.`tbl_sp_trone` d ON c.`sp_trone_id` = d.id "
				+ " WHERE b.id = "+ cpId + " and d.trone_api_id > 0 ";
		
				if(status>-1)
				{
					sql += " and c.status = " + status + " and  a.disable = " +  (status==0 ? 1 : 0) ;
				}
		
				if(spTroneId>0)
				{
					sql += " and d.id = " + spTroneId;
				}
				
				sql += " order by b.short_name asc";
		
		return (List<TroneOrderModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<TroneOrderModel> list = new ArrayList<TroneOrderModel>();
				
				while(rs.next())
				{
					TroneOrderModel model = new TroneOrderModel();
					
					model.setId(rs.getInt("id"));
					model.setTroneId(rs.getInt("trone_id"));
					model.setOrderNum(StringUtil.getString(rs.getString("order_num"), ""));
					model.setCpId(rs.getInt("cp_id"));
					model.setCpShortName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setOrderTroneName(StringUtil.getString(rs.getString("order_trone_name"), ""));
					model.setDynamic(rs.getInt("is_dynamic"));
					model.setPushUrlId(rs.getInt("push_url_id"));
					model.setDisable(rs.getInt("disable"));
					model.setIsUnKnow(rs.getInt("is_unknow"));
					model.setSpId(rs.getInt("sp_id"));
					model.setHoldPercent(rs.getInt("hold_percent"));
					model.setTroneName(StringUtil.getString(rs.getString("trone_name"), ""));
					model.setSpTroneName(StringUtil.getString(rs.getString("sp_trone_name"), ""));
					model.setPrice(rs.getFloat("price"));
					model.setSpTroneStatus(rs.getInt("status"));
					model.setHoldAcount(rs.getInt("hold_start"));
					model.setProvince(StringUtil.getString(rs.getString("provinces"), ""));
					
					list.add(model);
				}
				
				return list;
			}
		});
	}
	
	public CpSpTroneSynModel loadCpSpTroneSynModelById(int id)
	{
		String sql = "SELECT a.`order_num`,b.`trone_num`,c.url,f.`short_name` cp_name,e.`short_name` sp_name,d.`name` sp_trone_name,b.`price`,d.`trone_api_id`";
		sql += " FROM daily_config.`tbl_trone_order` a";
		sql += " LEFT JOIN daily_config.`tbl_trone` b ON a.`trone_id` = b.`id`";
		sql += " LEFT JOIN daily_config.`tbl_cp_push_url` c ON a.`push_url_id` = c.`id`";
		sql += " LEFT JOIN daily_config.`tbl_sp_trone` d ON b.`sp_trone_id` = d.`id`";
		sql += " LEFT JOIN daily_config.`tbl_sp` e ON d.`sp_id` = e.`id`";
		sql += " LEFT JOIN daily_config.tbl_cp f ON a.`cp_id` = f.`id`";
		sql += " WHERE a.id = " + id;
		
		return (CpSpTroneSynModel)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if(rs.next())
				{
					CpSpTroneSynModel model = new CpSpTroneSynModel();
					model.setCpName(StringUtil.getString(rs.getString("cp_name"), ""));
					model.setSpName(StringUtil.getString(rs.getString("sp_name"), ""));
					model.setSpTroneName(StringUtil.getString(rs.getString("sp_trone_name"), ""));
					model.setOrder(StringUtil.getString(rs.getString("order_num"), ""));
					model.setTroneNum(StringUtil.getString(rs.getString("trone_num"), ""));
					model.setPrice(rs.getFloat("price"));
					model.setCpUrl(StringUtil.getString(rs.getString("url"),""));
					model.setTroneOrderId(rs.getInt("trone_api_id"));
					return model;
				}
				return null;
			}
		});
		
	}
	
}
