package com.system.model;

/**
 * 这个是用于存储中转数据
 * @author Administrator
 *
 */
public class CpBillingSptroneDetailModel
{
	private int cpBillingId;
	private int spTroneId;
	private String mrDate;
	private float amount;
	private float rate;
	
	public int getCpBillingId()
	{
		return cpBillingId;
	}
	public void setCpBillingId(int cpBillingId)
	{
		this.cpBillingId = cpBillingId;
	}
	public String getMrDate()
	{
		return mrDate;
	}
	public void setMrDate(String mrDate)
	{
		this.mrDate = mrDate;
	}
	public float getAmount()
	{
		return amount;
	}
	public void setAmount(float amount)
	{
		this.amount = amount;
	}
	public float getRate()
	{
		return rate;
	}
	public void setRate(float rate)
	{
		this.rate = rate;
	}
	public int getSpTroneId()
	{
		return spTroneId;
	}
	public void setSpTroneId(int spTroneId)
	{
		this.spTroneId = spTroneId;
	}
	
}
