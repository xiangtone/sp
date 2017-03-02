<%@page import="com.system.server.JsTypeServer"%>
<%@page import="com.system.model.JsTypeModel"%>
<%@page import="com.system.server.UpDataTypeServer"%>
<%@page import="com.system.model.UpDataTypeModel"%>
<%@page import="com.system.util.ConfigManager"%>
<%@page import="com.system.server.ServiceCodeServer"%>
<%@page import="com.system.model.ServiceCodeModel"%>
<%@page import="com.system.server.SpTroneApiServer"%>
<%@page import="com.system.model.SpTroneApiModel"%>
<%@page import="com.system.util.PageUtil"%>
<%@page import="com.system.server.SpTroneServer"%>
<%@page import="com.system.model.SpTroneModel"%>
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
	int spTroneId = StringUtil.getInteger(request.getParameter("id"), -1);
	SpTroneModel spTroneModel = new SpTroneServer().loadSpTroneById(spTroneId);
	String query = StringUtil.getString(request.getParameter("query"), "");
	query = PageUtil.queryFilter(query, "id");
	if (spTroneModel == null) {
		response.sendRedirect("sptrone.jsp?" + query);
		return;
	}
	List<SpModel> spList = new SpServer().loadSp();
	List<ProvinceModel> provinceList = new ProvinceServer().loadProvince();
	List<SpTroneApiModel> spTroneApiList = new SpTroneApiServer().loadSpTroneApi();
	List<List<ServiceCodeModel>> serviceCodeList = new ServiceCodeServer().loadServiceCode();
	String jiuSuanName = ConfigManager.getConfigData("JIE_SUNA_NAME", "结算率");
	List<UpDataTypeModel> upDatatypeList=new UpDataTypeServer().loadUpDataType();
	List<JsTypeModel> jsTypeList = new JsTypeServer().loadJsType();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>运营管理平台</title>
