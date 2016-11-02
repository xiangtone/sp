<%@page import="com.system.model.CpBillingModel"%>
<%@page import="com.system.server.SpServer"%>
<%@page import="com.system.model.SpModel"%>
<%@page import="com.system.model.SpBillingModel"%>
<%@page import="com.system.server.SpBillingServer"%>
<%@page import="com.system.server.CpBillingServer"%>
<%@page import="com.system.util.Base64UTF"%>
<%@page import="com.system.model.SpBillExportModel" %>

<%@page import="com.system.model.SettleAccountModel" %>
<%@page import="java.util.List"%>
<%@page import="com.system.util.StringUtil"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.system.server.SettleAccountServer"%>



<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

	int id = StringUtil.getInteger(request.getParameter("spbillingid"),-1);
	int type = StringUtil.getInteger(request.getParameter("type"),-1);//0：获取上游确认帐单日期#结算申请开票日期#财务开票日期字符串
	int status=StringUtil.getInteger(request.getParameter("status"),-1);
	String date=StringUtil.getString(request.getParameter("date"),"");
	String startDateExp=StringUtil.getString(request.getParameter("start_date"), "");
	String endDateExp=StringUtil.getString(request.getParameter("end_date"),"");
	String jsTypeExp=StringUtil.getString(request.getParameter("js_types"), "");
	String statusExp=StringUtil.getString(request.getParameter("status_exp"), "");
	int load=StringUtil.getInteger(request.getParameter("load"), -1);
	int spIdExp=StringUtil.getInteger(request.getParameter("sp_id"), -1);
	
	int cpBillingId = StringUtil.getInteger(request.getParameter("cpbillingid"),-1);
	int cpBillingType = StringUtil.getInteger(request.getParameter("cpbilltype"),-1);//CP账单type
	int cpBillingStatus=StringUtil.getInteger(request.getParameter("cpbillstatus"),-1);//CP账单状态
	String cpdate=StringUtil.getString(request.getParameter("cpdate"),"");			  //CP账单时间


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
	if(load>=0)
	{
		SpBillingServer server = new SpBillingServer();
		List<SpBillExportModel> list = server.exportExcelData(startDateExp,endDateExp,spIdExp,jsTypeExp,statusExp);
		if(StringUtil.isNullOrEmpty(endDateExp)){
			endDateExp=StringUtil.getDefaultDate();
		}
		
		response.setContentType("application/octet-stream;charset=utf-8");
		String fileName ="";
		if(!StringUtil.isNullOrEmpty(startDateExp)){
		String fileStartDate=startDateExp.replace("-", "");
		fileName+=fileStartDate;
		}
		fileName+="-";
		if(!StringUtil.isNullOrEmpty(endDateExp)){
		String fileEndDate=endDateExp.replace("-", "");
		fileName+=fileEndDate;
		}
		fileName+="-";
		if(spIdExp>0){
			SpModel spModel=new SpServer().loadSpById(spIdExp);
			fileName+=spModel.getShortName();
		}else{
			fileName+="全部上游";
		}
		fileName+="-账单.xls";
		if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) 
		{
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} 
		else 
		{
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		}

		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

		server.exportSettleAccount(startDateExp, endDateExp, list, response.getOutputStream());
		
		out.clear();
		
		out = pageContext.pushBody();
		
		return;
	}
	//CP账单处理
	if(cpBillingType==0){
		CpBillingModel billingModel=new CpBillingServer().getCpBillingModel(cpBillingId);
		String data=billingModel.getStartBillDate()+"#"+billingModel.getGetBillDate()+"#"+billingModel.getApplyPayBillDate()+"#"+billingModel.getPayTime();
		out.clear();
		out.print(data); 
		}
	//CP账单更新状态和时间
		if(cpBillingType==1||cpBillingType==2||cpBillingType==3){    
			new CpBillingServer().updateCpBillingModel(cpBillingId, cpBillingType, cpBillingStatus, cpdate);
		}
%>
