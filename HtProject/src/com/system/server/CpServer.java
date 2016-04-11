package com.system.server;

import java.util.List;
import java.util.Map;

import com.system.dao.CpDao;
import com.system.model.CpModel;

public class CpServer
{
	public List<CpModel> loadCp()
	{
		return new CpDao().loadCp();
	}
	
	public List<CpModel> loadCpQiBa()
	{
		return new CpDao().loadCpQiBa();
	}
	
	public Map<String, Object> loadCp(int pageIndex)
	{
		return new CpDao().loadCp(pageIndex);
	}
	
	public Map<String, Object> loadCp(int pageIndex,String fullName,String shortName)
	{
		return new CpDao().loadCp(pageIndex, fullName, shortName);
	}
	
	public CpModel loadCpById(int id)
	{
		return new CpDao().loadCpById(id);
	}
	
	public boolean addCp(CpModel model)
	{
		return new CpDao().addCp(model);
	}
	
	public boolean updateCp(CpModel model)
	{
		return new CpDao().updateCp(model);
	}
	
	public boolean updateCpAccount(int cpId,int userId)
	{
		return new CpDao().updateCpAccount(cpId, userId);
	}
	
}
