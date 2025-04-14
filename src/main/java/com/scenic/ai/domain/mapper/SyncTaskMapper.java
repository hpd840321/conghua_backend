package com.scenic.ai.domain.mapper;

import com.scenic.ai.domain.model.SyncTask;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SyncTaskMapper {
    
    /**
     * 插入同步任务
     */
    int insert(SyncTask task);
    
    /**
     * 更新同步任务
     */
    int update(SyncTask task);
    
    /**
     * 删除同步任务
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据ID查询
     */
    SyncTask selectById(@Param("id") Long id);
    
    /**
     * 查询待执行的任务
     */
    List<SyncTask> selectPendingTasks();
    
    /**
     * 查询执行中的任务
     */
    List<SyncTask> selectRunningTasks();
    
    /**
     * 更新任务状态
     */
    int updateStatus(@Param("id") Long id, 
                    @Param("status") String status,
                    @Param("message") String message);
    
    /**
     * 查询超时的任务
     */
    List<SyncTask> selectTimeoutTasks(@Param("timeout") Integer timeoutMinutes);
    
    /**
     * 条件查询任务
     */
    List<SyncTask> selectByCondition(@Param("type") String type,
                                   @Param("status") String status,
                                   @Param("startTime") LocalDateTime startTime,
                                   @Param("endTime") LocalDateTime endTime);
} 