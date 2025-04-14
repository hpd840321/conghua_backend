<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>人群统计 - 景区智能分析系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/daterangepicker.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/loading.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/toastr.min.css">
</head>
<body>
    <!-- 加载动画 -->
    <div id="loading">
        <div class="loading-spinner"></div>
        <div class="loading-text">数据加载中...</div>
    </div>

    <!-- 错误提示 -->
    <div class="alert alert-danger alert-dismissible fade" role="alert" id="errorAlert" style="display:none; position:fixed; top:20px; right:20px; z-index:9999;">
        <span id="errorMessage"></span>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>

    <!-- 主容器 -->
    <div class="container-fluid py-3">
        <!-- 筛选条件 -->
        <div class="card mb-4">
            <div class="card-body">
                <form id="searchForm" class="needs-validation" novalidate>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label for="tourismName">景区名称</label>
                                <input type="text" class="form-control" id="tourismName" placeholder="请输入景区名称">
                                <div class="invalid-feedback">
                                    请输入有效的景区名称
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label for="deviceCode">设备编码</label>
                                <input type="text" class="form-control" id="deviceCode" placeholder="请输入设备编码">
                                <div class="invalid-feedback">
                                    请输入有效的设备编码
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label for="dateRange">时间范围</label>
                                <input type="text" class="form-control" id="dateRange" required>
                                <div class="invalid-feedback">
                                    请选择时间范围
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2 d-flex align-items-end">
                            <button type="button" class="btn btn-primary mr-2" id="searchBtn">
                                <i class="fas fa-search"></i> 查询
                            </button>
                            <button type="button" class="btn btn-secondary" id="resetBtn">
                                <i class="fas fa-redo"></i> 重置
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- 统计卡片 -->
        <div class="row mb-4">
            <div class="col-md-3">
                <div class="stats-card density">
                    <h6>平均密度</h6>
                    <h3><span id="avgDensity">0.00</span> <small>人/㎡</small></h3>
                    <div class="stats-trend" id="avgDensityTrend"></div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stats-card warning">
                    <h6>最大密度</h6>
                    <h3><span id="maxDensity">0.00</span> <small>人/㎡</small></h3>
                    <div class="stats-trend" id="maxDensityTrend"></div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stats-card danger">
                    <h6>高密度次数</h6>
                    <h3><span id="highDensityCount">0</span> <small>次</small></h3>
                    <div class="stats-trend" id="highDensityTrend"></div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stats-card success">
                    <h6>总记录数</h6>
                    <h3><span id="totalRecords">0</span> <small>条</small></h3>
                    <div class="stats-trend" id="totalRecordsTrend"></div>
                </div>
            </div>
        </div>

        <!-- 图表区域 -->
        <div class="row mb-4">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">密度分布</h5>
                        <div id="densityDistChart" class="chart-container"></div>
                        <div class="chart-loading" style="display:none;">
                            <div class="spinner-border text-primary" role="status">
                                <span class="sr-only">加载中...</span>
                            </div>
                        </div>
                        <div class="chart-error" style="display:none;">
                            <div class="text-center text-danger">
                                <i class="fas fa-exclamation-circle"></i>
                                <p>数据加载失败，请重试</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">密度趋势</h5>
                        <div id="densityTrendChart" class="chart-container"></div>
                        <div class="chart-loading" style="display:none;">
                            <div class="spinner-border text-primary" role="status">
                                <span class="sr-only">加载中...</span>
                            </div>
                        </div>
                        <div class="chart-error" style="display:none;">
                            <div class="text-center text-danger">
                                <i class="fas fa-exclamation-circle"></i>
                                <p>数据加载失败，请重试</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 数据表格 -->
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">密度记录</h5>
                <div class="table-responsive">
                    <div class="table-loading" style="display:none;">
                        <div class="d-flex justify-content-center">
                            <div class="spinner-border text-primary" role="status">
                                <span class="sr-only">加载中...</span>
                            </div>
                        </div>
                    </div>
                    <table class="table table-hover" id="dataTable">
                        <thead>
                            <tr>
                                <th>设备编码</th>
                                <th>设备名称</th>
                                <th>景区名称</th>
                                <th>人数</th>
                                <th>密度(人/㎡)</th>
                                <th>记录时间</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                    <div class="table-empty" style="display:none;">
                        <div class="text-center text-muted my-5">
                            <i class="fas fa-inbox fa-3x mb-3"></i>
                            <p>暂无数据</p>
                        </div>
                    </div>
                </div>
                <!-- 分页 -->
                <nav aria-label="分页导航" class="mt-3">
                    <ul class="pagination justify-content-center" id="pagination">
                    </ul>
                </nav>
            </div>
        </div>
    </div>

    <!-- 图片预览模态框 -->
    <div class="modal fade" id="imageModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">全景图预览</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="modal-loading" style="display:none;">
                        <div class="d-flex justify-content-center">
                            <div class="spinner-border text-primary" role="status">
                                <span class="sr-only">加载中...</span>
                            </div>
                        </div>
                    </div>
                    <img id="previewImage" class="img-fluid" src="" alt="全景图">
                    <div class="modal-error" style="display:none;">
                        <div class="text-center text-danger">
                            <i class="fas fa-exclamation-circle"></i>
                            <p>图片加载失败</p>
                        </div>
                    </div>
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
    <script src="${pageContext.request.contextPath}/static/js/toastr.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/fontawesome.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/crowd-statistics.js"></script>
</body>
</html> 