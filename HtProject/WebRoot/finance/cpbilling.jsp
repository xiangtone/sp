<%@page import="com.system.server.JsTypeServer"%>
<%@page import="com.system.model.JsTypeModel"%>
<%@page import="com.system.model.CpBillingModel"%>
<%@page import="com.system.server.CpBillingServer"%>
<%@page import="com.system.util.Base64UTF"%>
<%@page import="com.system.server.CpServer"%>
<%@page import="com.system.model.CpModel"%>
<%@page import="com.system.model.TroneOrderModel"%>
<%@page import="com.system.server.TroneOrderServer"%>
<%@page import="com.system.server.SpServer"%>
<%@page import="com.system.model.SpModel"%>
<%@page import="com.system.model.TroneModel"%>
<%@page import="com.system.server.TroneServer"%>
<%@page import="com.system.constant.Constant"%>
<%@page import="com.system.util.PageUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.system.model.SpTroneModel"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.system.server.SpTroneServer"%>
<%@page import="com.system.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int pageIndex = StringUtil.getInteger(request.getParameter("pageindex"), 1);

	String query = Base64UTF.encode(request.getQueryString());

	int cpId = StringUtil.getInteger(request.getParameter("cp_id"), -1);
	
	int jsType = StringUtil.getInteger(request.getParameter("js_type"), -1);
	
	String startDate = StringUtil.getString(request.getParameter("startdate"), "");
	
	String endDate = StringUtil.getString(request.getParameter("enddate"), "");

	Map<String, Object> map =  new CpBillingServer().loadCpBilling(startDate, endDate, cpId, jsType, pageIndex);
		
	List<CpBillingModel> list = (List<CpBillingModel>)map.get("list");
	
	List<CpModel> cpList = new CpServer().loadCp();
	
	List<JsTypeModel> jsTypeList = new JsTypeServer().loadJsType();
	
	int rowCount = (Integer)map.get("rows");
	
	Map<String, String> params = new HashMap<String,String>();
	
	params = new HashMap<String,String>();
	params.put("js_type", jsType + "");
	params.put("cp_id", cpId + "");
	params.put("startdate", startDate);
	params.put("enddate",endDate);
	
	String pageData = PageUtil.initPageQuery("cpbilling.jsp",params,rowCount,pageIndex);
	
	String[] statusData = {"准备","对帐中"};
	
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
<script type="text/javascript" src="../sysjs/MapUtil.js"></script>
<script type="text/javascript" src="../sysjs/pinyin.js"></script>
<script type="text/javascript" src="../sysjs/base.js"></script>
<script type="text/javascript" src="../sysjs/AndyNamePicker.js"></script>
<script type="text/javascript">

	var cpList = new Array();
	
	<%
	for(CpModel cpModel : cpList)
	{
		%>
		cpList.push(new joSelOption(<%= cpModel.getId() %>,1,'<%= cpModel.getShortName() %>'));
		<%
	}
	%>
	
	function onCpDataSelect(joData)
	{
		$("#sel_cp_id").val(joData.id);
	}

	function delCpBilling(id)
	{
		if(confirm('真的要删除帐单吗？删除后可以在CP帐单下重新生成'))
		{
			window.location.href = "troneaction.jsp?did=" + id;	
		}
	}
	
	$(function()
	{
		$("#sel_js_type").val(<%= jsType %>);
		$("#sel_cp_id").val(<%= cpId %>);
	});
	
	
</script>

<body>
	<div class="main_content">
		<div class="content" >
			<dl>
				<dd class="ddbtn" ><a href="troneorderadd.jsp">增  加</a></dd>
			</dl>
			<form action="cpbilling.jsp"  method="get" style="margin-top: 10px">
				<dl>
					<dd class="dd01_me">开始日期</dd>
					<dd class="dd03_me">
						<input name="startdate" type="text" value="<%=startDate%>"
							onclick="WdatePicker({isShowClear:false,readOnly:true})">
					</dd>
					<dd class="dd01_me">结束日期</dd>
					<dd class="dd03_me">
						<input name="enddate" type="text" value="<%=endDate%>"
							onclick="WdatePicker({isShowClear:false,readOnly:true})">
					</dd>
					<dd class="dd01_me">CP</dd>
					<dd class="dd04_me">
						<select name="cp_id" id="sel_cp_id" onclick="namePicker(this,cpList,onCpDataSelect)">
						<option value="-1">全部</option>
							<%
							for(CpModel cp : cpList)
							{
								%>
							<option value="<%= cp.getId() %>"><%= cp.getShortName() %></option>	
								<%
							}
							%>
						</select>
					</dd>
					<dd class="dd01_me">结算类型</dd>
					<dd class="dd04_me">
						<select name="js_type" id="sel_js_type" >
						<option value="-1">请选择</option>
						<%
						for(JsTypeModel jsTypeModel : jsTypeList)
						{
							%>
							<option value="<%= jsTypeModel.getJsType() %>"><%= jsTypeModel.getJsName() %></option>
							<%
						}
						%>
						</select>
					</dd>
					<dd class="ddbtn" style="margin-left: 10px; margin-top: 0px;">
						<input class="btn_match" name="search" value="查     询" type="submit" />
					</dd>
				</dl>
			</form>
		</div>
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td>序号</td>
					<td>CP</td>
					<td>开始时间</td>
					<td>结束时间</td>
					<td>结算类型</td>
					<td>税率</td>
					<td>预支付</td>
					<td>备注</td>
					<td>创建时间</td>
					<td>状态</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<%
					int rowNum = 1;
					for (CpBillingModel model : list)
					{
				%>
				<tr>
					<td><%=(pageIndex-1)*Constant.PAGE_SIZE + rowNum++ %></td>
					<td><%=model.getCpName() %></td>
					<td><%=model.getStartDate() %></td>
					<td><%=model.getEndDate()%></td>
					<td><%= model.getJsName() %></td>
					<td><%= model.getTaxRate() %></td>
					<td><%=model.getPreBilling() %></td>
					<td><%=model.getRemark() %></td>
					<td><%= model.getCreateDate() %></td>
					<td><%= statusData[model.getStatus()] %></td>
					<td>
						<a href="cpbillingedit.jsp?query=<%= query %>&id=<%= model.getId() %>" >修改</a>
						<a href="cpbilling.jsp?type=1&cpid=<%= model.getId() %>">导出</a>
					</td>
				</tr>
				<%
					}
				%>
				
			<tbody>
				<tr>
					<td colspan="13" class="tfooter" style="text-align: center;"><%= pageData %></td>
				</tr>
			</tbody>
		</table>
	</div>
	
</body>
</html>