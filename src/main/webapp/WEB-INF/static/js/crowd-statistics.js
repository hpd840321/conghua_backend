// 显示加载动画
function showLoading() {
    $('.loading-overlay').css('display', 'flex');
}

// 隐藏加载动画
function hideLoading() {
    $('.loading-overlay').css('display', 'none');
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
    const densityDistChart = echarts.init(document.getElementById('densityDistChart'));
    const densityTrendChart = echarts.init(document.getElementById('densityTrendChart'));

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

    // 加载数据函数
    function loadData() {
        showLoading();
        const params = getQueryParams();
        
        // 使用Promise.all并发请求数据
        Promise.all([
            // 获取概览数据
            $.get('/api/v1/crowd-statistics/overview', params),
            // 获取密度分布数据
            $.get('/api/v1/crowd-statistics/distribution/density', params),
            // 获取密度趋势数据
            $.get('/api/v1/crowd-statistics/trend', params),
            // 获取分页数据
            $.get('/api/v1/crowd-statistics', {
                ...params,
                pageNum: 1,
                pageSize: 10
            })
        ]).then(([overviewRes, distributionRes, trendRes, pageRes]) => {
            // 更新统计卡片
            updateOverview(overviewRes.data);
            // 更新密度分布图
            updateDensityDistChart(distributionRes.data);
            // 更新密度趋势图
            updateDensityTrendChart(trendRes.data);
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
            startTime: dateRange.startDate.format('YYYY-MM-DD HH:mm:ss'),
            endTime: dateRange.endDate.format('YYYY-MM-DD HH:mm:ss')
        };
    }

    // 更新统计概览
    function updateOverview(data) {
        $('#avgDensity').text(data.avgDensity.toFixed(2));
        $('#maxDensity').text(data.maxDensity.toFixed(2));
        $('#highDensityCount').text(data.highDensityCount || 0);
        $('#totalRecords').text(data.recordCount);
    }

    // 更新密度分布图表
    function updateDensityDistChart(data) {
        const option = {
            title: {
                text: '人群密度分布',
                left: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: '{b}: {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: data.map(item => item.density_level)
            },
            series: [{
                name: '密度分布',
                type: 'pie',
                radius: '50%',
                data: data.map(item => ({
                    name: item.density_level,
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
        densityDistChart.setOption(option);
    }

    // 更新密度趋势图表
    function updateDensityTrendChart(data) {
        const option = {
            title: {
                text: '人群密度趋势',
                left: 'center'
            },
            tooltip: {
                trigger: 'axis',
                formatter: function(params) {
                    const time = params[0].name;
                    let result = time + '<br/>';
                    params.forEach(param => {
                        result += param.seriesName + ': ' + param.value.toFixed(2) + '<br/>';
                    });
                    return result;
                }
            },
            legend: {
                data: ['密度'],
                bottom: 0
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '10%',
                containLabel: true
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: data.map(item => item.time),
                axisLabel: {
                    rotate: 45
                }
            },
            yAxis: {
                type: 'value',
                name: '密度'
            },
            series: [{
                name: '密度',
                type: 'line',
                smooth: true,
                data: data.map(item => item.avg_density),
                areaStyle: {
                    opacity: 0.3
                },
                itemStyle: {
                    color: '#409EFF'
                }
            }]
        };
        densityTrendChart.setOption(option);
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
                    <td>${item.count}</td>
                    <td>${item.density.toFixed(2)}</td>
                    <td>${moment(item.recordTime).format('YYYY-MM-DD HH:mm:ss')}</td>
                    <td>
                        <button class="btn btn-sm btn-primary" onclick="previewImage('${item.imageUrl}')">
                            查看全景图
                        </button>
                    </td>
                </tr>
            `);
        });

        // 更新分页信息
        updatePagination(data);
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
        $.get('/api/v1/crowd-statistics', params)
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

    // 预览全景图
    function previewImage(url) {
        if (url) {
            $('#previewImage').attr('src', url);
            $('#imageModal').modal('show');
        } else {
            alert('暂无全景图');
        }
    }

    // 窗口大小改变时重绘图表
    $(window).resize(function() {
        densityDistChart.resize();
        densityTrendChart.resize();
    });
}); 