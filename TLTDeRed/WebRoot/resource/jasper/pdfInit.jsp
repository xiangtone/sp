<%@ page contentType="text/html; charset=gbk"%>
<%@ include file="/common/include/taglib.jsp"%>
<eRedG4:html title="eRedG4.Report:���׵������������">
<eRedG4:body>
</eRedG4:body>
<script language="JavaScript">
window.onload = function(){
    window.location.href = '<%=request.getAttribute("dataUrl")%>';
}
</script>

</eRedG4:html>