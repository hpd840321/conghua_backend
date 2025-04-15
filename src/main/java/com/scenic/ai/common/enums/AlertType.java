package com.scenic.ai.common.enums;

/**
 * 告警类型枚举
 * 
 * @author AI
 * @date 2024-04-15
 */
public enum AlertType {
    CROWD_DENSITY("CROWD_DENSITY", "人群密度告警"),
    CROWD_FLOW("CROWD_FLOW", "客流告警"),
    CROWD_GATHERING("CROWD_GATHERING", "人群聚集告警"),
    CROWD_DISPERSION("CROWD_DISPERSION", "人群分散告警"),
    CROWD_ABNORMAL("CROWD_ABNORMAL", "人群异常告警"),
    DEVICE_OFFLINE("DEVICE_OFFLINE", "设备离线告警"),
    DEVICE_ERROR("DEVICE_ERROR", "设备错误告警"),
    SYSTEM_ERROR("SYSTEM_ERROR", "系统错误告警");

    private final String code;
    private final String description;

    AlertType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static AlertType fromCode(String code) {
        for (AlertType type : AlertType.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid alert type code: " + code);
    }
}