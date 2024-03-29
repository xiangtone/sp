<%@page import="com.system.server.CommRightServer"%>
<%@page import="com.system.server.UserServer"%>
<%@page import="com.system.model.UserModel"%>
<%@page import="com.system.util.ConfigManager"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.system.server.SpTroneServer"%>
<%@page import="com.system.model.SpTroneModel"%>
<%@page import="com.system.server.CityServer"%>
<%@page import="com.system.model.CityModel"%>
<%@page import="com.system.server.ProvinceServer"%>
<%@page import="com.system.model.ProvinceModel"%>
<%@page import="com.system.server.TroneOrderServer"%>
<%@page import="com.system.model.TroneOrderModel"%>
<%@page import="com.system.server.TroneServer"%>
<%@page import="com.system.model.TroneModel"%>
<%@page import="com.system.model.CpModel"%>
<%@page import="com.system.server.CpServer"%>
<%@page import="com.system.server.SpServer"%>
<%@page import="com.system.model.SpModel"%>
<%@page import="com.system.server.MrServer"%>
<%@page import="com.system.model.MrReportModel"%>
<%@page import="com.system.constant.Constant"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.system.util.PageUtil"%>
<%@page import="java.util.Map"%>
<%@page import="com.system.model.xy.XyUserModel"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="com.system.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int userId = ((UserModel)session.getAttribute("user")).getId();
	String userName=((UserModel)session.getAttribute("user")).getNickName();
	String defaultStartDate = StringUtil.getMonthHeadDate();
	String defaultEndDate = StringUtil.getMonthEndDate();
	
	String startDate = StringUtil
			.getString(request.getParameter("startdate"), defaultStartDate);
	String endDate = StringUtil
			.getString(request.getParameter("enddate"), defaultEndDate);
	
	int sortType = StringUtil.getInteger(request.getParameter("sort_type"), 1);
	
	int spId = StringUtil.getInteger(request.getParameter("sp_id"), -1);
	int cpId = StringUtil.getInteger(request.getParameter("cp_id"), -1);
	int spTroneId = StringUtil.getInteger(request.getParameter("sp_trone"), -1);
	int troneId = StringUtil.getInteger(request.getParameter("trone"), -1);
	int troneOrderId = StringUtil.getInteger(request.getParameter("trone_order"), -1);
	int provinceId = StringUtil.getInteger(request.getParameter("province"), -1);
	int cityId = StringUtil.getInteger(request.getParameter("city"), -1);
	int operatorId = StringUtil.getInteger(request.getParameter("operator"), -1);
	int dataType = StringUtil.getInteger(request.getParameter("data_type"), -1);
	int spCommerceUserId = StringUtil.getInteger(request.getParameter("commerce_user"), userId);
	int cpCommerceUserId = StringUtil.getInteger(request.getParameter("cp_commerce_user"), -1);
	
	int isUnHoldData = StringUtil.getInteger(request.getParameter("is_unhold_data"), -1);
	
	int spCommerceId = StringUtil.getInteger(ConfigManager.getConfigData("SP_COMMERCE_GROUP_ID"),-1);
	List<UserModel> userList = new UserServer().loadUserByGroupId(spCommerceId);
	
	int cpCommerceId = StringUtil.getInteger(ConfigManager.getConfigData("CP_COMMERCE_GROUP_ID"),-1);
	List<UserModel> cpCommerceUserList = new UserServer().loadUserByGroupId(cpCommerceId);
	
	boolean isFirstLoad = StringUtil.getInteger(request.getParameter("isfirstload"), -1) == -1 ? true : false;

	//查询SP权限，0表示SP商务
	String userRightList = new CommRightServer().getRightListByUserId(userId, 0);
	
	String[] rightStrs = null;
	
	if(userRightList == null || "".equals(userRightList))
	{
		userRightList = userId + "";
	}
	else
	{
		rightStrs = userRightList.split(",");
	}
	
	//把用户列表再次展示出来
	if(rightStrs!=null)
	{
		List<UserModel> tmpUserList = new ArrayList<UserModel>();
		
		for(String sId : rightStrs)
		{
			for(UserModel tmpModel : userList)
			{
				if(sId.equalsIgnoreCase(tmpModel.getId() + ""))
				{
					tmpUserList.add(tmpModel);
					break;
				}
			}
		}
		
		userList = tmpUserList;
	}
	
	//如果是公司或是商务就不是全部
	if(spCommerceUserId>0 || isFirstLoad)
	{
		userRightList = spCommerceUserId + "";
	}
	
	
	Map<String, Object> map =  new MrServer().getMrLrData(startDate,endDate, spId,spTroneId, troneId, cpId, troneOrderId, provinceId, cityId,operatorId,dataType,userRightList,cpCommerceUserId+"",isUnHoldData,sortType);
	
	List<SpModel> spList = new SpServer().loadSp();
	List<CpModel> cpList = new CpServer().loadCp();
	List<TroneModel> troneList = new ArrayList<TroneModel>(); //new TroneServer().loadTroneList();
	//List<TroneOrderModel> troneOrderList = new TroneOrderServer().loadTroneOrderList();
	
	List<TroneOrderModel> troneOrderList = new ArrayList();
	
	List<ProvinceModel> provinceList = new ProvinceServer().loadProvince();
	List<CityModel> cityList = new CityServer().loadCityList();
	List<SpTroneModel> spTroneList = new SpTroneServer().loadSpTroneList();
		
	List<MrReportModel> list = (List<MrReportModel>)map.get("list");
	
	int dataRows = (Integer)map.get("datarows");
	int showDataRows = (Integer)map.get("showdatarows");
	double amount = (Double)map.get("amount");
	double showAmount = (Double)map.get("showamount");
	double spAmount = (Double)map.get("spamount");
	double cpAmount = (Double)map.get("cpamount");
	
	String[] titles = {"日期","周数","月份","SP","CP","通道","CP业务","省份","城市","SP业务","时间","SP商务","CP商务","运营商", "数据类型", "第一业务线", "第二业务线"};
	
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>运营管理平台</title>
<link href="../wel_data/right.css" rel="stylesheet" type="text/css">
<link href="../wel_data/gray.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../sysjs/jquery-1.7.js"></script>
<script type="text/javascript" src="../sysjs/base.js"></script>
<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../sysjs/MapUtil.js"></script>
<script type="text/javascript" src="../sysjs/pinyin.js"></script>
<script type="text/javascript" src="../sysjs/AndyNamePickerV20.js"></script><link href="../css/namepicker.css" rel="stylesheet" type="text/css">
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
	
	var cpList = new Array();
	<%
	for(CpModel cpModel : cpList)
	{
		%>
		cpList.push(new joSelOption(<%= cpModel.getId() %>,1,'<%= cpModel.getShortName() %>'));
		<%
	}
	%>
	
	function onSpDataSelect(joData)
	{
		if(joData.id==-1)
			$("#input_sp").val("");
		else
			$("#input_sp").val(joData.text);
		
		$("#sel_sp").val(joData.id);
		troneChange();
	}
	
	function onCpDataSelect(joData)
	{
		if(joData.id==-1)
			$("#input_cp").val("");
		else
			$("#input_cp").val(joData.text);
		
		$("#sel_cp").val(joData.id);
		troneOrderChange();
	}

	function joCity(id,provinceId,name)
	{
		var obj = {};
		obj.id = id;
		obj.provinceId = provinceId;
		obj.name = name;
		return obj;
	}
	
	function joTrone(id,spId,troneName)
	{
		var obj = {};
		obj.id = id;
		obj.spId = spId;
		obj.troneName = troneName;
		return obj;
	}
	
	function joTroneOrder(id,cpId,troneOrderName)
	{
		var obj = {};
		obj.id = id;
		obj.cpId = cpId;
		obj.troneOrderName = troneOrderName;
		return obj;
	}

	var cityList = new Array();
	<%for(CityModel city : cityList){%>
	cityList.push(new joCity(<%= city.getId() %>,<%= city.getProvinceId() %>,'<%= city.getName() %>'));<%}%>
		
	var troneList = new Array();
	<%for(TroneModel trone : troneList){%>
	troneList.push(new joTrone(<%= trone.getId() %>,<%= trone.getSpId() %>,'<%= trone.getSpShortName() + "-" +trone.getTroneName() %>'));<%}%>
	
	var troneOrderList = new Array();
	<%for(TroneOrderModel troneOrder : troneOrderList){%>
	troneOrderList.push(new joTroneOrder(<%= troneOrder.getId() %>,<%= troneOrder.getCpId() %>,'<%= troneOrder.getCpShortName() + "-" +troneOrder.getOrderTroneName() %>'));<%}%>
	
	var spTroneArray = new Array();
	<%
	for(SpTroneModel spTroneModel : spTroneList)
	{
		%>
	spTroneArray.push(new joBaseObject(<%= spTroneModel.getId() %>,<%=spTroneModel.getSpId() %>,'<%= spTroneModel.getSpTroneName() %>'));	
		<%
	}
	%>
	
	$(function()
	{
		$("#sel_sort_type").val(<%= sortType %>);
		
		//SP的二级联动
		$("#sel_sp").val(<%= spId %>);
		
		<%
		if(spId>0)
		{
			%>
		$("#input_sp").val($("#sel_sp").find("option:selected").text());
			<%
		}
		%>
		
		$("#sel_sp").change(troneChange);
		troneChange();
		$("#sel_sp_trone").val(<%= spTroneId %>);
		$("#sel_trone").val(<%= troneId %>);
		
		//CP的二级联动
		$("#sel_cp").val(<%= cpId %>);	
		
		<%
		if(cpId>0)
		{
			%>
		$("#input_cp").val($("#sel_cp").find("option:selected").text());
			<%
		}
		%>
		
		$("#sel_cp").change(troneOrderChange);
		troneOrderChange();
		$("#sel_trone_order").val(<%= troneOrderId %>);
		
		//省份的二级联动
		$("#sel_province").val(<%= provinceId %>);
		$("#sel_province").change(provinceChange);
		provinceChange();		
		$("#sel_city").val(<%= cityId %>);
		
		$("#sel_operator").val(<%= operatorId %>);
		$("#sel_data_type").val(<%= dataType %>);
		$("#sel_commerce_user").val(<%= spCommerceUserId %>);
		$("#sel_cp_commerce_user").val(<%= cpCommerceUserId %>);
		
		$("#sel_is_unhold_data").val(<%= isUnHoldData %>);
		
	});
	
	
	var npSpTroneArray = new Array();
	
	<%
	for(SpTroneModel spTroneModel : spTroneList)
	{
		%>
		npSpTroneArray.push(new joSelOption(<%= spTroneModel.getId() %>,<%=spTroneModel.getSpId() %>,'<%= spTroneModel.getSpTroneName() %>'));	
		<%
	}
	%>
	
	function npSpTroneChange(jodata)
	{
		$("#sel_sp_trone").val(jodata.id);
	}
	
	function troneChange()
	{
		var spId = $("#sel_sp").val();
		
		$("#sel_sp_trone").empty(); 
		$("#sel_sp_trone").append("<option value='-1'>全部</option>");
		for(i=0; i<spTroneArray.length; i++)
		{
			if(spTroneArray[i].pid==spId || spId=="-1")
			{
				$("#sel_sp_trone").append("<option value='" + spTroneArray[i].id + "'>" + spTroneArray[i].name + "</option>");
			}
		}
		
		$("#sel_trone").empty(); 
		$("#sel_trone").append("<option value='-1'>全部</option>");
		for(i=0; i<troneList.length; i++)
		{
			if(troneList[i].spId==spId || spId=="-1")
			{
				$("#sel_trone").append("<option value='" + troneList[i].id + "'>" + troneList[i].troneName + "</option>");
			}
		}
	}
	
	function troneOrderChange()
	{
		var cpId = $("#sel_cp").val();
		$("#sel_trone_order").empty(); 
		$("#sel_trone_order").append("<option value='-1'>全部</option>");
		for(i=0; i<troneOrderList.length; i++)
		{
			if(troneOrderList[i].cpId==cpId || cpId=="-1")
			{
				$("#sel_trone_order").append("<option value='" + troneOrderList[i].id + "'>" + troneOrderList[i].troneOrderName + "</option>");
			}
		}
	}
	
	function provinceChange()
	{
		var provinceId = $("#sel_province").val();
		$("#sel_city").empty(); 
		$("#sel_city").append("<option value='-1'>全部</option>");
		for(i=0; i<cityList.length; i++)
		{
			if(cityList[i].provinceId==provinceId || provinceId=="-1")
			{
				$("#sel_city").append("<option value='" + cityList[i].id + "'>" + cityList[i].name + "</option>");
			}
		}
	}
	
	
