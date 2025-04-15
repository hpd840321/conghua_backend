/**
 * 告警统计页面JavaScript
 * 
 * @author AI
 * @date 2023-05-20
 */
$(function() {
    // 初始化日期时间选择器
    $('#startTime').datetimepicker({
        format: 'YYYY-MM-DD HH:mm:ss',
        locale: 'zh-cn',
        defaultDate: moment().subtract(7, 'days').startOf('day')
    });
    $('#endTime').datetimepicker({
        format: 'YYYY-MM-DD HH:mm:ss',
        locale: 'zh-cn',
        defaultDate: moment().endOf('day')
    });

    // 初始化图表
    var timeDistributionChart = echarts.init(document.getElementById('timeDistributionChart'));
    var typeDistributionChart = echarts.init(document.getElementById('typeDistributionChart'));
    var levelDistributionChart = echarts.init(document.getElementById('levelDistributionChart'));
    var trendChart = echarts.init(document.getElementById('trendChart'));
    var deviceDistributionChart = echarts.init(document.getElementById('deviceDistributionChart'));
    var tourismDistributionChart = echarts.init(document.getElementById('tourismDistributionChart'));

    // 图表配置
    var chartConfigs = {
        timeDistribution: {
            title: '告警时段分布',
            type: 'bar',
            xAxis: 'hour',
            yAxis: 'count',
            color: '#409EFF'
        },
        typeDistribution: {
            title: '告警类型分布',
            type: 'pie',
            name: 'type',
            value: 'count',
            radius: '55%'
        },
        levelDistribution: {
            title: '告警级别分布',
            type: 'pie',
            name: 'level',
            value: 'count',
            radius: '55%',
            colors: {
                '低': '#67C23A',
                '中': '#E6A23C',
                '高': '#F56C6C'
            }
        },
        trend: {
            title: '告警趋势',
            type: 'line',
            xAxis: 'date',
            yAxis: 'count',
            smooth: true,
            areaStyle: true
        },
        deviceDistribution: {
            title: '告警设备分布',
            type: 'bar',
            xAxis: 'deviceCode',
            yAxis: 'count',
            color: '#409EFF'
        },
        tourismDistribution: {
            title: '告警景区分布',
            type: 'bar',
            xAxis: 'tourismName',
            yAxis: 'count',
            color: '#409EFF'
        }
    };

    // 加载统计数据
    function loadStatistics() {
        // 显示加载状态
        showLoading();
        
        var params = {
            tourismName: $('#tourismName').val(),
            deviceCode: $('#deviceCode').val(),
            startTime: $('#startTime').val(),
            endTime: $('#endTime').val()
        };

        $.ajax({
            url: ctx + 'alert/statistics',
            type: 'GET',
            data: params,
            success: function(result) {
                if (result.code === 200) {
                    var data = result.data;
                    updateOverview(data.overview);
                    updateTimeDistribution(data.timeDistribution);
                    updateTypeDistribution(data.typeDistribution);
                    updateLevelDistribution(data.levelDistribution);
                    updateTrend(data.trend);
                    updateDeviceDistribution(data.deviceDistribution);
                    updateTourismDistribution(data.tourismDistribution);
                } else {
                    $.modal.alertError(result.msg || '获取统计数据失败');
                }
            },
            error: function(xhr, status, error) {
                $.modal.alertError('获取统计数据失败: ' + error);
            },
            complete: function() {
                // 隐藏加载状态
                hideLoading();
            }
        });
    }

    // 显示加载状态
    function showLoading() {
        $('.chart-container').addClass('loading');
        $('.overview-box').addClass('loading');
    }

    // 隐藏加载状态
    function hideLoading() {
        $('.chart-container').removeClass('loading');
        $('.overview-box').removeClass('loading');
    }

    // 更新概览数据
    function updateOverview(data) {
        if (!data) return;
        
        $('#totalCount').text(data.totalCount || 0);
        $('#pendingCount').text(data.pendingCount || 0);
        $('#processedCount').text(data.processedCount || 0);
        
        // 计算处理率
        var processRate = 0;
        if (data.totalCount > 0) {
            processRate = ((data.processedCount / data.totalCount) * 100).toFixed(2);
        }
        $('#processRate').text(processRate + '%');
        
        // 更新进度条
        $('.progress-bar').css('width', processRate + '%');
    }

    // 更新时段分布图表
    function updateTimeDistribution(data) {
        if (!data || !data.data || data.data.length === 0) {
            timeDistributionChart.showLoading({
                text: '暂无数据'
            });
            return;
        }
        
        var config = chartConfigs.timeDistribution;
        var xData = [];
        var yData = [];
        
        // 确保24小时数据完整
        for (var i = 0; i < 24; i++) {
            xData.push(i + '时');
            yData.push(0);
        }
        
        // 填充实际数据
        data.data.forEach(function(item) {
            var hour = parseInt(item.hour);
            if (hour >= 0 && hour < 24) {
                yData[hour] = item.count;
            }
        });
        
        var option = {
            title: {
                text: config.title,
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
                data: xData,
                axisLabel: {
                    interval: 0,
                    rotate: 30
                }
            },
            yAxis: {
                type: 'value',
                name: '告警数量'
            },
            series: [{
                name: '告警数量',
                type: config.type,
                data: yData,
                itemStyle: {
                    color: config.color
                },
                barWidth: '60%'
            }]
        };
        
        timeDistributionChart.hideLoading();
        timeDistributionChart.setOption(option);
    }

    // 更新类型分布图表
    function updateTypeDistribution(data) {
        if (!data || !data.data || data.data.length === 0) {
            typeDistributionChart.showLoading({
                text: '暂无数据'
            });
            return;
        }
        
        var config = chartConfigs.typeDistribution;
        var seriesData = [];
        
        data.data.forEach(function(item) {
            seriesData.push({
                name: item.type,
                value: item.count
            });
        });
        
        var option = {
            title: {
                text: config.title,
                left: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b}: {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: data.data.map(function(item) {
                    return item.type;
                })
            },
            series: [{
                name: '告警类型',
                type: config.type,
                radius: config.radius,
                center: ['50%', '60%'],
                data: seriesData,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }]
        };
        
        typeDistributionChart.hideLoading();
        typeDistributionChart.setOption(option);
    }

    // 更新级别分布图表
    function updateLevelDistribution(data) {
        if (!data || !data.data || data.data.length === 0) {
            levelDistributionChart.showLoading({
                text: '暂无数据'
            });
            return;
        }
        
        var config = chartConfigs.levelDistribution;
        var seriesData = [];
        
        data.data.forEach(function(item) {
            var level = item.level;
            var color = config.colors[level] || '#909399';
            
            seriesData.push({
                name: level + '级别',
                value: item.count,
                itemStyle: {
                    color: color
                }
            });
        });
        
        var option = {
            title: {
                text: config.title,
                left: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b}: {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: data.data.map(function(item) {
                    return item.level + '级别';
                })
            },
            series: [{
                name: '告警级别',
                type: config.type,
                radius: config.radius,
                center: ['50%', '60%'],
                data: seriesData,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }]
        };
        
        levelDistributionChart.hideLoading();
        levelDistributionChart.setOption(option);
    }

    // 更新趋势图表
    function updateTrend(data) {
        if (!data || !data.data || data.data.length === 0) {
            trendChart.showLoading({
                text: '暂无数据'
            });
            return;
        }
        
        var config = chartConfigs.trend;
        var xData = [];
        var yData = [];
        
        data.data.forEach(function(item) {
            xData.push(item.date);
            yData.push(item.count);
        });
        
        var option = {
            title: {
                text: config.title,
                left: 'center'
            },
            tooltip: {
                trigger: 'axis'
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'category',
                data: xData,
                axisLabel: {
                    interval: 0,
                    rotate: 30
                }
            },
            yAxis: {
                type: 'value',
                name: '告警数量'
            },
            series: [{
                name: '告警数量',
                type: config.type,
                data: yData,
                smooth: config.smooth,
                itemStyle: {
                    color: '#409EFF'
                },
                areaStyle: config.areaStyle ? {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                        offset: 0,
                        color: 'rgba(64, 158, 255, 0.3)'
                    }, {
                        offset: 1,
                        color: 'rgba(64, 158, 255, 0.1)'
                    }])
                } : null
            }]
        };
        
        trendChart.hideLoading();
        trendChart.setOption(option);
    }

    // 更新设备分布图表
    function updateDeviceDistribution(data) {
        if (!data || !data.data || data.data.length === 0) {
            deviceDistributionChart.showLoading({
                text: '暂无数据'
            });
            return;
        }
        
        var config = chartConfigs.deviceDistribution;
        var xData = [];
        var yData = [];
        
        data.data.forEach(function(item) {
            xData.push(item.deviceCode);
            yData.push(item.count);
        });
        
        var option = {
            title: {
                text: config.title,
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
                data: xData,
                axisLabel: {
                    interval: 0,
                    rotate: 30
                }
            },
            yAxis: {
                type: 'value',
                name: '告警数量'
            },
            series: [{
                name: '告警数量',
                type: config.type,
                data: yData,
                itemStyle: {
                    color: config.color
                },
                barWidth: '60%'
            }]
        };
        
        deviceDistributionChart.hideLoading();
        deviceDistributionChart.setOption(option);
    }

    // 更新景区分布图表
    function updateTourismDistribution(data) {
        if (!data || !data.data || data.data.length === 0) {
            tourismDistributionChart.showLoading({
                text: '暂无数据'
            });
            return;
        }
        
        var config = chartConfigs.tourismDistribution;
        var xData = [];
        var yData = [];
        
        data.data.forEach(function(item) {
            xData.push(item.tourismName);
            yData.push(item.count);
        });
        
        var option = {
            title: {
                text: config.title,
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
                data: xData,
                axisLabel: {
                    interval: 0,
                    rotate: 30
                }
            },
            yAxis: {
                type: 'value',
                name: '告警数量'
            },
            series: [{
                name: '告警数量',
                type: config.type,
                data: yData,
                itemStyle: {
                    color: config.color
                },
                barWidth: '60%'
            }]
        };
        
        tourismDistributionChart.hideLoading();
        tourismDistributionChart.setOption(option);
    }

    // 绑定搜索按钮事件
    $('#btnSearch').click(function() {
        loadStatistics();
    });

    // 绑定重置按钮事件
    $('#btnReset').click(function() {
        $('#tourismName').val('');
        $('#deviceCode').val('');
        $('#startTime').val(moment().subtract(7, 'days').startOf('day').format('YYYY-MM-DD HH:mm:ss'));
        $('#endTime').val(moment().endOf('day').format('YYYY-MM-DD HH:mm:ss'));
        loadStatistics();
    });

    // 窗口大小改变时，调整图表大小
    var resizeTimer;
    $(window).resize(function() {
        clearTimeout(resizeTimer);
        resizeTimer = setTimeout(function() {
            timeDistributionChart.resize();
            typeDistributionChart.resize();
            levelDistributionChart.resize();
            trendChart.resize();
            deviceDistributionChart.resize();
            tourismDistributionChart.resize();
        }, 200);
    });

    // 页面加载完成后，加载统计数据
    loadStatistics();
}); 