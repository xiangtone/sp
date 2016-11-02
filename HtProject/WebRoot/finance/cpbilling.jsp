<%@page import="jxl.common.BaseUnit"%>
<%@page import="com.system.server.CpServer"%>
<%@page import="com.system.model.CpModel"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.system.model.SettleAccountModel"%>
<%@page import="com.system.server.SettleAccountServer"%>
<%@page import="com.system.server.JsTypeServer"%>
<%@page import="com.system.model.JsTypeModel"%>
<%@page import="com.system.model.CpBillingModel"%>
<%@page import="com.system.server.CpBillingServer"%>
<%@page import="com.system.util.Base64UTF"%>
<%@page import="com.system.constant.Constant"%>
<%@page import="com.system.util.PageUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.system.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

	int cpBillingId = StringUtil.getInteger(request.getParameter("cpbillingid"), -1);

	int type = StringUtil.getInteger(request.getParameter("type"), -1);
	
	CpBillingServer server = new CpBillingServer();
	
	int status = StringUtil.getInteger(request.getParameter("status"), -1);
	
	//导出EXCEL数据
	if(cpBillingId>0 && type ==1)
	{
		List<SettleAccountModel> list = server.exportExcelData(cpBillingId);
		
		CpBillingModel cpBillingModel = server.getCpBillingModel(cpBillingId);
		
		response.setContentType("application/octet-stream;charset=utf-8");
		
		String fileName = cpBillingModel.getCpName() + "(" + cpBillingModel.getStartDate() + "_" + cpBillingModel.getEndDate() + ").xls";
		
		if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) 
		{
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} 
		else 
		{
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		}

		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

		SettleAccountServer accountServer = new SettleAccountServer();
		
		accountServer.exportSettleAccount(2, cpBillingModel.getJsType(), cpBillingModel.getCpName(), cpBillingModel.getStartDate() , cpBillingModel.getEndDate(), list,
				response.getOutputStream());
		
		out.clear();
		
		out = pageContext.pushBody();
		
		return;
	}
	//重新生成指定帐单的数据
	else if(type==2 && cpBillingId > 0)
	{
		server.reExportCpBillint(cpBillingId);
	}
	//删除帐单数据
	else if(type==3 && cpBillingId > 0)
	{
		server.delCpBilling(cpBillingId);
	}
	//确定对帐单无误，更新对帐单状态
	else  if(type==4 && cpBillingId > 0)
	{
		//把帐单更新为待CP审核
		server.updateCpBillingStatus(cpBillingId, 1);
	}
	//帮CP确认帐单无误，更新对帐单状态
	else  if(type==5 && cpBillingId > 0)
	{
		//把帐单更新为CP已审核
		server.updateCpBillingStatus(cpBillingId, 2);
	}
	//撤回待CP审核的帐单
	else if(type==6 && cpBillingId > 0)
	{
		server.updateCpBillingStatus(cpBillingId, 0);
	}

	int pageIndex = StringUtil.getInteger(request.getParameter("pageindex"), 1);

	String query = Base64UTF.encode(request.getQueryString());

	int cpId = StringUtil.getInteger(request.getParameter("cp_id"), -1);
	
	int jsType = StringUtil.getInteger(request.getParameter("js_type"), -1);
	
	String startDate = StringUtil.getString(request.getParameter("startdate"), "");
	
	String endDate = StringUtil.getString(request.getParameter("enddate"), "");

	Map<String, Object> map =  server.loadCpBilling(startDate, endDate, cpId, jsType,status,pageIndex);
		
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
	params.put("status",""+status);
	
	String pageData = PageUtil.initPageQuery("cpbilling.jsp",params,rowCount,pageIndex);
	
	String[] statusData = {"待审核","CP对帐中","CP已对帐","已付款"};
	
	String[] btnStrings = {"<a href='#' onclick='sendCpBillingToCp(helloisthereany)'>审核</a> <a href='#' onclick='delCpBilling(helloisthereany)'>删除</a> <a href='#' onclick='reExportCpBilling(helloisthereany)'>重新生成</a>",
			"<a href='#' onclick='confirmCpBillingForCp(helloisthereany)'>CP审核</a> <a href='#' onclick='reCallCpBillingFromCpStatus(helloisthereany)'>撤回</a>","",""};
	
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

	//删除帐单
	function delCpBilling(id)
	{
		if(confirm('真的要删除帐单吗？删除后可以在CP数据下重新生成帐单'))
		{
			window.location.href = "cpbilling.jsp?type=3&cpbillingid=" + id + "&<%= Base64UTF.decode(query) %>";	
		}
	}
	
	//重新生成帐单
	function reExportCpBilling(id)
	{
		if(confirm("确定是否重新生成帐单？"))
		{
			window.location.href = "cpbilling.jsp?type=2&cpbillingid=" + id + "&<%= Base64UTF.decode(query) %>";
		}
	}
	
	//审核帐单
	function sendCpBillingToCp(id)
	{
		if(confirm("是否确认数据无误？审核后会进入CP审核!"))
		{
			window.location.href = "cpbilling.jsp?type=4&cpbillingid=" + id + "&<%= Base64UTF.decode(query) %>";
		}
	}
	
	//替CP审核帐单
	function confirmCpBillingForCp(id)
	{
		if(confirm("CP是否已确认数据正确？审核后将会进入财务对帐流程！"))
		{
			window.location.href = "cpbilling.jsp?type=5&cpbillingid=" + id + "&<%= Base64UTF.decode(query) %>";
		}
	}
	
	//召回已发送给CP的帐单
	function reCallCpBillingFromCpStatus(id)
	{
		if(confirm("确定是否要撤回该CP帐单？"))
		{
			window.location.href = "cpbilling.jsp?type=6&cpbillingid=" + id + "&<%= Base64UTF.decode(query) %>";
		}
	}
	
	$(function()
	{
		$("#sel_js_type").val(<%= jsType %>);
		$("#sel_cp_id").val(<%= cpId %>);
		$("#sel_status").val(<%= status %>);
	});
	
