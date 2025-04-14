package com.scenic.ai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scenic.ai.model.RetryLog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 重试日志服务接口
 */
public interface RetryLogService extends IService<RetryLog> {
    
    /**
     * 创建重试日志
     *
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @param maxRetryCount 最大重试次数
     * @param errorMessage 错误信息
     * @return 重试日志
     */
    RetryLog createRetryLog(String businessType, String businessId, int maxRetryCount, String errorMessage);
    
    /**
     * 更新重试信息
     *
     * @param id 重试日志ID
     * @param retryCount 重试次数
     * @param nextRetryTime 下次重试时间
     * @param errorMessage 错误信息
     * @return 是否更新成功
     */
    boolean updateRetryInfo(Long id, int retryCount, LocalDateTime nextRetryTime, String errorMessage);
    
    /**
     * 更新状态
     *
     * @param id 重试日志ID
     * @param status 状态
     * @param errorMessage 错误信息
     * @return 是否更新成功
     */
    boolean updateStatus(Long id, String status, String errorMessage);
    
    /**
     * 查询需要重试的任务
     *
     * @param nextRetryTime 下次重试时间
     * @param status 状态
     * @return 重试日志列表
     */
    List<RetryLog> findRetryTasks(LocalDateTime nextRetryTime, String status);
    
    /**
     * 根据业务类型和业务ID查询重试日志
     *
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 重试日志
     */
    RetryLog findByBusinessTypeAndId(String businessType, String businessId);
    
    /**
     * 根据状态查询重试日志
     *
     * @param status 状态
     * @return 重试日志列表
     */
    List<RetryLog> findByStatus(String status);
    
    /**
     * 统计各业务类型重试次数
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    List<Map<String, Object>> countByBusinessType(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计重试状态分布
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    List<Map<String, Object>> countByStatus(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 条件分页查询
     *
     * @param params 查询参数
     * @return 重试日志列表
     */
    List<RetryLog> findByConditions(Map<String, Object> params);
    
    /**
     * 查询重试日志总数
     *
     * @param params 查询参数
     * @return 记录总数
     */
    Long countRecords(Map<String, Object> params);
    
    /**
     * 清理成功的重试日志
     *
     * @param beforeTime 指定时间之前的记录
     * @return 清理的记录数
     */
    int cleanSuccessLogs(LocalDateTime beforeTime);
} 