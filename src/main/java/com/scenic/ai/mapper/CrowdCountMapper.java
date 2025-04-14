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
    List<CrowdCount> findByTimeRange(@Param("areaId") String areaId,
                                    @Param("startTime") LocalDateTime startTime,
                                    @Param("endTime") LocalDateTime endTime);
    
    /**
     * 计算平均人数
     */
    Double calculateAverageCount(@Param("areaId") String areaId,
                               @Param("startTime") LocalDateTime startTime,
                               @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询最大人数
     */
    Integer findMaxCount(@Param("areaId") String areaId,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);
    
    /**
     * 删除历史数据
     */
    int deleteHistoricalData(@Param("time") LocalDateTime time);
    
    /**
     * 查询高密度区域
     */
    List<CrowdCount> findHighDensityAreas(@Param("areaId") String areaId,
                                         @Param("threshold") int threshold,
                                         @Param("startTime") LocalDateTime startTime,
                                         @Param("endTime") LocalDateTime endTime);
    
    /**
     * 计算区域密度
     */
    Double calculateDensity(@Param("areaId") String areaId,
                          @Param("time") LocalDateTime time);
    
    /**
     * 分析人群趋势
     */
    List<CrowdCount> analyzeTrend(@Param("areaId") String areaId,
                                 @Param("startTime") LocalDateTime startTime,
                                 @Param("endTime") LocalDateTime endTime);
    
    /**
     * 获取最新计数
     */
    CrowdCount findLatestCount(@Param("areaId") String areaId);
    
    /**
     * 获取所有区域最新计数
     */
    List<CrowdCount> findLatestCounts();
} 