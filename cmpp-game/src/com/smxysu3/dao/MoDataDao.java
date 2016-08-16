package com.smxysu3.dao;

import com.smxysu3.model.MoData;

public interface MoDataDao {

	void insert(MoData moData, String tableName);

	int countByGameAndCpn(String serverId, String cpn, String tableName);
}
