package com.smxysu3.manager;

import com.smxysu3.model.CompanyMtMsg;

public interface CompanyMtMsgManager {
	CompanyMtMsg selectByCompanyAndServerId(String company,String serverId );
	void add(CompanyMtMsg companyMtMsg);

}
