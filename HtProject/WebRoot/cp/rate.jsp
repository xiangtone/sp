<%@page import="java.net.URLEncoder"%>
<%@page import="com.system.util.Base64UTF"%>
<%@page import="com.system.constant.Constant"%>
<%@page import="com.system.model.CpSpTroneRateModel"%>
<%@page import="java.util.List"%>
<%@page import="com.system.util.PageUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.system.server.CpSpTroneRateServer"%>
<%@page import="com.system.util.PinYinUtil"%>
<%@ page import="com.system.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int refresh = StringUtil.getInteger(request.getParameter("refresh"), -1);

	if(refresh==1)
	{
		new CpSpTroneRateServer().syncUnAddCpSpTroneRate();
	}

	int pageIndex = StringUtil.getInteger(request.getParameter("pageindex"), 1);

	String keyWord = StringUtil.getString(request.getParameter("keyword"), "");
	
	Map<String, Object> map = new CpSpTroneRateServer().loadCpSpTroneRate(keyWord, pageIndex); 
	
	int rowCount = (Integer)map.get("rows");
	
	List<CpSpTroneRateModel> list = (List<CpSpTroneRateModel>)map.get("list");
	
	Map<String, String> params = new HashMap<String,String>();
	
	params = new HashMap<String,String>();
	
	params.put("keyword", keyWord);
	
	String pageData = PageUtil.initPageQuery("rate.jsp",params,rowCount,pageIndex);
	
	String query = request.getQueryString();
	
	query = Base64UTF.encode(query);
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
	
	function delTrone(id)
	{
		if(confirm('真的要删除吗？'))
		{
			window.location.href = "troneaction.jsp?did=" + id;	
		}
	}

	function editShowData(editId)
	{
		var curShowRows = $("#hid_" + editId).val();
		
		var newHtml = "<input type='text' id='myput_" + editId + "' style='width:30px;background-color:#CDC5BF;text-align:center;' value='"+ curShowRows +"' />";
		
		newHtml += "<input type='button' value='更新' style='margin-left: 10px' onclick='updateShowData(" + editId + ")'/>";
		 
		newHtml += "<input type='button' value='取消' style='margin-left: 10px' onclick='cancelShowData(" + editId + ")'/>";
		
		$("#span_" + editId).html(newHtml);
	}
	
	function updateShowData(editId)
	{
		var newShowRows = parseFloat($("#myput_" + editId).val());
		
		if(isNaN(newShowRows) || newShowRows>=1 || newShowRows<=0)
		{
			alert("请输入介于0和1之间的数据");
			return;
		}
		
		updateDbData(editId,newShowRows);
	}
	
	function updateDbData(editId,newShowRows)
	{
		$.post("rateaction.jsp", 
		{
			type : 1,
			rate : newShowRows,
			id :editId 
		}, 
		function(data) 
		{
			data = $.trim(data);
			if ("OK" == data) 
			{
				$("#hid_" + editId).val(newShowRows);		
				$("#span_" + editId).html(newShowRows);
			} 
			else 
			{
				alert("修改失败！请联系管理员");
				$("#span_" + editId).html($("#hid_" + editId).val());
			}
		});
	}
	
	function cancelShowData(editId)
	{
		$("#span_" + editId).html($("#hid_" + editId).val());
	}
	
</script>

<body>
	<div class="main_content">
		<div class="content" >
			<dl>
				<dd class="ddbtn" ><a href="rate.jsp?refresh=1">导  入</a></dd>
			</dl>
			<form action="rate.jsp"  method="get" style="margin-top: 10px" id="addform">
				<dl>
					<dd class="dd01_me">关键字</dd>
						<dd class="dd03_me"><input type="text" name="keyword" value="<%= keyWord %>"  /></dd>
					<dd class="ddbtn" style="margin-left: 10px; margin-top: 0px;">
						<input class="btn_match" type="submit" name="search" value="查     询"  />
					</dd>
				</dl>
			</form>
		</div>
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td>序号</td>
					<td>CP名称</td>
					<td>SP名称</td>
					<td>SP业务名称</td>
					<td>日限</td>
					<td>月限</td>
					<td>结算率</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<%
					int rowNum = 1;
					String title = "";
					for (CpSpTroneRateModel model : list)
					{
						title = model.getCpName() + "-" + model.getSpName() + "-" + model.getSpTroneName();
				%>
				<tr>
					<td>
						<%=(pageIndex-1)*Constant.PAGE_SIZE + rowNum++ %>
						<input type="hidden" id="hid_<%= model.getId() %>" value="<%= model.getRate() %>" />
					</td>
					<td><%=model.getCpName()%></td>
					<td><%=model.getSpName()%></td>
					<td><%=model.getSpTroneName()%></td>
					<td><%= model.getDayLimit() %></td>
					<td><%= model.getMonthLimit() %></td>
					<td ondblclick="editShowData('<%= model.getId() %>')">
						<span id="span_<%= model.getId() %>"><%= model.getRate() %></span>
					</td>
					<td>
						<a href="rateedit.jsp?query=<%= query %>&id=<%= model.getId() %>">修改</a>
						<a href="ratelist.jsp?query=<%= query %>&id=<%= model.getId() %>&title=<%= URLEncoder.encode(title,"utf-8") %>&rate=<%= model.getRate() %>">列表</a>
					</td>
				</tr>
				<%
					}
				%>
			<tbody>
				<tr>
					<td colspan="9" class="tfooter" style="text-align: center;"><%= pageData %></td>
				</tr>
			</tbody>
		</table>
	</div>
	
</body>
</html>