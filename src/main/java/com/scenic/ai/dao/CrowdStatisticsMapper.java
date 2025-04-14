package com.scenic.ai.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.model.CrowdStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

/**
 * 人群统计数据访问接口
 *
 * @author scenic
 * @date 2024-03-19
 */
@Mapper
public interface CrowdStatisticsMapper extends BaseMapper<CrowdStatistics> {
    
    /**
     * 根据设备编码查询统计数据
     *
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计数据列表
     */
    List<CrowdStatistics> selectByDevice(@Param("deviceCode") String deviceCode,
                                       @Param("startTime") LocalDateTime startTime,
                                       @Param("endTime") LocalDateTime endTime);
    
    /**
     * 根据景区名称查询统计数据
     *
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计数据列表
     */
    List<CrowdStatistics> selectByTourism(@Param("tourismName") String tourismName,
                                         @Param("startTime") LocalDateTime startTime,
                                         @Param("endTime") LocalDateTime endTime);
    
    /**
     * 获取设备最新人群统计数据
     *
     * @param deviceCode 设备编码
     * @return 最新统计数据
     */
    CrowdStatistics selectLatestByDevice(@Param("deviceCode") String deviceCode);

    /**
     * 根据时间范围和景区名称查询统计数据
     */
    List<CrowdStatistics> selectByTimeRangeAndTourism(@Param("startTime") LocalDateTime startTime,
                                                     @Param("endTime") LocalDateTime endTime,
                                                     @Param("tourismName") String tourismName);

    /**
     * 获取时段人群分布
     */
    List<Map<String, Object>> getHourDistribution(@Param("deviceCode") String deviceCode,
                                                @Param("tourismName") String tourismName,
                                                @Param("startTime") LocalDateTime startTime,
                                                @Param("endTime") LocalDateTime endTime);

    /**
     * 获取密度分布
     */
    List<Map<String, Object>> getDensityDistribution(@Param("deviceCode") String deviceCode,
                                                   @Param("tourismName") String tourismName,
                                                   @Param("startTime") LocalDateTime startTime,
                                                   @Param("endTime") LocalDateTime endTime);

    /**
     * 获取人群趋势
     */
    List<Map<String, Object>> getTrend(@Param("deviceCode") String deviceCode,
                                     @Param("tourismName") String tourismName,
                                     @Param("startTime") LocalDateTime startTime,
                                     @Param("endTime") LocalDateTime endTime);

    /**
     * 获取统计概览
     */
    Map<String, Object> getOverview(@Param("tourismName") String tourismName,
                                  @Param("startTime") LocalDateTime startTime,
                                  @Param("endTime") LocalDateTime endTime);

    /**
     * 获取高密度区域统计
     */
    List<Map<String, Object>> getHighDensityAreas(@Param("deviceCode") String deviceCode,
                                                @Param("tourismName") String tourismName,
                                                @Param("startTime") LocalDateTime startTime,
                                                @Param("endTime") LocalDateTime endTime,
                                                @Param("densityThreshold") BigDecimal densityThreshold);

    /**
     * 查询小时分布数据
     */
    List<Map<String, Object>> selectHourDistribution(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询密度分布数据
     */
    List<Map<String, Object>> selectDensityDistribution(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询趋势数据
     */
    List<Map<String, Object>> selectTrend(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询概览数据
     */
    Map<String, Object> selectOverview(
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询高密度区域数据
     */
    List<Map<String, Object>> selectHighDensity(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("densityThreshold") Double densityThreshold);
} 