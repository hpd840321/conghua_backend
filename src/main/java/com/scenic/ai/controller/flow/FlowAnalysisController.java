package com.scenic.ai.controller.flow;

import com.scenic.ai.common.Result;
import com.scenic.ai.domain.model.flow.*;
import com.scenic.ai.service.flow.FlowAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 流量分析控制器
 * 提供流量分析相关的RESTful API接口
 *
 * @author conghua
 * @version 1.0.0
 * @since 2024.01.20
 */
@Slf4j
@Tag(name = "流量分析接口")
@RestController
@RequestMapping("/api/v1/flow-analysis")
@Validated
public class FlowAnalysisController {

    @Autowired
    private FlowAnalysisService flowAnalysisService;

    /**
     * 获取实时流量分析
     */
    @Operation(summary = "获取实时流量分析")
    @GetMapping("/{areaId}/realtime")
    public FlowAnalysis getRealTimeAnalysis(
            @Parameter(description = "区域ID", required = true) @PathVariable String areaId) {
        return flowAnalysisService.getRealTimeAnalysis(areaId);
    }
    
    /**
     * 获取流量趋势
     */
    @Operation(summary = "获取流量趋势")
    @GetMapping("/{areaId}/trend")
    public List<FlowAnalysis.FlowTrendPoint> getFlowTrend(
        @Parameter(description = "区域ID", required = true) @PathVariable String areaId,
        @Parameter(description = "开始时间", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
        @Parameter(description = "结束时间", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
        @Parameter(description = "时间间隔") @RequestParam(defaultValue = "%Y-%m-%d %H:00:00") String interval
    ) {
        return flowAnalysisService.getFlowTrend(areaId, startTime, endTime, interval);
    }
    
    /**
     * 获取热力图数据
     */
    @Operation(summary = "获取热力图数据")
    @GetMapping("/{areaId}/heat-map")
    public List<FlowAnalysis.HeatMapPoint> getHeatMapData(
        @Parameter(description = "区域ID", required = true) @PathVariable String areaId,
        @Parameter(description = "开始时间", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
        @Parameter(description = "结束时间", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime
    ) {
        return flowAnalysisService.getHeatMapData(areaId, startTime, endTime);
    }
    
    /**
     * 获取路径分析
     */
    @Operation(summary = "获取路径分析")
    @GetMapping("/{areaId}/path-analysis")
    public List<FlowAnalysis.PathAnalysisData> getPathAnalysis(
        @Parameter(description = "区域ID", required = true) @PathVariable String areaId,
        @Parameter(description = "开始时间", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
        @Parameter(description = "结束时间", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
        @Parameter(description = "最小计数") @RequestParam(defaultValue = "10") int minCount
    ) {
        return flowAnalysisService.getPathAnalysis(areaId, startTime, endTime, minCount);
    }
    
    /**
     * 获取拥堵预警
     */
    @Operation(summary = "获取拥堵预警")
    @GetMapping("/{areaId}/congestion")
    public List<FlowAnalysis.CongestionWarning> getCongestionWarning(
        @Parameter(description = "区域ID", required = true) @PathVariable String areaId,
        @Parameter(description = "阈值") @RequestParam(defaultValue = "0.7") double threshold
    ) {
        return flowAnalysisService.getCongestionWarning(areaId, threshold);
    }
    
    /**
     * 获取流量预测
     */
    @Operation(summary = "获取流量预测")
    @GetMapping("/{areaId}/forecast")
    public List<FlowAnalysis.FlowForecast> getFlowForecast(
        @Parameter(description = "区域ID", required = true) @PathVariable String areaId,
        @Parameter(description = "开始时间", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime
    ) {
        return flowAnalysisService.getFlowForecast(areaId, startTime);
    }

    /**
     * 获取实时流量数据
     *
     * @param areaId 区域ID
     * @return 实时流量数据
     */
    @Operation(summary = "获取实时流量数据", description = "获取指定区域的实时流量数据")
    @GetMapping("/realtime/{areaId}")
    public Result<FlowData> getRealtimeFlow(
            @Parameter(description = "区域ID", required = true) @PathVariable String areaId) {
        log.info("获取实时流量数据，区域ID：{}", areaId);
        return Result.success(flowAnalysisService.getRealtimeFlow(areaId));
    }

    /**
     * 获取流量热力图数据
     *
     * @param areaId 区域ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 热力图数据
     */
    @Operation(summary = "获取流量热力图", description = "获取指定区域和时间范围的流量热力图数据")
    @GetMapping("/heatmap/{areaId}")
    public Result<HeatmapData> getFlowHeatmap(
            @Parameter(description = "区域ID", required = true) @PathVariable String areaId,
            @Parameter(description = "开始时间", required = true) @RequestParam String startTime,
            @Parameter(description = "结束时间", required = true) @RequestParam String endTime) {
        log.info("获取流量热力图，区域ID：{}，开始时间：{}，结束时间：{}", 
                areaId, startTime, endTime);
        return Result.success(flowAnalysisService.getFlowHeatmap(areaId, startTime, endTime));
    }

    /**
     * 获取路径分析数据
     *
     * @param areaId 区域ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 路径分析数据
     */
    @Operation(summary = "获取路径分析", description = "获取指定区域和时间范围的访客路径分析数据")
    @GetMapping("/path-analysis/{areaId}")
    public Result<PathAnalysisData> getPathAnalysis(
            @Parameter(description = "区域ID", required = true) @PathVariable String areaId,
            @Parameter(description = "开始时间", required = true) @RequestParam String startTime,
            @Parameter(description = "结束时间", required = true) @RequestParam String endTime) {
        log.info("获取路径分析，区域ID：{}，开始时间：{}，结束时间：{}", 
                areaId, startTime, endTime);
        return Result.success(flowAnalysisService.getPathAnalysis(areaId, startTime, endTime));
    }

    /**
     * 获取驻留时间分析
     *
     * @param areaId 区域ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 驻留时间分析数据
     */
    @Operation(summary = "获取驻留时间分析", description = "获取指定区域和时间范围的访客驻留时间分析")
    @GetMapping("/stay-time/{areaId}")
    public Result<StayTimeAnalysis> getStayTimeAnalysis(
            @Parameter(description = "区域ID", required = true) @PathVariable String areaId,
            @Parameter(description = "开始时间", required = true) @RequestParam String startTime,
            @Parameter(description = "结束时间", required = true) @RequestParam String endTime) {
        log.info("获取驻留时间分析，区域ID：{}，开始时间：{}，结束时间：{}", 
                areaId, startTime, endTime);
        return Result.success(flowAnalysisService.getStayTimeAnalysis(areaId, startTime, endTime));
    }

    /**
     * 获取区域流量对比
     *
     * @param areaIds 区域ID列表
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 区域流量对比数据
     */
    @Operation(summary = "获取区域流量对比", description = "获取多个区域的流量对比数据")
    @PostMapping("/comparison")
    public Result<List<FlowComparison>> getFlowComparison(
            @Parameter(description = "区域ID列表", required = true) @RequestBody List<String> areaIds,
            @Parameter(description = "开始时间", required = true) @RequestParam String startTime,
            @Parameter(description = "结束时间", required = true) @RequestParam String endTime) {
        log.info("获取区域流量对比，区域ID列表：{}，开始时间：{}，结束时间：{}", 
                areaIds, startTime, endTime);
        return Result.success(flowAnalysisService.getFlowComparison(areaIds, startTime, endTime));
    }

    /**
     * 获取流量预测数据
     *
     * @param areaId 区域ID
     * @param forecastHours 预测小时数
     * @return 流量预测数据
     */
    @Operation(summary = "获取流量预测", description = "获取指定区域的未来流量预测数据")
    @GetMapping("/forecast/{areaId}")
    public Result<FlowForecast> getFlowForecast(
            @Parameter(description = "区域ID", required = true) @PathVariable String areaId,
            @Parameter(description = "预测小时数", required = true) @RequestParam Integer forecastHours) {
        log.info("获取流量预测，区域ID：{}，预测小时数：{}", areaId, forecastHours);
        return Result.success(flowAnalysisService.getFlowForecast(areaId, forecastHours));
    }

    /**
     * 导出流量分析报告
     *
     * @param areaId 区域ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param reportType 报告类型
     * @return 报告文件路径
     */
    @Operation(summary = "导出流量分析报告", description = "导出指定条件的流量分析报告")
    @GetMapping("/export/{areaId}")
    public Result<String> exportFlowReport(
            @Parameter(description = "区域ID", required = true) @PathVariable String areaId,
            @Parameter(description = "开始时间", required = true) @RequestParam String startTime,
            @Parameter(description = "结束时间", required = true) @RequestParam String endTime,
            @Parameter(description = "报告类型", required = true) @RequestParam String reportType) {
        log.info("导出流量分析报告，区域ID：{}，开始时间：{}，结束时间：{}，报告类型：{}", 
                areaId, startTime, endTime, reportType);
        return Result.success(flowAnalysisService.exportFlowReport(areaId, startTime, endTime, reportType));
    }
} 