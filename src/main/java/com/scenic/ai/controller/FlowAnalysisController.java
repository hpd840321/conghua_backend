package com.scenic.ai.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.model.FlowAnalysis;
import com.scenic.ai.service.FlowAnalysisService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 客流分析控制器
 */
@RestController
@RequestMapping("/api/v1/flow-analysis")
public class FlowAnalysisController {

    private final FlowAnalysisService flowAnalysisService;

    public FlowAnalysisController(FlowAnalysisService flowAnalysisService) {
        this.flowAnalysisService = flowAnalysisService;
    }

    /**
     * 根据设备编码查询客流数据
     */
    @GetMapping("/by-device")
    public ResponseEntity<List<FlowAnalysis>> getFlowByDevice(
            @RequestParam String deviceCode,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return ResponseEntity.ok(flowAnalysisService.getFlowByDevice(deviceCode, startTime, endTime));
    }

    /**
     * 根据景区名称查询客流数据
     */
    @GetMapping("/by-tourism")
    public ResponseEntity<List<FlowAnalysis>> getFlowByTourism(
            @RequestParam String tourismName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return ResponseEntity.ok(flowAnalysisService.getFlowByTourism(tourismName, startTime, endTime));
    }

    /**
     * 统计时段客流分布
     */
    @GetMapping("/stats/hour-distribution")
    public ResponseEntity<List<Map<String, Object>>> getFlowHourDistribution(
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String tourismName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return ResponseEntity.ok(flowAnalysisService.getFlowHourDistribution(deviceCode, tourismName, startTime, endTime));
    }

    /**
     * 统计客流方向分布
     */
    @GetMapping("/stats/direction-distribution")
    public ResponseEntity<List<Map<String, Object>>> getFlowDirectionDistribution(
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String tourismName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return ResponseEntity.ok(flowAnalysisService.getFlowDirectionDistribution(deviceCode, tourismName, startTime, endTime));
    }

    /**
     * 获取客流趋势数据
     */
    @GetMapping("/stats/trend")
    public ResponseEntity<List<Map<String, Object>>> getFlowTrend(
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String tourismName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return ResponseEntity.ok(flowAnalysisService.getFlowTrend(deviceCode, tourismName, startTime, endTime));
    }

    /**
     * 分页查询客流数据
     */
    @GetMapping
    public ResponseEntity<IPage<FlowAnalysis>> pageFlowAnalysis(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String flowDirection,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        Page<FlowAnalysis> page = new Page<>(current, size);
        return ResponseEntity.ok(flowAnalysisService.pageFlowAnalysis(page, tourismName, deviceCode, flowDirection, startTime, endTime));
    }

    /**
     * 获取客流统计概览
     */
    @GetMapping("/stats/overview")
    public ResponseEntity<Map<String, Object>> getFlowStatistics(
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return ResponseEntity.ok(flowAnalysisService.getFlowStatistics(tourismName, startTime, endTime));
    }

    /**
     * 获取设备最新客流数据
     */
    @GetMapping("/latest")
    public ResponseEntity<FlowAnalysis> getLatestFlowByDevice(
            @RequestParam String deviceCode) {
        return ResponseEntity.ok(flowAnalysisService.getLatestFlowByDevice(deviceCode));
    }
} 