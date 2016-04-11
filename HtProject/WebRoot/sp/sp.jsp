<%@page import="com.system.server.UserServer"%>
<%@page import="com.system.model.UserModel"%>
<%@page import="com.system.util.ConfigManager"%>
<%@page import="com.system.util.Base64UTF"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.system.constant.Constant"%>
<%@page import="com.system.util.PageUtil"%>
<%@page import="com.system.model.SpModel"%>
<%@page import="java.util.List"%>
<%@page import="com.system.server.SpServer"%>
<%@page import="java.util.Map"%>
<%@page import="com.system.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int pageIndex = StringUtil.getInteger(request.getParameter("pageindex"), 1);
	String fullName = StringUtil.getString(request.getParameter("fullname"), "");
	String shortName = StringUtil.getString(request.getParameter("shortname"), "");
	
	int commerceUserId = StringUtil.getInteger(request.getParameter("commerce_user_id"), -1);

	Map<String, Object> map =  new SpServer().loadSp(pageIndex, fullName, shortName,commerceUserId);
		
	List<SpModel> list = (List<SpModel>)map.get("list");
	
	int rowCount = (Integer)map.get("rows");
	
	int spCommerceId = StringUtil.getInteger(ConfigManager.getConfigData("SP_COMMERCE_GROUP_ID"),-1);
	
	List<UserModel> userList = new UserServer().loadUserByGroupId(spCommerceId);
	
	Map<String,String> params = new HashMap<String,String>();
	
	params.put("fullname", fullName);
	params.put("shortname", shortName);
	params.put("commerce_user_id", commerceUserId + "");
	
	String pageData = PageUtil.initPageQuery("sp.jsp",params,rowCount,pageIndex);
	
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
	
	$(function()
	{
		$("#sel_commerce_user_id").val(<%= commerceUserId %>);
	});
	
</script>

<body>
	<div class="main_content">
		<div class="content" >
			<dl>
				<dd class="ddbtn" ><a href="spadd.jsp">增  加</a></dd>
				<form action="sp.jsp"  method="post" id="formid">
				<dl>
					<dd class="dd01_me">全称</dd>
					<dd class="dd03_me">
						<input name="fullname" id="input_fullname" value="<%=fullName %>" type="text" style="width: 150px">
					</dd>
					<dd class="dd01_me">简称</dd>
					<dd class="dd03_me">
						<input name="shortname" id="input_shortname" value="<%=shortName %>" type="text" style="width: 150px">
					</dd>
					<dd class="dd01_me">商务人员</dd>
						<dd class="dd04_me">
							<select name="commerce_user_id" id="sel_commerce_user_id">
								<option value="-1">请选择</option>
								<%
								for(UserModel model : userList)
								{
									%>
								<option value="<%= model.getId() %>"><%= model.getNickName() %></option>	
									<%
								}
								%>
							</select>
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
					<td>SPID</td>
					<td>全称</td>
					<td>简称</td>
					<td>联系人</td>
					<td>商务</td>
					<td>QQ</td>
					<td>电话</td>
					<td>邮箱</td>
					<td>合同起始日</td>
					<td>合同结束日</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<%
					int rowNum = 1;
					for (SpModel model : list)
					{
				%>
				<tr>
					<td><%=(pageIndex-1)*Constant.PAGE_SIZE + rowNum++ %></td>
					<td><%= model.getId() + 1000 %></td>
					<td><%=model.getFullName()%></td>
					<td><%=model.getShortName()%></td>
					<td><%=model.getContactPerson()%></td>
					<td><%=model.getCommerceUserName() %></td>
					<td><%=model.getQq()%></td>
					<td><%=model.getPhone() %></td>
					<td><%=model.getMail() %></td>
					<td><%=model.getContractStartDate() %></td>
					<td><%=model.getContractEndDate() %></td>
					<td>
						<a href="spedit.jsp?query=<%= query %>&id=<%= model.getId() %>">修改</a>
					</td>
				</tr>
				<%
					}
				%>
				
			<tbody>
				<tr>
					<td colspan="12" class="tfooter" style="text-align: center;"><%= pageData %></td>
				</tr>
			</tbody>
		</table>
	</div>
	
</body>
</html>