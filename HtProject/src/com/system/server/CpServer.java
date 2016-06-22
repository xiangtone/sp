package com.system.server;

import java.util.List;
import java.util.Map;

import com.system.dao.CpDao;
import com.system.model.CpModel;

public class CpServer {
	public List<CpModel> loadCp() {
		return new CpDao().loadCp();
	}

	public List<CpModel> loadCpQiBa() {
		return new CpDao().loadCpQiBa();
	}

	public Map<String, Object> loadCp(int pageIndex) {
		return new CpDao().loadCp(pageIndex);
	}

	public Map<String, Object> loadCp(int pageIndex, String keyWord) {
		return new CpDao().loadCp(pageIndex, keyWord);
	}

	public CpModel loadCpById(int id) {
		return new CpDao().loadCpById(id);
	}

	public List<CpModel> loadCpByIds(int[] ids) {
		return new CpDao().loadCpByIds(ids);
	}

	public List<CpModel> loadCpBySptone(int spTroneId) {
		return new CpDao().loadCpBySptone(spTroneId);
	}

	public boolean addCp(CpModel model) {
		return new CpDao().addCp(model);
	}

	public boolean updateCp(CpModel model) {
		return new CpDao().updateCp(model);
	}

	public boolean updateCpAccount(int cpId, int userId) {
		return new CpDao().updateCpAccount(cpId, userId);
	}

}
