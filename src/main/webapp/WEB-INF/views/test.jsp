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
    <title>Title</title>
    <!--页面Logo-->
    <link rel="shortcut icon" href="<%=basePath%>images/voicecyber.ico"/>
    <!--引入bootstrap样式-->
    <link href="<%=basePath%>bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<p>
    <button type="button" class="btn btn-primary btn-lg">学生</button>
    <button type="button" class="btn btn-default btn-lg">机构</button>
    <button type="button" class="btn btn-default btn-lg">经理</button>
</p>
<p>${student.email}</p>

<!--引入jquery脚本-->
<script src="<%=basePath%>bootstrap/js/jquery-3.1.1.min.js" type="text/javascript"></script>
<!--引入bootstrap脚本-->
<script src="<%=basePath%>bootstrap/js/bootstrap.js" type="text/javascript"></script>
</body>
</html>