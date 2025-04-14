package com.scenic.ai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scenic.ai.domain.model.CrowdStatistics;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 人群统计服务接口
 */
public interface CrowdStatisticsService extends IService<CrowdStatistics> {
    
    /**
     * 分页查询人群统计数据
     */
    IPage<CrowdStatistics> pageStatistics(IPage<CrowdStatistics> page, String tourismName, 
                                        String deviceCode, String algName,
                                        LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取指定时间范围内的人群密度趋势基础数据
     */
    List<Map<String, Object>> getDensityTrendBasic(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取指定时间范围内的人群数量趋势
     */
    List<Map<String, Object>> getCountTrend(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取指定时间点的人群热力图数据
     */
    List<Map<String, Object>> getHeatMapData(String tourismName, LocalDateTime recordTime);

    /**
     * 统计指定时间范围内的平均人数
     */
    double getAverageCount(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取人群密度最高的时间点
     */
    CrowdStatistics getPeakDensityRecord(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取统计数据
     */
    Map<String, Object> getStatistics(String tourismName, String deviceCode, 
            LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取密度趋势详细数据
     */
    Map<String, Object> getDensityTrendWithDetails(String tourismName, String deviceCode, 
            LocalDateTime startTime, LocalDateTime endTime);
} 