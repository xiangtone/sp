package com.system.server;

import java.util.List;
import java.util.Map;

import com.system.dao.TroneDao;
import com.system.model.TroneModel;

public class TroneServer
{
	public List<TroneModel> loadTroneList()
	{
		return new TroneDao().loadTrone();
	}
	
	public List<TroneModel> loadTrone(int spTroneId)
	{
		return new TroneDao().loadTrone(spTroneId);
	}
	
	public Map<String, Object> loadTrone(int spId,int pageIndex)
	{
		return new TroneDao().loadTrone(spId, pageIndex);
	}
	
	public Map<String, Object> loadTrone(int spId,int pageIndex,int spTroneId,String orders,String troneNum,String troneName)
	{
		return new TroneDao().loadTrone(spId, pageIndex, spTroneId, orders, troneNum, troneName);
	}
	
	public List<TroneModel> loadSpTrone()
	{
		return new TroneDao().loadSpTrone();
	}
	
	public TroneModel getTroneById(int id)
	{
		return new TroneDao().getTroneById(id);
	}
	
	public void addTrone(TroneModel model)
	{
		new TroneDao().addTrone(model);
	}
	
	public int insertTrone(TroneModel model)
	{
		return new TroneDao().insertTrone(model);
	}
	
	public void updateTrone(TroneModel model)
	{
		new TroneDao().updateTrone(model);
	}
	
	public void deleteTrone(int delId)
	{
		new TroneDao().deleteTrone(delId);
	}
}
