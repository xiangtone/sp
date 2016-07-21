package com.system.server;

import java.util.List;
import java.util.Map;

import com.system.dao.BlackDao;
import com.system.model.BlackModel;

public class BlackServer {
	public Map<String, Object> loadBlack(int pageIndex,String keyWord) {
		return new BlackDao().loadBlack(pageIndex, keyWord);
	}
	
	public List<BlackModel> loadBlack(){
		return new BlackDao().loadBlack();
	}
	public boolean updateBlack(BlackModel model){
		return new BlackDao().updateBlack(model);
	}
	public boolean addBlack(BlackModel model){
		return new BlackDao().addBlack(model);
	}
	public BlackModel loadBlackById(int id){
		return new BlackDao().loadBlackById(id);
	}
	/**
	 * 
	 * @param model
	 * @param cyType
	 * @return
	 */
	public void addBlack(BlackModel model,int cyType){
	      new BlackDao().addBlack(model,cyType);
		
	}
	public boolean delete(int id){
		return new BlackDao().delete(id);
	}
}
