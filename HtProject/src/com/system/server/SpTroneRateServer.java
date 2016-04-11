package com.system.server;

import java.util.Map;

import com.system.dao.SpTroneRateDao;
import com.system.model.SpTroneRateModel;

public class SpTroneRateServer
{
	public Map<String,Object> loadSpTroneRate(String keyWord,int pageIndex)
	{
		return new SpTroneRateDao().loadSpTroneRate(keyWord,pageIndex);
	}
	
	public void addSpTroneRate(SpTroneRateModel model)
	{
		new SpTroneRateDao().addSpTroneRate(model);
		new MrServer().updateMrRate(model.getSpTroneId(), model.getRate(), model.getStartDate(), model.getEndDate());
	}
	
	public void updateSpTroneRate(SpTroneRateModel model)
	{
		SpTroneRateDao dao = new SpTroneRateDao();
		SpTroneRateModel oriModel = dao.loadSpTroneRateById(model.getId());
		dao.updateSpTroneRate(model);
		new MrServer().updateMrRate(oriModel.getSpTroneId(), model.getRate(), oriModel.getStartDate(), oriModel.getEndDate());
	}
	
	public void delSpTroneRate(SpTroneRateModel model)
	{
		new SpTroneRateDao().delSpTroneRate(model);
		new MrServer().updateMrRate(model.getSpTroneId(), model.getDefaultRate(), model.getStartDate(), model.getEndDate());
	}
	
	public boolean isRateDateCross(int spTroneId,String startDate,String endDate)
	{
		return new SpTroneRateDao().isRateDateCross(spTroneId, startDate, endDate);
	}
	
	public SpTroneRateModel loadSpTroneRateById(int id)
	{
		return new SpTroneRateDao().loadSpTroneRateById(id);
	}
}
