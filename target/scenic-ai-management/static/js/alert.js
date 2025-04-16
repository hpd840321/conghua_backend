// 全局变量
let currentPage = 1;
let pageSize = 10;
let timeDistributionChart = null;
let typeDistributionChart = null;
let currentAlertId = null;

// 页面加载完成后初始化
$(document).ready(function() {
    // 初始化图表
    initCharts();
    // 加载下拉选项
    loadSelectOptions();
    // 加载数据
    loadData();
    // 加载图表数据
    loadChartData();
    // 绑定全选事件
    $('#selectAll').change(function() {
        $('input[name="alertCheckbox"]').prop('checked', $(this).prop('checked'));
    });
});

// 初始化图表
function initCharts() {
    // 初始化时段分布图表
    timeDistributionChart = echarts.init(document.getElementById('timeDistributionChart'));
    timeDistributionChart.setOption({
        title: {
            text: '告警时段分布'
        },
        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
            type: 'category',
            data: []
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            name: '告警数量',
            type: 'bar',
            data: []
        }]
    });

    // 初始化类型分布图表
    typeDistributionChart = echarts.init(document.getElementById('typeDistributionChart'));
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
            name: '告警类型',
            type: 'pie',
            radius: '50%',
            data: []
        }]
    });

    // 监听窗口大小变化，调整图表大小
    window.addEventListener('resize', function() {
        timeDistributionChart.resize();
        typeDistributionChart.resize();
    });
}

// 加载下拉选项
function loadSelectOptions() {
    // 加载景区列表
    $.get('/api/v1/tourism/list', function(data) {
        let tourismSelect = $('#tourismName');
        data.forEach(function(item) {
            tourismSelect.append($('<option></option>').val(item.name).text(item.name));
        });
    });

    // 加载设备列表
    $.get('/api/v1/device/list', function(data) {
        let deviceSelect = $('#deviceCode');
        data.forEach(function(item) {
            deviceSelect.append($('<option></option>').val(item.deviceCode).text(item.deviceName));
        });
    });

    // 加载告警类型列表
    $.get('/api/v1/alerts/types', function(data) {
        let typeSelect = $('#alertType');
        data.forEach(function(item) {
            typeSelect.append($('<option></option>').val(item.id).text(item.name));
        });
    });
}

// 加载数据
function loadData() {
    let params = {
        pageNum: currentPage,
        pageSize: pageSize,
        tourismName: $('#tourismName').val(),
        deviceCode: $('#deviceCode').val(),
        alertType: $('#alertType').val(),
        alertLevel: $('#alertLevel').val(),
        alertStatus: $('#alertStatus').val(),
        startTime: $('#startTime').val(),
        endTime: $('#endTime').val()
    };

    $.get('/api/v1/alerts/page', params, function(data) {
        renderTable(data.records);
        renderPagination(data.total);
        updateOverview(data.overview);
    });
}

// 渲染数据表格
function renderTable(data) {
    let tbody = $('#dataTable');
    tbody.empty();
    
    data.forEach(function(item) {
        let tr = $('<tr></tr>');
        tr.append($('<td></td>').html(
            '<input type="checkbox" name="alertCheckbox" value="' + item.id + '">'
        ));
        tr.append($('<td></td>').text(item.tourismName));
        tr.append($('<td></td>').text(item.deviceCode));
        tr.append($('<td></td>').text(item.deviceName));
        tr.append($('<td></td>').text(item.alertType));
        tr.append($('<td></td>').html(getAlertLevelBadge(item.alertLevel)));
        tr.append($('<td></td>').html(getAlertStatusBadge(item.alertStatus)));
        tr.append($('<td></td>').text(formatDateTime(item.recordTime)));
        tr.append($('<td></td>').html(
            '<button class="btn btn-info btn-sm" onclick="showAlertDetail(' + item.id + ')">查看</button> ' +
            '<button class="btn btn-success btn-sm" onclick="handleAlert(' + item.id + ')">处理</button>'
        ));
        tbody.append(tr);
    });
}

// 渲染分页
function renderPagination(total) {
    let totalPages = Math.ceil(total / pageSize);
    let pagination = $('#pagination');
    pagination.empty();

    // 上一页
    pagination.append($('<li class="page-item"></li>').append(
        $('<a class="page-link" href="javascript:void(0)"></a>')
            .text('上一页')
            .click(function() {
                if (currentPage > 1) {
                    currentPage--;
                    loadData();
                }
            })
    ));

    // 页码
    for (let i = 1; i <= totalPages; i++) {
        pagination.append($('<li class="page-item"></li>').append(
            $('<a class="page-link" href="javascript:void(0)"></a>')
                .text(i)
                .click(function() {
                    currentPage = i;
                    loadData();
                })
        ));
    }

    // 下一页
    pagination.append($('<li class="page-item"></li>').append(
        $('<a class="page-link" href="javascript:void(0)"></a>')
            .text('下一页')
            .click(function() {
                if (currentPage < totalPages) {
                    currentPage++;
                    loadData();
                }
            })
    ));
}

