package com.scenic.ai.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 同步记录实体
 */
@Data
public class SyncRecord {
    private Long id;
    private String syncType;
    private LocalDateTime syncTime;
    private Integer status; // 1: 成功, 0: 失败
    private String errorMessage;
    private Integer processedCount;
} 