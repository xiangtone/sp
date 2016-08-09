<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<eRedG4:html title="${sysTitle}" showLoading="false" exportParams="true">
<eRedG4:import src="/arm/js/login.js" />
<eRedG4:body>
	<div id="hello-win" class="x-hidden">
		<div id="hello-tabs">
			<img border="0" width="450" height="70" src="<%=request.getAttribute("bannerPath") == null ? request.getContextPath() + "/resource/image/login_banner.png" : request.getAttribute("bannerPath")  %>" />
		  <center>北 京 通 联 天 地 短 信 管 理 平 台</center>
		</div>
	</div>
	<div id="aboutDiv" class="x-hidden"
		style='color: black; padding-left: 10px; padding-top: 10px; font-size: 12px'>
		    北京通联天地短信管理平台
	</div>
	<div id="infoDiv" class="x-hidden"
		style='color: black; padding-left: 10px; padding-top: 10px; font-size: 12px'>
	</div>
</eRedG4:body>
</eRedG4:html>