// 显示加载动画
function showLoading() {
    $('.loading-overlay').css('display', 'flex');
}

// 隐藏加载动画
function hideLoading() {
    $('.loading-overlay').css('display', 'none');
}

$(document).ready(function() {
    // 初始化日期范围选择器
    $('#dateRange').daterangepicker({
        timePicker: true,
        timePicker24Hour: true,
        locale: {
            format: 'YYYY-MM-DD HH:mm:ss',
            applyLabel: '确定',
            cancelLabel: '取消',
            fromLabel: '从',
            toLabel: '至'
        },
        ranges: {
            '今天': [moment().startOf('day'), moment().endOf('day')],
            '昨天': [moment().subtract(1, 'days').startOf('day'), moment().subtract(1, 'days').endOf('day')],
            '最近7天': [moment().subtract(6, 'days').startOf('day'), moment().endOf('day')],
            '最近30天': [moment().subtract(29, 'days').startOf('day'), moment().endOf('day')]
        }
    });

    // 加载初始数据
    loadData();
});

// 加载数据函数
function loadData() {
    showLoading();
    const params = getSearchParams();
    
    Promise.all([
        loadOverview(params),
        loadHourDistribution(params),
        loadTypeDistribution(params),
        loadDataTable(params)
    ]).finally(() => {
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
        alertStatus: $('#alertStatus').val(),
        startTime: dateRange[0],
        endTime: dateRange[1]
    };
}

// 加载统计概览
function loadOverview(params) {
    return $.get('/api/v1/alerts/overview', params, function(response) {
        if (response.code === 200) {
            const data = response.data;
            $('#totalAlerts').text(data.totalAlerts);
            $('#pendingAlerts').text(data.pendingAlerts);
            $('#highLevelAlerts').text(data.highLevelAlerts);
            $('#todayAlerts').text(data.todayAlerts);
        }
    });
}

// 加载时段告警分布图表
function loadHourDistribution(params) {
    return $.get('/api/v1/alerts/distribution/hour', params, function(response) {
        if (response.code === 200) {
            const chart = echarts.init(document.getElementById('hourDistribution'));
            const data = response.data;
            
            const option = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {
                    data: ['告警数量']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    data: data.map(item => item.hour + '时')
                },
                yAxis: {
                    type: 'value',
                    name: '告警数量'
                },
                series: [
                    {
                        name: '告警数量',
                        type: 'bar',
                        data: data.map(item => item.count)
                    }
                ]
            };
            
            chart.setOption(option);
        }
    });
}

// 加载告警类型分布图表
function loadTypeDistribution(params) {
    return $.get('/api/v1/alerts/distribution/type', params, function(response) {
        if (response.code === 200) {
            const chart = echarts.init(document.getElementById('typeDistribution'));
            const data = response.data;
            
            const option = {
                tooltip: {
                    trigger: 'item',
                    formatter: '{b}: {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: data.map(item => getAlertTypeName(item.type))
                },
                series: [
                    {
                        type: 'pie',
                        radius: '50%',
                        data: data.map(item => ({
                            name: getAlertTypeName(item.type),
                            value: item.count
                        })),
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
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
            
            data.forEach(item => {
                html += `
                    <tr>
                        <td>${item.deviceCode}</td>
                        <td>${item.deviceName}</td>
                        <td>${item.tourismName}</td>
                        <td>${getAlertTypeName(item.alertType)}</td>
                        <td>${getAlertLevelName(item.alertLevel)}</td>
                        <td>${getAlertStatusName(item.alertStatus)}</td>
                        <td>${item.recordTime}</td>
                        <td>
                            <button class="btn btn-sm btn-info mr-1" onclick="showImage('${item.imageUrl}')">查看全景图</button>
                            ${item.alertStatus === 0 ? 
                                `<button class="btn btn-sm btn-primary" onclick="showHandleModal('${item.id}')">处理</button>` : 
                                ''
                            }
                        </td>
                    </tr>
                `;
            });
            
            $('#dataTableBody').html(html);
            
            // 更新分页
            updatePagination(response.data);
        }
    });
}

// 更新分页
function updatePagination(pageInfo) {
    const total = pageInfo.total;
    const current = pageInfo.current;
    const pages = pageInfo.pages;
    
    let html = `
        <ul class="pagination">
            <li class="page-item ${current === 1 ? 'disabled' : ''}">
                <a class="page-link" href="javascript:void(0)" onclick="goToPage(${current - 1})">上一页</a>
            </li>
    `;
    
    for (let i = 1; i <= pages; i++) {
        html += `
            <li class="page-item ${i === current ? 'active' : ''}">
                <a class="page-link" href="javascript:void(0)" onclick="goToPage(${i})">${i}</a>
            </li>
        `;
    }
    
    html += `
        <li class="page-item ${current === pages ? 'disabled' : ''}">
            <a class="page-link" href="javascript:void(0)" onclick="goToPage(${current + 1})">下一页</a>
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
    $('#previewImage').attr('src', url);
    $('#imageModal').modal('show');
}

// 显示处理模态框
function showHandleModal(id) {
    $('#alertId').val(id);
    $('#handleDescription').val('');
    $('#handleModal').modal('show');
}

// 处理告警
function handleAlert() {
    const id = $('#alertId').val();
    const description = $('#handleDescription').val();
    
    if (!description) {
        alert('请输入处理说明');
        return;
    }
    
    $.post('/api/v1/alerts/' + id + '/handle', {
        description: description
    }, function(response) {
        if (response.code === 200) {
            $('#handleModal').modal('hide');
            loadData();
        } else {
            alert(response.message || '处理失败');
        }
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
    $('#alertStatus').val('');
    $('#dateRange').data('daterangepicker').setStartDate(moment().startOf('day'));
    $('#dateRange').data('daterangepicker').setEndDate(moment().endOf('day'));
    loadData();
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

// 获取告警级别名称
function getAlertLevelName(level) {
    const levels = {
        1: '低',
        2: '中',
        3: '高'
    };
    return levels[level] || level;
}

// 获取告警状态名称
function getAlertStatusName(status) {
    const statuses = {
        0: '待处理',
        1: '已处理'
    };
    return statuses[status] || status;
}

// 窗口大小改变时重绘图表
$(window).resize(function() {
    const charts = ['hourDistribution', 'typeDistribution'].map(id => 
        echarts.getInstanceByDom(document.getElementById(id))
    );
    
    charts.forEach(chart => {
        if (chart) {
            chart.resize();
        }
    });
}); 