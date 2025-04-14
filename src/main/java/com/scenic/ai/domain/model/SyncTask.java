package com.scenic.ai.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SyncTask {
    private Long id;
    private String type;
    private String status;
    private String source;
    private String target;
    private String params;
    private Integer priority;
    private Integer retryCount;
    private Integer maxRetries;
    private String message;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime nextRetryTime;
    private String creator;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 