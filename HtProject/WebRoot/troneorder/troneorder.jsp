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

	int status = StringUtil.getInteger(request.getParameter("trone_status"), -1);
	
	String	keyWord = StringUtil.getString(request.getParameter("keyword"), "");

	Map<String, Object> map =  new TroneOrderServer().loadTroneOrder(status,pageIndex,keyWord);
		
	List<TroneOrderModel> list = (List<TroneOrderModel>)map.get("list");
	
	List<SpModel> spList = new SpServer().loadSp();
	
	List<CpModel> cpList = new CpServer().loadCp();
	
	List<SpTroneModel> spTroneList = new SpTroneServer().loadSpTroneList();
	
	int rowCount = (Integer)map.get("rows");
	
	Map<String, String> params = new HashMap<String,String>();
	
	params = new HashMap<String,String>();
	params.put("trone_status",status + "");
	params.put("keyword",keyWord);
	
	String pageData = PageUtil.initPageQuery("troneorder.jsp",params,rowCount,pageIndex);
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>翔通运营管理平台</title>
<link href="../wel_data/right.css" rel="stylesheet" type="text/css">
<link href="../wel_data/gray.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../sysjs/jquery-1.7.js"></script>
<script type="text/javascript">
	
	$(function()
	{
		$("#sel_trone_status").val(<%= status %>);
	});
	
</script>

<body>
	<div class="main_content">
		<div class="content" >
			<dl>
				<dd class="ddbtn" ><a href="troneorderadd.jsp">增  加</a></dd>
			</dl>
			<form action="troneorder.jsp"  method="get" style="margin-top: 10px">
				<dl>
					<dd class="dd01_me">状态</dd>
					<dd class="dd04_me">
						<select name="trone_status" id="sel_trone_status" >
							<option value="-1">全部</option>
							<option value="0">启用</option>
							<option value="1">停用</option>
						</select>
					</dd>
					<dd class="dd01_me">关键字</dd>
					<dd class="dd03_me">
						<input type="text" name="keyword" id="sel_keyword" value="<%= keyWord %>" />
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
					<td>SP名称</td>
					<td>SP业务名称</td>
					<td>通道名称</td>
					<td>价格</td>
					<td>指令</td>
					<td>扣量设置</td>
					<td>扣量比</td>
					<td>同步金额</td>
					<td>起扣数</td>
					<td>模糊</td>
					<td>启用</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<%
					int rowNum = 1;
					String stopStyle = "class=\"StopStyle\"";
					for (TroneOrderModel model : list)
					{
				%>
				<tr <%= model.getDisable() == 1 ? stopStyle : "" %>>
					<td><%=(pageIndex-1)*Constant.PAGE_SIZE + rowNum++ %></td>
					<td><%=model.getCpShortName()%></td>
					<td><%=model.getSpShortName() %></td>
					<td><%=model.getSpTroneName()%></td>
					<td><%=model.getTroneName() %></td>
					<td><%= model.getPrice() %></td>
					<td><%=model.getOrderNum() %></td>
					<td><%=model.getIsHoldCustom()==0 ? "URL" : "当前" %></td>
					<td><%=model.getHoldPercent() %></td>
					<td><%=model.getHoldAmount() %></td>
					<td><%=model.getHoldAcount()%></td>
					<td><%=model.getDynamic()==1 ? "是" : "否" %></td>
					<td><%=model.getDisable() ==0 ? "是" : "否" %></td>
					<td>
						<a href="troneorderedit.jsp?query=<%= query %>&id=<%= model.getId() %>" >修改</a>
						<a href="troneordersync.jsp?id=<%= model.getId() %>" target="_blank">模拟</a>
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