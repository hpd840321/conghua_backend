<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container-fluid">
    <!-- 区域选择 -->
    <div class="row mb-4">
        <div class="col-md-4">
            <select class="form-select" id="areaSelect">
                <option value="">选择区域</option>
            </select>
        </div>
    </div>

    <!-- 实时人群计数 -->
    <div class="row mb-4">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    实时人群计数
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-6">
                            <div id="realtimeCount" style="height: 300px;"></div>
                        </div>
                        <div class="col-md-6">
                            <img id="panoramaImage" class="img-fluid" alt="全景图">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    客流压力分析
                </div>
                <div class="card-body">
                    <div id="flowPressure" style="height: 300px;"></div>
                </div>
            </div>
        </div>
    </div>

    <!-- 时段客流量统计 -->
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-header">
                    时段客流量统计
                    <div class="float-end">
                        <input type="date" class="form-control-sm" id="startDate">
                        <input type="date" class="form-control-sm" id="endDate">
                    </div>
                </div>
                <div class="card-body">
                    <div id="hourlyStats" style="height: 400px;"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    // 初始化图表实例
    const realtimeChart = echarts.init(document.getElementById('realtimeCount'));
    const pressureChart = echarts.init(document.getElementById('flowPressure'));
    const hourlyChart = echarts.init(document.getElementById('hourlyStats'));

    // 加载区域列表
    function loadAreas() {
        $.get('/api/areas/all', function(res) {
            const select = $('#areaSelect');
            res.data.records.forEach(area => {
                select.append($('<option>').val(area.id).text(area.areaName));
            });
        });
    }

    // 更新实时人数
    function updateRealtimeCount(areaId) {
        $.get('/api/crowd-count/realtime/' + areaId, function(res) {
            const data = res.data;
            realtimeChart.setOption({
                title: {
                    text: '实时人数'
                },
                series: [{
                    type: 'gauge',
                    data: [{
                        value: data.count,
                        name: '当前人数'
                    }]
                }]
            });
            $('#panoramaImage').attr('src', data.panoramaImageUrl);
        });
    }

    // 更新客流压力
    function updateFlowPressure(areaId) {
        $.get('/api/crowd-count/pressure/' + areaId, function(res) {
            const data = res.data;
            pressureChart.setOption({
                title: {
                    text: '客流压力分布'
                },
                series: [{
                    type: 'pie',
                    data: [
                        {value: data.low, name: '低压力'},
                        {value: data.medium, name: '中压力'},
                        {value: data.high, name: '高压力'}
                    ]
                }]
            });
        });
    }

    // 更新时段统计
    function updateHourlyStats(areaId, startDate, endDate) {
        $.get('/api/crowd-count/hourly', {
            areaId: areaId,
            startTime: startDate,
            endTime: endDate
        }, function(res) {
            const data = res.data;
            hourlyChart.setOption({
                title: {
                    text: '时段客流量统计'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: data.map(item => item.hour)
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    type: 'line',
                    data: data.map(item => item.count)
                }]
            });
        });
    }

    // 页面加载完成后初始化
    $(document).ready(function() {
        loadAreas();

        // 区域选择变化事件
        $('#areaSelect').change(function() {
            const areaId = $(this).val();
            if (areaId) {
                updateRealtimeCount(areaId);
                updateFlowPressure(areaId);
                updateHourlyStats(areaId, 
                    $('#startDate').val(), 
                    $('#endDate').val());
            }
        });

        // 日期选择变化事件
        $('#startDate, #endDate').change(function() {
            const areaId = $('#areaSelect').val();
            if (areaId) {
                updateHourlyStats(areaId,
                    $('#startDate').val(),
                    $('#endDate').val());
            }
        });

        // 设置默认日期范围
        const today = new Date();
        const lastWeek = new Date(today.getTime() - 7 * 24 * 60 * 60 * 1000);
        $('#startDate').val(lastWeek.toISOString().split('T')[0]);
        $('#endDate').val(today.toISOString().split('T')[0]);
    });
</script> 