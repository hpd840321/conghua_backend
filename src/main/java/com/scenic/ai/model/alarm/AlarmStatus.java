package com.conghua.tourism.model.alarm;

import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * 告警状态枚举
 */
@Getter
@AllArgsConstructor
public enum AlarmStatus {
    
    PENDING("pending", "待处理"),
    PROCESSING("processing", "处理中"),
    RESOLVED("resolved", "已解决"),
    IGNORED("ignored", "已忽略"),
    AUTO_RESOLVED("auto_resolved", "自动解决");
    
    /**
     * 状态编码
     */
    private final String code;
    
    /**
     * 状态描述
     */
    private final String description;
    
    /**
     * 判断是否为终态
     */
    public boolean isFinalState() {
        return this == RESOLVED || this == IGNORED || this == AUTO_RESOLVED;
    }
    
    /**
     * 判断是否可以处理
     */
    public boolean canHandle() {
        return this == PENDING;
    }
} 