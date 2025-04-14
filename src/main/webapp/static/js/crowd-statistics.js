$(document).ready(function() {
    // 初始化日期选择器
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
        startDate: moment().startOf('day'),
        endDate: moment().endOf('day'),
        ranges: {
            '今天': [moment().startOf('day'), moment().endOf('day')],
            '昨天': [moment().subtract(1, 'days').startOf('day'), moment().subtract(1, 'days').endOf('day')],
            '最近7天': [moment().subtract(6, 'days').startOf('day'), moment().endOf('day')],
            '最近30天': [moment().subtract(29, 'days').startOf('day'), moment().endOf('day')],
            '本月': [moment().startOf('month'), moment().endOf('month')],
            '上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        }
    });

    // 初始化图表
    var densityChart = echarts.init(document.getElementById('densityChart'));
    var countChart = echarts.init(document.getElementById('countChart'));

    // 加载数据
    loadData();

    // 监听表单提交
    $('#searchForm').on('submit', function(e) {
        e.preventDefault();
        loadData();
    });

    // 监听重置按钮
    $('#searchForm button[type="reset"]').on('click', function() {
        setTimeout(loadData, 0);
    });

    function loadData(page = 1) {
        var dateRange = $('#dateRange').val().split(' - ');
        var params = {
            pageNo: page,
            pageSize: 10,
            tourismName: $('#tourismName').val(),
            deviceCode: $('#deviceCode').val(),
            algorithmType: $('#algorithmType').val(),
            startTime: dateRange[0],
            endTime: dateRange[1]
        };

        $('#loadingModal').modal('show');

        // 加载表格数据
        $.get('api/crowd/statistics/page', params)
            .done(function(response) {
                if (response.code === 200) {
                    updateTable(response.data);
                    updatePagination(response.data);
                } else {
                    showError(response.message || '加载数据失败');
                }
            })
            .fail(function(jqXHR) {
                showError('加载数据失败：' + (jqXHR.responseJSON?.message || jqXHR.statusText));
            })
            .always(function() {
                $('#loadingModal').modal('hide');
            });

        // 如果选择了设备，加载趋势图
        if (params.deviceCode) {
            // 加载密度趋势
            $.get('api/crowd/statistics/density/trend', {
                deviceCode: params.deviceCode,
                startTime: params.startTime,
                endTime: params.endTime
            })
            .done(function(response) {
                if (response.code === 200) {
                    updateDensityChart(response.data);
                }
            });

            // 加载数量趋势
            $.get('api/crowd/statistics/count/trend', {
                deviceCode: params.deviceCode,
                startTime: params.startTime,
                endTime: params.endTime
            })
            .done(function(response) {
                if (response.code === 200) {
                    updateCountChart(response.data);
                }
            });
        } else {
            // 清空图表
            densityChart.setOption({series: [{data: []}]});
            countChart.setOption({series: [{data: []}]});
        }
    }

    function updateTable(data) {
        var tbody = $('#dataTableBody');
        tbody.empty();
        
        if (!data.records || data.records.length === 0) {
            tbody.append('<tr><td colspan="6" class="text-center">暂无数据</td></tr>');
            return;
        }

        data.records.forEach(function(item) {
            tbody.append(`
                <tr>
                    <td>${moment(item.recordTime).format('YYYY-MM-DD HH:mm:ss')}</td>
                    <td>${item.tourismName || '-'}</td>
                    <td>${item.deviceCode || '-'}</td>
                    <td>${item.count || 0}</td>
                    <td>${(item.density || 0).toFixed(2)}</td>
                    <td>${getAlgorithmTypeName(item.algorithmType)}</td>
                </tr>
            `);
        });
    }

    function updatePagination(data) {
        var pagination = $('#pagination');
        pagination.empty();

        if (!data.records || data.records.length === 0) {
            return;
        }

        var ul = $('<ul class="pagination"></ul>');

        // 上一页
        ul.append(`
            <li class="page-item ${data.current === 1 ? 'disabled' : ''}">
                <a class="page-link" href="#" data-page="${data.current - 1}">上一页</a>
            </li>
        `);

        // 页码
        for (var i = 1; i <= data.pages; i++) {
            if (
                i === 1 || // 第一页
                i === data.pages || // 最后一页
                (i >= data.current - 2 && i <= data.current + 2) // 当前页附近的页码
            ) {
                ul.append(`
                    <li class="page-item ${i === data.current ? 'active' : ''}">
                        <a class="page-link" href="#" data-page="${i}">${i}</a>
                    </li>
                `);
            } else if (
                (i === data.current - 3 && data.current > 4) || // 当前页前的省略号
                (i === data.current + 3 && data.current < data.pages - 3) // 当前页后的省略号
            ) {
                ul.append('<li class="page-item disabled"><span class="page-link">...</span></li>');
            }
        }

        // 下一页
        ul.append(`
            <li class="page-item ${data.current === data.pages ? 'disabled' : ''}">
                <a class="page-link" href="#" data-page="${data.current + 1}">下一页</a>
            </li>
        `);

        pagination.append(ul);

        // 绑定点击事件
        pagination.find('a.page-link').on('click', function(e) {
            e.preventDefault();
            var page = $(this).data('page');
            if (!$(this).parent().hasClass('disabled')) {
                loadData(page);
            }
        });
    }

    function updateDensityChart(data) {
        var option = {
            title: {
                text: '人流密度趋势'
            },
            tooltip: {
                trigger: 'axis',
                formatter: function(params) {
                    var time = params[0].name;
                    var value = params[0].value;
                    return time + '<br/>' + params[0].seriesName + ': ' + value.toFixed(2);
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
                data: data.map(item => moment(item.time).format('HH:mm:ss')),
                axisLabel: {
                    rotate: 45
                }
            },
            yAxis: {
                type: 'value',
                name: '密度'
            },
            series: [{
                name: '人流密度',
                type: 'line',
                smooth: true,
                data: data.map(item => item.density),
                areaStyle: {
                    opacity: 0.3
                }
            }]
        };
        densityChart.setOption(option);
    }

    function updateCountChart(data) {
        var option = {
            title: {
                text: '人数统计趋势'
            },
            tooltip: {
                trigger: 'axis',
                formatter: function(params) {
                    var time = params[0].name;
                    var value = params[0].value;
                    return time + '<br/>' + params[0].seriesName + ': ' + value;
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
                data: data.map(item => moment(item.time).format('HH:mm:ss')),
                axisLabel: {
                    rotate: 45
                }
            },
            yAxis: {
                type: 'value',
                name: '人数'
            },
            series: [{
                name: '人数统计',
                type: 'line',
                smooth: true,
                data: data.map(item => item.count),
                areaStyle: {
                    opacity: 0.3
                }
            }]
        };
        countChart.setOption(option);
    }

    function showError(message) {
        $('#errorMessage').text(message);
        $('#errorModal').modal('show');
    }

    function getAlgorithmTypeName(type) {
        switch (type) {
            case 1: return '密度检测';
            case 2: return '人数统计';
            default: return '未知';
        }
    }

    // 监听窗口大小变化，调整图表大小
    $(window).on('resize', function() {
        densityChart.resize();
        countChart.resize();
    });
}); 