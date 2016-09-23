
package com.system.api;

/**
 * 订单请求信息
 * 
 * @author Shotgun
 */
public class AppOrderRequestModel extends BaseRequest
{

	String	orderId;
	int		levelId;
	short	payType;

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

	public short getPayType()
	{
		return payType;
	}

	public void setPayType(short payType)
	{
		this.payType = payType;
	}

}
