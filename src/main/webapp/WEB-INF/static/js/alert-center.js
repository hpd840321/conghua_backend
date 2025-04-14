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
    $('.loading-overlay').css('display', 'flex');
}

// 隐藏加载动画
function hideLoading() {
    $('.loading-overlay').css('display', 'none');
}

$(document).ready(function() {
    try {
        // 初始化日期范围选择器
        $('#dateRange').daterangepicker({
            timePicker: true,
            timePicker24Hour: true,
            timePickerSeconds: true,
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
            ranges: {
                '今天': [moment().startOf('day'), moment().endOf('day')],
                '昨天': [moment().subtract(1, 'days').startOf('day'), moment().subtract(1, 'days').endOf('day')],
                '最近7天': [moment().subtract(6, 'days').startOf('day'), moment().endOf('day')],
                '最近30天': [moment().subtract(29, 'days').startOf('day'), moment().endOf('day')]
            },
            startDate: moment().startOf('day'),
            endDate: moment().endOf('day')
        });

        // 加载初始数据
        loadData();
        
        // 监听窗口大小变化，自适应图表大小
        let resizeTimeout;
        $(window).on('resize', function() {
            clearTimeout(resizeTimeout);
            resizeTimeout = setTimeout(() => {
                const charts = ['hourDistribution', 'typeDistribution'].map(id => 
                    echarts.getInstanceByDom(document.getElementById(id))
                );
                
                charts.forEach(chart => {
                    if (chart) {
                        chart.resize();
                    }
                });
            }, 250);
        });
    } catch (error) {
        console.error('初始化失败:', error);
        showErrorMessage('页面初始化失败，请刷新重试');
    }
});

// 加载数据函数
function loadData() {
    showLoading();
    const params = getSearchParams();
    
    if (!validateDateRange(params.startTime, params.endTime)) {
        hideLoading();
        return;
    }
    
    Promise.all([
        retryOperation(() => loadOverview(params)),
        retryOperation(() => loadHourDistribution(params)),
        retryOperation(() => loadTypeDistribution(params)),
        retryOperation(() => loadDataTable(params))
    ]).catch(error => {
        console.error('数据加载失败:', error);
        showErrorMessage('数据加载失败，请刷新页面重试');
    }).finally(() => {
        hideLoading();
    });
}

// 获取搜索参数
function getSearchParams() {
    const dateRange = $('#dateRange').val().split(' - ');
    return {
        tourismName: $('#tourismName').val(),
        deviceCode: $('#deviceCode').val(),
        alertType: $('#alertType').val(),
        alertLevel: $('#alertLevel').val(),
        startTime: dateRange[0],
        endTime: dateRange[1]
    };
}

// 验证日期范围
function validateDateRange(startTime, endTime) {
    if (!startTime || !endTime) return false;
    
    const start = moment(startTime);
    const end = moment(endTime);
    const now = moment();
    
    if (!start.isValid() || !end.isValid()) return false;
    if (end.isBefore(start)) return false;
    if (start.isAfter(now)) return false;
    
    const daysDiff = end.diff(start, 'days');
    if (daysDiff > 90) {
        showErrorMessage('时间范围不能超过90天');
        return false;
    }
    
    return true;
}

// 加载统计概览
function loadOverview(params) {
    return new Promise((resolve, reject) => {
        $.get('/api/v1/alerts/overview', params)
            .done(response => {
                if (response.code === 200) {
                    const data = response.data;
                    if (!data) {
                        reject(new Error('返回数据为空'));
                        return;
                    }
                    
                    $('#totalAlerts').text(data.totalAlerts || 0);
                    $('#pendingAlerts').text(data.pendingAlerts || 0);
                    $('#highLevelAlerts').text(data.highLevelAlerts || 0);
                    $('#todayAlerts').text(data.todayAlerts || 0);
                    resolve(data);
                } else {
                    reject(new Error(response.message || '加载统计概览失败'));
                }
            })
            .fail(reject);
    });
}

