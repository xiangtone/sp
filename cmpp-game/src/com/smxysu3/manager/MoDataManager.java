package com.smxysu3.manager;

import com.smxysu3.model.MoData;

public interface MoDataManager {
	void add(MoData moData, String tableName);

	int countByServerIdAndCpn(String serverId, String cpn, String tableName);
}
