package com.system.server;

import java.util.Map;

import com.system.dao.CommRightDao;
import com.system.dao.SpDao;
import com.system.model.CommRightModel;

public class CommRightServer {
	public Map<String, Object> loadCommRight(int pageIndex, String keyWord,int type) {
		return new CommRightDao().loadCommRight(pageIndex, keyWord,type);
	}
	public boolean addCommRight(CommRightModel model){
		return new CommRightDao().addCommRight(model);
	}
	public boolean updateCommRight(CommRightModel model){
		return new CommRightDao().updateCommRight(model);
			
	}
	public CommRightModel loadCommRightById(int id){
		return new CommRightDao().loadCommRightById(id);
	}
	public boolean deleteCommRight(int id){
		return new CommRightDao().deleteCommRight(id);
	}
}