// 加载时段分布图表
function loadHourDistribution(params) {
    return new Promise((resolve, reject) => {
        $.get('/api/v1/alerts/distribution/hour', params)
            .done(response => {
                if (response.code === 200) {
                    const data = response.data;
                    if (!Array.isArray(data)) {
                        reject(new Error('返回数据格式错误'));
                        return;
                    }
                    
                    try {
                        const chart = echarts.init(document.getElementById('hourDistribution'));
                        const option = {
                            title: {
                                text: '告警时段分布',
                                left: 'center',
                                textStyle: {
                                    fontSize: 16,
                                    fontWeight: 'bold'
                                }
                            },
                            tooltip: {
                                trigger: 'axis',
                                axisPointer: {
                                    type: 'shadow'
                                },
                                formatter: function(params) {
                                    return `${params[0].axisValue}<br/>
                                            <span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:${params[0].color};"></span>
                                            告警数量: ${params[0].value}`;
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
                                data: data.map(item => item.hour + '时'),
                                axisLabel: {
                                    interval: 0,
                                    rotate: 45,
                                    textStyle: {
                                        fontSize: 12
                                    }
                                },
                                axisTick: {
                                    alignWithLabel: true
                                }
                            },
                            yAxis: {
                                type: 'value',
                                name: '告警数量',
                                nameTextStyle: {
                                    padding: [0, 0, 0, 30],
                                    fontSize: 12
                                },
                                splitLine: {
                                    show: true,
                                    lineStyle: {
                                        type: 'dashed',
                                        color: '#E0E0E0'
                                    }
                                },
                                axisLabel: {
                                    formatter: '{value}'
                                }
                            },
                            series: [
                                {
                                    name: '告警数量',
                                    type: 'bar',
                                    data: data.map(item => ({
                                        value: item.count,
                                        itemStyle: {
                                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                                {offset: 0, color: '#FF9800'},
                                                {offset: 1, color: '#FFB74D'}
                                            ])
                                        }
                                    })),
                                    barWidth: '60%',
                                    emphasis: {
                                        itemStyle: {
                                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                                {offset: 0, color: '#FFA726'},
                                                {offset: 1, color: '#FFB74D'}
                                            ])
                                        }
                                    },
                                    animationDelay: function (idx) {
                                        return idx * 100;
                                    }
                                }
                            ],
                            animationEasing: 'elasticOut',
                            animationDelayUpdate: function (idx) {
                                return idx * 5;
                            }
                        };
                        
                        chart.setOption(option);
                        resolve(data);
                    } catch (error) {
                        handleChartError('hourDistribution', error);
                        reject(error);
                    }
                } else {
                    reject(new Error(response.message || '加载时段分布失败'));
                }
            })
            .fail(reject);
    });
}

// 加载告警类型分布图表
function loadTypeDistribution(params) {
    return $.get('/api/v1/alerts/distribution/type', params, function(response) {
        if (response.code === 200) {
            const chart = echarts.init(document.getElementById('typeDistribution'));
            const data = response.data;
            
            const option = {
                title: {
                    text: '告警类型分布',
                    left: 'center',
                    textStyle: {
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                tooltip: {
                    trigger: 'item',
                    formatter: function(params) {
                        return `${params.name}<br/>
                                <span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:${params.color};"></span>
                                数量: ${params.value} (${params.percent}%)`;
                    }
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    top: 'middle',
                    itemWidth: 10,
                    itemHeight: 10,
                    textStyle: {
                        fontSize: 12
                    },
                    data: ['人群聚集', '高密度', '异常流动']
                },
                series: [
                    {
                        name: '告警类型',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        center: ['60%', '50%'],
                        avoidLabelOverlap: true,
                        itemStyle: {
                            borderRadius: 10,
                            borderColor: '#fff',
                            borderWidth: 2
                        },
                        label: {
                            show: true,
                            position: 'outside',
                            formatter: '{b}\n{c}次 ({d}%)',
                            fontSize: 12
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: 14,
                                fontWeight: 'bold'
                            },
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        },
                        data: [
                            {
                                value: data.find(item => item.type === 'CROWD_GATHERING')?.count || 0,
                                name: '人群聚集',
                                itemStyle: { 
                                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                        {offset: 0, color: '#E91E63'},
                                        {offset: 1, color: '#F06292'}
                                    ])
                                }
                            },
                            {
                                value: data.find(item => item.type === 'HIGH_DENSITY')?.count || 0,
                                name: '高密度',
                                itemStyle: { 
                                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                        {offset: 0, color: '#9C27B0'},
                                        {offset: 1, color: '#BA68C8'}
                                    ])
                                }
                            },
                            {
                                value: data.find(item => item.type === 'ABNORMAL_FLOW')?.count || 0,
                                name: '异常流动',
                                itemStyle: { 
                                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                        {offset: 0, color: '#673AB7'},
                                        {offset: 1, color: '#9575CD'}
                                    ])
                                }
                            }
                        ],
                        animationType: 'scale',
                        animationEasing: 'elasticOut',
                        animationDelay: function (idx) {
                            return Math.random() * 200;
                        }
                    }
                ]
            };
            
            chart.setOption(option);
        }
    });
}

