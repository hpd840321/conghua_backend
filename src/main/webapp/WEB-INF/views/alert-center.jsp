<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container-fluid">
    <!-- 查询条件 -->
    <div class="row mb-4">
        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <form id="searchForm" class="row g-3">
                        <div class="col-md-3">
                            <select class="form-select" id="areaSelect" name="areaId">
                                <option value="">选择区域</option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <input type="date" class="form-control" id="startDate" name="startTime">
                        </div>
                        <div class="col-md-3">
                            <input type="date" class="form-control" id="endDate" name="endTime">
                        </div>
                        <div class="col-md-3">
                            <button type="submit" class="btn btn-primary me-2">查询</button>
                            <button type="reset" class="btn btn-secondary">重置</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- 统计图表 -->
    <div class="row mb-4">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    告警时段分布
                </div>
                <div class="card-body">
                    <div id="hourlyAlerts" style="height: 300px;"></div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    告警类型分布
                </div>
                <div class="card-body">
                    <div id="alertTypes" style="height: 300px;"></div>
                </div>
            </div>
        </div>
    </div>

    <!-- 告警列表 -->
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-header">
                    告警列表
                </div>
                <div class="card-body">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>告警时间</th>
                                <th>区域</th>
                                <th>设备</th>
                                <th>告警类型</th>
                                <th>告警内容</th>
                                <th>告警等级</th>
                                <th>状态</th>
                            </tr>
                        </thead>
                        <tbody id="alertList">
                        </tbody>
                    </table>
                    <nav>
                        <ul class="pagination justify-content-center" id="pagination">
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    // 初始化图表实例
    const hourlyChart = echarts.init(document.getElementById('hourlyAlerts'));
    const typeChart = echarts.init(document.getElementById('alertTypes'));

    // 加载区域列表
    function loadAreas() {
        $.get('/api/areas/all', function(res) {
            const select = $('#areaSelect');
            res.data.records.forEach(area => {
                select.append($('<option>').val(area.id).text(area.areaName));
            });
        });
    }

    // 更新告警时段分布
    function updateHourlyAlerts(areaId, startDate, endDate) {
        $.get('/api/alerts/hourly', {
            areaId: areaId,
            startTime: startDate,
            endTime: endDate
        }, function(res) {
            const data = res.data;
            hourlyChart.setOption({
                title: {
                    text: '告警时段分布'
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
                    type: 'bar',
                    data: data.map(item => item.count)
                }]
            });
        });
    }

    // 更新告警类型分布
    function updateAlertTypes(areaId, startDate, endDate) {
        $.get('/api/alerts/types', {
            areaId: areaId,
            startTime: startDate,
            endTime: endDate
        }, function(res) {
            const data = res.data;
            typeChart.setOption({
                title: {
                    text: '告警类型分布'
                },
                tooltip: {
                    trigger: 'item'
                },
                series: [{
                    type: 'pie',
                    radius: '50%',
                    data: data.map(item => ({
                        name: item.alertType,
                        value: item.count
                    }))
                }]
            });
        });
    }

    // 加载告警列表
    function loadAlerts(page = 1) {
        const formData = new FormData($('#searchForm')[0]);
        formData.append('page', page);
        formData.append('size', 10);

        $.get('/api/alerts', formData, function(res) {
            const data = res.data;
            
            // 更新列表
            const tbody = $('#alertList');
            tbody.empty();
            data.records.forEach(alert => {
                tbody.append(`
                    <tr>
                        <td>${alert.alertTime}</td>
                        <td>${alert.areaName}</td>
                        <td>${alert.deviceName}</td>
                        <td>${alert.alertType}</td>
                        <td>${alert.alertContent}</td>
                        <td>${getAlertLevelText(alert.alertLevel)}</td>
                        <td>${getAlertStatusText(alert.status)}</td>
                    </tr>
                `);
            });

            // 更新分页
            updatePagination(data.total, data.size, data.current);
        });
    }

    // 更新分页组件
    function updatePagination(total, size, current) {
        const totalPages = Math.ceil(total / size);
        const pagination = $('#pagination');
        pagination.empty();

        // 上一页
        pagination.append(`
            <li class="page-item ${current === 1 ? 'disabled' : ''}">
                <a class="page-link" href="#" data-page="${current - 1}">上一页</a>
            </li>
        `);

        // 页码
        for (let i = 1; i <= totalPages; i++) {
            pagination.append(`
                <li class="page-item ${current === i ? 'active' : ''}">
                    <a class="page-link" href="#" data-page="${i}">${i}</a>
                </li>
            `);
        }

        // 下一页
        pagination.append(`
            <li class="page-item ${current === totalPages ? 'disabled' : ''}">
                <a class="page-link" href="#" data-page="${current + 1}">下一页</a>
            </li>
        `);
    }

    // 获取告警等级文本
    function getAlertLevelText(level) {
        const levels = {
            1: '<span class="badge bg-success">低</span>',
            2: '<span class="badge bg-warning">中</span>',
            3: '<span class="badge bg-danger">高</span>'
        };
        return levels[level] || '';
    }

    // 获取告警状态文本
    function getAlertStatusText(status) {
        const statuses = {
            0: '<span class="badge bg-danger">未处理</span>',
            1: '<span class="badge bg-success">已处理</span>'
        };
        return statuses[status] || '';
    }

    // 页面加载完成后初始化
    $(document).ready(function() {
        loadAreas();

        // 设置默认日期范围
        const today = new Date();
        const lastWeek = new Date(today.getTime() - 7 * 24 * 60 * 60 * 1000);
        $('#startDate').val(lastWeek.toISOString().split('T')[0]);
        $('#endDate').val(today.toISOString().split('T')[0]);

        // 表单提交事件
        $('#searchForm').submit(function(e) {
            e.preventDefault();
            const areaId = $('#areaSelect').val();
            const startDate = $('#startDate').val();
            const endDate = $('#endDate').val();

            updateHourlyAlerts(areaId, startDate, endDate);
            updateAlertTypes(areaId, startDate, endDate);
            loadAlerts(1);
        });

        // 分页点击事件
        $('#pagination').on('click', '.page-link', function(e) {
            e.preventDefault();
            const page = $(this).data('page');
            loadAlerts(page);
        });

        // 重置按钮点击事件
        $('button[type="reset"]').click(function() {
            setTimeout(() => {
                loadAlerts(1);
            }, 0);
        });

        // 初始加载
        loadAlerts(1);
    });
</script> 