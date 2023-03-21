<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>김지선's Template</title>

  <!-- Google Font: Source Sans Pro -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="/resources/adminlte3/plugins/fontawesome-free/css/all.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Tempusdominus Bootstrap 4 -->
  <link rel="stylesheet" href="/resources/adminlte3/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="/resources/adminlte3/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
  <!-- JQVMap -->
  <link rel="stylesheet" href="/resources/adminlte3/plugins/jqvmap/jqvmap.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="/resources/adminlte3/dist/css/adminlte.min.css">
  <!-- overlayScrollbars -->
  <link rel="stylesheet" href="/resources/adminlte3/plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
  <!-- Daterange picker -->
  <link rel="stylesheet" href="/resources/adminlte3/plugins/daterangepicker/daterangepicker.css">
  <!-- summernote -->
  <link rel="stylesheet" href="/resources/adminlte3/plugins/summernote/summernote-bs4.min.css">

 <!-- DataTables -->
<link rel="stylesheet" href="/resources/adminlte3/plugins/datatables-bs4/css/dataTables.bootstrap4.min.css">
<link rel="stylesheet" href="/resources/adminlte3/plugins/datatables-responsive/css/responsive.bootstrap4.min.css">
<link rel="stylesheet" href="/resources/adminlte3/plugins/datatables-buttons/css/buttons.bootstrap4.min.css">


</head>
<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">

  <!-- Preloader -->
  <div class="preloader flex-column justify-content-center align-items-center">
    <img class="animation__shake" src="/resources/adminlte3/dist/img/AdminLTELogo.png" alt="AdminLTELogo" height="60" width="60">
  </div>

  <!-- /////////////////// Navbar : header 시작 ////////////// -->
  <tiles:insertAttribute name="header" />
  <!-- ////////////////////.navbar : header 시작 ////////////// -->

  <!-- ////////////////// Main Sidebar Container : aside 시작 //////////////// -->
  <tiles:insertAttribute name="aside" />
  <!-- ////////////////// Content Wrapper. Contains page content : aside 끝 //////// -->
  
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0">Dashboard</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">Dashboard v1</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <!-- //////////// Small boxes (Stat box) : body 시작 /////////////// -->
        <!-- return "springNotice/register"; 
        	springNotice > register.jsp가 include됨
         -->
        <tiles:insertAttribute name="body" />
        <!-- ////////////.row (main row) : body 끝 /////////////////// -->
      </div>
      <!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
  <!-- ////////// footer 시작 //////////////// -->
  <tiles:insertAttribute name="footer" />
  <!-- /////////// footer 끝 ///////////////// -->
  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Control sidebar content goes here -->
  </aside>
  <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->

<!-- jQuery -->
<script src="/resources/adminlte3/plugins/jquery/jquery.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="/resources/adminlte3/plugins/jquery-ui/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
  $.widget.bridge('uibutton', $.ui.button)
</script>
<!-- Bootstrap 4 -->
<script src="/resources/adminlte3/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- ChartJS -->
<script src="/resources/adminlte3/plugins/chart.js/Chart.min.js"></script>
<!-- Sparkline -->
<script src="/resources/adminlte3/plugins/sparklines/sparkline.js"></script>
<!-- JQVMap -->
<script src="/resources/adminlte3/plugins/jqvmap/jquery.vmap.min.js"></script>
<script src="/resources/adminlte3/plugins/jqvmap/maps/jquery.vmap.usa.js"></script>
<!-- jQuery Knob Chart -->
<script src="/resources/adminlte3/plugins/jquery-knob/jquery.knob.min.js"></script>
<!-- daterangepicker -->
<script src="/resources/adminlte3/plugins/moment/moment.min.js"></script>
<script src="/resources/adminlte3/plugins/daterangepicker/daterangepicker.js"></script>
<!-- Tempusdominus Bootstrap 4 -->
<script src="/resources/adminlte3/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
<!-- Summernote -->
<script src="/resources/adminlte3/plugins/summernote/summernote-bs4.min.js"></script>
<!-- overlayScrollbars -->
<script src="/resources/adminlte3/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
<!-- AdminLTE App -->
<script src="/resources/adminlte3/dist/js/adminlte.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="/resources/adminlte3/dist/js/demo.js"></script>
<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
<script src="/resources/adminlte3/dist/js/pages/dashboard.js"></script>


<!-- DataTables  & Plugins -->
<script src="/resources/adminlte3/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/resources/adminlte3/plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>
<script src="/resources/adminlte3/plugins/datatables-responsive/js/dataTables.responsive.min.js"></script>
<script src="/resources/adminlte3/plugins/datatables-responsive/js/responsive.bootstrap4.min.js"></script>
<script src="/resources/adminlte3/plugins/datatables-buttons/js/dataTables.buttons.min.js"></script>
<script src="/resources/adminlte3/plugins/datatables-buttons/js/buttons.bootstrap4.min.js"></script>
<script src="/resources/adminlte3/plugins/jszip/jszip.min.js"></script>
<script src="/resources/adminlte3/plugins/pdfmake/pdfmake.min.js"></script>
<script src="/resources/adminlte3/plugins/pdfmake/vfs_fonts.js"></script>
<script src="/resources/adminlte3/plugins/datatables-buttons/js/buttons.html5.min.js"></script>
<script src="/resources/adminlte3/plugins/datatables-buttons/js/buttons.print.min.js"></script>
<script src="/resources/adminlte3/plugins/datatables-buttons/js/buttons.colVis.min.js"></script>


</body>
</html>
