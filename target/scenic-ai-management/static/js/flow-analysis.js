/**
 * 客流分析页面JavaScript
 */
$(function() {
    // 初始化日期时间选择器
    initDateTimePicker();
    
    // 初始化图表
    initCharts();
    
    // 绑定查询按钮事件
    $('#searchBtn').on('click', function() {
        loadData();
    });
    
    // 初始加载数据
    loadData();
});

/**
 * 初始化日期时间选择器
 */
function initDateTimePicker() {
    // 设置默认时间范围为最近7天
    const end = new Date();
    const start = new Date();
    start.setDate(start.getDate() - 7);
    
    $('#startTime').val(formatDateTime(start));
    $('#endTime').val(formatDateTime(end));
}

/**
 * 初始化图表
 */
function initCharts() {
    // 初始化客流方向分布图表
    const directionChart = echarts.init(document.getElementById('directionChart'));
    directionChart.setOption({
        title: {
            text: '客流方向分布',
            left: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
            orient: 'vertical',
            left: 10,
            data: ['进入', '离开']
        },
        series: [
            {
                name: '客流方向',
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: '18',
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: false
                },
                data: [
                    { value: 0, name: '进入' },
                    { value: 0, name: '离开' }
                ]
            }
        ]
    });
    
    // 初始化客流趋势图表
    const trendChart = echarts.init(document.getElementById('trendChart'));
    trendChart.setOption({
        title: {
            text: '客流趋势',
            left: 'center'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['客流量'],
            bottom: 0
        },
        xAxis: {
            type: 'category',
            data: []
        },
        yAxis: {
            type: 'value',
            name: '客流量'
        },
        series: [
            {
                name: '客流量',
                type: 'line',
                data: [],
                smooth: true,
                areaStyle: {}
            }
        ]
    });
    
    // 保存图表实例到全局变量
    window.directionChart = directionChart;
    window.trendChart = trendChart;
    
    // 监听窗口大小变化，调整图表大小
    $(window).resize(function() {
        directionChart.resize();
        trendChart.resize();
    });
}

/**
 * 加载数据
 */
function loadData() {
    // 获取查询参数
    const params = {
        tourismName: $('#tourismName').val(),
        deviceCode: $('#deviceCode').val(),
        flowDirection: $('#flowDirection').val(),
        startTime: $('#startTime').val(),
        endTime: $('#endTime').val(),
        pageNum: 1,
        pageSize: 10
    };
    
    // 显示加载中
    showLoading();
    
    // 加载统计数据
    loadStatistics(params);
    
    // 加载图表数据
    loadChartData(params);
    
    // 加载表格数据
    loadTableData(params);
}

/**
 * 加载统计数据
 */
function loadStatistics(params) {
    $.ajax({
        url: '/api/v1/flow-analysis/stats/overview',
        type: 'GET',
        data: {
            tourismName: params.tourismName,
            startTime: params.startTime,
            endTime: params.endTime
        },
        success: function(res) {
            if (res.code === 200) {
                const data = res.data;
                $('#totalFlowCount').text(data.totalFlow || 0);
                $('#inFlowCount').text(data.inFlow || 0);
                $('#outFlowCount').text(data.outFlow || 0);
                $('#recordCount').text(data.recordCount || 0);
            } else {
                showError('加载统计数据失败: ' + res.msg);
            }
        },
        error: function() {
            showError('加载统计数据失败，请稍后重试');
        }
    });
}

/**
 * 加载图表数据
 */
function loadChartData(params) {
    // 加载客流方向分布数据
    $.ajax({
        url: '/api/v1/flow-analysis/stats/direction-distribution',
        type: 'GET',
        data: {
            deviceCode: params.deviceCode,
            tourismName: params.tourismName,
            startTime: params.startTime,
            endTime: params.endTime
        },
        success: function(res) {
            if (res.code === 200) {
                updateDirectionChart(res.data);
            } else {
                showError('加载方向分布数据失败: ' + res.msg);
            }
        },
        error: function() {
            showError('加载方向分布数据失败，请稍后重试');
        }
    });
    
    // 加载客流趋势数据
    $.ajax({
        url: '/api/v1/flow-analysis/stats/trend',
        type: 'GET',
        data: {
            deviceCode: params.deviceCode,
            tourismName: params.tourismName,
            startTime: params.startTime,
            endTime: params.endTime
        },
        success: function(res) {
            if (res.code === 200) {
                updateTrendChart(res.data);
            } else {
                showError('加载趋势数据失败: ' + res.msg);
            }
        },
        error: function() {
            showError('加载趋势数据失败，请稍后重试');
        }
    });
}

/**
 * 更新客流方向分布图表
 */
function updateDirectionChart(data) {
    const chartData = [];
    
    // 处理数据
    data.forEach(item => {
        const direction = item.direction === 'IN' ? '进入' : '离开';
        chartData.push({
            name: direction,
            value: item.count
        });
    });
    
    // 更新图表
    window.directionChart.setOption({
        series: [{
            data: chartData
        }]
    });
}

