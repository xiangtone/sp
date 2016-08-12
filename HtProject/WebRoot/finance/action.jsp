<%@page import="com.system.server.CpBillingServer"%>
<%@page import="com.system.util.Base64UTF"%>
<%@page import="com.system.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

	int type = StringUtil.getInteger(request.getParameter("type"), 1);

	if(type==1)
	{
		int jsType = StringUtil.getInteger(request.getParameter("js_type"), -1);
		int cpId = StringUtil.getInteger(request.getParameter("cpid"), 0);
		String startDate = StringUtil.getString(request.getParameter("startdate"), "");
		String endDate = StringUtil.getString(request.getParameter("enddate"), "");
		
		if(jsType<0 || cpId == 0 || StringUtil.isNullOrEmpty(startDate) || StringUtil.isNullOrEmpty(endDate))
		{
			out.clear();
			out.print("NO");
			return;
		}
		
		out.print(new CpBillingServer().exportCpBilling(cpId, jsType, startDate, endDate) ? "OK" : "NO");
	}
	else if(type==2)
	{
		int cpBillingId = StringUtil.getInteger(request.getParameter("id"), -1);
		float actureBilling = StringUtil.getFloat(request.getParameter("money"), 0.0F);
		new CpBillingServer().updateCpBillingActurePay(cpBillingId, actureBilling);
		out.println("OK," + cpBillingId);
		return;
	}
	
%>
