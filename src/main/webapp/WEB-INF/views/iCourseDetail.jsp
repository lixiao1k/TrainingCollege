<%--
  Created by IntelliJ IDEA.
  User: shelton
  Date: 2018/3/11
  Time: 下午8:30
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
    <title>课程计划详细信息</title>
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
                        <li><a href="/iBillsPage">财务</a></li>
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
            <div class="col-md-9">
                <div class="row clearfix">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">班级信息列表</h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body table-responsive no-padding">
                            <table class="table table-hover">
                                <tr>
                                    <th>班级ID</th>
                                    <th>教师ID</th>
                                    <th>预收人数</th>
                                    <th>已收人数</th>
                                </tr>
                                <c:forEach items="${classrooms}" var="classroom">
                                    <tr>
                                        <td>${classroom.id}</td>
                                        <td>${classroom.teacherId}</td>
                                        <td>${classroom.studentNumPlan}</td>
                                        <td>${classroom.studentNumNow}</td>
                                        <td>
                                            <div class="pull-left" style="margin-right: 10pt">
                                                <a href="/iAddClassStudentNow/${classroom.id}">➕</a>
                                            </div>
                                            <div class="pull-left" style="margin-right: 10pt">
                                                <a href="/iMinusClassStudentNow/${classroom.id}">➖</a>
                                            </div>
                                            <div class="pull-right" style="margin-right: 10pt">
                                                <a href="/iClassSign/${classroom.id}">签到</a>
                                                <a href="/iGradePage/${classroom.id}">打分</a>
                                                <a href="/iDeleteClass/${classroom.id}">删除</a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                </div>
                <div class="row clearfix">
                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">添加班级</h3>
                        </div>
                        <!-- /.box-header -->
                        <!-- form start -->
                        <form class="form-horizontal" action="/iAddClass", method="post">
                            <div class="box-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="teacherID" class="col-sm-3 control-label">教师ID</label>

                                            <div class="col-sm-9" >
                                                <input type="text" class="form-control" id="teacherID" placeholder="请输入教师ID" name="teacherId">
                                            </div>
                                        </div>
                                    </div>
                                    <!-- /.col -->
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="studentsPlan" class="col-sm-3 control-label">预收学生数</label>

                                            <div class="col-sm-9">
                                                <input type="text" class="form-control" id="studentsPlan" placeholder="请输入预收学生数" name="studentsPlan">
                                            </div>
                                        </div>
                                        <!-- /.form-group -->
                                    </div>
                                    <!-- /.col -->
                                </div>
                                <button type="submit" class="btn btn-info pull-right">添加</button>
                            </div>
                            <!-- /.box-body -->
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="box box-info">
                    <div class="box box-solid">
                        <div class="box-header with-border">
                            <i class="fa fa-text-width"></i>

                            <h3 class="box-title">课程信息</h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <dl>
                                <dt>课程ID</dt>
                                <dd>${course.id}</dd>
                                <dt>开始时间</dt>
                                <dd>${course.beginDate}</dd>
                                <dt>结束时间</dt>
                                <dd>${course.endDate}</dd>
                                <dt>小时/周</dt>
                                <dd>${course.hourPerWeek}</dd>
                                <dt>周次</dt>
                                <dd>${course.weeks}</dd>
                                <dt>价格</dt>
                                <dd>${course.price}</dd>
                                <dt>类型</dt>
                                <dd>${course.type}</dd>
                                <dt>概要描述</dt>
                                <dd>${course.description}</dd>
                            </dl>
                        </div>
                        <!-- /.box-body -->
                    </div>
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