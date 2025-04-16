package com.scenic.ai.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.scenic.ai.common.core.domain.AjaxResult;
import com.scenic.ai.entity.Alert;
import com.scenic.ai.entity.AlertHandleRecord;
import com.scenic.ai.service.IAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 告警控制器
 * 处理告警相关的请求
 *
 * @author AI
 * @date 2024-03-20
 */
@RestController
@RequestMapping("/api/alert")
public class AlertController {

    private static final Logger logger = LoggerFactory.getLogger(AlertController.class);

    @Autowired
    private IAlertService alertService;

    /**
     * 获取告警详情
     *
     * @param id 告警ID
     * @return 告警详情
     */
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable Long id) {
        logger.debug("获取告警详情，ID: {}", id);
        Alert alert = alertService.getById(id);
        return AjaxResult.success(alert);
    }

    /**
     * 获取告警处理记录
     *
     * @param alertId 告警ID
     * @return 处理记录列表
     */
    @GetMapping("/{alertId}/records")
    public AjaxResult getHandleRecords(@PathVariable Long alertId) {
        logger.debug("获取告警处理记录，告警ID: {}", alertId);
        List<AlertHandleRecord> records = alertService.listHandleRecords(alertId);
        return AjaxResult.success(records);
    }

    /**
     * 处理告警
     *
     * @param alertId    告警ID
     * @param handleDesc 处理描述
     * @return 处理结果
     */
    @PostMapping("/{alertId}/handle")
    public AjaxResult handleAlert(@PathVariable Long alertId, @RequestParam String handleDesc) {
        logger.debug("处理告警，告警ID: {}, 处理描述: {}", alertId, handleDesc);
        boolean result = alertService.handleAlert(alertId, handleDesc);
        return result ? AjaxResult.success() : AjaxResult.error("处理告警失败");
    }

    /**
     * 分页查询告警信息
     *
     * @param pageNum     页码
     * @param pageSize    每页大小
     * @param deviceCode  设备编码
     * @param tourismName 景区名称
     * @param alertType   告警类型
     * @param alertLevel  告警级别
     * @param alertStatus 告警状态
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 告警列表
     */
    @GetMapping("/page")
    public AjaxResult page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) String alertType,
            @RequestParam(required = false) Integer alertLevel,
            @RequestParam(required = false) Integer alertStatus,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        logger.debug("分页查询告警信息，页码: {}, 每页大小: {}, 设备编码: {}, 景区名称: {}, 告警类型: {}, 告警级别: {}, 告警状态: {}, 开始时间: {}, 结束时间: {}",
                pageNum, pageSize, deviceCode, tourismName, alertType, alertLevel, alertStatus, startTime, endTime);

        LocalDateTime startDateTime = startTime != null
                ? startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                : null;
        LocalDateTime endDateTime = endTime != null
                ? endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                : null;

        IPage<Alert> pageResult = alertService.pageByConditions(pageNum, pageSize, tourismName, deviceCode,
                alertType, alertLevel, alertStatus, startDateTime, endDateTime);

        Map<String, Object> result = Map.of(
                "list", pageResult.getRecords(),
                "total", pageResult.getTotal());
        return AjaxResult.success(result);
    }

    /**
     * 获取告警统计信息
     *
     * @param tourismName 景区名称
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 统计信息
     */
    @GetMapping("/statistics")
    public AjaxResult getStatistics(
            @RequestParam(required = false) String tourismName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        logger.debug("获取告警统计信息，景区名称: {}, 开始时间: {}, 结束时间: {}", tourismName, startTime, endTime);
        Map<String, Object> statistics = alertService.getAlertStatistics(tourismName,
                startTime.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime(),
                endTime.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
        return AjaxResult.success(statistics);
    }

    /**
     * 获取告警类型列表
     *
     * @return 告警类型列表
     */
    @GetMapping("/types")
    public AjaxResult getAlertTypes() {
        logger.debug("获取告警类型列表");
        List<Map<String, Object>> types = alertService.countTypeDistribution(
                LocalDateTime.now().minusDays(30), LocalDateTime.now());
        return AjaxResult.success(types);
    }

    /**
     * 获取告警趋势
     *
     * @param tourismName 景区名称
     * @param deviceCode  设备编码
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 趋势数据
     */
    @GetMapping("/trend")
    public AjaxResult getTrend(
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        logger.debug("获取告警趋势，景区名称: {}, 设备编码: {}, 开始时间: {}, 结束时间: {}", tourismName, deviceCode, startTime, endTime);
        Map<String, Object> trend = alertService.getTrend(tourismName, deviceCode,
                startTime.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime(),
                endTime.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
        return AjaxResult.success(trend);
    }

    /**
     * 获取告警设备分布
     *
     * @param tourismName 景区名称
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 设备分布数据
     */
    @GetMapping("/device-distribution")
    public AjaxResult getDeviceDistribution(
            @RequestParam(required = false) String tourismName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        logger.debug("获取告警设备分布，景区名称: {}, 开始时间: {}, 结束时间: {}", tourismName, startTime, endTime);
        Map<String, Object> distribution = alertService.getDeviceDistribution(tourismName,
                startTime.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime(),
                endTime.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
        return AjaxResult.success(distribution);
    }

    /**
     * 获取告警景区分布
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 景区分布数据
     */
    @GetMapping("/tourism-distribution")
    public AjaxResult getTourismDistribution(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        logger.debug("获取告警景区分布，开始时间: {}, 结束时间: {}", startTime, endTime);
        Map<String, Object> distribution = alertService.getTourismDistribution(
                startTime.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime(),
                endTime.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
        return AjaxResult.success(distribution);
    }
}