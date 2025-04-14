package com.scenic.ai.domain.service;

import com.scenic.ai.domain.model.SyncTask;

import java.time.LocalDateTime;
import java.util.List;

public interface SyncTaskService {
    
    /**
     * 创建同步任务
     */
    void createTask(SyncTask task);
    
    /**
     * 更新同步任务
     */
    void updateTask(SyncTask task);
    
    /**
     * 删除同步任务
     */
    void deleteTask(Long id);
    
    /**
     * 获取任务详情
     */
    SyncTask getTaskById(Long id);
    
    /**
     * 获取待执行的任务列表
     */
    List<SyncTask> getPendingTasks();
    
    /**
     * 获取执行中的任务列表
     */
    List<SyncTask> getRunningTasks();
    
    /**
     * 更新任务状态
     */
    void updateTaskStatus(Long id, String status, String message);
    
    /**
     * 获取超时任务列表
     */
    List<SyncTask> getTimeoutTasks(Integer timeoutMinutes);
    
    /**
     * 条件查询任务
     */
    List<SyncTask> searchTasks(String type, String status, 
                             LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 重试失败的任务
     */
    void retryTask(Long id);
    
    /**
     * 取消任务
     */
    void cancelTask(Long id);
} 