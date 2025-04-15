package com.scenic.ai.controller;

import com.scenic.ai.service.IAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 告警统计控制器
 *
 * @author AI
 * @date 2023-05-20
 */
@RestController
@RequestMapping("/api/v1/alert/statistics")
public class AlertStatisticsController {

    private static final Logger log = LoggerFactory.getLogger(AlertStatisticsController.class);

    @Autowired
    private IAlertService alertService;

    /**
     * 获取告警统计概览
     *
     * @param tourismName 景区名称
     * @param deviceCode  设备编码
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 统计概览数据
     */
    @GetMapping("/overview")
    public ResponseEntity<Map<String, Object>> getOverview(
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取告警统计概览, tourismName={}, deviceCode={}, startTime={}, endTime={}", 
                tourismName, deviceCode, startTime, endTime);
        
        Map<String, Object> overview = alertService.getOverview(tourismName, deviceCode, startTime, endTime);
        return ResponseEntity.ok(overview);
    }

    /**
     * 获取告警时段分布
     *
     * @param tourismName 景区名称
     * @param deviceCode  设备编码
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 时段分布数据
     */
    @GetMapping("/time-distribution")
    public ResponseEntity<Map<String, Object>> getTimeDistribution(
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取告警时段分布, tourismName={}, deviceCode={}, startTime={}, endTime={}", 
                tourismName, deviceCode, startTime, endTime);
        
        Map<String, Object> timeDistribution = alertService.getTimeDistribution(tourismName, deviceCode, startTime, endTime);
        return ResponseEntity.ok(timeDistribution);
    }

    /**
     * 获取告警类型分布
     *
     * @param tourismName 景区名称
     * @param deviceCode  设备编码
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 类型分布数据
     */
    @GetMapping("/type-distribution")
    public ResponseEntity<Map<String, Object>> getTypeDistribution(
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取告警类型分布, tourismName={}, deviceCode={}, startTime={}, endTime={}", 
                tourismName, deviceCode, startTime, endTime);
        
        Map<String, Object> typeDistribution = alertService.getTypeDistribution(tourismName, deviceCode, startTime, endTime);
        return ResponseEntity.ok(typeDistribution);
    }

    /**
     * 获取告警级别分布
     *
     * @param tourismName 景区名称
     * @param deviceCode  设备编码
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 级别分布数据
     */
    @GetMapping("/level-distribution")
    public ResponseEntity<Map<String, Object>> getLevelDistribution(
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取告警级别分布, tourismName={}, deviceCode={}, startTime={}, endTime={}", 
                tourismName, deviceCode, startTime, endTime);
        
        Map<String, Object> levelDistribution = alertService.getLevelDistribution(tourismName, deviceCode, startTime, endTime);
        return ResponseEntity.ok(levelDistribution);
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
    public ResponseEntity<Map<String, Object>> getTrend(
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取告警趋势, tourismName={}, deviceCode={}, startTime={}, endTime={}", 
                tourismName, deviceCode, startTime, endTime);
        
        Map<String, Object> trend = alertService.getTrend(tourismName, deviceCode, startTime, endTime);
        return ResponseEntity.ok(trend);
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
    public ResponseEntity<Map<String, Object>> getDeviceDistribution(
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取告警设备分布, tourismName={}, startTime={}, endTime={}", 
                tourismName, startTime, endTime);
        
        Map<String, Object> deviceDistribution = alertService.getDeviceDistribution(tourismName, startTime, endTime);
        return ResponseEntity.ok(deviceDistribution);
    }

    /**
     * 获取告警景区分布
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 景区分布数据
     */
    @GetMapping("/tourism-distribution")
    public ResponseEntity<Map<String, Object>> getTourismDistribution(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取告警景区分布, startTime={}, endTime={}", startTime, endTime);
        
        Map<String, Object> tourismDistribution = alertService.getTourismDistribution(startTime, endTime);
        return ResponseEntity.ok(tourismDistribution);
    }

    /**
     * 获取所有统计数据
     *
     * @param tourismName 景区名称
     * @param deviceCode  设备编码
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 所有统计数据
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllStatistics(
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取所有统计数据, tourismName={}, deviceCode={}, startTime={}, endTime={}", 
                tourismName, deviceCode, startTime, endTime);
        
        Map<String, Object> result = new HashMap<>();
        
        // 获取概览数据
        Map<String, Object> overview = alertService.getOverview(tourismName, deviceCode, startTime, endTime);
        result.put("overview", overview);
        
        // 获取时段分布
        Map<String, Object> timeDistribution = alertService.getTimeDistribution(tourismName, deviceCode, startTime, endTime);
        result.put("timeDistribution", timeDistribution);
        
        // 获取类型分布
        Map<String, Object> typeDistribution = alertService.getTypeDistribution(tourismName, deviceCode, startTime, endTime);
        result.put("typeDistribution", typeDistribution);
        
        // 获取级别分布
        Map<String, Object> levelDistribution = alertService.getLevelDistribution(tourismName, deviceCode, startTime, endTime);
        result.put("levelDistribution", levelDistribution);
        
        // 获取趋势数据
        Map<String, Object> trend = alertService.getTrend(tourismName, deviceCode, startTime, endTime);
        result.put("trend", trend);
        
        // 获取设备分布
        Map<String, Object> deviceDistribution = alertService.getDeviceDistribution(tourismName, startTime, endTime);
        result.put("deviceDistribution", deviceDistribution);
        
        // 获取景区分布
        Map<String, Object> tourismDistribution = alertService.getTourismDistribution(startTime, endTime);
        result.put("tourismDistribution", tourismDistribution);
        
        return ResponseEntity.ok(result);
    }
} 