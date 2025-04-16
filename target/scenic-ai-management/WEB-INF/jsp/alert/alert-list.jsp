<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

<div class="container-fluid">
    <!-- 筛选条件 -->
    <div class="filter-section">
        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label>景区名称</label>
                    <input type="text" class="form-control" id="tourismName">
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>设备编码</label>
                    <input type="text" class="form-control" id="deviceCode">
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>告警类型</label>
                    <select class="form-control" id="alertType">
                        <option value="">全部</option>
                        <option value="CROWD">人群聚集</option>
                        <option value="DENSITY">密度过高</option>
                    </select>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>告警级别</label>
                    <select class="form-control" id="alertLevel">
                        <option value="">全部</option>
                        <option value="1">低</option>
                        <option value="2">中</option>
                        <option value="3">高</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row mt-3">
            <div class="col-md-6">
                <div class="form-group">
                    <label>时间范围</label>
                    <div class="input-group">
                        <input type="text" class="form-control" id="startTime">
                        <div class="input-group-append">
                            <span class="input-group-text">至</span>
                        </div>
                        <input type="text" class="form-control" id="endTime">
                    </div>
                </div>
            </div>
            <div class="col-md-6 text-right">
                <button class="btn btn-primary" onclick="search()">查询</button>
                <button class="btn btn-default" onclick="reset()">重置</button>
            </div>
        </div>
    </div>

    <!-- 数据列表 -->
    <div class="data-table mt-4">
        <div class="table-header">
            <h5>告警列表</h5>
            <div class="table-actions">
                <button class="btn btn-danger" onclick="batchDelete()">批量删除</button>
            </div>
        </div>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th><input type="checkbox" id="checkAll"></th>
                    <th>景区名称</th>
                    <th>设备名称</th>
                    <th>告警类型</th>
                    <th>告警级别</th>
                    <th>告警状态</th>
                    <th>记录时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody id="alertList">
                <!-- 数据行将通过JavaScript动态生成 -->
            </tbody>
        </table>
        <!-- 分页 -->
        <div class="pagination-container">
            <ul class="pagination" id="pagination">
                <!-- 分页将通过JavaScript动态生成 -->
            </ul>
        </div>
    </div>
</div>

<!-- 详情模态框 -->
<div class="modal fade" id="detailModal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">告警详情</h5>
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>景区名称</label>
                            <p id="detailTourismName"></p>
                        </div>
                        <div class="form-group">
                            <label>设备名称</label>
                            <p id="detailDeviceName"></p>
                        </div>
                        <div class="form-group">
                            <label>告警类型</label>
                            <p id="detailAlertType"></p>
                        </div>
                        <div class="form-group">
                            <label>告警级别</label>
                            <p id="detailAlertLevel"></p>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>告警状态</label>
                            <p id="detailAlertStatus"></p>
                        </div>
                        <div class="form-group">
                            <label>记录时间</label>
                            <p id="detailRecordTime"></p>
                        </div>
                        <div class="form-group">
                            <label>告警描述</label>
                            <p id="detailDescription"></p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label>全景图</label>
                            <img id="detailImageUrl" class="img-fluid" alt="全景图">
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="handleAlert()">处理告警</button>
            </div>
        </div>
    </div>
</div>

<%@ include file="../layout/footer.jsp" %>

<script src="${pageContext.request.contextPath}/static/js/alert/alert-list.js"></script> 