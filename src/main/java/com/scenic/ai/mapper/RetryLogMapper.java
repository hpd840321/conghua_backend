package com.scenic.ai.mapper;

import com.scenic.ai.model.RetryLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 重试日志数据访问接口
 */
@Mapper
public interface RetryLogMapper {

    /**
     * 插入重试日志
     */
    int insert(RetryLog retryLog);

    /**
     * 更新重试信息
     */
    int updateRetryInfo(@Param("id") Long id, @Param("retryCount") int retryCount,
            @Param("nextRetryTime") LocalDateTime nextRetryTime);

    /**
     * 更新状态
     */
    int updateStatus(@Param("id") Long id, @Param("status") String status, @Param("errorMessage") String errorMessage);

    /**
     * 查询需要重试的任务
     */
    List<RetryLog> findRetryTasks(@Param("nextRetryTime") LocalDateTime nextRetryTime, @Param("status") String status);

    /**
     * 根据业务类型和业务ID查询
     */
    RetryLog findByBusinessTypeAndId(@Param("businessType") String businessType,
            @Param("businessId") String businessId);

    /**
     * 根据状态查询
     */
    List<RetryLog> findByStatus(@Param("status") String status);

    /**
     * 按业务类型统计
     */
    List<Map<String, Object>> countByBusinessType(@Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 按状态统计
     */
    List<Map<String, Object>> countByStatus(@Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 根据条件查询
     */
    List<RetryLog> findByConditions(Map<String, Object> params);

    /**
     * 统计记录数
     */
    Long countRecords(Map<String, Object> params);

    /**
     * 清理成功日志
     */
    int cleanSuccessLogs(@Param("beforeTime") LocalDateTime beforeTime);
}