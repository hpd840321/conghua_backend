package com.scenic.ai.common.enums;

/**
 * 告警类型枚举类
 * 
 * @author AI
 * @date 2024-04-15
 */
public enum AlertType {
    CROWD_DENSITY(1, "人群密度"),
    ABNORMAL_BEHAVIOR(2, "异常行为"),
    SAFETY_RISK(3, "安全隐患"),
    EQUIPMENT_FAILURE(4, "设备故障"),
    ENVIRONMENTAL_ANOMALY(5, "环境异常");

    private final int code;
    private final String description;

    AlertType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static AlertType fromCode(int code) {
        for (AlertType type : AlertType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid alert type code: " + code);
    }
}