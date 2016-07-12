package com.system.server;

import java.util.List;

import com.system.dao.BaseDataShowDao;
import com.system.model.BaseDataShowModel;

public class BaseDataShowServer
{
	public List<BaseDataShowModel> loadShowData(String startDate,String endDate,int coId,int spId,int cpId,int showType)
	{
		List<BaseDataShowModel> list = new BaseDataShowDao().loadShowData(startDate, endDate, coId, spId, cpId, showType);

		return list;
	}
}
