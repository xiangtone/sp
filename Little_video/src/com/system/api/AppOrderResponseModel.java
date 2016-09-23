
package com.system.api;

public class AppOrderResponseModel extends baseResponse
{
	String	orderId;
	int		levelId;
	String	levelName;
	int		price;
	/**
	 * 0 未支付，1支付成功
	 */
	int		isPay;
	long	createDate;
	int		payDate;

	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	public int getLevelId()
	{
		return levelId;
	}

	public void setLevelId(int levelId)
	{
		this.levelId = levelId;
	}

	public String getLevelName()
	{
		return levelName;
	}

	public void setLevelName(String levelName)
	{
		this.levelName = levelName;
	}

	public int getPrice()
	{
		return price;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}

	public int getIsPay()
	{
		return isPay;
	}

	public void setIsPay(int isPay)
	{
		this.isPay = isPay;
	}

	public long getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(long createDate)
	{
		this.createDate = createDate;
	}

	public int getPayDate()
	{
		return payDate;
	}

	public void setPayDate(int payDate)
	{
		this.payDate = payDate;
	}
}
