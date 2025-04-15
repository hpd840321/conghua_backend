<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../layout/header.jsp" %>

        <div class="container-fluid">
            <div class="row">
                <div class="col-12">
                    <div class="card">
                        <div class="card-header">
                            <h3 class="card-title">告警详情</h3>
                            <div class="card-tools">
                                <button type="button" class="btn btn-default" onclick="history.back()">
                                    <i class="fas fa-arrow-left"></i> 返回
                                </button>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <table class="table table-bordered">
                                        <tr>
                                            <th style="width: 150px;">告警ID</th>
                                            <td id="alertId"></td>
                                        </tr>
                                        <tr>
                                            <th>设备编码</th>
                                            <td id="deviceCode"></td>
                                        </tr>
                                        <tr>
                                            <th>景区名称</th>
                                            <td id="tourismName"></td>
                                        </tr>
                                        <tr>
                                            <th>告警类型</th>
                                            <td id="alertType"></td>
                                        </tr>
                                        <tr>
                                            <th>告警等级</th>
                                            <td id="alertLevel"></td>
                                        </tr>
                                        <tr>
                                            <th>告警内容</th>
                                            <td id="alertContent"></td>
                                        </tr>
                                        <tr>
                                            <th>告警时间</th>
                                            <td id="alertTime"></td>
                                        </tr>
                                        <tr>
                                            <th>处理状态</th>
                                            <td id="status"></td>
                                        </tr>
                                        <tr>
                                            <th>处理时间</th>
                                            <td id="handleTime"></td>
                                        </tr>
                                        <tr>
                                            <th>处理人</th>
                                            <td id="handler"></td>
                                        </tr>
                                        <tr>
                                            <th>处理说明</th>
                                            <td id="handleDesc"></td>
                                        </tr>
                                        <tr>
                                            <th>备注</th>
                                            <td id="remark"></td>
                                        </tr>
                                    </table>
                                </div>
                                <div class="col-md-6">
                                    <div class="card">
                                        <div class="card-header">
                                            <h3 class="card-title">相关图片</h3>
                                        </div>
                                        <div class="card-body">
                                            <div class="row" id="alertImages">
                                                <!-- 图片将通过JavaScript动态加载 -->
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row mt-4">
                                <div class="col-12">
                                    <div class="card">
                                        <div class="card-header">
                                            <h3 class="card-title">处理记录</h3>
                                        </div>
                                        <div class="card-body">
                                            <table class="table table-bordered" id="handleRecordTable">
                                                <thead>
                                                    <tr>
                                                        <th>处理时间</th>
                                                        <th>处理人</th>
                                                        <th>处理说明</th>
                                                        <th>处理结果</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <!-- 处理记录将通过JavaScript动态加载 -->
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row mt-4">
                                <div class="col-12">
                                    <button type="button" class="btn btn-primary"
                                        onclick="$('#handleModal').modal('show')">
                                        <i class="fas fa-edit"></i> 处理告警
                                    </button>
                                    <button type="button" class="btn btn-default" onclick="history.back()">
                                        <i class="fas fa-times"></i> 关闭
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 处理告警模态框 -->
        <div class="modal fade" id="handleModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">处理告警</h5>
                        <button type="button" class="close" data-dismiss="modal">
                            <span>&times;</span>
                        </button>
                    </div>
                    <form id="handleForm">
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="handleDescInput">处理说明</label>
                                <textarea class="form-control" id="handleDescInput" rows="3" required></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                            <button type="submit" class="btn btn-primary">确定</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <%@ include file="../layout/footer.jsp" %>

            <!-- 页面相关的JavaScript -->
            <script src="${pageContext.request.contextPath}/static/js/alert-detail.js"></script>