<%--
  Created by IntelliJ IDEA.
  User: shelton
  Date: 2018/3/13
  Time: 上午9:21
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
    <title>订单查看</title>
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
                        <li class="active"><a href="/sOrder">订单查看<span class="sr-only">(current)</span></a></li>
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
                                        <a href="/sCardPage" class="btn btn-default btn-flat">Card</a>
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
                        <li class="active"><a href="#reserved" data-toggle="tab">预订中</a></li>
                        <li><a href="#payed" data-toggle="tab">已付款</a></li>
                        <li><a href="#cancelled" data-toggle="tab">已取消</a></li>
                        <li><a href="#dropped" data-toggle="tab">已退课</a></li>
                    </ul>
                    <div class="tab-content">
                        <div class="active tab-pane" id="reserved">
                            <table class="table table-hover">
                                <tr>
                                    <th>订单ID</th>
                                    <th>课程ID</th>
                                    <th>班级ID</th>
                                    <th>老师ID</th>
                                    <th>科目</th>
                                    <th>简介</th>
                                    <th>老师名</th>
                                    <th>机构电话</th>
                                    <th>老师电话</th>
                                    <th>价格</th>
                                    <th>下单时间</th>
                                    <th>状态</th>
                                </tr>
                                <c:forEach items="${ordersReserved}" var="orderReserved">
                                    <tr>
                                        <td>${orderReserved.orderId}</td>
                                        <td>${orderReserved.courseId}</td>
                                        <td>${orderReserved.classId}</td>
                                        <td>${orderReserved.teacherId}</td>
                                        <td><span class="label label-success">${orderReserved.type}</span></td>
                                        <td>${orderReserved.description}</td>
                                        <td>${orderReserved.teacherName}</td>
                                        <td>${orderReserved.institutionPhone}</td>
                                        <td>${orderReserved.teacherPhone}</td>
                                        <td>${orderReserved.price}</td>
                                        <td>${orderReserved.createTime}</td>
                                        <td><span class="label label-warning">预订中</span></td>
                                        <td>
                                            <div class="pull-right" style="margin-right: 10pt">
                                                <a href="/sPayInfoPage/${orderReserved.orderId}">支付</a>
                                            </div>
                                            <div class="pull-right" style="margin-right: 10pt">
                                                <a href="/sOrderCancel/${orderReserved.orderId}">取消</a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <!-- /.tab-pane -->
                        <div class="tab-pane" id="payed">
                            <table class="table table-hover">
                                <tr>
                                    <th>订单ID</th>
                                    <th>课程ID</th>
                                    <th>班级ID</th>
                                    <th>老师ID</th>
                                    <th>科目</th>
                                    <th>简介</th>
                                    <th>老师名</th>
                                    <th>机构电话</th>
                                    <th>老师电话</th>
                                    <th>价格</th>
                                    <th>下单时间</th>
                                    <th>状态</th>
                                </tr>
                                <c:forEach items="${ordersPayed}" var="orderPayed">
                                    <tr>
                                        <td>${orderPayed.orderId}</td>
                                        <td>${orderPayed.courseId}</td>
                                        <td>${orderPayed.classId}</td>
                                        <td>${orderPayed.teacherId}</td>
                                        <td><span class="label label-success">${orderPayed.type}</span></td>
                                        <td>${orderPayed.description}</td>
                                        <td>${orderPayed.teacherName}</td>
                                        <td>${orderPayed.institutionPhone}</td>
                                        <td>${orderPayed.teacherPhone}</td>
                                        <td>${orderPayed.price}</td>
                                        <td>${orderPayed.createTime}</td>
                                        <td><span class="label label-success">已支付</span></td>
                                        <td>
                                            <div class="pull-right" style="margin-right: 10pt">
                                                <a href="/sUnsubscribe/${orderPayed.orderId}">退课</a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <!-- /.tab-pane -->

                        <div class="tab-pane" id="cancelled">
                            <table class="table table-hover">
                                <tr>
                                    <th>订单ID</th>
                                    <th>课程ID</th>
                                    <th>班级ID</th>
                                    <th>老师ID</th>
                                    <th>科目</th>
                                    <th>简介</th>
                                    <th>老师名</th>
                                    <th>机构电话</th>
                                    <th>老师电话</th>
                                    <th>价格</th>
                                    <th>下单时间</th>
                                    <th>状态</th>
                                </tr>
                                <c:forEach items="${ordersCancelled}" var="orderCancelled">
                                    <tr>
                                        <td>${orderCancelled.orderId}</td>
                                        <td>${orderCancelled.courseId}</td>
                                        <td>${orderCancelled.classId}</td>
                                        <td>${orderCancelled.teacherId}</td>
                                        <td><span class="label label-success">${orderCancelled.type}</span></td>
                                        <td>${orderCancelled.description}</td>
                                        <td>${orderCancelled.teacherName}</td>
                                        <td>${orderCancelled.institutionPhone}</td>
                                        <td>${orderCancelled.teacherPhone}</td>
                                        <td>${orderCancelled.price}</td>
                                        <td>${orderCancelled.createTime}</td>
                                        <td><span class="label label-danger">已取消</span></td>
                                        <td>
                                        </td>
                                    </tr>
                                </c:forEach>

                            </table>
                        </div>
                        <div class="tab-pane" id="dropped">
                            <table class="table table-hover">
                                <tr>
                                    <th>订单ID</th>
                                    <th>课程ID</th>
                                    <th>班级ID</th>
                                    <th>科目</th>
                                    <th>下单时间</th>
                                    <th>支付时间</th>
                                    <th>退课时间</th>
                                    <th>支付金额</th>
                                    <th>退还金额</th>
                                    <th>状态</th>
                                </tr>
                                <c:forEach items="${ordersDropped}" var="orderDropped">
                                    <tr>
                                        <td>${orderDropped.orderId}</td>
                                        <td>${orderDropped.courseId}</td>
                                        <td>${orderDropped.classId}</td>
                                        <td><span class="label label-success">${orderReserved.type}</span></td>
                                        <td>${orderDropped.createTime}</td>
                                        <td>${orderDropped.payedTime}</td>
                                        <td>${orderDropped.dropTime}</td>
                                        <td>${orderDropped.payment}</td>
                                        <td>${orderDropped.amountReturned}</td>
                                        <td><span class="label label-danger">已退课</span></td>
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