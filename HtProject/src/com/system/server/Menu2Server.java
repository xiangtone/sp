package com.system.server;

import java.util.List;
import java.util.Map;

import com.system.cache.RightConfigCacheMgr;
import com.system.dao.Menu2Dao;
import com.system.model.Menu2Model;

public class Menu2Server
{
	public String getMenuGuildTitle(int id)
	{
		for(Menu2Model model : RightConfigCacheMgr.menu2ListCache)
		{
			if(model.getId()==id)
				return model.getMenuHeadName() + "->" + model.getMenu1Name() + "->" + model.getName();
		}
		return "";
	}
	
	public List<Menu2Model> loadMenu2List()
	{
		return new Menu2Dao().loadMenu2List();
	}
	
	public Map<String, Object> loadMenu2(int menuHeadId,int menu1Id,int pageIndex)
	{
		return new Menu2Dao().loadMenu2(menuHeadId, menu1Id, pageIndex);
	}
	
	public Menu2Model loadMenu2ById(int id)
	{
		return new Menu2Dao().loadMenu2ById(id);
	}
	
	public void addMenu2(Menu2Model model)
	{
		new Menu2Dao().addMenu2(model);
	}
	
	public void updateMenu2(Menu2Model model)
	{
		new Menu2Dao().updateMenu2(model);
	}
	
	public boolean updateMenu2(int id,int sort)
	{
		return new Menu2Dao().updateMenu2(id, sort);
	}
	
}
