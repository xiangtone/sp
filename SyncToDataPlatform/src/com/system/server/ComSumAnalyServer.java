package com.system.server;

import java.util.List;

import com.system.dao.ComSumSummerDao;
import com.system.model.ComSumSummerModel;

public class ComSumAnalyServer
{
	/**
	 * 去分析指定公司指定日期的数据
	 * @param coId
	 * @param startDate
	 * @param endDate
	 */
	public void analyComSumData(int coId,String startDate,String endDate)
	{
		ComSumSummerDao dao = new ComSumSummerDao();
		dao.delDailyData(coId, startDate, endDate);
		List<ComSumSummerModel> list = dao.loadComSumSummerData(coId,startDate,endDate);
		
		String head = "INSERT INTO comsum_config.`tbl_data_summer`(co_id,trone_id,province_id,data_rows,amount,mr_date,record_type,cp_id) VALUES ";
		StringBuffer sbData = new StringBuffer(512);
		
		if(list!=null && !list.isEmpty())
		{
			ComSumSummerModel model = null;
			for(int i=0; i<list.size(); i++)
			{
				model = list.get(i);
				sbData.append("(" + coId + "," + model.getTroneId() + "," + model.getProvinceId() + "," + model.getDataRows() + "," + model.getAmount() + ",'" + model.getMrDate() + "'," + model.getRecordType() + "," + model.getCpId() + "),");
				if((i+1)%500==0)
				{
					sbData.deleteCharAt(sbData.length()-1);
					sbData.append(";");
					dao.addCpDailyData(head + sbData.toString());
					sbData = new StringBuffer(512);
				}
			}
			if(sbData.length()>0)
			{
				sbData.deleteCharAt(sbData.length()-1);
				sbData.append(";");
				dao.addCpDailyData(head + sbData.toString());
			}
		}
	}
	
	/**
	 * 开始分析各大公司的数据
	 */
	public void startAnalyComSumData()
	{
		
	}
	
	public static void main(String[] args)
	{
		ComSumAnalyServer server = new ComSumAnalyServer();
		server.analyComSumData(2, "2016-06-08", "2016-06-08");
	}
}
