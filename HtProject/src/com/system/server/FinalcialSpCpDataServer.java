package com.system.server;

import java.util.List;

import com.system.dao.FinalcialSpCpDataDao;
import com.system.vmodel.FinancialSpCpDataShowModel;

public class FinalcialSpCpDataServer
{
	public List<FinancialSpCpDataShowModel> loadData(String startDate,String endDate,int spId,int cpId,int dataType)
	{
		return new FinalcialSpCpDataDao().loadData(startDate, endDate,spId,cpId,dataType);
	}
}
