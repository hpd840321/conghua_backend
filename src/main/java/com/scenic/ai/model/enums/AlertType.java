package com.scenic.ai.model.enums;

import lombok.Getter;

/**
 * 告警类型枚举
 */
@Getter
public enum AlertType {
    CROWD_DENSITY("CROWD_DENSITY", "人群密度"),
    CROWD_COUNT("CROWD_COUNT", "人群数量"),
    FLOW_ANALYSIS("FLOW_ANALYSIS", "客流分析");

    private final String code;
    private final String desc;

    AlertType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static AlertType fromCode(String code) {
        for (AlertType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown alert type code: " + code);
    }
} 