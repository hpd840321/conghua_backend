<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>景区智能分析系统</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="<c:url value='/static/css/main.css'/>" rel="stylesheet">
    <!-- jQuery -->
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <!-- Bootstrap JS -->
    <script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
    <!-- ECharts -->
    <script src="https://cdn.bootcdn.net/ajax/libs/echarts/5.4.0/echarts.min.js"></script>
</head>
<body>
    <div class="wrapper">
        <!-- 左侧菜单 -->
        <nav id="sidebar" class="sidebar">
            <div class="sidebar-header">
                <h3>景区智能分析系统</h3>
            </div>
            <ul class="list-unstyled components">
                <li class="active">
                    <a href="<c:url value='/crowd/statistics'/>">人群统计</a>
                </li>
                <li>
                    <a href="<c:url value='/alert/list'/>">告警中心</a>
                </li>
                <li>
                    <a href="<c:url value='/device/list'/>">设备管理</a>
                </li>
            </ul>
        </nav>

        <!-- 页面内容 -->
        <div id="content">
            <!-- 顶部导航 -->
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="container-fluid">
                    <button type="button" id="sidebarCollapse" class="btn btn-info">
                        <i class="fas fa-align-left"></i>
                        <span>切换菜单</span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="nav navbar-nav ml-auto">
                            <li class="nav-item">
                                <a class="nav-link" href="#">帮助</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>

            <!-- 主要内容区域 -->
            <div class="container-fluid">
                <jsp:include page="${content}" />
            </div>
        </div>
    </div>

    <!-- Custom JS -->
    <script src="<c:url value='/static/js/main.js'/>"></script>
</body>
</html> 