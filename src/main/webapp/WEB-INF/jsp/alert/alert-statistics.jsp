<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

<!-- 引入告警统计页面样式 -->
<link rel="stylesheet" href="${ctx}/static/css/alert/alert-statistics.css">

<div class="container-fluid">
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-header">
                    <h3 class="card-title">告警统计</h3>
                </div>
                <div class="card-body">
                    <!-- 筛选条件 -->
                    <div class="row mb-3">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label for="tourismName">景区名称</label>
                                <input type="text" class="form-control" id="tourismName" placeholder="请输入景区名称">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label for="deviceCode">设备编码</label>
                                <input type="text" class="form-control" id="deviceCode" placeholder="请输入设备编码">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label for="startTime">开始时间</label>
                                <div class="input-group date" id="startTimePicker">
                                    <input type="text" class="form-control" id="startTime">
                                    <div class="input-group-append">
                                        <span class="input-group-text"><i class="fa fa-calendar"></i></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label for="endTime">结束时间</label>
                                <div class="input-group date" id="endTimePicker">
                                    <input type="text" class="form-control" id="endTime">
                                    <div class="input-group-append">
                                        <span class="input-group-text"><i class="fa fa-calendar"></i></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-12">
                            <button type="button" class="btn btn-primary" id="btnSearch">
                                <i class="fa fa-search"></i> 查询
                            </button>
                            <button type="button" class="btn btn-default" id="btnReset">
                                <i class="fa fa-refresh"></i> 重置
                            </button>
                        </div>
                    </div>

                    <!-- 统计概览 -->
                    <div class="row mb-4">
                        <div class="col-md-3">
                            <div class="info-box">
                                <span class="info-box-icon bg-info"><i class="fa fa-bell"></i></span>
                                <div class="info-box-content">
                                    <span class="info-box-text">总告警数</span>
                                    <span class="info-box-number" id="totalCount">0</span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="info-box">
                                <span class="info-box-icon bg-warning"><i class="fa fa-clock-o"></i></span>
                                <div class="info-box-content">
                                    <span class="info-box-text">待处理告警</span>
                                    <span class="info-box-number" id="pendingCount">0</span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="info-box">
                                <span class="info-box-icon bg-success"><i class="fa fa-check"></i></span>
                                <div class="info-box-content">
                                    <span class="info-box-text">已处理告警</span>
                                    <span class="info-box-number" id="processedCount">0</span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="info-box">
                                <span class="info-box-icon bg-primary"><i class="fa fa-bar-chart"></i></span>
                                <div class="info-box-content">
                                    <span class="info-box-text">处理率</span>
                                    <span class="info-box-number" id="processRate">0%</span>
                                    <div class="progress">
                                        <div class="progress-bar" style="width: 0%"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 图表区域 -->
                    <div class="row">
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h3 class="card-title">告警时段分布</h3>
                                </div>
                                <div class="card-body">
                                    <div id="timeDistributionChart" style="height: 300px;" class="chart-container"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h3 class="card-title">告警类型分布</h3>
                                </div>
                                <div class="card-body">
                                    <div id="typeDistributionChart" style="height: 300px;" class="chart-container"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h3 class="card-title">告警级别分布</h3>
                                </div>
                                <div class="card-body">
                                    <div id="levelDistributionChart" style="height: 300px;" class="chart-container"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h3 class="card-title">告警趋势</h3>
                                </div>
                                <div class="card-body">
                                    <div id="trendChart" style="height: 300px;" class="chart-container"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h3 class="card-title">告警设备分布</h3>
                                </div>
                                <div class="card-body">
                                    <div id="deviceDistributionChart" style="height: 300px;" class="chart-container"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h3 class="card-title">告警景区分布</h3>
                                </div>
                                <div class="card-body">
                                    <div id="tourismDistributionChart" style="height: 300px;" class="chart-container"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../layout/footer.jsp" %>
<script src="${ctx}/static/js/alert/alert-statistics.js"></script> 