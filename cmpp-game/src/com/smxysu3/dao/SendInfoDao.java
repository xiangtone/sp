package com.smxysu3.dao;

import com.smxysu3.model.SendInfo;

public interface SendInfoDao {

	SendInfo selectByCompany(String company);
}
