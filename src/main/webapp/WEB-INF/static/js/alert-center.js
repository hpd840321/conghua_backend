// 全局错误处理
window.onerror = function(message, source, lineno, colno, error) {
    console.error('全局错误:', {message, source, lineno, colno, error});
    showErrorMessage('系统发生错误，请刷新页面重试');
    hideLoading();
    return false;
};

// Ajax错误处理
$(document).ajaxError(function(event, jqXHR, settings, error) {
    console.error('Ajax请求错误:', {
        status: jqXHR.status,
        statusText: jqXHR.statusText,
        responseText: jqXHR.responseText,
        error: error
    });
    hideLoading();
    
    if (jqXHR.status === 401) {
        showErrorMessage('会话已过期，请重新登录');
        setTimeout(() => {
            window.location.href = '/login';
        }, 2000);
        return;
    }
    
    if (jqXHR.status === 403) {
        showErrorMessage('没有权限访问该资源');
        return;
    }
    
    if (jqXHR.status === 404) {
        showErrorMessage('请求的资源不存在');
        return;
    }
    
    if (jqXHR.status >= 500) {
        showErrorMessage('服务器错误，请稍后重试');
        return;
    }
    
    showErrorMessage('请求失败，请稍后重试');
});

// 显示错误消息
function showErrorMessage(message) {
    if ($('#errorAlert').length === 0) {
        $('body').append(`
            <div id="errorAlert" class="alert alert-danger alert-dismissible fade show position-fixed" 
                 style="top: 20px; right: 20px; z-index: 9999;" role="alert">
                <i class="fa fa-exclamation-circle"></i>
                <span class="ml-2"></span>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        `);
    }
    
    const $alert = $('#errorAlert');
    $alert.find('span').text(message);
    $alert.show();
    
    setTimeout(() => {
        $alert.alert('close');
    }, 3000);
}

// 显示成功消息
function showSuccessMessage(message) {
    if ($('#successAlert').length === 0) {
        $('body').append(`
            <div id="successAlert" class="alert alert-success alert-dismissible fade show position-fixed" 
                 style="top: 20px; right: 20px; z-index: 9999;" role="alert">
                <i class="fa fa-check-circle"></i>
                <span class="ml-2"></span>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        `);
    }
    
    const $alert = $('#successAlert');
    $alert.find('span').text(message);
    $alert.show();
    
    setTimeout(() => {
        $alert.alert('close');
    }, 3000);
}

// 数据加载重试机制
function retryOperation(operation, maxRetries = 3, delay = 1000) {
    return new Promise((resolve, reject) => {
        let attempts = 0;
        
        function attempt() {
            attempts++;
            operation()
                .then(resolve)
                .catch(error => {
                    console.error(`操作失败 (尝试 ${attempts}/${maxRetries}):`, error);
                    
                    if (attempts < maxRetries) {
                        setTimeout(attempt, delay);
                    } else {
                        reject(error);
                    }
                });
        }
        
        attempt();
    });
}

// 显示加载动画
function showLoading() {
    $('#loading').css('display', 'flex');
}

// 隐藏加载动画
function hideLoading() {
    $('#loading').css('display', 'none');
}