</script>
<body>
	<div class="main_content">
		<div class="content" >
			<form action="sp_wx_person_mr1_lr.jsp"  method="get" style="margin-top: 10px">
				<input type="hidden" name="isfirstload" value="1" />
				<dl>
					<dd class="dd01_me">开始日期</dd>
					<dd class="dd03_me">
						<input name="startdate"  type="text" value="<%=startDate%>" 
							onclick="WdatePicker({isShowClear:false,readOnly:true})" style="width: 100px;">
					</dd>
					<dd class="dd01_me">结束日期</dd>
					<dd class="dd03_me">
						<input name="enddate" type="text" value="<%=endDate%>" 
							onclick="WdatePicker({isShowClear:false,readOnly:true})" style="width: 100px;">
					</dd>
					<dd class="dd01_me">SP</dd>
					<dd class="dd03_me">
						<input  type="text" id="input_sp" onclick="namePicker(this,spList,onSpDataSelect)" style="width: 100px;" readonly="readonly" >
						<select name="sp_id" id="sel_sp" style="width: 110px;display: none" >
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
						<select name="sp_trone" id="sel_sp_trone" style="width: 110px;" ></select>
					</dd>
					<dd class="dd01_me">SP通道</dd>
						<dd class="dd04_me">
						<select name="trone" id="sel_trone" title="请选择通道" style="width: 110px;"></select>
					</dd>
					<dd class="dd01_me">数据类型</dd>
						<dd class="dd04_me">
						<select name="data_type" id="sel_data_type" style="width: 100px;">
							<option value="-1">全部</option>
							<option value="0">普通</option>
							<option value="1">隔天</option>
							<option value="2">IVR</option>
						</select>
					</dd>
					<dd class="dd01_me">导量类型</dd>
					<dd class="dd04_me">
						<select name="is_unhold_data" id="sel_is_unhold_data" style="width: 100px;">
							<option value="-1">全部</option>
							<option value="1">导量</option>
							<option value="0">非导量</option>
						</select>
					</dd>
					<br /><br /><br />
					<dd class="dd01_me">CP</dd>
					<dd class="dd03_me">
						<input  type="text" id="input_cp" onclick="namePicker(this,cpList,onCpDataSelect)" style="width: 100px;" readonly="readonly" >
						<select name="cp_id" id="sel_cp" title="选择CP" style="width: 110px;display: none;">
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
					<!--  
					<dd>
						<dd class="dd01_me">业务</dd>
						<dd class="dd04_me">
						<select name="trone_order" id="sel_trone_order" title="请选择业务" style="width: 110px;"></select>
					</dd>
					-->
					<!-- 暂时先隐藏 -->
					<!--  
					<dd>
						<dd class="dd01_me">省份</dd>
						<dd class="dd04_me">
						<select name="province" id="sel_province" title="请选择省份">
							<option value="-1">全部</option>
							<%
							for(ProvinceModel province : provinceList)
							{
								%>
							<option value="<%= province.getId() %>"><%= province.getName() %></option>	
								<%
							}
							%>
						</select>
					</dd>
					<dd>
						<dd class="dd01_me">城市</dd>
						<dd class="dd04_me">
						<select name="city" id="sel_city" title="请选择城市">
							
						</select>
					</dd>
					-->
					<dd class="dd01_me">运营商</dd>
						<dd class="dd04_me">
						<select name="operator" id="sel_operator" style="width: 100px;">
							<option value="-1">全部</option>
							<option value="3">移动</option>
							<option value="1">联通</option>
							<option value="2">电信</option>
						</select>
					</dd>
					<!--
					<dd class="dd01_me">SP商务</dd>
						<dd class="dd04_me">
						<select name="commerce_user" id="sel_commerce_user" style="width: 100px;">
							<option value="-1">全部</option>
							<%
							for(UserModel commerceUser : userList)
							{
								%>
							<option value="<%= commerceUser.getId() %>"><%= commerceUser.getNickName() %></option>
								<%
							}
							%>
						</select>
					</dd>
					<dd class="dd01_me">CP商务</dd>
						<dd class="dd04_me">
						<select name="cp_commerce_user" id="sel_cp_commerce_user" style="width: 100px;">
							<option value="-1">全部</option>
							<%
							for(UserModel commerceUser : cpCommerceUserList)
							{
								%>
							<option value="<%= commerceUser.getId() %>"><%= commerceUser.getNickName() %></option>
								<%
							}
							%>
						</select>
					</dd>
					-->
					<dd class="dd01_me" style="font-weight: bold;font-size: 14px">展示方式</dd>
					<dd class="dd04_me">
						<select name="sort_type" id="sel_sort_type" title="展示方式" style="width: 110px;" >
							<option value="1">日期</option>
							<option value="2">周数</option>
							<option value="3">月份</option>
							<option value="4">SP</option>
							<option value="10">SP业务</option>
							<option value="6">SP通道</option>
							<option value="5">CP</option>
							<option value="7">CP业务</option>
							<!-- 暂时先隐藏 -->
							<option value="8">省份</option>
							<option value="9">城市</option>
							<!-- <option value="11">按小时</option> -->
							<!--
							<option value="12">SP商务</option>
							<option value="13">CP商务</option>
							-->
							<option value="14">运营商</option>
							<option value="15">数据类型</option>
							<option value="16">第一业务线</option>
							<option value="17">第二业务线</option>
							
						</select>
					</dd>
					<dd class="ddbtn" style="margin-left: 10px; margin-top: 0px;">
						<input class="btn_match" name="search" value="查 询" type="submit" />
					</dd>
					<dd class="dd01_me"><a style="color:blue;" href="sp_person_mr1.jsp?<%= request.getQueryString() %>">返回</a></dd>
					</dl>
			</form>
		</div>
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td>序号</td>
					<td><%= titles[sortType-1] %></td>
					<td>数据量(条)</td>
					<td>金额(元)</td>
					<td>失败量(条)</td>
					<td>推送量(条)</td>
					<td>失败金额(元 )</td>
					<td>推送金额(元)</td>
					<td>失败率</td>
					<td>预收入(元)</td>
					<td>预结算(元)</td>
					<td>利润(元)</td>
					<td>利润率</td>
				</tr>
			</thead>
			<tbody>		
				<%
					int index = 1;
					for(MrReportModel model : list)
					{
						%>
				<tr>
					<td><%= index++ %></td>
					<td><%= model.getTitle1() %></td>
					<td><%= model.getDataRows() %></td>
					<td><%= StringUtil.getDecimalFormat(model.getAmount()) %></td>
					<td><%= model.getDataRows() - model.getShowDataRows()  %></td>
					<td><%= model.getShowDataRows() %></td>
					
					<td><%= StringUtil.getDecimalFormat(model.getAmount() - model.getShowAmount()) %></td>
					<td><%= StringUtil.getDecimalFormat(model.getShowAmount()) %></td>
					<td><%= StringUtil.getPercent(model.getDataRows() - model.getShowDataRows(), model.getDataRows()) %></td>
					<td><%= StringUtil.getDecimalFormat(model.getSpMoney()) %></td>
					<td><%= StringUtil.getDecimalFormat(model.getCpMoney()) %></td>
					<td><%= StringUtil.getDecimalFormat(model.getSpMoney() - model.getCpMoney()) %></td>
					<td><%= StringUtil.getPercent(model.getSpMoney() - model.getCpMoney(), model.getAmount()) %></td>
				</tr>
						<%
					}
				%>
			<tbody>
				<tr>
					<td></td>
					<td></td>
					<td>总数据量(条)：<%= dataRows %></td>
					<td>总金额(元)：<%= StringUtil.getDecimalFormat(amount) %></td>
					<td>总失败量(条)：<%= dataRows - showDataRows  %> </td>
					<td>总推送量(条)：<%= showDataRows %></td>
					
					<td>总失败金额(元 )：<%= StringUtil.getDecimalFormat(amount - showAmount) %></td>
					<td>总推送金额(元)：<%= StringUtil.getDecimalFormat(showAmount) %></td>
					<td>总失败率：<%= StringUtil.getPercent(dataRows - showDataRows, dataRows) %></td>
					<td>总预收入(元):<%= StringUtil.getDecimalFormat(spAmount) %></td>
					<td>总预结算(元):<%= StringUtil.getDecimalFormat(cpAmount) %></td>
					<td>总预利润(元):<%= StringUtil.getDecimalFormat(spAmount-cpAmount) %></td>
					<td>利润率:<%= StringUtil.getPercent(spAmount-cpAmount,amount) %></td>
				</tr>
			</tbody>
		</table>
	</div>
	
</body>
</html>