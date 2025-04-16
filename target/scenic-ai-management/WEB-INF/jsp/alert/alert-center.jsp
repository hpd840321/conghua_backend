<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>告警中心</title>
    <%@ include file="../common/header.jsp"%>
    <link rel="stylesheet" href="${ctx}/static/css/alert-center.css">
</head>
<body class="hold-transition sidebar-mini">
    <div class="wrapper">
        <%@ include file="../common/navbar.jsp"%>
        <%@ include file="../common/sidebar.jsp"%>
        
        <!-- Content Wrapper -->
        <div class="content-wrapper">
            <!-- Content Header -->
            <section class="content-header">
                <div class="container-fluid">
                    <div class="row mb-2">
                        <div class="col-sm-6">
                            <h1>告警中心</h1>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="container-fluid">
                    <!-- 筛选条件 -->
                    <div class="card">
                        <div class="card-body">
                            <form id="searchForm">
                                <div class="row">
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>景区名称</label>
                                            <input type="text" class="form-control" name="tourismName" placeholder="请输入景区名称">
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>设备编码</label>
                                            <input type="text" class="form-control" name="deviceCode" placeholder="请输入设备编码">
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>告警类型</label>
                                            <select class="form-control" name="alertType">
                                                <option value="">全部</option>
                                                <option value="CROWD">人群聚集</option>
                                                <option value="DENSITY">高密度</option>
                                                <option value="FLOW">异常流动</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>告警级别</label>
                                            <select class="form-control" name="alertLevel">
                                                <option value="">全部</option>
                                                <option value="1">低</option>
                                                <option value="2">中</option>
                                                <option value="3">高</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>告警状态</label>
                                            <select class="form-control" name="alertStatus">
                                                <option value="">全部</option>
                                                <option value="0">待处理</option>
                                                <option value="1">已处理</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>时间范围</label>
                                            <div class="input-group">
                                                <input type="text" class="form-control" id="startTime" name="startTime" placeholder="开始时间">
                                                <div class="input-group-append">
                                                    <span class="input-group-text">至</span>
                                                </div>
                                                <input type="text" class="form-control" id="endTime" name="endTime" placeholder="结束时间">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group" style="margin-top: 32px;">
                                            <button type="button" class="btn btn-primary" onclick="searchAlerts()">
                                                <i class="fas fa-search"></i> 查询
                                            </button>
                                            <button type="button" class="btn btn-default" onclick="resetForm()">
                                                <i class="fas fa-redo"></i> 重置
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- 统计图表 -->
                    <div class="row">
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h3 class="card-title">告警时段分布</h3>
                                </div>
                                <div class="card-body">
                                    <div id="timeDistributionChart" style="height: 300px;"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h3 class="card-title">告警类型分布</h3>
                                </div>
                                <div class="card-body">
                                    <div id="typeDistributionChart" style="height: 300px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 告警列表 -->
                    <div class="card">
                        <div class="card-header">
                            <h3 class="card-title">告警列表</h3>
                            <div class="card-tools">
                                <button type="button" class="btn btn-success btn-sm" onclick="batchHandle()">
                                    <i class="fas fa-check"></i> 批量处理
                                </button>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table id="alertTable" class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th width="40px">
                                                <input type="checkbox" id="checkAll">
                                            </th>
                                            <th>告警ID</th>
                                            <th>景区名称</th>
                                            <th>设备编码</th>
                                            <th>设备名称</th>
                                            <th>告警类型</th>
                                            <th>告警级别</th>
                                            <th>告警状态</th>
                                            <th>记录时间</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <!-- 动态加载数据 -->
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                            <div id="pagination" class="mt-3"></div>
                        </div>
                    </div>
                </div>
            </section>
        </div>

        <!-- 处理告警弹窗 -->
        <div class="modal fade" id="handleModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">处理告警</h5>
                        <button type="button" class="close" data-dismiss="modal">
                            <span>&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="handleForm">
                            <input type="hidden" id="alertId" name="alertId">
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

        <!-- 图片预览弹窗 -->
        <div class="modal fade" id="imageModal" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">全景图预览</h5>
                        <button type="button" class="close" data-dismiss="modal">
                            <span>&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <img id="previewImage" src="" class="img-fluid">
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="../common/footer.jsp"%>
    <script src="${ctx}/static/js/alert-center.js"></script>
</body>
</html> 