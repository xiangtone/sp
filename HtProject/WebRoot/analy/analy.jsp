<%@page import="com.system.server.DailyAnalyServer"%>
<%@page import="com.system.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String date = StringUtil.getString(request.getParameter("date"), StringUtil.getDefaultDate());
	boolean isAnaly = StringUtil.getInteger(request.getParameter("analy"), -1) == 1 ;
	System.out.println("analy:" + isAnaly);
	String msg = "重新分析数据";
	if(isAnaly)
	{
		msg += new DailyAnalyServer().analyDailyMr(date) ? "成功" : "失败";
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
<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	function onSubmit()
	{
		var date = document.getElementById("input_date").value;
		if(confirm("确定要重新分析【" + date + "】这一天的数据吗？"))
		{
			document.getElementById("formid").submit();
		}
	}
	
</script>

<body>
	<div class="main_content">
		<div class="content" >
			<dl>
				<form action="analy.jsp"  method="post" id="formid">
				<input type="hidden" value="1" name="analy"/>
				<dl>
					<dd class="dd01_me">选择日期</dd>
					<dd class="dd03_me">
						<input name="date" id="input_date"  value="<%= date %>" onclick="WdatePicker({isShowClear:false,readOnly:true})" type="text" style="width: 80px">
					</dd>
					<dd class="ddbtn" style="margin-left: 10px; margin-top: 0px;width:200px">
						<input class="btn_match" name="search" value="重新分析" type="button" onclick="onSubmit()" >
						<%= isAnaly ? msg : "" %>
					</dd>
				</dl>
				</form>
			</dl>
		</div>
	</div>
	
</body>
</html>