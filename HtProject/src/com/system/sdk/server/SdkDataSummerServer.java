package com.system.sdk.server;

import java.util.List;

import com.system.sdk.dao.SdkDataSummerDao;
import com.system.sdk.model.SdkDataSummerModel;

public class SdkDataSummerServer
{
	public List<SdkDataSummerModel>loadSdkDataSummerModel(int cpId,int channelId,int appId,int troneId,int spTroneId,String startDate,String endDate,int showType){
		return new SdkDataSummerDao().loadSdkDataSummer(cpId, channelId, appId, troneId, spTroneId, startDate, endDate, showType);
	}
	
}
