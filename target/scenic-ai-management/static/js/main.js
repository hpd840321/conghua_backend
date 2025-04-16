$(document).ready(function () {
    // 侧边栏切换
    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
    });

    // 激活当前菜单项
    function activateCurrentMenu() {
        var currentPath = window.location.pathname;
        $('.sidebar ul li a').each(function() {
            var menuPath = $(this).attr('href');
            if (currentPath.indexOf(menuPath) !== -1) {
                $(this).parent().addClass('active');
            } else {
                $(this).parent().removeClass('active');
            }
        });
    }

    // 初始化激活菜单
    activateCurrentMenu();

    // 初始化所有图表容器
    function initCharts() {
        $('.chart-container').each(function() {
            var chartId = $(this).attr('id');
            if (chartId) {
                var chart = echarts.init(document.getElementById(chartId));
                // 设置默认配置
                var option = {
                    title: {
                        text: ''
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: []
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    }
                };
                chart.setOption(option);
            }
        });
    }

    // 初始化图表
    initCharts();

    // 窗口大小改变时重绘图表
    $(window).resize(function() {
        $('.chart-container').each(function() {
            var chartId = $(this).attr('id');
            if (chartId) {
                var chart = echarts.getInstanceByDom(document.getElementById(chartId));
                if (chart) {
                    chart.resize();
                }
            }
        });
    });

    // 通用的AJAX错误处理
    $(document).ajaxError(function(event, jqXHR, settings, error) {
        console.error('AJAX Error:', error);
        // 显示错误提示
        alert('操作失败：' + error);
    });

    // 通用的表单提交处理
    $('form').on('submit', function(e) {
        var $form = $(this);
        var $submitBtn = $form.find('button[type="submit"]');
        
        // 禁用提交按钮
        $submitBtn.prop('disabled', true);
        
        // 可以在这里添加表单验证逻辑
        
        return true;
    });

    // 通用的数据加载函数
    function loadData(url, params, callback) {
        $.ajax({
            url: url,
            type: 'GET',
            data: params,
            success: function(response) {
                if (callback && typeof callback === 'function') {
                    callback(response);
                }
            },
            error: function(xhr, status, error) {
                console.error('加载数据失败:', error);
                alert('加载数据失败：' + error);
            }
        });
    }

    // 通用的图表更新函数
    function updateChart(chartId, data) {
        var chart = echarts.getInstanceByDom(document.getElementById(chartId));
        if (chart) {
            chart.setOption(data);
        }
    }
}); 