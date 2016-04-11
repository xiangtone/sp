<%@page import="com.system.cache.RightConfigCacheMgr"%>
<%@page import="com.system.model.UserModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>运营管理平台-登录</title>
<script type="text/javascript" src="sysjs/jquery-1.7.js"></script>
<script type="text/javascript" src="sysjs/jquery.md5.js"></script>
<script type="text/javascript">
	function subForm() {
		var username = $("#name").val();
		var userpwd = $("#pwd").val();
		if ("" == username) {
			$("#tipDiv").text("用户名不能为空");
			$("#tipDiv").css("display", "block");
			$("#name").focus();
			return;
		}
		if ("" == userpwd) {
			$("#pwd").focus();
			$("#tipDiv").text("密码不能为空！");
			$("#tipDiv").css("display", "block");
			return;
		}
		var result = ajaxLogin(username, userpwd);
		if (1 == result) {
			$("#pwd").val($.md5(userpwd));
			window.location.href = "index.jsp";
		} 
		else if(2 == result) {
			$("#tipDiv").text("用户没权限，请联系管理员");
			$("#tipDiv").css("display", "block");
		}
		else {
			$("#tipDiv").text("用户名或密码不正确");
			$("#tipDiv").css("display", "block");
		}
	}

	function keypress(e) {
		var currKey = 0, e = e || event;
		if (e.keyCode == 13)
			subForm();
	}

	document.onkeypress = keypress;

	$(function() {
		$("#name").focus();
	});

	function resetMsg() {
		$("#tipDiv").text();
		$("#tipDiv").css("display", "none");
	}

	function ajaxLogin(userName, password) {
		var result = "";
		$.ajax({
			url : "loginaction.jsp",
			data : "username=" + userName + "&pwd=" + password,
			cache : false,
			async : false,
			success : function(html) {
				result = $.trim(html);
			}
		});
		return result;
	}
</script>



<!-- Custom Theme files -->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<!-- Custom Theme files -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords"
	content="Login form web template, Sign up Web Templates, Flat Web Templates, Login signup Responsive web template, Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<!--Google Fonts-->
<link
	href='http://fonts.useso.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700,700italic,900,900italic'
	rel='stylesheet' type='text/css'>
<!--Google Fonts-->
</head>
<body style="background-color: #F7F7F7">
	<!--header start here-->
	<div class="login" style="background-color: #F7F7F7">
		<div class="login-main">
			<div class="login-top">
				<h1 style="color: #A6A6A6">系统登录</h1>
				<input type="text" id="name" placeholder="用户名" required="">
				<input type="password" id="pwd" placeholder="密码" required="">
				<div class="login-bottom">
					<div class="login-check"></div>

				</div>
				<input type="submit"  value="登录" onclick="subForm()"  />
				<div class="clear" style="height: 20px; color: red; display: none"
					id="tipDiv">用户名密码不正确</div>
			</div>

		</div>
	</div>
	<!--header end here-->
	<div style="text-align: center;"></div>
</body>
</html>