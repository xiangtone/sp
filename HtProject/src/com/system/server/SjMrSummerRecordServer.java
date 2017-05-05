package com.system.server;

import java.util.Map;

import com.system.dao.SjMrSummerRecordDao;
import com.system.model.SjMrSummerRecordModel;

public class SjMrSummerRecordServer
{
	public Map<String, Object> loadSjMrSummerRecordData(String startMonth,String endMonth,SjMrSummerRecordModel params,int pageIndex)
	{
		return new SjMrSummerRecordDao().loadSjMrSummerRecordData(startMonth, endMonth, params, pageIndex);
	}
	
	public boolean addSjMrSummerRecord(SjMrSummerRecordModel model)
	{
		return new SjMrSummerRecordDao().addSjMrSummerRecord(model);
	}
	
	public boolean delSjMrSummerRecord(int id)
	{
		return new SjMrSummerRecordDao().delSjMrSummerRecord(id);
	}
	
	public SjMrSummerRecordModel getSjMrSummerRecord(int id)
	{
		return new SjMrSummerRecordDao().getSjMrSummerRecord(id);
	}
	
	public boolean isExistDataInRecord(int year,int month,int troneOrderId)
	{
		return new SjMrSummerRecordDao().isExistDataInRecord(year, month, troneOrderId);
	}
	
	public boolean isExistDataInSummer(int year,int month,int troneOrderId)
	{
		return new SjMrSummerRecordDao().isExistDataInSummer(year, month, troneOrderId);
	}
}
