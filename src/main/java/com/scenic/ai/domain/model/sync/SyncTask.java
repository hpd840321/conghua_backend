package com.scenic.ai.domain.model.sync;

import java.time.LocalDateTime;

/**
 * 同步任务实体类
 */
public class SyncTask {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 任务编码
     */
    private String taskCode;
    
    /**
     * 任务类型
     */
    private String taskType;
    
    /**
     * 任务状态（0-待执行，1-执行中，2-执行成功，3-执行失败）
     */
    private Integer status;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 错误信息
     */
    private String errorMessage;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    // 构造函数
    public SyncTask() {
    }

    public SyncTask(Long id, String taskCode, String taskType, Integer status,
                   LocalDateTime startTime, LocalDateTime endTime, String errorMessage,
                   LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.taskCode = taskCode;
        this.taskType = taskType;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.errorMessage = errorMessage;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // Getter方法
    public Long getId() {
        return id;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public String getTaskType() {
        return taskType;
    }

    public Integer getStatus() {
        return status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    // Setter方法
    public void setId(Long id) {
        this.id = id;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
} 