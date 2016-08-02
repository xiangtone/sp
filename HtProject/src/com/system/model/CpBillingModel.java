package com.system.model;

public class CpBillingModel
{
	private int id;
	private String startDate;
	private String endDate;
	private int cpId;
	private String cpName;
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
	public int getCpId()
	{
		return cpId;
	}
	public void setCpId(int cpId)
	{
		this.cpId = cpId;
	}
	public int getJsType()
	{
		return jsType;
	}
	public void setJsType(int jsType)
	{
		this.jsType = jsType;
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
	public String getCpName()
	{
		return cpName;
	}
	public void setCpName(String cpName)
	{
		this.cpName = cpName;
	}
	public String getJsName()
	{
		return jsName;
	}
	public void setJsName(String jsName)
	{
		this.jsName = jsName;
	}
	public String getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(String createDate)
	{
		this.createDate = createDate;
	}
	public float getAmount()
	{
		return amount;
	}
	public void setAmount(float amount)
	{
		this.amount = amount;
	}
	
	
	
}
