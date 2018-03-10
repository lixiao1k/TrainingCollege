<%--
  Created by IntelliJ IDEA.
  User: shelton
  Date: 2018/3/10
  Time: 下午2:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>AdminLTE 2 | General UI</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/mine/AdminLTE.min.css">
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/bootstrap.min.css">
    <!-- Google Font -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body>
<div class="lockscreen-wrapper">
    <div class="alert alert-danger alert-dismissible">
        <h4><i class="icon fa fa-ban"></i> 注意 Alert!</h4>
        邮箱或者用户名或者密码不能为空！<br>
        Email \ Username \Password can not be empty!
    </div>
</div>
<!--引入jquery脚本-->
<script src="<%=basePath%>bootstrap/js/jquery-3.1.1.min.js"></script>
<!--引入bootstrap脚本-->
<script src="<%=basePath%>bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
