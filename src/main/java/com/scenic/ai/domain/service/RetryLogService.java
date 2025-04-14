package com.scenic.ai.domain.service;

import com.scenic.ai.domain.model.RetryLog;

import java.time.LocalDateTime;
import java.util.List;

public interface RetryLogService {
    
    /**
     * 记录重试日志
     */
    void logRetry(RetryLog log);
    
    /**
     * 更新重试日志
     */
    void updateLog(RetryLog log);
    
    /**
     * 删除重试日志
     */
    void deleteLog(Long id);
    
    /**
     * 获取重试日志详情
     */
    RetryLog getLogById(Long id);
    
    /**
     * 获取任务的重试日志列表
     */
    List<RetryLog> getTaskRetryLogs(Long taskId);
    
    /**
     * 获取失败的重试日志
     */
    List<RetryLog> getFailedLogs();
    
    /**
     * 获取超过最大重试次数的日志
     */
    List<RetryLog> getMaxRetriedLogs();
    
    /**
     * 条件查询重试日志
     */
    List<RetryLog> searchLogs(Long taskId, String status,
                            LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 清理历史日志
     */
    void cleanHistoricalLogs(LocalDateTime beforeTime);
    
    /**
     * 分析重试成功率
     */
    double analyzeRetrySuccessRate(String taskType, 
                                 LocalDateTime startTime, LocalDateTime endTime);
} 