package com.system.sdk.server;

import java.util.List;

import com.system.sdk.dao.SdkSpTroneDao;
import com.system.sdk.model.SdkSpTroneModel;

public class SdkSpTroneServer {
	public List<SdkSpTroneModel>loadSdkSpTrone(){
		return new SdkSpTroneDao().loadSdkSpTrone();
	}

}
