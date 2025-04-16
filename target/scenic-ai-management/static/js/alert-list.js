// 页面加载完成后初始化
$(document).ready(function() {
    // 初始化日期时间选择器
    initDateTimePickers();
    
    // 加载告警统计数据
    loadAlertStatistics();
    
    // 加载告警列表数据
    loadAlertList(1);
    
    // 绑定表单提交事件
    $('#searchForm').on('submit', function(e) {
        e.preventDefault();
        loadAlertList(1);
    });
    
    // 绑定重置按钮事件
    $('#searchForm button[type="reset"]').on('click', function() {
        setTimeout(function() {
            loadAlertList(1);
        }, 0);
    });
    
    // 绑定处理告警按钮事件
    $('#processAlertBtn').on('click', function() {
        const alertId = $(this).data('alertId');
        processAlert(alertId);
    });
});

// 初始化日期时间选择器
function initDateTimePickers() {
    $('input[name="startTime"], input[name="endTime"]').datetimepicker({
        format: 'YYYY-MM-DD HH:mm:ss',
        icons: {
            time: 'fas fa-clock',
            date: 'fas fa-calendar',
            up: 'fas fa-chevron-up',
            down: 'fas fa-chevron-down',
            previous: 'fas fa-chevron-left',
            next: 'fas fa-chevron-right',
            today: 'fas fa-calendar-check',
            clear: 'fas fa-trash',
            close: 'fas fa-times'
        }
    });
}

// 加载告警统计数据
function loadAlertStatistics() {
    $.ajax({
        url: '/api/alerts/statistics',
        method: 'GET',
        success: function(response) {
            if (response.success) {
                updateStatistics(response.data);
                renderCharts(response.data);
            } else {
                showError('加载统计数据失败：' + response.message);
            }
        },
        error: function() {
            showError('加载统计数据失败，请稍后重试');
        }
    });
}

// 更新统计数据
function updateStatistics(data) {
    $('#totalAlerts').text(data.totalAlerts || 0);
    $('#pendingAlerts').text(data.pendingAlerts || 0);
    $('#highPriorityAlerts').text(data.highPriorityAlerts || 0);
    $('#todayAlerts').text(data.todayAlerts || 0);
}

// 渲染图表
function renderCharts(data) {
    // 渲染告警时段分布图表
    const timeDistributionChart = echarts.init(document.getElementById('alertTimeDistributionChart'));
    timeDistributionChart.setOption({
        title: {
            text: '告警时段分布'
        },
        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
            type: 'category',
            data: data.timeDistribution.map(item => item.hour + '时')
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: data.timeDistribution.map(item => item.count),
            type: 'line',
            smooth: true
        }]
    });
    
    // 渲染告警类型分布图表
    const typeDistributionChart = echarts.init(document.getElementById('alertTypeDistributionChart'));
    typeDistributionChart.setOption({
        title: {
            text: '告警类型分布'
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left'
        },
        series: [{
            type: 'pie',
            radius: '50%',
            data: data.typeDistribution.map(item => ({
                name: item.type,
                value: item.count
            })),
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }]
    });
    
    // 监听窗口大小变化，调整图表大小
    window.addEventListener('resize', function() {
        timeDistributionChart.resize();
        typeDistributionChart.resize();
    });
}

// 加载告警列表
function loadAlertList(page) {
    const formData = $('#searchForm').serialize();
    
    $.ajax({
        url: '/api/alerts/list?page=' + page + '&' + formData,
        method: 'GET',
        success: function(response) {
            if (response.success) {
                renderAlertList(response.data.list);
                renderPagination(response.data.total, page);
            } else {
                showError('加载告警列表失败：' + response.message);
            }
        },
        error: function() {
            showError('加载告警列表失败，请稍后重试');
        }
    });
}

