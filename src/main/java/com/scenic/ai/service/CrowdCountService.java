package com.scenic.ai.service;

import com.scenic.ai.domain.model.CrowdCount;

import java.time.LocalDateTime;
import java.util.List;

public interface CrowdCountService {
    
    /**
     * 记录人流量数据
     */
    void recordCrowdCount(CrowdCount crowdCount);
    
    /**
     * 批量记录人流量数据
     */
    void batchRecordCrowdCount(List<CrowdCount> crowdCounts);
    
    /**
     * 获取区域人流量历史记录
     */
    List<CrowdCount> getAreaHistory(String areaId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取区域平均人流量
     */
    Double getAreaAverageCount(String areaId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取区域最大人流量
     */
    Integer getAreaMaxCount(String areaId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 清理历史数据
     */
    void cleanHistoricalData(LocalDateTime beforeTime);
    
    /**
     * 获取高密度区域
     */
    List<CrowdCount> getHighDensityAreas(String areaId, Integer threshold, 
                                        LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取区域密度
     */
    Double getAreaDensity(String areaId, LocalDateTime time);
    
    /**
     * 分析区域人流量趋势
     */
    List<CrowdCount> analyzeAreaTrend(String areaId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取区域实时人流量
     */
    CrowdCount getAreaRealtimeCount(String areaId);
    
    /**
     * 获取所有区域实时人流量
     */
    List<CrowdCount> getAllAreasRealtimeCount();
} 