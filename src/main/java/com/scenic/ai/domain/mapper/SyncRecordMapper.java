package com.scenic.ai.domain.mapper;

import com.scenic.ai.domain.model.SyncRecord;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SyncRecordMapper {
    
    /**
     * 插入同步记录
     */
    int insert(SyncRecord record);
    
    /**
     * 更新同步记录
     */
    int update(SyncRecord record);
    
    /**
     * 删除同步记录
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据ID查询
     */
    SyncRecord selectById(@Param("id") Long id);
    
    /**
     * 根据任务ID查询同步记录
     */
    List<SyncRecord> selectByTaskId(@Param("taskId") Long taskId);
    
    /**
     * 查询成功的同步记录
     */
    List<SyncRecord> selectSuccessRecords(@Param("startTime") LocalDateTime startTime,
                                        @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询失败的同步记录
     */
    List<SyncRecord> selectFailedRecords(@Param("startTime") LocalDateTime startTime,
                                       @Param("endTime") LocalDateTime endTime);
    
    /**
     * 条件查询同步记录
     */
    List<SyncRecord> selectByCondition(@Param("taskId") Long taskId,
                                     @Param("status") String status,
                                     @Param("startTime") LocalDateTime startTime,
                                     @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计同步成功率
     */
    double calculateSuccessRate(@Param("startTime") LocalDateTime startTime,
                              @Param("endTime") LocalDateTime endTime);
    
    /**
     * 清理历史记录
     */
    int deleteHistoricalRecords(@Param("beforeTime") LocalDateTime beforeTime);
} 