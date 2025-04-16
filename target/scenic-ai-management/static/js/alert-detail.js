// 告警详情页面JavaScript

// 页面加载完成后执行
$(function () {
    // 获取告警ID
    const alertId = getUrlParam('id');
    if (alertId) {
        // 加载告警详情
        loadAlertDetail(alertId);
    } else {
        showError('未找到告警ID');
    }

    // 初始化处理告警模态框
    initHandleModal();
});

// 从URL获取参数
function getUrlParam(name) {
    const reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    const r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURIComponent(r[2]); return null;
}

// 加载告警详情
function loadAlertDetail(alertId) {
    showLoading();
    $.ajax({
        url: '/api/alert/' + alertId,
        type: 'GET',
        success: function (response) {
            if (response.code === 200) {
                renderAlertDetail(response.data);
                loadHandleRecords(alertId);
            } else {
                showError(response.msg || '加载告警详情失败');
            }
        },
        error: function () {
            showError('加载告警详情失败');
        },
        complete: function () {
            hideLoading();
        }
    });
}

// 渲染告警详情
function renderAlertDetail(data) {
    // 渲染基本信息
    $('#alertId').text(data.id);
    $('#deviceCode').text(data.deviceCode);
    $('#tourismName').text(data.tourismName);
    $('#alertType').text(getAlertTypeText(data.alertType));
    $('#alertLevel').text(getAlertLevelText(data.alertLevel));
    $('#alertContent').text(data.alertContent);
    $('#alertTime').text(formatDateTime(data.alertTime));
    $('#status').text(getStatusText(data.status));
    $('#handleTime').text(data.handleTime ? formatDateTime(data.handleTime) : '-');
    $('#handler').text(data.handler || '-');
    $('#handleDesc').text(data.handleDesc || '-');
    $('#remark').text(data.remark || '-');

    // 渲染图片
    renderImages(data.images);
}

// 获取告警类型文本
function getAlertTypeText(type) {
    const types = {
        1: '客流异常',
        2: '设备离线',
        3: '设备故障',
        4: '其他'
    };
    return types[type] || '未知';
}

// 获取告警等级文本
function getAlertLevelText(level) {
    const levels = {
        1: '一般',
        2: '重要',
        3: '紧急'
    };
    return levels[level] || '未知';
}

// 获取状态文本
function getStatusText(status) {
    const statuses = {
        0: '未处理',
        1: '处理中',
        2: '已处理',
        3: '已关闭'
    };
    return statuses[status] || '未知';
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

// 渲染图片
function renderImages(images) {
    const container = $('#alertImages');
    container.empty();

    if (images && images.length > 0) {
        images.forEach(function (image) {
            container.append(`
                <div class="col-md-3 mb-3">
                    <div class="card">
                        <img src="${image}" class="card-img-top" alt="告警图片">
                    </div>
                </div>
            `);
        });
    } else {
        container.append('<p class="text-center">暂无图片</p>');
    }
}

// 加载处理记录
function loadHandleRecords(alertId) {
    $.ajax({
        url: '/api/alert/' + alertId + '/records',
        type: 'GET',
        success: function (response) {
            if (response.code === 200) {
                renderHandleRecords(response.data);
            } else {
                showError(response.msg || '加载处理记录失败');
            }
        },
        error: function () {
            showError('加载处理记录失败');
        }
    });
}

// 渲染处理记录
function renderHandleRecords(records) {
    const tbody = $('#handleRecordTable tbody');
    if (!records || records.length === 0) {
        $('#handleRecordTable tbody').html('<tr><td colspan="4" class="text-center">暂无处理记录</td></tr>');
        return;
    }

    let html = '';
    records.forEach(function (record) {
        html += '<tr>' +
            '<td>' + formatDateTime(record.handleTime) + '</td>' +
            '<td>' + record.handler + '</td>' +
            '<td>' + record.handleDesc + '</td>' +
            '<td>' + getStatusHtml(record.handleResult) + '</td>' +
            '</tr>';
    });
    $('#handleRecordTable tbody').html(html);
}

// 初始化处理告警模态框
function initHandleModal() {
    $('#handleForm').on('submit', function (e) {
        e.preventDefault();
        handleAlert();
    });
}

// 处理告警
function handleAlert() {
    const alertId = $('#alertId').text();
    const handleDesc = $('#handleDescInput').val();

    if (!handleDesc) {
        showError('请输入处理说明');
        return;
    }

    showLoading();
    $.ajax({
        url: '/api/alert/' + alertId + '/handle',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            handleDesc: handleDesc
        }),
        success: function (response) {
            if (response.code === 200) {
                showSuccess('处理成功');
                $('#handleModal').modal('hide');
                // 重新加载告警详情
                loadAlertDetail(alertId);
            } else {
                showError(response.msg || '处理失败');
            }
        },
        error: function () {
            showError('处理失败');
        },
        complete: function () {
            hideLoading();
        }
    });
}

// 显示加载动画
function showLoading() {
    if (!$('.loading-overlay').length) {
        $('body').append('<div class="loading-overlay"><div class="loading-spinner"></div></div>');
    }
}

// 隐藏加载动画
function hideLoading() {
    $('.loading-overlay').remove();
}

// 显示成功提示
function showSuccess(message) {
    showToast(message, 'success');
}

// 显示错误提示
function showError(message) {
    showToast(message, 'error');
}

// 显示提示消息
function showToast(message, type) {
    const toast = $('<div class="toast toast-' + type + '" role="alert">' +
        '<div class="toast-body">' + message + '</div>' +
        '</div>');

    $('body').append(toast);
    toast.toast({
        delay: 3000
    }).toast('show');

    toast.on('hidden.bs.toast', function () {
        toast.remove();
    });
} 