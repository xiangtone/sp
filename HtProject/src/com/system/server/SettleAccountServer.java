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
import com.system.util.FileUtil;
import com.system.vmodel.SpFinanceShowModel;

public class SettleAccountServer
{
	public List<SettleAccountModel> loadSpSettleAccountList(int spId,String startDate,String endDate)
	{
		List<SettleAccountModel> list = new SettleAcountDao().loadSpSettleAccountData(spId, startDate, endDate);
		//List<SettleAccountModel> list = loadSpSettleAccountFromFile("c:/duizhangdan.txt");
		return list;
	}
	
	public List<SettleAccountModel> loadCpSettleAccountList(int cpId,String startDate,String endDate)
	{
		List<SettleAccountModel> list = new SettleAcountDao().loadCpSettleAccountData(cpId, startDate, endDate);
		//List<SettleAccountModel> list = loadSpSettleAccountFromFile("c:/duizhangdan.txt");
		return list;
	}
	
	public Map<String, List<SpFinanceShowModel>> loadSpSettleAccountData(String startDate,String endDate)
	{
		List<SpFinanceShowModel> list = new SettleAcountDao().loadSpSettleAccountData(startDate, endDate);
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
	
	
	public Map<String, List<SpFinanceShowModel>> loadCpSettleAccountData(String startDate,String endDate)
	{
		List<SpFinanceShowModel> list = new SettleAcountDao().loadCpSettleAccountData(startDate, endDate);
		
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
			case 1:
				dateTypeValue = "周结";
				break;
			case 2:
				dateTypeValue = "双周结";
				break;
			case 3:
				dateTypeValue = "月结";
				break;
			case 4:
				dateTypeValue = "自定义";
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
	
	@SuppressWarnings("unused")
	private List<SettleAccountModel> loadSpSettleAccountFromFile(String filePath)
	{
		String[] strs = null;
		List<SettleAccountModel> list = new ArrayList<SettleAccountModel>();
		for(String line : FileUtil.readFileToList(filePath, "GBK"))
		{
			strs = line.split("\t");
			SettleAccountModel model = new SettleAccountModel();
			
			model.setOperatorName(strs[0]);
			model.setSpTroneName(strs[1]);
			model.setAmount(Float.parseFloat(strs[2].replace(",", "")));
			model.setJiesuanlv(Float.parseFloat(strs[3]));
			
			list.add(model);
		}
		return list;
	}
	 
}
