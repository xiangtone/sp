package com.system.server;

import java.util.Map;

import com.system.dao.CpSpTroneRateDao;
import com.system.model.CpSpTroneRateModel;

public class CpSpTroneRateServer
{
	public Map<String, Object> loadCpSpTroneRate(String keyWord,int pageIndex)
	{
		return new CpSpTroneRateDao().loadCpSpTroneRate(keyWord, pageIndex);
	}
	
	public CpSpTroneRateModel loadCpSpTroneRateById(int id)
	{
		return new CpSpTroneRateDao().loadCpSpTroneRateById(id);
	}
	
	public void addCpSpTroneRate(CpSpTroneRateModel model)
	{
		new CpSpTroneRateDao().addCpSpTroneRate(model);
	}
	
	public void updateCpSpTroneRate(CpSpTroneRateModel model)
	{
		new CpSpTroneRateDao().updateCpSpTroneRate(model);
	}
	
	public void updateCpSpTroneLimit(CpSpTroneRateModel model)
	{
		new CpSpTroneRateDao().updateCpSpTroneLimit(model);
	}
	
	public void updateCpSpTroneRate(int id,float rate)
	{
		new CpSpTroneRateDao().updateCpSpTroneRate(id, rate);
	}
	
	public void delCpSpTroneRate(int id)
	{
		new CpSpTroneRateDao().delCpSpTroneRate(id);
	}
	
	public void syncUnAddCpSpTroneRate()
	{
		new CpSpTroneRateDao().syncUnAddCpSpTroneRate();
	}
}
