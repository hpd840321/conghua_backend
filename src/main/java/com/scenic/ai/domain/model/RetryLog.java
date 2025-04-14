package com.scenic.ai.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RetryLog {
    private Long id;
    private Long taskId;
    private String taskType;
    private Integer retryCount;
    private String status;
    private String errorMessage;
    private String stackTrace;
    private LocalDateTime retryTime;
    private LocalDateTime nextRetryTime;
    private String handler;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 