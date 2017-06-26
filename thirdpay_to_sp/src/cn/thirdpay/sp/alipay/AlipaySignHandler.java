
package cn.thirdpay.sp.alipay;

import javax.servlet.annotation.WebFilter;

import cn.thirdpay.sp.apiModel.baseApiModel;
import cn.thirdpay.sp.baseHandler.baseFilter;

@WebFilter("/AlipaySign")
public class AlipaySignHandler extends baseFilter
{
	@Override
	protected baseApiModel BeginProcess()
	{
		  
	}
}
