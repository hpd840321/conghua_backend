<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>告警管理</title>
    <link href="<c:url value='/static/css/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/static/css/daterangepicker.css'/>" rel="stylesheet">
    <style>
        .chart-container {
            height: 300px;
            margin-bottom: 20px;
        }
        .alert-card {
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-bottom: 15px;
        }
        .alert-high {
            border-left: 4px solid #dc3545;
        }
        .alert-medium {
            border-left: 4px solid #ffc107;
        }
        .alert-low {
            border-left: 4px solid #28a745;
        }
    </style>
</head>
<body>
    <div class="container-fluid mt-4">
        <!-- 搜索条件 -->
        <div class="card mb-4">
            <div class="card-body">
                <form id="searchForm">
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>景区名称</label>
                                <input type="text" class="form-control" name="tourismName">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>设备编码</label>
                                <input type="text" class="form-control" name="deviceCode">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>告警类型</label>
                                <select class="form-control" name="alertType">
                                    <option value="">全部</option>
                                    <option value="DENSITY">密度告警</option>
                                    <option value="COUNT">人数告警</option>
                                    <option value="DEVICE">设备告警</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>告警状态</label>
                                <select class="form-control" name="alertStatus">
                                    <option value="">全部</option>
                                    <option value="PENDING">待处理</option>
                                    <option value="PROCESSING">处理中</option>
                                    <option value="RESOLVED">已解决</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row mt-3">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>告警等级</label>
                                <select class="form-control" name="alertLevel">
                                    <option value="">全部</option>
                                    <option value="HIGH">高</option>
                                    <option value="MEDIUM">中</option>
                                    <option value="LOW">低</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>时间范围</label>
                                <input type="text" class="form-control" id="dateRange" name="dateRange">
                            </div>
                        </div>
                        <div class="col-md-3 d-flex align-items-end">
                            <button type="button" class="btn btn-primary mr-2" onclick="searchAlerts()">搜索</button>
                            <button type="button" class="btn btn-secondary" onclick="resetForm()">重置</button>
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
                        <h2 class="card-text" id="totalAlerts">0</h2>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card bg-warning text-white">
                    <div class="card-body">
                        <h5 class="card-title">待处理告警</h5>
                        <h2 class="card-text" id="pendingAlerts">0</h2>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card bg-info text-white">
                    <div class="card-body">
                        <h5 class="card-title">处理中告警</h5>
                        <h2 class="card-text" id="processingAlerts">0</h2>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card bg-success text-white">
                    <div class="card-body">
                        <h5 class="card-title">已解决告警</h5>
                        <h2 class="card-text" id="resolvedAlerts">0</h2>
                    </div>
                </div>
            </div>
        </div>

        <!-- 图表区域 -->
        <div class="row mb-4">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">告警类型分布</h5>
                        <div id="alertTypeChart" class="chart-container"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">告警等级分布</h5>
                        <div id="alertLevelChart" class="chart-container"></div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 告警列表 -->
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">告警列表</h5>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>景区名称</th>
                                <th>设备编码</th>
                                <th>告警类型</th>
                                <th>告警等级</th>
                                <th>告警状态</th>
                                <th>告警时间</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody id="alertTableBody">
                        </tbody>
                    </table>
                </div>
                <!-- 分页 -->
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-end" id="pagination">
                    </ul>
                </nav>
            </div>
        </div>
    </div>

    <!-- 处理告警模态框 -->
    <div class="modal fade" id="handleAlertModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">处理告警</h5>
                    <button type="button" class="close" data-dismiss="modal">
                        <span>&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="handleAlertForm">
                        <input type="hidden" id="alertId">
                        <div class="form-group">
                            <label>处理意见</label>
                            <textarea class="form-control" id="handleComment" rows="3" required></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" onclick="submitHandle()">确定</button>
                </div>
            </div>
        </div>
    </div>

    <script src="<c:url value='/static/js/jquery.min.js'/>"></script>
    <script src="<c:url value='/static/js/bootstrap.bundle.min.js'/>"></script>
    <script src="<c:url value='/static/js/moment.min.js'/>"></script>
    <script src="<c:url value='/static/js/daterangepicker.js'/>"></script>
    <script src="<c:url value='/static/js/echarts.min.js'/>"></script>
    <script>
        let currentPage = 1;
        let pageSize = 10;
        let typeChart = null;
        let levelChart = null;

        $(document).ready(function() {
            initDateRangePicker();
            initCharts();
            loadAlertStatistics();
            searchAlerts();
        });

        function initDateRangePicker() {
            $('#dateRange').daterangepicker({
                startDate: moment().subtract(7, 'days'),
                endDate: moment(),
                ranges: {
                    '今天': [moment(), moment()],
                    '昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                    '最近7天': [moment().subtract(6, 'days'), moment()],
                    '最近30天': [moment().subtract(29, 'days'), moment()],
                    '本月': [moment().startOf('month'), moment().endOf('month')],
                    '上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                },
                locale: {
                    format: 'YYYY-MM-DD',
                    applyLabel: '确定',
                    cancelLabel: '取消',
                    customRangeLabel: '自定义',
                    daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
                    monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
                               '七月', '八月', '九月', '十月', '十一月', '十二月']
                }
            });
        }

        function initCharts() {
            typeChart = echarts.init(document.getElementById('alertTypeChart'));
            levelChart = echarts.init(document.getElementById('alertLevelChart'));
            
            window.addEventListener('resize', function() {
                typeChart.resize();
                levelChart.resize();
            });
        }

        function loadAlertStatistics() {
            $.get('/api/alert/statistics', function(data) {
                if (data.code === 200) {
                    $('#totalAlerts').text(data.data.total);
                    $('#pendingAlerts').text(data.data.pending);
                    $('#processingAlerts').text(data.data.processing);
                    $('#resolvedAlerts').text(data.data.resolved);
                }
            });

            $.get('/api/alert/type/distribution', function(data) {
                if (data.code === 200) {
                    updateTypeChart(data.data);
                }
            });

            $.get('/api/alert/level/distribution', function(data) {
                if (data.code === 200) {
                    updateLevelChart(data.data);
                }
            });
        }

        function updateTypeChart(data) {
            const option = {
                tooltip: {
                    trigger: 'item',
                    formatter: '{b}: {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['密度告警', '人数告警', '设备告警']
                },
                series: [{
                    type: 'pie',
                    radius: '60%',
                    center: ['60%', '50%'],
                    data: [
                        {name: '密度告警', value: data.density},
                        {name: '人数告警', value: data.count},
                        {name: '设备告警', value: data.device}
                    ],
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }]
            };
            typeChart.setOption(option);
        }

        function updateLevelChart(data) {
            const option = {
                tooltip: {
                    trigger: 'item',
                    formatter: '{b}: {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['高', '中', '低']
                },
                series: [{
                    type: 'pie',
                    radius: '60%',
                    center: ['60%', '50%'],
                    data: [
                        {name: '高', value: data.high, itemStyle: {color: '#dc3545'}},
                        {name: '中', value: data.medium, itemStyle: {color: '#ffc107'}},
                        {name: '低', value: data.low, itemStyle: {color: '#28a745'}}
                    ],
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }]
            };
            levelChart.setOption(option);
        }

        function searchAlerts() {
            const params = {
                page: currentPage,
                size: pageSize,
                tourismName: $('input[name="tourismName"]').val(),
                deviceCode: $('input[name="deviceCode"]').val(),
                alertType: $('select[name="alertType"]').val(),
                alertStatus: $('select[name="alertStatus"]').val(),
                alertLevel: $('select[name="alertLevel"]').val()
            };

            const dateRange = $('#dateRange').val().split(' - ');
            if (dateRange.length === 2) {
                params.startTime = dateRange[0];
                params.endTime = dateRange[1];
            }

            $.get('/api/alert/page', params, function(data) {
                if (data.code === 200) {
                    updateAlertTable(data.data);
                    updatePagination(data.data);
                }
            });
        }

        function updateAlertTable(pageData) {
            const tbody = $('#alertTableBody');
            tbody.empty();

            pageData.records.forEach(function(alert) {
                const tr = $('<tr>');
                tr.append($('<td>').text(alert.id));
                tr.append($('<td>').text(alert.tourismName));
                tr.append($('<td>').text(alert.deviceCode));
                tr.append($('<td>').text(getAlertTypeText(alert.alertType)));
                tr.append($('<td>').text(getAlertLevelText(alert.alertLevel)));
                tr.append($('<td>').text(getAlertStatusText(alert.alertStatus)));
                tr.append($('<td>').text(alert.createTime));
                
                const actions = $('<td>');
                if (alert.alertStatus === 'PENDING') {
                    actions.append(
                        $('<button>')
                            .addClass('btn btn-sm btn-primary mr-2')
                            .text('处理')
                            .click(function() { showHandleModal(alert.id); })
                    );
                }
                actions.append(
                    $('<button>')
                        .addClass('btn btn-sm btn-info')
                        .text('详情')
                        .click(function() { window.location.href = '/alert/detail?id=' + alert.id; })
                );
                tr.append(actions);

                tbody.append(tr);
            });
        }

        function updatePagination(pageData) {
            const pagination = $('#pagination');
            pagination.empty();

            // 上一页
            pagination.append(
                $('<li>')
                    .addClass('page-item' + (pageData.current === 1 ? ' disabled' : ''))
                    .append(
                        $('<a>')
                            .addClass('page-link')
                            .attr('href', '#')
                            .text('上一页')
                            .click(function(e) {
                                e.preventDefault();
                                if (pageData.current > 1) {
                                    currentPage = pageData.current - 1;
                                    searchAlerts();
                                }
                            })
                    )
            );

            // 页码
            for (let i = 1; i <= pageData.pages; i++) {
                pagination.append(
                    $('<li>')
                        .addClass('page-item' + (i === pageData.current ? ' active' : ''))
                        .append(
                            $('<a>')
                                .addClass('page-link')
                                .attr('href', '#')
                                .text(i)
                                .click(function(e) {
                                    e.preventDefault();
                                    currentPage = i;
                                    searchAlerts();
                                })
                        )
                );
            }

            // 下一页
            pagination.append(
                $('<li>')
                    .addClass('page-item' + (pageData.current === pageData.pages ? ' disabled' : ''))
                    .append(
                        $('<a>')
                            .addClass('page-link')
                            .attr('href', '#')
                            .text('下一页')
                            .click(function(e) {
                                e.preventDefault();
                                if (pageData.current < pageData.pages) {
                                    currentPage = pageData.current + 1;
                                    searchAlerts();
                                }
                            })
                    )
            );
        }

        function getAlertTypeText(type) {
            const types = {
                'DENSITY': '密度告警',
                'COUNT': '人数告警',
                'DEVICE': '设备告警'
            };
            return types[type] || type;
        }

        function getAlertLevelText(level) {
            const levels = {
                'HIGH': '高',
                'MEDIUM': '中',
                'LOW': '低'
            };
            return levels[level] || level;
        }

        function getAlertStatusText(status) {
            const statuses = {
                'PENDING': '待处理',
                'PROCESSING': '处理中',
                'RESOLVED': '已解决'
            };
            return statuses[status] || status;
        }

        function resetForm() {
            $('#searchForm')[0].reset();
            $('#dateRange').data('daterangepicker').setStartDate(moment().subtract(7, 'days'));
            $('#dateRange').data('daterangepicker').setEndDate(moment());
            currentPage = 1;
            searchAlerts();
        }

        function showHandleModal(alertId) {
            $('#alertId').val(alertId);
            $('#handleComment').val('');
            $('#handleAlertModal').modal('show');
        }

        function submitHandle() {
            const alertId = $('#alertId').val();
            const comment = $('#handleComment').val();

            if (!comment) {
                alert('请输入处理意见');
                return;
            }

            $.ajax({
                url: '/api/alert/handle/' + alertId,
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    comment: comment
                }),
                success: function(data) {
                    if (data.code === 200) {
                        $('#handleAlertModal').modal('hide');
                        searchAlerts();
                        loadAlertStatistics();
                    } else {
                        alert(data.message || '处理失败');
                    }
                },
                error: function() {
                    alert('处理失败，请稍后重试');
                }
            });
        }
    </script>
</body>
</html> 