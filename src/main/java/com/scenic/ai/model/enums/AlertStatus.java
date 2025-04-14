package com.scenic.ai.model.enums;

public enum AlertStatus {
    PENDING(0),    // 待处理
    PROCESSED(1);  // 已处理

    private final int value;

    AlertStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static AlertStatus fromValue(int value) {
        for (AlertStatus status : AlertStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid AlertStatus value: " + value);
    }
} 