$(function() {
    // 初始化日期范围选择器
    $('#dateRange').daterangepicker({
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
        },
        timePicker: true,
        timePicker24Hour: true,
        timePickerSeconds: true,
        ranges: {
            '今天': [moment().startOf('day'), moment().endOf('day')],
            '昨天': [moment().subtract(1, 'days').startOf('day'), moment().subtract(1, 'days').endOf('day')],
            '最近7天': [moment().subtract(6, 'days').startOf('day'), moment().endOf('day')],
            '最近30天': [moment().subtract(29, 'days').startOf('day'), moment().endOf('day')]
        },
        startDate: moment().startOf('day'),
        endDate: moment().endOf('day')
    });

    // 初始化ECharts实例
    const hourDistributionChart = echarts.init(document.getElementById('hourDistributionChart'));
    const typeDistributionChart = echarts.init(document.getElementById('typeDistributionChart'));

    // 页面加载完成后加载数据
    loadData();

    // 绑定查询按钮事件
    $('#searchBtn').click(function() {
        loadData();
    });

    // 绑定重置按钮事件
    $('#resetBtn').click(function() {
        $('#searchForm')[0].reset();
        $('#dateRange').data('daterangepicker').setStartDate(moment().startOf('day'));
        $('#dateRange').data('daterangepicker').setEndDate(moment().endOf('day'));
        loadData();
    });

    // 绑定告警处理按钮事件
    $('#confirmHandle').click(function() {
        handleAlert();
    });

    // 加载数据函数
    function loadData() {
        showLoading();
        const params = getQueryParams();
        
        // 使用Promise.all并发请求数据
        Promise.all([
            // 获取概览数据
            $.get('/api/v1/alerts/overview', params),
            // 获取时段分布数据
            $.get('/api/v1/alerts/distribution/hour', params),
            // 获取类型分布数据
            $.get('/api/v1/alerts/distribution/type', params),
            // 获取分页数据
            $.get('/api/v1/alerts', {
                ...params,
                pageNum: 1,
                pageSize: 10
            })
        ]).then(([overviewRes, hourRes, typeRes, pageRes]) => {
            // 更新统计卡片
            updateOverview(overviewRes.data);
            // 更新时段分布图
            updateHourDistributionChart(hourRes.data);
            // 更新类型分布图
            updateTypeDistributionChart(typeRes.data);
            // 更新数据表格
            updateDataTable(pageRes.data);
            hideLoading();
        }).catch(error => {
            console.error('加载数据失败:', error);
            hideLoading();
            alert('加载数据失败，请稍后重试');
        });
    }

    // 获取查询参数
    function getQueryParams() {
        const dateRange = $('#dateRange').data('daterangepicker');
        return {
            tourismName: $('#tourismName').val(),
            deviceCode: $('#deviceCode').val(),
            alertType: $('#alertType').val(),
            alertLevel: $('#alertLevel').val(),
            alertStatus: $('#alertStatus').val(),
            startTime: dateRange.startDate.format('YYYY-MM-DD HH:mm:ss'),
            endTime: dateRange.endDate.format('YYYY-MM-DD HH:mm:ss')
        };
    }

    // 更新统计概览
    function updateOverview(data) {
        $('#totalAlerts').text(data.totalAlerts);
        $('#pendingAlerts').text(data.pendingAlerts);
        $('#handledAlerts').text(data.handledAlerts);
        $('#highLevelAlerts').text(data.highLevelAlerts);
    }

    // 更新时段分布图表
    function updateHourDistributionChart(data) {
        const option = {
            title: {
                text: '告警时段分布',
                left: 'center'
            },
            tooltip: {
                trigger: 'axis',
                formatter: '{b}时: {c}条告警'
            },
            xAxis: {
                type: 'category',
                data: Array.from({length: 24}, (_, i) => i),
                name: '小时',
                axisLabel: {
                    formatter: '{value}:00'
                }
            },
            yAxis: {
                type: 'value',
                name: '告警数量'
            },
            series: [{
                name: '告警数量',
                type: 'bar',
                data: data,
                itemStyle: {
                    color: '#409EFF'
                }
            }]
        };
        hourDistributionChart.setOption(option);
    }

    // 更新类型分布图表
    function updateTypeDistributionChart(data) {
        const option = {
            title: {
                text: '告警类型分布',
                left: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: '{b}: {c}条 ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: data.map(item => item.name)
            },
            series: [{
                name: '告警类型',
                type: 'pie',
                radius: '50%',
                data: data.map(item => ({
                    name: formatAlertType(item.type),
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
        };
        typeDistributionChart.setOption(option);
    }

    // 更新数据表格
    function updateDataTable(data) {
        const tbody = $('#dataTable');
        tbody.empty();
        
        data.records.forEach(item => {
            tbody.append(`
                <tr>
                    <td>${item.deviceCode}</td>
                    <td>${item.deviceName || '-'}</td>
                    <td>${item.tourismName || '-'}</td>
                    <td>${formatAlertType(item.alertType)}</td>
                    <td>${formatAlertLevel(item.alertLevel)}</td>
                    <td>${formatAlertStatus(item.alertStatus)}</td>
                    <td>${item.description || '-'}</td>
                    <td>${moment(item.recordTime).format('YYYY-MM-DD HH:mm:ss')}</td>
                    <td>
                        <button class="btn btn-sm btn-primary mr-1" onclick="previewImage('${item.imageUrl}')">
                            查看全景图
                        </button>
                        ${item.alertStatus === 0 ? `
                            <button class="btn btn-sm btn-success" onclick="showHandleModal(${item.id})">
                                处理
                            </button>
                        ` : ''}
                    </td>
                </tr>
            `);
        });

        // 更新分页信息
        updatePagination(data);
    }

    // 格式化告警类型显示
    function formatAlertType(type) {
        const typeMap = {
            'CROWD_GATHERING': '人群聚集',
            'HIGH_DENSITY': '高密度',
            'ABNORMAL_FLOW': '异常流动'
        };
        return typeMap[type] || type;
    }

    // 格式化告警级别显示
    function formatAlertLevel(level) {
        const levelMap = {
            1: '<span class="badge badge-info">低</span>',
            2: '<span class="badge badge-warning">中</span>',
            3: '<span class="badge badge-danger">高</span>'
        };
        return levelMap[level] || level;
    }

    // 格式化告警状态显示
    function formatAlertStatus(status) {
        const statusMap = {
            0: '<span class="badge badge-warning">待处理</span>',
            1: '<span class="badge badge-success">已处理</span>'
        };
        return statusMap[status] || status;
    }

    // 更新分页组件
    function updatePagination(data) {
        $('#totalCount').text(data.total);
        const pagination = $('#pagination');
        pagination.empty();

        // 计算页码范围
        const currentPage = data.current;
        const totalPages = data.pages;
        const pageSize = data.size;
        
        // 添加上一页按钮
        pagination.append(`
            <li class="page-item ${currentPage === 1 ? 'disabled' : ''}">
                <a class="page-link" href="#" data-page="${currentPage - 1}">上一页</a>
            </li>
        `);

        // 添加页码按钮
        for (let i = 1; i <= totalPages; i++) {
            if (i === 1 || i === totalPages || (i >= currentPage - 2 && i <= currentPage + 2)) {
                pagination.append(`
                    <li class="page-item ${i === currentPage ? 'active' : ''}">
                        <a class="page-link" href="#" data-page="${i}">${i}</a>
                    </li>
                `);
            } else if (i === currentPage - 3 || i === currentPage + 3) {
                pagination.append('<li class="page-item disabled"><span class="page-link">...</span></li>');
            }
        }

        // 添加下一页按钮
        pagination.append(`
            <li class="page-item ${currentPage === totalPages ? 'disabled' : ''}">
                <a class="page-link" href="#" data-page="${currentPage + 1}">下一页</a>
            </li>
        `);

        // 绑定页码点击事件
        pagination.find('a.page-link').click(function(e) {
            e.preventDefault();
            const page = $(this).data('page');
            if (page >= 1 && page <= totalPages) {
                loadPageData(page, pageSize);
            }
        });
    }

    // 加载指定页的数据
    function loadPageData(pageNum, pageSize) {
        const params = {
            ...getQueryParams(),
            pageNum: pageNum,
            pageSize: pageSize
        };
        
        showLoading();
        $.get('/api/v1/alerts', params)
            .then(response => {
                updateDataTable(response.data);
                hideLoading();
            })
            .catch(error => {
                console.error('加载数据失败:', error);
                hideLoading();
                alert('加载数据失败，请稍后重试');
            });
    }

    // 窗口大小改变时重绘图表
    $(window).resize(function() {
        hourDistributionChart.resize();
        typeDistributionChart.resize();
    });
});

// 显示全景图
function previewImage(url) {
    if (url) {
        $('#previewImage').attr('src', url);
        $('#imageModal').modal('show');
    } else {
        alert('暂无全景图');
    }
}

// 显示处理告警模态框
function showHandleModal(alertId) {
    $('#alertId').val(alertId);
    $('#handleDescription').val('');
    $('#handleModal').modal('show');
}

// 处理告警
function handleAlert() {
    const alertId = $('#alertId').val();
    const description = $('#handleDescription').val();

    if (!description) {
        alert('请输入处理说明');
        return;
    }

    showLoading();
    $.post('/api/v1/alerts/' + alertId + '/handle', {
        description: description
    }).then(response => {
        hideLoading();
        $('#handleModal').modal('hide');
        loadData();
    }).catch(error => {
        console.error('处理告警失败:', error);
        hideLoading();
        alert('处理告警失败，请稍后重试');
    });
} 