// 渲染告警列表
function renderAlertList(alerts) {
    const tbody = $('#alertList');
    tbody.empty();
    
    alerts.forEach(function(alert) {
        const tr = $('<tr>');
        tr.append($('<td>').text(alert.tourismName));
        tr.append($('<td>').text(alert.deviceName));
        tr.append($('<td>').text(getAlertTypeText(alert.alertType)));
        tr.append($('<td>').html(getAlertLevelBadge(alert.alertLevel)));
        tr.append($('<td>').html(getAlertStatusBadge(alert.alertStatus)));
        tr.append($('<td>').text(formatDateTime(alert.recordTime)));
        
        const actions = $('<td>');
        actions.append($('<button>')
            .addClass('btn btn-sm btn-info me-2')
            .text('详情')
            .on('click', function() {
                showAlertDetail(alert);
            }));
            
        if (alert.alertStatus === 0) {
            actions.append($('<button>')
                .addClass('btn btn-sm btn-primary')
                .text('处理')
                .on('click', function() {
                    processAlert(alert.id);
                }));
        }
        
        tr.append(actions);
        tbody.append(tr);
    });
}

// 渲染分页
function renderPagination(total, currentPage) {
    const pagination = $('#pagination');
    pagination.empty();
    
    const pageSize = 10;
    const totalPages = Math.ceil(total / pageSize);
    
    // 上一页
    pagination.append($('<li>')
        .addClass('page-item' + (currentPage === 1 ? ' disabled' : ''))
        .append($('<a>')
            .addClass('page-link')
            .text('上一页')
            .on('click', function() {
                if (currentPage > 1) {
                    loadAlertList(currentPage - 1);
                }
            })));
    
    // 页码
    for (let i = 1; i <= totalPages; i++) {
        pagination.append($('<li>')
            .addClass('page-item' + (i === currentPage ? ' active' : ''))
            .append($('<a>')
                .addClass('page-link')
                .text(i)
                .on('click', function() {
                    loadAlertList(i);
                })));
    }
    
    // 下一页
    pagination.append($('<li>')
        .addClass('page-item' + (currentPage === totalPages ? ' disabled' : ''))
        .append($('<a>')
            .addClass('page-link')
            .text('下一页')
            .on('click', function() {
                if (currentPage < totalPages) {
                    loadAlertList(currentPage + 1);
                }
            })));
}

// 显示告警详情
function showAlertDetail(alert) {
    $('#detailTourismName').text(alert.tourismName);
    $('#detailDeviceName').text(alert.deviceName);
    $('#detailAlertType').text(getAlertTypeText(alert.alertType));
    $('#detailAlertLevel').html(getAlertLevelBadge(alert.alertLevel));
    $('#detailAlertStatus').html(getAlertStatusBadge(alert.alertStatus));
    $('#detailRecordTime').text(formatDateTime(alert.recordTime));
    $('#detailDescription').text(alert.description);
    $('#detailImage').attr('src', alert.imageUrl);
    
    $('#processAlertBtn')
        .data('alertId', alert.id)
        .toggle(alert.alertStatus === 0);
    
    $('#alertDetailModal').modal('show');
}

// 处理告警
function processAlert(alertId) {
    $.ajax({
        url: '/api/alerts/process/' + alertId,
        method: 'POST',
        success: function(response) {
            if (response.success) {
                showSuccess('告警处理成功');
                $('#alertDetailModal').modal('hide');
                loadAlertStatistics();
                loadAlertList(1);
            } else {
                showError('告警处理失败：' + response.message);
            }
        },
        error: function() {
            showError('告警处理失败，请稍后重试');
        }
    });
}

// 获取告警类型文本
function getAlertTypeText(type) {
    const types = {
        'CROWD_DENSITY': '人群聚集',
        'CROWD_FLOW': '人群流动',
        'ABNORMAL_BEHAVIOR': '异常行为'
    };
    return types[type] || type;
}

// 获取告警级别徽章
function getAlertLevelBadge(level) {
    const levels = {
        1: ['低', 'secondary'],
        2: ['中', 'warning'],
        3: ['高', 'danger']
    };
    const [text, type] = levels[level] || ['未知', 'secondary'];
    return `<span class="badge bg-${type}">${text}</span>`;
}

// 获取告警状态徽章
function getAlertStatusBadge(status) {
    const statuses = {
        0: ['待处理', 'warning'],
        1: ['已处理', 'success']
    };
    const [text, type] = statuses[status] || ['未知', 'secondary'];
    return `<span class="badge bg-${type}">${text}</span>`;
}

// 格式化日期时间
function formatDateTime(dateTimeStr) {
    return moment(dateTimeStr).format('YYYY-MM-DD HH:mm:ss');
}

// 显示成功消息
function showSuccess(message) {
    toastr.success(message);
}

// 显示错误消息
function showError(message) {
    toastr.error(message);
} 