</script>

<body style="padding-top: 40px">
	<div class="main_content">
		<div class="content" style="position: fixed; left: 0px; right: 0px">
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
					<dd class="dd01_me">状态</dd>
					<dd class="dd04_me">
						<select name="status" id="sel_status" >
						<option value="-1">请选择</option>
						<%
						for(int i=0; i<statusData.length; i++)
						{
							%>
							<option value="<%= i %>"><%= statusData[i] %></option>
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
					<td>信息费</td>
					<td title="信息费*结算率">预支付</td>
					<td title="从结算款里核减掉的钱">核减款</td>
					<td title="预支付-核减款">实际预支付</td>
					<td title="财务最终支付给渠道的钱">财务支付</td>
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
					<td><%= model.getAmount() %></td>
					<td><%= model.getPreBilling() %></td>
					<td><%= model.getReduceAmount() %></td>
					<td><%= model.getPreBilling() - model.getReduceAmount() %></td>
					<td><%= model.getActureBilling() %></td>
					<td><%=model.getRemark() %></td>
					<td><%= model.getCreateDate() %></td>
					<td><%= statusData[model.getStatus()] %></td>
					<td style="text-align: left">
						<a href="cpbillingdetail.jsp?query=<%= query %>&cpbillingid=<%= model.getId() %>" >详细</a>
						<%= btnStrings[model.getStatus()].replaceAll("helloisthereany", "" + model.getId()) %>
						<a href="cpbilling.jsp?type=1&cpbillingid=<%= model.getId() %>">导出</a>
					</td>
				</tr>
				<%
					}
				%>
				
			<tbody>
				<tr>
					<td colspan="100" class="tfooter" style="text-align: center;"><%= pageData %></td>
				</tr>
			</tbody>
		</table>
	</div>
	
</body>
</html>