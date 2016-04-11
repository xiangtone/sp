<%@page import="com.system.util.Base64UTF"%>
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
	
	int spId = StringUtil.getInteger(request.getParameter("sp_id"), -1);
	
	int spTroneId = StringUtil.getInteger(request.getParameter("sp_trone"), -1);
	
	String orders = StringUtil.getString(request.getParameter("orders"), "");
	
	String troneNum = StringUtil.getString(request.getParameter("trone_num"), "");
	
	String troneName = StringUtil.getString(request.getParameter("trone_name"), "");
	
	Map<String, Object> map =  new TroneServer().loadTrone(spId, pageIndex, spTroneId, orders, troneNum, troneName);
		
	List<TroneModel> list = (List<TroneModel>)map.get("list");
	
	List<SpModel> spList = new SpServer().loadSp();
	
	List<TroneModel> tronList = new TroneServer().loadSpTrone();
	
	int rowCount = (Integer)map.get("rows");
	
	Map<String, String> params = null;
	
	params = new HashMap<String,String>();
	
	params.put("sp_id", spId + "");
	params.put("sp_trone", spTroneId+"");
	params.put("orders", orders);
	params.put("trone_num", troneNum);
	params.put("trone_name", troneName);
	
	String pageData = PageUtil.initPageQuery("trone.jsp",params,rowCount,pageIndex);
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>翔通运营管理平台</title>
<link href="../wel_data/right.css" rel="stylesheet" type="text/css">
<link href="../wel_data/gray.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../sysjs/jquery-1.7.js"></script>
<script type="text/javascript" src="../sysjs/MapUtil.js"></script>
<script type="text/javascript" src="../sysjs/pinyin.js"></script>
<script type="text/javascript" src="../sysjs/AndyNamePicker.js"></script>
<script type="text/javascript">

	function SpTroneObject(SpTroneId,SpId,name)
	{
		var obj = {};
		obj.SpId = SpId;
		obj.SpTroneId = SpTroneId;
		obj.name = name;
		return obj;
	}
	
	var spList = new Array();
	<%
	for(SpModel spModel : spList)
	{
		%>
		spList.push(new joSelOption(<%= spModel.getId() %>,1,'<%= spModel.getShortName() %>'));
		<%
	}
	%>

	var menu1Arr = new Array();
	<%
		for(TroneModel trone : tronList)
		{
			
			%>
	menu1Arr.push(new SpTroneObject(<%= trone.getSpTroneId() %>,<%= trone.getSpId() %>,'<%= trone.getSpTroneName() %>'));
			<%
		}
	%>
	
	function delTrone(id)
	{
		if(confirm('真的要删除吗？'))
		{
			window.location.href = "troneaction.jsp?did=" + id;	
		}
	}
	
	$(function()
	{
		$("#sel_sp").val(<%= spId %>);
		$("#sel_sp").change(SpTroneChange);
		SpTroneChange();
		$("#sel_sp_trone_id").val(<%= spTroneId %>);
		$("#input_orders").val(<%= orders %>);
		$("#input_trone_num").val(<%= troneNum %>);
		$("#input_trone_name").val(<%= troneName %>);
	});
	
	function SpTroneChange()
	{
		var SpId = $("#sel_sp").val();
		$("#sel_sp_trone_id").empty(); 
		$("#sel_sp_trone_id").append("<option value='-1'>请选择</option>");
		for(i=0; i<menu1Arr.length; i++)
		{
			if(menu1Arr[i].SpId==SpId)
			{
				$("#sel_sp_trone_id").append("<option value='" + menu1Arr[i].SpTroneId + "'>" + menu1Arr[i].name + "</option>");
			}
		}
	}
	
	function onSpDataSelect(joData)
	{
		$("#sel_sp").val(joData.id);
		SpTroneChange();
	}
	
</script>

<body>
	<div class="main_content">
		<div class="content" >
			<dl>
				<dd class="ddbtn" ><a href="troneadd.jsp">增  加</a></dd>
			</dl>
			<form action="trone.jsp"  method="get" style="margin-top: 10px">
				<dl>
					<dd class="dd01_me">SP</dd>
					<dd class="dd04_me">
						<select name="sp_id" id="sel_sp" title="选择SP" onclick="namePicker(this,spList,onSpDataSelect)">
							<option value="-1">全部</option>
							<%
							for(SpModel sp : spList)
							{
								%>
							<option value="<%= sp.getId() %>"><%= sp.getShortName() %></option>	
								<%
							}
							%>
						</select>
					</dd>
					<dd class="dd01_me">SP业务</dd>
					<dd class="dd04_me">
						<select name="sp_trone" id="sel_sp_trone_id" > </select>
					</dd>
					<dd class="dd01_me">指令</dd>
					<dd class="dd03_me">
						<input name="orders" id="input_orders" value="" type="text" style="width: 100px">
					</dd>
					<dd class="dd01_me">通道号</dd>
					<dd class="dd03_me">
						<input name="trone_num" id="input_trone_num" value="" type="text" style="width: 100px">
					</dd>
					<dd class="dd01_me">通道名称</dd>
					<dd class="dd03_me">
						<input name="trone_name" id="input_trone_name" value="" type="text" style="width: 100px">
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
					<td>SP名称</td>
					<td>SP业务名称</td>
					<td>指令</td>
					<td>通道号</td>
					<td>通道名称</td>
					<td>价格</td>
					<td>状态</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<%
					int rowNum = 1;
					String stopStyle = "class=\"StopStyle\"";
					for (TroneModel model : list)
					{
				%>
				<tr <%= model.getStatus()==0 ? stopStyle : "" %>>
					<td><%=(pageIndex-1)*Constant.PAGE_SIZE + rowNum++ %></td>
					<td><%=model.getSpShortName()%></td>
					<td><%=model.getSpTroneName()%></td>
					<td><%=model.getOrders()%></td>
					<td><%=model.getTroneNum() %></td>
					<td><%=model.getTroneName() %></td>
					<td><%=model.getPrice() %></td>
					<td><%=model.getStatus()==1 ? "启用" : "停用" %></td>
					<td>
						<a href="troneedit.jsp?query=<%= query %>&id=<%= model.getId() %>">修改</a>
						<a href="troneedit.jsp?copy=1&id=<%= model.getId() %>">复制</a>
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