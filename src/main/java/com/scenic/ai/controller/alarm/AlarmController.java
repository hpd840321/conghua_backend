package com.scenic.ai.controller.alarm;

import com.scenic.ai.common.PageResult;
import com.scenic.ai.common.Result;
import com.scenic.ai.domain.model.alarm.*;
import com.scenic.ai.service.alarm.AlarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 告警管理控制器
 * 提供告警相关的RESTful API接口
 *
 * @author conghua
 * @version 1.0.0
 * @since 2024.01.20
 */
@Slf4j
@Tag(name = "告警管理接口")
@RestController
@RequestMapping("/api/v1/alarms")
@Validated
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    /**
     * 获取告警列表
     * 支持分页查询和多条件过滤
     *
     * @param queryParams 查询参数，包含分页信息和过滤条件
     * @return 告警列表分页数据
     */
    @Operation(summary = "获取告警列表", description = "支持按类型、级别、状态等条件筛选")
    @Parameters({
        @Parameter(name = "type", description = "告警类型"),
        @Parameter(name = "level", description = "告警级别"),
        @Parameter(name = "status", description = "告警状态"),
        @Parameter(name = "page", description = "页码", example = "1"),
        @Parameter(name = "pageSize", description = "每页条数", example = "10")
    })
    @GetMapping
    public Result<PageResult<AlarmData>> getAlarmList(@Valid AlarmQueryParams queryParams) {
        log.info("查询告警列表，参数：{}", queryParams);
        return Result.success(alarmService.getAlarmList(queryParams));
    }

    /**
     * 获取告警统计信息
     * 包括各类型、级别的告警数量统计
     *
     * @return 告警统计数据
     */
    @Operation(summary = "获取告警统计信息", description = "返回告警数量统计和分布情况")
    @GetMapping("/stats")
    public Result<AlarmStats> getAlarmStats() {
        log.info("获取告警统计信息");
        return Result.success(alarmService.getAlarmStats());
    }

    /**
     * 获取告警配置列表
     *
     * @return 告警配置列表
     */
    @Operation(summary = "获取告警配置列表", description = "返回所有告警规则配置")
    @GetMapping("/configs")
    public Result<List<AlarmConfigResponse>> getAlarmConfigs() {
        log.info("获取告警配置列表");
        return Result.success(alarmService.getAlarmConfigs());
    }

    /**
     * 更新告警配置
     *
     * @param id 配置ID
     * @param config 告警配置信息
     * @return 操作结果
     */
    @Operation(summary = "更新告警配置", description = "更新指定ID的告警规则配置")
    @PutMapping("/configs/{id}")
    public Result<Void> updateAlarmConfig(
            @Parameter(description = "配置ID", required = true) @PathVariable("id") String id,
            @Parameter(description = "告警配置", required = true) @Valid @RequestBody AlarmConfig config) {
        log.info("更新告警配置，ID：{}，配置：{}", id, config);
        alarmService.updateAlarmConfig(id, config);
        return Result.success();
    }

    /**
     * 处理告警
     *
     * @param id 告警ID
     * @param params 处理参数
     * @return 操作结果
     */
    @Operation(summary = "处理告警", description = "处理指定ID的告警")
    @PostMapping("/{id}/handle")
    public Result<Void> handleAlarm(
            @Parameter(description = "告警ID", required = true) @PathVariable("id") String id,
            @Parameter(description = "处理参数", required = true) @Valid @RequestBody AlarmHandleParams params) {
        log.info("处理告警，ID：{}，参数：{}", id, params);
        alarmService.handleAlarm(id, params);
        return Result.success();
    }

    /**
     * 批量处理告警
     *
     * @param params 批量处理参数列表
     * @return 批量处理结果
     */
    @Operation(summary = "批量处理告警", description = "批量处理多个告警")
    @PostMapping("/batch-handle")
    public Result<BatchOperationResult> batchHandleAlarms(
            @Parameter(description = "批量处理参数", required = true) 
            @Valid @RequestBody List<AlarmHandleParams> params) {
        log.info("批量处理告警，参数：{}", params);
        return Result.success(alarmService.batchHandleAlarms(params));
    }

    /**
     * 删除告警
     *
     * @param id 告警ID
     * @return 操作结果
     */
    @Operation(summary = "删除告警", description = "删除指定ID的告警")
    @DeleteMapping("/{id}")
    public Result<Void> deleteAlarm(
            @Parameter(description = "告警ID", required = true) @PathVariable("id") String id) {
        log.info("删除告警，ID：{}", id);
        alarmService.deleteAlarm(id);
        return Result.success();
    }

    /**
     * 批量删除告警
     *
     * @param ids 告警ID列表
     * @return 批量删除结果
     */
    @Operation(summary = "批量删除告警", description = "批量删除多个告警")
    @DeleteMapping("/batch")
    public Result<BatchOperationResult> batchDeleteAlarms(
            @Parameter(description = "告警ID列表", required = true) @RequestBody List<String> ids) {
        log.info("批量删除告警，ID列表：{}", ids);
        return Result.success(alarmService.batchDeleteAlarms(ids));
    }

    /**
     * 获取实时告警数据
     *
     * @param areaId 区域ID（可选）
     * @return 实时告警数据列表
     */
    @Operation(summary = "获取实时告警数据", description = "获取实时告警信息，支持按区域筛选")
    @GetMapping("/realtime")
    public Result<List<AlarmData>> getRealtimeAlarms(
            @Parameter(description = "区域ID") @RequestParam(required = false) String areaId) {
        log.info("获取实时告警数据，区域ID：{}", areaId);
        return Result.success(alarmService.getRealtimeAlarms(areaId));
    }

    /**
     * 获取告警趋势数据
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param interval 时间间隔（可选）
     * @return 告警趋势数据列表
     */
    @Operation(summary = "获取告警趋势数据", description = "获取指定时间范围内的告警趋势数据")
    @GetMapping("/trends")
    public Result<List<AlarmTrendData>> getAlarmTrends(
            @Parameter(description = "开始时间", required = true) @RequestParam String startTime,
            @Parameter(description = "结束时间", required = true) @RequestParam String endTime,
            @Parameter(description = "时间间隔") @RequestParam(required = false) String interval) {
        log.info("获取告警趋势数据，开始时间：{}，结束时间：{}，间隔：{}", startTime, endTime, interval);
        return Result.success(alarmService.getAlarmTrends(startTime, endTime, interval));
    }
} 