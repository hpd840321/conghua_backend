<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>人流统计</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/daterangepicker.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/custom.css">
</head>
<body>
    <div class="container-fluid">
        <h2 class="mt-4 mb-4">人流统计</h2>
        
        <!-- 搜索表单 -->
        <div class="card mt-3">
            <div class="card-body">
                <form id="searchForm" class="row g-3">
                    <div class="col-md-3">
                        <label for="tourismName" class="form-label">景区名称</label>
                        <input type="text" class="form-control" id="tourismName" name="tourismName">
                    </div>
                    <div class="col-md-3">
                        <label for="deviceCode" class="form-label">设备编号</label>
                        <input type="text" class="form-control" id="deviceCode" name="deviceCode">
                    </div>
                    <div class="col-md-3">
                        <label for="algorithmType" class="form-label">算法类型</label>
                        <select class="form-select" id="algorithmType" name="algorithmType">
                            <option value="">全部</option>
                            <option value="1">密度检测</option>
                            <option value="2">人数统计</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label for="dateRange" class="form-label">时间范围</label>
                        <input type="text" class="form-control" id="dateRange" name="dateRange">
                    </div>
                    <div class="col-12">
                        <button type="submit" class="btn btn-primary">查询</button>
                        <button type="reset" class="btn btn-secondary">重置</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- 图表展示 -->
        <div class="row mt-3">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">人流密度趋势</h5>
                        <div id="densityChart" style="height: 400px;"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">人数统计趋势</h5>
                        <div id="countChart" style="height: 400px;"></div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 数据表格 -->
        <div class="card mt-3">
            <div class="card-body">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>时间</th>
                            <th>景区名称</th>
                            <th>设备编号</th>
                            <th>人数</th>
                            <th>密度</th>
                            <th>算法类型</th>
                        </tr>
                    </thead>
                    <tbody id="dataTableBody">
                    </tbody>
                </table>
                <div id="pagination" class="d-flex justify-content-center"></div>
            </div>
        </div>
    </div>

    <!-- 加载中模态框 -->
    <div class="modal fade" id="loadingModal" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-body text-center">
                    <div class="spinner-border text-primary" role="status">
                        <span class="visually-hidden">加载中...</span>
                    </div>
                    <p class="mt-2">数据加载中，请稍候...</p>
                </div>
            </div>
        </div>
    </div>

    <!-- 错误提示模态框 -->
    <div class="modal fade" id="errorModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">错误提示</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <p id="errorMessage"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/daterangepicker.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/crowd-statistics.js"></script>
</body>
</html> 