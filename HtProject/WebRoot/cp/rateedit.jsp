<%@page import="com.system.model.CpSpTroneRateModel"%>
<%@page import="com.system.server.CpSpTroneRateServer"%>
<%@page import="java.util.List"%>
<%@page import="com.system.server.CpServer"%>
<%@page import="com.system.model.CpModel"%>
<%@page import="com.system.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int id = StringUtil.getInteger(request.getParameter("id"), -1);
	String query = StringUtil.getString(request.getParameter("query"), "");
	CpSpTroneRateModel model =new CpSpTroneRateServer().loadCpSpTroneRateById(id);
	if(model==null)
	{
		response.sendRedirect("<script>history.go(-1)</script>");
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>翔通运营管理平台</title>
<link href="../wel_data/right.css" rel="stylesheet" type="text/css">
<link href="../wel_data/gray.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../sysjs/jquery-1.7.js"></script>
<script type="text/javascript" src="../sysjs/base.js"></script>
<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	$(function()
	{
		resetForm();
	});
	
	function resetForm()
	{
		$("#input_rate").val("<%= model.getRate() %>");
		$("#input_day_limit").val("<%= model.getDayLimit() %>");
		$("#input_month_limit").val("<%= model.getMonthLimit() %>");
	}
	
	function subForm() 
	{
		var limit = parseFloat($("#input_day_limit").val());
		if (isNaN(limit) || limit < 0)
		{
			alert("请输入正确的日限");
			$("#input_day_limit").focus();
			return;
		}
		
		limit = parseFloat($("#input_month_limit").val());
		if (isNaN(limit) || limit < 0)
		{
			alert("请输入正确的月限");
			$("#input_month_limit").focus();
			return;
		}
		
		limit = parseFloat($("#input_rate").val());
		if (isNaN(limit) || limit<0 || limit>=1)
		{
			alert("请输入正确的结算率");
			$("#input_rate").focus();
			return;
		}
		
		document.getElementById("addform").submit();
	}
	
</script>
<body>
	<div class="main_content">
		<div class="content" style="margin-top: 10px">
			<dl>
				<dd class="ddbtn" style="width:200px" >
				<label >CP业务数据修改</label>
				</dd>
			</dl>
			<br />	<br />		
			<dl>
				<form action="rateaction.jsp?type=6&query=<%= query %>" method="post" id="addform">
					<input type="hidden" value="<%= model.getId() %>" name="id" />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">CP业务</dd>
					<dd class="dd03_me">
						<input type="text" name="full_name" id="input_full_name" readonly="readonly" value="<%= model.getCpName() + "-" + model.getSpName() + "-" + model.getSpTroneName() %>"
							style="width: 200px;color: #ccc">
					</dd>
					
					<br />
					<br />
					<br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">日限</dd>
					<dd class="dd03_me">
						<input type="text" name="day_limit" value="0" id="input_day_limit"
							style="width: 200px">
					</dd>
					
					<br />
					<br />
					<br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">月限</dd>
					<dd class="dd03_me">
						<input type="text" name="month_limit" value="0" id="input_month_limit"
							style="width: 200px">
					</dd>
					
					<br />
					<br />
					<br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">结算率</dd>
					<dd class="dd03_me">
						<input type="text" name="rate" title="结算率" id="input_rate"
							style="width: 200px">
					</dd>

					<br />
					<br />
					<br />
					<dd class="dd00"></dd>
					<dd class="dd00_me"></dd>
					<dd class="ddbtn" style="margin-left: 100px; margin-top: 10px">
						<input type="button" value="提 交" onclick="subForm()">
					</dd>
					<dd class="ddbtn" style="margin-left: 32px; margin-top: 10px">
						<input type="button" value="重 置" onclick="resetForm()">
					</dd>
					<dd class="ddbtn" style="margin-left: 32px; margin-top: 10px">
						<input type="button" value="返 回" onclick="history.go(-1)">
					</dd>
				</form>
			</dl>
		</div>

	</div>
</body>
</html>