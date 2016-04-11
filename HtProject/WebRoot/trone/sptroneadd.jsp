<%@page import="com.system.server.SpTroneApiServer"%>
<%@page import="com.system.model.SpTroneApiModel"%>
<%@page import="com.system.model.ProvinceModel"%>
<%@page import="com.system.server.ProvinceServer"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.system.model.SettleAccountModel"%>
<%@page import="java.util.Map"%>
<%@page import="com.system.server.SettleAccountServer"%>
<%@page import="com.system.server.SpServer"%>
<%@page import="com.system.model.SpModel"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="com.system.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	List<SpModel> spList = new SpServer().loadSp();
	List<ProvinceModel> provinceList = new ProvinceServer().loadProvince();
	List<SpTroneApiModel> spTroneApiList = new SpTroneApiServer().loadSpTroneApi();
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
<script type="text/javascript" src="../sysjs/AndyNamePicker.js"></script>
<script type="text/javascript">

	var provinceList = new Array();
	
	<%
	for(ProvinceModel proModel : provinceList)
	{
		%>
		provinceList.push(new joSelOption(<%= proModel.getId() %>,1,'<%= proModel.getName() %>'));
		<%
	}
	%>

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

	function subForm() 
	{
		if ($("#sel_sp").val() == "-1") {
			alert("请选择SP");
			$("#sel_sp").focus();
			return;
		}
		
		if ($("#sel_operator").val() == "-1") {
			alert("请选择运营商");
			$("#sel_operator").focus();
			return;
		}
		
		if ($("#input_sp_trone_name").val() == "") {
			alert("请输入业务名称");
			$("#input_sp_trone_name").focus();
			return;
		}
		
		var rate = parseFloat($("#input_jiesuanlv").val());
		
		if(isNaN(rate) || rate>=1 || rate<=0)
		{
			alert("结算率只能介于0和1之间");
			$("#input_jiesuanlv").focus();
			return;
		}
		
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
		
		limit = parseFloat($("#input_user_day_limit").val());
		if (isNaN(limit) || limit < 0)
		{
			alert("请输入正确的用户日限");
			$("#input_user_day_limit").focus();
			return;
		}
		
		limit = parseFloat($("#input_user_month_limit").val());
		if (isNaN(limit) || limit < 0) 
		{
			alert("请输入正确的用户月限");
			$("#input_user_month_limit").focus();
			return;
		}
		
		if(getProvinceCount('area[]')<=0)
		{
			alert("请选择省份");
			return;
		}

		document.getElementById("addform").submit();
	}
	
	function getProvinceCount(items)
	{
		var i = 0;
		$('[name=' + items + ']:checkbox').each(function() {
			if(this.checked)
				i++;
		});
		return i;
	}
	
	//声明整数的正则表达式
	function isNum(a)
	{
		var reg=/^(([a-z]+[0-9]+)|([0-9]+[a-z]+))[a-z0-9]*$/i;
		return reg.test(a);
	}

	function allCkb(items) {
		$('[name=' + items + ']:checkbox').attr("checked", true);
	}

	function unAllCkb() {
		$('[type=checkbox]:checkbox').attr('checked', false);
	}

	function inverseCkb(items) {
		$('[name=' + items + ']:checkbox').each(function() {
			this.checked = !this.checked;
		});
	}
	
	function importProvince()
	{
		var tmpPro = prompt("请输入省份", "");
		
		if ( tmpPro == null || "" == tmpPro )
			return;

		$('[name=area[]]:checkbox').each(function() 
		{
			if(tmpPro.indexOf(this.title) != -1)
			{
				this.checked = true;
				tmpPro = tmpPro.replace(this.title, "");
			}
		});
		
		if(tmpPro!="")
			alert(tmpPro);
	}
	
