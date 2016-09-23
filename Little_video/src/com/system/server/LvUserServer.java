
package com.system.server;

import com.system.dao.LvUserDao;
import com.system.model.LvUserModel;

public class LvUserServer
{
	public LvUserModel getUserByImsi(String imei)
	{
		LvUserDao dao = new LvUserDao();
		return dao.getUserByImei(imei);
	}

	public void InsertUser(LvUserModel u)
	{
		new LvUserDao().Insert(u);
	}

	public void UpdateImsi(LvUserModel u)
	{
		new LvUserDao().UpdateImsi(u);
	}

	public void UpdateLevel(LvUserModel u)
	{
		new LvUserDao().UpdateLevel(u);
	}

}
