package com.system.model;

public class SpTroneModel
{
	private int id;
	private int spId;
	private String spName;
	private String commerceUserName;
	private String spTroneName;
	private int operator;
	private String operatorName;
	private float jieSuanLv;
	private String provinces;
	private int troneApiId;
	private String troneApiName;
	private int troneType;
	private int status;
	
	private float dayLimit;
	private float monthLimit;
	private float userDayLimit;
	private float userMonthLimit;
	
	public int getTroneType()
	{
		return troneType;
	}
	public void setTroneType(int troneType)
	{
		this.troneType = troneType;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
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
	public String getSpTroneName()
	{
		return spTroneName;
	}
	public void setSpTroneName(String spTroneName)
	{
		this.spTroneName = spTroneName;
	}
	public int getOperator()
	{
		return operator;
	}
	public void setOperator(int operator)
	{
		this.operator = operator;
	}
	public String getOperatorName()
	{
		return operatorName;
	}
	public void setOperatorName(String operatorName)
	{
		this.operatorName = operatorName;
	}
	public float getJieSuanLv()
	{
		return jieSuanLv;
	}
	public void setJieSuanLv(float jieSuanLv)
	{
		this.jieSuanLv = jieSuanLv;
	}
	public String getProvinces()
	{
		return provinces;
	}
	public void setProvinces(String provinces)
	{
		this.provinces = provinces;
	}
	public int getTroneApiId()
	{
		return troneApiId;
	}
	public void setTroneApiId(int troneApiId)
	{
		this.troneApiId = troneApiId;
	}
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	public String getTroneApiName()
	{
		return troneApiName;
	}
	public void setTroneApiName(String troneApiName)
	{
		this.troneApiName = troneApiName;
	}
	public String getCommerceUserName()
	{
		return commerceUserName;
	}
	public void setCommerceUserName(String commerceUserName)
	{
		this.commerceUserName = commerceUserName;
	}
	public float getDayLimit()
	{
		return dayLimit;
	}
	public void setDayLimit(float dayLimit)
	{
		this.dayLimit = dayLimit;
	}
	public float getMonthLimit()
	{
		return monthLimit;
	}
	public void setMonthLimit(float monthLimit)
	{
		this.monthLimit = monthLimit;
	}
	public float getUserDayLimit()
	{
		return userDayLimit;
	}
	public void setUserDayLimit(float userDayLimit)
	{
		this.userDayLimit = userDayLimit;
	}
	public float getUserMonthLimit()
	{
		return userMonthLimit;
	}
	public void setUserMonthLimit(float userMonthLimit)
	{
		this.userMonthLimit = userMonthLimit;
	}
}
