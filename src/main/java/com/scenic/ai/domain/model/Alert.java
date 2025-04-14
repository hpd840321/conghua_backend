package com.scenic.ai.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Alert {
    private Long id;
    private String type;
    private String level;
    private String status;
    private String deviceCode;
    private String deviceName;
    private String tourismName;
    private String description;
    private Double value;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String handler;
    private String remark;
    private LocalDateTime handleTime;
} 