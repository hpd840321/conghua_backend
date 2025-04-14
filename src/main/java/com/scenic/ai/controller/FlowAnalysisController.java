package com.scenic.ai.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.common.Result;
import com.scenic.ai.model.FlowAnalysis;
import com.scenic.ai.service.FlowAnalysisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 客流分析控制器
 */
@Api(tags = "客流分析")
@RestController
@RequestMapping("/api/v1/flow-analysis")
@RequiredArgsConstructor
public class FlowAnalysisController {

    private final FlowAnalysisService flowAnalysisService;

    @ApiOperation("根据设备编码查询客流数据")
    @GetMapping("/by-device")
    public ResponseEntity<List<FlowAnalysis>> getFlowByDevice(
            @ApiParam("设备编码") @RequestParam String deviceCode,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return ResponseEntity.ok(flowAnalysisService.getFlowByDevice(deviceCode, startTime, endTime));
    }

    @ApiOperation("根据景区名称查询客流数据")
    @GetMapping("/by-tourism")
    public ResponseEntity<List<FlowAnalysis>> getFlowByTourism(
            @ApiParam("景区名称") @RequestParam String tourismName,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return ResponseEntity.ok(flowAnalysisService.getFlowByTourism(tourismName, startTime, endTime));
    }

    @ApiOperation("统计时段客流分布")
    @GetMapping("/stats/hour-distribution")
    public ResponseEntity<List<Map<String, Object>>> getHourDistribution(
            @ApiParam("设备编码") @RequestParam(required = false) String deviceCode,
            @ApiParam("景区名称") @RequestParam(required = false) String tourismName,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return ResponseEntity.ok(flowAnalysisService.getHourDistribution(deviceCode, tourismName, startTime, endTime));
    }

    @ApiOperation("统计流向分布")
    @GetMapping("/stats/direction-distribution")
    public ResponseEntity<List<Map<String, Object>>> getDirectionDistribution(
            @ApiParam("设备编码") @RequestParam(required = false) String deviceCode,
            @ApiParam("景区名称") @RequestParam(required = false) String tourismName,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return ResponseEntity.ok(flowAnalysisService.getDirectionDistribution(deviceCode, tourismName, startTime, endTime));
    }

    @ApiOperation("获取客流趋势")
    @GetMapping("/stats/flow-trend")
    public ResponseEntity<List<Map<String, Object>>> getFlowTrend(
            @ApiParam("设备编码") @RequestParam(required = false) String deviceCode,
            @ApiParam("景区名称") @RequestParam(required = false) String tourismName,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return ResponseEntity.ok(flowAnalysisService.getFlowTrend(deviceCode, tourismName, startTime, endTime));
    }

    @ApiOperation("分页查询客流数据")
    @GetMapping
    public ResponseEntity<IPage<FlowAnalysis>> pageFlowAnalysis(
            @ApiParam("页码") @RequestParam(defaultValue = "1") long current,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") long size,
            @ApiParam("景区名称") @RequestParam(required = false) String tourismName,
            @ApiParam("设备编码") @RequestParam(required = false) String deviceCode,
            @ApiParam("流动方向") @RequestParam(required = false) String flowDirection,
            @ApiParam("开始时间") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        Page<FlowAnalysis> page = new Page<>(current, size);
        return ResponseEntity.ok(flowAnalysisService.pageFlowAnalysis(page, tourismName, deviceCode, flowDirection, startTime, endTime));
    }

    @ApiOperation("获取设备最新客流数据")
    @GetMapping("/latest")
    public ResponseEntity<FlowAnalysis> getLatestFlowByDevice(
            @ApiParam("设备编码") @RequestParam String deviceCode) {
        return ResponseEntity.ok(flowAnalysisService.getLatestFlowByDevice(deviceCode));
    }
} 