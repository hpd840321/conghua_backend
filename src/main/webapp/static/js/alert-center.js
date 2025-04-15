/**
 * 告警中心JavaScript
 */
$(function() {
    // 初始化日期选择器
    initDatePicker();
    
    // 初始化下拉选择框
    initSelect();
    
    // 加载统计数据
    loadStatistics();
    
    // 加载告警列表
    loadAlertList();
    
    // 加载告警时间分布图表
    loadAlertTimeChart();
    
    // 加载告警类型分布图表
    loadAlertTypeChart();
    
    // 绑定搜索按钮事件
    $('#searchBtn').click(function() {
        loadAlertList();
    });
    
    // 绑定重置按钮事件
    $('#resetBtn').click(function() {
        resetFilter();
    });
});

/**
 * 初始化日期选择器
 */
function initDatePicker() {
    $('#startTime, #endTime').datetimepicker({
        format: 'YYYY-MM-DD HH:mm:ss',
        locale: 'zh-cn',
        sideBySide: true
    });
}

/**
 * 初始化下拉选择框
 */
function initSelect() {
    // 告警类型下拉框
    $.ajax({
        url: '/alert/types',
        type: 'GET',
        success: function(data) {
            if (data.code === 0) {
                var html = '<option value="">全部</option>';
                $.each(data.data, function(index, item) {
                    html += '<option value="' + item.id + '">' + item.name + '</option>';
                });
                $('#alertType').html(html);
            }
        }
    });
    
    // 告警级别下拉框
    var levelHtml = '<option value="">全部</option>' +
                    '<option value="1">低</option>' +
                    '<option value="2">中</option>' +
                    '<option value="3">高</option>';
    $('#alertLevel').html(levelHtml);
    
    // 告警状态下拉框
    var statusHtml = '<option value="">全部</option>' +
                     '<option value="0">未处理</option>' +
                     '<option value="1">已处理</option>';
    $('#alertStatus').html(statusHtml);
}

/**
 * 加载统计数据
 */
function loadStatistics() {
    $.ajax({
        url: '/alert/statistics',
        type: 'GET',
        success: function(data) {
            if (data.code === 0) {
                $('#totalAlerts').text(data.data.totalAlerts);
                $('#pendingAlerts').text(data.data.pendingAlerts);
                $('#highPriorityAlerts').text(data.data.highPriorityAlerts);
                $('#todayAlerts').text(data.data.todayAlerts);
            }
        }
    });
}

/**
 * 加载告警列表
 */
function loadAlertList() {
    var params = {
        tourismName: $('#tourismName').val(),
        deviceCode: $('#deviceCode').val(),
        alertType: $('#alertType').val(),
        alertLevel: $('#alertLevel').val(),
        alertStatus: $('#alertStatus').val(),
        startTime: $('#startTime').val(),
        endTime: $('#endTime').val(),
        pageNum: $('#pageNum').val() || 1,
        pageSize: $('#pageSize').val() || 10
    };
    
    $.ajax({
        url: '/alert/list',
        type: 'GET',
        data: params,
        success: function(data) {
            if (data.code === 0) {
                renderAlertList(data.data);
                renderPagination(data.data);
            } else {
                layer.msg(data.msg, {icon: 2});
            }
        }
    });
}

/**
 * 渲染告警列表
 */
function renderAlertList(data) {
    var html = '';
    if (data.list && data.list.length > 0) {
        $.each(data.list, function(index, item) {
            html += '<tr>';
            html += '<td>' + (item.tourismName || '-') + '</td>';
            html += '<td>' + (item.deviceName || '-') + '</td>';
            html += '<td>' + (item.alertTypeName || '-') + '</td>';
            html += '<td><span class="alert-level-' + item.alertLevel + '">' + getAlertLevelText(item.alertLevel) + '</span></td>';
            html += '<td><span class="alert-status-' + item.alertStatus + '">' + getAlertStatusText(item.alertStatus) + '</span></td>';
            html += '<td>' + (item.recordTime || '-') + '</td>';
            html += '<td>';
            html += '<button type="button" class="btn btn-sm btn-info" onclick="viewAlertDetail(' + item.id + ')">查看</button>';
            if (item.alertStatus === 0) {
                html += ' <button type="button" class="btn btn-sm btn-success" onclick="handleAlert(' + item.id + ')">处理</button>';
            }
            html += '</td>';
            html += '</tr>';
        });
    } else {
        html = '<tr><td colspan="7" class="text-center">暂无数据</td></tr>';
    }
    $('#alertListBody').html(html);
}

/**
 * 渲染分页
 */
