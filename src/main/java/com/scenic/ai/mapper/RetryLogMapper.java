package com.scenic.ai.mapper;

import com.scenic.ai.domain.model.sync.RetryLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 重试日志数据访问接口
 */
@Mapper
public interface RetryLogMapper {
    
    /**
     * 分页查询重试日志
     */
    List<RetryLog> findByPage(@Param("businessType") String businessType,
                             @Param("businessId") String businessId,
                             @Param("status") Integer status,
                             @Param("startTime") LocalDateTime startTime,
                             @Param("endTime") LocalDateTime endTime,
                             @Param("offset") int offset,
                             @Param("pageSize") int pageSize);
    
    /**
     * 统计符合条件的记录总数
     */
    int countByCondition(@Param("businessType") String businessType,
                        @Param("businessId") String businessId,
                        @Param("status") Integer status,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);
    
    /**
     * 批量插入重试日志
     */
    int batchInsert(@Param("list") List<RetryLog> retryLogs);
    
    /**
     * 批量更新重试状态
     */
    int batchUpdateStatus(@Param("list") List<RetryLog> retryLogs);
} 