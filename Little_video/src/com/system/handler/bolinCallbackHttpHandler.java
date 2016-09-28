
package com.system.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class bolinCallbackHttpHandler extends baseCallbackFilter
{

	@Override
	void OnRequest(HttpServletRequest request, HttpServletResponse response)
	{

		int price;
		String cpOrderId;
		try
		{
			price = Integer.parseInt(request.getParameter("price"));
		}
		catch (Exception ex)
		{
			write(response, "price error");
			return;
		}
		cpOrderId = request.getParameter("cpOrderId");

		if (super.ProceModel(cpOrderId, null, price))
			write(response, "ok");
		else
			write(response, "fail " + getErrMsg());
	}

}
