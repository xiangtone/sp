package com.smxysu3.manager.impl;

import com.smxysu3.dao.SendInfoDao;
import com.smxysu3.dao.jdbcimpl.SendInfoDaoImpl;
import com.smxysu3.manager.SendInfoManager;
import com.smxysu3.model.SendInfo;

public class SendInfoManagerImpl implements SendInfoManager {
	
	private static SendInfoManagerImpl instance = new SendInfoManagerImpl();
	
	private SendInfoManagerImpl(){}
	
	private SendInfoDao sendInfoDao = SendInfoDaoImpl.getInstance();
	

	public SendInfo select(String company) {
		
		return sendInfoDao.selectByCompany(company);
	}


	public static  SendInfoManagerImpl getInstance() {
		return instance;
	}


	

}
