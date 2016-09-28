package com.smxysu3.manager.impl;

import com.smxysu3.dao.CompanyMtMsgDao;
import com.smxysu3.dao.jdbcimpl.CompanyMtMsgDaoImpl;
import com.smxysu3.manager.CompanyMtMsgManager;
import com.smxysu3.model.CompanyMtMsg;

public class CompanyMtMsgManagerImpl implements CompanyMtMsgManager {
	private static CompanyMtMsgManagerImpl instance = new CompanyMtMsgManagerImpl();

	private CompanyMtMsgManagerImpl() {
	}

	private CompanyMtMsgDao dao = CompanyMtMsgDaoImpl.getInstance();

	public CompanyMtMsg selectByCompanyAndServerId(String company, String serverId) {

		return dao.select(company, serverId);
	}

	public CompanyMtMsgDao getDao() {
		return dao;
	}

	public void setDao(CompanyMtMsgDao dao) {
		this.dao = dao;
	}

	public static CompanyMtMsgManagerImpl getInstance() {
		return instance;
	}

	public void add(CompanyMtMsg companyMtMsg) {
		dao.insert(companyMtMsg);

	}

}
