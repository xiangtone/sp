
package com.system.handler;

import java.util.List;

import com.system.api.AppInitRequestModel;
import com.system.api.AppInitResponseModel;
import com.system.api.BaseRequest;
import com.system.api.LevelItem;
import com.system.api.baseResponse;
import com.system.cache.LocateCache;
import com.system.cache.LvLevelCache;
import com.system.model.LvLevelModel;
import com.system.model.LvUserModel;
import com.system.model.ProvinceModel;
import com.system.server.LvUserServer;
import com.system.util.ImsiUtil;
import com.system.util.StringUtil;

public class AppInitHttpHandler extends BaseFilter
{

	@Override
	protected baseResponse ProcessReuqest(String s)
	{
		AppInitRequestModel m = BaseRequest.ParseJson(s,
				AppInitRequestModel.class);

		AppInitResponseModel result = new AppInitResponseModel();

		if (StringUtil.isNullOrEmpty(m.getImei()))
		{
			result.setStatus(com.system.constant.Constant.ERROR_MISS_PARAMETER);
			return result;
		}

		LvUserModel user = getUserInfo(m);

		result.setLevel(user.getLevel());
		result.setName(user.getName());
		result.setPassword(user.getPwd());

		List<LvLevelModel> levels = LvLevelCache.getCache();
		LevelItem[] lItems = new LevelItem[levels.size()];
		for (int i = 0; i < lItems.length; i++)
		{
			lItems[i] = new LevelItem(levels.get(i));
		}
		result.setLevels(lItems);

		return result;
	}

	private LvUserModel getUserInfo(AppInitRequestModel m)
	{
		LvUserServer s = new LvUserServer();
		LvUserModel u = s.getUserByImsi(m.getImei());
		if (u != null)
		{
			if (!StringUtil.isNullOrEmpty(m.getImsi())
					&& u.getImsi() != m.getImsi())
			{
				u.setImsi(m.getImsi());
				FillAreaInfo(u);
				s.UpdateImsi(u);
			}
			return u;
		}

		u = new LvUserModel();
		u.setAndroidLevel(m.getAndroidLevel());
		u.setAndroidVersion(m.getAndroidVersion());

		u.setImei(m.getImei());
		u.setImsi(m.getImsi());
		u.setLevel(0);
		u.setMac(m.getMac());
		u.setModel(m.getModel());

		FillRandName(u);
		FillAreaInfo(u);

		s.InsertUser(u);
		return u;
	}

	private void FillRandName(LvUserModel u)
	{
		long ts = System.currentTimeMillis();

		u.setName(Long.toString(100000 + (ts & 0xFFffFF)));
		u.setPwd(Long.toHexString((ts & 0xFFffFF) | 0x100000));

	}

	private void FillAreaInfo(LvUserModel u)
	{
		String phone = ImsiUtil.ImsiToPhone(u.getImsi());
		u.setCity(416);
		u.setProvince(32);

		if (StringUtil.isNullOrEmpty(phone))
			return;

		int city = LocateCache.getCityIdByPhone(phone);
		if(city==-1)
			return ;
		ProvinceModel provinceModel = LocateCache.getProvinceByCityId(city);
		u.setCity(city);
		System.out.println(phone);
		u.setProvince(provinceModel.getId());
	}

}
