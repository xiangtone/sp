package com.system.server;

import java.util.List;
import java.util.Map;

import com.system.dao.CpBillingDao;
import com.system.model.CpBillingModel;
import com.system.model.CpBillingSpTroneModel;
import com.system.model.CpBillingTroneOrderDetailModel;
import com.system.model.CpSpTroneRateModel;
import com.system.model.SettleAccountModel;

public class CpBillingServer
{
	/**
	 * 开始CP对帐,从原始的CP_MR表里面获取帐单数据到帐单表，数据一旦对帐和 付款完成，将对数据进行封存
	 * 	 
	 *  
	 * @param cpId
	 * @param jsType
	 * @param startDate
	 * @param endDate
	 */
	private void startExportCpBilling(int cpId,int jsType,String startDate,String endDate)
	{
		List<CpSpTroneRateModel> spTroneRateList = new CpSpTroneRateServer().loadCpSpTroneRateList(cpId, jsType, startDate, endDate);
		CpBillingDao dao = new CpBillingDao();
		List<CpBillingTroneOrderDetailModel> orderDetailList = dao.loadCpBillingTroneOrderDetailOri(cpId, jsType, startDate, endDate);
		//List<CpBillingSptroneDetailModel> spTroneDetailList = dao.loadCpBillingSpTroneDetailOri(cpId, jsType, startDate, endDate);
		List<CpBillingSpTroneModel> spTroneList = dao.loadCpBillingSpTroneOri(cpId, jsType, startDate, endDate);
		
		float preBilling = 0;
		
		float amount = 0;
		
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
		}
		
		for(CpBillingSpTroneModel model : spTroneList)
		{
			amount += model.getAmount();
			preBilling += model.getAmount()*model.getRate();
		}
		
		int billingId = dao.addCpBilling(cpId, jsType, startDate, endDate, preBilling,amount);
		
		dao.addCpBillingSpTroneData(spTroneList, billingId);
		
		dao.addCpBillingTroneOrderDetailData(orderDetailList, billingId);
	}
	
	public CpBillingModel getCpBillingModel(int id)
	{
		return new CpBillingDao().getCpBillingModel(id);
	}
	
	public Map<String, Object> loadCpBilling(String startDate, String endDate,
			int cpId,int jsType,int status,int pageIndex)
	{
		return new CpBillingDao().loadCpBilling(startDate, endDate, cpId,jsType,status,pageIndex);
	}
	
	public List<SettleAccountModel> exportExcelData(int cpBillingId)
	{
		return new CpBillingDao().exportExcelData(cpBillingId);
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
	
	public void updateCpBillingActurePay(int cpBillingId,float money)
	{
		new CpBillingDao().updateCpBillingActurePay(cpBillingId, money);
	}
	
	public void delCpBilling(int cpBillingId)
	{
		new CpBillingDao().delCpBilling(cpBillingId);
	}
	
	/**
	 * 更新指定帐单的状态
	 * @param cpBillingId
	 * @param status
	 */
	public void updateCpBillingStatus(int cpBillingId,int status)
	{
		new CpBillingDao().updateCpBillingStatus(cpBillingId, status);
	}
	
	
	/**
	 * 重新生成指定帐单的数据
	 * @param cpBillingId
	 */
	public void reExportCpBillint(int cpBillingId)
	{
		CpBillingDao dao = new CpBillingDao();
		CpBillingModel model = dao.getCpBillingModel(cpBillingId);
		dao.delCpBilling(cpBillingId);
		startExportCpBilling(model.getCpId(),model.getJsType(),model.getStartDate(),model.getEndDate());
	}
	
	/**
	 * 调整CP对应的业务数据后，重新进行计算信息费、核减费用、应结费用等
	 * @param cpBillingId
	 */
	public void updateCpBilling(int cpBillingId)
	{
		new CpBillingDao().updateCpBilling(cpBillingId);
	}
	
	public void updateCpBillingModel(int id, int type,int status,String date){
		new CpBillingDao().updateCpBillingModel(id,type,status,date);
	}
	
	public void updateCpBillingActurePay(int cpBillingId,float money,String date)
	{
		new CpBillingDao().updateCpBillingActurePay(cpBillingId, money,date);
	}
}
