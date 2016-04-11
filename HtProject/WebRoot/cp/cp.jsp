<%@page import="com.system.util.Base64UTF"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.system.constant.Constant"%>
<%@page import="com.system.util.PageUtil"%>
<%@page import="com.system.model.CpModel"%>
<%@page import="java.util.List"%>
<%@page import="com.system.server.CpServer"%>
<%@page import="java.util.Map"%>
<%@page import="com.system.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int pageIndex = StringUtil.getInteger(request.getParameter("pageindex"), 1);
	String fullName = StringUtil.getString(request.getParameter("fullname"), "");
	String shortName = StringUtil.getString(request.getParameter("shortname"), "");

	Map<String, Object> map =  new CpServer().loadCp(pageIndex, fullName, shortName);
		
	List<CpModel> list = (List<CpModel>)map.get("list");
	
	int rowCount = (Integer)map.get("rows");
	
	Map<String,String> params = new HashMap<String,String>();
	
	params.put("fullname", fullName);
	params.put("shortname", shortName);
	
	String pageData = PageUtil.initPageQuery("cp.jsp",params,rowCount,pageIndex);
	
	String query = Base64UTF.encode(request.getQueryString());
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
	
	function delTrone(id)
	{
		if(confirm('真的要删除吗？'))
		{
			window.location.href = "troneaction.jsp?did=" + id;	
		}
	}
	
</script>

<body>
	<div class="main_content">
		<div class="content" >
			<dl>
				<dd class="ddbtn" ><a href="cpadd.jsp">增  加</a></dd>
				<form action="cp.jsp"  method="post" id="formid">
				<dl>
					<dd class="dd01_me">全称</dd>
					<dd class="dd03_me">
						<input name="fullname" id="input_fullname" value="<%=fullName %>" type="text" style="width: 150px">
					</dd>
					<dd class="dd01_me">简称</dd>
					<dd class="dd03_me">
						<input name="shortname" id="input_shortname" value="<%=shortName %>" type="text" style="width: 150px">
					</dd>
					<dd class="ddbtn" style="margin-left: 10px; margin-top: 0px;">
						<input class="btn_match" name="search" value="查 询" type="submit" >
					</dd>
				</dl>
				</form>
			</dl>
		</div>
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td>序号</td>
					<td>CPID</td>
					<td>全称</td>
					<td>简称</td>
					<td>联系人</td>
					<td>QQ</td>
					<td>电话</td>
					<td>邮箱</td>
					<td>合同起始日</td>
					<td>合同结束日</td>
					<td>登录名</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<%
					int rowNum = 1;
					for (CpModel model : list)
					{
				%>
				<tr>
					<td><%=(pageIndex-1)*Constant.PAGE_SIZE + rowNum++ %></td>
					<td><%= model.getId() + 2000 %></td>
					<td><%=model.getFullName()%></td>
					<td><%=model.getShortName()%></td>
					<td><%=model.getContactPerson()%></td>
					<td><%=model.getQq()%></td>
					<td><%=model.getPhone() %></td>
					<td><%=model.getMail() %></td>
					<td><%=model.getContractStartDate() %></td>
					<td><%=model.getContractEndDate() %></td>
					<td><%=model.getUserName() %></td>
					<td>
						<a href="cpedit.jsp?id=<%= model.getId() %>&pageindex=<%=pageIndex%>&fullname=<%=fullName%>&shortname=<%=shortName%>">修改</a>
						<a href="cpaccount.jsp?id=<%= model.getId() %>&query=<%= query %>">用户分配</a>
					</td>
				</tr>
				<%
					}
				%>
			</tbody>	
			<tbody>
				<tr>
					<td colspan="12" class="tfooter" style="text-align: center;"><%= pageData %></td>
				</tr>
			</tbody>
		</table>
	</div>
	
</body>
</html>