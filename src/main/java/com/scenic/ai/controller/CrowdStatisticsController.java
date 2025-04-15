package com.scenic.ai.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.model.CrowdStatistics;
import com.scenic.ai.service.ICrowdStatisticsService;
import com.scenic.ai.exception.BusinessException;
import com.scenic.ai.exception.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 人群统计控制器
 * 提供人群密度统计、分布分析、趋势分析等接口
 *
 * @author scenic
 * @date 2024-03-19
 */
@Controller
@Validated
@RequestMapping("/api/v1/crowd-statistics")
public class CrowdStatisticsController {

    private static final Logger log = LoggerFactory.getLogger(CrowdStatisticsController.class);

    @Autowired
    private ICrowdStatisticsService crowdStatisticsService;

    /**
     * 人群统计页面
     */
    @GetMapping("/page")
    public String page(Model model) {
        return "crowd/statistics";
    }

    /**
     * 分页查询人群统计数据
     */
    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<IPage<CrowdStatistics>> getPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("分页查询人群统计数据: pageNum={}, pageSize={}, tourismName={}, deviceCode={}, startTime={}, endTime={}",
                pageNum, pageSize, tourismName, deviceCode, startTime, endTime);
        
        return ResponseEntity.ok(crowdStatisticsService.getPage(pageNum, pageSize, tourismName, deviceCode, startTime, endTime));
    }

    /**
     * 根据设备编码查询统计数据
     */
    @GetMapping("/by-device/{deviceCode}")
    @ResponseBody
    public ResponseEntity<List<CrowdStatistics>> getByDevice(
            @PathVariable String deviceCode,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("根据设备编码查询统计数据: deviceCode={}, startTime={}, endTime={}", deviceCode, startTime, endTime);
        return ResponseEntity.ok(crowdStatisticsService.getByDevice(deviceCode, startTime, endTime));
    }

    /**
     * 根据景区名称查询统计数据
     */
    @GetMapping("/by-tourism/{tourismName}")
    @ResponseBody
    public ResponseEntity<List<CrowdStatistics>> getByTourism(
            @PathVariable String tourismName,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("根据景区名称查询统计数据: tourismName={}, startTime={}, endTime={}", tourismName, startTime, endTime);
        return ResponseEntity.ok(crowdStatisticsService.getByTourism(tourismName, startTime, endTime));
    }

    /**
     * 获取时段人群分布
     */
    @GetMapping("/distribution/hour")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getHourDistribution(
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String tourismName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取时段人群分布: deviceCode={}, tourismName={}, startTime={}, endTime={}",
                deviceCode, tourismName, startTime, endTime);
        return ResponseEntity.ok(crowdStatisticsService.getHourDistribution(deviceCode, tourismName, startTime, endTime));
    }

    /**
     * 获取密度分布
     */
    @GetMapping("/distribution/density")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getDensityDistribution(
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String tourismName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取密度分布: deviceCode={}, tourismName={}, startTime={}, endTime={}",
                deviceCode, tourismName, startTime, endTime);
        return ResponseEntity.ok(crowdStatisticsService.getDensityDistribution(deviceCode, tourismName, startTime, endTime));
    }

    /**
     * 获取人群趋势
     */
    @GetMapping("/trend")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getTrend(
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String tourismName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取人群趋势: deviceCode={}, tourismName={}, startTime={}, endTime={}",
                deviceCode, tourismName, startTime, endTime);
        return ResponseEntity.ok(crowdStatisticsService.getTrend(deviceCode, tourismName, startTime, endTime));
    }

    /**
     * 获取统计概览
     */
    @GetMapping("/overview")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getOverview(
            @RequestParam(required = false) String tourismName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取统计概览: tourismName={}, startTime={}, endTime={}", tourismName, startTime, endTime);
        return ResponseEntity.ok(crowdStatisticsService.getOverview(tourismName, startTime, endTime));
    }

    /**
     * 获取设备最新人群统计数据
     */
    @GetMapping("/latest/{deviceCode}")
    @ResponseBody
    public ResponseEntity<CrowdStatistics> getLatest(@PathVariable String deviceCode) {
        log.info("获取设备最新人群统计数据: deviceCode={}", deviceCode);
        return ResponseEntity.ok(crowdStatisticsService.getLatest(deviceCode));
    }

    /**
     * 获取高密度区域统计
     */
    @GetMapping("/high-density")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getHighDensity(
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String tourismName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @RequestParam(defaultValue = "0.7") BigDecimal densityThreshold) {
        log.info("获取高密度区域统计: deviceCode={}, tourismName={}, startTime={}, endTime={}, densityThreshold={}",
                deviceCode, tourismName, startTime, endTime, densityThreshold);
        return ResponseEntity.ok(crowdStatisticsService.getHighDensity(deviceCode, tourismName, startTime, endTime, densityThreshold.doubleValue()));
    }
} 