package com.scenic.ai.model.enums;

/**
 * 告警类型枚举
 */
public enum AlertType {
    
    CROWD_DENSITY("CD", "人群密度告警"),
    CROWD_GATHERING("CG", "人群聚集告警"),
    CROWD_FLOW("CF", "客流告警"),
    CROWD_COUNT("CC", "人群数量告警"),
    DEVICE_OFFLINE("DO", "设备离线告警"),
    DEVICE_ERROR("DE", "设备异常告警");
    
    private final String code;
    private final String description;
    
    AlertType(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 获取告警类型编码
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 获取告警类型描述
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * 获取告警类型值
     */
    public String getValue() {
        return code;
    }
    
    /**
     * 根据值获取告警类型
     */
    public static AlertType fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (AlertType type : values()) {
            if (type.getCode().equals(value)) {
                return type;
            }
        }
        return null;
    }
} 