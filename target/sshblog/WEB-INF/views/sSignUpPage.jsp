<%--
  Created by IntelliJ IDEA.
  User: shelton
  Date: 2018/3/9
  Time: 上午11:41
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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--页面标题-->
    <title>学生注册</title>
    <!--引入bootstrap样式-->
    <link href="<%=basePath%>bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<div class="col-md-12">
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="/">首页</a>
            </div>
        </div>
    </nav>
</div>
<div class="container">
    <div class="row clearfix" style="margin-top: 100pt">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <div class="jumbotron">
                <h2 style="margin-top: -20pt; margin-left: -12pt" >
                    SignUp 学生
                </h2>
                <form class="form-horizontal" role="form" style="padding-top: 20pt" action="/sSignUp" method="post">
                    <div class="form-group">
                        <label for="email" class="control-label">邮箱</label>
                        <input type="text" class="form-control" id="email" name="email" placeholder="请输入注册邮箱">
                    </div>
                    <div class="form-group">
                        <label for="username" class="control-label">昵称</label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="请输入昵称">
                    </div>
                    <div class="form-group">
                        <label for="password" class="control-label">密码</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
                    </div>
                    <div class="form-group">
                        <div class="col-md-12" style="margin-top: 10pt">
                            <button type="submit" class="btn btn-primary btn-large">注册</button>
                        </div>
                    </div>
                </form>
                <a href="/sSignInPage" class="btn btn-sm" role="button" style="margin-left: 120pt">已有账号？登陆</a>
            </div>
        </div>
    </div>
</div>
<!--引入jquery脚本-->
<script src="<%=basePath%>bootstrap/js/jquery-3.1.1.min.js"></script>
<!--引入bootstrap脚本-->
<script src="<%=basePath%>bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
