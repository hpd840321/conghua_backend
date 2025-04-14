<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>客流分析</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/daterangepicker.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/loading.css">
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
        <h2 class="mt-4 mb-4">客流分析</h2>
        
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
                <div class="col-md-2">
                    <div class="form-group">
                        <label>流动方向</label>
                        <select class="form-control" id="flowDirection">
                            <option value="">全部</option>
                            <option value="IN">进入</option>
                            <option value="OUT">离开</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>时间范围</label>
                        <input type="text" class="form-control" id="dateRange">
                    </div>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-md-12">
                    <button class="btn btn-primary" onclick="searchData()">搜索</button>
                    <button class="btn btn-default ml-2" onclick="resetSearch()">重置</button>
                </div>
            </div>
        </div>

        <!-- 统计卡片区域 -->
        <div class="stats-cards">
            <div class="stat-card">
                <h5>总客流量</h5>
                <p id="totalFlow">0</p>
            </div>
            <div class="stat-card">
                <h5>进入人数</h5>
                <p id="inFlow">0</p>
            </div>
            <div class="stat-card">
                <h5>离开人数</h5>
                <p id="outFlow">0</p>
            </div>
            <div class="stat-card">
                <h5>今日客流</h5>
                <p id="todayFlow">0</p>
            </div>
        </div>

        <!-- 图表区域 -->
        <div class="row">
            <div class="col-md-6">
                <div class="chart-container">
                    <div class="chart-header">
                        <h5>客流时段分布</h5>
                    </div>
                    <div id="hourDistribution" style="height: 400px;"></div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="chart-container">
                    <div class="chart-header">
                        <h5>客流趋势</h5>
                    </div>
                    <div id="flowTrend" style="height: 400px;"></div>
                </div>
            </div>
        </div>

        <!-- 数据表格区域 -->
        <div class="data-table">
            <div class="table-header">
                <h5>客流记录</h5>
            </div>
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>设备编码</th>
                        <th>设备名称</th>
                        <th>景区名称</th>
                        <th>客流数量</th>
                        <th>流动方向</th>
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
    <script src="${pageContext.request.contextPath}/static/js/flow-analysis.js"></script>
</body>
</html> 