// 加载数据表格
function loadDataTable(params) {
    return $.get('/api/v1/alerts', params, function(response) {
        if (response.code === 200) {
            const data = response.data.records;
            let html = '';
            
            if (data.length === 0) {
                html = `
                    <tr>
                        <td colspan="9" class="text-center">
                            <div class="no-data">
                                <i class="fa fa-exclamation-circle"></i>
                                暂无数据
                            </div>
                        </td>
                    </tr>
                `;
            } else {
                data.forEach(item => {
                    html += `
                        <tr class="${item.alertLevel === 3 ? 'table-danger' : (item.alertLevel === 2 ? 'table-warning' : '')}">
                            <td>
                                <div class="text-truncate" style="max-width: 120px;" title="${item.deviceCode}">
                                    ${item.deviceCode}
                                </div>
                            </td>
                            <td>
                                <div class="text-truncate" style="max-width: 150px;" title="${item.deviceName}">
                                    ${item.deviceName}
                                </div>
                            </td>
                            <td>
                                <div class="text-truncate" style="max-width: 150px;" title="${item.tourismName}">
                                    ${item.tourismName}
                                </div>
                            </td>
                            <td>
                                <div class="alert-type-tag ${getAlertTypeClass(item.alertType)}">
                                    ${getAlertTypeName(item.alertType)}
                                </div>
                            </td>
                            <td>
                                <div class="alert-level-badge">
                                    ${getAlertLevelBadge(item.alertLevel)}
                                </div>
                            </td>
                            <td>
                                <div class="alert-status-badge">
                                    ${getAlertStatusBadge(item.alertStatus)}
                                </div>
                            </td>
                            <td>
                                <div class="text-truncate" style="max-width: 200px;" title="${item.description}">
                                    ${item.description}
                                </div>
                            </td>
                            <td>
                                <div class="record-time" title="${formatDateTime(item.recordTime)}">
                                    ${formatDateTime(item.recordTime)}
                                </div>
                            </td>
                            <td>
                                <div class="action-buttons">
                                    <button class="btn btn-sm btn-info" onclick="showImage('${item.imageUrl}')" title="查看全景图">
                                        <i class="fa fa-image"></i>
                                    </button>
                                    ${item.alertStatus === 0 ? 
                                        `<button class="btn btn-sm btn-success ml-1" onclick="showHandleModal(${item.id})" title="处理告警">
                                            <i class="fa fa-check"></i>
                                        </button>` : 
                                        ''}
                                </div>
                            </td>
                        </tr>
                    `;
                });
            }
            
            $('#dataTableBody').html(html);
            
            // 更新分页和统计信息
            updatePagination(response.data);
            updateTableInfo(response.data);
        }
    });
}

// 获取告警类型名称
function getAlertTypeName(type) {
    const types = {
        'CROWD_GATHERING': '人群聚集',
        'HIGH_DENSITY': '高密度',
        'ABNORMAL_FLOW': '异常流动'
    };
    return types[type] || type;
}

// 获取告警级别标签
function getAlertLevelBadge(level) {
    const badges = {
        1: '<span class="badge badge-info">低级</span>',
        2: '<span class="badge badge-warning">中级</span>',
        3: '<span class="badge badge-danger">高级</span>'
    };
    return badges[level] || level;
}

// 获取告警状态标签
function getAlertStatusBadge(status) {
    const badges = {
        0: '<span class="badge badge-warning">待处理</span>',
        1: '<span class="badge badge-success">已处理</span>'
    };
    return badges[status] || status;
}

// 获取告警类型样式类
function getAlertTypeClass(type) {
    const classes = {
        'CROWD_GATHERING': 'type-gathering',
        'HIGH_DENSITY': 'type-density',
        'ABNORMAL_FLOW': 'type-flow'
    };
    return classes[type] || '';
}

// 格式化日期时间
function formatDateTime(datetime) {
    return moment(datetime).format('YYYY-MM-DD HH:mm:ss');
}

// 更新表格信息
function updateTableInfo(pageInfo) {
    const start = (pageInfo.current - 1) * pageInfo.size + 1;
    const end = Math.min(pageInfo.current * pageInfo.size, pageInfo.total);
    
    $('#tableInfo').html(`
        显示第 ${start} 到第 ${end} 条记录，共 ${pageInfo.total} 条
    `);
}

