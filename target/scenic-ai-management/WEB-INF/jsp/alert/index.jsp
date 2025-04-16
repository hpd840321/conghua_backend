<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>告警中心</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
    <div class="container-fluid">
        <!-- 筛选条件区域 -->
        <div class="filter-section">
            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">
                        <label>景区名称</label>
                        <select class="form-control" id="tourismName">
                            <option value="">全部</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label>设备编码</label>
                        <select class="form-control" id="deviceCode">
                            <option value="">全部</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label>告警类型</label>
                        <select class="form-control" id="alertType">
                            <option value="">全部</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label>告警级别</label>
                        <select class="form-control" id="alertLevel">
                            <option value="">全部</option>
                            <option value="1">低</option>
                            <option value="2">中</option>
                            <option value="3">高</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label>告警状态</label>
                        <select class="form-control" id="alertStatus">
                            <option value="">全部</option>
                            <option value="0">待处理</option>
                            <option value="1">已处理</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row">
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
                <div class="col-md-6 text-right">
                    <button class="btn btn-primary" onclick="search()">查询</button>
                    <button class="btn btn-success" onclick="batchHandle()">批量处理</button>
                </div>
            </div>
        </div>

        <!-- 统计概览区域 -->
        <div class="overview-section">
            <div class="row">
                <div class="col-md-3">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">告警总数</h5>
                            <p class="card-text" id="totalCount">0</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">待处理告警</h5>
                            <p class="card-text" id="pendingCount">0</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">已处理告警</h5>
                            <p class="card-text" id="handledCount">0</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">平均处理时间</h5>
                            <p class="card-text" id="avgHandleTime">0分钟</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 图表区域 -->
        <div class="chart-section">
            <div class="row">
                <div class="col-md-6">
                    <div class="chart-container">
                        <h5>告警时段分布</h5>
                        <div id="timeDistributionChart" style="height: 400px;"></div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="chart-container">
                        <h5>告警类型分布</h5>
                        <div id="typeDistributionChart" style="height: 400px;"></div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 数据表格区域 -->
        <div class="data-table">
            <div class="table-header">
                <h5>告警信息列表</h5>
            </div>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th><input type="checkbox" id="selectAll"></th>
                        <th>景区名称</th>
                        <th>设备编码</th>
                        <th>设备名称</th>
                        <th>告警类型</th>
                        <th>告警级别</th>
                        <th>告警状态</th>
                        <th>记录时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody id="dataTable">
                    <!-- 数据将通过JavaScript动态加载 -->
                </tbody>
            </table>
            <div class="pagination-container">
                <ul class="pagination" id="pagination">
                    <!-- 分页将通过JavaScript动态加载 -->
                </ul>
            </div>
        </div>
    </div>

    <!-- 告警详情模态框 -->
    <div class="modal fade" id="alertModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">告警详情</h5>
                    <button type="button" class="close" data-dismiss="modal">
                        <span>&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6">
                            <img id="alertImage" class="img-fluid">
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>告警描述</label>
                                <textarea class="form-control" id="alertDescription" rows="3" readonly></textarea>
                            </div>
                            <div class="form-group">
                                <label>处理说明</label>
                                <textarea class="form-control" id="handleDescription" rows="3"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="handleAlert()">处理</button>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/alert.js"></script>
</body>
</html> 