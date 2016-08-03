package com.system.sdk.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.system.database.JdbcGameControl;
import com.system.database.QueryCallBack;
import com.system.sdk.model.SdkDataSummerModel;
import com.system.util.StringUtil;

public class SdkDataSummerDao
{
	public void loadSdkDataSummer(int cpId,int channelId,int appId,int troneId,int spTroneId,String startDate,String endDate,int showType)
	{
		String[] joinQuery = getShowType(showType);
		String queryParams = joinQuery[0];
		String joinId = joinQuery[1];
		
		String query = "";
		
		if(cpId>0)
		{
			query += " AND g.cp_id = " + cpId;
		}
		
		if(channelId>0)
		{
			query += " AND d.sdk_channel_id = " + channelId;
		}
		
		if(appId>0)
		{
			query += " AND e.sdk_app_id = " + appId;
		}
		
		String troneQuery = "";
		
		if(troneId>0)
		{
			troneQuery += " AND b.trone_id = " + troneId;
		}
		
		if(spTroneId>0)
		{
			troneQuery += " AND c.sp_trone_id = " + spTroneId;
		}
		
		String sql = " SELECT a.*,b.user_rows,b.activity_rows ";
		sql += " FROM ( ";
		sql += " SELECT " + joinId + " join_id, " + queryParams + " show_title,";
		sql += " SUM(data_rows) trone_request_rows, ";
		sql += " SUM(CASE WHEN STATUS <> 99 THEN data_rows END) trone_effect_request_rows, ";
		sql += " SUM(CASE WHEN STATUS = 3 OR STATUS = 4 OR STATUS = 5 OR STATUS = 0 THEN data_rows END) trone_order_rows, ";
		sql += " SUM(CASE WHEN STATUS = 4 OR STATUS = 0 THEN data_rows END) msg_rows, ";
		sql += " SUM(CASE WHEN STATUS = 0 THEN data_rows END) suc_rows, ";
		sql += " SUM(CASE WHEN STATUS = 0 THEN data_rows*b.price END)/100 amount ";
		sql += " FROM game_log.`tbl_sdk_fee_summer` a ";
		sql += " LEFT JOIN daily_config.`tbl_sdk_trone` b ON a.`trone_id` = b.`trone_id` ";
		sql += " LEFT JOIN daily_config.tbl_sdk_sp_trone c ON b.`sp_trone_id` = c.`sp_trone_id` ";
		sql += " LEFT JOIN daily_config.`tbl_sdk_channel` d ON a.sdk_channel_id = d.sdk_channel_id ";
		sql += " LEFT JOIN daily_config.`tbl_sdk_app` e ON d.app_id = e.sdk_app_id ";
		sql += " LEFT JOIN daily_config.`tbl_province` f ON a.province_id = f.id ";
		sql += " LEFT JOIN daily_config.`tbl_sdk_cp` g ON e.cp_id = g.cp_id ";
		sql += " WHERE a.mr_date >= '" + startDate + "' AND a.mr_date <= '" + endDate + "' ";
		sql += " AND a.sdk_channel_id > 0 ";
		
		sql += query;
		
		sql += troneQuery;
		
		sql += " GROUP BY join_id ";
		sql += " ) a ";
		sql += " LEFT JOIN ";
		sql += " ( ";
		sql += " SELECT " + ( (showType==7 || showType==8) ? "-99999" : joinId) + " join_id,SUM(user_rows) user_rows,SUM(activity_rows) activity_rows ";
		sql += " FROM game_log.`tbl_sdk_base_data_summer` a ";
		sql += " LEFT JOIN daily_config.`tbl_sdk_channel` d ON a.`sdk_channel_id` = d.`sdk_channel_id` ";
		sql += " LEFT JOIN daily_config.`tbl_sdk_app` e ON d.`app_id` = e.`sdk_app_id` ";
		sql += " LEFT JOIN daily_config.`tbl_province` f ON a.province_id = f.id ";
		sql += " LEFT JOIN daily_config.`tbl_sdk_cp` g ON e.cp_id = g.cp_id ";
		sql += " WHERE a.mr_date >= '" + startDate + "' AND a.mr_date <= '" + endDate + "' ";
		sql += " AND a.sdk_channel_id > 0 ";
		
		sql += query;
		
		sql += " GROUP BY join_id ";
		sql += " )b ON a.join_id = b.join_id ";
		
		System.out.println(sql);
		
		new JdbcGameControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<SdkDataSummerModel> list = new ArrayList<SdkDataSummerModel>();
				while(rs.next())
				{
					SdkDataSummerModel model = new SdkDataSummerModel();
					
					model.setTitle(StringUtil.getString(rs.getString("show_title"), ""));
					model.setActivityRows(rs.getInt("user_rows"));
					model.setUserRows(rs.getInt("activity_rows"));
					model.setTroneRequestRows(rs.getInt("trone_request_rows"));
					model.setTroneEffectRequestRows(rs.getInt("trone_effect_request_rows"));
					model.setTroneOrderRows(rs.getInt("trone_order_rows"));
					model.setMsgRows(rs.getInt("msg_rows"));
					model.setSucRows(rs.getInt("suc_rows"));
					model.setAmount(rs.getFloat("amount"));
					
					list.add(model);
				}
				return list;
			}
		});
		
	}
	
	//sortType 1:天  2:周  3：月  4：CP 5:APP 6:channel 7:sp_trone_id 8:trone_id 9:province_id
	private String[] getShowType(int showType)
	{
		String joinId = " a.mr_date ";
		String queryParams = " a.mr_date ";
		switch(showType)
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
				joinId = " g.cp_id ";
				queryParams = " g.nick_name ";
				break;
			case 5:
				joinId = " e.sdk_app_id ";
				queryParams = " concat(e.name,'-',e.appkey) ";
				break;
			case 6:
				joinId = " d.sdk_channel_id ";
				queryParams = " d.channel_name ";
				break;
			case 7:
				joinId = " c.sp_trone_id ";
				queryParams = " c.name ";
				break;
			case 8:
				joinId = " b.trone_id ";
				queryParams = " b.name ";
				break;
			case 9:
				joinId = " f.id ";
				queryParams = " f.name ";
				break;
				
			default:
					break;
		}
		
		String[] result = {queryParams,joinId};
		return result;
	}
	
	public static void main(String[] args)
	{
		new SdkDataSummerDao().loadSdkDataSummer(0, 308, 0, 0, 0, "2016-07-02","2016-09-02",7);
	}
	
}
