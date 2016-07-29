package com.system.server;

import java.util.List;
import java.util.Map;

import com.system.dao.CpBillingDao;
import com.system.model.CpBillingSpTroneModel;
import com.system.model.CpBillingTroneOrderDetailModel;
import com.system.model.CpSpTroneRateModel;

public class CpBillingServer
{
	/**
	 * 开始CP对帐
	 * 	 
	 *  
	 * @param cpId
	 * @param jsType
	 * @param startDate
	 * @param endDate
	 */
	private void startExportCpBilling(int cpId,int jsType,String startDate,String endDate)
	{
		// 1、获取指定参数内特殊结算率
		// 2、获取指定参数内的所有TroneOrder 详细内容
		// 3、获取指定参数内的所有SpTrone 详细内容
		// 4、匹配SpTrone参数下的对应结算率
		// 5、更新 SpTrone结算率，TroneOrder 结算率
		// 6、计算帐单的结算金额，更新三张表的数据
		
		List<CpSpTroneRateModel> spTroneRateList = new CpSpTroneRateServer().loadCpSpTroneRateList(cpId, jsType, startDate, endDate);
		CpBillingDao dao = new CpBillingDao();
		List<CpBillingTroneOrderDetailModel> orderDetailList = dao.loadCpBillingTroneOrderDetailOri(cpId, jsType, startDate, endDate);
		//List<CpBillingSptroneDetailModel> spTroneDetailList = dao.loadCpBillingSpTroneDetailOri(cpId, jsType, startDate, endDate);
		List<CpBillingSpTroneModel> spTroneList = dao.loadCpBillingSpTroneOri(cpId, jsType, startDate, endDate);
		
		//Date sDate,eDate,useDate = null;
		
		float preBilling = 0;
		
		//因为特殊结算率的数据还是比较少的，所以以这个开头来更新结算率，会减少很多循环
		for(CpSpTroneRateModel rateModel : spTroneRateList)
		{
			for(CpBillingSpTroneModel model : spTroneList)
			{
				if(model.getSpTroneId() == rateModel.getSpTroneId() 
						&& model.getStartDate().equalsIgnoreCase(rateModel.getStartDate())
						&& model.getEndDate().equalsIgnoreCase(rateModel.getEndDate()))
				{
					model.setRate(rateModel.getRate());
				}
			}
			
			/*
			sDate = StringUtil.getDateFromString(rateModel.getStartDate());
			eDate = StringUtil.getDateFromString(rateModel.getEndDate());
			
			if(sDate==null || eDate==null)
				continue;

			for(CpBillingSptroneDetailModel model : spTroneDetailList)
			{
				if(model.getSpTroneId() == rateModel.getSpTroneId())
				{
					useDate = StringUtil.getDateFromString(model.getMrDate());
					
					if(useDate==null)
						continue;
					
					if(useDate.compareTo(sDate)>=0 && useDate.compareTo(eDate)<=0)
					{
						model.setRate(rateModel.getRate());
					}
				}
				preBilling += model.getAmount()*model.getRate();
			}
			
			for(CpBillingTroneOrderDetailModel model : orderDetailList)
			{
				if(model.getSpTroneId() == rateModel.getSpTroneId())
				{
					useDate = StringUtil.getDateFromString(model.getMrDate());
					
					if(useDate==null)
						continue;
					
					if(useDate.compareTo(sDate)>=0 && useDate.compareTo(eDate)<=0)
					{
						model.setRate(rateModel.getRate());
					}
				}
			}
			
			*/
		}
		
		for(CpBillingSpTroneModel model : spTroneList)
		{
			preBilling += model.getAmount()*model.getRate();
		}
		
		int billingId = dao.addCpBilling(cpId, jsType, startDate, endDate, preBilling);
		
		dao.addCpBillingSpTroneData(spTroneList, billingId);
		
		dao.addCpBillingTroneOrderDetailData(orderDetailList, billingId);
	}
	
	public Map<String, Object> loadCpBilling(String startDate, String endDate,
			int cpId,int jsType,int pageIndex)
	{
		return new CpBillingDao().loadCpBilling(startDate, endDate, cpId, jsType, pageIndex);
	}
	
	public boolean exportCpBilling(final int cpId,final int jsType,final String startDate,final String endDate)
	{
		CpBillingDao dao = new CpBillingDao();
		
		if(dao.isCpBillingCross(cpId, jsType, startDate, endDate))
		{
			return false;
		}
		
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				startExportCpBilling(cpId, jsType, startDate, endDate);
			}
		}).start();
		
		return true;
	}
}
