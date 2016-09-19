package com.system.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.dao.SpBillingDao;
import com.system.dao.SpTroneRateDao;
import com.system.database.JdbcControl;
import com.system.database.QueryCallBack;
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

	/**
	 * 调整SP对应的业务数据后，重新进行计算信息费、核减费用、应结费用等
	 * @param cpBillingId
	 */
	public void updateSpBilling(int spBillingId)
	{
		String sql = "SELECT  SUM(amount) amount, ";
		sql += " SUM(amount*rate) pre_billing, ";
		sql += " SUM(CASE reduce_type WHEN 0 THEN reduce_amount*rate  WHEN 1 THEN reduce_amount END) reduce_amount ";
		sql += " FROM daily_log.`tbl_sp_billing_sp_trone` WHERE sp_billing_id = " + spBillingId + " AND STATUS = 0; ";

		
		JdbcControl control = new JdbcControl();
		float[] result = (float[])control.query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if(rs.next())
				{
					float[] result = {rs.getFloat("amount"),rs.getFloat("reduce_amount"),rs.getFloat("pre_billing")};
					return result;
				}
				return null;
			}
		});
		String sqlUpdate = "UPDATE daily_config.`tbl_sp_billing` SET amount = ?,reduce_amount = ?,pre_billing = ?  WHERE id = ? ";
		Map<Integer, Object> params = new HashMap<Integer, Object>();
		params.put(1, result[0]);
		params.put(2, result[1]);
		params.put(3, result[2]);
		params.put(4, spBillingId);
		control.execute(sqlUpdate, params);
	}
	
	public void reExportSpBillint(int spBillingId)
	{
		SpBillingDao dao = new SpBillingDao();
		SpBillingModel model = dao.getSpBillingModel(spBillingId);
		dao.delSpBilling(spBillingId);
		startExportSpBilling(model.getSpId(),model.getJsType(),model.getStartDate(),model.getEndDate());
	}
	
	public void delSpBilling(int spBillingId)
	{
		SpBillingDao dao = new SpBillingDao();
		dao.delSpBilling(spBillingId);
	}
	
	public void updateSpBillingStatus(int spBillingId,int status)
	{
		new SpBillingDao().updateSpBillingStatus(spBillingId, status);
	}
	
	public boolean recallSpBilling(int spBillingId)
	{
		return new SpBillingDao().recallSpBilling(spBillingId);
	}
	
	public void updateSpBillingActurePay(int spBillingId,float money)
	{
		new SpBillingDao().updateSpBillingActurePay(spBillingId, money);
	}
	/**
	 * SP商务只能查看自己的运营账单列表
	 * @param startDate
	 * @param endDate
	 * @param spId
	 * @param jsType
	 * @param status
	 * @param pageIndex
	 * @return
	 */
	public Map<String, Object> loadSpBilling(String startDate, String endDate,
			int spId,int jsType,int userId,int status,int pageIndex)
	{
		return new SpBillingDao().loadSpBilling(startDate, endDate, spId,jsType,userId,status,pageIndex);
	}
	
}
