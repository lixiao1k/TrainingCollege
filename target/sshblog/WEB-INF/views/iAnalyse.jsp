<%--
  Created by IntelliJ IDEA.
  User: shelton
  Date: 2018/3/10
  Time: 下午10:08
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
    <title>机构信息主页</title>
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
                        <li><a href="/iHome">机构信息<span class="sr-only">(current)</span></a></li>
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
                        <li><a href="/iBillsPage">财务</a></li>
                        <li class="active"><a href="/iAnalyse">分析</a></li>
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

    <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
            <!-- sidebar menu: : style can be found in sidebar.less -->
            <ul class="sidebar-menu" data-widget="tree">
                <li class="header">总数统计</li>
                <li>
                    <a>
                        <i class="fa fa-circle-o text-green"></i> <span>总成交额</span>
                        <span class="pull-right-container">
                            <small class="label pull-right bg-green" id="totalPrice">0</small>
                        </span>
                    </a>
                </li>
                <li>
                    <a>
                        <i class="fa fa-circle-o text-aqua"></i> <span>总学员数</span>
                        <span class="pull-right-container">
                            <small class="label pull-right bg-blue" id="totalStudent">0</small>
                        </span>
                    </a>
                </li>
                <li>
                    <a>
                        <i class="fa fa-circle-o text-orange"></i> <span>学员忠诚度</span>
                        <span class="pull-right-container">
                            <small class="label pull-right bg-orange" id="loyalty">0</small>
                        </span>
                    </a>
                </li>
                <li class="header">分析子项导航</li>
                <li class="treeview" id="li1">
                    <a>
                        <span>订单总数</span>
                    </a>
                    <ul class="treeview-menu">
                        <li id="li1_1"><a><i class="fa fa-circle-o"></i> 年度</a></li>
                        <li id="li1_2"><a><i class="fa fa-circle-o"></i> 季度</a></li>
                        <li id="li1_3"><a><i class="fa fa-circle-o"></i> 月份</a></li>
                    </ul>
                </li>
                <li class="treeview" id="li2">
                    <a>
                        <span>成交总额</span>
                    </a>
                    <ul class="treeview-menu">
                        <li id="li2_1"><a><i class="fa fa-circle-o"></i> 年度</a></li>
                        <li id="li2_2"><a><i class="fa fa-circle-o"></i> 季度</a></li>
                        <li id="li2_3"><a><i class="fa fa-circle-o"></i> 月份</a></li>
                    </ul>
                </li>
                <li class="treeview" id="li3">
                    <a>
                        <span>总成交率</span>
                    </a>
                    <ul class="treeview-menu">
                        <li id="li3_1"><a><i class="fa fa-circle-o"></i> 年度</a></li>
                        <li id="li3_2"><a><i class="fa fa-circle-o"></i> 季度</a></li>
                        <li id="li3_3"><a><i class="fa fa-circle-o"></i> 月份</a></li>
                    </ul>
                </li>
                <li class="treeview" id="li4">
                    <a>
                        <span>购买方式比例</span>
                    </a>
                    <ul class="treeview-menu">
                        <li id="li4_1"><a><i class="fa fa-circle-o"></i> 年度</a></li>
                        <li id="li4_2"><a><i class="fa fa-circle-o"></i> 季度</a></li>
                        <li id="li4_3"><a><i class="fa fa-circle-o"></i> 月份</a></li>
                    </ul>
                </li>
                <li class="" id="li5">
                    <a>
                        <span>销售额增长率</span>
                    </a>
                </li>
                <li class="" id="li6">
                    <a>
                        <span>各类型课程成交金额</span>
                    </a>
                </li>
                <li class="" id="li7">
                    <a>
                        <span>课程数据统计</span>
                    </a>
                </li>
                <li class="" id="li8">
                    <a>
                        <span>教师数据统计</span>
                    </a>
                </li>
                <li class="treeview" id="li9">
                    <a>
                        <span>平均订单单价</span>
                    </a>
                    <ul class="treeview-menu">
                        <li id="li9_1"><a><i class="fa fa-circle-o"></i> 年度</a></li>
                        <li id="li9_2"><a><i class="fa fa-circle-o"></i> 季度</a></li>
                        <li id="li9_3"><a><i class="fa fa-circle-o"></i> 月份</a></li>
                    </ul>
                </li>
            </ul>
        </section>
        <!-- /.sidebar -->
    </aside>

    <!-- Full Width Column -->
    <div class="content-wrapper">
        <div class="col-md-2"></div>
        <div class="col-md-10">
            <div class="col-md-12" style="margin-top: 20px">
                <div id="table_panel">
                </div>
                <div id="charts" style="width: 700px; height: 350px; margin-left: 200px; margin-top: 200px"></div>
            </div>
        </div>
    </div>
</div>
<!-- ./wrapper -->
</body>
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
<script src="<%=basePath%>bootstrap/js/echarts.min.js"></script>
<%--管理信息系统--%>
<script src="<%=basePath%>bootstrap/js/mine/iAnalyse.js"></script>
</html>
