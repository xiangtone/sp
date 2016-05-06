package com.system.dao;

import com.system.database.JdbcControl;

public class DailyAnalyDao
{
	public boolean analyDailyMr(String date,String dateMonth)
	{
		StringBuffer sb = new StringBuffer(1024);
		
		JdbcControl control = new JdbcControl();
		
		sb.append("DELETE FROM  daily_log.tbl_mr_summer WHERE mr_date >= '" + date + "' AND mr_date <= '" 
		+ date + "'   AND record_type IN (0, 2);");
		
		control.execute(sb.toString());
		sb.delete(0,sb.length());
		
		sb.append("DELETE  FROM daily_log.tbl_cp_mr_summer  WHERE mr_date >= '" + date + "' AND mr_date <= '" 
		+ date + "' AND record_type IN (0, 2);");
		
		control.execute(sb.toString());
		sb.delete(0,sb.length());
		
		sb.append("INSERT INTO daily_log.tbl_mr_summer (mr_date,sp_id,trone_id,cp_id,trone_order_id,mcc,province_id,city_id,data_rows,amount) ");
		sb.append(" SELECT  mr_date, a.sp_id, trone_id, cp_id, trone_order_id, mcc, province_id, city_id, COUNT(*) data_rows, SUM(b.price) amount ");
		sb.append(" FROM daily_log.tbl_mr_" + dateMonth + " a LEFT JOIN daily_config.tbl_trone b ON a.trone_id = b.id");
		sb.append(" WHERE mr_date >= '" + date + "' AND mr_date <= '" + date + "'  AND a.trone_id > 0  AND trone_order_id > 0  AND trone_type = 0 ");
		sb.append(" GROUP BY mr_date,a.sp_id,b.id,cp_id,trone_order_id,mcc,province_id,city_id ");
		sb.append(" ORDER BY mr_date,a.sp_id,b.id,cp_id,trone_order_id,mcc,province_id,city_id;");
		
		control.execute(sb.toString());
		sb.delete(0,sb.length());
		
		sb.append("INSERT INTO daily_log.tbl_mr_summer (mr_date,sp_id, trone_id,cp_id,trone_order_id,mcc,province_id,city_id,data_rows,amount, record_type) ");
		sb.append(" SELECT mr_date, a.sp_id, trone_id, cp_id,trone_order_id,mcc,province_id,city_id,COUNT(*) data_rows,SUM(b.price * a.ivr_time) amount,2 record_type ");
		sb.append(" FROM daily_log.tbl_mr_" + dateMonth + " a  LEFT JOIN daily_config.tbl_trone b  ON a.trone_id = b.id");
		sb.append(" WHERE mr_date >= '" + date + "' AND mr_date <= '" + date + "' AND a.trone_id > 0  AND trone_order_id > 0 AND trone_type = 2 ");
		sb.append(" GROUP BY mr_date,a.sp_id,b.id,cp_id,trone_order_id,mcc,province_id,city_id");
		sb.append(" ORDER BY mr_date,a.sp_id,b.id,cp_id,trone_order_id,mcc,province_id,city_id;");
		
		control.execute(sb.toString());
		sb.delete(0,sb.length());
		
		sb.append("INSERT INTO daily_log.tbl_cp_mr_summer (mr_date,cp_id,trone_order_id,mcc,province_id,city_id,data_rows,amount) ");
		sb.append(" SELECT a.mr_date,b.cp_id,b.id trone_order_id,a.mcc,province_id,city_id,COUNT(*) data_rows, SUM(c.price) amount ");
		sb.append(" FROM daily_log.tbl_cp_mr_" + dateMonth + " a   LEFT JOIN daily_config.tbl_trone_order b   ON a.trone_order_id = b.id");
		sb.append(" LEFT JOIN daily_config.tbl_trone c  ON b.trone_id = c.id ");
		sb.append(" WHERE a.mr_date >= '" + date + "' AND a.mr_date <= '" + date + "' AND trone_type = 0 ");
		sb.append(" GROUP BY mr_date,cp_id,trone_order_id,mcc,province_id,city_id ORDER BY mr_date ASC;");
		
		control.execute(sb.toString());
		sb.delete(0,sb.length());
		
		sb.append("INSERT INTO daily_log.tbl_cp_mr_summer (mr_date,cp_id,trone_order_id,mcc,province_id,city_id,data_rows,amount,record_type) ");
		sb.append(" SELECT a.mr_date,b.cp_id,b.id trone_order_id,a.mcc,province_id,city_id,COUNT(*) data_rows,SUM(c.price * a.ivr_time) amount,2 ");
		sb.append(" FROM daily_log.tbl_cp_mr_" + dateMonth + " a LEFT JOIN daily_config.tbl_trone_order b ON a.trone_order_id = b.id");
		sb.append(" LEFT JOIN daily_config.tbl_trone c  ON b.trone_id = c.id");
		sb.append(" WHERE a.mr_date >= '" + date + "'  AND a.mr_date <= '" + date + "'  AND trone_type = 2");
		sb.append(" GROUP BY mr_date, cp_id, trone_order_id, mcc,  province_id, city_id ORDER BY mr_date ASC;");
		
		control.execute(sb.toString());
		sb.delete(0,sb.length());
		
		return true;
	}
	
	public static void main(String[] args)
	{
		System.out.println(new DailyAnalyDao().analyDailyMr("2016-05-05", "201605"));
	}
	
}
