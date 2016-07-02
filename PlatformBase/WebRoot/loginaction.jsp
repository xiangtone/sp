<%@page import="com.system.server.RightServer"%>
<%@page import="com.system.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int loginType = StringUtil.getInteger(request.getParameter("type"), 1);
	if(loginType==1)
	{
		String userName = StringUtil.getString(request.getParameter("username"), "");
		String password = StringUtil.getString(request.getParameter("pwd"), "");
		int loginStatus = RightServer.login(session, userName, password);
		out.clear();
		out.print(loginStatus);
	}
	else if(loginType==-1)
	{
		session.setAttribute("user", null);
		out.print("<script>window.location.href='login.jsp'</script>");
	}
%>