package com.scenic.ai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scenic.ai.entity.CrowdStatistics;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 人群统计服务接口
 *
 * @author scenic
 * @date 2024-03-19
 */
public interface CrowdStatisticsService extends IService<CrowdStatistics> {

    /**
     * 分页查询人群统计数据
     *
     * @param pageNum     页码
     * @param pageSize    每页大小
     * @param tourismName 景区名称
     * @param deviceCode  设备编码
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 分页结果
     */
    IPage<CrowdStatistics> getPage(Integer pageNum, Integer pageSize, String tourismName,
            String deviceCode, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据设备编码查询统计数据
     *
     * @param deviceCode 设备编码
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 统计数据列表
     */
    List<CrowdStatistics> getByDevice(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据景区名称查询统计数据
     *
     * @param tourismName 景区名称
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 统计数据列表
     */
    List<CrowdStatistics> getByTourism(String tourismName, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取时段人群分布
     *
     * @param deviceCode  设备编码
     * @param tourismName 景区名称
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 时段分布数据
     */
    List<Map<String, Object>> getHourDistribution(String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取密度分布
     *
     * @param deviceCode  设备编码
     * @param tourismName 景区名称
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 密度分布数据
     */
    List<Map<String, Object>> getDensityDistribution(String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取人群趋势
     *
     * @param deviceCode  设备编码
     * @param tourismName 景区名称
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 趋势数据
     */
    List<Map<String, Object>> getTrend(String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取人群统计概览
     *
     * @param tourismName 景区名称
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 概览数据
     */
    Map<String, Object> getOverview(String tourismName, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取设备最新人群统计数据
     *
     * @param deviceCode 设备编码
     * @return 最新统计数据
     */
    CrowdStatistics getLatest(String deviceCode);

    /**
     * 获取高密度区域统计
     *
     * @param deviceCode       设备编码
     * @param tourismName      景区名称
     * @param startTime        开始时间
     * @param endTime          结束时间
     * @param densityThreshold 密度阈值
     * @return 高密度区域统计数据
     */
    List<Map<String, Object>> getHighDensity(String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime,
            Double densityThreshold);
}