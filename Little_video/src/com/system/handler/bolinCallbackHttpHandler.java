
package com.system.handler;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.system.api.bolincallModel;

import net.sf.json.JSONObject;

public class bolinCallbackHttpHandler extends baseCallbackFilter
{

	@Override
	void OnRequest(HttpServletRequest request, HttpServletResponse response)
	{
		int c = request.getContentLength();

		String data;
		if (c < 0)
		{
			write(response, "error no data!");
			return;
		}

		ServletInputStream stm;
		byte[] bin = new byte[c];
		try
		{
			stm = request.getInputStream();
			stm.read(bin);
			stm.close();
		}
		catch (IOException e)
		{
		}

		data = new String(bin);

		JSONObject jsonObj = JSONObject.fromObject(data);
		bolincallModel bl = (bolincallModel) JSONObject.toBean(jsonObj,
				bolincallModel.class);

		int price = bl.getPrice();
		String cpOrderId;
		cpOrderId = bl.getCpOrderId();

		if (super.ProceModel(cpOrderId, null, price))
			write(response, "ok");
		else
			write(response, "fail " + getErrMsg());
	}

}
