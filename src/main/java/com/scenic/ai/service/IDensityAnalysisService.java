package com.scenic.ai.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scenic.ai.entity.DensityAnalysis;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 密度分析服务接口
 */
public interface IDensityAnalysisService extends IService<DensityAnalysis> {

    /**
     * 分页查询密度分析数据
     *
     * @param page        分页参数
     * @param tourismName 景区名称
     * @param deviceCode  设备编码
     * @param algName     算法名称
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 分页结果
     */
    Page<DensityAnalysis> pageDensityAnalysis(Page<DensityAnalysis> page, String tourismName, String deviceCode,
            String algName, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据设备编码查询密度分析数据
     *
     * @param deviceCode 设备编码
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 密度分析数据列表
     */
    List<DensityAnalysis> getByDeviceCode(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据景区名称查询密度分析数据
     *
     * @param tourismName 景区名称
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 密度分析数据列表
     */
    List<DensityAnalysis> getByTourismName(String tourismName, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取密度趋势数据
     *
     * @param deviceCode  设备编码
     * @param tourismName 景区名称
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 密度趋势数据
     */
    List<Map<String, Object>> getDensityTrend(String deviceCode, String tourismName, LocalDateTime startTime,
            LocalDateTime endTime);

    /**
     * 获取密度级别分布
     *
     * @param deviceCode  设备编码
     * @param tourismName 景区名称
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 密度级别分布数据
     */
    List<Map<String, Object>> getDensityLevelDistribution(String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取高峰时段
     *
     * @param deviceCode 设备编码
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 高峰时段数据
     */
    List<Map<String, Object>> getPeakHours(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取总密度计数
     *
     * @param deviceCode 设备编码
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 总密度计数
     */
    int getTotalDensityCount(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取设备最新的密度分析数据
     *
     * @param deviceCode 设备编码
     * @return 最新的密度分析数据
     */
    DensityAnalysis getLatestDensityByDevice(String deviceCode);

    /**
     * 根据时间范围和景区名称获取密度分析数据
     *
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @param tourismName 景区名称
     * @return 密度分析数据列表
     */
    List<DensityAnalysis> getDensityByTimeRangeAndTourism(LocalDateTime startTime, LocalDateTime endTime,
            String tourismName);

    /**
     * 获取密度统计概览
     *
     * @param tourismName 景区名称
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 密度统计概览
     */
    Map<String, Object> getDensityStatistics(String tourismName, LocalDateTime startTime, LocalDateTime endTime);
}