package com.system.server;

import java.util.List;
import java.util.Map;

import com.system.dao.SpTroneDao;
import com.system.model.SpTroneModel;
import com.system.util.ConfigManager;
import com.system.util.StringUtil;

public class SpTroneServer
{
	public Map<String, Object> loadSpTroneList(int pageIndex,int spId,int userId,String spTroneName)
	{
		return new SpTroneDao().loadSpTroneList(pageIndex,spId,userId,spTroneName);
	}
	
	//废弃
	public Map<String, Object> loadSpTroneList(int pageIndex,String keyWord)
	{
		int isUnHoldData = -1;
		
		if("导量".equalsIgnoreCase(keyWord))
		{
			isUnHoldData = 1;
		}
		else if("非导量".equalsIgnoreCase(keyWord))
		{
			isUnHoldData = 0;
		}
		
		return new SpTroneDao().loadOriSpTroneList(pageIndex,keyWord,isUnHoldData);
	}
	
	public Map<String, Object> loadSpTroneList2(int pageIndex,String keyWord,int isUnHoldData)
	{
		return new SpTroneDao().loadOriSpTroneList(pageIndex,keyWord,isUnHoldData);
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
	public int checkAdd(int userId){
		int commerceId = StringUtil.getInteger(ConfigManager.getConfigData("SP_COMMERCE_GROUP_ID"),-1);
		int count=new SpTroneDao().checkAdd(userId,commerceId);
		if(count>=1){
			return 1;//在商务组里
		}else{
			return 0;
		}
	}
	public Map<String, Object> loadSpTroneList(int pageIndex,String keyWord,int userId)
	{
		return new SpTroneDao().loadSpTroneList(pageIndex,keyWord,userId);
	}
}	
