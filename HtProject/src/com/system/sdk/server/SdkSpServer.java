package com.system.sdk.server;

import java.util.List;

import com.system.sdk.dao.SdkSpDao;
import com.system.sdk.model.SdkSpModel;

public class SdkSpServer {
	public List<SdkSpModel>loadSdkSp(){
		return new SdkSpDao().loadSdkSp();
		
	}

}
