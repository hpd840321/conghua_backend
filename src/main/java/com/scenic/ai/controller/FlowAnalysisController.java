package com.scenic.ai.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.common.Result;
import com.scenic.ai.model.FlowAnalysis;
import com.scenic.ai.service.FlowAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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
@Tag(name = "客流分析")
@RestController
@RequestMapping("/api/flow-analysis")
@RequiredArgsConstructor
public class FlowAnalysisController {

    private final FlowAnalysisService flowAnalysisService;

    @GetMapping("/page")
    @Operation(summary = "分页查询客流分析数据")
    public Result<IPage<FlowAnalysis>> pageFlowAnalysis(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNo,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "景区名称") @RequestParam(required = false) String tourismName,
            @Parameter(description = "设备编码") @RequestParam(required = false) String deviceCode,
            @Parameter(description = "算法类型") @RequestParam(required = false) String algName,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        Page<FlowAnalysis> page = new Page<>(pageNo, pageSize);
        return Result.ok(flowAnalysisService.pageFlowAnalysis(page, tourismName, deviceCode, algName, startTime, endTime));
    }

    @GetMapping("/trend")
    @Operation(summary = "获取客流量趋势")
    public Result<List<Map<String, Object>>> getFlowTrend(
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String tourismName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return Result.ok(flowAnalysisService.getFlowTrend(deviceCode, tourismName, startTime, endTime));
    }

    @GetMapping("/direction/distribution")
    @Operation(summary = "获取客流方向分布")
    public Result<List<Map<String, Object>>> getFlowDirectionDistribution(
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String tourismName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return Result.ok(flowAnalysisService.getFlowDirectionDistribution(deviceCode, tourismName, startTime, endTime));
    }

    @GetMapping("/peak/hours")
    @Operation(summary = "获取客流高峰时段")
    public Result<List<Map<String, Object>>> getPeakHours(
            @Parameter(description = "设备编码") @RequestParam String deviceCode,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return Result.ok(flowAnalysisService.getPeakHours(deviceCode, startTime, endTime));
    }

    @GetMapping("/total")
    @Operation(summary = "获取总客流量")
    public Result<Integer> getTotalFlowCount(
            @Parameter(description = "设备编码") @RequestParam String deviceCode,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return Result.ok(flowAnalysisService.getTotalFlowCount(deviceCode, startTime, endTime));
    }

    @GetMapping("/peak/record")
    @Operation(summary = "获取客流量峰值记录")
    public Result<FlowAnalysis> getPeakFlowRecord(
            @Parameter(description = "设备编码") @RequestParam String deviceCode,
            @Parameter(description = "开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return Result.ok(flowAnalysisService.getPeakFlowRecord(deviceCode, startTime, endTime));
    }
} 