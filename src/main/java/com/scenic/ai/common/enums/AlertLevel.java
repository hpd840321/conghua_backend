package com.scenic.ai.common.enums;

/**
 * 告警级别枚举
 * 
 * @author AI
 * @date 2024-04-15
 */
public enum AlertLevel {
    LOW(1, "低"),
    MEDIUM(2, "中"),
    HIGH(3, "高");

    private final int value;
    private final String description;

    AlertLevel(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static AlertLevel fromValue(int value) {
        for (AlertLevel level : AlertLevel.values()) {
            if (level.value == value) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid alert level value: " + value);
    }
}