package com.system.server;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.dao.SettleAcountDao;
import com.system.excel.ExcelManager;
import com.system.model.SettleAccountModel;
import com.system.util.ConfigManager;
import com.system.vmodel.SpFinanceShowModel;

public class SettleAccountServer
{
	public List<SettleAccountModel> loadSpSettleAccountList(int spId,String startDate,String endDate,int dateType)
	{
		List<SettleAccountModel> list = new SettleAcountDao().loadSpSettleAccountData(spId, startDate, endDate,dateType);
		//List<SettleAccountModel> list = loadSpSettleAccountFromFile("c:/duizhangdan.txt");
		return list;
	}
	
	public List<SettleAccountModel> loadCpSettleAccountList(int cpId,String startDate,String endDate,int dateType)
	{
		List<SettleAccountModel> list = new SettleAcountDao().loadCpSettleAccountData(cpId, startDate, endDate,dateType);
		//List<SettleAccountModel> list = loadSpSettleAccountFromFile("c:/duizhangdan.txt");
		return list;
	}
	
	public Map<String, List<SpFinanceShowModel>> loadSpSettleAccountData(String startDate,String endDate,int spId,int jsType)
	{
		List<SpFinanceShowModel> list = new SettleAcountDao().loadSpSettleAccountData(startDate, endDate,spId,jsType);
		Map<String, List<SpFinanceShowModel>> map = new HashMap<String, List<SpFinanceShowModel>>();
		
		List<SpFinanceShowModel> tmpList = null;
		
		for(SpFinanceShowModel model : list)
		{
			if(map.containsKey(model.getShortName()))
			{
				tmpList = map.get(model.getShortName());
			}
			else
			{
				tmpList = new ArrayList<SpFinanceShowModel>();
				map.put(model.getShortName(), tmpList);
			}
			tmpList.add(model);
		}
		return map;
	}
	
	
	public Map<String, List<SpFinanceShowModel>> loadCpSettleAccountData(String startDate,String endDate,int cpId,int jsType)
	{
		List<SpFinanceShowModel> list = new SettleAcountDao().loadCpSettleAccountData(startDate, endDate,cpId,jsType);
		
		Map<String, List<SpFinanceShowModel>> map = new HashMap<String, List<SpFinanceShowModel>>();
		
		List<SpFinanceShowModel> tmpList = null;
		
		for(SpFinanceShowModel model : list)
		{
			if(map.containsKey(model.getShortName()))
			{
				tmpList = map.get(model.getShortName());
			}
			else
			{
				tmpList = new ArrayList<SpFinanceShowModel>();
				map.put(model.getShortName(), tmpList);
			}
			tmpList.add(model);
		}
		return map;
	}
	
	public Map<String, List<SpFinanceShowModel>> loadCpSettleAccountDataAll(String startDate,String endDate,int cpId,int jsType)
	{
		List<SpFinanceShowModel> list = new SettleAcountDao().loadCpSettleAccountDataAll(startDate, endDate,cpId,jsType);
		
		Map<String, List<SpFinanceShowModel>> map = new HashMap<String, List<SpFinanceShowModel>>();
		
		List<SpFinanceShowModel> tmpList = null;
		
		for(SpFinanceShowModel model : list)
		{
			if(map.containsKey(model.getShortName()))
			{
				tmpList = map.get(model.getShortName());
			}
			else
			{
				tmpList = new ArrayList<SpFinanceShowModel>();
				map.put(model.getShortName(), tmpList);
			}
			tmpList.add(model);
		}
		return map;
	}
	
	public Map<String, List<SpFinanceShowModel>> loadSpSettleAccountDataAll(String startDate,String endDate,int spId,int jsType)
	{
		List<SpFinanceShowModel> list = new SettleAcountDao().loadSpSettleAccountDataAll(startDate, endDate,spId,jsType);
		
		Map<String, List<SpFinanceShowModel>> map = new HashMap<String, List<SpFinanceShowModel>>();
		
		List<SpFinanceShowModel> tmpList = null;
		
		for(SpFinanceShowModel model : list)
		{
			if(map.containsKey(model.getShortName()))
			{
				tmpList = map.get(model.getShortName());
			}
			else
			{
				tmpList = new ArrayList<SpFinanceShowModel>();
				map.put(model.getShortName(), tmpList);
			}
			tmpList.add(model);
		}
		return map;
	}
	
	//channelType 1 SP 2 CP
	public void exportSettleAccount(int channelType,int dateType,String channelName,String startDate,String endDate,List<SettleAccountModel> list,OutputStream os)
	{
		String dateTypeValue = "";
		
		switch(dateType)
		{
			case 0:
				dateTypeValue = "对公周结";
				break;
			case 1:
				dateTypeValue = "对公双周结";
				break;
			case 2:
				dateTypeValue = "对公N+1结";
				break;
			case 3:
				dateTypeValue = "对私周结";
				break;
			case 4:
				dateTypeValue = "对私双周结";
				break;
			case 5:
				dateTypeValue = "对私月结";
				break;
			case 6:
				dateTypeValue = "见帐单结";
				break;
			case 7:
				dateTypeValue = "对公N+2结";
				break;
			default:
				break;
		}
		
		String date = getDateFormat(startDate,endDate);
		
		String filePath = ConfigManager.getConfigData("EXCEL_DEMO") + (channelType==1 ? "SpDemo.xls" : "CpDemo.xls");
		new ExcelManager().writeSettleAccountToExcel(dateTypeValue, date, channelName, list, filePath, os);
	}
	
	private String getDateFormat(String startDate,String endDate)
	{
		String date = startDate + ":" + endDate;
		try
		{
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
			return sdf2.format(sdf1.parse(startDate)) + "-" + sdf2.format(sdf1.parse(endDate));
		}
		catch(Exception ex)
		{
			
		}
		return date;
	}
	
	 
}
