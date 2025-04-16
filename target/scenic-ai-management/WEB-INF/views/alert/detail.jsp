<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>告警详情</title>
    <link href="<c:url value='/static/css/bootstrap.min.css'/>" rel="stylesheet">
    <style>
        .timeline {
            position: relative;
            padding: 20px 0;
        }
        .timeline::before {
            content: '';
            position: absolute;
            top: 0;
            bottom: 0;
            left: 20px;
            width: 2px;
            background: #dee2e6;
        }
        .timeline-item {
            position: relative;
            margin-bottom: 30px;
            padding-left: 50px;
        }
        .timeline-item::before {
            content: '';
            position: absolute;
            left: 16px;
            top: 0;
            width: 10px;
            height: 10px;
            border-radius: 50%;
            background: #007bff;
            border: 2px solid #fff;
        }
        .timeline-item.resolved::before {
            background: #28a745;
        }
        .timeline-content {
            padding: 15px;
            background: #f8f9fa;
            border-radius: 4px;
        }
        .alert-info-card {
            border-left: 4px solid #007bff;
            background: #f8f9fa;
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 4px;
        }
        .device-info-card {
            border-left: 4px solid #28a745;
            background: #f8f9fa;
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <div class="row mb-4">
            <div class="col-12">
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="/alert">告警管理</a></li>
                        <li class="breadcrumb-item active">告警详情</li>
                    </ol>
                </nav>
            </div>
        </div>

        <!-- 告警基本信息 -->
        <div class="row mb-4">
            <div class="col-md-6">
                <div class="alert-info-card">
                    <h5 class="card-title">告警信息</h5>
                    <div class="row mt-3">
                        <div class="col-md-4">
                            <label class="font-weight-bold">告警ID</label>
                            <p id="alertId"></p>
                        </div>
                        <div class="col-md-4">
                            <label class="font-weight-bold">告警类型</label>
                            <p id="alertType"></p>
                        </div>
                        <div class="col-md-4">
                            <label class="font-weight-bold">告警等级</label>
                            <p id="alertLevel"></p>
                        </div>
                    </div>
                    <div class="row mt-2">
                        <div class="col-md-4">
                            <label class="font-weight-bold">告警状态</label>
                            <p id="alertStatus"></p>
                        </div>
                        <div class="col-md-8">
                            <label class="font-weight-bold">告警时间</label>
                            <p id="createTime"></p>
                        </div>
                    </div>
                    <div class="row mt-2">
                        <div class="col-12">
                            <label class="font-weight-bold">告警描述</label>
                            <p id="description"></p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="device-info-card">
                    <h5 class="card-title">设备信息</h5>
                    <div class="row mt-3">
                        <div class="col-md-6">
                            <label class="font-weight-bold">景区名称</label>
                            <p id="tourismName"></p>
                        </div>
                        <div class="col-md-6">
                            <label class="font-weight-bold">设备编码</label>
                            <p id="deviceCode"></p>
                        </div>
                    </div>
                    <div class="row mt-2">
                        <div class="col-md-6">
                            <label class="font-weight-bold">设备名称</label>
                            <p id="deviceName"></p>
                        </div>
                        <div class="col-md-6">
                            <label class="font-weight-bold">设备位置</label>
                            <p id="deviceLocation"></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 告警图片 -->
        <div class="row mb-4" id="imageSection" style="display: none;">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">告警图片</h5>
                        <img id="alertImage" class="img-fluid" alt="告警图片">
                    </div>
                </div>
            </div>
        </div>

        <!-- 处理记录 -->
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">处理记录</h5>
                        <div class="timeline" id="handleRecords">
                            <!-- 处理记录将通过JavaScript动态添加 -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="<c:url value='/static/js/jquery.min.js'/>"></script>
    <script src="<c:url value='/static/js/bootstrap.bundle.min.js'/>"></script>
    <script>
        $(document).ready(function() {
            // 从URL获取告警ID
            const urlParams = new URLSearchParams(window.location.search);
            const alertId = urlParams.get('id');
            
            if (alertId) {
                loadAlertDetail(alertId);
                loadHandleRecords(alertId);
            } else {
                alert('未找到告警ID');
                window.location.href = '/alert';
            }
        });

        function loadAlertDetail(alertId) {
            $.get('/api/alert/' + alertId, function(data) {
                if (data.code === 200) {
                    const alert = data.data;
                    
                    // 更新告警信息
                    $('#alertId').text(alert.id);
                    $('#alertType').text(getAlertTypeText(alert.alertType));
                    $('#alertLevel').text(getAlertLevelText(alert.alertLevel));
                    $('#alertStatus').text(getAlertStatusText(alert.alertStatus));
                    $('#createTime').text(alert.createTime);
                    $('#description').text(alert.description);
                    
                    // 更新设备信息
                    $('#tourismName').text(alert.tourismName);
                    $('#deviceCode').text(alert.deviceCode);
                    $('#deviceName').text(alert.deviceName);
                    $('#deviceLocation').text(alert.deviceLocation || '未知');
                    
                    // 如果有告警图片，显示图片区域
                    if (alert.imageUrl) {
                        $('#alertImage').attr('src', alert.imageUrl);
                        $('#imageSection').show();
                    }
                } else {
                    alert(data.message || '加载告警详情失败');
                }
            });
        }

        function loadHandleRecords(alertId) {
            $.get('/api/alert/handle/records/' + alertId, function(data) {
                if (data.code === 200) {
                    const records = data.data;
                    const container = $('#handleRecords');
                    container.empty();
                    
                    records.forEach(function(record) {
                        const timelineItem = $('<div>')
                            .addClass('timeline-item' + (record.status === 'RESOLVED' ? ' resolved' : ''));
                        
                        const content = $('<div>')
                            .addClass('timeline-content')
                            .append(
                                $('<div>')
                                    .addClass('d-flex justify-content-between')
                                    .append(
                                        $('<h6>')
                                            .addClass('mb-1')
                                            .text(getAlertStatusText(record.status))
                                    )
                                    .append(
                                        $('<small>')
                                            .addClass('text-muted')
                                            .text(record.createTime)
                                    )
                            )
                            .append(
                                $('<p>')
                                    .addClass('mb-0')
                                    .text(record.comment)
                            );
                        
                        timelineItem.append(content);
                        container.append(timelineItem);
                    });
                } else {
                    alert(data.message || '加载处理记录失败');
                }
            });
        }

        function getAlertTypeText(type) {
            const types = {
                'DENSITY': '密度告警',
                'COUNT': '人数告警',
                'DEVICE': '设备告警'
            };
            return types[type] || type;
        }

        function getAlertLevelText(level) {
            const levels = {
                'HIGH': '高',
                'MEDIUM': '中',
                'LOW': '低'
            };
            return levels[level] || level;
        }

        function getAlertStatusText(status) {
            const statuses = {
                'PENDING': '待处理',
                'PROCESSING': '处理中',
                'RESOLVED': '已解决'
            };
            return statuses[status] || status;
        }
    </script>
</body>
</html> 