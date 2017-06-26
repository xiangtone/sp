
package cn.thirdpay.sp.baseHandler;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.thirdpay.sp.apiModel.baseApiModel;

/**
 * Servlet Filter implementation class baseFilter
 */
public abstract class baseFilter implements Filter
{

	protected HttpServletRequest	request;
	protected HttpServletResponse	response;
	
	int code;
	String errMesage;

	public void destroy()
	{
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{

		this.request = (HttpServletRequest) request;
		this.response = (HttpServletResponse) response;
		BeginProcess();
	}

	public void init(FilterConfig fConfig) throws ServletException
	{
	}

	protected abstract baseApiModel BeginProcess();

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public String getErrMesage()
	{
		return errMesage;
	}

	public void setErrMesage(String msg)
	{
		errMesage = msg;
	}

}
