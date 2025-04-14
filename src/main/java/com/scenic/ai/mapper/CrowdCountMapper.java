package com.scenic.ai.mapper;

import com.scenic.ai.domain.model.CrowdCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 人群计数数据访问接口
 */
@Mapper
public interface CrowdCountMapper {
    /**
     * 插入记录
     */
    void insert(CrowdCount entity);
    
    /**
     * 批量插入记录
     */
    int batchInsert(@Param("list") List<CrowdCount> list);
    
    /**
     * 根据时间范围查询记录
     */
    List<CrowdCount> findByTimeRange(@Param("deviceCode") String deviceCode,
                                    @Param("startTime") LocalDateTime startTime,
                                    @Param("endTime") LocalDateTime endTime);
    
    /**
     * 计算平均人数
     */
    Double calculateAverageCount(@Param("deviceCode") String deviceCode,
                               @Param("startTime") LocalDateTime startTime,
                               @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询最大人数
     */
    Integer findMaxCount(@Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);
    
    /**
     * 删除历史数据
     */
    int deleteHistoricalData(@Param("time") LocalDateTime time);
    
    /**
     * 查询高密度区域
     */
    List<CrowdCount> findHighDensityAreas(@Param("deviceCode") String deviceCode,
                                         @Param("threshold") int threshold,
                                         @Param("startTime") LocalDateTime startTime,
                                         @Param("endTime") LocalDateTime endTime);
    
    /**
     * 计算区域密度
     */
    Double calculateDensity(@Param("deviceCode") String deviceCode,
                          @Param("time") LocalDateTime time);
    
    /**
     * 分析人群趋势
     */
    List<CrowdCount> analyzeTrend(@Param("deviceCode") String deviceCode,
                                 @Param("startTime") LocalDateTime startTime,
                                 @Param("endTime") LocalDateTime endTime);
    
    /**
     * 获取最新计数
     */
    CrowdCount findLatestCount(@Param("deviceCode") String deviceCode);
    
    /**
     * 获取所有区域最新计数
     */
    List<CrowdCount> findLatestCounts();
    
    /**
     * 计算平均密度
     */
    Double calculateAverageDensity(@Param("deviceCode") String deviceCode,
                                  @Param("startTime") LocalDateTime startTime,
                                  @Param("endTime") LocalDateTime endTime);
} 