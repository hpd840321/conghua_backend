package com.scenic.ai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.model.CrowdStatistics;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 人群统计服务接口
 */
public interface ICrowdStatisticsService {
    /**
     * 分页查询人群统计数据
     */
    IPage<CrowdStatistics> getPage(Integer pageNum, Integer pageSize, String tourismName,
            String deviceCode, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据设备编码查询统计数据
     */
    List<CrowdStatistics> getByDevice(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据景区名称查询统计数据
     */
    List<CrowdStatistics> getByTourism(String tourismName, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取时段人群分布
     */
    List<Map<String, Object>> getHourDistribution(String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取密度分布
     */
    List<Map<String, Object>> getDensityDistribution(String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取人群趋势
     */
    List<Map<String, Object>> getTrend(String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取统计概览
     */
    Map<String, Object> getOverview(String tourismName, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取设备最新人群统计数据
     */
    CrowdStatistics getLatest(String deviceCode);

    /**
     * 获取高密度区域统计
     */
    List<Map<String, Object>> getHighDensity(String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime, Double densityThreshold);
} 