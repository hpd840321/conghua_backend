<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>告警列表</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/laydate.css">
    <style>
        .search-box {
            padding: 15px;
            background-color: #f8f9fa;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        .alert-level-1 { color: #dc3545; font-weight: bold; }
        .alert-level-2 { color: #ffc107; font-weight: bold; }
        .alert-level-3 { color: #28a745; font-weight: bold; }
        .alert-status-0 { color: #dc3545; font-weight: bold; }
        .alert-status-1 { color: #28a745; font-weight: bold; }
        .alert-status-2 { color: #6c757d; font-weight: bold; }
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
        .table-hover tbody tr:hover {
            background-color: rgba(0,0,0,.075);
            cursor: pointer;
        }
        .btn-action {
            margin-right: 5px;
        }
        .empty-data {
            text-align: center;
            padding: 30px;
            color: #6c757d;
        }
        .empty-data i {
            font-size: 48px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <!-- 搜索条件 -->
        <div class="search-box">
            <form id="searchForm" class="form-inline">
                <div class="form-group mx-sm-3 mb-2">
                    <input type="text" class="form-control" name="tourismName" placeholder="景区名称">
        </div>
                <div class="form-group mx-sm-3 mb-2">
                    <input type="text" class="form-control" name="deviceCode" placeholder="设备编码">
        </div>
                <div class="form-group mx-sm-3 mb-2">
                    <select class="form-control" name="alertType">
                        <option value="">告警类型</option>
                        <option value="1">人员聚集</option>
                        <option value="2">异常行为</option>
                        <option value="3">设备离线</option>
            </select>
        </div>
                <div class="form-group mx-sm-3 mb-2">
                    <select class="form-control" name="alertLevel">
                        <option value="">告警等级</option>
                        <option value="1">高</option>
                <option value="2">中</option>
                        <option value="3">低</option>
            </select>
        </div>
                <div class="form-group mx-sm-3 mb-2">
                    <select class="form-control" name="alertStatus">
                        <option value="">处理状态</option>
                <option value="0">待处理</option>
                <option value="1">已处理</option>
                        <option value="2">已忽略</option>
            </select>
        </div>
                <div class="form-group mx-sm-3 mb-2">
                    <input type="text" class="form-control" id="startTime" name="startTime" placeholder="开始时间">
        </div>
                <div class="form-group mx-sm-3 mb-2">
                    <input type="text" class="form-control" id="endTime" name="endTime" placeholder="结束时间">
        </div>
                <button type="button" class="btn btn-primary mb-2" onclick="search()">搜索</button>
                <button type="button" class="btn btn-secondary mb-2 ml-2" onclick="reset()">重置</button>
    </form>
</div>

        <!-- 数据表格 -->
        <div class="table-responsive">
            <table class="table table-striped table-bordered table-hover">
                <thead class="thead-dark">
                    <tr>
                        <th>景区名称</th>
                        <th>设备编码</th>
                        <th>告警类型</th>
                        <th>告警等级</th>
                        <th>告警状态</th>
                        <th>告警时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody id="dataList"></tbody>
            </table>
        </div>

        <!-- 分页 -->
        <div class="d-flex justify-content-between align-items-center">
            <div>
                共 <span id="total">0</span> 条记录
            </div>
            <nav>
                <ul class="pagination" id="pagination"></ul>
        </nav>
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
                        <input type="hidden" name="id">
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
    <script src="${pageContext.request.contextPath}/static/js/laydate.js"></script>
    <script>
        // 初始化日期时间选择器
        laydate.render({
            elem: '#startTime',
            type: 'datetime',
            format: 'yyyy-MM-dd HH:mm:ss'
        });
        laydate.render({
            elem: '#endTime',
            type: 'datetime',
            format: 'yyyy-MM-dd HH:mm:ss'
        });

        // 当前页码和每页大小
        let currentPage = 1;
        const pageSize = 10;

        // 页面加载完成后执行
        $(document).ready(function() {
            loadData();
        });

        // 加载数据
        function loadData() {
            showLoading();
            const params = {
                pageNum: currentPage,
                pageSize: pageSize,
                tourismName: $('input[name="tourismName"]').val(),
                deviceCode: $('input[name="deviceCode"]').val(),
                alertType: $('select[name="alertType"]').val(),
                alertLevel: $('select[name="alertLevel"]').val(),
                alertStatus: $('select[name="alertStatus"]').val(),
                startTime: $('#startTime').val(),
                endTime: $('#endTime').val()
            };

            $.ajax({
                url: '${pageContext.request.contextPath}/alert/page',
                type: 'GET',
                data: params,
                success: function(response) {
                    hideLoading();
                    if (response.code === 200) {
                        renderTable(response.data);
                        renderPagination(response.data);
                    } else {
                        showToast('error', '加载数据失败：' + response.message);
                    }
                },
                error: function(xhr, status, error) {
                    hideLoading();
                    showToast('error', '加载数据失败：' + error);
                }
            });
        }

        // 渲染表格
        function renderTable(data) {
            const tbody = $('#dataList');
            tbody.empty();

            if (data.records.length === 0) {
                tbody.html('<tr><td colspan="7" class="empty-data"><i class="fas fa-inbox"></i><br>暂无数据</td></tr>');
                return;
            }

            data.records.forEach(function(item) {
                const tr = $('<tr>');
                tr.append(`<td>${item.tourismName || '-'}</td>`);
                tr.append(`<td>${item.deviceCode || '-'}</td>`);
                tr.append(`<td>${getAlertTypeName(item.alertType)}</td>`);
                tr.append(`<td><span class="alert-level-${item.alertLevel}">${getAlertLevelName(item.alertLevel)}</span></td>`);
                tr.append(`<td><span class="alert-status-${item.alertStatus}">${getAlertStatusName(item.alertStatus)}</span></td>`);
                tr.append(`<td>${formatDateTime(item.recordTime)}</td>`);
                tr.append(`
                    <td>
                        <button type="button" class="btn btn-sm btn-primary btn-action" onclick="showDetail(${item.id})">详情</button>
                        ${item.alertStatus === 0 ? `<button type="button" class="btn btn-sm btn-success btn-action" onclick="showHandleModal(${item.id})">处理</button>` : ''}
                    </td>
                `);
                tbody.append(tr);
            });
        }

        // 渲染分页
        function renderPagination(data) {
            const pagination = $('#pagination');
            pagination.empty();
            $('#total').text(data.total);

            if (data.pages <= 1) {
                return;
            }

            // 上一页
            pagination.append(`
                <li class="page-item ${data.current === 1 ? 'disabled' : ''}">
                    <a class="page-link" href="javascript:void(0)" onclick="changePage(${data.current - 1})">上一页</a>
                </li>
            `);

            // 页码
            for (let i = 1; i <= data.pages; i++) {
                if (i === 1 || i === data.pages || (i >= data.current - 2 && i <= data.current + 2)) {
                    pagination.append(`
                        <li class="page-item ${i === data.current ? 'active' : ''}">
                            <a class="page-link" href="javascript:void(0)" onclick="changePage(${i})">${i}</a>
                        </li>
                    `);
                } else if (i === data.current - 3 || i === data.current + 3) {
                    pagination.append('<li class="page-item disabled"><a class="page-link">...</a></li>');
                }
            }

            // 下一页
            pagination.append(`
                <li class="page-item ${data.current === data.pages ? 'disabled' : ''}">
                    <a class="page-link" href="javascript:void(0)" onclick="changePage(${data.current + 1})">下一页</a>
                </li>
            `);
        }

        // 切换页码
        function changePage(page) {
            currentPage = page;
            loadData();
        }

        // 搜索
        function search() {
            currentPage = 1;
            loadData();
        }

        // 重置
        function reset() {
            $('#searchForm')[0].reset();
            currentPage = 1;
            loadData();
        }

        // 显示详情
        function showDetail(id) {
            window.location.href = '${pageContext.request.contextPath}/alert/detail/' + id;
        }

        // 显示处理弹窗
        function showHandleModal(id) {
            $('#handleForm input[name="id"]').val(id);
            $('#handleModal').modal('show');
        }

        // 处理告警
        function handleAlert() {
            const id = $('#handleForm input[name="id"]').val();
            const description = $('#handleForm textarea[name="description"]').val();

            if (!description) {
                showToast('warning', '请输入处理说明');
                return;
            }

            showLoading();
            $.ajax({
                url: '${pageContext.request.contextPath}/alert/' + id + '/handle',
                type: 'POST',
                data: { description: description },
                success: function(response) {
                    hideLoading();
                    if (response.code === 200) {
                        showToast('success', '处理成功');
                        $('#handleModal').modal('hide');
                        loadData();
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
    </script>
</body>
</html> 