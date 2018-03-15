<%--
  Created by IntelliJ IDEA.
  User: shelton
  Date: 2018/3/14
  Time: 下午6:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>我的课程</title>
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
<!-- ADD THE CLASS layout-top-nav TO REMOVE THE SIDEBAR. -->
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
                        <li><a href="/sHome">个人信息</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">课程<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="/sCourseMine">我的</a></li>
                                <li class="divider"></li>
                                <li><a href="/sAllCourse">全部</a></li>
                            </ul>
                        </li>
                        <li><a href="/sOrder">订单查看</a></li>
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
                                <span class="hidden-xs">${student.userName}</span>
                            </a>
                            <ul class="dropdown-menu">
                                <!-- The user image in the menu -->
                                <li class="user-header">
                                    <p>
                                        ${student.userName}
                                        <small>${student.email}</small>
                                        <small>More Pain, More Gain</small>
                                    </p>
                                </li>
                                <!-- Menu Footer-->
                                <li class="user-footer">
                                    <div class="pull-left">
                                        <a href="#" class="btn btn-default btn-flat">Card</a>
                                    </div>
                                    <div class="pull-right">
                                        <a href="#" class="btn btn-default btn-flat">Sign out</a>
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
            <div class="col-md-12">
                <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#running" data-toggle="tab">进行中</a></li>
                        <li><a href="#outOfWay" data-toggle="tab">已结课</a></li>
                    </ul>
                    <div class="tab-content">
                        <div class="active tab-pane" id="running">
                            <table class="table table-hover">
                                <tr>
                                    <th>课程ID</th>
                                    <th>简介</th>
                                    <th>科目</th>
                                    <th>开课时间</th>
                                    <th>结课时间</th>
                                    <th>价格</th>
                                    <th>课时/周</th>
                                    <th>周数</th>
                                    <th>状态</th>
                                </tr>
                                <c:forEach items="${uOrders}" var="uOrder">
                                    <tr>
                                        <td>${uOrder.courseId}</td>
                                        <td>${uOrder.description}</td>
                                        <td><span class="label label-success">${uOrder.type}</span></td>
                                        <td>${uOrder.beginDate}</td>
                                        <td>${uOrder.endDate}</td>
                                        <td>${uOrder.price}</td>
                                        <td>${uOrder.hourPerWeek}</td>
                                        <td>${uOrder.weeks}</td>
                                        <td><span class="label label-success">进行中</span></td>
                                        <td>
                                            <div class="pull-right" style="margin-right: 10pt">
                                                <a href="/sMyCourseDetail/${uOrder.courseId}">查看</a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <!-- /.tab-pane -->
                        <div class="tab-pane" id="outOfWay">
                            <table class="table table-hover">
                                <tr>
                                    <th>课程ID</th>
                                    <th>简介</th>
                                    <th>科目</th>
                                    <th>开课时间</th>
                                    <th>结课时间</th>
                                    <th>价格</th>
                                    <th>课时/周</th>
                                    <th>周数</th>
                                    <th>状态</th>
                                    <th>得分</th>
                                </tr>
                                <c:forEach items="${oOrders}" var="oOrder">
                                    <tr>
                                        <td>${oOrder.courseId}</td>
                                        <td>${oOrder.description}</td>
                                        <td><span class="label label-success">${oOrder.type}</span></td>
                                        <td>${oOrder.beginDate}</td>
                                        <td>${oOrder.endDate}</td>
                                        <td>${oOrder.price}</td>
                                        <td>${oOrder.hourPerWeek}</td>
                                        <td>${oOrder.weeks}</td>
                                        <td><span class="label label-danger">已结课</span></td>
                                        <td>${oOrder.grade}</td>
                                        <td>
                                            <div class="pull-right" style="margin-right: 10pt">
                                                <a href="/sMyCourseDetail/${oOrder.courseId}">查看</a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <!-- /.tab-pane -->
                    </div>
                    <!-- /.tab-content -->
                </div>
                <!-- /.nav-tabs-custom -->
            </div>
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
<script src="<%=basePath%>bootstrap/js/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="<%=basePath%>bootstrap/js/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="<%=basePath%>bootstrap/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="<%=basePath%>bootstrap/js/demo.js"></script>
</body>
</html>