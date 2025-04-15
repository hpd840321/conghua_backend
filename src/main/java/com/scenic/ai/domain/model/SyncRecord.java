package com.scenic.ai.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 同步记录实体
 */
public class SyncRecord {
    private Long id;
    private String syncType;
    private LocalDateTime syncTime;
    private Integer status; // 1: 成功, 0: 失败
    private String errorMessage;
    private Integer processedCount;
    
    public SyncRecord() {
    }
    
    public Long getId() {
        return id;
    }
    
    public SyncRecord setId(Long id) {
        this.id = id;
        return this;
    }
    
    public String getSyncType() {
        return syncType;
    }
    
    public SyncRecord setSyncType(String syncType) {
        this.syncType = syncType;
        return this;
    }
    
    public LocalDateTime getSyncTime() {
        return syncTime;
    }
    
    public SyncRecord setSyncTime(LocalDateTime syncTime) {
        this.syncTime = syncTime;
        return this;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public SyncRecord setStatus(Integer status) {
        this.status = status;
        return this;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public SyncRecord setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
    
    public Integer getProcessedCount() {
        return processedCount;
    }
    
    public SyncRecord setProcessedCount(Integer processedCount) {
        this.processedCount = processedCount;
        return this;
    }
    
    /**
     * 验证数据有效性
     */
    public void validate() {
        Objects.requireNonNull(syncType, "同步类型不能为空");
        Objects.requireNonNull(syncTime, "同步时间不能为空");
        Objects.requireNonNull(status, "状态不能为空");
        if (status != 0 && status != 1) {
            throw new IllegalArgumentException("状态值必须是0或1");
        }
        if (processedCount != null && processedCount < 0) {
            throw new IllegalArgumentException("处理数量不能为负数");
        }
    }
    
    /**
     * 创建新的同步记录
     */
    public static SyncRecord create(String syncType, Integer status, Integer processedCount) {
        SyncRecord record = new SyncRecord()
            .setSyncType(syncType)
            .setSyncTime(LocalDateTime.now())
            .setStatus(status)
            .setProcessedCount(processedCount);
            
        record.validate();
        return record;
    }
    
    /**
     * 创建失败的同步记录
     */
    public static SyncRecord createFailed(String syncType, String errorMessage) {
        SyncRecord record = new SyncRecord()
            .setSyncType(syncType)
            .setSyncTime(LocalDateTime.now())
            .setStatus(0)
            .setErrorMessage(errorMessage)
            .setProcessedCount(0);
            
        record.validate();
        return record;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SyncRecord that = (SyncRecord) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(syncType, that.syncType) &&
               Objects.equals(syncTime, that.syncTime) &&
               Objects.equals(status, that.status) &&
               Objects.equals(errorMessage, that.errorMessage) &&
               Objects.equals(processedCount, that.processedCount);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, syncType, syncTime, status, errorMessage, processedCount);
    }
    
    @Override
    public String toString() {
        return "SyncRecord{" +
               "id=" + id +
               ", syncType='" + syncType + '\'' +
               ", syncTime=" + syncTime +
               ", status=" + status +
               ", errorMessage='" + errorMessage + '\'' +
               ", processedCount=" + processedCount +
               '}';
    }
} 