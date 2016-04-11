<%@page import="com.system.server.analy.MrSummerRecordServer"%>
<%@page import="com.system.model.analy.MrSummerRecordModel"%>
<%@page import="com.system.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int type = StringUtil.getInteger(request.getParameter("type"), -1);
	//是否存在相同数据
	if(type==1)
	{
		String feeDate = StringUtil.getString(request.getParameter("fee_date"), "");
		
		int troneOrderId = StringUtil.getInteger(request.getParameter("trone_order_id"), -1);
		
		if(StringUtil.isNullOrEmpty(feeDate)||troneOrderId<0)
			return;
		
		MrSummerRecordModel model = new MrSummerRecordModel();
		
		model.setFeeDate(feeDate);
		
		model.setTroneOrderId(troneOrderId);
		
		boolean result = new MrSummerRecordServer().existMrSummerRecord(model);
		
		out.print(result);
	}
	//删除
	else if(type==2)
	{
		String query =  request.getQueryString();
		
		int mrSummerId = StringUtil.getInteger(request.getParameter("mrsummerid"), -1);
		int cpMrSummerId = StringUtil.getInteger(request.getParameter("cpmrsummerid"),-1);
		
		if(mrSummerId>0 && cpMrSummerId>0)
		{
			new MrSummerRecordServer().deleteMrSummerRecordModel(mrSummerId, cpMrSummerId);
		}
		
		response.sendRedirect("mrrecord.jsp?" + query);
		return;
	}
	//增加
	else if(type==3)
	{
		int spId = StringUtil.getInteger(request.getParameter("sp_id"), -1);
		int cpId = StringUtil.getInteger(request.getParameter("cp_id"), -1);
		int troneId = StringUtil.getInteger(request.getParameter("trone_id"), -1);
		int troneOrderId = StringUtil.getInteger(request.getParameter("trone_order_id"), -1);
		String feeDate = StringUtil.getString(request.getParameter("fee_date"), "");
		int dataRows = StringUtil.getInteger(request.getParameter("data_rows"), 0);
		int showDataRows = StringUtil.getInteger(request.getParameter("show_data_rows"), -1);
		float amount = StringUtil.getFloat(request.getParameter("amount"), 0);
		float showAmount =  StringUtil.getFloat(request.getParameter("show_amount"), 0);

		MrSummerRecordModel model = new MrSummerRecordModel();
		
		model.setSpId(spId);
		model.setCpId(cpId);
		model.setTroneId(troneId);
		model.setTroneOrderId(troneOrderId);
		model.setFeeDate(feeDate);
		model.setDataRows(dataRows);
		model.setShowDataRows(showDataRows);
		model.setAmount(amount);
		model.setShowAmount(showAmount);
		
		new MrSummerRecordServer().addMrSummerRecordModel(model);
		
		out.print(true);
	}
	//修改
	else if(type==4)
	{
		int dataRows = StringUtil.getInteger(request.getParameter("data_rows"), 0);
		int showDataRows = StringUtil.getInteger(request.getParameter("show_data_rows"), -1);
		float amount = StringUtil.getFloat(request.getParameter("amount"), 0);
		float showAmount =  StringUtil.getFloat(request.getParameter("show_amount"), 0);
		int mrSummerId = StringUtil.getInteger(request.getParameter("mrsummerid"), -1);
		int cpMrSummerId = StringUtil.getInteger(request.getParameter("cpmrsummerid"), -1);
		
		MrSummerRecordModel model = new MrSummerRecordModel();
		
		model.setDataRows(dataRows);
		model.setShowDataRows(showDataRows);
		model.setAmount(amount);
		model.setShowAmount(showAmount);
		model.setMrSummerId(mrSummerId);
		model.setCpMrSummerId(cpMrSummerId);
		
		if(mrSummerId>0 && cpMrSummerId>0)
		{
			new MrSummerRecordServer().updateMrSummerRecordModel(model);
			
			out.print(true);
			
			return;
		}
		
		out.print(false);
	}
%>