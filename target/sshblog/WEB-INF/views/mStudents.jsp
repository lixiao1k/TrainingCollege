<%--
  Created by IntelliJ IDEA.
  User: shelton
  Date: 2018/3/19
  Time: 下午1:10
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
    <title>学生统计信息查看</title>
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
<!-- ADD THE CLASS layout-top-nav TO REMOVE THE SIDEBAR. -->
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
                        <li><a href="#">结算</a></li>
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
                        <li><a href="/mAnalyse">分析数据</a></li>
                    </ul>
                    <form class="navbar-form navbar-left" role="search">
                        <div class="form-group">
                            <input type="text" class="form-control" id="navbar-search-input" placeholder="Search">
                        </div>
                    </form>
                </div>
                <div class="navbar-custom-menu">
                    <ul class="nav navbar-nav">
                        <!-- User Account Menu -->
                        <li><a href="/mLogOut">登出</a></li>
                    </ul>
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
                        <li class="active"><a href="#reserved" data-toggle="tab">会员</a></li>
                        <li><a href="#payed" data-toggle="tab">资格取消</a></li>
                    </ul>
                    <div class="tab-content">
                        <div class="active tab-pane" id="reserved">
                            <div class="box">
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <table id="example1" class="table table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>会员ID</th>
                                            <th>邮箱</th>
                                            <th>等级</th>
                                            <th>卡号</th>
                                            <th>总消费</th>
                                            <th>积分余额</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${studentsNonCancelled}" var="studentNC">
                                            <tr>
                                                <td>${studentNC.id}</td>
                                                <td>${studentNC.email}</td>
                                                <td>${studentNC.level}</td>
                                                <td>${studentNC.cardNumber}</td>
                                                <td>${studentNC.totalConsumption}</td>
                                                <td>${studentNC.bpBalance}</td>
                                                <td>
                                                    <div class="pull-right" style="margin-right: 10pt">
                                                        <a href="/mStudentCancel/${studentNC.id}"><span class="label label-danger">取消资格</span></a>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <tfoot>
                                        <tr>
                                            <th>会员ID</th>
                                            <th>邮箱</th>
                                            <th>等级</th>
                                            <th>卡号</th>
                                            <th>总消费</th>
                                            <th>积分余额</th>
                                            <th></th>
                                        </tr>
                                        </tfoot>
                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                        </div>
                        <!-- /.tab-pane -->
                        <div class="tab-pane" id="payed">
                            <div class="box">
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <table id="example2" class="table table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>会员ID</th>
                                            <th>邮箱</th>
                                            <th>等级</th>
                                            <th>卡号</th>
                                            <th>总消费</th>
                                            <th>积分余额</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${studentsCancelled}" var="studentC">
                                            <tr>
                                                <td>${studentC.id}</td>
                                                <td>${studentC.email}</td>
                                                <td>${studentC.level}</td>
                                                <td>${studentC.cardNumber}</td>
                                                <td>${studentC.totalConsumption}</td>
                                                <td>${studentC.bpBalance}</td>
                                                <td>
                                                    <div class="pull-right" style="margin-right: 10pt">
                                                        <a href="/mStudentreinstatement/${studentC.id}"><span class="label label-success">恢复资格</span></a>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <tfoot>
                                        <tr>
                                            <th>会员ID</th>
                                            <th>邮箱</th>
                                            <th>等级</th>
                                            <th>卡号</th>
                                            <th>总消费</th>
                                            <th>积分余额</th>
                                            <th></th>
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

