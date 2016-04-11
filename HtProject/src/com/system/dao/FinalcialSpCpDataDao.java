package com.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.system.database.JdbcControl;
import com.system.database.QueryCallBack;
import com.system.util.StringUtil;
import com.system.vmodel.FinancialSpCpDataShowModel;

public class FinalcialSpCpDataDao
{
	@SuppressWarnings("unchecked")
	public List<FinancialSpCpDataShowModel> loadData(String startDate,String endDate,int spId,int cpId,int dataType)
	{
		String query = "";
		
		if(spId>0)
			query += " and d.id = " + spId;
		
		if(cpId>0)
			query += " and f.id = " + cpId;
		
		if(dataType>-1)
			query+= " and a.record_type = " + dataType;
		
		String sql = "SELECT a.sp_id,a.sp_name,a.sp_trone_id,a.sp_trone_name,a.cp_id,";
		sql += " a.cp_name,a.data_rows,a.amount,b.show_data_rows,b.show_amounts,";
		sql += " a.sp_jie_suan_lv,b.cp_jie_suan_lv";
		sql += " FROM(";
		sql += " SELECT d.id sp_id,d.`short_name` sp_name,c.id sp_trone_id,c.`name` sp_trone_name,";
		sql += " f.id cp_id,f.`short_name` cp_name,c.`jiesuanlv` sp_jie_suan_lv,SUM(a.data_rows) data_rows,SUM(a.amount) amount";
		sql += " FROM daily_log.tbl_mr_summer a";
		sql += " LEFT JOIN daily_config.`tbl_trone` b ON a.`trone_id` = b.`id`";
		sql += " LEFT JOIN daily_config.`tbl_sp_trone` c ON b.`sp_trone_id` = c.`id`";
		sql += " LEFT JOIN daily_config.tbl_sp d ON c.`sp_id` = d.`id`";
		sql += " LEFT JOIN daily_config.`tbl_trone_order` e ON a.`trone_order_id` = e.`id`";
		sql += " LEFT JOIN daily_config.tbl_cp f ON e.`cp_id` = f.id";
		sql += " WHERE a.`mr_date` >= '" + startDate + "' AND a.`mr_date` <= '" + endDate + "'" + query;
		sql += " GROUP BY d.id,c.id,f.id ORDER BY CONVERT(d.`short_name` USING gbk),CONVERT(c.`name` USING gbk),CONVERT(f.`short_name` USING gbk) ASC";
		sql += " ) a";
		sql += " LEFT JOIN ";
		sql += " (";
		sql += " SELECT d.id sp_id,d.`short_name` sp_name,c.id sp_trone_id,c.`name` sp_trone_name,";
		sql += " f.id cp_id,f.`short_name` cp_name,g.`jiesuanlv` cp_jie_suan_lv,SUM(a.data_rows) show_data_rows,SUM(a.amount) show_amounts";
		sql += " FROM daily_log.`tbl_cp_mr_summer` a";
		sql += " LEFT JOIN daily_config.`tbl_trone_order` e ON a.`trone_order_id` = e.`id`";
		sql += " LEFT JOIN daily_config.tbl_trone b ON e.`trone_id` = b.`id`";
		sql += " LEFT JOIN daily_config.tbl_sp_trone c ON b.`sp_trone_id` = c.`id`";
		sql += " LEFT JOIN daily_config.tbl_sp d ON c.`sp_id` = d.`id`";
		sql += " LEFT JOIN daily_config.tbl_cp f ON e.`cp_id` = f.`id`";
		sql += " LEFT JOIN daily_config.`tbl_cp_jiesuan` g ON f.id = g.`cp_id` AND c.`id` = g.`sp_trone_id`";
		sql += " WHERE a.`mr_date` >= '" + startDate + "' AND a.`mr_date` <= '" + endDate + "'" + query;
		sql += " GROUP BY d.id,c.id,f.id ORDER BY CONVERT(d.`short_name` USING gbk),CONVERT(c.`name` USING gbk),CONVERT(f.`short_name` USING gbk) ASC";
		sql += " ) b";
		sql += " ON a.sp_id = b.`sp_id` AND a.sp_trone_id = b.sp_trone_id AND a.cp_id = b.cp_id";
		sql += " ORDER BY CONVERT(a.sp_name USING gbk),CONVERT(a.sp_trone_name USING gbk),CONVERT(a.cp_name USING gbk) ASC";
		
		return (List<FinancialSpCpDataShowModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<FinancialSpCpDataShowModel> list = new ArrayList<FinancialSpCpDataShowModel>();
				int spId;
				String spShortName;
				int spTroneId;
				String spTroneName;
				double spJieSuanLv;
				int dataRows;
				double amount;
				int showDataRows;
				double showAmount;
				int cpId;
				String cpShortName;
				double cpJieSuanLv;
				
				FinancialSpCpDataShowModel model = null;
				FinancialSpCpDataShowModel.SpTroneModel spTroneModel = null;
				
				while(rs.next())
				{
					spId = rs.getInt("sp_id");
					spShortName = StringUtil.getString(rs.getString("sp_name"), "");
					spTroneId = rs.getInt("sp_trone_id");
					spTroneName = StringUtil.getString(rs.getString("sp_trone_name"), "");
					spJieSuanLv = rs.getDouble("sp_jie_suan_lv");
					dataRows = rs.getInt("data_rows");
					amount = rs.getDouble("amount");
					showDataRows = rs.getInt("show_data_rows");
					showAmount = rs.getInt("show_amounts");
					cpId = rs.getInt("cp_id");
					cpShortName = StringUtil.getString(rs.getString("cp_name"), "");
					cpJieSuanLv = rs.getDouble("cp_jie_suan_lv");
					
					boolean existSp = false;
					
					for(FinancialSpCpDataShowModel spCpDataShowModel : list)
					{
						if(spCpDataShowModel.spId == spId)
						{
							existSp = true;
							break;
						}
					}
					
					if(!existSp)
					{
						model = new FinancialSpCpDataShowModel();
						
						model.spId = spId;
						model.spShortName = spShortName;
						
						spTroneModel = model.new SpTroneModel();
						spTroneModel.spJieSuanLv = spJieSuanLv;
						spTroneModel.spTroneId = spTroneId;
						spTroneModel.spTroneName = spTroneName;
						
						model.list.add(spTroneModel);
						
						FinancialSpCpDataShowModel.SpTroneModel.CpModelData cpModelData = spTroneModel.new CpModelData();
						
						cpModelData.cpId = cpId;
						cpModelData.cpShortName = cpShortName;
						cpModelData.cpJieSuanLv = cpJieSuanLv;
						cpModelData.dataRows = dataRows;
						cpModelData.showDataRows = showDataRows;
						cpModelData.amount = amount;
						cpModelData.showAmount = showAmount;
						
						spTroneModel.list.add(cpModelData);
						
						list.add(model);
					}
					else
					{
						boolean existSpTrone = false;
						for(FinancialSpCpDataShowModel.SpTroneModel spTroneModel1 : model.list)
						{
							if(spTroneModel1.spTroneId==spTroneId)
							{
								existSpTrone = true;
								break;
							}
						}
						if(!existSpTrone)
						{
							spTroneModel = model.new SpTroneModel();
							spTroneModel.spJieSuanLv = spJieSuanLv;
							spTroneModel.spTroneId = spTroneId;
							spTroneModel.spTroneName = spTroneName;
							
							model.list.add(spTroneModel);
							
							FinancialSpCpDataShowModel.SpTroneModel.CpModelData cpModelData = spTroneModel.new CpModelData();
							
							cpModelData.cpId = cpId;
							cpModelData.cpShortName = cpShortName;
							cpModelData.cpJieSuanLv = cpJieSuanLv;
							cpModelData.dataRows = dataRows;
							cpModelData.showDataRows = showDataRows;
							cpModelData.amount = amount;
							cpModelData.showAmount = showAmount;
							
							spTroneModel.list.add(cpModelData);
						}
						else
						{
							FinancialSpCpDataShowModel.SpTroneModel.CpModelData cpModelData = spTroneModel.new CpModelData();
							
							cpModelData.cpId = cpId;
							cpModelData.cpShortName = cpShortName;
							cpModelData.cpJieSuanLv = cpJieSuanLv;
							cpModelData.dataRows = dataRows;
							cpModelData.showDataRows = showDataRows;
							cpModelData.amount = amount;
							cpModelData.showAmount = showAmount;
							
							spTroneModel.list.add(cpModelData);
							
							spTroneModel.spTroneRowSpan++;
						}
						model.spRowSpan++;
					}
					
					
					
					spId = 0;
					spShortName = "";
					spTroneId = 0;
					spTroneName = "";
					spJieSuanLv = 0;
					dataRows = 0;
					amount = 0;
					showDataRows = 0;
					showAmount = 0;
					cpId = 0;
					cpShortName = "";
					cpJieSuanLv = 0;
				}
				
				return list;
			}
		});
	}
	
	public static void main(String[] args)
	{
		List<FinancialSpCpDataShowModel> list = new FinalcialSpCpDataDao().loadData("2015-10-01", "2015-11-30",1,2,0);
		for(FinancialSpCpDataShowModel model : list)
		{
			for(FinancialSpCpDataShowModel.SpTroneModel spTroneModel : model.list)
			{
				for(FinancialSpCpDataShowModel.SpTroneModel.CpModelData cpModelData : spTroneModel.list)
				{
					System.out.println("----CpName:" + cpModelData.cpShortName + ";dataRows:" + cpModelData.dataRows + ";"
							+ "showDataRows:" + cpModelData.showDataRows + ";amount:" + cpModelData.amount + ";showAmount:" 
							+ cpModelData.showAmount + ";CpJieSuanLv:" + cpModelData.cpJieSuanLv);
				}
			}
		}
	}
}
