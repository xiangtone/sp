package com.system.server;

import java.util.Map;

import com.system.dao.LvMrDao;
import com.system.util.StringUtil;

public class LvMrServer {
	public Map<String, Object> getLvMrTodayData(String date,int payType,int userId)
	{
		String tableName = StringUtil.getMonthFormat(date);
		return new LvMrDao().getLvMrTodayData(tableName, date, payType, userId);
	}
	
	public Map<String, Object> getLvMrDaysData(String startDate,String endDate,int payType,int userId)
	{
		String tableName = StringUtil.getMonthFormat(startDate);
		return new LvMrDao().getLvMrDaysData(tableName, startDate, endDate, payType, userId);
	}

}
