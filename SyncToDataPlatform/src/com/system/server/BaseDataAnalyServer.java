package com.system.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.system.dao.BaseDataDao;
import com.system.model.SpModel;

public class BaseDataAnalyServer
{
	public void startAnalyBaseSpData()
	{
		BaseDataDao dao = new BaseDataDao();
		
		SpModel oriModel = null;
		SpModel descModel = null;
		
		List<SpModel> addList = new ArrayList<SpModel>();
		List<SpModel> updateList = new ArrayList<SpModel>();
		
		for(int i=1; i<=3; i++)
		{
			Map<Integer, SpModel> oriSource = dao.loadOriSpData(i);
			Map<Integer, SpModel> descSource = dao.loadDescSpData(i);
			
			addList.clear();
			
			for(int spId : oriSource.keySet())
			{
				oriModel = oriSource.get(spId);
				descModel = descSource.get(spId);
				
				if(descModel==null)
				{
					addList.add(oriModel);
				}
				else
				{
					if(!oriModel.equals(descModel))
					{
						updateList.add(oriModel);
					}
				}
			}
			
			if(!addList.isEmpty())
			{
				
			}
			
			if(!updateList.isEmpty())
			{
				
			}
		}
	}
}
