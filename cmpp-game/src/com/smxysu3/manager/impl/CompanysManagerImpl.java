package com.smxysu3.manager.impl;

import com.smxysu3.dao.CompanysDao;
import com.smxysu3.dao.jdbcimpl.CompanysDaoImpl;
import com.smxysu3.manager.CompanysManager;
import com.smxysu3.model.Companys;

public class CompanysManagerImpl implements CompanysManager {

	// µ¥ÀýÄ£Ê½
	private static CompanysManagerImpl instance = new CompanysManagerImpl();

	private CompanysManagerImpl() {
	}

	private CompanysDao companysDao = CompanysDaoImpl.getInstance();

	public Companys select(String companytag, String cpn) {
		return companysDao.selectByCompanytag(companytag, cpn);
	}

	public CompanysDao getCompanysDao() {
		return companysDao;
	}

	public void setCompanysDao(CompanysDao companysDao) {
		this.companysDao = companysDao;
	}

	public static CompanysManagerImpl getInstance() {
		return instance;
	}

	public static void setInstance(CompanysManagerImpl instance) {
		CompanysManagerImpl.instance = instance;
	}

}
