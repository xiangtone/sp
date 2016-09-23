
package com.system.model;

import java.util.Date;

public class LvMrModel
{
	int		id;
	String	orderId;
	int		price;
	Date	createDate;
	int		channelId;
	int		levelId;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getOrderId()
	{
		return orderId;
	}
	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}
	public int getPrice()
	{
		return price;
	}
	public void setPrice(int price)
	{
		this.price = price;
	}
	public Date getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}
	public int getChannelId()
	{
		return channelId;
	}
	public void setChannelId(int channelId)
	{
		this.channelId = channelId;
	}
	public int getLevelId()
	{
		return levelId;
	}
	public void setLevelId(int levelId)
	{
		this.levelId = levelId;
	}

}
