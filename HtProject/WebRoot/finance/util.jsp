<%@page import="com.system.model.SpBillingModel"%>
<%@page import="com.system.server.SpBillingServer"%>
<%@page import="com.system.server.CpBillingServer"%>
<%@page import="com.system.util.Base64UTF"%>
<%@page import="com.system.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

	int id = StringUtil.getInteger(request.getParameter("spbillingid"),-1);
	int type = StringUtil.getInteger(request.getParameter("type"),-1);//0：获取上游确认帐单日期#结算申请开票日期#财务开票日期字符串
	int status=StringUtil.getInteger(request.getParameter("status"),-1);
	String date=StringUtil.getString(request.getParameter("date"),"");

	if(type==0){
	SpBillingModel billingModel=new SpBillingServer().getSpBillingModel(id);
	String data=billingModel.getBillingDate()+"#"+billingModel.getApplyKaipiaoDate()+"#"+billingModel.getKaipiaoDate()+"#"+billingModel.getPayTime();
	out.clear();
	out.print(data); 
	}
	//更新状态和时间
	if(type==1||type==2||type==3){    
		new SpBillingServer().updateSpBillingModel(id, type, status, date);
	}
%>
