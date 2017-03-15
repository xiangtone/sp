
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
import com.system.model.MrReportModel;

public class MrDaoSj
{
	public Map<String, Object> getMrAnalyData(String startDate, String endDate,
			int spId,  int spTroneId,int troneId, int cpId, int troneOrderId, int provinceId,
			int cityId,int operatorId,int dataType, int spCommerceUserId,int cpCommerceUserId,int sortType)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		String query = "";
		
		if(spId>0)
			query += " and d.id = " + spId;
		
		if(troneId>0)
			query += " and c.id = " + troneId;
		
		if(cpId>0)
			query += " and e.id = " + cpId;
		
		if(troneOrderId>0)
			query += " and b.id = " + troneOrderId;
		
		if(provinceId>0)
			query += " and f.id = " + provinceId;
		
		if(cityId>0)
			query += " and g.id =" + cityId;
		
		if(spTroneId>0)
			query += " and h.id = " + spTroneId;
		
		if(operatorId>0)
			query += " and n.flag = " + operatorId;
		
		if(dataType>-1)
			query+= " and a.record_type = " + dataType;
		
		if(spCommerceUserId>0)
			query += " and d.commerce_user_id = " + spCommerceUserId;
		
		if(cpCommerceUserId>0)
			query += " and e.commerce_user_id = " + cpCommerceUserId;
		
		String[] result = getSortType(sortType);
		String queryParams = result[0];
		String joinId = result[1];
		
		String sql = "select a.show_title,aa,bb,cc,dd from (";
		sql += " select  " + joinId + " join_id," + queryParams + " show_title,sum(a.data_rows) aa,sum(a.amount) bb ";
		sql += " from daily_log.tbl_mr_summer_2 a";
		sql += " left join daily_config.tbl_trone_order b on a.trone_order_id = b.id ";
		sql += " left join daily_config.tbl_trone c on b.trone_id = c.id";
		sql += " left join daily_config.tbl_sp d on c.sp_id = d.id";
		sql += " left join daily_config.tbl_cp e on b.cp_id = e.id ";
		sql += " left join daily_config.tbl_province f on a.province_id = f.id";
		sql += " left join daily_config.tbl_city g on a.city_id = g.id";
		sql += " left join daily_config.tbl_sp_trone h on c.sp_trone_id = h.id";
		sql += " LEFT JOIN daily_config.tbl_user j ON d.commerce_user_id = j.id";
		sql += " LEFT JOIN daily_config.tbl_user k ON e.commerce_user_id = k.id";
		sql += " LEFT JOIN daily_config.tbl_product_2 l on h.product_id = l.id";
		sql += " LEFT JOIN daily_config.tbl_product_1 m on l.product_1_id = m.id";
		sql += " LEFT JOIN daily_config.tbl_operator n on m.operator_id = n.id";		
		sql += " where a.mr_date >= '" + startDate + "' and a.mr_date <= '" + endDate + "' " + query;
		sql += " group by join_id order by show_title asc )a";
		sql += " left join(";
		sql += " select  " + joinId + " join_id," + queryParams + " show_title,sum(a.data_rows) cc,sum(a.amount) dd ";
		sql += " from daily_log.tbl_cp_mr_summer_2 a ";
		sql += " left join daily_config.tbl_trone_order b on a.trone_order_id = b.id";
		sql += " left join daily_config.tbl_trone c on b.trone_id = c.id";
		sql += " left join daily_config.tbl_sp d on c.sp_id = d.id";
		sql += " left join daily_config.tbl_cp e on b.cp_id = e.id";
		sql += " left join daily_config.tbl_province f on a.province_id = f.id";
		sql += " left join daily_config.tbl_city g on a.city_id = g.id";
		sql += " left join daily_config.tbl_sp_trone h on c.sp_trone_id = h.id ";
		sql += " LEFT JOIN daily_config.tbl_user j ON d.commerce_user_id = j.id";
		sql += " LEFT JOIN daily_config.tbl_user k ON e.commerce_user_id = k.id";
		sql += " LEFT JOIN daily_config.tbl_product_2 l on h.product_id = l.id";
		sql += " LEFT JOIN daily_config.tbl_product_1 m on l.product_1_id = m.id";
		sql += " LEFT JOIN daily_config.tbl_operator n on m.operator_id = n.id";
		sql += " where a.mr_date >= '" + startDate + "' and a.mr_date <= '" + endDate + "' " + query;
		sql += " group by join_id order by show_title asc";
		sql += " )b on a.join_id = b.join_id;";
		
		
		JdbcControl control = new JdbcControl();
		
		final List<Object> datalist = new ArrayList<Object>();
		
