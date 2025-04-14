package com.scenic.ai.service;

import com.scenic.ai.domain.model.DensityRecord;

import java.time.LocalDateTime;
import java.util.List;

public interface DensityService {
    
    /**
     * 记录区域密度数据
     */
    void recordDensity(DensityRecord record);
    
    /**
     * 批量记录区域密度数据
     */
    void batchRecordDensity(List<DensityRecord> records);
    
    /**
     * 获取密度记录详情
     */
    DensityRecord getDensityById(Long id);
    
    /**
     * 获取区域最新密度记录
     */
    DensityRecord getLatestDensity(String areaId);
    
    /**
     * 获取区域历史密度记录
     */
    List<DensityRecord> getHistoricalDensity(String areaId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 更新密度记录
     */
    void updateDensity(DensityRecord record);
    
    /**
     * 删除密度记录
     */
    void deleteDensity(Long id);
    
    /**
     * 计算区域密度等级
     */
    String calculateDensityLevel(Double density);
} 