</script>
<body>
	<div class="main_content">
		<div class="content" style="margin-top: 10px">
			<dl>
				<dd class="ddbtn" >
				<label>增加业务</label>
				</dd>
			</dl>
			<br />	<br />
			<dl>
				<form action="sptroneaction.jsp" method="post" id="addform">
					<dd class="dd01_me">SP名称</dd>
					<dd class="dd04_me">
						<select name="sp_id_1" id="sel_sp" title="选择SP" style="width: 200px" onclick="namePicker(this,spList,onDataSelect)">
							<option value="-1">请选择SP名称</option>
							<%
								for (SpModel sp : spList)
								{
							%>
							<option value="<%=sp.getId()%>"><%=sp.getShortName()%></option>
							<%
								}
							%>
						</select>
					</dd>

					<br />
					<br />
					<br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">运营商</dd>
					<dd class="dd04_me">
						<select name="operator" id="sel_operator" title="选择运营商"
							style="width: 200px">
							<option value="-1">请选择</option>
							<option value="1">联通</option>
							<option value="2">电信</option>
							<option value="3">移动</option>
						</select>
					</dd>

					<br />
					<br />
					<br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">业务名称</dd>
					<dd class="dd03_me">
						<input type="text" name="sp_trone_name_1" title="业务名称" id="input_sp_trone_name"
							style="width: 200px">
					</dd>

					<br />
					<br />
					<br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">结算率</dd>
					<dd class="dd03_me">
						<input type="text" name="jiesuanlv" title="结算率" id="input_jiesuanlv"
							style="width: 200px">
					</dd>
					
					<br />
					<br />
					<br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">业务API</dd>
					<dd class="dd04_me">
						<select name="sp_trone_api" id="sel_sp_trone_api" title="选择业务API" style="width: 200px">
							<option value="-1">请选择业务API</option>
							<%
								for (SpTroneApiModel spTroneApiModel : spTroneApiList)
								{
							%>
							<option value="<%=spTroneApiModel.getId()%>"><%= spTroneApiModel.getName() %></option>
							<%
								}
							%>
						</select>
					</dd>
					
					<br />
					<br />
					<br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">业务类型</dd>
					<dd class="dd03_me">
						<input type="radio" name="trone_type" style="width: 35px;float:left" value="0" checked="checked" >
						<label style="font-size: 14px;float:left">普通</label>
						<input type="radio" name="trone_type" style="width: 35px;float:left" value="1" >
						<label style="font-size: 14px;float:left">包月</label>
						<input type="radio" name="trone_type" style="width: 35px;float:left" value="2" >
						<label style="font-size: 14px;float:left">IVR</label>
					</dd>
					
					<br />
					<br />
					<br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">状态</dd>
					<dd class="dd03_me">
						<input type="radio" name="status" style="width: 35px;float:left" value="1" checked="checked" >
						<label style="font-size: 14px;float:left">开启</label>
						<input type="radio" name="status" style="width: 35px;float:left" value="0" >
						<label style="font-size: 14px;float:left">关闭</label>
					</dd>
					
					<br />
					<br />
					<br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">通道日限</dd>
					<dd class="dd03_me">
						<input type="text" name="day_limit" value="0" id="input_day_limit"
							style="width: 200px">
					</dd>
					
					<br />
					<br />
					<br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">通道月限</dd>
					<dd class="dd03_me">
						<input type="text" name="month_limit" value="0" id="input_month_limit"
							style="width: 200px">
					</dd>
					
					<br />
					<br />
					<br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">用户日限</dd>
					<dd class="dd03_me">
						<input type="text" name="user_day_limit" value="0" id="input_user_day_limit"
							style="width: 200px">
					</dd>
					
					<br />
					<br />
					<br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">用户月限</dd>
					<dd class="dd03_me">
						<input type="text" name="user_month_limit"  value="0" id="input_user_month_limit"
							style="width: 200px">
					</dd>

					<br />
					<br />
					<br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">省份</dd>
					<div style="margin-left: 95px; width: 580px;" id="pro">

						<%
							for (ProvinceModel province : provinceList)
							{
						%>
						<dd class="dd01"><%=province.getName()%>
							<input style="" type="checkbox" title="<%= province.getName() %>" class="chpro" name="area[]"
								value="<%=province.getId()%>">
						</dd>
						<%
							}
						%>
						<input type="button" onclick="allCkb('area[]')"
							style="horve: point;" value="全　选" /> <input type="button"
							onclick="unAllCkb()" style="padding-top: 10px;" value="全不选" /> <input
							type="button" onclick="inverseCkb('area[]')"
							style="padding-top: 10px;" value="反　选" />
							<input
							type="button" onclick="importProvince()" style="padding-top: 10px;" value="导　入" />
					</div>

					<br />
					<br />
					<br />
					<dd class="dd00"></dd>
					<dd class="dd00_me"></dd>
					<dd class="ddbtn" style="margin-left: 100px; margin-top: 10px">
						<input type="button" value="提 交" onclick="subForm()">
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