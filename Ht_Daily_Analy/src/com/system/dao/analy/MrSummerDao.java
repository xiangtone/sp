package com.system.dao.analy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.system.database.JdbcControl;
import com.system.database.QueryCallBack;
import com.system.model.analy.MrSummerModel;

public class MrSummerDao 
{
	@SuppressWarnings("unchecked")
	public ArrayList<MrSummerModel> loadMrSummer(String tableName,String startDate,String endDate)
	{
		
		String sql = "select mr_date,sp_id,b.id trone_id,cp_id,trone_order_id,mcc,province_id,"
				+ "city_id,count(*) data_rows from daily_log.tbl_mr_"+ tableName +" a left join daily_config.tbl_trone b "
				+ "on a.trone_id = b.id left join daily_config.tbl_trone_order c on c.trone_id = b.id "
				+ "where mr_date >= '" + startDate + "' and mr_date <= '" + endDate + "' "
				+ "group by mr_date,sp_id,b.id,cp_id,trone_order_id,mcc,province_id,city_id";
		
		return (ArrayList<MrSummerModel>)new JdbcControl().query(sql, new QueryCallBack() 
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException 
			{
				ArrayList<MrSummerModel> list = new ArrayList<MrSummerModel>();
				
				while(rs.next())
				{
					MrSummerModel model = new MrSummerModel();
					
					model.setFeeDate(rs.getString("mr_date"));
					model.setSpId(rs.getInt("sp_id"));
					model.setCpId(rs.getInt("cp_id"));
					model.setTroneId(rs.getInt("trone_id"));
					model.setTroneOrderId(rs.getInt("trone_order_id"));
					model.setMcc(rs.getString("mcc"));
					model.setProvinceId(rs.getInt("province_id"));
					model.setCityId(rs.getInt("city_id"));
					model.setDataRows(rs.getInt("data_rows"));
					
					list.add(model);
				}
				
				return list;
			}
		});
	}
	
	
	
	/**
	 * 写入MR SUMMER数据之前确保相同的数据已经删除
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public boolean deleteMrSummer(String startDate,String endDate)
	{
		String sql  = "delete from daily_log.tbl_mr_summer where mr_date >= '" + startDate + "' and mr_date <= '" + endDate + "' and record_type in( 0,2,3) ";
		
		return new JdbcControl().execute(sql);
	}
	
	
	/**
	 * 在指定时间把昨天的数据分析到SUMMER表
	 */
	public boolean analyMrToSummer(String tableName,String startDate,String endDate)
	{
		String sql  = " insert into daily_log.tbl_mr_summer(mr_date,sp_id,trone_id,"
				+ " cp_id,trone_order_id,mcc,province_id,city_id,data_rows,amount)"
				+ " select mr_date,a.sp_id,trone_id,cp_id,trone_order_id,mcc,province_id,"
				+ " city_id,count(*) data_rows,sum(b.price) amount from daily_log.tbl_mr_"+ tableName +" a left join daily_config.tbl_trone b "
				+ " on a.trone_id = b.id "
				+ " where mr_date >= '" + startDate + "' and mr_date <= '" + endDate + "' AND a.trone_id>0 AND trone_order_id>0 AND trone_type = 0"
				+ " group by mr_date,a.sp_id,b.id,cp_id,trone_order_id,mcc,province_id,city_id "
				+ " ORDER BY mr_date,a.sp_id,b.id,cp_id,trone_order_id,mcc,province_id,city_id";
		
		return new JdbcControl().execute(sql);
	}
	
	
	/**
	 * 把第三方支付的数据同步到浩天数据
	 * @param tableName
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public boolean analyThirdPayToSummer(String tableName,String startDate,String endDate)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("INSERT INTO daily_log.`tbl_third_pay_mr_summer`(sp_id,cp_id,mcc,province_id,city_id,trone_id,trone_order_id,mr_date,data_rows,amount,record_type)");
		sb.append(" SELECT b.sp_id,b.cp_id,460,32,416,b.trone_id,b.trone_order_id,DATE_FORMAT(a.`createdate`,'%Y-%m-%d') mr_date,COUNT(*) data_rows,SUM(amount)/100 amount,3 FROM game_log.`tbl_xypay_" + tableName + "` a");
		sb.append(" LEFT JOIN");
		sb.append(" (");
		sb.append(" SELECT a.id trone_order_id,a.`order_num` appkey,b.`orders` pay_type,c.`sp_id`,a.cp_id,b.id trone_id  FROM daily_config.tbl_trone_order a");
		sb.append(" LEFT JOIN daily_config.`tbl_trone` b ON a.`trone_id` = b.`id`");
		sb.append(" LEFT JOIN daily_config.`tbl_sp_trone` c ON b.`sp_trone_id` = c.`id`");
		sb.append(" WHERE c.`trone_type` = 3");
		sb.append(" )");
		sb.append(" b ON a.`appkey` = b.appkey AND a.`oprator` = b.pay_type");
		sb.append(" WHERE a.oprator >= 4 AND a.`createdate` >= '" + startDate + " 00:00:00' AND a.`createdate` <= '" + endDate + " 23:59:59' AND trone_order_id >0 ");
		sb.append(" GROUP BY b.trone_order_id;");
		
		return new JdbcControl().execute(sb.toString());
	}
	
	
	/**
	 * 在指定时间把昨天的数据分析到SUMMER表
	 */
	public boolean analyIvrMrToSummer(String tableName,String startDate,String endDate)
	{
		String sql  = " insert into daily_log.tbl_mr_summer(mr_date,sp_id,trone_id,"
				+ " cp_id,trone_order_id,mcc,province_id,city_id,data_rows,amount,record_type)"
				+ " select mr_date,a.sp_id,trone_id,cp_id,trone_order_id,mcc,province_id,"
				+ " city_id,count(*) data_rows,sum(b.price*a.ivr_time) amount,2 record_type from daily_log.tbl_mr_"+ tableName +" a left join daily_config.tbl_trone b "
				+ " on a.trone_id = b.id "
				+ " where mr_date >= '" + startDate + "' and mr_date <= '" + endDate + "' AND a.trone_id>0 AND trone_order_id>0 AND trone_type = 2"
				+ " group by mr_date,a.sp_id,b.id,cp_id,trone_order_id,mcc,province_id,city_id "
				+ " ORDER BY mr_date,a.sp_id,b.id,cp_id,trone_order_id,mcc,province_id,city_id";
		
		return new JdbcControl().execute(sql);
	}
	
}
