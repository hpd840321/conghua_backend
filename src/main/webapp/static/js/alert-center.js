$(function() {
    // 初始化日期范围选择器
    initDateRangePicker();
    
    // 初始化图表
    initCharts();
    
    // 加载数据
    loadData();
    
    // 绑定事件
    bindEvents();
});

// 初始化日期范围选择器
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
            format: 'YYYY-MM-DD HH:mm:ss',
            applyLabel: '确定',
            cancelLabel: '取消',
            fromLabel: '从',
            toLabel: '至',
            customRangeLabel: '自定义',
            daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
            monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
                '七月', '八月', '九月', '十月', '十一月', '十二月']
        }
    });
}

// 初始化图表
let timeChart, typeChart;
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
            data: []
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            name: '告警数量',
            type: 'bar',
            data: [],
            itemStyle: {
                color: '#1890ff'
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
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
                show: false,
                position: 'center'
            },
            emphasis: {
                label: {
                    show: true,
                    fontSize: '30',
                    fontWeight: 'bold'
                }
            },
            labelLine: {
                show: false
            },
            data: []
        }]
    });

    // 监听窗口大小变化，调整图表大小
    window.addEventListener('resize', function() {
        timeChart.resize();
        typeChart.resize();
    });
}

// 加载数据
function loadData(pageNum = 1) {
    const params = getSearchParams();
    params.pageNum = pageNum;
    params.pageSize = 10;

    // 显示加载动画
    showLoading();

    // 并行加载数据
    Promise.all([
        loadOverview(params),
        loadTimeDistribution(params),
        loadTypeDistribution(params),
        loadAlertList(params)
    ]).then(() => {
        // 隐藏加载动画
        hideLoading();
    }).catch(error => {
        console.error('加载数据失败:', error);
        hideLoading();
        showError('加载数据失败，请稍后重试');
    });
}

// 获取搜索参数
function getSearchParams() {
    const dateRange = $('#dateRange').val().split(' - ');
    return {
        tourismName: $('input[name="tourismName"]').val(),
        deviceCode: $('input[name="deviceCode"]').val(),
        alertType: $('select[name="alertType"]').val(),
        alertLevel: $('select[name="alertLevel"]').val(),
        alertStatus: $('select[name="alertStatus"]').val(),
        startTime: dateRange[0],
        endTime: dateRange[1]
    };
}

// 加载概览数据
function loadOverview(params) {
    return $.get('/api/v1/alerts/overview', params)
        .then(data => {
            $('#totalCount').text(data.totalCount || 0);
            $('#pendingCount').text(data.pendingCount || 0);
            $('#handledCount').text(data.handledCount || 0);
            $('#highLevelCount').text(data.highLevelCount || 0);
        });
}

// 加载时段分布数据
function loadTimeDistribution(params) {
    return $.get('/api/v1/alerts/time-distribution', params)
        .then(data => {
            const hours = data.map(item => item.hour + '时');
            const counts = data.map(item => item.count);
            
            timeChart.setOption({
                xAxis: {
                    data: hours
                },
                series: [{
                    data: counts
                }]
            });
        });
}

// 加载类型分布数据
function loadTypeDistribution(params) {
    return $.get('/api/v1/alerts/type-distribution', params)
        .then(data => {
            const legendData = data.map(item => formatAlertType(item.alertType));
            const seriesData = data.map(item => ({
                name: formatAlertType(item.alertType),
                value: item.count
            }));
            
            typeChart.setOption({
                legend: {
                    data: legendData
                },
                series: [{
                    data: seriesData
                }]
            });
        });
}

// 加载告警列表
function loadAlertList(params) {
    return $.get('/api/v1/alerts', params)
        .then(data => {
            renderTable(data.records);
            renderPagination(data);
        });
}

// 渲染表格
function renderTable(records) {
    const tbody = $('#alertTable');
    tbody.empty();
    
    if (!records || records.length === 0) {
        tbody.append('<tr><td colspan="10" class="text-center">暂无数据</td></tr>');
        return;
    }
    
    records.forEach(record => {
        tbody.append(`
            <tr>
                <td>
                    <input type="checkbox" class="alert-checkbox" value="${record.id}"
                        ${record.alertStatus === 1 ? 'disabled' : ''}>
                </td>
                <td>${formatAlertType(record.alertType)}</td>
                <td>
                    <span class="alert-level-${record.alertLevel}">
                        ${formatAlertLevel(record.alertLevel)}
                    </span>
                </td>
                <td>${record.deviceCode}</td>
                <td>${record.deviceName || '-'}</td>
                <td>${record.tourismName || '-'}</td>
                <td>${record.description || '-'}</td>
                <td>${formatDateTime(record.recordTime)}</td>
                <td>
                    <span class="alert-status-${record.alertStatus}">
                        ${formatAlertStatus(record.alertStatus)}
                    </span>
                </td>
                <td>
                    ${record.imageUrl ? `
                        <button type="button" class="btn btn-sm btn-link preview-btn"
                            data-url="${record.imageUrl}">查看图片</button>
                    ` : '-'}
                    ${record.alertStatus === 0 ? `
                        <button type="button" class="btn btn-sm btn-primary handle-btn"
                            data-id="${record.id}">处理</button>
                    ` : ''}
                </td>
            </tr>
        `);
    });
}

