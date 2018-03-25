<%--
  Created by IntelliJ IDEA.
  User: shelton
  Date: 2018/3/11
  Time: 上午11:15
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
    <title>经理主页</title>
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
                    <a href="/" class="navbar-brand"><b>Training</b>College</a>
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse">
                        <i class="fa fa-bars"></i>
                    </button>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse pull-left" id="navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="/mInstitutionFinancial">结算</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">申请<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="/mRegisterApplication">机构注册申请</a></li>
                                <li class="divider"></li>
                                <li><a href="/mModifyApplication">机构信息修改申请</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">统计<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="/mInstitutions">机构信息</a></li>
                                <li class="divider"></li>
                                <li><a href="/mStudents">学员信息</a></li>
                                <li class="divider"></li>
                                <li><a href="/mBillsPage">财务情况</a></li>
                            </ul>
                        </li>
                    </ul>
                    <form class="navbar-form navbar-left" role="search">
                        <div class="form-group">
                            <input type="text" class="form-control" id="navbar-search-input" placeholder="Search">
                        </div>
                    </form>
                </div>
                <!-- /.navbar-collapse -->
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
                        <li class="active"><a href="#payed" data-toggle="tab">支付账目</a></li>
                        <li><a href="#droped" data-toggle="tab">退款账目</a></li>
                        <li><a href="#payedOffline" data-toggle="tab">线下支付账目</a></li>
                    </ul>
                    <div class="tab-content">
                        <div class="active tab-pane" id="payed">
                            <div class="box">
                                <!-- /.box-header -->
                                <div class="box-header">
                                    <h3 class="box-title"><span class="label label-success">汇总订单支付共${payedSum}元</span></h3>
                                </div>
                                <div class="box-body">
                                    <table id="example1" class="table table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>订单ID</th>
                                            <th>操作</th>
                                            <th>时间</th>
                                            <th>金额</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${payedBills}" var="payedBill">
                                            <tr>
                                                <td>${payedBill.orderId}</td>
                                                <td>${payedBill.action}</td>
                                                <td>${payedBill.time}</td>
                                                <td><span class="label label-success">${payedBill.moneyChange}</span></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <tfoot>
                                        <tr>
                                            <th>订单ID</th>
                                            <th>操作</th>
                                            <th>时间</th>
                                            <th>金额</th>
                                        </tr>
                                        </tfoot>
                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                        </div>
                        <!-- /.tab-pane -->
                        <div class="tab-pane" id="droped">
                            <div class="box">
                                <!-- /.box-header -->
                                <div class="box-header">
                                    <h3 class="box-title"><span class="label label-danger">汇总退课出款支付共${droppedSum}元</span></h3>
                                </div>
                                <div class="box-body">
                                    <table id="example2" class="table table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>订单ID</th>
                                            <th>操作</th>
                                            <th>时间</th>
                                            <th>金额</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${droppedBills}" var="droppedBill">
                                            <tr>
                                                <td>${droppedBill.orderId}</td>
                                                <td>${droppedBill.action}</td>
                                                <td>${droppedBill.time}</td>
                                                <td><span class="label label-danger">${droppedBill.moneyChange}</span></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <tfoot>
                                        <tr>
                                            <th>订单ID</th>
                                            <th>操作</th>
                                            <th>时间</th>
                                            <th>金额</th>
                                        </tr>
                                        </tfoot>
                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                        </div>
                        <!-- /.tab-pane -->
                        <div class="active tab-pane" id="payedOffline">
                            <div class="box">
                                <!-- /.box-header -->
                                <div class="box-header">
                                    <h3 class="box-title"><span class="label label-info">汇总线下支付共${payedOfflineSum}元</span></h3>
                                </div>
                                <div class="box-body">
                                    <table id="example3" class="table table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>订单ID</th>
                                            <th>操作</th>
                                            <th>时间</th>
                                            <th>金额</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${payedOfflineBills}" var="payedOfflineBill">
                                            <tr>
                                                <td>${payedOfflineBill.orderId}</td>
                                                <td>${payedOfflineBill.action}</td>
                                                <td>${payedOfflineBill.time}</td>
                                                <td><span class="label label-info">${payedOfflineBill.moneyChange}</span></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <tfoot>
                                        <tr>
                                            <th>订单ID</th>
                                            <th>操作</th>
                                            <th>时间</th>
                                            <th>金额</th>
                                        </tr>
                                        </tfoot>
                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                        </div>
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