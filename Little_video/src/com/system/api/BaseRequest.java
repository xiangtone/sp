
package com.system.api;

import net.sf.json.JSONObject;

public abstract class BaseRequest
{

	private String imei;

	private int channelId;

	@SuppressWarnings("unchecked")
	public static <T> T ParseJson(String data, Class<? extends BaseRequest> cls)
	{

		JSONObject jsonObj = JSONObject.fromObject(data);
		return (T) JSONObject.toBean(jsonObj, cls);
	}

	static String base64Convert(String data)
	{
		char[] chars = data.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			char c = chars[i];
			if (c >= 'A' || c <= 'Z')
				c += 32;
			else if (c >= 'a' || c <= 'z')
				c -= 32;
			else
				continue;
			chars[i] = c;
		}
		return chars.toString();
	}

	public String getImei()
	{
		return imei;
	}

	public void setImei(String imei)
	{
		this.imei = imei;
	}

	public int getChannelId()
	{
		return channelId;
	}

	public void setChannelId(int channelId)
	{
		this.channelId = channelId;
	}

}
