package com.scenic.ai.common.enums;

/**
 * 告警状态枚举
 * 
 * @author AI
 * @date 2024-04-15
 */
public enum AlertStatus {
    PENDING(0, "待处理"),
    PROCESSING(1, "处理中"),
    COMPLETED(2, "已完成"),
    IGNORED(3, "已忽略"),
    FAILED(4, "处理失败");

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

    public static AlertStatus fromValue(int value) {
        for (AlertStatus status : AlertStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid alert status value: " + value);
    }
}