$(function() {
    // 配置toastr通知
    toastr.options = {
        "closeButton": true,
        "progressBar": true,
        "positionClass": "toast-top-right",
        "timeOut": "3000"
    };

    // 初始化日期选择器
    $('#dateRange').daterangepicker({
        startDate: moment().subtract(7, 'days'),
        endDate: moment(),
        timePicker: true,
        timePicker24Hour: true,
        locale: {
            format: 'YYYY-MM-DD HH:mm:ss',
            applyLabel: '确定',
            cancelLabel: '取消',
            customRangeLabel: '自定义范围'
        }
    });

    // 初始化ECharts实例
    let densityDistChart = null;
    let densityTrendChart = null;
    let avgDensityTrendChart = null;
    let maxDensityTrendChart = null;
    let highDensityTrendChart = null;
    let totalRecordsTrendChart = null;

    // 确保图表容器存在后再初始化
    try {
        if (document.getElementById('densityDistChart')) {
            densityDistChart = echarts.init(document.getElementById('densityDistChart'));
        }
        if (document.getElementById('densityTrendChart')) {
            densityTrendChart = echarts.init(document.getElementById('densityTrendChart'));
        }
        if (document.getElementById('avgDensityTrend')) {
            avgDensityTrendChart = echarts.init(document.getElementById('avgDensityTrend'));
        }
        if (document.getElementById('maxDensityTrend')) {
            maxDensityTrendChart = echarts.init(document.getElementById('maxDensityTrend'));
        }
        if (document.getElementById('highDensityTrend')) {
            highDensityTrendChart = echarts.init(document.getElementById('highDensityTrend'));
        }
        if (document.getElementById('totalRecordsTrend')) {
            totalRecordsTrendChart = echarts.init(document.getElementById('totalRecordsTrend'));
        }
    } catch (error) {
        console.error('图表初始化失败:', error);
        toastr.error('图表初始化失败，请刷新页面重试');
    }

    // 当前页码
    let currentPage = 1;
    const pageSize = 10;

    // 表单验证
    function validateForm() {
        const form = document.getElementById('searchForm');
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
            form.classList.add('was-validated');
            return false;
        }
        form.classList.add('was-validated');
        return true;
    }

    // 显示加载状态
    function showLoading(type) {
        switch(type) {
            case 'chart':
                $('.chart-loading').show();
                $('.chart-error').hide();
                break;
            case 'table':
                $('.table-loading').show();
                $('.table-empty').hide();
                break;
            case 'modal':
                $('.modal-loading').show();
                $('.modal-error').hide();
                break;
            default:
                $('#loading').show();
        }
    }

    // 隐藏加载状态
    function hideLoading(type) {
        switch(type) {
            case 'chart':
                $('.chart-loading').hide();
                break;
            case 'table':
                $('.table-loading').hide();
                break;
            case 'modal':
                $('.modal-loading').hide();
                break;
            default:
                $('#loading').hide();
        }
    }

    // 显示错误状态
    function showError(type, message) {
        switch(type) {
            case 'chart':
                $('.chart-error').show();
                break;
            case 'table':
                $('.table-empty').show();
                break;
            case 'modal':
                $('.modal-error').show();
                break;
            default:
                toastr.error(message || '操作失败，请重试');
        }
    }

    // 加载数据
    function loadData() {
        if (!validateForm()) {
            return;
        }

        const params = {
            pageNum: currentPage,
            pageSize: pageSize,
            tourismName: $('#tourismName').val(),
            deviceCode: $('#deviceCode').val(),
            startTime: $('#dateRange').data('daterangepicker').startDate.format('YYYY-MM-DD HH:mm:ss'),
            endTime: $('#dateRange').data('daterangepicker').endDate.format('YYYY-MM-DD HH:mm:ss')
        };

        showLoading();

        // 加载概览数据
        $.get('/api/v1/crowd-statistics/overview', params)
            .done(function(response) {
                if (response.code === 200) {
                    const data = response.data;
                    $('#avgDensity').text((data.avgDensity || 0).toFixed(2));
                    $('#maxDensity').text((data.maxDensity || 0).toFixed(2));
                    $('#highDensityCount').text(data.highDensityCount || 0);
                    $('#totalRecords').text(data.totalCount || 0);
                } else {
                    toastr.warning(response.message || '获取概览数据失败');
                }
            })
            .fail(function(jqXHR) {
                console.error('加载概览数据失败:', jqXHR.responseText);
                toastr.error('加载概览数据失败，请重试');
            });

        // 加载趋势数据用于更新统计卡片的趋势图
        $.get('/api/v1/crowd-statistics/trend', params)
            .done(function(response) {
                if (response.code === 200) {
                    updateStatsTrendCharts(response.data);
                }
            })
            .fail(function(jqXHR) {
                console.error('加载趋势数据失败:', jqXHR.responseText);
            });

        // 加载密度分布数据
        showLoading('chart');
        $.get('/api/v1/crowd-statistics/distribution/density', params)
            .done(function(response) {
                if (response.code === 200 && densityDistChart) {
                    const data = response.data;
                    updateDensityDistChart(data);
                } else {
                    showError('chart');
                }
            })
            .fail(function(jqXHR) {
                console.error('加载密度分布数据失败:', jqXHR.responseText);
                showError('chart');
            })
            .always(function() {
                hideLoading('chart');
            });

        // 加载趋势数据
        showLoading('chart');
        $.get('/api/v1/crowd-statistics/trend', params)
            .done(function(response) {
                if (response.code === 200 && densityTrendChart) {
                    const data = response.data;
                    updateDensityTrendChart(data);
                } else {
                    showError('chart');
                }
            })
            .fail(function(jqXHR) {
                console.error('加载趋势数据失败:', jqXHR.responseText);
                showError('chart');
            })
            .always(function() {
                hideLoading('chart');
            });

        // 加载表格数据
        showLoading('table');
        $.get('/api/v1/crowd-statistics', params)
            .done(function(response) {
                if (response.code === 200) {
                    const data = response.data;
                    if (data.records && data.records.length > 0) {
                        updateTable(data);
                        updatePagination(data.total);
                    } else {
                        showError('table');
                    }
                } else {
                    showError('table');
                }
            })
            .fail(function(jqXHR) {
                console.error('加载表格数据失败:', jqXHR.responseText);
                showError('table');
            })
            .always(function() {
                hideLoading('table');
                hideLoading();
            });
    }

    // 更新密度分布图表
    function updateDensityDistChart(data) {
        if (!densityDistChart) return;

        try {
            const option = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    },
                    formatter: function(params) {
                        const barData = params[0];
                        const lineData = params[1];
                        return `密度等级：${barData.name}<br/>
                                记录数：${barData.value}<br/>
                                平均人数：${lineData.value}`;
                    }
                },
                legend: {
                    data: ['记录数', '平均人数'],
                    textStyle: {
                        color: '#666'
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
                    data: data.map(item => item.density_level),
                    axisLine: {
                        lineStyle: {
                            color: '#999'
                        }
                    },
                    axisLabel: {
                        color: '#666',
                        interval: 0,
                        rotate: 30
                    }
                },
                yAxis: [
                    {
                        type: 'value',
                        name: '记录数',
                        position: 'left',
                        axisLine: {
                            show: true,
                            lineStyle: {
                                color: '#5470c6'
                            }
                        },
                        axisLabel: {
                            color: '#666',
                            formatter: '{value}'
                        },
                        splitLine: {
                            lineStyle: {
                                type: 'dashed',
                                color: '#ddd'
                            }
                        }
                    },
                    {
                        type: 'value',
                        name: '平均人数',
                        position: 'right',
                        axisLine: {
                            show: true,
                            lineStyle: {
                                color: '#91cc75'
                            }
                        },
                        axisLabel: {
                            color: '#666',
                            formatter: '{value}'
                        },
                        splitLine: {
                            show: false
                        }
                    }
                ],
                series: [
                    {
                        name: '记录数',
                        type: 'bar',
                        data: data.map(item => item.count || 0),
                        itemStyle: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                {offset: 0, color: '#5470c6'},
                                {offset: 1, color: '#91a7e0'}
                            ])
                        },
                        barWidth: '40%',
                        emphasis: {
                            itemStyle: {
                                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                    {offset: 0, color: '#3c56b5'},
                                    {offset: 1, color: '#7088d1'}
                                ])
                            }
                        }
                    },
                    {
                        name: '平均人数',
                        type: 'line',
                        yAxisIndex: 1,
                        data: data.map(item => item.avg_count || 0),
                        symbol: 'circle',
                        symbolSize: 8,
                        lineStyle: {
                            color: '#91cc75',
                            width: 3
                        },
                        itemStyle: {
                            color: '#91cc75',
                            borderWidth: 2,
                            borderColor: '#fff'
                        },
                        emphasis: {
                            scale: true
                        }
                    }
                ]
            };
            densityDistChart.setOption(option);
        } catch (error) {
            console.error('更新密度分布图表失败:', error);
            showError('chart');
        }
    }

    // 更新趋势图表
    function updateDensityTrendChart(data) {
        if (!densityTrendChart) return;

        try {
            const option = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross'
                    },
                    formatter: function(params) {
                        const timeStr = params[0].axisValue;
                        let html = `${timeStr}<br/>`;
                        params.forEach(param => {
                            const marker = param.marker;
                            const name = param.seriesName;
                            const value = param.value;
                            html += `${marker}${name}: ${value}<br/>`;
                        });
                        return html;
                    }
                },
                legend: {
                    data: ['总人数', '平均密度'],
                    textStyle: {
                        color: '#666'
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
                    data: data.map(item => moment(item.time).format('HH:mm')),
                    axisLine: {
                        lineStyle: {
                            color: '#999'
                        }
                    },
                    axisLabel: {
                        color: '#666'
                    }
                },
                yAxis: [
                    {
                        type: 'value',
                        name: '总人数',
                        position: 'left',
                        axisLine: {
                            show: true,
                            lineStyle: {
                                color: '#5470c6'
                            }
                        },
                        axisLabel: {
                            color: '#666',
                            formatter: '{value}'
                        },
                        splitLine: {
                            lineStyle: {
                                type: 'dashed',
                                color: '#ddd'
                            }
                        }
                    },
                    {
                        type: 'value',
                        name: '平均密度',
                        position: 'right',
                        axisLine: {
                            show: true,
                            lineStyle: {
                                color: '#91cc75'
                            }
                        },
                        axisLabel: {
                            color: '#666',
                            formatter: '{value}'
                        },
                        splitLine: {
                            show: false
                        }
                    }
                ],
                series: [
                    {
                        name: '总人数',
                        type: 'line',
                        data: data.map(item => item.total_count || 0),
                        smooth: true,
                        symbol: 'circle',
                        symbolSize: 8,
                        lineStyle: {
                            color: '#5470c6',
                            width: 3
                        },
                        itemStyle: {
                            color: '#5470c6',
                            borderWidth: 2,
                            borderColor: '#fff'
                        },
                        areaStyle: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                {offset: 0, color: 'rgba(84,112,198,0.3)'},
                                {offset: 1, color: 'rgba(84,112,198,0.1)'}
                            ])
                        },
                        emphasis: {
                            scale: true
                        }
                    },
                    {
                        name: '平均密度',
                        type: 'line',
                        yAxisIndex: 1,
                        data: data.map(item => item.avg_density || 0),
                        smooth: true,
                        symbol: 'circle',
                        symbolSize: 8,
                        lineStyle: {
                            color: '#91cc75',
                            width: 3
                        },
                        itemStyle: {
                            color: '#91cc75',
                            borderWidth: 2,
                            borderColor: '#fff'
                        },
                        areaStyle: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                {offset: 0, color: 'rgba(145,204,117,0.3)'},
                                {offset: 1, color: 'rgba(145,204,117,0.1)'}
                            ])
                        },
                        emphasis: {
                            scale: true
                        }
                    }
                ]
            };
            densityTrendChart.setOption(option);
        } catch (error) {
            console.error('更新趋势图表失败:', error);
            showError('chart');
        }
    }

    // 更新统计卡片趋势图
    function updateStatsTrendCharts(data) {
        try {
            // 处理数据
            const times = data.map(item => item.time);
            const densities = data.map(item => item.avg_density || 0);
            const counts = data.map(item => item.total_count || 0);
            const highDensityCounts = data.map(item => 
                (item.avg_density || 0) > 0.7 ? 1 : 0
            );

            // 通用的迷你图表配置
            const miniChartOption = {
                animation: false,
                grid: {
                    left: 0,
                    right: 0,
                    top: 0,
                    bottom: 0
                },
                xAxis: {
                    type: 'category',
                    show: false,
                    data: times
                },
                yAxis: {
                    type: 'value',
                    show: false
                },
                series: [{
                    type: 'line',
                    showSymbol: false,
                    data: [],
                    lineStyle: {
                        color: '#1890ff'
                    },
                    areaStyle: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: 'rgba(24,144,255,0.3)'
                        }, {
                            offset: 1,
                            color: 'rgba(24,144,255,0.1)'
                        }])
                    }
                }]
            };

            // 更新平均密度趋势
            if (avgDensityTrendChart) {
                const option = {...miniChartOption};
                option.series[0].data = densities;
                avgDensityTrendChart.setOption(option);
            }

            // 更新最大密度趋势
            if (maxDensityTrendChart) {
                const option = {...miniChartOption};
                option.series[0].data = data.map(item => item.max_density || 0);
                maxDensityTrendChart.setOption(option);
            }

            // 更新高密度次数趋势
            if (highDensityTrendChart) {
                const option = {...miniChartOption};
                option.series[0].data = highDensityCounts;
                highDensityTrendChart.setOption(option);
            }

            // 更新总记录数趋势
            if (totalRecordsTrendChart) {
                const option = {...miniChartOption};
                option.series[0].data = counts;
                totalRecordsTrendChart.setOption(option);
            }
        } catch (error) {
            console.error('更新统计卡片趋势图失败:', error);
        }
    }

    // 更新数据表格
    function updateTable(data) {
        const tbody = $('#dataTable tbody');
        tbody.empty();

        if (!data.records || !Array.isArray(data.records)) {
            showError('table');
            return;
        }

        data.records.forEach(item => {
            try {
                // 计算密度等级和样式
                let densityClass = '';
                let densityText = '';
                const density = parseFloat(item.density || 0);
                
                if (density >= 0.7) {
                    densityClass = 'text-danger font-weight-bold';
                    densityText = '高密度';
                } else if (density >= 0.4) {
                    densityClass = 'text-warning font-weight-bold';
                    densityText = '中密度';
                } else {
                    densityClass = 'text-success';
                    densityText = '低密度';
                }

                tbody.append(`
                    <tr>
                        <td>
                            <span class="text-primary">${item.deviceCode || ''}</span>
                            ${item.deviceName ? `<br><small class="text-muted">${item.deviceName}</small>` : ''}
                        </td>
                        <td>${item.tourismName || ''}</td>
                        <td class="text-right">${item.count || 0}</td>
                        <td class="text-right ${densityClass}">
                            ${(item.density || 0).toFixed(2)}
                            <br>
                            <small>${densityText}</small>
                        </td>
                        <td>${moment(item.recordTime).format('YYYY-MM-DD HH:mm:ss')}</td>
                        <td>
                            <button type="button" class="btn btn-sm btn-primary view-image" 
                                    data-url="${item.imageUrl || '#'}"
                                    ${!item.imageUrl ? 'disabled' : ''}>
                                <i class="fas fa-image"></i> 查看图片
                            </button>
                        </td>
                    </tr>
                `);
            } catch (error) {
                console.error('处理表格行数据失败:', error);
            }
        });

        // 如果没有数据，显示空状态
        if (data.records.length === 0) {
            tbody.append(`
                <tr>
                    <td colspan="6" class="text-center text-muted py-5">
                        <i class="fas fa-inbox fa-3x mb-3"></i>
                        <p>暂无数据</p>
                    </td>
                </tr>
            `);
        }
    }

    // 更新分页
    function updatePagination(total) {
        const totalPages = Math.ceil(total / pageSize);
        const pagination = $('#pagination');
        pagination.empty();

        // 如果总页数小于等于1，不显示分页
        if (totalPages <= 1) {
            return;
        }

        // 上一页
        pagination.append(`
            <li class="page-item ${currentPage === 1 ? 'disabled' : ''}">
                <a class="page-link" href="#" data-page="${currentPage - 1}" aria-label="上一页">
                    <i class="fas fa-chevron-left"></i>
                </a>
            </li>
        `);

        // 页码按钮
        let startPage = Math.max(1, currentPage - 2);
        let endPage = Math.min(totalPages, startPage + 4);
        
        // 调整startPage确保显示5个页码
        if (endPage - startPage < 4) {
            startPage = Math.max(1, endPage - 4);
        }

        // 第一页
        if (startPage > 1) {
            pagination.append(`
                <li class="page-item">
                    <a class="page-link" href="#" data-page="1">1</a>
                </li>
            `);
            if (startPage > 2) {
                pagination.append('<li class="page-item disabled"><span class="page-link">...</span></li>');
            }
        }

        // 中间页码
        for (let i = startPage; i <= endPage; i++) {
            pagination.append(`
                <li class="page-item ${i === currentPage ? 'active' : ''}">
                    <a class="page-link" href="#" data-page="${i}">${i}</a>
                </li>
            `);
        }

        // 最后一页
        if (endPage < totalPages) {
            if (endPage < totalPages - 1) {
                pagination.append('<li class="page-item disabled"><span class="page-link">...</span></li>');
            }
            pagination.append(`
                <li class="page-item">
                    <a class="page-link" href="#" data-page="${totalPages}">${totalPages}</a>
                </li>
            `);
        }

        // 下一页
        pagination.append(`
            <li class="page-item ${currentPage === totalPages ? 'disabled' : ''}">
                <a class="page-link" href="#" data-page="${currentPage + 1}" aria-label="下一页">
                    <i class="fas fa-chevron-right"></i>
                </a>
            </li>
        `);

        // 显示总记录数
        pagination.append(`
            <li class="page-item disabled ml-2">
                <span class="page-link border-0 bg-transparent">
                    共 ${total} 条记录
                </span>
            </li>
        `);
    }

    // 绑定事件处理器
    $('#searchBtn').click(function() {
        currentPage = 1;
        loadData();
    });

    $('#resetBtn').click(function() {
        const form = document.getElementById('searchForm');
        form.reset();
        form.classList.remove('was-validated');
        $('#tourismName').val('');
        $('#deviceCode').val('');
        $('#dateRange').data('daterangepicker').setStartDate(moment().subtract(7, 'days'));
        $('#dateRange').data('daterangepicker').setEndDate(moment());
        currentPage = 1;
        loadData();
    });

    $('#pagination').on('click', 'a.page-link', function(e) {
        e.preventDefault();
        const page = $(this).data('page');
        if (page && !$(this).parent().hasClass('disabled')) {
            currentPage = page;
            loadData();
        }
    });

    $(document).on('click', '.view-image', function() {
        const url = $(this).data('url');
        if (url && url !== '#') {
            showLoading('modal');
            $('#previewImage')
                .on('load', function() {
                    hideLoading('modal');
                })
                .on('error', function() {
                    hideLoading('modal');
                    showError('modal');
                })
                .attr('src', url);
            $('#imageModal').modal('show');
        } else {
            toastr.warning('图片地址无效');
        }
    });

    // 窗口大小改变时重绘所有图表
    $(window).resize(function() {
        try {
            [densityDistChart, densityTrendChart, avgDensityTrendChart, 
             maxDensityTrendChart, highDensityTrendChart, totalRecordsTrendChart]
                .forEach(chart => {
                    if (chart) {
                        chart.resize();
                    }
                });
        } catch (error) {
            console.error('图表重绘失败:', error);
        }
    });

    // 初始加载
    loadData();
}); 