// 更新概览数据
function updateOverview(data) {
    $('#totalCount').text(data.totalCount || 0);
    $('#pendingCount').text(data.pendingCount || 0);
    $('#handledCount').text(data.handledCount || 0);
    $('#avgHandleTime').text(data.avgHandleTime || 0 + '分钟');
}

// 加载图表数据
function loadChartData() {
    let params = {
        tourismName: $('#tourismName').val(),
        deviceCode: $('#deviceCode').val(),
        startTime: $('#startTime').val(),
        endTime: $('#endTime').val()
    };

    // 加载时段分布数据
    $.get('/api/v1/alerts/distribution/time', params, function(data) {
        updateTimeDistributionChart(data);
    });

    // 加载类型分布数据
    $.get('/api/v1/alerts/distribution/type', params, function(data) {
        updateTypeDistributionChart(data);
    });
}

// 更新时段分布图表
function updateTimeDistributionChart(data) {
    let hours = data.map(item => item.hour);
    let counts = data.map(item => item.count);

    timeDistributionChart.setOption({
        xAxis: {
            data: hours
        },
        series: [{
            data: counts
        }]
    });
}

// 更新类型分布图表
function updateTypeDistributionChart(data) {
    let seriesData = data.map(item => ({
        name: item.type,
        value: item.count
    }));

    typeDistributionChart.setOption({
        series: [{
            data: seriesData
        }]
    });
}

// 显示告警详情
function showAlertDetail(id) {
    currentAlertId = id;
    $.get('/api/v1/alerts/' + id, function(data) {
        $('#alertImage').attr('src', data.imageUrl);
        $('#alertDescription').val(data.description);
        $('#alertModal').modal('show');
    });
}

// 处理告警
function handleAlert(id) {
    let description = $('#handleDescription').val();
    if (!description) {
        alert('请输入处理说明');
        return;
    }

    $.ajax({
        url: '/api/v1/alerts/' + id + '/handle',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            description: description
        }),
        success: function() {
            $('#alertModal').modal('hide');
            loadData();
            loadChartData();
        },
        error: function() {
            alert('处理失败，请重试');
        }
    });
}

// 批量处理告警
function batchHandle() {
    let ids = [];
    $('input[name="alertCheckbox"]:checked').each(function() {
        ids.push($(this).val());
    });

    if (ids.length === 0) {
        alert('请选择要处理的告警');
        return;
    }

    if (confirm('确定要批量处理选中的告警吗？')) {
        $.ajax({
            url: '/api/v1/alerts/batch/handle',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(ids),
            success: function() {
                loadData();
                loadChartData();
            },
            error: function() {
                alert('批量处理失败，请重试');
            }
        });
    }
}

// 搜索
function search() {
    currentPage = 1;
    loadData();
    loadChartData();
}

// 获取告警级别标签
function getAlertLevelBadge(level) {
    let badgeClass = '';
    let levelText = '';
    switch (level) {
        case 1:
            badgeClass = 'badge-success';
            levelText = '低';
            break;
        case 2:
            badgeClass = 'badge-warning';
            levelText = '中';
            break;
        case 3:
            badgeClass = 'badge-danger';
            levelText = '高';
            break;
        default:
            badgeClass = 'badge-secondary';
            levelText = '未知';
    }
    return '<span class="badge ' + badgeClass + '">' + levelText + '</span>';
}

// 获取告警状态标签
function getAlertStatusBadge(status) {
    let badgeClass = status === 0 ? 'badge-warning' : 'badge-success';
    let statusText = status === 0 ? '待处理' : '已处理';
    return '<span class="badge ' + badgeClass + '">' + statusText + '</span>';
}

// 格式化日期时间
function formatDateTime(dateTimeStr) {
    if (!dateTimeStr) return '';
    let date = new Date(dateTimeStr);
    return date.getFullYear() + '-' +
           String(date.getMonth() + 1).padStart(2, '0') + '-' +
           String(date.getDate()).padStart(2, '0') + ' ' +
           String(date.getHours()).padStart(2, '0') + ':' +
           String(date.getMinutes()).padStart(2, '0') + ':' +
           String(date.getSeconds()).padStart(2, '0');
} 