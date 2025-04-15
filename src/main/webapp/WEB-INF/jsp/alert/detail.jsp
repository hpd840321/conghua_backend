<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>告警详情</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <style>
        .detail-container {
            padding: 20px;
        }
        .detail-header {
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        .detail-title {
            font-size: 20px;
            font-weight: bold;
        }
        .detail-info {
            margin-top: 10px;
            color: #666;
        }
        .detail-content {
            margin-bottom: 20px;
        }
        .detail-item {
            margin-bottom: 15px;
        }
        .detail-label {
            font-weight: bold;
            color: #333;
        }
        .detail-value {
            color: #666;
        }
        .alert-level-1 { color: #dc3545; font-weight: bold; }
        .alert-level-2 { color: #ffc107; font-weight: bold; }
        .alert-level-3 { color: #28a745; font-weight: bold; }
        .alert-status-0 { color: #dc3545; font-weight: bold; }
        .alert-status-1 { color: #28a745; font-weight: bold; }
        .alert-status-2 { color: #6c757d; font-weight: bold; }
        .image-container {
            margin-top: 20px;
            text-align: center;
        }
        .image-container img {
            max-width: 100%;
            max-height: 500px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .action-buttons {
            margin-top: 20px;
            text-align: center;
        }
        .loading-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(255, 255, 255, 0.7);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 9999;
        }
        .loading-spinner {
            width: 50px;
            height: 50px;
            border: 5px solid #f3f3f3;
            border-top: 5px solid #3498db;
            border-radius: 50%;
            animation: spin 1s linear infinite;
        }
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
        .toast-container {
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 9999;
        }
        .toast {
            background-color: #fff;
            border-radius: 4px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.2);
            margin-bottom: 10px;
            padding: 15px;
            min-width: 250px;
            display: flex;
            align-items: center;
        }
        .toast-success {
            border-left: 4px solid #28a745;
        }
        .toast-error {
            border-left: 4px solid #dc3545;
        }
        .toast-warning {
            border-left: 4px solid #ffc107;
        }
        .toast-icon {
            margin-right: 10px;
            font-size: 20px;
        }
        .toast-success .toast-icon { color: #28a745; }
        .toast-error .toast-icon { color: #dc3545; }
        .toast-warning .toast-icon { color: #ffc107; }
        .toast-content {
            flex-grow: 1;
        }
        .toast-close {
            cursor: pointer;
            font-size: 18px;
            color: #6c757d;
        }
        .detail-card {
            background-color: #fff;
            border-radius: 4px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            padding: 20px;
            margin-bottom: 20px;
        }
        .detail-card-title {
            font-size: 16px;
            font-weight: bold;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        .detail-row {
            display: flex;
            flex-wrap: wrap;
            margin: 0 -10px;
        }
        .detail-col {
            flex: 0 0 50%;
            padding: 0 10px;
            margin-bottom: 15px;
        }
        .detail-description {
            white-space: pre-line;
            line-height: 1.5;
        }
        .no-image {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 300px;
            background-color: #f8f9fa;
            border: 1px dashed #ddd;
            border-radius: 4px;
            color: #6c757d;
        }
        .no-image i {
            font-size: 48px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="container-fluid detail-container">
        <div class="detail-header">
            <div class="detail-title">告警详情</div>
            <div class="detail-info">
                <span id="alertId"></span>
                <span class="ml-3">创建时间：<span id="createTime"></span></span>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="detail-card">
                    <div class="detail-card-title">基本信息</div>
                    <div class="detail-row">
                        <div class="detail-col">
                            <div class="detail-item">
                                <div class="detail-label">景区名称</div>
                                <div class="detail-value" id="tourismName"></div>
                            </div>
                        </div>
                        <div class="detail-col">
                            <div class="detail-item">
                                <div class="detail-label">设备编码</div>
                                <div class="detail-value" id="deviceCode"></div>
                            </div>
                        </div>
                        <div class="detail-col">
                            <div class="detail-item">
                                <div class="detail-label">设备名称</div>
                                <div class="detail-value" id="deviceName"></div>
                            </div>
                        </div>
                        <div class="detail-col">
                            <div class="detail-item">
                                <div class="detail-label">告警类型</div>
                                <div class="detail-value" id="alertType"></div>
                            </div>
                        </div>
                        <div class="detail-col">
                            <div class="detail-item">
                                <div class="detail-label">告警等级</div>
                                <div class="detail-value" id="alertLevel"></div>
                            </div>
                        </div>
                        <div class="detail-col">
                            <div class="detail-item">
                                <div class="detail-label">告警状态</div>
                                <div class="detail-value" id="alertStatus"></div>
                            </div>
                        </div>
                        <div class="detail-col">
                            <div class="detail-item">
                                <div class="detail-label">告警时间</div>
                                <div class="detail-value" id="recordTime"></div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="detail-card">
                    <div class="detail-card-title">告警描述</div>
                    <div class="detail-description" id="description"></div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="detail-card">
                    <div class="detail-card-title">告警图片</div>
                    <div class="image-container" id="imageContainer">
                        <img id="imageUrl" src="" alt="告警图片">
                    </div>
                </div>
            </div>
        </div>

        <div class="action-buttons">
            <button type="button" class="btn btn-primary" id="handleBtn">处理告警</button>
            <button type="button" class="btn btn-secondary" onclick="history.back()">返回</button>
        </div>
    </div>

    <!-- 处理告警弹窗 -->
    <div class="modal fade" id="handleModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">处理告警</h5>
                    <button type="button" class="close" data-dismiss="modal">
                        <span>&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="handleForm">
                        <input type="hidden" name="id" id="handleId">
                        <div class="form-group">
                            <label>处理说明</label>
                            <textarea class="form-control" name="description" rows="3" required></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" onclick="submitHandle()">确定</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 加载中遮罩 -->
    <div class="loading-overlay" id="loadingOverlay" style="display: none;">
        <div class="loading-spinner"></div>
    </div>

    <!-- 提示消息容器 -->
    <div class="toast-container" id="toastContainer"></div>

    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
    <script>
        // 页面加载完成后执行
        $(document).ready(function() {
            loadAlertDetail();
        });

        // 加载告警详情
        function loadAlertDetail() {
            const alertId = getUrlParam('id');
            if (!alertId) {
                showToast('error', '告警ID不能为空');
                return;
            }

            showLoading();
            $.ajax({
                url: '${pageContext.request.contextPath}/alert/detail/' + alertId,
                type: 'GET',
                success: function(response) {
                    hideLoading();
                    if (response.code === 200) {
                        renderAlertDetail(response.data);
                    } else {
                        showToast('error', '加载告警详情失败：' + response.message);
                    }
                },
                error: function(xhr, status, error) {
                    hideLoading();
                    showToast('error', '加载告警详情失败：' + error);
                }
            });
        }

        // 渲染告警详情
        function renderAlertDetail(alert) {
            // 基本信息
            $('#alertId').text('告警ID：' + alert.id);
            $('#createTime').text(formatDateTime(alert.createTime));
            $('#tourismName').text(alert.tourismName || '-');
            $('#deviceCode').text(alert.deviceCode || '-');
            $('#deviceName').text(alert.deviceName || '-');
            $('#alertType').text(getAlertTypeName(alert.alertType));
            $('#alertLevel').html(`<span class="alert-level-${alert.alertLevel}">${getAlertLevelName(alert.alertLevel)}</span>`);
            $('#alertStatus').html(`<span class="alert-status-${alert.alertStatus}">${getAlertStatusName(alert.alertStatus)}</span>`);
            $('#recordTime').text(formatDateTime(alert.recordTime));

            // 告警描述
            $('#description').text(alert.description || '暂无描述');

            // 告警图片
            if (alert.imageUrl) {
                $('#imageContainer').html(`
                    <img src="${alert.imageUrl}" alt="告警图片" class="img-fluid">
                `);
            } else {
                $('#imageContainer').html(`
                    <div class="no-image">
                        <i class="fas fa-image"></i>
                        <p>暂无图片</p>
                    </div>
                `);
            }

            // 处理记录
            if (alert.handlingRecords && alert.handlingRecords.length > 0) {
                let html = '';
                alert.handlingRecords.forEach(function(record) {
                    html += `
                        <div class="handling-record">
                            <div class="record-time">${formatDateTime(record.createTime)}</div>
                            <div class="record-content">${record.description}</div>
                        </div>
                    `;
                });
                $('#handlingRecords').html(html);
            } else {
                $('#handlingRecords').html('<div class="text-muted">暂无处理记录</div>');
            }

            // 操作按钮
            if (alert.alertStatus === 0) {
                $('#actionButtons').html(`
                    <button type="button" class="btn btn-primary" onclick="showHandleModal()">处理告警</button>
                    <button type="button" class="btn btn-secondary" onclick="history.back()">返回列表</button>
                `);
            } else {
                $('#actionButtons').html(`
                    <button type="button" class="btn btn-secondary" onclick="history.back()">返回列表</button>
                `);
            }
        }

        // 显示处理弹窗
        function showHandleModal() {
            $('#handleForm textarea[name="description"]').val('');
            $('#handleModal').modal('show');
        }

        // 处理告警
        function handleAlert() {
            const description = $('#handleForm textarea[name="description"]').val();
            if (!description) {
                showToast('warning', '请输入处理说明');
                return;
            }

            const alertId = getUrlParam('id');
            showLoading();
            $.ajax({
                url: '${pageContext.request.contextPath}/alert/' + alertId + '/handle',
                type: 'POST',
                data: { description: description },
                success: function(response) {
                    hideLoading();
                    if (response.code === 200) {
                        showToast('success', '处理成功');
                        $('#handleModal').modal('hide');
                        loadAlertDetail();
                    } else {
                        showToast('error', '处理失败：' + response.message);
                    }
                },
                error: function(xhr, status, error) {
                    hideLoading();
                    showToast('error', '处理失败：' + error);
                }
            });
        }

        // 显示加载中
        function showLoading() {
            if (!$('.loading-overlay').length) {
                $('body').append('<div class="loading-overlay"><div class="loading-spinner"></div></div>');
            }
        }

        // 隐藏加载中
        function hideLoading() {
            $('.loading-overlay').remove();
        }

        // 显示提示信息
        function showToast(type, message) {
            const toast = $(`
                <div class="toast toast-${type}">
                    <div class="toast-icon">
                        <i class="fas fa-${type === 'success' ? 'check-circle' : type === 'error' ? 'times-circle' : 'exclamation-circle'}"></i>
                    </div>
                    <div class="toast-content">${message}</div>
                    <div class="toast-close">&times;</div>
                </div>
            `);

            if (!$('.toast-container').length) {
                $('body').append('<div class="toast-container"></div>');
            }

            $('.toast-container').append(toast);
            setTimeout(() => toast.remove(), 3000);

            toast.find('.toast-close').click(function() {
                toast.remove();
            });
        }

        // 格式化日期时间
        function formatDateTime(dateTimeStr) {
            if (!dateTimeStr) return '-';
            const date = new Date(dateTimeStr);
            return date.getFullYear() + '-' +
                String(date.getMonth() + 1).padStart(2, '0') + '-' +
                String(date.getDate()).padStart(2, '0') + ' ' +
                String(date.getHours()).padStart(2, '0') + ':' +
                String(date.getMinutes()).padStart(2, '0') + ':' +
                String(date.getSeconds()).padStart(2, '0');
        }

        // 获取告警类型名称
        function getAlertTypeName(type) {
            const types = {
                '1': '人员聚集',
                '2': '异常行为',
                '3': '设备离线'
            };
            return types[type] || '-';
        }

        // 获取告警等级名称
        function getAlertLevelName(level) {
            const levels = {
                '1': '高',
                '2': '中',
                '3': '低'
            };
            return levels[level] || '-';
        }

        // 获取告警状态名称
        function getAlertStatusName(status) {
            const statuses = {
                '0': '待处理',
                '1': '已处理',
                '2': '已忽略'
            };
            return statuses[status] || '-';
        }

        // 获取URL参数
        function getUrlParam(name) {
            const reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            const r = window.location.search.substr(1).match(reg);
            if (r != null) return decodeURIComponent(r[2]);
            return null;
        }
    </script>
</body>
</html> 