// 更新分页
function updatePagination(pageInfo) {
    const total = pageInfo.total;
    const current = pageInfo.current;
    const pages = pageInfo.pages;
    
    if (pages <= 1) {
        $('#pagination').empty();
        return;
    }
    
    let html = `
        <ul class="pagination justify-content-center">
            <li class="page-item ${current === 1 ? 'disabled' : ''}">
                <a class="page-link" href="javascript:void(0)" onclick="goToPage(1)" title="首页">
                    <i class="fa fa-angle-double-left"></i>
                </a>
            </li>
            <li class="page-item ${current === 1 ? 'disabled' : ''}">
                <a class="page-link" href="javascript:void(0)" onclick="goToPage(${current - 1})" title="上一页">
                    <i class="fa fa-angle-left"></i>
                </a>
            </li>
    `;
    
    // 计算显示的页码范围
    let startPage = Math.max(1, current - 2);
    let endPage = Math.min(pages, startPage + 4);
    startPage = Math.max(1, endPage - 4);
    
    for (let i = startPage; i <= endPage; i++) {
        html += `
            <li class="page-item ${i === current ? 'active' : ''}">
                <a class="page-link" href="javascript:void(0)" onclick="goToPage(${i})">${i}</a>
            </li>
        `;
    }
    
    html += `
        <li class="page-item ${current === pages ? 'disabled' : ''}">
            <a class="page-link" href="javascript:void(0)" onclick="goToPage(${current + 1})" title="下一页">
                <i class="fa fa-angle-right"></i>
            </a>
        </li>
        <li class="page-item ${current === pages ? 'disabled' : ''}">
            <a class="page-link" href="javascript:void(0)" onclick="goToPage(${pages})" title="末页">
                <i class="fa fa-angle-double-right"></i>
            </a>
        </li>
    </ul>
    `;
    
    $('#pagination').html(html);
}

// 跳转到指定页
function goToPage(page) {
    const params = getSearchParams();
    params.pageNum = page;
    loadDataTable(params);
}

// 显示全景图
function showImage(url) {
    if (!url) {
        showErrorMessage('图片地址不能为空');
        return;
    }
    
    const $img = $('#previewImage');
    const $modal = $('#imageModal');
    const $loading = $('<div class="text-center py-5"><i class="fa fa-spinner fa-spin fa-3x"></i></div>');
    
    $img.hide();
    $modal.find('.modal-body').prepend($loading);
    $modal.modal('show');
    
    $img.on('load', function() {
        $loading.remove();
        $img.show();
    }).on('error', function() {
        $loading.remove();
        $img.replaceWith('<div class="alert alert-danger m-3">图片加载失败</div>');
    }).attr('src', url);
}

// 显示处理模态框
function showHandleModal(alertId) {
    $('#alertId').val(alertId);
    $('#handleRemark').val('');
    $('#handleModal').modal('show');
}

// 处理告警
function handleAlert() {
    const alertId = $('#alertId').val();
    const remark = $('#handleRemark').val().trim();
    
    if (!alertId) {
        showErrorMessage('告警ID不能为空');
        return;
    }
    
    if (!remark) {
        showErrorMessage('请输入处理备注');
        return;
    }
    
    if (remark.length > 500) {
        showErrorMessage('处理备注不能超过500字');
        return;
    }
    
    const $submitBtn = $('#handleModal .btn-primary');
    const originalText = $submitBtn.html();
    $submitBtn.prop('disabled', true).html('<i class="fa fa-spinner fa-spin"></i> 处理中...');
    
    $.post('/api/v1/alerts/' + alertId + '/handle', {
        remark: remark
    })
    .done(response => {
        if (response.code === 200) {
            $('#handleModal').modal('hide');
            showSuccessMessage('处理成功');
            loadData();
        } else {
            showErrorMessage(response.message || '处理失败');
        }
    })
    .fail(() => {
        showErrorMessage('处理失败，请重试');
    })
    .always(() => {
        $submitBtn.prop('disabled', false).html(originalText);
    });
}

// 搜索按钮点击事件
function searchData() {
    loadData();
}

// 重置按钮点击事件
function resetSearch() {
    $('#tourismName').val('');
    $('#deviceCode').val('');
    $('#alertType').val('');
    $('#alertLevel').val('');
    $('#dateRange').data('daterangepicker').setStartDate(moment().startOf('day'));
    $('#dateRange').data('daterangepicker').setEndDate(moment().endOf('day'));
    loadData();
}

// 图表错误处理优化
function handleChartError(chartId, error) {
    const chart = echarts.getInstanceByDom(document.getElementById(chartId));
    if (chart) {
        chart.clear();
        chart.setOption({
            title: {
                text: '数据加载失败',
                left: 'center',
                top: 'center',
                textStyle: {
                    color: '#999',
                    fontSize: 14
                }
            }
        });
    }
    console.error(`图表 ${chartId} 加载失败:`, error);
} 