function renderPagination(data) {
    var html = '';
    if (data.pages > 1) {
        html += '<ul class="pagination">';
        // 上一页
        html += '<li class="page-item ' + (data.pageNum <= 1 ? 'disabled' : '') + '">';
        html += '<a class="page-link" href="javascript:void(0);" onclick="changePage(' + (data.pageNum - 1) + ')">上一页</a>';
        html += '</li>';
        
        // 页码
        for (var i = 1; i <= data.pages; i++) {
            html += '<li class="page-item ' + (data.pageNum === i ? 'active' : '') + '">';
            html += '<a class="page-link" href="javascript:void(0);" onclick="changePage(' + i + ')">' + i + '</a>';
            html += '</li>';
        }
        
        // 下一页
        html += '<li class="page-item ' + (data.pageNum >= data.pages ? 'disabled' : '') + '">';
        html += '<a class="page-link" href="javascript:void(0);" onclick="changePage(' + (data.pageNum + 1) + ')">下一页</a>';
        html += '</li>';
        html += '</ul>';
    }
    $('#pagination').html(html);
}

/**
 * 切换页码
 */
function changePage(pageNum) {
    $('#pageNum').val(pageNum);
    loadAlertList();
}

/**
 * 重置筛选条件
 */
function resetFilter() {
    $('#tourismName').val('');
    $('#deviceCode').val('');
    $('#alertType').val('');
    $('#alertLevel').val('');
    $('#alertStatus').val('');
    $('#startTime').val('');
    $('#endTime').val('');
    $('#pageNum').val(1);
    loadAlertList();
}

/**
 * 查看告警详情
 */
function viewAlertDetail(id) {
    $.ajax({
        url: '/alert/detail/' + id,
        type: 'GET',
        success: function(data) {
            if (data.code === 0) {
                var alert = data.data;
                $('#detailTourismName').text(alert.tourismName || '-');
                $('#detailDeviceName').text(alert.deviceName || '-');
                $('#detailAlertType').text(alert.alertTypeName || '-');
                $('#detailAlertLevel').text(getAlertLevelText(alert.alertLevel));
                $('#detailAlertStatus').text(getAlertStatusText(alert.alertStatus));
                $('#detailRecordTime').text(alert.recordTime || '-');
                $('#detailDescription').text(alert.description || '-');
                if (alert.imageUrl) {
                    $('#detailImage').attr('src', alert.imageUrl).show();
                } else {
                    $('#detailImage').hide();
                }
                $('#alertDetailModal').modal('show');
            } else {
                layer.msg(data.msg, {icon: 2});
            }
        }
    });
}

/**
 * 处理告警
 */
function handleAlert(id) {
    layer.confirm('确认处理该告警？', {
        btn: ['确定', '取消']
    }, function() {
        $.ajax({
            url: '/alert/handle/' + id,
            type: 'POST',
            success: function(data) {
                if (data.code === 0) {
                    layer.msg('处理成功', {icon: 1});
                    loadAlertList();
                    loadStatistics();
                } else {
                    layer.msg(data.msg, {icon: 2});
                }
            }
        });
    });
}

/**
 * 加载告警时间分布图表
 */
function loadAlertTimeChart() {
    $.ajax({
        url: '/alert/time-distribution',
        type: 'GET',
        success: function(data) {
            if (data.code === 0) {
                var chart = echarts.init(document.getElementById('alertTimeChart'));
                var option = {
                    title: {
                        text: '告警时间分布'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: data.data.times
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        name: '告警数量',
                        type: 'line',
                        data: data.data.counts,
                        smooth: true,
                        areaStyle: {}
                    }]
                };
                chart.setOption(option);
            }
        }
    });
}

/**
 * 加载告警类型分布图表
 */
function loadAlertTypeChart() {
    $.ajax({
        url: '/alert/type-distribution',
        type: 'GET',
        success: function(data) {
            if (data.code === 0) {
                var chart = echarts.init(document.getElementById('alertTypeChart'));
                var option = {
                    title: {
                        text: '告警类型分布'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b}: {c} ({d}%)'
                    },
                    series: [{
                        name: '告警类型',
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
                                fontSize: '16',
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: data.data
                    }]
                };
                chart.setOption(option);
            }
        }
    });
}

/**
 * 获取告警级别文本
 */
function getAlertLevelText(level) {
    switch (level) {
        case 1:
            return '低';
        case 2:
            return '中';
        case 3:
            return '高';
        default:
            return '-';
    }
}

/**
 * 获取告警状态文本
 */
function getAlertStatusText(status) {
    switch (status) {
        case 0:
            return '未处理';
        case 1:
            return '已处理';
        default:
            return '-';
    }
} 