
package com.system.server;

import java.util.Map;

import com.system.dao.MrDao;
import com.system.util.StringUtil;

public class MrServer
{
	public Map<String, Object> getMrData(String startDate, String endDate,
			int spId,int spTroneId, int troneId, int cpId, int troneOrderId, int provinceId,
			int cityId,int operatorId,int dataType,int sortType)
	{
		return new MrDao().getMrAnalyData(startDate, endDate, spId, spTroneId,troneId,
				cpId, troneOrderId, provinceId, cityId,operatorId,dataType, sortType);
	}
	
	public Map<String, Object> getMrDataQiBa(String startDate, String endDate,
			int spId,int spTroneId, int troneId, int cpId, int troneOrderId, int provinceId,
			int cityId, int sortType)
	{
		return new MrDao().getMrAnalyDataQiBa(startDate, endDate, spId, spTroneId,troneId,
				cpId, troneOrderId, provinceId, cityId, sortType);
	}
	
	public Map<String, Object> getMrTodayData(String startDate,
			int spId, int spTroneId,int troneId, int cpId, int troneOrderId, int provinceId,
			int cityId, int sortType)
	{
		String tableName = StringUtil.getMonthFormat(startDate);
		return new MrDao().getMrTodayData(tableName, startDate, spId,spTroneId, troneId,
				cpId, troneOrderId, provinceId, cityId, sortType);
	}
	
	public Map<String, Object> getMrTodayDataQiBa(
			int spId, int spTroneId,int troneId, int cpId, int troneOrderId, int provinceId,
			int cityId, int sortType)
	{
		String tableName = StringUtil.getMonthFormat();
		String startDate = StringUtil.getDefaultDate();
		return new MrDao().getMrTodayDataQiBa(tableName, startDate, spId,spTroneId, troneId,
				cpId, troneOrderId, provinceId, cityId, sortType);
	}
	
	public Map<String,Object> getCpMrTodayShowData(int userId,int spTroneId)
	{
		String tableName = StringUtil.getMonthFormat();
		String startDate = StringUtil.getDefaultDate();
		return new MrDao().getCpMrTodayShowData(tableName, startDate, userId,spTroneId);
	}
	
	public Map<String,Object> getCpMrShowData(String startDate,String endDate,int userId,int spTroneId)
	{
		return new MrDao().getCpMrShowData(startDate, endDate, userId,spTroneId);
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
		new MrDao().updateMrRate(spTroneId, rate, startDate, endDate);
	}
	
	
	/**
	 * 更新CP MR汇总表里面的下游结算率
	 * @param cpId
	 * @param spTroneId
	 * @param rate
	 * @param startDate
	 * @param endDate
	 */
	public void updateCpMrRate(int cpId,int spTroneId,float rate,String startDate,String endDate)
	{
		new MrDao().updateCpMrRate(cpId,spTroneId, rate, startDate, endDate);
	}
}
