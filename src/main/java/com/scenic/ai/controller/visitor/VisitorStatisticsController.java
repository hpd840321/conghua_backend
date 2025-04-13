package com.conghua.tourism.controller.visitor;

import com.conghua.tourism.model.visitor.*;
import com.conghua.tourism.service.visitor.VisitorStatisticsService;
import com.conghua.tourism.common.Result;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 访客统计控制器
 * 提供访客统计相关的RESTful API接口
 *
 * @author conghua
 * @version 1.0.0
 * @since 2024.01.20
 */
@Slf4j
@Api(tags = "访客统计接口")
@RestController
@RequestMapping("/api/v1/visitor-statistics")
@Validated
public class VisitorStatisticsController {

    @Autowired
    private VisitorStatisticsService visitorStatisticsService;

    /**
     * 获取实时人数统计
     *
     * @param areaId 区域ID
     * @return 实时人数统计数据
     */
    @ApiOperation(value = "获取实时人数统计", notes = "获取指定区域的实时人数统计数据")
    @GetMapping("/realtime/{areaId}")
    public Result<PeopleCountData> getRealtimeCount(
            @ApiParam(value = "区域ID", required = true) @PathVariable String areaId) {
        log.info("获取实时人数统计，区域ID：{}", areaId);
        return Result.success(visitorStatisticsService.getRealtimeCount(areaId));
    }

    /**
     * 获取区域人数趋势
     *
     * @param areaId 区域ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param interval 时间间隔（可选）
     * @return 人数趋势数据列表
     */
    @ApiOperation(value = "获取区域人数趋势", notes = "获取指定区域在时间范围内的人数变化趋势")
    @GetMapping("/trends/{areaId}")
    public Result<List<TrendData>> getTrends(
            @ApiParam(value = "区域ID", required = true) @PathVariable String areaId,
            @ApiParam(value = "开始时间", required = true) @RequestParam String startTime,
            @ApiParam(value = "结束时间", required = true) @RequestParam String endTime,
            @ApiParam(value = "时间间隔") @RequestParam(required = false) String interval) {
        log.info("获取区域人数趋势，区域ID：{}，开始时间：{}，结束时间：{}，间隔：{}", 
                areaId, startTime, endTime, interval);
        return Result.success(visitorStatisticsService.getTrends(areaId, startTime, endTime, interval));
    }

    /**
     * 获取区域统计概览
     *
     * @param areaId 区域ID
     * @return 区域统计概览数据
     */
    @ApiOperation(value = "获取区域统计概览", notes = "获取指定区域的统计概览数据")
    @GetMapping("/overview/{areaId}")
    public Result<AreaOverview> getAreaOverview(
            @ApiParam(value = "区域ID", required = true) @PathVariable String areaId) {
        log.info("获取区域统计概览，区域ID：{}", areaId);
        return Result.success(visitorStatisticsService.getAreaOverview(areaId));
    }

    /**
     * 获取区域实时密度分布
     *
     * @param areaId 区域ID
     * @return 区域密度分布数据
     */
    @ApiOperation(value = "获取区域实时密度分布", notes = "获取指定区域的实时人流密度分布数据")
    @GetMapping("/density/{areaId}")
    public Result<DensityDistribution> getDensityDistribution(
            @ApiParam(value = "区域ID", required = true) @PathVariable String areaId) {
        log.info("获取区域实时密度分布，区域ID：{}", areaId);
        return Result.success(visitorStatisticsService.getDensityDistribution(areaId));
    }

    /**
     * 更新区域监控配置
     *
     * @param areaId 区域ID
     * @param config 监控配置
     * @return 操作结果
     */
    @ApiOperation(value = "更新区域监控配置", notes = "更新指定区域的监控配置信息")
    @PutMapping("/config/{areaId}")
    public Result<Void> updateMonitoringConfig(
            @ApiParam(value = "区域ID", required = true) @PathVariable String areaId,
            @ApiParam(value = "监控配置", required = true) @Valid @RequestBody MonitoringConfig config) {
        log.info("更新区域监控配置，区域ID：{}，配置：{}", areaId, config);
        visitorStatisticsService.updateMonitoringConfig(areaId, config);
        return Result.success();
    }

    /**
     * 获取历史统计数据
     *
     * @param queryParams 查询参数
     * @return 历史统计数据
     */
    @ApiOperation(value = "获取历史统计数据", notes = "获取历史访客统计数据，支持多维度查询")
    @GetMapping("/history")
    public Result<List<VisitorStats>> getHistoryStats(
            @ApiParam(value = "查询参数") @Valid QueryParams queryParams) {
        log.info("获取历史统计数据，参数：{}", queryParams);
        return Result.success(visitorStatisticsService.getHistoryStats(queryParams));
    }

    /**
     * 导出统计报表
     *
     * @param queryParams 查询参数
     * @return 文件下载响应
     */
    @ApiOperation(value = "导出统计报表", notes = "导出指定条件的统计数据报表")
    @GetMapping("/export")
    public Result<String> exportStats(
            @ApiParam(value = "查询参数") @Valid QueryParams queryParams) {
        log.info("导出统计报表，参数：{}", queryParams);
        String fileUrl = visitorStatisticsService.exportStats(queryParams);
        return Result.success(fileUrl);
    }
} 