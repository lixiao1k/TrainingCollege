<%--
  Created by IntelliJ IDEA.
  User: shelton
  Date: 2018/3/19
  Time: 下午2:05
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
    <title>机构信息主页</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/mine/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/mine/ionicons.min.css">
    <!-- daterange picker -->
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/mine/daterangepicker.css">
    <!-- bootstrap datepicker -->
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/mine/bootstrap-datepicker.min.css">
    <!-- iCheck for checkboxes and radio inputs -->
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/mine/all.css">
    <!-- Bootstrap Color Picker -->
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/mine/bootstrap-colorpicker.min.css">
    <!-- Bootstrap time Picker -->
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/mine/bootstrap-timepicker.min.css">
    <!-- Select2 -->
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/mine/select2.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/mine/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/mine/_all-skins.min.css">
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/mine/dataTables.bootstrap.min.css">

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
            <div class="col-md-12">
                <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#payed" data-toggle="tab">支付订单</a></li>
                        <li><a href="#unSubscrible" data-toggle="tab">退订订单</a></li>
                    </ul>
                    <div class="tab-content">
                        <div class="active tab-pane" id="payed">
                            <div class="box">
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <table id="example1" class="table table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>订单ID</th>
                                            <th>课程ID</th>
                                            <th>班级ID</th>
                                            <th>学生ID</th>
                                            <th>价格</th>
                                            <th>支付金额</th>
                                            <th>下单时间</th>
                                            <th>支付时间</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${orderVOSPayed}" var="orderVOPayed">
                                            <tr>
                                                <td>${orderVOPayed.orderId}</td>
                                                <td>${orderVOPayed.courseId}</td>
                                                <td>${orderVOPayed.classId}</td>
                                                <td>${orderVOPayed.studentId}</td>
                                                <td>${orderVOPayed.price}</td>
                                                <td>${orderVOPayed.payment}</td>
                                                <td>${orderVOPayed.createTime}</td>
                                                <td>${orderVOPayed.payedTime}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <tfoot>
                                        <tr>
                                            <th>订单ID</th>
                                            <th>课程ID</th>
                                            <th>班级ID</th>
                                            <th>学生ID</th>
                                            <th>价格</th>
                                            <th>支付金额</th>
                                            <th>下单时间</th>
                                            <th>支付时间</th>
                                        </tr>
                                        </tfoot>
                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                        </div>
                        <!-- /.tab-pane -->
                        <div class="tab-pane" id="unSubscrible">
                            <div class="box">
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <table id="example2" class="table table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>订单ID</th>
                                            <th>课程ID</th>
                                            <th>班级ID</th>
                                            <th>学生ID</th>
                                            <th>价格</th>
                                            <th>支付金额</th>
                                            <th>返还金额</th>
                                            <th>下单时间</th>
                                            <th>支付时间</th>
                                            <th>退课时间</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${orderVOSDropped}" var="orderVODropped">
                                            <tr>
                                                <td>${orderVODropped.orderId}</td>
                                                <td>${orderVODropped.courseId}</td>
                                                <td>${orderVODropped.classId}</td>
                                                <td>${orderVODropped.studentId}</td>
                                                <td>${orderVODropped.price}</td>
                                                <td>${orderVODropped.payment}</td>
                                                <td>${orderVODropped.amountReturned}</td>
                                                <td>${orderVODropped.createTime}</td>
                                                <td>${orderVODropped.payedTime}</td>
                                                <td>${orderVODropped.dropTime}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <tfoot>
                                        <tr>
                                            <th>订单ID</th>
                                            <th>课程ID</th>
                                            <th>班级ID</th>
                                            <th>学生ID</th>
                                            <th>价格</th>
                                            <th>支付金额</th>
                                            <th>返还金额</th>
                                            <th>下单时间</th>
                                            <th>支付时间</th>
                                            <th>退课时间</th>
                                        </tr>
                                        </tfoot>
                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                        </div>
                        <!-- /.tab-pane -->
                    </div>
                    <!-- /.tab-content -->
                </div>
                <!-- /.nav-tabs-custom -->
            </div>
        </div>
        <!-- /.container -->
    </div>
</div>
<!-- ./wrapper -->

<!-- ./wrapper -->
<script src="<%=basePath%>bootstrap/js/demo.js"></script>
<!-- jQuery 3 -->
<script src="<%=basePath%>bootstrap/js/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="<%=basePath%>bootstrap/js/bootstrap.min.js"></script>
<!-- Select2 -->
<script src="<%=basePath%>bootstrap/js/mine/select2.full.min.js"></script>
<!-- InputMask -->
<script src="<%=basePath%>bootstrap/js/mine/jquery.inputmask.js"></script>
<script src="<%=basePath%>bootstrap/js/mine/jquery.inputmask.date.extensions.js"></script>
<script src="<%=basePath%>bootstrap/js/mine/jquery.inputmask.extensions.js"></script>
<!-- date-range-picker -->
<script src="<%=basePath%>bootstrap/js/mine/moment.min.js"></script>
<script src="<%=basePath%>bootstrap/js/mine/daterangepicker.js"></script>
<!-- bootstrap datepicker -->
<script src="<%=basePath%>bootstrap/js/mine/bootstrap-datepicker.min.js"></script>
<!-- bootstrap color picker -->
<script src="<%=basePath%>bootstrap/js/mine/bootstrap-colorpicker.min.js"></script>
<!-- bootstrap time picker -->
<script src="<%=basePath%>bootstrap/js/mine/bootstrap-timepicker.min.js"></script>
<!-- SlimScroll -->
<script src="<%=basePath%>bootstrap/js/mine/jquery.slimscroll.min.js"></script>
<!-- iCheck 1.0.1 -->
<script src="<%=basePath%>bootstrap/js/mine/icheck.min.js"></script>
<!-- FastClick -->
<script src="<%=basePath%>bootstrap/js/mine/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="<%=basePath%>bootstrap/js/mine/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="<%=basePath%>bootstrap/js/mine/demo.js"></script>
<!-- Page script -->
<script src="<%=basePath%>bootstrap/js/mine/jquery.dataTables.min.js"></script>
<script src="<%=basePath%>bootstrap/js/mine/dataTables.bootstrap.min.js"></script>

<script>
    $(function () {
        $('#example1').DataTable({
            'paging'      : true,
            'lengthChange': false,
            'searching'   : false,
            'ordering'    : true,
            'info'        : true,
            'autoWidth'   : false
        })
        $('#example2').DataTable({
            'paging'      : true,
            'lengthChange': false,
            'searching'   : false,
            'ordering'    : true,
            'info'        : true,
            'autoWidth'   : false
        })
    })
</script>
</body>
</html>