/**
 * 更新客流趋势图表
 */
function updateTrendChart(data) {
    const xAxisData = [];
    const seriesData = [];
    
    // 处理数据
    data.forEach(item => {
        xAxisData.push(item.time);
        seriesData.push(item.total_count);
    });
    
    // 更新图表
    window.trendChart.setOption({
        xAxis: {
            data: xAxisData
        },
        series: [{
            data: seriesData
        }]
    });
}

/**
 * 加载表格数据
 */
function loadTableData(params) {
    $.ajax({
        url: '/api/v1/flow-analysis',
        type: 'GET',
        data: params,
        success: function(res) {
            if (res.code === 200) {
                renderTable(res.data);
                renderPagination(res.data);
            } else {
                showError('加载表格数据失败: ' + res.msg);
            }
            hideLoading();
        },
        error: function() {
            showError('加载表格数据失败，请稍后重试');
            hideLoading();
        }
    });
}

/**
 * 渲染表格数据
 */
function renderTable(data) {
    const tbody = $('#dataTable');
    tbody.empty();
    
    if (!data.list || data.list.length === 0) {
        tbody.append('<tr><td colspan="6" class="text-center">暂无数据</td></tr>');
        return;
    }
    
    data.list.forEach(item => {
        const tr = $('<tr></tr>');
        tr.append(`<td>${item.tourismName || '-'}</td>`);
        tr.append(`<td>${item.deviceName || '-'}</td>`);
        tr.append(`<td>${item.flowCount || 0}</td>`);
        tr.append(`<td>${item.flowDirection === 'IN' ? '进入' : '离开'}</td>`);
        tr.append(`<td>${formatDateTime(new Date(item.recordTime))}</td>`);
        tr.append(`
            <td>
                <button class="btn btn-sm btn-info" onclick="viewDetail(${item.id})">查看</button>
            </td>
        `);
        tbody.append(tr);
    });
}

/**
 * 渲染分页
 */
function renderPagination(data) {
    const pagination = $('#pagination');
    pagination.empty();
    
    if (!data.pages || data.pages <= 1) {
        return;
    }
    
    // 上一页
    const prevLi = $('<li class="page-item"></li>');
    if (data.pageNum <= 1) {
        prevLi.addClass('disabled');
    } else {
        prevLi.append(`<a class="page-link" href="javascript:void(0)" onclick="changePage(${data.pageNum - 1})">上一页</a>`);
    }
    pagination.append(prevLi);
    
    // 页码
    for (let i = 1; i <= data.pages; i++) {
        const li = $('<li class="page-item"></li>');
        if (i === data.pageNum) {
            li.addClass('active');
        }
        li.append(`<a class="page-link" href="javascript:void(0)" onclick="changePage(${i})">${i}</a>`);
        pagination.append(li);
    }
    
    // 下一页
    const nextLi = $('<li class="page-item"></li>');
    if (data.pageNum >= data.pages) {
        nextLi.addClass('disabled');
    } else {
        nextLi.append(`<a class="page-link" href="javascript:void(0)" onclick="changePage(${data.pageNum + 1})">下一页</a>`);
    }
    pagination.append(nextLi);
}

/**
 * 切换页码
 */
function changePage(pageNum) {
    const params = {
        tourismName: $('#tourismName').val(),
        deviceCode: $('#deviceCode').val(),
        flowDirection: $('#flowDirection').val(),
        startTime: $('#startTime').val(),
        endTime: $('#endTime').val(),
        pageNum: pageNum,
        pageSize: 10
    };
    
    showLoading();
    loadTableData(params);
}

/**
 * 查看详情
 */
function viewDetail(id) {
    $.ajax({
        url: '/api/v1/flow-analysis/' + id,
        type: 'GET',
        success: function(res) {
            if (res.code === 200) {
                const data = res.data;
                $('#detailTourismName').text(data.tourismName || '-');
                $('#detailDeviceName').text(data.deviceName || '-');
                $('#detailFlowCount').text(data.flowCount || 0);
                $('#detailFlowDirection').text(data.flowDirection === 'IN' ? '进入' : '离开');
                $('#detailRecordTime').text(formatDateTime(new Date(data.recordTime)));
                
                if (data.imageUrl) {
                    $('#detailImage').attr('src', data.imageUrl);
                } else {
                    $('#detailImage').attr('src', '');
                }
                
                $('#detailModal').modal('show');
            } else {
                showError('加载详情失败: ' + res.msg);
            }
        },
        error: function() {
            showError('加载详情失败，请稍后重试');
        }
    });
}

/**
 * 格式化日期时间
 */
function formatDateTime(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    
    return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
}

/**
 * 显示加载中
 */
function showLoading() {
    // 可以添加加载动画
}

/**
 * 隐藏加载中
 */
function hideLoading() {
    // 隐藏加载动画
}

/**
 * 显示错误信息
 */
function showError(message) {
    alert(message);
} 