<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<base href="<%=basePath%>">
	<title>App下载</title>
</head>
<body>

<h1>智慧农场测试版APP下载链接：</h2>
<a href="android-debug.apk" style="font-size:50px;">点击安装</a>
<br>
<br>
<!-- <h2>旧的APP下载链接：</h2>
<a href="SplashActivity.apk">点击安装</a> -->
</body>
</html>