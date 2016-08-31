package com.system.server;

import java.util.List;
import java.util.Map;

import com.system.dao.SpBillingDao;
import com.system.dao.SpTroneRateDao;
import com.system.model.SettleAccountModel;
import com.system.model.SpBillingModel;
import com.system.model.SpBillingSpTroneModel;
import com.system.model.SpTroneRateModel;

public class SpBillingServer
{
	public boolean exportSpBilling(final int spId,final int jsType,final String startDate,final String endDate)
	{
		SpBillingDao dao = new SpBillingDao();
		
		if(dao.isSpBillingCross(spId, jsType, startDate, endDate))
		{
			return false;
		}
		
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				startExportSpBilling(spId, jsType, startDate, endDate);
			}
		}).start();
		
		return true;
	}
	
	private void startExportSpBilling(int spId,int jsType,String startDate,String endDate)
	{
		List<SpTroneRateModel> spTroneRateList = new SpTroneRateDao().loadSpTroneRateList(spId, jsType, startDate, endDate);
		
		SpBillingDao dao = new SpBillingDao();
		
		List<SpBillingSpTroneModel> list = dao.loadSpBillingSpTroneOri(spId, jsType, startDate, endDate);
		
		float preBilling = 0;
		
		float amount = 0;
		
		for(SpTroneRateModel rateModel : spTroneRateList)
		{
			for(SpBillingSpTroneModel model : list)
			{
				if(model.getSpTroneId() == rateModel.getSpTroneId() 
						&& model.getStartDate().equalsIgnoreCase(rateModel.getStartDate())
						&& model.getEndDate().equalsIgnoreCase(rateModel.getEndDate()))
				{
					model.setRate(rateModel.getRate());
				}
			}
		}
		
		for(SpBillingSpTroneModel model : list)
		{
			amount += model.getAmount();
			preBilling += model.getAmount()*model.getRate();
		}
		
		int billingId = dao.addSpBilling(spId, jsType, startDate, endDate, preBilling,amount);
		
		dao.addSpBillingSpTroneData(list, billingId);
	}
	
	public List<SettleAccountModel> exportExcelData(int spBillingId)
	{
		return new SpBillingDao().exportExcelData(spBillingId);
	}
	
	public SpBillingModel getSpBillingModel(int id)
	{
		return new SpBillingDao().getSpBillingModel(id);
	}
	
	public Map<String, Object> loadSpBilling(String startDate, String endDate,
			int spId,int jsType,int status,int pageIndex)
	{
		return new SpBillingDao().loadSpBilling(startDate, endDate, spId,jsType,status,pageIndex);
	}
}