<link href="../wel_data/right.css" rel="stylesheet" type="text/css">
<link href="../wel_data/gray.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../sysjs/jquery-1.7.js"></script>
<script type="text/javascript" src="../sysjs/base.js"></script>
<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../sysjs/MapUtil.js"></script>
<script type="text/javascript" src="../sysjs/pinyin.js"></script>
<script type="text/javascript" src="../sysjs/AndyNamePicker.js"></script>
<script type="text/javascript">

	var provinceList = new Array();
	
	<%for (ProvinceModel proModel : provinceList) {%>
		provinceList.push(new joSelOption(<%=proModel.getId()%>,1,'<%=proModel.getName()%>'));
		<%}%>

	var spList = new Array();
	<%for (SpModel spModel : spList) {%>
		spList.push(new joSelOption(<%=spModel.getId()%>,1,'<%=spModel.getShortName()%>'));
		<%}%>
	
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
		
		if ($("#sel_service_code").val() == "-1") {
			alert("请选择业务线");
			$("#sel_service_code").focus();
			return;
		}
		
		if ($("#input_sp_trone_name").val() == "") {
			alert("请输入业务名称");
			$("#input_sp_trone_name").focus();
			return;
		}
		
		if ($("#sel_js_type").val() == "-1") {
			alert("请输入结算类型");
			$("#sel_js_type").focus();
			return;
		}
		if ($("#up_data_type").val() == "-1") {
			alert("请输入上量类型");
			$("#up_data_type").focus();
			return;
		}
		var rate = parseFloat($("#input_jiesuanlv").val());
		
		if(isNaN(rate) || rate>=10 || rate<=0)
		{
			alert("<%=jiuSuanName%>只能介于0和1之间");
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
	
	$(function() 
	{
	<%
	if(spTroneModel.getApiStatus()==1){
	%>
	$("#div_sp_trone_api").show();
	<%}%>
		resetForm();
		$("input[name=api_status]").click(function(){
			 apiStatus();
			 });
	});
	
	function apiStatus(){
		 switch($("input[name=api_status]:checked").attr("id")){
		  case "api_status_1":
		   //alert("one");
		   $("#div_sp_trone_api").show();
		   
		   break;
		  case "api_status_0":
			$("#div_sp_trone_api").hide();
		   break;
		  default:
		   break;
		 }
	}
	
	function resetFormTwo()
	{
		$("#sel_sp").val("<%=spTroneModel.getSpId()%>");
		$("#sel_operator").val("<%=spTroneModel.getOperator()%>");
		$("#input_sp_trone_name").val("<%=spTroneModel.getSpTroneName()%>");
		$("#input_jiesuanlv").val("<%=spTroneModel.getJieSuanLv()%>");
		$("#sel_sp_trone_api").val("<%=spTroneModel.getTroneApiId()%>");
		
		$("#sel_service_code").val("<%=spTroneModel.getServiceCodeId()%>");
		$("#sel_js_type").val("<%=spTroneModel.getJsTypes()%>");
		
		$("#input_shield_start").val("<%=spTroneModel.getShieldStart()%>");
		$("#input_shield_end").val("<%=spTroneModel.getShieldEnd()%>");
		
		$("#input_day_limit").val("<%=spTroneModel.getDayLimit()%>");
		$("#input_month_limit").val("<%=spTroneModel.getMonthLimit()%>");
		$("#input_user_day_limit").val("<%=spTroneModel.getUserDayLimit()%>");
		$("#input_user_month_limit").val("<%=spTroneModel.getUserMonthLimit()%>");
		$("#up_data_type").val("<%=spTroneModel.getUpDataType()%>");
		
		<%
		if(spTroneModel.getApiStatus()==1){
		%>
		$("#div_sp_trone_api").show();
		<%}else{%>
		$("#div_sp_trone_api").hidden();
		<%}%>
		
		var provinceIds = "<%=spTroneModel.getProvinces()%>";
		var provinces = provinceIds.split(",");
		setRadioCheck("limit_type",
				<%=spTroneModel.getLimiteType()%>
					);
		setRadioCheck("trone_type",
<%=spTroneModel.getTroneType()%>
	);
		setRadioCheck("status",
<%=spTroneModel.getStatus()%>
	);
		
		setRadioCheck("is_unhold_data",<%=spTroneModel.getIsUnHoldData()%>);
		
		setRadioCheck("api_status",
				<%=spTroneModel.getApiStatus()%>
					);
		unAllCkb();
		$('[name=area[]]:checkbox').each(function() {

			for (k = 0; k < provinces.length; k++) {
				if (provinces[k] == this.value) {
					this.checked = true;
					break;
				}
			}
		});
	}
	
	function resetForm()
	{
		$("#sel_sp").val("<%=spTroneModel.getSpId()%>");
		$("#sel_operator").val("<%=spTroneModel.getOperator()%>");
		$("#input_sp_trone_name").val("<%=spTroneModel.getSpTroneName()%>");
		$("#input_jiesuanlv").val("<%=spTroneModel.getJieSuanLv()%>");
		$("#sel_sp_trone_api").val("<%=spTroneModel.getTroneApiId()%>");
		
		$("#sel_service_code").val("<%=spTroneModel.getServiceCodeId()%>");
		$("#sel_js_type").val("<%=spTroneModel.getJsTypes()%>");
	
		$("#input_shield_start").val("<%=spTroneModel.getShieldStart()%>");
		$("#input_shield_end").val("<%=spTroneModel.getShieldEnd()%>");
		
		$("#up_data_type").val("<%=spTroneModel.getUpDataType()%>");
		
		var provinceIds = "<%=spTroneModel.getProvinces()%>";
		var provinces = provinceIds.split(",");
		setRadioCheck("limit_type",
				<%=spTroneModel.getLimiteType()%>
					);
		setRadioCheck("trone_type",
<%=spTroneModel.getTroneType()%>
	);
		setRadioCheck("status",
<%=spTroneModel.getStatus()%>
	);
		setRadioCheck("api_status",
				<%=spTroneModel.getApiStatus()%>
					);
		
		setRadioCheck("is_unhold_data",<%=spTroneModel.getIsUnHoldData()%>);
		
		unAllCkb();
		$('[name=area[]]:checkbox').each(function() {

			for (k = 0; k < provinces.length; k++) {
				if (provinces[k] == this.value) {
					this.checked = true;
					break;
				}
			}
		});
	}

	function getProvinceCount(items) {
		var i = 0;
		$('[name=' + items + ']:checkbox').each(function() {
			if (this.checked)
				i++;
		});
		return i;
	}

	//声明整数的正则表达式
	function isNum(a) {
		var reg = /^(([a-z]+[0-9]+)|([0-9]+[a-z]+))[a-z0-9]*$/i;
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

	function importProvince() {
		var tmpPro = prompt("请输入省份", "");

		if (tmpPro == null || "" == tmpPro)
			return;

		$('[name=area[]]:checkbox').each(function() {
			if (tmpPro.indexOf(this.title) != -1) {
				this.checked = true;
				tmpPro = tmpPro.replace(this.title, "");
			}
		});

		if (tmpPro != "")
			alert(tmpPro);
	}

	function exportProvince() {
		var exportData = "";

		$('[name=area[]]:checkbox').each(function() {
			if (this.checked) {
				exportData += this.title + ",";
			}
		});

		if ("" != exportData) {
			exportData = exportData.substring(0, exportData.length - 1);
			prompt("已选择省份", exportData);
		}
	}
</script>
<body>
	<div class="main_content">
		<div class="content" style="margin-top: 10px">
			<dl>
				<dd class="ddbtn" style="width: 200px">
				<label>修改SP业务</label>
				</dd>
			</dl>
			<dl>
				<form action="sptroneaction.jsp?query=<%=query%>" method="post"
					id="addform">
					<table>
						<thead>
							<input type="hidden" value="<%=spTroneModel.getId()%>"
								name="id" />
						</thead>
					</table>
					<dd class="dd01_me">SP名称</dd>
					<dd class="dd04_me">
						<select name="sp_id_1" id="sel_sp" title="选择SP"
							style="width: 200px"
							onclick="namePicker(this,spList,onDataSelect)">
							<option value="-1">请选择SP名称</option>
							<%
								for (SpModel sp : spList) {
							%>
							<option value="<%=sp.getId()%>"><%=sp.getShortName()%></option>
							<%
								}
							%>
						</select>
					</dd>

					<br /> <br /> <br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">业务线</dd>
					<dd class="dd04_me">
						<select name="service_code" id="sel_service_code"
							style="width: 200px">
							<option value="-1">请选择业务线</option>
							<optgroup label="移动">
								<%
									for (ServiceCodeModel serviceCodeModel : serviceCodeList.get(0)) {
								%>
								<option value="<%=serviceCodeModel.getId()%>"><%=serviceCodeModel.getServiceName()%></option>
								<%
									}
								%>
							</optgroup>
							<optgroup label="联通">
								<%
									for (ServiceCodeModel serviceCodeModel : serviceCodeList.get(1)) {
								%>
								<option value="<%=serviceCodeModel.getId()%>"><%=serviceCodeModel.getServiceName()%></option>
								<%
									}
								%>
							</optgroup>
							<optgroup label="电信">
								<%
									for (ServiceCodeModel serviceCodeModel : serviceCodeList.get(2)) {
								%>
								<option value="<%=serviceCodeModel.getId()%>"><%=serviceCodeModel.getServiceName()%></option>
								<%
									}
								%>
							</optgroup>
							<optgroup label="第三方支付">
								<%
									for(ServiceCodeModel  serviceCodeModel : serviceCodeList.get(3))
									{
										%>
								<option value="<%= serviceCodeModel.getId() %>"><%= serviceCodeModel.getServiceName() %></option>		
										<%
									}
								%>
							</optgroup>									
							
							<!--  
							<option value="1">联通</option>
							<option value="2">电信</option>
							<option value="3">移动</option>
							-->

						</select>
					</dd>

					<br /> <br /> <br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">业务名称</dd>
					<dd class="dd03_me">
						<input type="text" name="sp_trone_name_1" title="业务名称"
							id="input_sp_trone_name" style="width: 200px">
					</dd>
					<br />
					<br />
					<br />
					<dd class="dd01_me">上量类型</dd>
					<dd class="dd04_me">
						<select name="up_data_type" id="up_data_type" title="上量类型" style="width: 200px" >
							<option value="-1">请选择上量类型</option>
								<%
									for(UpDataTypeModel  upDataTypeModel : upDatatypeList)
									{
										%>
								<option value="<%= upDataTypeModel.getId() %>"><%= upDataTypeModel.getName() %></option>		
										<%
									}
								%>
						</select>
					</dd>

					<br /> <br /> <br />
					<dd class="dd01_me">结算类型</dd>
					<dd class="dd04_me">
						<select name="js_type" id="sel_js_type" title="结算类型"
							style="width: 200px">
							<option value="-1">请选择结算类型</option>
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

					<br /> <br /> <br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me"><%=jiuSuanName%></dd>
					<dd class="dd03_me">
						<input type="text" name="jiesuanlv" id="input_jiesuanlv"
							style="width: 200px">
					</dd>

					

					<br /> <br /> <br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">数据类型</dd>
					<dd class="dd03_me" style="background: none">
						<input type="radio" name="trone_type"
							style="width: 35px; float: left" value="0" checked="checked">
						<label style="font-size: 14px; float: left">实时</label> <input
							type="radio" name="trone_type" style="width: 35px; float: left"
							value="1"> <label style="font-size: 14px; float: left">隔天</label>
						<input type="radio" name="trone_type"
							style="width: 35px; float: left" value="2"> <label
							style="font-size: 14px; float: left">IVR</label>
						<input type="radio" name="trone_type" style="width: 35px;float:left" value="3" >
						<label style="font-size: 14px;float:left">第三方支付</label>
					</dd>
					
					<br /> <br /> <br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">是否导量</dd>
					<dd class="dd03_me">
						<input type="radio" name="is_unhold_data" style="width: 35px; float: left"
							value="0"> <label style="font-size: 14px; float: left">否</label>
						<input type="radio" name="is_unhold_data" style="width: 35px; float: left"
							value="1"> <label style="font-size: 14px; float: left">是</label>
					</dd>
					
					<br /> <br /> <br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">状态</dd>
					<dd class="dd03_me">
						<input type="radio" name="status" style="width: 35px; float: left"
							value="1"> <label style="font-size: 14px; float: left">开启</label>
						<input type="radio" name="status" style="width: 35px; float: left"
							value="0"> <label style="font-size: 14px; float: left">关闭</label>
					</dd>
					
					<br />
					<br />
					<br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">代码池</dd>
					<dd class="dd03_me">
						<input type="radio" name="api_status" id="api_status_0" style="width: 35px;float:left" value="0" checked="checked">
						<label style="font-size: 14px;float:left">否</label>
						<input type="radio" name="api_status" id="api_status_1" style="width: 35px;float:left" value="1"  >
						<label style="font-size: 14px;float:left">是</label>
					</dd>
				<div  id="div_sp_trone_api"  style="display: none"> <!--API状态相关表单-->
					<br /> <br /> <br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">业务API</dd>
					<dd class="dd04_me">
						<select name="sp_trone_api" id="sel_sp_trone_api" title="选择业务API"
							style="width: 200px">
							<option value="-1">请选择业务API</option>
							<%
								for (SpTroneApiModel spTroneApiModel : spTroneApiList) {
							%>
							<option value="<%=spTroneApiModel.getId()%>"><%=spTroneApiModel.getName()%></option>
							<%
								}
							%>
						</select>
					</dd>
					<br />
					<br />
					<br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">限量类型</dd>
					<dd class="dd03_me">
						<input type="radio" name="limit_type" style="width: 35px;float:left" value="0" checked="checked" >
						<label style="font-size: 14px;float:left">元</label>
						<input type="radio" name="limit_type" style="width: 35px;float:left" value="1" >
						<label style="font-size: 14px;float:left">条数</label>
					</dd>
					<br />
					<br />
					<br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">屏蔽起始时间</dd>
					<dd class="dd03_me">
						<input type="text" name="shield_start" value="<%=spTroneModel.getShieldStart()%>" id="input_shield_start" style="width: 200px" onclick="WdatePicker({dateFmt:'HH:mm',isShowClear:false,readOnly:true})">
					</dd>
					
					<br />
					<br />
					<br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">屏蔽结束时间</dd>
					<dd class="dd03_me">
						<input type="text" name="shield_end" value="<%=spTroneModel.getShieldEnd()%>" id="input_shield_end" style="width: 200px" onclick="WdatePicker({dateFmt:'HH:mm',isShowClear:false,readOnly:true})">
					</dd>
				

					<br /> <br /> <br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">通道日限</dd>
					<dd class="dd03_me">
						<input type="text" name="day_limit"
							value="<%=spTroneModel.getDayLimit()%>" id="input_day_limit"
							style="width: 200px">
					</dd>

					<br /> <br /> <br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">通道月限</dd>
					<dd class="dd03_me">
						<input type="text" name="month_limit"
							value="<%=spTroneModel.getMonthLimit()%>"
							id="input_month_limit" style="width: 200px">
					</dd>

					<br /> <br /> <br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">用户日限</dd>
					<dd class="dd03_me">
						<input type="text" name="user_day_limit"
							value="<%=spTroneModel.getUserDayLimit()%>"
							id="input_user_day_limit" style="width: 200px">
					</dd>

					<br /> <br /> <br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">用户月限</dd>
					<dd class="dd03_me">
						<input type="text" name="user_month_limit"
							value="<%=spTroneModel.getUserMonthLimit()%>"
							id="input_user_month_limit" style="width: 200px">
					</dd>
</div>
					<br /> <br /> <br />
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">省份</dd>
					<div style="margin-left: 95px; width: 580px;" id="pro">

						<%
							for (ProvinceModel province : provinceList) {
						%>
						<dd class="dd01"><%=province.getName()%>
							<input style="" type="checkbox" title="<%=province.getName()%>"
								class="chpro" name="area[]" value="<%=province.getId()%>">
						</dd>
						<%
							}
						%>
						<input type="button" onclick="allCkb('area[]')"
							style="horve: point;" value="全　选" /> <input type="button"
							onclick="unAllCkb()" style="padding-top: 10px;" value="全不选" /> <input
							type="button" onclick="inverseCkb('area[]')"
							style="padding-top: 10px;" value="反　选" /> <input type="button"
							onclick="importProvince()" style="padding-top: 10px;" value="导　入" />

						<input type="button" onclick="exportProvince()"
							style="padding-top: 10px;" value="导　出" />
					</div>
					<br />
					<div style="clear: both;"><br /></div>
					<dd class="dd00_me"></dd>
					<dd class="dd01_me">备注</dd>
					<dd class="dd03_me"></dd>
					&nbsp;
					&nbsp;
					<textarea name="remark"   style="border:solid 1px black;" overflow-y="auto" overflow-x="hidden" maxlength="1000" cols="91" rows="10"  id="remark" ><%=spTroneModel.getRemark()%></textarea>

					<br /> <br /> <br />
					<dd class="dd00"></dd>
					<dd class="dd00_me"></dd>
					<dd class="ddbtn" style="margin-left: 100px; margin-top: 10px">
						<input type="button" value="提 交" onclick="subForm()">
					</dd>
					<dd class="ddbtn" style="margin-left: 32px; margin-top: 10px">
						<input type="button" value="重 置" onclick="resetFormTwo()">
					</dd>
					<dd class="ddbtn" style="margin-left: 32px; margin-top: 10px">
						<input type="button" value="返 回" onclick="history.go(-1)">
					</dd>
					<dd class="ddbtn" style="margin-left: 32px; margin-top: 10px">
						<input type="button" value="通知渠道"  onclick="window.open('../email/troneprovincenotices.jsp?id=<%=spTroneId %>','_self');">
					</dd>
				</form>
			</dl>
		</div>

	</div>
</body>
</html>