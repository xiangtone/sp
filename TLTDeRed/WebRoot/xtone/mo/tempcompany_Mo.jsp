<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<eRedG4:html title="JDBC执行监控" uxEnabled="true">
<eRedG4:import src="/xtone/mo/js/tempcompany_Mo.js"/>
<!-- 可以选择grid中的数据复制 -->
<eRedG4:import src="/xtone/utiljs/selectGrid.js"/>
<style type= "text/css" > 
.x-selectable, .x-selectable * { 
-moz-user-select: text! important ; 
-khtml-user-select: text! important ; 
} 
</style>
<eRedG4:body>
<eRedG4:div key="companyMoDiv"></eRedG4:div>
</eRedG4:body>
</eRedG4:html>