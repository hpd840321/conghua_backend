package com.scenic.ai.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.common.Result;
import com.scenic.ai.model.Alert;
import com.scenic.ai.model.AlertHandlingRecord;
import com.scenic.ai.service.AlertService;
import com.scenic.ai.service.AlertHandlingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 告警管理控制器
 */
@RestController
@RequestMapping("/api/alert")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @Autowired
    private AlertHandlingRecordService alertHandlingRecordService;

    /**
     * 分页查询告警信息
     */
    @GetMapping("/page")
    public Result<?> pageAlerts(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String alertType,
            @RequestParam(required = false) Integer alertLevel,
            @RequestParam(required = false) Integer alertStatus,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime endTime) {
        
        Page<Alert> page = new Page<>(pageNo, pageSize);
        return Result.ok(alertService.pageAlerts(page, tourismName, deviceCode, alertType, 
                alertLevel, alertStatus, startTime, endTime));
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
} 