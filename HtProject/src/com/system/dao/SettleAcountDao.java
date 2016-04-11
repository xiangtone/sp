package com.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.system.database.JdbcControl;
import com.system.database.QueryCallBack;
import com.system.model.SettleAccountModel;
import com.system.util.StringUtil;
import com.system.vmodel.SpFinanceShowModel;

public class SettleAcountDao
{
	@SuppressWarnings("unchecked")
	public List<SpFinanceShowModel> loadSpSettleAccountData(String startDate,String endDate)
	{
		String sql = "select d.id,d.short_name,e.name_cn,c.name,sum(a.amount) amounts,c.jiesuanlv";
		
			sql += " from daily_log.tbl_mr_summer a";
			sql += " left join daily_config.tbl_trone b on a.trone_id = b.id";
			sql += " left join daily_config.tbl_sp_trone c on b.sp_trone_id = c.id";
			sql += " left join daily_config.tbl_sp d on c.sp_id = d.id";
			sql += " left join daily_config.tbl_operator e on c.operator = e.id";
			sql += " where a.mr_date >= '" + startDate + "' and a.mr_date <= '" + endDate + "'";
			sql += " group by d.id,c.id";
			sql += " order by d.short_name,c.name";
		
		
		return (List<SpFinanceShowModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<SpFinanceShowModel> list = new ArrayList<SpFinanceShowModel>();
				SpFinanceShowModel model = null;
				
				while(rs.next())
				{
					model = new SpFinanceShowModel();
					
					model.setSpId(rs.getInt("id"));
					model.setShortName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setSpTroneName(StringUtil.getString(rs.getString("name"), ""));
					model.setOperatorName(StringUtil.getString(rs.getString("name_cn"), ""));
					model.setAmount(rs.getFloat("amounts"));
					model.setJiesuanlv(rs.getFloat("jiesuanlv"));
					
					list.add(model);
				}
				
				return list;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<SpFinanceShowModel> loadCpSettleAccountData(String startDate,String endDate)
	{
		String sql = " SELECT f.id,f.short_name,h.short_name sp_name,e.name_cn,d.name,sum(a.amount) amounts,g.jiesuanlv ";
		sql += " FROM daily_log.`tbl_cp_mr_summer` a  ";
		sql += " LEFT JOIN daily_config.tbl_trone_order b ON a.`trone_order_id` = b.`id`";
		sql += " Left join daily_config.tbl_trone c on b.trone_id = c.id ";
		sql += " LEFT JOIN daily_config.`tbl_sp_trone` d ON c.`sp_trone_id` = d.`id`";
		sql += " LEFT JOIN daily_config.`tbl_operator` e ON d.`operator` = e.`id` ";
		sql += " left join daily_config.tbl_cp f on b.cp_id = f.id ";
		sql += " left join daily_config.tbl_cp_jiesuan g on f.id = g.cp_id and d.id = g.sp_trone_id ";
		sql += " left join daily_config.tbl_sp h on d.sp_id = h.id ";
		sql += " where a.mr_date >= '" + startDate + "' and a.mr_date <= '" + endDate + "' ";
		sql += " group by f.id,d.id order by f.short_name,d.name";
		
		return (List<SpFinanceShowModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<SpFinanceShowModel> list = new ArrayList<SpFinanceShowModel>();
				SpFinanceShowModel model = null;
				
				while(rs.next())
				{
					model = new SpFinanceShowModel();
					
					model.setSpId(rs.getInt("id"));
					model.setShortName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setSpTroneName(StringUtil.getString(rs.getString("sp_name") + "-" + rs.getString("name"), ""));
					model.setOperatorName(StringUtil.getString(rs.getString("name_cn"), ""));
					model.setAmount(rs.getFloat("amounts"));
					model.setJiesuanlv(rs.getFloat("jiesuanlv"));
					
					list.add(model);
				}
				
				return list;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<SettleAccountModel> loadSpSettleAccountData(int spId,String startDate,String endDate)
	{
		String sql = "SELECT c.`name`,d.`name_cn`,SUM(a.amount) total_amount,c.`jiesuanlv`";
			sql += " FROM daily_log.`tbl_mr_summer` a";
			sql += " LEFT JOIN daily_config.tbl_trone b";
			sql += " ON a.`trone_id` = b.`id`";
			sql += " LEFT JOIN daily_config.`tbl_sp_trone` c";
			sql += " ON b.`sp_trone_id` = c.`id`";
			sql += " LEFT JOIN daily_config.`tbl_operator` d";
			sql += " ON c.`operator` = d.`id`";
			sql += " where a.`sp_id` =  " + spId;
			sql += " and a.mr_date >= '" + startDate + "' and a.mr_date <= '" + endDate + "'";
			sql += " group by c.id";
		
		return (List<SettleAccountModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<SettleAccountModel> list = new ArrayList<SettleAccountModel>();
				SettleAccountModel model = null;
				
				while(rs.next())
				{
					model = new SettleAccountModel();
					
					model.setSpTroneName(StringUtil.getString(rs.getString("name"), ""));
					model.setOperatorName(StringUtil.getString(rs.getString("name_cn"), ""));
					model.setAmount(rs.getFloat("total_amount"));
					model.setJiesuanlv(rs.getFloat("jiesuanlv"));
					
					list.add(model);
				}
				
				return list;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<SettleAccountModel> loadCpSettleAccountData(int cpId,String startDate,String endDate)
	{
		String sql = "SELECT c.`name`,d.`name_cn`,SUM(a.amount) total_amount,c.`jiesuanlv`";
			sql += " FROM daily_log.`tbl_cp_mr_summer` a";
			sql += " LEFT JOIN daily_config.tbl_trone_order b ON a.`trone_order_id` = b.`id` ";
			sql += " LEFT JOIN daily_config.tbl_trone e ON b.`trone_id` = e.`id`";
			sql += " LEFT JOIN daily_config.`tbl_sp_trone` c ON e.`sp_trone_id` = c.`id`";
			sql += " LEFT JOIN daily_config.`tbl_operator` d ON c.`operator` = d.`id`";
			sql += " left join daily_config.tbl_cp g on b.cp_id = g.id";
			sql += " left join daily_config.tbl_cp_jiesuan f on e.id = f.cp_id and c.id = f.sp_trone_id";
			sql += " where a.`cp_id` =  " + cpId;
			sql += " and a.mr_date >= '" + startDate + "' and a.mr_date <= '" + endDate + "'";
			sql += " group by c.id";
		
		return (List<SettleAccountModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<SettleAccountModel> list = new ArrayList<SettleAccountModel>();
				SettleAccountModel model = null;
				
				while(rs.next())
				{
					model = new SettleAccountModel();
					
					model.setOperatorName(StringUtil.getString(rs.getString("name_cn"), ""));
					model.setSpTroneName(StringUtil.getString(rs.getString("name"), ""));
					model.setAmount(rs.getFloat("total_amount"));
					model.setJiesuanlv(rs.getFloat("jiesuanlv"));
					
					list.add(model);
				}
				
				return list;
			}
		});
	}
}
