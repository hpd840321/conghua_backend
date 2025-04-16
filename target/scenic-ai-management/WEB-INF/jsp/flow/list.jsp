<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>客流分析</title>
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/static/css/flow-analysis.css">
    <script src="${ctx}/static/js/jquery.min.js"></script>
    <script src="${ctx}/static/js/bootstrap.min.js"></script>
    <script src="${ctx}/static/js/echarts.min.js"></script>
</head>
<body>
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
                        <label>流动方向</label>
                        <select class="form-control" id="flowDirection">
                            <option value="">全部</option>
                            <option value="IN">进入</option>
                            <option value="OUT">离开</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label>开始时间</label>
                        <input type="datetime-local" class="form-control" id="startTime">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label>结束时间</label>
                        <input type="datetime-local" class="form-control" id="endTime">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label>&nbsp;</label>
                        <button class="btn btn-primary form-control" id="searchBtn">查询</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- 统计概览 -->
        <div class="stats-section">
            <div class="row">
                <div class="col-md-3">
                    <div class="stats-card">
                        <div class="stats-title">总客流量</div>
                        <div class="stats-value" id="totalFlowCount">0</div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="stats-card">
                        <div class="stats-title">进入人数</div>
                        <div class="stats-value" id="inFlowCount">0</div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="stats-card">
                        <div class="stats-title">离开人数</div>
                        <div class="stats-value" id="outFlowCount">0</div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="stats-card">
                        <div class="stats-title">记录数量</div>
                        <div class="stats-value" id="recordCount">0</div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 图表展示 -->
        <div class="chart-section">
            <div class="row">
                <div class="col-md-6">
                    <div class="chart-card">
                        <div class="chart-title">客流方向分布</div>
                        <div id="directionChart" class="chart-container"></div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="chart-card">
                        <div class="chart-title">客流趋势</div>
                        <div id="trendChart" class="chart-container"></div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 数据列表 -->
        <div class="table-section">
            <div class="table-header">
                <h5>客流分析数据</h5>
            </div>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>景区名称</th>
                        <th>设备名称</th>
                        <th>客流量</th>
                        <th>流动方向</th>
                        <th>记录时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody id="dataTable">
                    <!-- 数据行将通过JavaScript动态生成 -->
                </tbody>
            </table>
            <div class="pagination-container">
                <ul class="pagination" id="pagination">
                    <!-- 分页将通过JavaScript动态生成 -->
                </ul>
            </div>
        </div>
    </div>

    <!-- 详情模态框 -->
    <div class="modal fade" id="detailModal" tabindex="-1" role="dialog" aria-labelledby="detailModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="detailModalLabel">客流详情</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="detail-item">
                                <label>景区名称：</label>
                                <span id="detailTourismName"></span>
                            </div>
                            <div class="detail-item">
                                <label>设备名称：</label>
                                <span id="detailDeviceName"></span>
                            </div>
                            <div class="detail-item">
                                <label>客流量：</label>
                                <span id="detailFlowCount"></span>
                            </div>
                            <div class="detail-item">
                                <label>流动方向：</label>
                                <span id="detailFlowDirection"></span>
                            </div>
                            <div class="detail-item">
                                <label>记录时间：</label>
                                <span id="detailRecordTime"></span>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="detail-image">
                                <img id="detailImage" src="" alt="全景图" class="img-fluid">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>

    <script src="${ctx}/static/js/flow-analysis.js"></script>
</body>
</html> 