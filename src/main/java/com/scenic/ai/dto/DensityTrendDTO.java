package com.scenic.ai.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 密度趋势数据传输对象
 */
@Data
public class DensityTrendDTO {
    
    /**
     * 区域ID
     */
    private String areaId;
    
    /**
     * 密度值
     */
    private Double density;
    
    /**
     * 密度等级(LOW/MEDIUM/HIGH)
     */
    private String level;
    
    /**
     * 统计时间
     */
    private LocalDateTime timestamp;
    
    /**
     * 区域名称
     */
    private String areaName;
    
    /**
     * 告警阈值
     */
    private Double threshold;
} 