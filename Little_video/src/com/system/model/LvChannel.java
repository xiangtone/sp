
package com.system.model;

import java.util.Date;

public class LvChannel
{
	int		id;
	String	appkey;
	String	channel;
	int		hold_percent;
	int		user_id;
	Date	create_date;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getAppkey()
	{
		return appkey;
	}
	public void setAppkey(String appkey)
	{
		this.appkey = appkey;
	}
	public String getChannel()
	{
		return channel;
	}
	public void setChannel(String channel)
	{
		this.channel = channel;
	}
	public int getHold_percent()
	{
		return hold_percent;
	}
	public void setHold_percent(int hold_percent)
	{
		this.hold_percent = hold_percent;
	}
	public int getUser_id()
	{
		return user_id;
	}
	public void setUser_id(int user_id)
	{
		this.user_id = user_id;
	}
	public Date getCreate_date()
	{
		return create_date;
	}
	public void setCreate_date(Date create_date)
	{
		this.create_date = create_date;
	}
}
