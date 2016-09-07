package com.system.model;

public class SpBillingModel
{
	private int id;
	private String startDate;
	private String endDate;
	private int spId;
	private String spName;
	private int jsType;
	private String jsName;
	private float taxRate;
	private float preBilling;
	private float actureBilling;
	private float amount;
	private int status;
	private String remark;
	private String payTime;
	private String createDate;
	private float reduceAmount;
	
	public float getReduceAmount()
	{
		return reduceAmount;
	}
	public void setReduceAmount(float reduceAmount)
	{
		this.reduceAmount = reduceAmount;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getStartDate()
	{
		return startDate;
	}
	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}
	public String getEndDate()
	{
		return endDate;
	}
	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}
	public int getSpId()
	{
		return spId;
	}
	public void setSpId(int spId)
	{
		this.spId = spId;
	}
	public String getSpName()
	{
		return spName;
	}
	public void setSpName(String spName)
	{
		this.spName = spName;
	}
	public int getJsType()
	{
		return jsType;
	}
	public void setJsType(int jsType)
	{
		this.jsType = jsType;
	}
	public String getJsName()
	{
		return jsName;
	}
	public void setJsName(String jsName)
	{
		this.jsName = jsName;
	}
	public float getTaxRate()
	{
		return taxRate;
	}
	public void setTaxRate(float taxRate)
	{
		this.taxRate = taxRate;
	}
	public float getPreBilling()
	{
		return preBilling;
	}
	public void setPreBilling(float preBilling)
	{
		this.preBilling = preBilling;
	}
	public float getActureBilling()
	{
		return actureBilling;
	}
	public void setActureBilling(float actureBilling)
	{
		this.actureBilling = actureBilling;
	}
	public float getAmount()
	{
		return amount;
	}
	public void setAmount(float amount)
	{
		this.amount = amount;
	}
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	public String getPayTime()
	{
		return payTime;
	}
	public void setPayTime(String payTime)
	{
		this.payTime = payTime;
	}
	public String getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(String createDate)
	{
		this.createDate = createDate;
	}
	
}
