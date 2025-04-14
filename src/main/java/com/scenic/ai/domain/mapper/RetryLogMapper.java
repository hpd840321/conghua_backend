package com.scenic.ai.domain.mapper;

import com.scenic.ai.domain.model.RetryLog;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RetryLogMapper {
    
    /**
     * 插入重试日志
     */
    int insert(RetryLog log);
    
    /**
     * 更新重试日志
     */
    int update(RetryLog log);
    
    /**
     * 删除重试日志
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据ID查询
     */
    RetryLog selectById(@Param("id") Long id);
    
    /**
     * 根据任务ID查询重试日志
     */
    List<RetryLog> selectByTaskId(@Param("taskId") Long taskId);
    
    /**
     * 查询失败的重试日志
     */
    List<RetryLog> selectFailedLogs();
    
    /**
     * 查询超过最大重试次数的日志
     */
    List<RetryLog> selectMaxRetriedLogs();
    
    /**
     * 条件查询重试日志
     */
    List<RetryLog> selectByCondition(@Param("taskId") Long taskId,
                                   @Param("status") String status,
                                   @Param("startTime") LocalDateTime startTime,
                                   @Param("endTime") LocalDateTime endTime);
    
    /**
     * 清理历史日志
     */
    int deleteHistoricalLogs(@Param("beforeTime") LocalDateTime beforeTime);
} 