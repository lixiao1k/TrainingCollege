<%--
  Created by IntelliJ IDEA.
  User: shelton
  Date: 2018/3/11
  Time: 上午12:24
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
    <title>机构信息修改</title>
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/mine/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/mine/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/mine/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/mine/_all-skins.min.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="hold-transition skin-blue layout-top-nav">
<div class="wrapper">
    <header class="main-header">
        <nav class="navbar navbar-static-top">
            <div class="container">
                <div class="navbar-header">
                    <a href="/" class="navbar-brand"><b>Training College</b>首页</a>
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse">
                        <i class="fa fa-bars"></i>
                    </button>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse pull-left" id="navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="/iHome">机构信息</a></li>
                        <li><a href="/iTeachers">师资</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">计划<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="/iCourses">查看</a></li>
                                <li class="divider"></li>
                                <li><a href="/iAddCoursePage">发布</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">订单<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="/iOrders">查看</a></li>
                                <li class="divider"></li>
                                <li><a href="/iPayOfflinePagew">线下支付</a></li>
                            </ul>
                        </li>
                        <li><a href="/iBillsPage">财务</a></li>
                        <li><a href="/iAnalyse">分析</a></li>
                    </ul>
                    <form class="navbar-form navbar-left" role="search">
                        <div class="form-group">
                            <input type="text" class="form-control" id="navbar-search-input" placeholder="Search">
                        </div>
                    </form>
                </div>
                <!-- /.navbar-collapse -->
                <!-- Navbar Right Menu -->
                <div class="navbar-custom-menu">
                    <ul class="nav navbar-nav">
                        <!-- User Account Menu -->
                        <li class="dropdown user user-menu">
                            <!-- Menu Toggle Button -->
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <!-- hidden-xs hides the username on small devices so only the image appears. -->
                                <span class="hidden-xs">${institution.name}</span>
                            </a>
                            <ul class="dropdown-menu">
                                <!-- The user image in the menu -->
                                <li class="user-header">
                                    <p>
                                        ${institution.name}
                                        <small>Best Education!</small>
                                    </p>
                                </li>
                                <!-- Menu Footer-->
                                <li class="user-footer">
                                    <div class="pull-right">
                                        <a href="/iLogOut" class="btn btn-default btn-flat">Sign out</a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
                <!-- /.navbar-custom-menu -->
            </div>
            <!-- /.container-fluid -->
        </nav>
    </header>
    <!-- Full Width Column -->
    <div class="content-wrapper">
        <div class="container">
            <div class="jumbotron" style="margin-top: 100pt; margin-left: 100pt; margin-right: 100pt">
                <h2 style="margin-top: 0pt; margin-left: 0pt" >
                    SignUp 机构信息修改
                </h2>
                <form class="form-horizontal" role="form" style="padding-top: 20pt" action="/iModifyApplication" method="post">
                    <div class="form-group col-md-6" style="padding-right: 20pt; padding-left: 40pt">
                        <label for="name" class="control-label">名称</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="请输入注册机构名称" value=${institution.name}>
                    </div>
                    <div class="form-group col-md-6" style="padding-right: 20pt; padding-left: 40pt">
                        <label for="address" class="control-label">地点</label>
                        <input type="text" class="form-control" id="address" name="address" placeholder="请输入机构所在地址" value=${institution.address}>
                    </div>
                    <div class="form-group col-md-6" style="padding-right: 20pt; padding-left: 40pt;">
                        <label for="briefDescription" class="control-label">简单描述</label>
                        <textarea class="form-control" rows="4" name="description" placeholder="机构简单描述" id="briefDescription">${institution.description}</textarea>
                    </div>
                    <div class="form-group col-md-6" style="padding-right: 20pt; padding-left: 40pt">
                        <label for="phone" class="control-label">电话</label>
                        <input type="text" class="form-control" name="phone" id="phone" placeholder="请输入电话" value=${institution.phone}>
                    </div>
                    <div class="form-group" style="padding-right: 80pt; padding-left: 110pt; padding-top: 20pt">
                        <div style="margin-top: 150pt;margin-left: 350pt">
                            <button type="submit" class="btn btn-primary btn-large">申请修改</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <!-- /.container -->
    </div>
</div>
<!-- jQuery 3 -->
<script src="<%=basePath%>bootstrap/js/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="<%=basePath%>bootstrap/js/bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="<%=basePath%>bootstrap/js/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="<%=basePath%>bootstrap/js/fastclick.js"></script>
<!-- AdminLTE App -->
<script src=".<%=basePath%>bootstrap/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="<%=basePath%>bootstrap/js/demo.js"></script>
</body>
</html>