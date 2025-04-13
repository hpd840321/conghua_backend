package com.conghua.tourism.model.alarm;

import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * 告警类型枚举
 */
@Getter
@AllArgsConstructor
public enum AlarmType {
    
    CROWD_DENSITY("crowd_density", "人流密度告警"),
    ABNORMAL_BEHAVIOR("abnormal_behavior", "异常行为告警"),
    DEVICE_FAULT("device_fault", "设备故障告警"),
    ENVIRONMENT("environment", "环境告警"),
    SECURITY("security", "安全告警");
    
    /**
     * 类型编码
     */
    private final String code;
    
    /**
     * 类型描述
     */
    private final String description;
} 