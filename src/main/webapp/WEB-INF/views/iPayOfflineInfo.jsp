<%--
  Created by IntelliJ IDEA.
  User: shelton
  Date: 2018/3/24
  Time: 下午11:19
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
    <title>线下支付</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
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

    <!-- Google Font -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
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
                                <li><a href="/iPayOfflinePage">线下支付</a></li>
                            </ul>
                        </li>
                        <li><a>财务</a></li>
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
                                        <a href="/" class="btn btn-default btn-flat">Sign out</a>
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
            <div class="col-md-6" style="margin-top: 100pt; margin-left: 230pt">
                <div class="box box-solid">
                    <div class="box-header with-border">
                        <i class="fa fa-text-width"></i>

                        <h3 class="box-title">支付信息预览</h3>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <dl class="dl-horizontal">
                            <dt>订单号</dt>
                            <dd>${Order.id}</dd>
                            <dt>订单金额</dt>
                            <dd>${Order.price}</dd>
                            <dt>下单时间</dt>
                            <dd>${Order.createTime}</dd>
                        </dl>
                        <div class="box-footer">
                            <a href="/sPayCancel" class="btn btn-default">取消</a>
                            <a href="/sPay/${Order.id}" class="btn btn-info pull-right">确定支付</a>
                        </div>
                    </div>
                    <!-- /.box-body -->
                </div>
                <!-- /.box -->
            </div>
        </div>
        <!-- /.container -->
    </div>
</div>
<!-- ./wrapper -->

<!-- jQuery 3 -->
<script src="<%=basePath%>bootstrap/js/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="<%=basePath%>bootstrap/js/bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="<%=basePath%>bootstrap/js/mine/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="<%=basePath%>bootstrap/js/mine/fastclick.js"></script>
<!-- AdminLTE App -->
<script src=".<%=basePath%>bootstrap/js/mine/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="<%=basePath%>bootstrap/js/mine/demo.js"></script>
</body>
</html>
