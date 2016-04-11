<%@page import="com.system.util.ConfigManager"%>
<%@page import="com.system.server.UserServer"%>
<%@page import="com.system.model.UserModel"%>
<%@page import="com.system.util.Base64UTF"%>
<%@page import="com.system.server.SpServer"%>
<%@page import="com.system.model.SpModel"%>
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
	String spTroneName = StringUtil.getString(request.getParameter("sp_trone_name"), "");

	String query = Base64UTF.encode(request.getQueryString());

	int spId = StringUtil.getInteger(request.getParameter("sp_id"), -1);

	int pageIndex = StringUtil.getInteger(request.getParameter("pageindex"), 1);
	
	int commerceUserId = StringUtil.getInteger(request.getParameter("commerce_user_id"), -1);
	
	Map<String, Object> map = new SpTroneServer().loadSpTroneList(pageIndex,spId,commerceUserId,spTroneName);
	
	List<SpModel> spList = new SpServer().loadSp();

	List<SpTroneModel> list = (List<SpTroneModel>) map.get("list");

	int rowCount = (Integer) map.get("rows");
	
	Map<String,String> params = new HashMap<String,String>();
	
	params.put("commerce_user_id", commerceUserId + "");
	
	String pageData = PageUtil.initPageQuery("sptrone.jsp", params, rowCount, pageIndex);
	
	String[] troneTypes = {"点播","包月","IVR"};
	
	int spCommerceId = StringUtil.getInteger(ConfigManager.getConfigData("SP_COMMERCE_GROUP_ID"),-1);
	
	List<UserModel> userList = new UserServer().loadUserByGroupId(spCommerceId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>翔通运营管理平台</title>
<link href="../wel_data/right.css" rel="stylesheet" type="text/css">
<link href="../wel_data/gray.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../sysjs/jquery-1.7.js"></script>
<script type="text/javascript" src="../sysjs/MapUtil.js"></script>
<script type="text/javascript" src="../sysjs/pinyin.js"></script>
<script type="text/javascript" src="../sysjs/AndyNamePicker.js"></script>

<script type="text/javascript">

	var spList = new Array();
	<%
	for(SpModel spModel : spList)
	{
		%>
		spList.push(new joSelOption(<%= spModel.getId() %>,1,'<%= spModel.getShortName() %>'));
		<%
	}
	%>
	
	function onDataSelect(joData) 
	{
		$("#sel_sp").val(joData.id);
	}

	function delSpTrone(id)
	{
		if(confirm('真的要删除吗？'))
		{
			window.location.href = "sptroneaction.jsp?did=" + id;	
		}
	}
	
	$(function()
	{
		$("#sel_sp").val(<%= spId %>);
		$("#sel_commerce_user_id").val(<%= commerceUserId %>);
	});
	
	
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
		$.post("sptroneaction.jsp", 
		{
			type : 1,
			jiesuanlv : newShowRows,
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
		<div class="content">
			<dl>
				<dd class="ddbtn">
					<a href="sptroneadd.jsp">增 加</a>
				</dd>
				<form action="sptrone.jsp" method="get" id="formid">
					<dl>
						<dd class="dd01_me">SP</dd>
						<dd class="dd04_me">
							<select name="sp_id" id="sel_sp" title="选择SP" onclick="namePicker(this,spList,onDataSelect)">
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
						<dd class="dd01_me">业务</dd>
						<dd class="dd03_me">
							<input name="sp_trone_name" id="input_sp_trone_name" value="<%= spTroneName %>"
								type="text" style="width: 150px">
						</dd>
						<dd class="ddbtn" style="margin-left: 10px; margin-top: 0px;">
							<input class="btn_match" name="search" value="查 询" type="submit">
						</dd>
					</dl>
				</form>
			</dl>
		</div>
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td>序号</td>
					<td>SP名称</td>
					<td>运营商</td>
					<td>业务名称</td>
					<td>商务人员</td>
					<td>类型</td>
					<td>结算率</td>
					<td>日限</td>
					<td>月限</td>
					<td>用户日限</td>
					<td>用户月限</td>
					<td>状态</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<%
					int rowNum = 1;
					String stopStyle = "class=\"StopStyle\"";
					for (SpTroneModel model : list)
					{
				%>
				<tr <%= model.getStatus()==0 ? stopStyle : "" %>>
					<td><%=(pageIndex - 1) * Constant.PAGE_SIZE + rowNum++%>
						<input type="hidden" id="hid_<%= model.getId() %>" value="<%= model.getJieSuanLv() %>" />
					</td>
					<td><%=model.getSpName()%></td>
					<td><%=model.getOperatorName()%></td>
					<td><%=model.getSpTroneName()%></td>
					<td><%= model.getCommerceUserName() %></td>
					<td><%= troneTypes[model.getTroneType()]%></td>
					<td ondblclick="editShowData('<%= model.getId() %>')">
						<span id="span_<%= model.getId() %>"><%= model.getJieSuanLv() %></span>
					</td>
					<td><%= model.getDayLimit() %></td>
					<td><%= model.getMonthLimit() %></td>
					<td><%= model.getUserDayLimit() %></td>
					<td><%= model.getUserMonthLimit() %></td>
					<td><%= model.getStatus()==1 ? "开启" : "关闭" %></td>
					<td><a href="sptroneedit.jsp?query=<%= query %>&id=<%= model.getId() %>">修改</a>
						<a href="#" onclick="delSpTrone(<%=model.getId()%>)">删除</a></td>
				</tr>
				<%
					}
				%>
			
			<tbody>
				<tr>
					<td colspan="13" class="tfooter" style="text-align: center;"><%=pageData%></td>
				</tr>
			</tbody>
		</table>
	</div>

</body>
</html>