// 渲染分页
function renderPagination(data) {
    const container = $('#pagination');
    container.empty();
    
    if (data.total === 0) {
        return;
    }
    
    const totalPages = Math.ceil(data.total / data.size);
    const currentPage = data.current;
    
    let html = '<ul class="pagination">';
    
    // 上一页
    html += `
        <li class="page-item ${currentPage === 1 ? 'disabled' : ''}">
            <a class="page-link" href="javascript:void(0)" data-page="${currentPage - 1}">上一页</a>
        </li>
    `;
    
    // 页码
    for (let i = 1; i <= totalPages; i++) {
        if (i === 1 || i === totalPages || (i >= currentPage - 2 && i <= currentPage + 2)) {
            html += `
                <li class="page-item ${i === currentPage ? 'active' : ''}">
                    <a class="page-link" href="javascript:void(0)" data-page="${i}">${i}</a>
                </li>
            `;
        } else if (i === currentPage - 3 || i === currentPage + 3) {
            html += '<li class="page-item disabled"><span class="page-link">...</span></li>';
        }
    }
    
    // 下一页
    html += `
        <li class="page-item ${currentPage === totalPages ? 'disabled' : ''}">
            <a class="page-link" href="javascript:void(0)" data-page="${currentPage + 1}">下一页</a>
        </li>
    `;
    
    html += '</ul>';
    container.html(html);
}

// 绑定事件
function bindEvents() {
    // 搜索按钮点击事件
    $('#searchBtn').click(() => loadData(1));
    
    // 重置按钮点击事件
    $('#resetBtn').click(() => {
        $('#searchForm')[0].reset();
        loadData(1);
    });
    
    // 全选/取消全选
    $('#checkAll').change(function() {
        $('.alert-checkbox:not(:disabled)').prop('checked', $(this).prop('checked'));
        updateBatchHandleBtn();
    });
    
    // 复选框变化事件
    $(document).on('change', '.alert-checkbox', updateBatchHandleBtn);
    
    // 分页点击事件
    $(document).on('click', '.page-link', function() {
        if (!$(this).parent().hasClass('disabled')) {
            const page = $(this).data('page');
            loadData(page);
        }
    });
    
    // 图片预览
    $(document).on('click', '.preview-btn', function() {
        const url = $(this).data('url');
        $('#previewImage').attr('src', url);
        $('#imageModal').modal('show');
    });
    
    // 处理告警
    $(document).on('click', '.handle-btn', function() {
        const id = $(this).data('id');
        $('#alertIds').val(id);
        $('#handleModal').modal('show');
    });
    
    // 批量处理按钮点击事件
    $('#batchHandleBtn').click(function() {
        const ids = $('.alert-checkbox:checked').map(function() {
            return $(this).val();
        }).get();
        $('#alertIds').val(ids.join(','));
        $('#handleModal').modal('show');
    });
    
    // 提交处理
    $('#submitHandleBtn').click(function() {
        const ids = $('#alertIds').val().split(',').map(Number);
        const status = $('#handleForm select[name="status"]').val();
        
        $.ajax({
            url: '/api/v1/alerts/status',
            method: 'PUT',
            data: {
                ids: ids,
                status: status
            },
            success: function(result) {
                if (result) {
                    $('#handleModal').modal('hide');
                    showSuccess('处理成功');
                    loadData();
                } else {
                    showError('处理失败，请重试');
                }
            },
            error: function() {
                showError('处理失败，请重试');
            }
        });
    });
}

// 更新批量处理按钮状态
function updateBatchHandleBtn() {
    const checkedCount = $('.alert-checkbox:checked').length;
    $('#batchHandleBtn').prop('disabled', checkedCount === 0);
}

// 格式化告警类型
function formatAlertType(type) {
    const types = {
        'CROWD': '人群聚集',
        'DENSITY': '高密度',
        'FLOW': '客流异常'
    };
    return types[type] || type;
}

// 格式化告警级别
function formatAlertLevel(level) {
    const levels = {
        1: '低',
        2: '中',
        3: '高'
    };
    return levels[level] || level;
}

// 格式化告警状态
function formatAlertStatus(status) {
    return status === 0 ? '待处理' : '已处理';
}

// 格式化日期时间
function formatDateTime(datetime) {
    return moment(datetime).format('YYYY-MM-DD HH:mm:ss');
}

// 显示加载动画
function showLoading() {
    $('.card-body').addClass('loading');
}

// 隐藏加载动画
function hideLoading() {
    $('.card-body').removeClass('loading');
}

// 显示成功提示
function showSuccess(message) {
    alert(message); // 可以替换为更好的提示组件
}

// 显示错误提示
function showError(message) {
    alert(message); // 可以替换为更好的提示组件
} 