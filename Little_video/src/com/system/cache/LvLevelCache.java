
package com.system.cache;

import java.util.List;

import com.system.model.LvLevelModel;

public class LvLevelCache
{
	private static List<LvLevelModel> _LvLevel;

	public static LvLevelModel getLvLevelByLevelId(int id)
	{
		if (_LvLevel == null)
			return null;

		for (LvLevelModel m : _LvLevel)
		{
			if (m.getLevel() == id)
				return m;
		}
		return null;
	}

	static void setCache(List<LvLevelModel> data)
	{
		_LvLevel = data;
	}

	public static List<LvLevelModel> getCache()
	{
		return _LvLevel;
	}

}
