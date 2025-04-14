package com.scenic.ai.controller;

import com.scenic.ai.model.CrowdStatistics;
import com.scenic.ai.service.CrowdStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 人群统计控制器
 */
@Api(tags = "人群统计")
@RestController
@RequestMapping("/api/v1/crowd-statistics")
@RequiredArgsConstructor
public class CrowdStatisticsController {
    
    private final CrowdStatisticsService crowdStatisticsService;
    
    @ApiOperation("根据设备编码查询人群统计数据")
    @GetMapping("/by-device")
    public ResponseEntity<List<CrowdStatistics>> getByDeviceCode(
            @ApiParam("设备编码") @RequestParam String deviceCode,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        List<CrowdStatistics> data = crowdStatisticsService.getByDeviceCode(deviceCode, startTime, endTime);
        return ResponseEntity.ok(data);
    }
    
    @ApiOperation("根据景区名称查询人群统计数据")
    @GetMapping("/by-tourism")
    public ResponseEntity<List<CrowdStatistics>> getByTourismName(
            @ApiParam("景区名称") @RequestParam String tourismName,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        List<CrowdStatistics> data = crowdStatisticsService.getByTourismName(tourismName, startTime, endTime);
        return ResponseEntity.ok(data);
    }
    
    @ApiOperation("统计时段人群分布")
    @GetMapping("/stats/hourly")
    public ResponseEntity<List<Map<String, Object>>> getHourlyDistribution(
            @ApiParam("设备编码") @RequestParam(required = false) String deviceCode,
            @ApiParam("景区名称") @RequestParam(required = false) String tourismName,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        List<Map<String, Object>> stats = crowdStatisticsService.getHourlyDistribution(deviceCode, tourismName, startTime, endTime);
        return ResponseEntity.ok(stats);
    }
    
    @ApiOperation("统计密度分布")
    @GetMapping("/stats/density")
    public ResponseEntity<List<Map<String, Object>>> getDensityDistribution(
            @ApiParam("设备编码") @RequestParam(required = false) String deviceCode,
            @ApiParam("景区名称") @RequestParam(required = false) String tourismName,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        List<Map<String, Object>> stats = crowdStatisticsService.getDensityDistribution(deviceCode, tourismName, startTime, endTime);
        return ResponseEntity.ok(stats);
    }
    
    @ApiOperation("获取人群趋势数据")
    @GetMapping("/trend")
    public ResponseEntity<List<Map<String, Object>>> getCrowdTrend(
            @ApiParam("设备编码") @RequestParam(required = false) String deviceCode,
            @ApiParam("景区名称") @RequestParam(required = false) String tourismName,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        List<Map<String, Object>> trend = crowdStatisticsService.getCrowdTrend(deviceCode, tourismName, startTime, endTime);
        return ResponseEntity.ok(trend);
    }
    
    @ApiOperation("条件分页查询")
    @GetMapping
    public ResponseEntity<Map<String, Object>> findByConditions(
            @ApiParam("设备编码") @RequestParam(required = false) String deviceCode,
            @ApiParam("设备名称") @RequestParam(required = false) String deviceName,
            @ApiParam("景区名称") @RequestParam(required = false) String tourismName,
            @ApiParam("开始时间") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @ApiParam("页码") @RequestParam(defaultValue = "1") int pageNum,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") int pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("deviceCode", deviceCode);
        params.put("deviceName", deviceName);
        params.put("tourismName", tourismName);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("offset", (pageNum - 1) * pageSize);
        params.put("limit", pageSize);
        
        List<CrowdStatistics> records = crowdStatisticsService.findByConditions(params);
        Long total = crowdStatisticsService.countRecords(params);
        
        Map<String, Object> result = new HashMap<>();
        result.put("records", records);
        result.put("total", total);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        
        return ResponseEntity.ok(result);
    }
    
    @ApiOperation("获取最新的人群统计数据")
    @GetMapping("/latest")
    public ResponseEntity<CrowdStatistics> getLatestByDeviceCode(
            @ApiParam("设备编码") @RequestParam String deviceCode) {
        CrowdStatistics data = crowdStatisticsService.getLatestByDeviceCode(deviceCode);
        return ResponseEntity.ok(data);
    }
    
    @ApiOperation("获取高密度区域统计")
    @GetMapping("/stats/high-density")
    public ResponseEntity<List<Map<String, Object>>> getHighDensityStats(
            @ApiParam("设备编码") @RequestParam(required = false) String deviceCode,
            @ApiParam("景区名称") @RequestParam(required = false) String tourismName,
            @ApiParam("密度阈值") @RequestParam(defaultValue = "0.6") Double densityThreshold,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        List<Map<String, Object>> stats = crowdStatisticsService.getHighDensityStats(deviceCode, tourismName, densityThreshold, startTime, endTime);
        return ResponseEntity.ok(stats);
    }
} 