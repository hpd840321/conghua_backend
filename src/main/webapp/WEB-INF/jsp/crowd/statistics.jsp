<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>人群统计</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/daterangepicker.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/loading.css">
    <style>
        .chart-container {
            height: 400px;
            margin-bottom: 20px;
        }
        .data-table {
            margin-top: 20px;
        }
        .filter-section {
            margin-bottom: 20px;
            padding: 15px;
            background-color: #f8f9fa;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <!-- 加载动画 -->
    <div class="loading-overlay">
        <div class="loading-content">
            <div class="loading-spinner"></div>
            <div class="loading-text">数据加载中...</div>
        </div>
    </div>

    <div class="container-fluid">
        <h2 class="mt-4 mb-4">人群统计分析</h2>
        
        <!-- 筛选条件区域 -->
        <div class="filter-section">
            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">
                        <label>景区名称</label>
                        <input type="text" class="form-control" id="tourismName" placeholder="请输入景区名称">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label>设备编码</label>
                        <input type="text" class="form-control" id="deviceCode" placeholder="请输入设备编码">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>时间范围</label>
                        <input type="text" class="form-control" id="dateRange">
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group" style="margin-top: 32px;">
                        <button class="btn btn-primary" onclick="searchData()">搜索</button>
                        <button class="btn btn-default" onclick="resetSearch()">重置</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- 统计卡片区域 -->
        <div class="stats-cards">
            <div class="stat-card">
                <h5>总人数</h5>
                <p id="totalCount">0</p>
            </div>
            <div class="stat-card">
                <h5>平均密度</h5>
                <p id="avgDensity">0.00</p>
            </div>
            <div class="stat-card">
                <h5>最大人数</h5>
                <p id="maxCount">0</p>
            </div>
            <div class="stat-card">
                <h5>最大密度</h5>
                <p id="maxDensity">0.00</p>
            </div>
        </div>

        <!-- 图表区域 -->
        <div class="row">
            <div class="col-md-12">
                <div class="chart-container">
                    <div class="chart-header">
                        <h5>时段人群分布</h5>
                    </div>
                    <div id="hourDistribution" style="height: 400px;"></div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="chart-container">
                    <div class="chart-header">
                        <h5>密度分布</h5>
                    </div>
                    <div id="densityDistribution" style="height: 400px;"></div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="chart-container">
                    <div class="chart-header">
                        <h5>人群趋势</h5>
                    </div>
                    <div id="crowdTrend" style="height: 400px;"></div>
                </div>
            </div>
        </div>

        <!-- 数据表格区域 -->
        <div class="data-table">
            <div class="table-header">
                <h5>详细数据</h5>
            </div>
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>设备编码</th>
                        <th>设备名称</th>
                        <th>景区名称</th>
                        <th>人数</th>
                        <th>密度</th>
                        <th>记录时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody id="dataTableBody">
                </tbody>
            </table>
            <div id="pagination" class="text-center">
            </div>
        </div>
    </div>

    <!-- 图片预览模态框 -->
    <div class="modal fade" id="imageModal" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">全景图预览</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <img id="previewImage" src="" class="img-fluid" alt="全景图">
                </div>
            </div>
        </div>
    </div>

    <!-- 引入相关JS文件 -->
    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/daterangepicker.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/crowd-statistics.js"></script>
</body>
</html> 