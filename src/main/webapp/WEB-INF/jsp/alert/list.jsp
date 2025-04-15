<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../layout/header.jsp" %>

        <div class="container-fluid">
            <!-- 筛选条件 -->
            <div class="card mb-4">
                <div class="card-body">
                    <form id="searchForm" class="form-inline">
                        <div class="form-group mx-sm-3 mb-2">
                            <label for="deviceCode" class="mr-2">设备编号</label>
                            <input type="text" class="form-control" id="deviceCode" name="deviceCode">
                        </div>
                        <div class="form-group mx-sm-3 mb-2">
                            <label for="tourismName" class="mr-2">景区名称</label>
                            <input type="text" class="form-control" id="tourismName" name="tourismName">
                        </div>
                        <div class="form-group mx-sm-3 mb-2">
                            <label for="alertType" class="mr-2">告警类型</label>
                            <select class="form-control" id="alertType" name="alertType">
                                <option value="">全部</option>
                                <option value="1">人数超限</option>
                                <option value="2">设备离线</option>
                                <option value="3">异常行为</option>
                            </select>
                        </div>
                        <div class="form-group mx-sm-3 mb-2">
                            <label for="alertLevel" class="mr-2">告警级别</label>
                            <select class="form-control" id="alertLevel" name="alertLevel">
                                <option value="">全部</option>
                                <option value="1">一般</option>
                                <option value="2">重要</option>
                                <option value="3">紧急</option>
                            </select>
                        </div>
                        <div class="form-group mx-sm-3 mb-2">
                            <label for="alertStatus" class="mr-2">处理状态</label>
                            <select class="form-control" id="alertStatus" name="alertStatus">
                                <option value="">全部</option>
                                <option value="0">未处理</option>
                                <option value="1">已处理</option>
                            </select>
                        </div>
                        <div class="form-group mx-sm-3 mb-2">
                            <label for="timeRange" class="mr-2">时间范围</label>
                            <input type="text" class="form-control" id="timeRange" name="timeRange">
                        </div>
                        <button type="button" class="btn btn-primary mb-2" onclick="search()">查询</button>
                        <button type="button" class="btn btn-secondary mb-2 ml-2" onclick="reset()">重置</button>
                    </form>
                </div>
            </div>

            <!-- 统计数据 -->
            <div class="row mb-4">
                <div class="col-xl-3 col-md-6">
                    <div class="card bg-primary text-white mb-4">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h6 class="mb-0">总告警数</h6>
                                    <h2 class="mb-0" id="totalCount">0</h2>
                                </div>
                                <i class="fas fa-bell fa-2x"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-3 col-md-6">
                    <div class="card bg-warning text-white mb-4">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h6 class="mb-0">未处理告警</h6>
                                    <h2 class="mb-0" id="unhandledCount">0</h2>
                                </div>
                                <i class="fas fa-exclamation-triangle fa-2x"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-3 col-md-6">
                    <div class="card bg-success text-white mb-4">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h6 class="mb-0">已处理告警</h6>
                                    <h2 class="mb-0" id="handledCount">0</h2>
                                </div>
                                <i class="fas fa-check-circle fa-2x"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-3 col-md-6">
                    <div class="card bg-danger text-white mb-4">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h6 class="mb-0">紧急告警</h6>
                                    <h2 class="mb-0" id="urgentCount">0</h2>
                                </div>
                                <i class="fas fa-radiation fa-2x"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 图表区域 -->
            <div class="row mb-4">
                <div class="col-xl-6">
                    <div class="card mb-4">
                        <div class="card-header">
                            <i class="fas fa-chart-pie mr-1"></i>
                            告警类型分布
                        </div>
                        <div class="card-body">
                            <div id="typeChart" style="height: 300px;"></div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-6">
                    <div class="card mb-4">
                        <div class="card-header">
                            <i class="fas fa-chart-bar mr-1"></i>
                            告警级别分布
                        </div>
                        <div class="card-body">
                            <div id="levelChart" style="height: 300px;"></div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 告警列表 -->
            <div class="card mb-4">
                <div class="card-header">
                    <i class="fas fa-table mr-1"></i>
                    告警列表
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered" id="alertTable" width="100%" cellspacing="0">
                            <thead>
                                <tr>
                                    <th>设备编号</th>
                                    <th>景区名称</th>
                                    <th>告警类型</th>
                                    <th>告警级别</th>
                                    <th>告警内容</th>
                                    <th>告警时间</th>
                                    <th>处理状态</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <!-- 处理告警模态框 -->
        <div class="modal fade" id="handleModal" tabindex="-1" role="dialog" aria-labelledby="handleModalLabel"
            aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="handleModalLabel">处理告警</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="handleForm">
                            <input type="hidden" id="alertId" name="alertId">
                            <div class="form-group">
                                <label for="handleDescription">处理说明</label>
                                <textarea class="form-control" id="handleDescription" name="handleDescription" rows="3"
                                    required></textarea>
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

        <%@ include file="../layout/footer.jsp" %>

            <!-- 页面相关的JavaScript -->
            <script src="${pageContext.request.contextPath}/static/js/alert.js"></script>