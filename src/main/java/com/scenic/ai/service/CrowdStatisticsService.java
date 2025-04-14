package com.scenic.ai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scenic.ai.model.CrowdStatistics;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 人群统计服务接口
 */
public interface CrowdStatisticsService extends IService<CrowdStatistics> {
    
    /**
     * 根据设备编码查询人群统计数据
     *
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 人群统计数据列表
     */
    List<CrowdStatistics> getCrowdByDevice(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 根据景区名称查询人群统计数据
     *
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 人群统计数据列表
     */
    List<CrowdStatistics> getCrowdByTourism(String tourismName, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计时段人群分布
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    List<Map<String, Object>> getHourDistribution(String deviceCode, String tourismName, 
            LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计密度分布
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    List<Map<String, Object>> getDensityDistribution(String deviceCode, String tourismName, 
            LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取人群趋势
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 趋势数据
     */
    List<Map<String, Object>> getCrowdTrend(String deviceCode, String tourismName, 
            LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 分页查询人群统计数据
     *
     * @param params 查询参数
     * @return 人群统计数据列表
     */
    List<CrowdStatistics> pageCrowdStatistics(Map<String, Object> params);
    
    /**
     * 查询记录总数
     *
     * @param params 查询参数
     * @return 记录总数
     */
    Long countCrowdStatistics(Map<String, Object> params);

    /**
     * 获取高密度区域统计
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param densityThreshold 密度阈值
     * @return 统计结果
     */
    List<Map<String, Object>> getHighDensityStats(String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime, BigDecimal densityThreshold);

    /**
     * 根据时间范围和景区名称查询人群统计数据
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param tourismName 景区名称
     * @return 人群统计数据列表
     */
    List<CrowdStatistics> getCrowdByTimeRangeAndTourism(LocalDateTime startTime, LocalDateTime endTime, 
            String tourismName);

    /**
     * 根据设备编码查询最新的人群统计数据
     *
     * @param deviceCode 设备编码
     * @return 人群统计数据
     */
    CrowdStatistics getLatestCrowdByDevice(String deviceCode);

    /**
     * 分页查询人群统计数据
     *
     * @param page 分页参数
     * @param tourismName 景区名称
     * @param deviceCode 设备编码
     * @param algName 算法类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页结果
     */
    IPage<CrowdStatistics> pageCrowdStatistics(IPage<CrowdStatistics> page, String tourismName,
            String deviceCode, String algName, LocalDateTime startTime, LocalDateTime endTime);
} 