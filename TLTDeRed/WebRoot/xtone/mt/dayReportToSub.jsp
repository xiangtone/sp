<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<eRedG4:html title="移动日报到服务器">
<eRedG4:import src="/xtone/mt/js/dayReportToSub.js" />
<eRedG4:body>
	<eRedG4:div key="window1" style="padding:5px">
		<table align="center">
			<tr style="">
				<td><eRedG4:div key="btn1_div"></eRedG4:div></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><eRedG4:div key="btn2_div"></eRedG4:div></td>
			</tr>
			<tr>
				<td colspan='3' align="center"><div id="quartz1"></div></td>
			</tr>
		</table>
	</eRedG4:div>
</eRedG4:body>
</eRedG4:html>