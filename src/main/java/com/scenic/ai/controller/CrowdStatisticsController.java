package com.scenic.ai.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.model.CrowdStatistics;
import com.scenic.ai.service.CrowdStatisticsService;
import com.scenic.ai.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/crowd-statistics")
@Api(tags = "人群统计接口")
public class CrowdStatisticsController {

    private final CrowdStatisticsService crowdStatisticsService;

    /**
     * 分页查询人群统计数据
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param tourismName 景区名称
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页数据
     */
    @GetMapping
    @ApiOperation("分页查询人群统计数据")
    public ResponseEntity<Result<IPage<CrowdStatistics>>> page(
            @RequestParam(defaultValue = "1") @Min(1) Integer pageNum,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer pageSize,
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("分页查询人群统计数据: pageNum={}, pageSize={}, tourismName={}, deviceCode={}, startTime={}, endTime={}",
                pageNum, pageSize, tourismName, deviceCode, startTime, endTime);
        
        Page<CrowdStatistics> page = new Page<>(pageNum, pageSize);
        IPage<CrowdStatistics> result = crowdStatisticsService.page(page, tourismName, deviceCode, startTime, endTime);
        return ResponseEntity.ok(Result.success(result));
    }

    /**
     * 根据设备编码查询统计数据
     *
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计数据列表
     */
    @GetMapping("/by-device/{deviceCode}")
    @ApiOperation("根据设备编码查询统计数据")
    public ResponseEntity<Result<List<CrowdStatistics>>> getByDevice(
            @PathVariable @NotBlank String deviceCode,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("根据设备编码查询统计数据: deviceCode={}, startTime={}, endTime={}", deviceCode, startTime, endTime);
        return ResponseEntity.ok(Result.success(crowdStatisticsService.getByDevice(deviceCode, startTime, endTime)));
    }

    /**
     * 根据景区名称查询统计数据
     *
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计数据列表
     */
    @GetMapping("/by-tourism/{tourismName}")
    @ApiOperation("根据景区名称查询统计数据")
    public ResponseEntity<Result<List<CrowdStatistics>>> getByTourism(
            @PathVariable @NotBlank String tourismName,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("根据景区名称查询统计数据: tourismName={}, startTime={}, endTime={}", tourismName, startTime, endTime);
        return ResponseEntity.ok(Result.success(crowdStatisticsService.getByTourism(tourismName, startTime, endTime)));
    }

    /**
     * 获取时段人群分布
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 时段分布数据
     */
    @GetMapping("/distribution/hour")
    @ApiOperation("获取时段人群分布")
    public ResponseEntity<Result<List<Map<String, Object>>>> getHourDistribution(
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String tourismName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取时段人群分布: deviceCode={}, tourismName={}, startTime={}, endTime={}",
                deviceCode, tourismName, startTime, endTime);
        return ResponseEntity.ok(Result.success(
                crowdStatisticsService.getHourDistribution(deviceCode, tourismName, startTime, endTime)));
    }

    /**
     * 获取密度分布
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 密度分布数据
     */
    @GetMapping("/distribution/density")
    @ApiOperation("获取密度分布")
    public ResponseEntity<Result<List<Map<String, Object>>>> getDensityDistribution(
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String tourismName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取密度分布: deviceCode={}, tourismName={}, startTime={}, endTime={}",
                deviceCode, tourismName, startTime, endTime);
        return ResponseEntity.ok(Result.success(
                crowdStatisticsService.getDensityDistribution(deviceCode, tourismName, startTime, endTime)));
    }

    /**
     * 获取人群趋势
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 趋势数据
     */
    @GetMapping("/trend")
    @ApiOperation("获取人群趋势")
    public ResponseEntity<Result<List<Map<String, Object>>>> getTrend(
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String tourismName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取人群趋势: deviceCode={}, tourismName={}, startTime={}, endTime={}",
                deviceCode, tourismName, startTime, endTime);
        return ResponseEntity.ok(Result.success(
                crowdStatisticsService.getTrend(deviceCode, tourismName, startTime, endTime)));
    }

    /**
     * 获取统计概览
     *
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 概览数据
     */
    @GetMapping("/overview")
    @ApiOperation("获取统计概览")
    public ResponseEntity<Result<Map<String, Object>>> getOverview(
            @RequestParam @NotBlank String tourismName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        log.info("获取统计概览: tourismName={}, startTime={}, endTime={}", tourismName, startTime, endTime);
        return ResponseEntity.ok(Result.success(crowdStatisticsService.getOverview(tourismName, startTime, endTime)));
    }

    /**
     * 获取设备最新人群统计数据
     *
     * @param deviceCode 设备编码
     * @return 最新统计数据
     */
    @GetMapping("/latest/{deviceCode}")
    @ApiOperation("获取设备最新人群统计数据")
    public ResponseEntity<Result<CrowdStatistics>> getLatest(@PathVariable @NotBlank String deviceCode) {
        log.info("获取设备最新人群统计数据: deviceCode={}", deviceCode);
        return ResponseEntity.ok(Result.success(crowdStatisticsService.getLatest(deviceCode)));
    }

    /**
     * 获取高密度区域统计
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param threshold 密度阈值
     * @return 高密度区域统计数据
     */
    @GetMapping("/high-density")
    @ApiOperation("获取高密度区域统计")
    public ResponseEntity<Result<List<Map<String, Object>>>> getHighDensityAreas(
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String tourismName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @RequestParam(defaultValue = "0.7") BigDecimal threshold) {
        log.info("获取高密度区域统计: deviceCode={}, tourismName={}, startTime={}, endTime={}, threshold={}",
                deviceCode, tourismName, startTime, endTime, threshold);
        return ResponseEntity.ok(Result.success(
                crowdStatisticsService.getHighDensityAreas(deviceCode, tourismName, startTime, endTime, threshold)));
    }
} 