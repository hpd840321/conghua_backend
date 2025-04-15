package com.scenic.ai.controller;

import com.scenic.ai.model.Alert;
import com.scenic.ai.service.IAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 告警控制器
 * 提供告警信息的查询、统计、处理等功能
 *
 * @author scenic
 * @date 2024-03-20
 */
@Controller
@RequestMapping("/alert")
public class AlertController {

    private static final Logger log = LoggerFactory.getLogger(AlertController.class);

    @Autowired
    private IAlertService alertService;

    /**
     * 告警列表页面
     */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAlertList(
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String alertType,
            @RequestParam(required = false) Integer alertLevel,
            @RequestParam(required = false) Integer alertStatus,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            log.info("查询告警列表, tourismName={}, deviceCode={}, alertType={}, alertLevel={}, status={}, startTime={}, endTime={}, pageNum={}, pageSize={}",
                    tourismName, deviceCode, alertType, alertLevel, alertStatus, startTime, endTime, pageNum, pageSize);
            
            Map<String, Object> result = alertService.getAlertList(tourismName, deviceCode, alertType,
                    alertLevel, alertStatus, startTime, endTime, pageNum, pageSize);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("查询告警列表失败", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 告警统计页面
     */
    @GetMapping("/statistics")
    public String statistics() {
        return "alert/statistics";
    }

    /**
     * 分页查询告警列表
     */
    @GetMapping("/page")
    public ResponseEntity<Page<Alert>> getAlertPage(
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String alertType,
            @RequestParam(required = false) Integer alertLevel,
            @RequestParam(required = false) Integer alertStatus,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            log.info("分页查询告警列表, tourismName={}, deviceCode={}, alertType={}, alertLevel={}, status={}, startTime={}, endTime={}, pageNum={}, pageSize={}",
                    tourismName, deviceCode, alertType, alertLevel, alertStatus, startTime, endTime, pageNum, pageSize);
            
            Page<Alert> page = new Page<>(pageNum, pageSize);
            Page<Alert> result = alertService.getAlertPage(page, tourismName, deviceCode,
                    alertType, alertLevel, alertStatus, startTime, endTime);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("分页查询告警列表失败", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 告警详情页面
     */
    @GetMapping("/detail")
    public String detail() {
        return "alert/detail";
    }

    /**
     * 获取告警详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<Alert> getAlertDetail(@PathVariable Long id) {
        log.info("获取告警详情, id={}", id);
        Alert alert = alertService.getById(id);
        return ResponseEntity.ok(alert);
    }

    /**
     * 处理告警
     */
    @PostMapping("/{id}/handle")
    public ResponseEntity<Void> handleAlert(@PathVariable Long id, @RequestParam String description) {
        try {
            alertService.handleAlert(id, description);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("处理告警失败", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 批量处理告警
     */
    @PostMapping("/batch-handle")
    public ResponseEntity<Void> batchHandleAlerts(@RequestBody List<Long> ids) {
        try {
            alertService.batchHandleAlerts(ids);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("批量处理告警失败", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 获取告警时段分布
     */
    @GetMapping("/distribution/time")
    public ResponseEntity<Map<String, Object>> getTimeDistribution(
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        try {
            Map<String, Object> distribution = alertService.getTimeDistribution(tourismName,
                    deviceCode, startTime, endTime);
            return ResponseEntity.ok(distribution);
        } catch (Exception e) {
            log.error("获取告警时段分布失败", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 获取告警类型分布
     */
    @GetMapping("/distribution/type")
    public ResponseEntity<Map<String, Object>> getTypeDistribution(
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        try {
            Map<String, Object> distribution = alertService.getTypeDistribution(tourismName,
                    deviceCode, startTime, endTime);
            return ResponseEntity.ok(distribution);
        } catch (Exception e) {
            log.error("获取告警类型分布失败", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 获取告警概览统计
     */
    @GetMapping("/overview")
    public ResponseEntity<Map<String, Object>> getOverview(
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        try {
            Map<String, Object> overview = alertService.getOverview(tourismName, deviceCode,
                    startTime, endTime);
            return ResponseEntity.ok(overview);
        } catch (Exception e) {
            log.error("获取告警概览统计失败", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 获取设备最新告警
     */
    @GetMapping("/latest/{deviceCode}")
    public ResponseEntity<Alert> getLatestByDevice(@PathVariable String deviceCode) {
        try {
            if (StringUtils.isBlank(deviceCode)) {
                return ResponseEntity.badRequest().body(null);
            }
            Alert alert = alertService.getLatestByDevice(deviceCode);
            if (alert == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(alert);
        } catch (Exception e) {
            log.error("获取设备最新告警失败", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 获取待处理告警数量
     *
     * @param tourismName 景区名称
     * @return 待处理告警数量
     */
    @GetMapping("/pending/count")
    public ResponseEntity<Integer> countPendingAlerts(@RequestParam(required = false) String tourismName) {
        try {
            int count = alertService.countUnhandledAlerts(null, tourismName);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            log.error("获取待处理告警数量失败", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 获取告警类型列表
     */
    @GetMapping("/types")
    public ResponseEntity<List<Map<String, Object>>> getAlertTypes() {
        try {
            List<Map<String, Object>> types = alertService.getAlertTypes();
            return ResponseEntity.ok(types);
        } catch (Exception e) {
            log.error("获取告警类型列表失败", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 创建告警
     *
     * @param alert 告警信息
     * @return 创建结果
     */
    @PostMapping
    public ResponseEntity<Alert> createAlert(@RequestBody Alert alert) {
        try {
            log.info("创建告警, alert={}", alert);
            boolean success = alertService.createAlert(alert);
            if (!success) {
                return ResponseEntity.badRequest().body(null);
            }
            return ResponseEntity.ok(alert);
        } catch (Exception e) {
            log.error("创建告警失败", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 更新告警状态
     *
     * @param id     告警ID
     * @param status 状态
     * @return 更新结果
     */
    @PostMapping("/{id}/status")
    public ResponseEntity<Boolean> updateAlertStatus(
            @PathVariable Long id,
            @RequestParam Integer status) {
        log.info("更新告警状态, id={}, status={}", id, status);
        boolean result = alertService.updateStatus(id, status);
        return ResponseEntity.ok(result);
    }

    /**
     * 删除告警
     *
     * @param id 告警ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Long id) {
        log.info("删除告警, id={}", id);
        alertService.removeById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 处理告警
     */
    @PostMapping("/process/{id}")
    public ResponseEntity<Boolean> processAlert(@PathVariable Long id) {
        try {
            boolean result = alertService.processAlert(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("处理告警失败", e);
            return ResponseEntity.badRequest().body(false);
        }
    }

    @GetMapping("/distribution/level")
    public ResponseEntity<Map<String, Object>> getLevelDistribution(
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        try {
            Map<String, Object> distribution = alertService.getLevelDistribution(tourismName,
                    deviceCode, startTime, endTime);
            return ResponseEntity.ok(distribution);
        } catch (Exception e) {
            log.error("获取告警级别分布失败", e);
            return ResponseEntity.badRequest().body(null);
        }
    }
} 