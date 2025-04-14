package com.scenic.ai.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.common.Result;
import com.scenic.ai.model.Alert;
import com.scenic.ai.model.AlertHandlingRecord;
import com.scenic.ai.service.AlertService;
import com.scenic.ai.service.AlertHandlingRecordService;
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
import java.util.HashMap;

/**
 * 告警中心控制器
 */
@Api(tags = "告警中心")
@RestController
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    private final AlertHandlingRecordService alertHandlingRecordService;

    @ApiOperation("创建告警信息")
    @PostMapping
    public ResponseEntity<Boolean> createAlert(@RequestBody Alert alert) {
        return ResponseEntity.ok(alertService.createAlert(alert));
    }

    @ApiOperation("更新告警状态")
    @PutMapping("/{id}/status")
    public ResponseEntity<Boolean> updateAlertStatus(
            @ApiParam("告警ID") @PathVariable Long id,
            @ApiParam("告警状态") @RequestParam Integer status) {
        return ResponseEntity.ok(alertService.updateStatus(id, status));
    }

    @ApiOperation("批量更新告警状态")
    @PutMapping("/batch/status")
    public ResponseEntity<Boolean> batchUpdateAlertStatus(
            @ApiParam("告警ID列表") @RequestParam List<Long> ids,
            @ApiParam("告警状态") @RequestParam Integer status) {
        return ResponseEntity.ok(alertService.batchUpdateStatus(ids, status));
    }

    @ApiOperation("根据设备编码查询告警信息")
    @GetMapping("/by-device")
    public ResponseEntity<List<Alert>> getAlertsByDevice(
            @ApiParam("设备编码") @RequestParam String deviceCode,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return ResponseEntity.ok(alertService.getAlertsByDevice(deviceCode, startTime, endTime));
    }

    @ApiOperation("根据景区名称查询告警信息")
    @GetMapping("/by-tourism")
    public ResponseEntity<List<Alert>> getAlertsByTourism(
            @ApiParam("景区名称") @RequestParam String tourismName,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return ResponseEntity.ok(alertService.getAlertsByTourism(tourismName, startTime, endTime));
    }

    @ApiOperation("统计告警时段分布")
    @GetMapping("/stats/hour-distribution")
    public ResponseEntity<List<Map<String, Object>>> getAlertHourDistribution(
            @ApiParam("设备编码") @RequestParam(required = false) String deviceCode,
            @ApiParam("景区名称") @RequestParam(required = false) String tourismName,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return ResponseEntity.ok(alertService.getAlertHourDistribution(deviceCode, tourismName, startTime, endTime));
    }

    @ApiOperation("统计告警类型分布")
    @GetMapping("/stats/type-distribution")
    public ResponseEntity<List<Map<String, Object>>> getAlertTypeDistribution(
            @ApiParam("设备编码") @RequestParam(required = false) String deviceCode,
            @ApiParam("景区名称") @RequestParam(required = false) String tourismName,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return ResponseEntity.ok(alertService.getAlertTypeDistribution(deviceCode, tourismName, startTime, endTime));
    }

    @ApiOperation("统计告警级别分布")
    @GetMapping("/stats/level-distribution")
    public ResponseEntity<List<Map<String, Object>>> getAlertLevelDistribution(
            @ApiParam("设备编码") @RequestParam(required = false) String deviceCode,
            @ApiParam("景区名称") @RequestParam(required = false) String tourismName,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return ResponseEntity.ok(alertService.getAlertLevelDistribution(deviceCode, tourismName, startTime, endTime));
    }

    @ApiOperation("分页查询告警信息")
    @GetMapping
    public Result<IPage<Alert>> page(
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @ApiParam("景区名称") @RequestParam(required = false) String tourismName,
            @ApiParam("设备编码") @RequestParam(required = false) String deviceCode,
            @ApiParam("告警类型") @RequestParam(required = false) String alertType,
            @ApiParam("告警级别") @RequestParam(required = false) Integer alertLevel,
            @ApiParam("告警状态") @RequestParam(required = false) Integer alertStatus,
            @ApiParam("开始时间") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        
        IPage<Alert> page = alertService.page(pageNum, pageSize, tourismName, deviceCode,
                alertType, alertLevel, alertStatus, startTime, endTime);
        return Result.success(page);
    }

    @ApiOperation("获取未处理告警数量")
    @GetMapping("/count/unhandled")
    public ResponseEntity<Long> getUnhandledAlertCount(
            @ApiParam("设备编码") @RequestParam(required = false) String deviceCode,
            @ApiParam("景区名称") @RequestParam(required = false) String tourismName) {
        return ResponseEntity.ok(alertService.countUnhandledAlerts(deviceCode, tourismName));
    }

    /**
     * 获取告警类型分布
     */
    @GetMapping("/type/distribution")
    public Result<?> getAlertTypeDistribution(
            @RequestParam(required = false) String tourismName,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime endTime) {
        return Result.ok(alertService.getAlertTypeDistribution(tourismName, startTime, endTime));
    }

    /**
     * 获取告警级别分布
     */
    @GetMapping("/level/distribution")
    public Result<?> getAlertLevelDistribution(
            @RequestParam(required = false) String tourismName,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime endTime) {
        return Result.ok(alertService.getAlertLevelDistribution(tourismName, startTime, endTime));
    }

    /**
     * 获取告警时段分布
     */
    @GetMapping("/time/distribution")
    public Result<?> getAlertTimeDistribution(
            @RequestParam(required = false) String tourismName,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime endTime) {
        return Result.ok(alertService.getAlertTimeDistribution(tourismName, startTime, endTime));
    }

    /**
     * 获取告警统计概览
     */
    @GetMapping("/statistics")
    public Result<?> getAlertStatistics(
            @RequestParam(required = false) String tourismName,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime endTime) {
        return Result.ok(alertService.getAlertStatistics(tourismName, startTime, endTime));
    }

    /**
     * 处理单个告警
     */
    @PostMapping("/handle/{id}")
    public Result<?> handleAlert(@PathVariable Long id) {
        return alertService.handleAlert(id) ? Result.ok() : Result.error("处理失败");
    }

    /**
     * 批量处理告警
     */
    @PostMapping("/handle/batch")
    public Result<?> batchHandleAlerts(@RequestBody List<Long> ids) {
        return alertService.batchHandleAlerts(ids) ? Result.ok() : Result.error("批量处理失败");
    }

    /**
     * 获取待处理告警数量
     */
    @GetMapping("/pending/count")
    public Result<?> countPendingAlerts(@RequestParam(required = false) String tourismName) {
        return Result.ok(alertService.countPendingAlerts(tourismName));
    }

    /**
     * 获取告警详情
     */
    @GetMapping("/{id}")
    public Result<?> getAlertDetail(@PathVariable Long id) {
        Alert alert = alertService.getById(id);
        if (alert == null) {
            return Result.error("告警不存在");
        }
        return Result.ok(alert);
    }

    /**
     * 获取告警处理记录
     */
    @GetMapping("/{id}/records")
    public Result<?> getAlertRecords(@PathVariable Long id) {
        List<AlertHandlingRecord> records = alertHandlingRecordService.getHandlingRecordsByAlertId(id);
        return Result.ok(records);
    }

    @ApiOperation("获取告警时段分布")
    @GetMapping("/distribution/hour")
    public Result<List<Map<String, Object>>> getHourDistribution(
            @ApiParam("景区名称") @RequestParam(required = false) String tourismName,
            @ApiParam("设备编码") @RequestParam(required = false) String deviceCode,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        
        List<Map<String, Object>> distribution = alertService.getHourDistribution(tourismName, deviceCode, startTime, endTime);
        return Result.success(distribution);
    }
    
    @ApiOperation("获取告警类型分布")
    @GetMapping("/distribution/type")
    public Result<List<Map<String, Object>>> getTypeDistribution(
            @ApiParam("景区名称") @RequestParam(required = false) String tourismName,
            @ApiParam("设备编码") @RequestParam(required = false) String deviceCode,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        
        List<Map<String, Object>> distribution = alertService.getTypeDistribution(tourismName, deviceCode, startTime, endTime);
        return Result.success(distribution);
    }
    
    @ApiOperation("获取告警概览统计")
    @GetMapping("/overview")
    public Result<Map<String, Object>> getOverview(
            @ApiParam("景区名称") @RequestParam(required = false) String tourismName,
            @ApiParam("设备编码") @RequestParam(required = false) String deviceCode,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        
        Map<String, Object> overview = alertService.getOverview(tourismName, deviceCode, startTime, endTime);
        return Result.success(overview);
    }
    
    @ApiOperation("处理告警")
    @PostMapping("/{alertId}/handle")
    public Result<Void> handleAlert(
            @ApiParam("告警ID") @PathVariable Long alertId,
            @ApiParam("处理说明") @RequestParam String description) {
        
        alertService.handleAlert(alertId, description);
        return Result.success();
    }
} 