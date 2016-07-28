package com.smxysu3.dao;

import com.smxysu3.model.Companys;

public interface CompanysDao {
	Companys selectByCompanytag(String companytag,String cpn);

}
