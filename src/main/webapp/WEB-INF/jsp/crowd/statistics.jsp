<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>人群统计分析</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">
    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
</head>
<body>
    <div class="container-fluid">
        <!-- 筛选条件 -->
        <div class="filter-section">
            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">
                        <label>景区名称</label>
                        <input type="text" class="form-control" id="tourismName">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label>设备编码</label>
                        <input type="text" class="form-control" id="deviceCode">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label>开始时间</label>
                        <input type="datetime-local" class="form-control" id="startTime">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label>结束时间</label>
                        <input type="datetime-local" class="form-control" id="endTime">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <button class="btn btn-primary" onclick="search()">查询</button>
                    <button class="btn btn-default" onclick="reset()">重置</button>
                </div>
            </div>
        </div>

        <!-- 统计概览 -->
        <div class="overview-section">
            <div class="row">
                <div class="col-md-3">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">总人数</h5>
                            <p class="card-text" id="totalCount">0</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">平均密度</h5>
                            <p class="card-text" id="avgDensity">0</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">最大人数</h5>
                            <p class="card-text" id="maxCount">0</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">记录数量</h5>
                            <p class="card-text" id="recordCount">0</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 图表展示 -->
        <div class="chart-section">
            <div class="row">
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">时段分布</h5>
                            <div id="hourDistributionChart" style="height: 400px;"></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">密度分布</h5>
                            <div id="densityDistributionChart" style="height: 400px;"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">人群趋势</h5>
                            <div id="trendChart" style="height: 400px;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 数据列表 -->
        <div class="data-table">
            <div class="table-header">
                <h5>人群统计列表</h5>
                <div class="table-actions">
                    <button class="btn btn-primary" onclick="refreshTable()">刷新</button>
                </div>
            </div>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>景区名称</th>
                        <th>设备编码</th>
                        <th>设备名称</th>
                        <th>人数</th>
                        <th>密度</th>
                        <th>算法类型</th>
                        <th>记录时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody id="dataTable">
                </tbody>
            </table>
            <div class="pagination-container">
                <ul class="pagination" id="pagination"></ul>
            </div>
        </div>
    </div>

    <!-- 全景图模态框 -->
    <div class="modal fade" id="imageModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">全景图</h5>
                    <button type="button" class="close" data-dismiss="modal">
                        <span>&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <img id="panoramaImage" class="img-fluid">
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/static/js/crowd-statistics.js"></script>
</body>
</html> 