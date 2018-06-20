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
                    <a href="pages/widgets.html">
                        <i class="fa fa-circle-o text-green"></i> <span>总成交额</span>
                        <span class="pull-right-container">
                            <small class="label pull-right bg-green">51980</small>
                        </span>
                    </a>
                </li>
                <li>
                    <a href="pages/widgets.html">
                        <i class="fa fa-circle-o text-aqua"></i> <span>总学员数</span>
                        <span class="pull-right-container">
                            <small class="label pull-right bg-blue">890</small>
                        </span>
                    </a>
                </li>
                <li class="header">分析子项导航</li>
                <li class="treeview" id="li1">
                    <a>
                        <span>订单总数</span>
                    </a>
                    <ul class="treeview-menu">
                        <li><a href="index.html"><i class="fa fa-circle-o"></i> 年度</a></li>
                        <li><a href="index2.html"><i class="fa fa-circle-o"></i> 季度</a></li>
                        <li><a href="index2.html"><i class="fa fa-circle-o"></i> 月份</a></li>
                    </ul>
                </li>
                <li class="treeview" id="li2">
                    <a>
                        <span>成交总额</span>
                    </a>
                    <ul class="treeview-menu">
                        <li><a href="index.html"><i class="fa fa-circle-o"></i> 年度</a></li>
                        <li><a href="index2.html"><i class="fa fa-circle-o"></i> 季度</a></li>
                        <li><a href="index2.html"><i class="fa fa-circle-o"></i> 月份</a></li>
                    </ul>
                </li>
                <li class="treeview" id="li3">
                    <a>
                        <span>总成交率</span>
                    </a>
                    <ul class="treeview-menu">
                        <li><a href="index.html"><i class="fa fa-circle-o"></i> 年度</a></li>
                        <li><a href="index2.html"><i class="fa fa-circle-o"></i> 季度</a></li>
                        <li><a href="index2.html"><i class="fa fa-circle-o"></i> 月份</a></li>
                    </ul>
                </li>
                <li class="treeview" id="li4">
                    <a>
                        <span>学员忠诚度</span>
                    </a>
                    <ul class="treeview-menu">
                        <li><a href="index.html"><i class="fa fa-circle-o"></i> 年度</a></li>
                        <li><a href="index2.html"><i class="fa fa-circle-o"></i> 季度</a></li>
                        <li><a href="index2.html"><i class="fa fa-circle-o"></i> 月份</a></li>
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
            </ul>
        </section>
        <!-- /.sidebar -->
    </aside>

    <!-- Full Width Column -->
    <div class="content-wrapper">
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
<script>
    $(function () {
        $("#li1").attr("onclick", "click1()");
        $("#li2").attr("onclick", "click2()");
        $("#li3").attr("onclick", "click3()");
        $("#li4").attr("onclick", "click4()");
        $("#li5").attr("onclick", "click5()");
        $("#li6").attr("onclick", "click6()");
        $("#li7").attr("onclick", "click7()");
        $("#li8").attr("onclick", "click8()");
    });

    function click1() {
        if($("#li1").attr("class") == "treeview"){
            remove();
            $("#li1").attr("class", "active treeview");
        }else{
            $("#li1").attr("class", "treeview");
        }

    }
    function click2() {
        if($("#li2").attr("class") == "treeview"){
            remove();
            $("#li2").attr("class", "active treeview");
        }else{
            $("#li2").attr("class", "treeview");
        }
    }

    function click3() {
        if($("#li3").attr("class") == "treeview"){
            remove();
            $("#li3").attr("class", "active treeview");
        }else{
            $("#li3").attr("class", "treeview");
        }
    }

    function click4() {
        if($("#li4").attr("class") == "treeview"){
            remove();
            $("#li4").attr("class", "active treeview");
        }else{
            $("#li4").attr("class", "treeview");
        }
    }

    function click5() {
        remove();
        $("#li5").attr("class", "active");
    }

    function click6() {
        remove();
        $("#li6").attr("class", "active");
    }

    function click7() {
        remove();
        $("#li7").attr("class", "active");
    }

    function click8() {
        remove();
        $("#li8").attr("class", "active");
    }


    function remove() {
        $("#li1, #li2, #li3, #li4").attr("class", "treeview");
        $("#li5, #li6, #li7, #li8").attr("class", "");
    }
</script>
</body>
</html>
