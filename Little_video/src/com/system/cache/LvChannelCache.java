
package com.system.cache;

import java.util.List;

import com.system.model.LvChannelModel;

public class LvChannelCache
{
	private static List<LvChannelModel> _lvChn;

	public static LvChannelModel getDataByChannelAndKey(String Channel,
			String appKey)
	{
		if (_lvChn == null)
			return null;

		for (LvChannelModel m : _lvChn)
		{
			if (m.getChannel().equalsIgnoreCase(Channel)
					&& m.getAppkey().equalsIgnoreCase(appKey))
				return m;
		}
		return null;
	}

	static void setCache(List<LvChannelModel> data)
	{
		_lvChn = data;
	}

	public static List<LvChannelModel> getCache()
	{
		return _lvChn;
	}

}
