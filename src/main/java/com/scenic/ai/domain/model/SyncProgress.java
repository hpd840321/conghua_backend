package com.scenic.ai.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 同步进度领域模型
 * 用于跟踪数据同步的进度和状态
 */
@Getter
@Setter
public class SyncProgress {
    /**
     * 同步状态枚举
     */
    public enum SyncStatus {
        STARTED("已开始"),
        IN_PROGRESS("进行中"),
        COMPLETED("已完成"),
        FAILED("失败");

        private final String description;

        SyncStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    private String syncId;
    private float progress;
    private SyncStatus status;
    private String errorMessage;
    private LocalDateTime startTime;
    private LocalDateTime lastUpdateTime;

    public SyncProgress(String syncId) {
        this.syncId = syncId;
        this.progress = 0.0f;
        this.status = SyncStatus.STARTED;
        this.startTime = LocalDateTime.now();
        this.lastUpdateTime = this.startTime;
    }

    /**
     * 更新同步进度
     * @param progress 进度值(0.0-1.0)
     * @throws IllegalArgumentException 当进度值无效时抛出
     */
    public void updateProgress(float progress) {
        if (progress < 0.0f || progress > 1.0f) {
            throw new IllegalArgumentException("进度值必须在0.0到1.0之间");
        }
        if (progress < this.progress) {
            throw new IllegalArgumentException("进度不能回退");
        }
        if (status == SyncStatus.COMPLETED || status == SyncStatus.FAILED) {
            throw new IllegalStateException("当前状态不允许更新进度");
        }
        
        this.progress = progress;
        this.status = progress < 1.0f ? SyncStatus.IN_PROGRESS : SyncStatus.COMPLETED;
        this.lastUpdateTime = LocalDateTime.now();
    }

    /**
     * 标记同步完成
     */
    public void complete() {
        if (status == SyncStatus.FAILED) {
            throw new IllegalStateException("失败状态不能直接标记为完成");
        }
        this.progress = 1.0f;
        this.status = SyncStatus.COMPLETED;
        this.lastUpdateTime = LocalDateTime.now();
    }

    /**
     * 标记同步失败
     * @param errorMessage 错误信息
     */
    public void fail(String errorMessage) {
        this.status = SyncStatus.FAILED;
        this.errorMessage = errorMessage;
        this.lastUpdateTime = LocalDateTime.now();
    }

    /**
     * 获取同步持续时间（分钟）
     */
    public long getDurationInMinutes() {
        return java.time.Duration.between(startTime, lastUpdateTime).toMinutes();
    }
} 