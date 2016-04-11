package com.system.server;

import java.util.List;
import java.util.Map;

import com.system.dao.SpTroneDao;
import com.system.model.SpTroneModel;

public class SpTroneServer
{
	public Map<String, Object> loadSpTroneList(int pageIndex,int spId,int userId,String spTroneName)
	{
		return new SpTroneDao().loadSpTroneList(pageIndex,spId,userId,spTroneName);
	}
	
	public void addSpTrone(SpTroneModel model)
	{
		new SpTroneDao().addSpTrone(model);
	}
	
	public void updateSpTrone(SpTroneModel model)
	{
		new SpTroneDao().updateSpTroneModel(model);
	}
	
	public SpTroneModel loadSpTroneById(int id)
	{
		return new SpTroneDao().loadSpTroneById(id);
	}
	
	public void delSpTrone(int id)
	{
		new SpTroneDao().delSpTrone(id);
	}
	
	public List<SpTroneModel> loadSpTroneList()
	{
		return new SpTroneDao().loadSpTroneList();
	}
	
	public List<SpTroneModel> loadSpTroneList(int spId)
	{
		return new SpTroneDao().loadSpTroneList(spId);
	}
	
	public List<SpTroneModel> loadCpTroneList(int userId)
	{
		return new SpTroneDao().loadCpTroneList(userId);
	}
	
	public List<SpTroneModel> loadTroneListByCpid(int cpId)
	{
		List<SpTroneModel> list = new SpTroneDao().loadTroneListByCpid(cpId);
		
		return list;
	}
	
	public boolean updateSpTroneRate(int id,float rate)
	{
		return new SpTroneDao().updateSpTroneRate(id, rate);
	}
}	
