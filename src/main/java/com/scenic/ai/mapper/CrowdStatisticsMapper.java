package com.scenic.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scenic.ai.model.CrowdStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 人群统计Mapper接口
 */
@Mapper
public interface CrowdStatisticsMapper extends BaseMapper<CrowdStatistics> {
    
    /**
     * 根据设备编码查询人群统计数据
     *
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 人群统计数据列表
     */
    List<CrowdStatistics> selectByDeviceCode(
            @Param("deviceCode") String deviceCode,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 根据景区名称查询人群统计数据
     *
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 人群统计数据列表
     */
    List<CrowdStatistics> selectByTourismName(
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计时段人群分布
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    List<Map<String, Object>> countByHour(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计密度分布
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    List<Map<String, Object>> countByDensity(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 获取人群趋势
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 趋势数据
     */
    List<Map<String, Object>> getCrowdTrend(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 条件分页查询
     *
     * @param params 查询参数
     * @return 人群统计数据列表
     */
    List<CrowdStatistics> findByConditions(@Param("params") Map<String, Object> params);
    
    /**
     * 查询记录总数
     *
     * @param params 查询参数
     * @return 记录总数
     */
    Long countRecords(@Param("params") Map<String, Object> params);

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
    List<Map<String, Object>> getHighDensityStats(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("densityThreshold") BigDecimal densityThreshold);

    /**
     * 根据时间范围和景区名称查询人群统计数据
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param tourismName 景区名称
     * @return 人群统计数据列表
     */
    List<CrowdStatistics> selectByTimeRangeAndTourism(
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 根据设备编码查询最新的人群统计数据
     *
     * @param deviceCode 设备编码
     * @return 人群统计数据
     */
    CrowdStatistics selectLatestByDeviceCode(@Param("deviceCode") String deviceCode);

    /**
     * 获取时段人群分布
     */
    List<Map<String, Object>> getHourDistribution(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 获取密度分布
     */
    List<Map<String, Object>> getDensityDistribution(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 获取人群趋势
     */
    List<Map<String, Object>> getTrend(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 获取统计概览
     */
    Map<String, Object> getOverview(
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 获取高密度区域统计
     */
    List<Map<String, Object>> getHighDensityAreas(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("densityThreshold") BigDecimal densityThreshold);
} 