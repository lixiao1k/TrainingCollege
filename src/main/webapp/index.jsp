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
    <title>网上大学管理系统</title>
    <!--引入bootstrap样式-->
    <link href="<%=basePath%>bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<div class="container">
    <div class="row clearfix" style="margin-top: 100pt">
        <div class="col-md-12 column">
            <div class="jumbotron">
                <h1 style="margin-left: 115pt">
                    Training College System
                </h1>
                <p style="margin-left: 80pt; margin-top: 50pt">
                    The largest online education management platform.You can get everything you are interested in.
                </p>
            </div>
            <div class="col-md-4">
            </div>
            <div class="col-md-4">
                <div class="col-md-4">
                    <a class="btn btn-primary btn-large" href="/sSignInPage">学生</a>
                </div>
                <div class="col-md-4">
                    <a class="btn btn-primary btn-large" href="/iSignInPage">机构</a>
                </div>
                <div class="col-md-4">
                    <a class="btn btn-primary btn-large" href="/mSignInPage">经理</a>
                </div>
            </div>
            <div class="col-md-4">
                <a class="btn btn-primary btn-large" href="/iClassSignPage">测试</a>
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