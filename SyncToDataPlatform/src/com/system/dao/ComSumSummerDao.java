package com.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.database.HtJdbcControl;
import com.system.database.IJdbcControl;
import com.system.database.JdbcControl;
import com.system.database.QueryCallBack;
import com.system.database.TlJdbcControl;
import com.system.database.YdJdbcControl;
import com.system.model.ComSumSummerModel;
import com.system.model.FeeDateDataModel;

public class ComSumSummerDao
{
	public void addCpDailyData(String sql)
	{
		new JdbcControl().execute(sql);
	}
	
	//删除指定公司内的指定时间的数据
	public void delDailyData(int companyId,String startDate,String endDate)
	{
		String sql = "DELETE FROM comsum_config.`tbl_data_summer` WHERE co_id = " + companyId + " AND mr_date >= '" + startDate + "' and mr_date <= '" + endDate + "'";
		new JdbcControl().execute(sql);
	}
	
	//根据公司获取指定时间内的 数据
	@SuppressWarnings("unchecked")
	public List<ComSumSummerModel> loadComSumSummerData(int coId,String startDate,String endDate)
	{
		StringBuffer sql = new StringBuffer(512);
		
		int keepCpId = -1;
		
		sql.append(" SELECT a.mr_date,a.trone_id,a.trone_order_id,a.province_id,a.data_rows,");
		sql.append(" a.amount,b.data_rows show_data_rows,b.amount show_amount,record_type,cp_id");
		sql.append(" FROM");
		sql.append(" (");
		sql.append(" SELECT mr_date,trone_order_id,b.trone_id,province_id,SUM(data_rows) data_rows,SUM(a.amount) amount,record_type,c.id cp_id");
		sql.append(" FROM daily_log.`tbl_mr_summer` a");
		sql.append(" LEFT JOIN daily_config.tbl_trone_order b ON a.`trone_order_id` = b.`id`");
		sql.append(" LEFT JOIN daily_config.`tbl_cp` c ON b.`cp_id` = c.id");
		sql.append(" WHERE mr_date >= '" + startDate + "' AND mr_date <= '" + endDate + "'");
		sql.append(" GROUP BY mr_date,trone_order_id,province_id");
		sql.append(" ) a");
		sql.append(" LEFT JOIN");
		sql.append(" (");
		sql.append(" SELECT mr_date,trone_order_id,province_id,SUM(data_rows) data_rows,SUM(amount) amount ");
		sql.append(" FROM daily_log.`tbl_cp_mr_summer` ");
		sql.append(" WHERE mr_date >= '" + startDate + "' AND mr_date <= '" + endDate + "'");
		sql.append(" GROUP BY mr_date,trone_order_id,province_id");
		sql.append(" ) b ON a.trone_order_id = b.trone_order_id AND a.province_id = b.province_id AND a.mr_date = b.mr_date");
		sql.append(" ORDER BY a.mr_date,trone_order_id,province_id");
		
		IJdbcControl control = getCoControl(coId);
		
		switch(coId)
		{
			case 1:
				keepCpId = 157;
				break;
				
			case 2:
				keepCpId = 43;
				break;
				
			case 3:
				keepCpId = 162;
				break;			
				
			default:
				break;
		}
		
		final int lastKeepCpId = keepCpId;
		
		if(control!=null)
		{
			return (List<ComSumSummerModel>)control.query(sql.toString(), new QueryCallBack()
			{
				@Override
				public Object onCallBack(ResultSet rs) throws SQLException
				{
					String mrDate = "";
					int troneId;
					int provinceId;
					int dataRows;
					float amount;
					int showDataRows;
					float showAmount;
					int recordType;
					int cpId;
					int insertId;
					int diffDataRows;
					float diffAmount;
					
					List<ComSumSummerModel> list = new ArrayList<ComSumSummerModel>();
					
					while(rs.next())
					{
						mrDate = rs.getString("mr_date");
						troneId = rs.getInt("trone_id");
						provinceId = rs.getInt("province_id");
						dataRows = rs.getInt("data_rows");
						amount = rs.getFloat("amount");
						showDataRows = rs.getInt("show_data_rows");
						showAmount = rs.getFloat("show_amount");
						recordType = rs.getInt("record_type");
						cpId = rs.getInt("cp_id");
						insertId = cpId;
						diffDataRows = dataRows - showDataRows;
						diffAmount = amount - showAmount;
						
						if(cpId==34)
						{
							insertId = lastKeepCpId;
							ComSumSummerModel model = new ComSumSummerModel();
							model.setMrDate(mrDate);
							model.setAmount(amount);
							model.setDataRows(dataRows);
							model.setTroneId(troneId);
							model.setProvinceId(provinceId);
							model.setRecordType(recordType);
							model.setCpId(insertId);
							list.add(model);
							continue;
						}
						
						//正常传送给CP的数据
						if(showAmount>0)
						{
							ComSumSummerModel model = new ComSumSummerModel();
							model.setMrDate(mrDate);
							model.setAmount(showAmount);
							model.setDataRows(showDataRows);
							model.setTroneId(troneId);
							model.setProvinceId(provinceId);
							model.setRecordType(recordType);
							model.setCpId(insertId);
							list.add(model);
						}
						
						//扣掉的CP量
						if(diffAmount!=0)
						{
							ComSumSummerModel model = new ComSumSummerModel();
							model.setMrDate(mrDate);
							model.setAmount(diffAmount);
							model.setDataRows(diffDataRows);
							model.setTroneId(troneId);
							model.setProvinceId(provinceId);
							model.setRecordType(recordType);
							model.setCpId(lastKeepCpId);
							list.add(model);
						}
					}
					
					return list;
				}
			});
		}
		
		return null;
	}
	
	/**
	 * 从指定公司里面获取指定日期的每日数据
	 * @param coId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map<String,FeeDateDataModel> loadOriSource(int coId,String startDate,String endDate)
	{
		String sql = " SELECT mr_date,SUM(data_rows) data_rows,SUM(amount) amount FROM daily_log.`tbl_mr_summer` a";
		sql += " WHERE mr_date >= '" + startDate + "' AND mr_date <= '" + endDate + "'";
		sql += " GROUP BY mr_date ORDER BY mr_date";
		
		IJdbcControl control = getCoControl(coId);
		
		if(control!=null)
		{
			control.query(sql, new QueryCallBack()
			{
				@Override
				public Object onCallBack(ResultSet rs) throws SQLException
				{
					Map<String,FeeDateDataModel> map = new HashMap<String, FeeDateDataModel>();
					
					while(rs.next())
					{
						FeeDateDataModel model = new FeeDateDataModel();
						model.setFeeDate(rs.getString("mr_date"));
						model.setAmount(rs.getFloat("amount"));
						model.setDataRows(rs.getInt("data_rows"));
						map.put(model.getFeeDate(), model);
					}
					
					return map;
				}
			});
		}
		
		return null;
	}
	
	private IJdbcControl getCoControl(int coId)
	{
		IJdbcControl control = null;
		
		switch(coId)
		{
			case 1:
				control = new HtJdbcControl();
				break;
				
			case 2:
				control = new TlJdbcControl();
				break;
				
			case 3:
				control = new YdJdbcControl();
				break;			
				
			default:
				break;
		}
		
		return control;
	}
	
}
