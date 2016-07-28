package com.smxysu3.manager.impl;

import com.smxysu3.dao.MoDataDao;
import com.smxysu3.dao.jdbcimpl.MoDataDaoImpl;
import com.smxysu3.manager.MoDataManager;
import com.smxysu3.model.MoData;

public class MoDataManagerImpl implements MoDataManager {
	private static MoDataManagerImpl instance = new MoDataManagerImpl();
	
	
	private MoDataDao moDataDao = MoDataDaoImpl.getInstance();
	
	public MoDataDao getMoDataDao() {
		return moDataDao;
	}

	public void setMoDataDao(MoDataDao moDataDao) {
		this.moDataDao = moDataDao;
	}

	private MoDataManagerImpl(){}

	public void add(MoData moData,String tableName) {
		moDataDao.insert(moData, tableName);

	}

	public static MoDataManagerImpl getInstance() {
		return instance;
	}

	public static void setInstance(MoDataManagerImpl instance) {
		MoDataManagerImpl.instance = instance;
	}

	public int countByServerIdAndCpn(String serverId, String cpn,String tableName) {
		
		return moDataDao.countByGameAndCpn(serverId,cpn,tableName);
	}

}