		map.put("list", control.query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<MrReportModel> list = new ArrayList<MrReportModel>();
				int dataRows=0,showDataRows = 0;
				double amount=0,showAmount = 0;
				while(rs.next())
				{
					MrReportModel model = new MrReportModel();
					
					model.setTitle1(rs.getString("show_title"));
					model.setDataRows(rs.getInt(2));
					model.setAmount(rs.getFloat(3));
					model.setShowDataRows(rs.getInt(4));
					model.setShowAmount(rs.getFloat(5));
					
					dataRows += model.getDataRows();
					showDataRows += model.getShowDataRows();
					amount += model.getAmount();
					showAmount += model.getShowAmount();
					
					list.add(model);
				}
				
				datalist.add(dataRows);
				datalist.add(showDataRows);
				datalist.add(amount);
				datalist.add(showAmount);
				
				return list;
			}
		}));
		
		map.put("datarows", datalist.get(0));
		map.put("showdatarows", datalist.get(1));
		map.put("amount", datalist.get(2));
		map.put("showamount", datalist.get(3));
		
		return map;
	}
	
	public Map<String, Object> getMrAnalyLrData(String startDate, String endDate,
			int spId,  int spTroneId,int troneId, int cpId, int troneOrderId, int provinceId,
			int cityId,int operatorId,int dataType, int spCommerceUserId,int cpCommerceUserId,int sortType)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		String query = "";
		
		if(spId>0)
			query += " and d.id = " + spId;
		
		if(troneId>0)
			query += " and c.id = " + troneId;
		
		if(cpId>0)
			query += " and e.id = " + cpId;
		
		if(troneOrderId>0)
			query += " and b.id = " + troneOrderId;
		
		if(provinceId>0)
			query += " and f.id = " + provinceId;
		
		if(cityId>0)
			query += " and g.id =" + cityId;
		
		if(spTroneId>0)
			query += " and h.id = " + spTroneId;
		
		if(operatorId>0)
			query += " and h.operator = " + operatorId;
		
		if(dataType>-1)
			query+= " and a.record_type = " + dataType;
		
		if(spCommerceUserId>0)
			query += " and d.commerce_user_id = " + spCommerceUserId;
		
		if(cpCommerceUserId>0)
			query += " and e.commerce_user_id = " + cpCommerceUserId;
		
		String[] result = getSortType(sortType);
		String queryParams = result[0];
		String joinId = result[1];
		
		String sql = "select a.show_title,aa,bb,cc,dd,sp_money,cp_money from (";
		sql += " select  " + joinId + " join_id," + queryParams + " show_title,sum(a.data_rows) aa,sum(a.amount) bb,sum(a.amount*h.jiesuanlv) sp_money";
		sql += " from daily_log.tbl_mr_summer_2 a";
		sql += " left join daily_config.tbl_trone_order b on a.trone_order_id = b.id ";
		sql += " left join daily_config.tbl_trone c on b.trone_id = c.id";
		sql += " left join daily_config.tbl_sp d on c.sp_id = d.id";
		sql += " left join daily_config.tbl_cp e on b.cp_id = e.id ";
		sql += " left join daily_config.tbl_province f on a.province_id = f.id";
		sql += " left join daily_config.tbl_city g on a.city_id = g.id";
		sql += " left join daily_config.tbl_sp_trone h on c.sp_trone_id = h.id";
		sql += " LEFT JOIN daily_config.tbl_user j ON d.commerce_user_id = j.id";
		sql += " LEFT JOIN daily_config.tbl_user k ON e.commerce_user_id = k.id";
		sql += " where a.mr_date >= '" + startDate + "' and a.mr_date <= '" + endDate + "' " + query;
		sql += " group by join_id order by show_title asc )a";
		sql += " left join(";
		sql += " select  " + joinId + " join_id," + queryParams + " show_title,sum(a.data_rows) cc,sum(a.amount) dd,sum(a.amount*i.rate) cp_money";
		sql += " from daily_log.tbl_cp_mr_summer_2 a ";
		sql += " left join daily_config.tbl_trone_order b on a.trone_order_id = b.id";
		sql += " left join daily_config.tbl_trone c on b.trone_id = c.id";
		sql += " left join daily_config.tbl_sp d on c.sp_id = d.id";
		sql += " left join daily_config.tbl_cp e on a.cp_id = e.id";
		sql += " left join daily_config.tbl_province f on a.province_id = f.id";
		sql += " left join daily_config.tbl_city g on a.city_id = g.id";
		sql += " left join daily_config.tbl_sp_trone h on c.sp_trone_id = h.id ";
		sql	+= " LEFT JOIN daily_config.tbl_cp_trone_rate i ON e.id = i.cp_id AND h.id = i.sp_trone_id";
		sql += " LEFT JOIN daily_config.tbl_user j ON d.commerce_user_id = j.id";
		sql += " LEFT JOIN daily_config.tbl_user k ON e.commerce_user_id = k.id";
		sql += " where a.mr_date >= '" + startDate + "' and a.mr_date <= '" + endDate + "' " + query;
		sql += " group by join_id order by show_title asc";
		sql += " )b on a.join_id = b.join_id;";
		
		
		JdbcControl control = new JdbcControl();
		
		final List<Object> datalist = new ArrayList<Object>();
		
		map.put("list", control.query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<MrReportModel> list = new ArrayList<MrReportModel>();
				int dataRows=0,showDataRows = 0;
				double amount=0,showAmount = 0,spAmount=0,cpAmount=0;
				while(rs.next())
				{
					MrReportModel model = new MrReportModel();
					
					model.setTitle1(rs.getString("show_title"));
					model.setDataRows(rs.getInt(2));
					model.setAmount(rs.getFloat(3));
					model.setShowDataRows(rs.getInt(4));
					model.setShowAmount(rs.getFloat(5));
					model.setSpMoney(rs.getFloat("sp_money"));
					model.setCpMoney(rs.getFloat("cp_money"));
					
					dataRows += model.getDataRows();
					showDataRows += model.getShowDataRows();
					amount += model.getAmount();
					showAmount += model.getShowAmount();
					spAmount += model.getSpMoney();
					cpAmount += model.getCpMoney();
					
					list.add(model);
				}
				
				datalist.add(dataRows);
				datalist.add(showDataRows);
				datalist.add(amount);
				datalist.add(showAmount);
				datalist.add(spAmount);
				datalist.add(cpAmount);
				
				return list;
			}
		}));
		
		map.put("datarows", datalist.get(0));
		map.put("showdatarows", datalist.get(1));
		map.put("amount", datalist.get(2));
		map.put("showamount", datalist.get(3));
		map.put("spamount", datalist.get(4));
		map.put("cpamount", datalist.get(5));
		
		return map;
	}
	
	public Map<String, Object> getMrAnalyDataQiBa(String startDate, String endDate,
			int spId,  int spTroneId,int troneId, int cpId, int troneOrderId, int provinceId,
			int cityId, int sortType)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		String query = "";
		
		if(spId>0)
			query += " and d.id = " + spId;
		
		if(troneId>0)
			query += " and c.id = " + troneId;
		
		if(cpId>0)
			query += " and e.id = " + cpId;
		
		if(troneOrderId>0)
			query += " and b.id = " + troneOrderId;
		
		if(provinceId>0)
			query += " and f.id = " + provinceId;
		
		if(cityId>0)
			query += " and g.id =" + cityId;
		
		if(spTroneId>0)
			query += " and h.id = " + spTroneId;
		
		String[] result = getSortTypeQiBa(sortType);
		String queryParams = result[0];
		String joinId = result[1];
		
		String sql = "select a.show_title,aa,bb,cc,dd from (";
		sql += " select  " + joinId + " join_id," + queryParams + " show_title,sum(a.data_rows) aa,sum(a.amount) bb";
		sql += " from daily_log.tbl_mr_summer_2 a";
		sql += " left join daily_config.tbl_trone_order b on a.trone_order_id = b.id ";
		sql += " left join daily_config.tbl_trone c on b.trone_id = c.id";
		sql += " left join daily_config.tbl_sp d on c.sp_id = d.id";
		sql += " left join daily_config.tbl_cp e on b.cp_id = e.id ";
		sql += " left join daily_config.tbl_province f on a.province_id = f.id";
		sql += " left join daily_config.tbl_city g on a.city_id = g.id";
		sql += " left join daily_config.tbl_sp_trone h on c.sp_trone_id = h.id";
		sql += " where a.mr_date >= '" + startDate + "' and a.mr_date <= '" + endDate + "' " + query;
		sql += " group by join_id order by show_title asc )a";
		sql += " left join(";
		sql += " select  " + joinId + " join_id," + queryParams + " show_title,sum(a.data_rows) cc,sum(a.amount) dd";
		sql += " from daily_log.tbl_cp_mr_summer_2 a ";
		sql += " left join daily_config.tbl_trone_order b on a.trone_order_id = b.id";
		sql += " left join daily_config.tbl_trone c on b.trone_id = c.id";
		sql += " left join daily_config.tbl_sp d on c.sp_id = d.id";
		sql += " left join daily_config.tbl_cp e on a.cp_id = e.id";
		sql += " left join daily_config.tbl_province f on a.province_id = f.id";
		sql += " left join daily_config.tbl_city g on a.city_id = g.id";
		sql += " left join daily_config.tbl_sp_trone h on c.sp_trone_id = h.id";
		sql += " where a.mr_date >= '" + startDate + "' and a.mr_date <= '" + endDate + "' " + query;
		sql += " group by join_id order by show_title asc";
		sql += " )b on a.join_id = b.join_id;";
		
		
		
		JdbcControl control = new JdbcControl();
		
		final List<Object> datalist = new ArrayList<Object>();
		
		map.put("list", control.query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<MrReportModel> list = new ArrayList<MrReportModel>();
				int dataRows=0,showDataRows = 0;
				double amount=0,showAmount = 0;
				while(rs.next())
				{
					MrReportModel model = new MrReportModel();
					
					model.setTitle1(rs.getString("show_title"));
					model.setDataRows(rs.getInt(2));
					model.setAmount(rs.getFloat(3));
					model.setShowDataRows(rs.getInt(4));
					model.setShowAmount(rs.getFloat(5));
					
					dataRows += model.getDataRows();
					showDataRows += model.getShowDataRows();
					amount += model.getAmount();
					showAmount += model.getShowAmount();
					
					list.add(model);
				}
				
				datalist.add(dataRows);
				datalist.add(showDataRows);
				datalist.add(amount);
				datalist.add(showAmount);
				
				return list;
			}
		}));
		
		map.put("datarows", datalist.get(0));
		map.put("showdatarows", datalist.get(1));
		map.put("amount", datalist.get(2));
		map.put("showamount", datalist.get(3));
		
		return map;
	}
	
	public Map<String,Object> getCpMrTodayShowData(String tableName,String startDate,int userId,int spTroneId)
	{
		final Map<String, Object> map = new HashMap<String, Object>();
		
		String sql = "SELECT COUNT(*) show_data_rows,SUM(d.price) show_amount";
		sql += " FROM daily_log.`tbl_cp_mr_" + tableName + "` a";
		sql += " LEFT JOIN daily_config.`tbl_trone_order` b ON a.`trone_order_id` = b.`id`";
		sql += " LEFT JOIN daily_config.`tbl_trone` d ON b.trone_id = d.id";
		sql += " LEFT JOIN daily_config.`tbl_cp` c ON b.`cp_id` = c.`id`";
		sql += " LEFT JOIN daily_config.`tbl_sp_trone` e ON d.sp_trone_id = e.id ";
		sql += " WHERE a.`create_date` >= '" + startDate + " 00:00:00' AND a.`create_date` <= '" + startDate + " 23:59:59'";
		sql += " AND c.user_id = " + userId;
		
		if(spTroneId>0)
		{
			sql += " AND e.id = " + spTroneId;
		}
		
		new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if(rs.next())
				{
					map.put("show_data_rows", rs.getInt("show_data_rows"));
					map.put("show_amount", rs.getFloat("show_amount"));
				}
				return null;
			}
		});
		
		return map;
	}
	
	public Map<String,Object> getCpMrShowData(String startDate,String endDate,int userId,int spTroneId,int showType)
	{
		final Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("show_data_rows", 0);
		map.put("show_amount", 0F);
		
		String query = " title,SUM(a.`data_rows`) show_data_rows,SUM(a.`amount`) show_amount ";
		
		String sql = " select " + Constant.CONSTANT_REPLACE_STRING + "  from daily_log.tbl_cp_mr_summer_2 a ";
		sql += " LEFT JOIN daily_config.`tbl_trone_order` b ON a.`trone_order_id` = b.`id`";
		sql += " LEFT JOIN daily_config.`tbl_trone` d ON b.trone_id = d.id";
		sql += " LEFT JOIN daily_config.`tbl_cp` c ON b.`cp_id` = c.`id`";
		sql += " LEFT JOIN daily_config.`tbl_sp_trone` e ON d.sp_trone_id = e.id ";		
		
		sql += " WHERE a.`mr_date` >= '" + startDate + "' AND a.`mr_date` <= '" + endDate + "'";
		
		if(spTroneId>0)
			sql += " AND e.id =" + spTroneId;
		
		sql += " AND c.user_id = " + userId ;
		
		String group = " GROUP BY a.`mr_date` ";
		String queryName = " a.`mr_date`";
		
//		<option value="1">日期</option>
//		<option value="2">周数</option>
//		<option value="3">月份</option>
//		<option value="4">业务</option>
//		<option value="5">指令</option>
		
		switch(showType)
		{
			case 0:
				break;
				
			case 1:
				queryName = " DATE_FORMAT(a.mr_date,'%Y-%u')";
				group = " GROUP BY  DATE_FORMAT(a.mr_date,'%Y-%u')";
				break;
				
			case 2:
				queryName = " DATE_FORMAT(a.mr_date,'%Y-%m')";
				group = " GROUP BY  DATE_FORMAT(a.mr_date,'%Y-%m')";
				break;
			
			case 3:
				queryName = " e.name";
				group = " GROUP BY e.id ";
				break;
				
			case 4:
				queryName = " concat(e.name,'-',d.orders,'-',d.price)";
				group = " GROUP BY d.id ";
				break;
		}
		
		query = queryName + query;
		
		JdbcControl control = new JdbcControl();
		
		control.query(sql.replace(Constant.CONSTANT_REPLACE_STRING, "SUM(a.`data_rows`) show_data_rows,SUM(a.`amount`) show_amount"), new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if(rs.next())
				{
					map.put("show_data_rows", rs.getInt("show_data_rows"));
					map.put("show_amount", rs.getFloat("show_amount"));
				}
				return null;
			}
		});
		
		map.put("list", control.query(sql.replace(Constant.CONSTANT_REPLACE_STRING, query) + group + " order by title asc", new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<MrReportModel> list = new ArrayList<MrReportModel>();
				while(rs.next())
				{
					MrReportModel model = new MrReportModel();
					model.setTitle1(rs.getString("title"));
					model.setShowDataRows(rs.getInt("show_data_rows"));
					model.setShowAmount(rs.getFloat("show_amount"));
					list.add(model);
				}
				return list;
			}
		}));
		
		return map;
	}
	
	public Map<String, Object> getMrTodayLrData(String tableName,String startDate,
			int spId, int spTroneId,int troneId, int cpId, int troneOrderId, int provinceId,
			int cityId,int spCommerceUserId,int cpCommerceUserId,int sortType)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		String query = "";
		
		if(spId>0)
			query += " and d.id = " + spId;
		
		if(troneId>0)
			query += " and c.id = " + troneId;
		
		if(cpId>0)
			query += " and e.id = " + cpId;
		
		if(troneOrderId>0)
			query += " and b.id = " + troneOrderId;
		
		if(provinceId>0)
			query += " and f.id = " + provinceId;
		
		if(cityId>0)
			query += " and g.id = " + cityId;
		
		if(spTroneId>0)
			query += " and h.id = " + spTroneId;
		
		if(spCommerceUserId>0)
			query += " and j.id = " + spCommerceUserId;
		
		if(cpCommerceUserId>0)
			query += " and k.id = " + cpCommerceUserId;
		
		String[] result = getSortType(sortType);
		String queryParams = result[0];
		String joinId = result[1];
		
		String sql = "select a.show_title,aa,bb,cc,dd,sp_money,cp_money,a.join_id from (";
		sql += " select  " + joinId + " join_id," + queryParams + " show_title,count(*) aa,sum(c.price) bb,sum(c.price*h.jiesuanlv) sp_money";
		sql += " from daily_log.tbl_mr_" + tableName + " a";
		sql += " left join daily_config.tbl_trone_order b on a.trone_order_id = b.id ";
		sql += " left join daily_config.tbl_trone c on b.trone_id = c.id";
		sql += " left join daily_config.tbl_sp d on c.sp_id = d.id";
		sql += " left join daily_config.tbl_cp e on b.cp_id = e.id ";
		sql += " left join daily_config.tbl_province f on a.province_id = f.id";
		sql += " left join daily_config.tbl_city g on a.city_id = g.id";
		sql += " left join daily_config.tbl_sp_trone h on c.sp_trone_id = h.id";
		sql += " LEFT JOIN daily_config.tbl_user j ON d.commerce_user_id = j.id";
		sql += " LEFT JOIN daily_config.tbl_user k ON e.commerce_user_id = k.id";
		sql += " where a.mr_date >= '" + startDate + "' and a.mr_date <= '" + startDate + "' " + query;
		sql += " group by join_id order by show_title asc )a";
		sql += " left join(";
		sql += " select  " + joinId + " join_id," + queryParams + " show_title,count(*) cc,sum(c.price) dd,sum(c.price*i.rate) cp_money";
		sql += " from daily_log.tbl_cp_mr_" + tableName + " a ";
		sql += " left join daily_config.tbl_trone_order b on a.trone_order_id = b.id";
		sql += " left join daily_config.tbl_trone c on b.trone_id = c.id";
		sql += " left join daily_config.tbl_sp d on c.sp_id = d.id";
		sql += " left join daily_config.tbl_cp e on b.cp_id = e.id";
		sql += " left join daily_config.tbl_province f on a.province_id = f.id";
		sql += " left join daily_config.tbl_city g on a.city_id = g.id";
		sql += " left join daily_config.tbl_sp_trone h on c.sp_trone_id = h.id";
		sql	+= " LEFT JOIN daily_config.tbl_cp_trone_rate i ON e.id = i.cp_id AND h.id = i.sp_trone_id";
		sql += " LEFT JOIN daily_config.tbl_user j ON d.commerce_user_id = j.id";
		sql += " LEFT JOIN daily_config.tbl_user k ON e.commerce_user_id = k.id";
		sql += " where a.mr_date >= '" + startDate + "' and a.mr_date <= '" + startDate + "' " + query;
		sql += " group by join_id order by show_title asc";
		sql += " )b on a.join_id = b.join_id;";		
		
		
		JdbcControl control = new JdbcControl();
		
		final List<Object> datalist = new ArrayList<Object>();
		
		map.put("list", control.query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<MrReportModel> list = new ArrayList<MrReportModel>();
				int dataRows=0,showDataRows = 0;
				double amount=0,showAmount = 0,spAmount=0,cpAmount=0;
				while(rs.next())
				{
					MrReportModel model = new MrReportModel();
					
					model.setTitle1(rs.getString("show_title"));
					model.setJoinId(rs.getString("join_id"));
					model.setDataRows(rs.getInt(2));
					model.setAmount(rs.getFloat(3));
					model.setShowDataRows(rs.getInt(4));
					model.setShowAmount(rs.getFloat(5));
					model.setSpMoney(rs.getFloat("sp_money"));
					model.setCpMoney(rs.getFloat("cp_money"));
					
					dataRows += model.getDataRows();
					showDataRows += model.getShowDataRows();
					amount += model.getAmount();
					showAmount += model.getShowAmount();
					spAmount += model.getSpMoney();
					cpAmount += model.getCpMoney();
					
					list.add(model);
				}
				
				datalist.add(dataRows);
				datalist.add(showDataRows);
				datalist.add(amount);
				datalist.add(showAmount);
				datalist.add(spAmount);
				datalist.add(cpAmount);
				
				return list;
			}
		}));
		
		map.put("datarows", datalist.get(0));
		map.put("showdatarows", datalist.get(1));
		map.put("amount", datalist.get(2));
		map.put("showamount", datalist.get(3));
		map.put("spamount", datalist.get(4));
		map.put("cpamount", datalist.get(5));
		
		return map;
	}
	
	public Map<String, Object> getMrTodayData(String tableName,String startDate,
			int spId, int spTroneId,int troneId, int cpId, int troneOrderId, int provinceId,
			int cityId,int spCommerceUserId,int cpCommerceUserId,int sortType)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		String query = "";
		
		if(spId>0)
			query += " and d.id = " + spId;
		
		if(troneId>0)
			query += " and c.id = " + troneId;
		
		if(cpId>0)
			query += " and e.id = " + cpId;
		
		if(troneOrderId>0)
			query += " and b.id = " + troneOrderId;
		
		if(provinceId>0)
			query += " and f.id = " + provinceId;
		
		if(cityId>0)
			query += " and g.id = " + cityId;
		
		if(spTroneId>0)
			query += " and h.id = " + spTroneId;
		
		if(spCommerceUserId>0)
			query += " and j.id = " + spCommerceUserId;
		
		if(cpCommerceUserId>0)
			query += " and k.id = " + cpCommerceUserId;
		
		String[] result = getSortType(sortType);
		String queryParams = result[0];
		String joinId = result[1];
		
		String sql = "select a.show_title,aa,bb,cc,dd,a.join_id from (";
		sql += " select  " + joinId + " join_id," + queryParams + " show_title,count(*) aa,sum(c.price) bb ";
		sql += " from daily_log.tbl_mr_" + tableName + " a";
		sql += " left join daily_config.tbl_trone_order b on a.trone_order_id = b.id ";
		sql += " left join daily_config.tbl_trone c on b.trone_id = c.id";
		sql += " left join daily_config.tbl_sp d on c.sp_id = d.id";
		sql += " left join daily_config.tbl_cp e on b.cp_id = e.id ";
		sql += " left join daily_config.tbl_province f on a.province_id = f.id";
		sql += " left join daily_config.tbl_city g on a.city_id = g.id";
		sql += " left join daily_config.tbl_sp_trone h on c.sp_trone_id = h.id";
		sql += " LEFT JOIN daily_config.tbl_user j ON d.commerce_user_id = j.id";
		sql += " LEFT JOIN daily_config.tbl_user k ON e.commerce_user_id = k.id";
		sql += " where a.mr_date >= '" + startDate + "' and a.mr_date <= '" + startDate + "' " + query;
		sql += " group by join_id order by show_title asc )a";
		sql += " left join(";
		sql += " select  " + joinId + " join_id," + queryParams + " show_title,count(*) cc,sum(c.price) dd ";
		sql += " from daily_log.tbl_cp_mr_" + tableName + " a ";
		sql += " left join daily_config.tbl_trone_order b on a.trone_order_id = b.id";
		sql += " left join daily_config.tbl_trone c on b.trone_id = c.id";
		sql += " left join daily_config.tbl_sp d on c.sp_id = d.id";
		sql += " left join daily_config.tbl_cp e on b.cp_id = e.id";
		sql += " left join daily_config.tbl_province f on a.province_id = f.id";
		sql += " left join daily_config.tbl_city g on a.city_id = g.id";
		sql += " left join daily_config.tbl_sp_trone h on c.sp_trone_id = h.id";
		sql += " LEFT JOIN daily_config.tbl_user j ON d.commerce_user_id = j.id";
		sql += " LEFT JOIN daily_config.tbl_user k ON e.commerce_user_id = k.id";
		sql += " where a.mr_date >= '" + startDate + "' and a.mr_date <= '" + startDate + "' " + query;
		sql += " group by join_id order by show_title asc";
		sql += " )b on a.join_id = b.join_id;";		
		
		
		JdbcControl control = new JdbcControl();
		
		final List<Object> datalist = new ArrayList<Object>();
		
		map.put("list", control.query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<MrReportModel> list = new ArrayList<MrReportModel>();
				int dataRows=0,showDataRows = 0;
				double amount=0,showAmount = 0;
				while(rs.next())
				{
					MrReportModel model = new MrReportModel();
					
					model.setTitle1(rs.getString("show_title"));
					model.setJoinId(rs.getString("join_id"));
					model.setDataRows(rs.getInt(2));
					model.setAmount(rs.getFloat(3));
					model.setShowDataRows(rs.getInt(4));
					model.setShowAmount(rs.getFloat(5));
					
					dataRows += model.getDataRows();
					showDataRows += model.getShowDataRows();
					amount += model.getAmount();
					showAmount += model.getShowAmount();
					
					list.add(model);
				}
				
				datalist.add(dataRows);
				datalist.add(showDataRows);
				datalist.add(amount);
				datalist.add(showAmount);
				
				return list;
			}
		}));
		
		map.put("datarows", datalist.get(0));
		map.put("showdatarows", datalist.get(1));
		map.put("amount", datalist.get(2));
		map.put("showamount", datalist.get(3));
		
		return map;
	}
	
	public Map<String, Object> getMrTodayDataQiBa(String tableName,String startDate,
			int spId, int spTroneId,int troneId, int cpId, int troneOrderId, int provinceId,
			int cityId, int sortType)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		String query = "";
		
		if(spId>0)
			query += " and d.id = " + spId;
		
		if(troneId>0)
			query += " and c.id = " + troneId;
		
		if(cpId>0)
			query += " and e.id = " + cpId;
		
		if(troneOrderId>0)
			query += " and b.id = " + troneOrderId;
		
		if(provinceId>0)
			query += " and f.id = " + provinceId;
		
		if(cityId>0)
			query += " and g.id = " + cityId;
		
		if(spTroneId>0)
			query += " and h.id = " + spTroneId;
		
		String[] result = getSortTypeQiBa(sortType);
		String queryParams = result[0];
		String joinId = result[1];
		
		String sql = "select a.show_title,aa,bb,cc,dd from (";
		sql += " select  " + joinId + " join_id," + queryParams + " show_title,count(*) aa,sum(c.price) bb";
		sql += " from daily_log.tbl_mr_" + tableName + " a";
		sql += " left join daily_config.tbl_trone_order b on a.trone_order_id = b.id ";
		sql += " left join daily_config.tbl_trone c on b.trone_id = c.id";
		sql += " left join daily_config.tbl_sp d on c.sp_id = d.id";
		sql += " left join daily_config.tbl_cp e on b.cp_id = e.id ";
		sql += " left join daily_config.tbl_province f on a.province_id = f.id";
		sql += " left join daily_config.tbl_city g on a.city_id = g.id";
		sql += " left join daily_config.tbl_sp_trone h on c.sp_trone_id = h.id";
		sql += " where a.mr_date >= '" + startDate + "' and a.mr_date <= '" + startDate + "' " + query;
		sql += " group by join_id order by show_title asc )a";
		sql += " left join(";
		sql += " select  " + joinId + " join_id," + queryParams + " show_title,count(*) cc,sum(c.price) dd";
		sql += " from daily_log.tbl_cp_mr_" + tableName + " a ";
		sql += " left join daily_config.tbl_trone_order b on a.trone_order_id = b.id";
		sql += " left join daily_config.tbl_trone c on b.trone_id = c.id";
		sql += " left join daily_config.tbl_sp d on c.sp_id = d.id";
		sql += " left join daily_config.tbl_cp e on b.cp_id = e.id";
		sql += " left join daily_config.tbl_province f on a.province_id = f.id";
		sql += " left join daily_config.tbl_city g on a.city_id = g.id";
		sql += " left join daily_config.tbl_sp_trone h on c.sp_trone_id = h.id";
		sql += " where a.mr_date >= '" + startDate + "' and a.mr_date <= '" + startDate + "' " + query;
		sql += " group by join_id order by show_title asc";
		sql += " )b on a.join_id = b.join_id;";		
		
		
		JdbcControl control = new JdbcControl();
		
		final List<Object> datalist = new ArrayList<Object>();
		
		map.put("list", control.query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<MrReportModel> list = new ArrayList<MrReportModel>();
				int dataRows=0,showDataRows = 0;
				double amount=0,showAmount = 0;
				while(rs.next())
				{
					MrReportModel model = new MrReportModel();
					
					model.setTitle1(rs.getString("show_title"));
					model.setDataRows(rs.getInt(2));
					model.setAmount(rs.getFloat(3));
					model.setShowDataRows(rs.getInt(4));
					model.setShowAmount(rs.getFloat(5));
					
					dataRows += model.getDataRows();
					showDataRows += model.getShowDataRows();
					amount += model.getAmount();
					showAmount += model.getShowAmount();
					
					list.add(model);
				}
				
				datalist.add(dataRows);
				datalist.add(showDataRows);
				datalist.add(amount);
				datalist.add(showAmount);
				
				return list;
			}
		}));
		
		map.put("datarows", datalist.get(0));
		map.put("showdatarows", datalist.get(1));
		map.put("amount", datalist.get(2));
		map.put("showamount", datalist.get(3));
		
		return map;
	}
	
	//sortType 1:天  2:周  3：月  4：SP 5：CP 6：TRONE 7 CP通道 8省份 9城市 10SP业务 11小时 12SP商务 13CP商务 14运营商 15数据类型 16一级业务线 17二级业务线
	private String[] getSortType(int sortType)
	{
		String joinId = " a.mr_date ";
		String queryParams = " a.mr_date ";
		switch(sortType)
		{
			case 1:
				joinId = " a.mr_date ";
				break;
			case 2:
				queryParams = " DATE_FORMAT(a.mr_date,'%Y-%u') ";
				joinId = " DATE_FORMAT(a.mr_date,'%Y-%u') ";
				break;
			case 3:
				queryParams = " DATE_FORMAT(a.mr_date,'%Y-%m') ";
				joinId = " DATE_FORMAT(a.mr_date,'%Y-%m') ";
				break;
			case 4:
				queryParams = " d.short_name  ";
				joinId = " d.id ";
				break;
			case 5:
				queryParams = " e.short_name  ";
				joinId = " e.id ";
				break;
			case 6:
				queryParams = " concat(d.short_name,'-',c.trone_name,'-',c.price)  ";
				joinId = " c.id ";
				break;
			case 7:
				queryParams = " concat(e.short_name,'-',d.short_name,'-',c.trone_name,'-',c.price)  ";
				joinId = " b.id ";
				break;
			case 8:
				queryParams = " f.name  ";
				joinId = " f.id ";
				break;
			case 9:
				queryParams = " g.name  ";
				joinId = " g.id ";
				break;
				
			case 10:
				queryParams = " CONCAT(d.short_name,'-',h.name)  ";
				joinId = " h.id ";
				break;
				
			case 11:
				queryParams = " DATE_FORMAT(a.create_date,'%Y-%m-%d-%H') ";
				joinId = " DATE_FORMAT(a.create_date,'%Y-%u-%d-%H') ";
				break;
				
			case 12:
				queryParams = " j.nick_name ";
				joinId = " j.id ";
				break;
				
			case 13:
				queryParams = " k.nick_name ";
				joinId = " k.id ";
				break;
				
			case 14:
				queryParams = " n.name_cn ";
				joinId = " n.id ";
				break;
			
			case 15:
				queryParams = " a.record_type ";
				joinId = " a.record_type ";
				break;
				
			case 16: //一级业务线
				queryParams = "  CONCAT(n.name_cn,'-',m.name) ";
				joinId = " m.id ";
				break;
				
			case 17: //二级业务线
				queryParams = "  CONCAT(n.name_cn,'-',m.name,'-',l.name) ";
				joinId = " l.id ";
				break;
				
			default:
					break;
		}
		
		String[] result = {queryParams,joinId};
		return result;
	}
	
	//sortType 1:天  2:周  3：月  4：SP 5：CP 6：TRONE 7:TRONE_ORDER 8:PROVINCE 9:CITY 10:SP业务 11小时
	private String[] getSortTypeQiBa(int sortType)
	{
		String joinId = " a.mr_date ";
		String queryParams = " a.mr_date ";
		switch(sortType)
		{
			case 1:
				joinId = " a.mr_date ";
				break;
			case 2:
				queryParams = " DATE_FORMAT(a.mr_date,'%Y-%u') ";
				joinId = " DATE_FORMAT(a.mr_date,'%Y-%u') ";
				break;
			case 3:
				queryParams = " DATE_FORMAT(a.mr_date,'%Y-%m') ";
				joinId = " DATE_FORMAT(a.mr_date,'%Y-%m') ";
				break;
			case 4:
				queryParams = " d.id + 1000  ";
				joinId = " d.id ";
				break;
			case 5:
				queryParams = " e.id + 2000  ";
				joinId = " e.id ";
				break;
			case 6:
				queryParams = " concat(1000 + d.id,'-',c.trone_name,'-',c.price)  ";
				joinId = " c.id ";
				break;
			case 7:
				queryParams = " concat(2000 + e.id,'-',1000 + d.id,'-',c.trone_name,'-',c.price)  ";
				joinId = " b.id ";
				break;
			case 8:
				queryParams = " f.name  ";
				joinId = " f.id ";
				break;
			case 9:
				queryParams = " g.name  ";
				joinId = " g.id ";
				break;
				
			case 10:
				queryParams = " concat(1000 + d.id,'-',h.name)  ";
				joinId = " h.id ";
				break;
				
			case 11:
				queryParams = " DATE_FORMAT(a.create_date,'%Y-%m-%d-%H') ";
				joinId = " DATE_FORMAT(a.create_date,'%Y-%u-%d-%H') ";
				break;
				
			default:
					break;
		}
		String[] result = {queryParams,joinId};
		return result;
	}
	
	/**
	 * 更新MR汇总表里面的上游结算率
	 * @param spTroneId
	 * @param rate
	 * @param startDate
	 * @param endDate
	 */
	public void updateMrRate(int spTroneId,float rate,String startDate,String endDate)
	{
		String sql = "UPDATE daily_log.`tbl_mr_summer_2` a,daily_config.`tbl_trone` b,daily_config.`tbl_sp_trone` c ";
		sql += " SET a.sp_trone_rate = " + rate;
		sql += " WHERE a.`trone_id` = b.`id` ";
		sql += " AND b.`sp_trone_id` = c.id";
		sql += " AND a.`mr_date` >= '" + startDate + "'";
		sql += " AND a.`mr_date` <= '" + endDate + "'";
		sql += " AND c.id = " + spTroneId;
		
		new JdbcControl().execute(sql);
	}
	
	public void updateCpMrRate(int cpId,int spTroneId,float rate,String startDate,String endDate)
	{
		String sql = " UPDATE daily_log.`tbl_cp_mr_summer_2` a,daily_config.`tbl_trone` b,";
		
		sql += " daily_config.`tbl_trone_order` c,daily_config.`tbl_sp_trone` d,daily_config.`tbl_cp` e";
		sql += " SET a.`rate` = " + rate;
		sql += " WHERE a.`trone_order_id` = c.`id`";
		sql += " AND c.`trone_id` = b.`id`";
		sql += " AND b.`sp_trone_id` = d.`id`";
		sql += " AND c.`cp_id` = e.`id`";
		sql += " AND a.`mr_date` >= '" + startDate + "'";
		sql += " AND a.`mr_date` <= '" + endDate + "'";
		sql += " AND e.id = " + cpId;
		sql += " AND d.id = " + spTroneId;
		
		new JdbcControl().execute(sql);
	}
	
	
	public static void main(String[] args)
	{
		Map<String, Object> map =new MrDaoSj().getMrAnalyData("2015-09-27", "2015-09-27", 0, 0,0, 0, 0, 0, 0, -1,-1,-1,-1,1);
		
//		map.put("datarows", datalist.get(0));
//		map.put("showdatarows", datalist.get(1));
//		map.put("amount", datalist.get(2));
//		map.put("showamount", datalist.get(3));
		
		System.out.println(map.get("datarows"));
		System.out.println(map.get("showdatarows"));
		System.out.println(map.get("amount"));
		System.out.println(map.get("showamount"));
		
	}
	
}
