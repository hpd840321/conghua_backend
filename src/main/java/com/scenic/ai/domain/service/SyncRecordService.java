package com.scenic.ai.domain.service;

import com.scenic.ai.domain.model.SyncRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SyncRecordService {
    
    /**
     * 创建同步记录
     */
    void createRecord(SyncRecord record);
    
    /**
     * 更新同步记录
     */
    void updateRecord(SyncRecord record);
    
    /**
     * 删除同步记录
     */
    void deleteRecord(Long id);
    
    /**
     * 获取同步记录详情
     */
    SyncRecord getRecordById(Long id);
    
    /**
     * 获取任务的同步记录列表
     */
    List<SyncRecord> getTaskRecords(Long taskId);
    
    /**
     * 获取成功的同步记录
     */
    List<SyncRecord> getSuccessRecords(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取失败的同步记录
     */
    List<SyncRecord> getFailedRecords(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 条件查询同步记录
     */
    List<SyncRecord> searchRecords(Long taskId, String status,
                                 LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计同步成功率
     */
    double calculateSuccessRate(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 清理历史记录
     */
    void cleanHistoricalRecords(LocalDateTime beforeTime);
    
    /**
     * 分析同步性能
     */
    Map<String, Object> analyzeSyncPerformance(String taskType,
                                             LocalDateTime startTime,
                                             LocalDateTime endTime);
} 