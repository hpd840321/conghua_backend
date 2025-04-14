<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>告警中心</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/daterangepicker.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/font-awesome.min.css">
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
        <h2 class="mt-4 mb-4">告警中心</h2>
        
        <!-- 筛选条件区域 -->
        <div class="filter-section">
            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">
                        <label>景区名称</label>
                        <input type="text" class="form-control" id="tourismName" placeholder="请输入景区名称">
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label>设备编码</label>
                        <input type="text" class="form-control" id="deviceCode" placeholder="请输入设备编码">
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label>告警类型</label>
                        <select class="form-control" id="alertType">
                            <option value="">全部</option>
                            <option value="CROWD_GATHERING">人群聚集</option>
                            <option value="HIGH_DENSITY">高密度</option>
                            <option value="ABNORMAL_FLOW">异常流动</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label>告警级别</label>
                        <select class="form-control" id="alertLevel">
                            <option value="">全部</option>
                            <option value="1">低级</option>
                            <option value="2">中级</option>
                            <option value="3">高级</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label>时间范围</label>
                        <input type="text" class="form-control" id="dateRange">
                    </div>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-md-12">
                    <button class="btn btn-primary" onclick="searchData()">
                        <i class="fa fa-search"></i> 搜索
                    </button>
                    <button class="btn btn-secondary ml-2" onclick="resetSearch()">
                        <i class="fa fa-refresh"></i> 重置
                    </button>
                </div>
            </div>
        </div>

        <!-- 统计卡片区域 -->
        <div class="stats-cards">
            <div class="stat-card">
                <h5>总告警数</h5>
                <p id="totalAlerts">0</p>
            </div>
            <div class="stat-card">
                <h5>待处理告警</h5>
                <p id="pendingAlerts">0</p>
            </div>
            <div class="stat-card">
                <h5>高级别告警</h5>
                <p id="highLevelAlerts">0</p>
            </div>
            <div class="stat-card">
                <h5>今日告警</h5>
                <p id="todayAlerts">0</p>
            </div>
        </div>

        <!-- 图表区域 -->
        <div class="row">
            <div class="col-md-6">
                <div class="chart-container">
                    <div class="chart-header">
                        <h5>告警时段分布</h5>
                    </div>
                    <div id="hourDistribution" style="height: 400px;"></div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="chart-container">
                    <div class="chart-header">
                        <h5>告警类型分布</h5>
                    </div>
                    <div id="typeDistribution" style="height: 400px;"></div>
                </div>
            </div>
        </div>

        <!-- 数据表格区域 -->
        <div class="data-table">
            <div class="card">
                <div class="card-header bg-white d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">告警记录</h5>
                    <div id="tableInfo" class="text-muted"></div>
                </div>
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>设备编码</th>
                                    <th>设备名称</th>
                                    <th>景区名称</th>
                                    <th>告警类型</th>
                                    <th>告警级别</th>
                                    <th>告警状态</th>
                                    <th>告警描述</th>
                                    <th>记录时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody id="dataTableBody">
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="card-footer bg-white">
                    <div id="pagination" class="d-flex justify-content-center"></div>
                </div>
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

    <!-- 告警处理模态框 -->
    <div class="modal fade" id="handleModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">处理告警</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="handleForm">
                        <input type="hidden" id="alertId">
                        <div class="form-group">
                            <label>处理备注</label>
                            <textarea class="form-control" id="handleRemark" rows="3" placeholder="请输入处理备注"></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" onclick="handleAlert()">确定</button>
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
    <script src="${pageContext.request.contextPath}/static/js/alert-center.js"></script>
</body>
</html> 