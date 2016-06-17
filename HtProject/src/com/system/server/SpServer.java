package com.system.server;

import java.util.List;
import java.util.Map;

import com.system.dao.SpDao;
import com.system.model.SpModel;

public class SpServer
{
	public List<SpModel> loadSp()
	{
		return new SpDao().loadSp();
	}
	
	public List<SpModel> loadSpQiBa()
	{
		return new SpDao().loadSpQiBa();
	}
	
	public Map<String, Object> loadSp(int pageIndex)
	{
		return new SpDao().loadSp(pageIndex);
	}
	
	public Map<String, Object> loadSp(int pageIndex,String keyWord)
	{
		return new SpDao().loadSp(pageIndex, keyWord);
	}
	
	public SpModel loadSpById(int id)
	{
		return new SpDao().loadSpById(id);
	}
	
	public boolean addSp(SpModel model)
	{
		return new SpDao().addSp(model);
	}
	
	public boolean updateSp(SpModel model)
	{
		return new SpDao().updateSp(model);
	}
}
