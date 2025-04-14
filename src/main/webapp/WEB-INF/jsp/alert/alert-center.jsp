<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>告警中心 - 景区智能分析系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/daterangepicker.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
    <!-- 加载动画 -->
    <div id="loading" class="loading-overlay" style="display: none;">
        <div class="loading-spinner"></div>
    </div>

    <!-- 主容器 -->
    <div class="container-fluid">
        <!-- 筛选条件 -->
        <div class="card mb-4">
            <div class="card-body">
                <form id="searchForm">
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>景区名称</label>
                                <input type="text" class="form-control" id="tourismName" name="tourismName">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>设备编码</label>
                                <input type="text" class="form-control" id="deviceCode" name="deviceCode">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>告警类型</label>
                                <select class="form-control" id="alertType" name="alertType">
                                    <option value="">全部</option>
                                    <option value="CROWD_GATHERING">人群聚集</option>
                                    <option value="HIGH_DENSITY">高密度</option>
                                    <option value="ABNORMAL_FLOW">异常流动</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>告警级别</label>
                                <select class="form-control" id="alertLevel" name="alertLevel">
                                    <option value="">全部</option>
                                    <option value="1">低</option>
                                    <option value="2">中</option>
                                    <option value="3">高</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row mt-3">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>告警状态</label>
                                <select class="form-control" id="alertStatus" name="alertStatus">
                                    <option value="">全部</option>
                                    <option value="0">待处理</option>
                                    <option value="1">已处理</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>时间范围</label>
                                <input type="text" class="form-control" id="dateRange" name="dateRange">
                            </div>
                        </div>
                        <div class="col-md-5">
                            <div class="form-group" style="margin-top: 32px;">
                                <button type="button" class="btn btn-primary" id="searchBtn">查询</button>
                                <button type="button" class="btn btn-secondary" id="resetBtn">重置</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- 统计卡片 -->
        <div class="row mb-4">
            <div class="col-md-3">
                <div class="card bg-primary text-white">
                    <div class="card-body">
                        <h5 class="card-title">总告警数</h5>
                        <h3 class="card-text" id="totalAlerts">0</h3>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card bg-warning text-white">
                    <div class="card-body">
                        <h5 class="card-title">待处理告警</h5>
                        <h3 class="card-text" id="pendingAlerts">0</h3>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card bg-success text-white">
                    <div class="card-body">
                        <h5 class="card-title">已处理告警</h5>
                        <h3 class="card-text" id="handledAlerts">0</h3>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card bg-danger text-white">
                    <div class="card-body">
                        <h5 class="card-title">高级别告警</h5>
                        <h3 class="card-text" id="highLevelAlerts">0</h3>
                    </div>
                </div>
            </div>
        </div>

        <!-- 图表区域 -->
        <div class="row mb-4">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">告警时段分布</h5>
                        <div id="hourDistributionChart" style="height: 400px;"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">告警类型分布</h5>
                        <div id="typeDistributionChart" style="height: 400px;"></div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 数据表格 -->
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">告警记录</h5>
                <div class="table-responsive">
                    <table class="table table-striped">
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
                        <tbody id="dataTable">
                        </tbody>
                    </table>
                </div>
                <!-- 分页 -->
                <div class="d-flex justify-content-between align-items-center">
                    <div class="pagination-info">
                        共 <span id="totalCount">0</span> 条记录
                    </div>
                    <ul class="pagination" id="pagination">
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <!-- 图片预览模态框 -->
    <div class="modal fade" id="imageModal" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">全景图预览</h5>
                    <button type="button" class="close" data-dismiss="modal">
                        <span>&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <img id="previewImage" src="" class="img-fluid">
                </div>
            </div>
        </div>
    </div>

    <!-- 告警处理模态框 -->
    <div class="modal fade" id="handleModal" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">处理告警</h5>
                    <button type="button" class="close" data-dismiss="modal">
                        <span>&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="handleForm">
                        <input type="hidden" id="alertId" name="alertId">
                        <div class="form-group">
                            <label>处理说明</label>
                            <textarea class="form-control" id="handleDescription" name="handleDescription" rows="3" required></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="confirmHandle">确认处理</button>
                </div>
            </div>
        </div>
    </div>

    <!-- JavaScript文件 -->
    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/daterangepicker.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/alert-center.js"></script>
</body>
</html> 