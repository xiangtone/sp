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
import com.system.model.CpBillingModel;
import com.system.model.CpBillingSpTroneModel;
import com.system.model.CpBillingSptroneDetailModel;
import com.system.model.CpBillingTroneOrderDetailModel;
import com.system.util.SqlUtil;
import com.system.util.StringUtil;

public class CpBillingDao
{
	/**
	 * 开始CP对帐
	 * 	 
	 * @param cpId
	 * @param jsType
	 * @param startDate
	 * @param endDate
	 */
	public int addCpBilling(int cpId,int jsType,String startDate,String endDate,float preBilling)
	{
		String sql = "INSERT INTO daily_config.`tbl_cp_billing`(cp_id,js_type,start_date,end_date,pre_billing) VALUES(?,?,?,?,?)";
		
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		
		map.put(1, cpId);
		map.put(2, jsType);
		map.put(3, startDate);
		map.put(4, endDate);
		map.put(5, preBilling);
		
		return new JdbcControl().insertWithGenKey(sql, map);
	}
	
	/**
	 * 是否重复出帐单了
	 * @param cpId
	 * @param jsType
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public boolean isCpBillingCross(int cpId,int jsType,String startDate,String endDate)
	{
		String sql = "SELECT COUNT(*) FROM daily_config.`tbl_cp_billing` WHERE cp_id = " + cpId + " AND js_type = " + jsType;
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
	
	/**
	 * 获取CP对应帐单的基础数据
	 */
	@SuppressWarnings("unchecked")
	public List<CpBillingSptroneDetailModel> loadCpBillingSpTroneDetailOri(int cpId,int jsType,String startDate,String endDate)
	{
		String sql = "SELECT b.cp_id,c.`sp_trone_id`,a.`mr_date`,SUM(a.amount) amount,d.`rate`";
		sql += " FROM  ";
		sql += " ( ";
		sql += " SELECT mr_date,trone_order_id,SUM(amount) amount ";
		sql += " FROM daily_log.`tbl_cp_mr_summer` a WHERE a.`mr_date` >= '" + startDate + "' AND a.`mr_date` <= '" + endDate + "' ";
		sql += " GROUP BY mr_date,trone_order_id ";
		sql += " )a ";
		sql += " LEFT JOIN daily_config.`tbl_trone_order` b ON a.`trone_order_id` = b.`id` ";
		sql += " LEFT JOIN daily_config.`tbl_trone` c ON b.`trone_id` = c.`id` ";
		sql += " LEFT JOIN daily_config.`tbl_cp_trone_rate` d ON b.`cp_id` = d.`cp_id` AND c.`sp_trone_id` = d.`sp_trone_id` ";
		sql += " WHERE 1=1 AND b.`cp_id` = " + cpId + "  AND d.`js_type` = " + jsType;
		sql += " GROUP BY a.`mr_date`,c.`sp_trone_id`";

		return (List<CpBillingSptroneDetailModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<CpBillingSptroneDetailModel> list = new ArrayList<CpBillingSptroneDetailModel>();
				
				while(rs.next())
				{
					CpBillingSptroneDetailModel model = new CpBillingSptroneDetailModel();
					
					model.setAmount(rs.getFloat("amount"));
					model.setMrDate(rs.getString("mr_date"));
					model.setSpTroneId(rs.getInt("sp_trone_id"));
					model.setRate(rs.getFloat("rate"));
					
					list.add(model);
				}
				return list;
			}
		});
	}
	
	/**
	 * 获取CP对应帐单的基础数据
	 */
	@SuppressWarnings("unchecked")
	public List<CpBillingTroneOrderDetailModel> loadCpBillingTroneOrderDetailOri(int cpId,int jsType,String startDate,String endDate)
	{
		String sql = " SELECT a.`mr_date`,a.trone_order_id,a.`province_id`,c.`sp_trone_id`,SUM(a.amount) amount,d.`rate` ";
		sql += " FROM  ";
		sql += " ( ";
		sql += " SELECT mr_date,trone_order_id,province_id,SUM(amount) amount ";
		sql += " FROM daily_log.`tbl_cp_mr_summer` a WHERE a.`mr_date` >= '" + startDate + "' AND a.`mr_date` <= '" + endDate + "' ";
		sql += " GROUP BY mr_date,trone_order_id,province_id ";
		sql += " )a ";
		sql += " LEFT JOIN daily_config.`tbl_trone_order` b ON a.`trone_order_id` = b.`id` ";
		sql += " LEFT JOIN daily_config.`tbl_trone` c ON b.`trone_id` = c.`id` ";
		sql += " LEFT JOIN daily_config.`tbl_cp_trone_rate` d ON b.`cp_id` = d.`cp_id` AND c.`sp_trone_id` = d.`sp_trone_id` ";
		sql += " WHERE 1=1 AND b.`cp_id` = " + cpId + "  AND d.`js_type` = " + jsType;
		sql += " GROUP BY a.`mr_date`,a.`trone_order_id`,a.`province_id` ";


		return (List<CpBillingTroneOrderDetailModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<CpBillingTroneOrderDetailModel> list = new ArrayList<CpBillingTroneOrderDetailModel>();
				
				while(rs.next())
				{
					CpBillingTroneOrderDetailModel model = new CpBillingTroneOrderDetailModel();
					
					model.setAmount(rs.getFloat("amount"));
					model.setMrDate(rs.getString("mr_date"));
					model.setProvinceId(rs.getInt("province_id"));
					model.setRate(rs.getFloat("rate"));
					model.setTroneOrderId(rs.getInt("trone_order_id"));
					model.setSpTroneId(rs.getInt("sp_trone_id"));
					
					list.add(model);
				}
				return list;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<CpBillingSpTroneModel> loadCpBillingSpTroneOri(int cpId,int jsType,final String startDate,final String endDate)
	{
		String sql = " SELECT c.`sp_trone_id`,SUM(a.amount) amount,d.`rate` ";
		sql += " FROM  ";
		sql += " ( ";
		sql += " SELECT mr_date,trone_order_id,SUM(amount) amount ";
		sql += " FROM daily_log.`tbl_cp_mr_summer` a WHERE a.`mr_date` >= '" + startDate + "' AND a.`mr_date` <= '" + endDate + "' ";
		sql += " GROUP BY mr_date,trone_order_id ";
		sql += " )a ";
		sql += " LEFT JOIN daily_config.`tbl_trone_order` b ON a.`trone_order_id` = b.`id` ";
		sql += " LEFT JOIN daily_config.`tbl_trone` c ON b.`trone_id` = c.`id` ";
		sql += " LEFT JOIN daily_config.`tbl_cp_trone_rate` d ON b.`cp_id` = d.`cp_id` AND c.`sp_trone_id` = d.`sp_trone_id` ";
		sql += " WHERE 1=1 AND b.`cp_id` = " + cpId + "  AND d.`js_type` = " + jsType;
		sql += " GROUP BY c.`sp_trone_id` ";

		return (List<CpBillingSpTroneModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<CpBillingSpTroneModel> list = new ArrayList<CpBillingSpTroneModel>();
				
				while(rs.next())
				{
					CpBillingSpTroneModel model = new CpBillingSpTroneModel();
					
					model.setAmount(rs.getFloat("amount"));
					model.setStartDate(startDate);
					model.setEndDate(endDate);
					model.setRate(rs.getFloat("rate"));
					model.setSpTroneId(rs.getInt("sp_trone_id"));
					
					list.add(model);
				}
				return list;
			}
		});
		
	}
	
	public Map<String, Object> loadCpBilling(String startDate, String endDate,
			int cpId, int pageIndex)
	{
		startDate = SqlUtil.sqlEncode(startDate);
		endDate = SqlUtil.sqlEncode(endDate);		
		
		String sql = "SELECT a.`cp_id`,b.`short_name`,a.`js_type`,c.`name` js_name,a.`pre_billing`,a.`remark`";
		sql += " FROM daily_config.`tbl_cp_billing` a";
		sql += " LEFT JOIN daily_config.`tbl_cp` b ON a.`cp_id` = b.`id`";
		sql += " LEFT JOIN daily_config.`tbl_js_type` c ON a.`js_type` = c.`id`";
		sql += " WHERE 1=1";
		
		if(cpId>0)
		{
			sql += " and b.id = " + cpId;
		}
		
		if(!StringUtil.isNullOrEmpty(startDate))
		{
			sql += " and a.start_date >= '" + startDate + "'";
		}
		
		if(!StringUtil.isNullOrEmpty(endDate))
		{
			sql += " and a.end_date >= '" + endDate + "'";
		}
		
		sql += "ORDER BY id DESC;";
		

		String limit = " limit " + Constant.PAGE_SIZE * (pageIndex - 1) + ","
				+ Constant.PAGE_SIZE;

		Map<String, Object> map = new HashMap<String, Object>();

		JdbcControl control = new JdbcControl();
		map.put("rows", control.query(
				sql.replace(Constant.CONSTANT_REPLACE_STRING, "count(*)"),
				new QueryCallBack()
				{
					@Override
					public Object onCallBack(ResultSet rs) throws SQLException
					{
						if (rs.next())
							return rs.getInt(1);

						return 0;
					}
				}));

		map.put("list", control.query(
				sql.replace(Constant.CONSTANT_REPLACE_STRING, " * ") + limit,
				new QueryCallBack()
				{
					@Override
					public Object onCallBack(ResultSet rs) throws SQLException
					{
						List<CpBillingModel> list = new ArrayList<CpBillingModel>();
						while (rs.next())
						{
							CpBillingModel model = new CpBillingModel();

							model.setId(rs.getInt("id"));
							
							
							list.add(model);
						}
						return list;
					}
				}));

		return map;
	}
	
	/**
	 * 增加CP帐单下面业务每天流水数据
	 * @param list
	 * @param cpBillingId
	 */
	public void addCpBillingSpTroneDetailData(List<CpBillingSptroneDetailModel> list,int cpBillingId)
	{
		
	}
	
	/**
	 * 增加CP帐单下面业务汇总数据
	 * @param list
	 * @param cpBillingId
	 */
	public void addCpBillingSpTroneData(List<CpBillingSpTroneModel> list,int cpBillingId)
	{
		String sql = "INSERT INTO daily_log.`tbl_cp_billing_sp_trone` (cp_billing_id,start_date,end_date,sp_trone_id,amount,rate) values ";
		
		for(CpBillingSpTroneModel model : list)
		{
			sql += "(" + cpBillingId + ",'" + model.getStartDate() + "','" + model.getEndDate() + "'," + model.getSpTroneId() + "," + model.getAmount() + "," + model.getRate() + "),";
		}
		
		sql = sql.substring(0,sql.length()-1);
		
		new JdbcControl().execute(sql);
		
	}
	
	/**
	 * 增加CP帐单下面业务的每个通道详细数据
	 * @param list
	 * @param cpBillingId
	 */
	public void addCpBillingTroneOrderDetailData(List<CpBillingTroneOrderDetailModel> list,int cpBillingId)
	{
		String sql = "INSERT INTO daily_log.`tbl_cp_billing_trone_order_detail`(cp_billing_id,province_id,trone_order_id,mr_date,amount) values ";
		
		for(CpBillingTroneOrderDetailModel model : list)
		{
			sql += "(" + cpBillingId + "," + model.getProvinceId() +  "," + model.getTroneOrderId() + ",'" + model.getMrDate() + "'," + model.getAmount() + "),";
		}
		
		sql = sql.substring(0,sql.length()-1);
		
		new JdbcControl().execute(sql);
	}
	
	
	
}
