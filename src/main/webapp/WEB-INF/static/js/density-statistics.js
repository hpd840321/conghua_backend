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
        loadDensityDistribution(params),
        loadDensityTrend(params),
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
        startTime: dateRange[0],
        endTime: dateRange[1]
    };
}

// 加载统计概览
function loadOverview(params) {
    return $.get('/api/v1/crowd-statistics/overview', params, function(response) {
        if (response.code === 200) {
            const data = response.data;
            $('#avgDensity').text(data.avgDensity.toFixed(2));
            $('#maxDensity').text(data.maxDensity.toFixed(2));
            $('#highDensityCount').text(data.highDensityCount || 0);
            $('#totalCount').text(data.recordCount);
        }
    });
}

// 加载密度分布图表
function loadDensityDistribution(params) {
    return $.get('/api/v1/crowd-statistics/distribution/density', params, function(response) {
        if (response.code === 200) {
            const chart = echarts.init(document.getElementById('densityDistribution'));
            const data = response.data;
            
            const option = {
                title: {
                    text: '密度分布',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{b}: {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['低密度', '中密度', '高密度']
                },
                series: [
                    {
                        name: '密度分布',
                        type: 'pie',
                        radius: '50%',
                        data: [
                            {
                                value: data.find(item => item.density_level === '低密度')?.count || 0,
                                name: '低密度',
                                itemStyle: { color: '#91CC75' }
                            },
                            {
                                value: data.find(item => item.density_level === '中密度')?.count || 0,
                                name: '中密度',
                                itemStyle: { color: '#FAC858' }
                            },
                            {
                                value: data.find(item => item.density_level === '高密度')?.count || 0,
                                name: '高密度',
                                itemStyle: { color: '#EE6666' }
                            }
                        ],
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

// 加载密度趋势图表
function loadDensityTrend(params) {
    return $.get('/api/v1/crowd-statistics/trend', params, function(response) {
        if (response.code === 200) {
            const chart = echarts.init(document.getElementById('densityTrend'));
            const data = response.data;
            
            const option = {
                title: {
                    text: '密度趋势',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis',
                    formatter: function(params) {
                        return params[0].axisValue + '<br/>' +
                               '密度: ' + params[0].value.toFixed(2);
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
                    boundaryGap: false,
                    data: data.map(item => item.time),
                    axisLabel: {
                        interval: 'auto',
                        rotate: 45
                    }
                },
                yAxis: {
                    type: 'value',
                    name: '密度',
                    nameTextStyle: {
                        padding: [0, 0, 0, 30]
                    },
                    splitLine: {
                        show: true,
                        lineStyle: {
                            type: 'dashed'
                        }
                    }
                },
                series: [
                    {
                        name: '密度',
                        type: 'line',
                        smooth: true,
                        data: data.map(item => item.avg_density),
                        symbol: 'circle',
                        symbolSize: 8,
                        itemStyle: {
                            color: '#5470C6'
                        },
                        areaStyle: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                {
                                    offset: 0,
                                    color: 'rgba(84,112,198,0.5)'
                                },
                                {
                                    offset: 1,
                                    color: 'rgba(84,112,198,0.1)'
                                }
                            ])
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
    return $.get('/api/v1/crowd-statistics', params, function(response) {
        if (response.code === 200) {
            const data = response.data.records;
            let html = '';
            
            data.forEach(item => {
                html += `
                    <tr>
                        <td>${item.deviceCode}</td>
                        <td>${item.deviceName}</td>
                        <td>${item.tourismName}</td>
                        <td>${item.count}</td>
                        <td>${item.density.toFixed(2)}</td>
                        <td>${getDensityLevel(item.density)}</td>
                        <td>${item.recordTime}</td>
                        <td>
                            <button class="btn btn-sm btn-info" onclick="showImage('${item.imageUrl}')">查看全景图</button>
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

// 获取密度等级
function getDensityLevel(density) {
    if (density >= 0.7) {
        return '<span class="badge badge-danger">高密度</span>';
    } else if (density >= 0.4) {
        return '<span class="badge badge-warning">中密度</span>';
    } else {
        return '<span class="badge badge-success">低密度</span>';
    }
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

// 搜索按钮点击事件
function searchData() {
    loadData();
}

// 重置按钮点击事件
function resetSearch() {
    $('#tourismName').val('');
    $('#deviceCode').val('');
    $('#dateRange').data('daterangepicker').setStartDate(moment().startOf('day'));
    $('#dateRange').data('daterangepicker').setEndDate(moment().endOf('day'));
    loadData();
}

// 窗口大小改变时重绘图表
$(window).resize(function() {
    const charts = ['densityDistribution', 'densityTrend'].map(id => 
        echarts.getInstanceByDom(document.getElementById(id))
    );
    
    charts.forEach(chart => {
        if (chart) {
            chart.resize();
        }
    });
}); 