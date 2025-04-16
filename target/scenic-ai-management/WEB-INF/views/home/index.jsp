<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>景区智能管理系统</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f0f2f5;
            margin: 0;
            padding: 0;
        }
        .sidebar {
            position: fixed;
            top: 0;
            bottom: 0;
            left: 0;
            width: 200px;
            background-color: #001529;
            color: #fff;
            z-index: 100;
            transition: all 0.3s;
        }
        .sidebar-header {
            padding: 20px;
            text-align: center;
            border-bottom: 1px solid rgba(255,255,255,0.1);
        }
        .sidebar-header h3 {
            color: #fff;
            margin: 0;
            font-size: 18px;
        }
        .nav-menu {
            padding: 20px 0;
        }
        .nav-item {
            padding: 0;
            margin: 4px 0;
        }
        .nav-link {
            color: rgba(255,255,255,0.65);
            padding: 12px 20px;
            display: flex;
            align-items: center;
            transition: all 0.3s;
        }
        .nav-link:hover {
            color: #fff;
            background-color: rgba(255,255,255,0.1);
        }
        .nav-link.active {
            color: #fff;
            background-color: #1890ff;
        }
        .nav-link i {
            margin-right: 10px;
            width: 16px;
            text-align: center;
        }
        .main-content {
            margin-left: 200px;
            padding: 20px;
            min-height: 100vh;
        }
        .header {
            background: #fff;
            padding: 16px 24px;
            box-shadow: 0 1px 4px rgba(0,21,41,0.08);
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .search-box {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .search-input {
            width: 300px;
        }
        .stat-card {
            background: #fff;
            border-radius: 4px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 1px 4px rgba(0,21,41,0.08);
        }
        .stat-card h5 {
            color: rgba(0,0,0,0.85);
            margin-bottom: 15px;
        }
        .stat-number {
            font-size: 28px;
            font-weight: bold;
            color: #1890ff;
            margin-bottom: 10px;
        }
        .stat-desc {
            color: rgba(0,0,0,0.45);
            font-size: 13px;
        }
        .chart-card {
            background: #fff;
            border-radius: 4px;
            margin-bottom: 20px;
            box-shadow: 0 1px 4px rgba(0,21,41,0.08);
        }
        .chart-header {
            padding: 16px;
            border-bottom: 1px solid #f0f0f0;
        }
        .chart-content {
            padding: 16px;
        }
    </style>
</head>
<body>
    <div class="sidebar">
        <div class="sidebar-header">
            <h3>景区智能管理系统</h3>
        </div>
        <ul class="nav flex-column nav-menu">
            <li class="nav-item">
                <a class="nav-link active" href="/">
                    <i class="bi bi-house"></i>首页概览
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/crowd/statistics">
                    <i class="bi bi-people"></i>人群统计
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/flow/analysis">
                    <i class="bi bi-graph-up"></i>客流分析
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/alert">
                    <i class="bi bi-exclamation-triangle"></i>告警管理
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/device">
                    <i class="bi bi-camera"></i>设备管理
                </a>
            </li>
        </ul>
    </div>

    <div class="main-content">
        <div class="header">
            <div class="search-box">
                <div class="input-group search-input">
                    <input type="text" class="form-control" placeholder="请输入景区名称">
                    <button class="btn btn-primary" type="button">查询</button>
                </div>
                <button class="btn btn-outline-secondary">重置</button>
            </div>
            <div class="user-info">
                <span class="me-3">管理员</span>
                <button class="btn btn-outline-danger btn-sm">退出</button>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 col-lg-3">
                <div class="stat-card">
                    <h5>设备总数</h5>
                    <div class="stat-number">12</div>
                    <div class="stat-desc">
                        <span class="text-success">在线：10</span> | 
                        <span class="text-danger">离线：2</span>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-lg-3">
                <div class="stat-card">
                    <h5>当前客流量</h5>
                    <div class="stat-number">1,234</div>
                    <div class="stat-desc">
                        较昨日 <span class="text-success">↑5%</span>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-lg-3">
                <div class="stat-card">
                    <h5>今日告警</h5>
                    <div class="stat-number">5</div>
                    <div class="stat-desc">
                        待处理：2 | 已处理：3
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-lg-3">
                <div class="stat-card">
                    <h5>平均密度</h5>
                    <div class="stat-number">0.45</div>
                    <div class="stat-desc">
                        人/平方米
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-8">
                <div class="chart-card">
                    <div class="chart-header">
                        <h5 class="mb-0">实时客流趋势</h5>
                    </div>
                    <div class="chart-content">
                        <div id="flowTrendChart" style="height: 350px;"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="chart-card">
                    <div class="chart-header">
                        <h5 class="mb-0">告警分布</h5>
                    </div>
                    <div class="chart-content">
                        <div id="alertPieChart" style="height: 350px;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="/static/js/jquery.min.js"></script>
    <script src="/static/js/bootstrap.bundle.min.js"></script>
    <script src="/static/js/echarts.min.js"></script>
    <script>
        $(document).ready(function() {
            // 初始化客流趋势图表
            var flowTrendChart = echarts.init(document.getElementById('flowTrendChart'));
            var flowTrendOption = {
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
                    data: ['00:00', '02:00', '04:00', '06:00', '08:00', '10:00', '12:00'],
                    axisLine: {
                        lineStyle: {
                            color: '#999'
                        }
                    }
                },
                yAxis: {
                    type: 'value',
                    name: '人数',
                    axisLine: {
                        lineStyle: {
                            color: '#999'
                        }
                    }
                },
                series: [{
                    data: [820, 932, 901, 934, 1290, 1330, 1320],
                    type: 'line',
                    smooth: true,
                    lineStyle: {
                        color: '#1890ff',
                        width: 3
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
            flowTrendChart.setOption(flowTrendOption);

            // 初始化告警分布图表
            var alertPieChart = echarts.init(document.getElementById('alertPieChart'));
            var alertPieOption = {
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b}: {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 10,
                    top: 'center',
                    data: ['密度告警', '客流告警', '设备告警']
                },
                series: [{
                    name: '告警分布',
                    type: 'pie',
                    radius: ['50%', '70%'],
                    avoidLabelOverlap: false,
                    itemStyle: {
                        borderRadius: 10,
                        borderColor: '#fff',
                        borderWidth: 2
                    },
                    label: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        label: {
                            show: true,
                            fontSize: '20',
                            fontWeight: 'bold'
                        }
                    },
                    labelLine: {
                        show: false
                    },
                    data: [
                        {value: 3, name: '密度告警', itemStyle: {color: '#ff4d4f'}},
                        {value: 2, name: '客流告警', itemStyle: {color: '#ffa940'}},
                        {value: 1, name: '设备告警', itemStyle: {color: '#1890ff'}}
                    ]
                }]
            };
            alertPieChart.setOption(alertPieOption);

            // 响应式调整图表大小
            window.addEventListener('resize', function() {
                flowTrendChart.resize();
                alertPieChart.resize();
            });
        });
    </script>
</body>
</html> 