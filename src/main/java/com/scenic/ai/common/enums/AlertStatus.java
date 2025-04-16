package com.scenic.ai.common.enums;

/**
 * 告警状态枚举类
 * 
 * @author AI
 * @date 2024-04-15
 */
public enum AlertStatus {
    /**
     * 待处理
     */
    PENDING(0, "待处理"),

    /**
     * 处理中
     */
    PROCESSING(1, "处理中"),

    /**
     * 已处理
     */
    HANDLED(2, "已处理"),

    /**
     * 已忽略
     */
    IGNORED(3, "已忽略");

    private final int code;
    private final String description;

    AlertStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static AlertStatus getByCode(int code) {
        for (AlertStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}