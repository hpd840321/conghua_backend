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
        loadFlowTrend(params),
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
        flowDirection: $('#flowDirection').val(),
        startTime: dateRange[0],
        endTime: dateRange[1]
    };
}

// 加载统计概览
function loadOverview(params) {
    return $.get('/api/v1/flow-analysis/overview', params, function(response) {
        if (response.code === 200) {
            const data = response.data;
            $('#totalFlow').text(data.totalFlow);
            $('#inFlow').text(data.inFlow);
            $('#outFlow').text(data.outFlow);
            $('#todayFlow').text(data.todayFlow);
        }
    });
}

// 加载时段分布图表
function loadHourDistribution(params) {
    return $.get('/api/v1/flow-analysis/distribution/hour', params, function(response) {
        if (response.code === 200) {
            const chart = echarts.init(document.getElementById('hourDistribution'));
            const data = response.data;
            
            const option = {
                title: {
                    text: '客流时段分布',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    },
                    formatter: function(params) {
                        let html = params[0].axisValue + '<br/>';
                        params.forEach(item => {
                            html += item.seriesName + ': ' + item.value + '人<br/>';
                        });
                        return html;
                    }
                },
                legend: {
                    data: ['进入', '离开'],
                    top: 30
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
                        rotate: 45
                    }
                },
                yAxis: {
                    type: 'value',
                    name: '人数',
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
                        name: '进入',
                        type: 'bar',
                        stack: 'total',
                        data: data.map(item => item.inCount),
                        itemStyle: {
                            color: '#4CAF50'
                        },
                        emphasis: {
                            itemStyle: {
                                color: '#81C784'
                            }
                        }
                    },
                    {
                        name: '离开',
                        type: 'bar',
                        stack: 'total',
                        data: data.map(item => item.outCount),
                        itemStyle: {
                            color: '#F44336'
                        },
                        emphasis: {
                            itemStyle: {
                                color: '#E57373'
                            }
                        }
                    }
                ]
            };
            
            chart.setOption(option);
        }
    });
}

// 加载客流趋势图表
function loadFlowTrend(params) {
    return $.get('/api/v1/flow-analysis/trend', params, function(response) {
        if (response.code === 200) {
            const chart = echarts.init(document.getElementById('flowTrend'));
            const data = response.data;
            
            const option = {
                title: {
                    text: '客流趋势',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis',
                    formatter: function(params) {
                        return params[0].axisValue + '<br/>' +
                               params[0].seriesName + ': ' + params[0].value + '人';
                    }
                },
                legend: {
                    data: ['客流量'],
                    top: 30
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
                    name: '人数',
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
                        name: '客流量',
                        type: 'line',
                        smooth: true,
                        data: data.map(item => item.count),
                        symbol: 'circle',
                        symbolSize: 8,
                        itemStyle: {
                            color: '#1976D2'
                        },
                        areaStyle: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                {
                                    offset: 0,
                                    color: 'rgba(25,118,210,0.5)'
                                },
                                {
                                    offset: 1,
                                    color: 'rgba(25,118,210,0.1)'
                                }
                            ])
                        },
                        emphasis: {
                            itemStyle: {
                                color: '#42A5F5'
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
    return $.get('/api/v1/flow-analysis', params, function(response) {
        if (response.code === 200) {
            const data = response.data.records;
            let html = '';
            
            data.forEach(item => {
                html += `
                    <tr>
                        <td>${item.deviceCode}</td>
                        <td>${item.deviceName}</td>
                        <td>${item.tourismName}</td>
                        <td>${item.flowCount}</td>
                        <td>${getFlowDirectionName(item.flowDirection)}</td>
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
    $('#flowDirection').val('');
    $('#dateRange').data('daterangepicker').setStartDate(moment().startOf('day'));
    $('#dateRange').data('daterangepicker').setEndDate(moment().endOf('day'));
    loadData();
}

// 获取流动方向名称
function getFlowDirectionName(direction) {
    const directions = {
        'IN': '进入',
        'OUT': '离开'
    };
    return directions[direction] || direction;
}

// 窗口大小改变时重绘图表
$(window).resize(function() {
    const charts = ['hourDistribution', 'flowTrend'].map(id => 
        echarts.getInstanceByDom(document.getElementById(id))
    );
    
    charts.forEach(chart => {
        if (chart) {
            chart.resize();
        }
    });
}); 