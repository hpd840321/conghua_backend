package com.scenic.ai.model.enums;

/**
 * 告警状态枚举
 */
public enum AlertStatus {
    
    PENDING(0, "待处理"),
    PROCESSED(1, "已处理");
    
    private final int value;
    private final String description;
    
    AlertStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }
    
    public int getValue() {
        return value;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static AlertStatus fromValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (AlertStatus status : values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        return null;
    }
} 