package com.scenic.ai.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DensityRecord {
    private Long id;
    private String areaId;
    private String areaName;
    private Double density;
    private String level;
    private LocalDateTime recordTime;
    private String deviceCode;
    private String deviceName;
    private String tourismName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 