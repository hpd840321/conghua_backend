package com.scenic.ai.model.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 告警级别枚举
 */
@Getter
@AllArgsConstructor
public enum AlarmLevel {
    
    CRITICAL(1, "严重", "#FF0000"),
    HIGH(2, "高", "#FFA500"),
    MEDIUM(3, "中", "#FFFF00"),
    LOW(4, "低", "#00FF00");
    
    /**
     * 级别值
     */
    private final int value;
    
    /**
     * 级别名称
     */
    private final String name;
    
    /**
     * 显示颜色
     */
    private final String color;
} 