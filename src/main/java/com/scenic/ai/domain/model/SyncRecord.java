package com.scenic.ai.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SyncRecord {
    private Long id;
    private Long taskId;
    private String taskType;
    private String status;
    private String source;
    private String target;
    private Integer totalCount;
    private Integer successCount;
    private Integer failCount;
    private String errorMessage;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long duration;
    private String executor;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 