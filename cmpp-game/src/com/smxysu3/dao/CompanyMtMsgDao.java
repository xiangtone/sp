package com.smxysu3.dao;

import com.smxysu3.model.CompanyMtMsg;

public interface CompanyMtMsgDao {

	CompanyMtMsg select(String company,String serverId);
	void insert(CompanyMtMsg companyMtMsg);
}
