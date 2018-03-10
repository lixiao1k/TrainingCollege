<%--
  Created by IntelliJ IDEA.
  User: shelton
  Date: 2018/3/10
  Time: 上午12:12
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
    <title>机构登陆</title>
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
                    SignIn 机构
                </h2>
                <form class="form-horizontal" role="form" style="padding-top: 20pt">
                    <div class="form-group">
                        <label for="code" class="control-label">编码</label>
                        <input type="text" class="form-control" id="code" placeholder="请输入机构编码">
                    </div>
                    <div class="form-group">
                        <label for="password" class="control-label">密码</label>
                        <input type="password" class="form-control" id="password" placeholder="请输入密码">
                    </div>
                    <div class="form-group">
                        <div class="col-md-12" style="margin-top: 10pt">
                            <button type="submit" class="btn btn-primary btn-large">登录</button>
                        </div>
                    </div>
                </form>
                <a href="/iSignUp" class="btn btn-sm" role="button" style="margin-left: 150pt">注册</a>
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
