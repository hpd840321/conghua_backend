<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>告警统计</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/laydate.css">
    <style>
        .search-box {
            padding: 15px;
            background-color: #f8f9fa;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        .overview-card {
            padding: 20px;
            border-radius: 4px;
            margin-bottom: 20px;
            color: #fff;
        }
        .overview-card .number {
            font-size: 24px;
            font-weight: bold;
            margin: 10px 0;
        }
        .overview-card .title {
            font-size: 14px;
            opacity: 0.8;
        }
        .card-total { background-color: #007bff; }
        .card-pending { background-color: #dc3545; }
        .card-high { background-color: #ffc107; }
        .card-today { background-color: #28a745; }
        .chart-container {
            height: 400px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <!-- 搜索条件 -->
        <div class="search-box">
            <form id="searchForm" class="form-inline">
                <div class="form-group mx-sm-3 mb-2">
                    <input type="text" class="form-control" name="tourismName" placeholder="景区名称">
                </div>
                <div class="form-group mx-sm-3 mb-2">
                    <input type="text" class="form-control" name="deviceCode" placeholder="设备编码">
                </div>
                <div class="form-group mx-sm-3 mb-2">
                    <input type="text" class="form-control" id="startTime" name="startTime" placeholder="开始时间">
                </div>
                <div class="form-group mx-sm-3 mb-2">
                    <input type="text" class="form-control" id="endTime" name="endTime" placeholder="结束时间">
                </div>
                <button type="button" class="btn btn-primary mb-2" onclick="search()">搜索</button>
                <button type="button" class="btn btn-secondary mb-2 ml-2" onclick="reset()">重置</button>
            </form>
        </div>

        <!-- 统计概览 -->
        <div class="row">
            <div class="col-md-3">
                <div class="overview-card card-total">
                    <div class="title">总告警数</div>
                    <div class="number" id="totalCount">0</div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="overview-card card-pending">
                    <div class="title">待处理告警</div>
                    <div class="number" id="pendingCount">0</div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="overview-card card-high">
                    <div class="title">高优先级告警</div>
                    <div class="number" id="highCount">0</div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="overview-card card-today">
                    <div class="title">今日告警</div>
                    <div class="number" id="todayCount">0</div>
                </div>
            </div>
        </div>

        <!-- 图表展示 -->
        <div class="row">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        告警时段分布
                    </div>
                    <div class="card-body">
                        <div id="timeChart" class="chart-container"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        告警类型分布
                    </div>
                    <div class="card-body">
                        <div id="typeChart" class="chart-container"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/laydate.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
    <script>
        let timeChart = null;
        let typeChart = null;

        $(function() {
            // 初始化日期选择器
            initDatePicker();
            
            // 初始化图表
            initCharts();
            
            // 加载数据
            loadData();

            // 绑定搜索事件
            $('#searchForm').on('submit', function(e) {
                e.preventDefault();
                loadData();
            });
        });

        // 初始化日期选择器
        function initDatePicker() {
            laydate.render({
                elem: '#startTime',
                type: 'datetime',
                format: 'yyyy-MM-dd HH:mm:ss',
                trigger: 'click'
            });
            laydate.render({
                elem: '#endTime',
                type: 'datetime',
                format: 'yyyy-MM-dd HH:mm:ss',
                trigger: 'click'
            });
        }

        // 初始化图表
        function initCharts() {
            // 初始化时段分布图表
            timeChart = echarts.init(document.getElementById('timeChart'));
            timeChart.setOption({
                title: {
                    text: '告警时段分布',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    data: ['00:00', '02:00', '04:00', '06:00', '08:00', '10:00', '12:00', 
                           '14:00', '16:00', '18:00', '20:00', '22:00'],
                    axisLabel: {
                        interval: 0,
                        rotate: 30
                    }
                },
                yAxis: {
                    type: 'value',
                    name: '告警数量'
                },
                series: [{
                    name: '告警数量',
                    type: 'bar',
                    data: [],
                    itemStyle: {
                        color: '#007bff'
                    }
                }]
            });

            // 初始化类型分布图表
            typeChart = echarts.init(document.getElementById('typeChart'));
            typeChart.setOption({
                title: {
                    text: '告警类型分布',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b}: {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: []
                },
                series: [{
                    name: '告警类型',
                    type: 'pie',
                    radius: '65%',
                    center: ['50%', '60%'],
                    data: [],
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }]
            });

            // 监听窗口大小变化
            window.addEventListener('resize', function() {
                timeChart && timeChart.resize();
                typeChart && typeChart.resize();
            });
        }

        // 加载数据
        function loadData() {
            showLoading();
            const params = $('#searchForm').serialize();
            
            // 加载概览数据
            $.ajax({
                url: '${pageContext.request.contextPath}/alert/overview',
                type: 'GET',
                data: params,
                success: function(response) {
                    if (response.code === 200) {
                        updateOverview(response.data);
                    } else {
                        showToast('error', '加载概览数据失败：' + response.message);
                    }
                },
                error: function(xhr, status, error) {
                    showToast('error', '加载概览数据失败：' + error);
                }
            });

            // 加载时段分布数据
            $.ajax({
                url: '${pageContext.request.contextPath}/alert/time-distribution',
                type: 'GET',
                data: params,
                success: function(response) {
                    if (response.code === 200) {
                        updateTimeChart(response.data);
                    } else {
                        showToast('error', '加载时段分布数据失败：' + response.message);
                    }
                },
                error: function(xhr, status, error) {
                    showToast('error', '加载时段分布数据失败：' + error);
                }
            });

            // 加载类型分布数据
            $.ajax({
                url: '${pageContext.request.contextPath}/alert/type-distribution',
                type: 'GET',
                data: params,
                success: function(response) {
                    if (response.code === 200) {
                        updateTypeChart(response.data);
                    } else {
                        showToast('error', '加载类型分布数据失败：' + response.message);
                    }
                    hideLoading();
                },
                error: function(xhr, status, error) {
                    showToast('error', '加载类型分布数据失败：' + error);
                    hideLoading();
                }
            });
        }

        // 更新概览数据
        function updateOverview(data) {
            $('#totalCount').text(data.totalCount || 0);
            $('#pendingCount').text(data.pendingCount || 0);
            $('#highCount').text(data.highCount || 0);
            $('#todayCount').text(data.todayCount || 0);
        }

        // 更新时段分布图表
        function updateTimeChart(data) {
            const hours = data.map(item => item.hour);
            const counts = data.map(item => item.count);
            
            timeChart.setOption({
                xAxis: {
                    data: hours
                },
                series: [{
                    data: counts
                }]
            });
        }

        // 更新类型分布图表
        function updateTypeChart(data) {
            const types = data.map(item => ({
                name: getAlertTypeName(item.type),
                value: item.count
            }));
            
            typeChart.setOption({
                legend: {
                    data: types.map(item => item.name)
                },
                series: [{
                    data: types
                }]
            });
        }

        // 搜索
        function search() {
            loadData();
        }

        // 重置
        function reset() {
            $('#searchForm')[0].reset();
            loadData();
        }

        // 显示加载中
        function showLoading() {
            if (!$('.loading-overlay').length) {
                $('body').append('<div class="loading-overlay"><div class="loading-spinner"></div></div>');
            }
        }

        // 隐藏加载中
        function hideLoading() {
            $('.loading-overlay').remove();
        }

        // 显示提示信息
        function showToast(type, message) {
            const toast = $(`
                <div class="toast toast-${type}">
                    <div class="toast-icon">
                        <i class="fas fa-${type === 'success' ? 'check-circle' : type === 'error' ? 'times-circle' : 'exclamation-circle'}"></i>
                    </div>
                    <div class="toast-content">${message}</div>
                    <div class="toast-close">&times;</div>
                </div>
            `);

            if (!$('.toast-container').length) {
                $('body').append('<div class="toast-container"></div>');
            }

            $('.toast-container').append(toast);
            setTimeout(() => toast.remove(), 3000);

            toast.find('.toast-close').click(function() {
                toast.remove();
            });
        }

        // 获取告警类型名称
        function getAlertTypeName(type) {
            const types = {
                '1': '人员聚集',
                '2': '异常行为',
                '3': '设备离线'
            };
            return types[type] || '-';
        }
    </script>
</body>
</html> 