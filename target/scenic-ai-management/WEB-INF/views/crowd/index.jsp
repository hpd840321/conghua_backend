<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>景区游客人数统计</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
    <script src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
    <style>
        .data-card {
            text-align: center;
            padding: 20px;
        }
        .data-card .number {
            font-size: 24px;
            font-weight: bold;
            color: #009688;
        }
        .data-card .label {
            margin-top: 10px;
            color: #666;
        }
        .chart-container {
            height: 400px;
            margin: 10px 0;
        }
    </style>
</head>
<body>
    <div class="layui-container">
        <!-- 搜索条件 -->
        <div class="layui-card">
            <div class="layui-card-header">查询条件</div>
            <div class="layui-card-body">
                <form class="layui-form" id="searchForm">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">景区名称</label>
                            <div class="layui-input-inline">
                                <input type="text" name="tourismName" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">设备编码</label>
                            <div class="layui-input-inline">
                                <input type="text" name="deviceCode" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">算法类型</label>
                            <div class="layui-input-inline">
                                <select name="algName">
                                    <option value="">全部</option>
                                    <option value="CROWD_COUNT">人群计数</option>
                                    <option value="FLOW_ANALYSIS">客流分析</option>
                                    <option value="DENSITY_COUNT">高密度计数</option>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">时间范围</label>
                            <div class="layui-input-inline">
                                <input type="text" name="timeRange" class="layui-input" id="timeRange">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <button type="button" class="layui-btn" lay-submit lay-filter="searchBtn">查询</button>
                            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- 数据概览 -->
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md4">
                <div class="layui-card">
                    <div class="layui-card-body data-card">
                        <div class="number" id="totalCount">0</div>
                        <div class="label">当前总人数</div>
                    </div>
                </div>
            </div>
            <div class="layui-col-md4">
                <div class="layui-card">
                    <div class="layui-card-body data-card">
                        <div class="number" id="avgDensity">0</div>
                        <div class="label">平均密度(人/㎡)</div>
                    </div>
                </div>
            </div>
            <div class="layui-col-md4">
                <div class="layui-card">
                    <div class="layui-card-body data-card">
                        <div class="number" id="flowCount">0</div>
                        <div class="label">累计客流量</div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 图表展示 -->
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md6">
                <div class="layui-card">
                    <div class="layui-card-header">人群数量趋势</div>
                    <div class="layui-card-body">
                        <div id="countTrendChart" class="chart-container"></div>
                    </div>
                </div>
            </div>
            <div class="layui-col-md6">
                <div class="layui-card">
                    <div class="layui-card-header">客流分布热力图</div>
                    <div class="layui-card-body">
                        <div id="flowHeatMap" class="chart-container"></div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 实时监控 -->
        <div class="layui-card">
            <div class="layui-card-header">实时监控</div>
            <div class="layui-card-body">
                <table id="monitorTable" lay-filter="monitorTable"></table>
            </div>
        </div>
    </div>

    <!-- 图片预览工具栏 -->
    <script type="text/html" id="imageBar">
        <a class="layui-btn layui-btn-xs" lay-event="preview">查看图片</a>
    </script>

    <script>
    layui.use(['table', 'form', 'laydate', 'echarts'], function(){
        var table = layui.table;
        var form = layui.form;
        var laydate = layui.laydate;
        var $ = layui.$;
        
        // 初始化时间选择器
        laydate.render({
            elem: '#timeRange',
            type: 'datetime',
            range: true
        });

        // 初始化表格
        table.render({
            elem: '#monitorTable',
            url: '${pageContext.request.contextPath}/crowd/list',
            cols: [[
                {field: 'deviceName', title: '设备名称'},
                {field: 'count', title: '当前人数'},
                {field: 'density', title: '密度'},
                {field: 'algName', title: '算法类型'},
                {field: 'recordTime', title: '记录时间'},
                {title: '操作', toolbar: '#imageBar', width: 100}
            ]],
            page: true
        });

        // 表格工具栏事件
        table.on('tool(monitorTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'preview'){
                layer.open({
                    type: 1,
                    title: '全景图预览',
                    area: ['800px', '600px'],
                    content: '<img src="' + data.imageUrl + '" style="max-width: 100%; max-height: 100%;">'
                });
            }
        });

        // 查询按钮事件
        form.on('submit(searchBtn)', function(data){
            var field = data.field;
            var timeRange = field.timeRange;
            if(timeRange){
                var times = timeRange.split(' - ');
                field.startTime = times[0];
                field.endTime = times[1];
            }
            delete field.timeRange;
            
            table.reload('monitorTable', {
                where: field,
                page: {curr: 1}
            });
            loadData(field);
            return false;
        });

        // 加载数据
        function loadData(params) {
            // 加载概览数据
            $.get('${pageContext.request.contextPath}/crowd/overview', params, function(res){
                if(res.code === 200){
                    $('#totalCount').text(res.data.totalCount);
                    $('#avgDensity').text(res.data.avgDensity);
                    $('#flowCount').text(res.data.flowCount);
                }
            });

            // 加载趋势图
            $.get('${pageContext.request.contextPath}/crowd/trend', params, function(res){
                if(res.code === 200){
                    var countChart = echarts.init(document.getElementById('countTrendChart'));
                    countChart.setOption({
                        tooltip: {trigger: 'axis'},
                        xAxis: {type: 'category', data: res.data.times},
                        yAxis: {type: 'value'},
                        series: [{
                            data: res.data.counts,
                            type: 'line',
                            smooth: true
                        }]
                    });
                }
            });

            // 加载热力图
            $.get('${pageContext.request.contextPath}/crowd/heatmap', params, function(res){
                if(res.code === 200){
                    var heatMap = echarts.init(document.getElementById('flowHeatMap'));
                    heatMap.setOption({
                        tooltip: {position: 'top'},
                        grid: {height: '50%', top: '10%'},
                        xAxis: {type: 'category', data: res.data.hours},
                        yAxis: {type: 'category', data: res.data.areas},
                        visualMap: {
                            min: 0,
                            max: 100,
                            calculable: true,
                            orient: 'horizontal',
                            left: 'center',
                            bottom: '15%'
                        },
                        series: [{
                            name: '客流密度',
                            type: 'heatmap',
                            data: res.data.values,
                            label: {show: true},
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }]
                    });
                }
            });
        }

        // 初始加载
        loadData({});

        // 自动刷新
        setInterval(function(){
            var params = form.val('searchForm');
            loadData(params);
        }, 60000);
    });
    </script>
</body>
</html> 