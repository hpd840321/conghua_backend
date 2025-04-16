<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>人群统计</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/daterangepicker.css" rel="stylesheet">
    <style>
        .chart-container {
            height: 400px;
            margin-bottom: 20px;
        }
        .loading-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(255, 255, 255, 0.8);
            display: none;
            justify-content: center;
            align-items: center;
            z-index: 1000;
        }
    </style>
</head>
<body>
    <div class="container-fluid mt-3">
        <!-- 搜索表单 -->
        <div class="card mb-3">
            <div class="card-body">
                <form id="searchForm" class="row g-3">
                    <div class="col-md-3">
                        <label class="form-label">景区名称</label>
                        <input type="text" class="form-control" name="tourismName">
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">设备编码</label>
                        <input type="text" class="form-control" name="deviceCode">
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">算法类型</label>
                        <select class="form-select" name="algName">
                            <option value="">全部</option>
                            <option value="crowd_count">人群计数</option>
                            <option value="crowd_density">高密度计数</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">时间范围</label>
                        <input type="text" class="form-control" name="dateRange">
                    </div>
                    <div class="col-12">
                        <button type="submit" class="btn btn-primary">查询</button>
                        <button type="reset" class="btn btn-secondary">重置</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- 图表展示区 -->
        <div class="row">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">人群密度趋势</h5>
                        <div id="densityChart" class="chart-container"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">人数统计趋势</h5>
                        <div id="countChart" class="chart-container"></div>
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
                            <th>设备编码</th>
                            <th>人数</th>
                            <th>密度</th>
                            <th>算法类型</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody id="dataTable">
                    </tbody>
                </table>
                <nav>
                    <ul class="pagination justify-content-end" id="pagination">
                    </ul>
                </nav>
            </div>
        </div>
    </div>

    <!-- 加载中遮罩 -->
    <div class="loading-overlay" id="loadingOverlay">
        <div class="spinner-border text-primary" role="status">
            <span class="visually-hidden">加载中...</span>
        </div>
    </div>

    <!-- 图片预览模态框 -->
    <div class="modal fade" id="imageModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">全景图预览</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <img src="" class="img-fluid" id="previewImage">
                </div>
            </div>
        </div>
    </div>

    <script src="/static/js/jquery.min.js"></script>
    <script src="/static/js/bootstrap.bundle.min.js"></script>
    <script src="/static/js/moment.min.js"></script>
    <script src="/static/js/daterangepicker.min.js"></script>
    <script src="/static/js/echarts.min.js"></script>
    <script>
        $(document).ready(function() {
            // 初始化日期选择器
            $('input[name="dateRange"]').daterangepicker({
                locale: {
                    format: 'YYYY-MM-DD'
                }
            });

            // 初始化图表
            const densityChart = echarts.init(document.getElementById('densityChart'));
            const countChart = echarts.init(document.getElementById('countChart'));

            // 加载数据的函数
            function loadData(page = 1) {
                const formData = new FormData(document.getElementById('searchForm'));
                formData.append('page', page);

                $.ajax({
                    url: '/api/crowd/statistics/list',
                    data: Object.fromEntries(formData),
                    success: function(response) {
                        updateTable(response.data);
                        updateCharts(response.data);
                        updatePagination(response.total, page);
                    },
                    error: function(xhr) {
                        alert('加载数据失败：' + xhr.responseText);
                    }
                });
            }

            // 更新表格
            function updateTable(data) {
                const tbody = $('#dataTable');
                tbody.empty();

                data.forEach(item => {
                    tbody.append(`
                        <tr>
                            <td>${moment(item.recordTime).format('YYYY-MM-DD HH:mm:ss')}</td>
                            <td>${item.tourismName}</td>
                            <td>${item.deviceCode}</td>
                            <td>${item.count}</td>
                            <td>${item.density}</td>
                            <td>${item.algName}</td>
                            <td>
                                <button class="btn btn-sm btn-primary preview-image" 
                                        data-url="${item.imageUrl}">
                                    查看图片
                                </button>
                            </td>
                        </tr>
                    `);
                });
            }

            // 更新图表
            function updateCharts(data) {
                const times = data.map(item => moment(item.recordTime).format('HH:mm'));
                const densities = data.map(item => item.density);
                const counts = data.map(item => item.count);

                densityChart.setOption({
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: times
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: densities,
                        type: 'line',
                        smooth: true
                    }]
                });

                countChart.setOption({
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: times
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: counts,
                        type: 'line',
                        smooth: true
                    }]
                });
            }

            // 更新分页
            function updatePagination(total, currentPage) {
                const pagination = $('#pagination');
                pagination.empty();

                const pageCount = Math.ceil(total / 10);
                for (let i = 1; i <= pageCount; i++) {
                    pagination.append(`
                        <li class="page-item ${i === currentPage ? 'active' : ''}">
                            <a class="page-link" href="#" data-page="${i}">${i}</a>
                        </li>
                    `);
                }
            }

            // 绑定事件处理器
            $('#searchForm').on('submit', function(e) {
                e.preventDefault();
                loadData();
            });

            $(document).on('click', '.page-link', function(e) {
                e.preventDefault();
                loadData($(this).data('page'));
            });

            $(document).on('click', '.preview-image', function() {
                const imageUrl = $(this).data('url');
                $('#previewImage').attr('src', imageUrl);
                $('#imageModal').modal('show');
            });

            // 初始加载
            loadData();
        });
    </script>
</body>
</html> 