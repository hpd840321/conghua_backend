package com.scenic.ai.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 密度记录领域模型
 */
@Data
public class DensityRecord {
    
    /**
     * 记录ID
     */
    private Long id;
    
    /**
     * 区域ID
     */
    private String areaId;
    
    /**
     * 区域名称
     */
    private String areaName;
    
    /**
     * 密度值
     */
    private Double density;
    
    /**
     * 密度等级(LOW/MEDIUM/HIGH)
     */
    private String level;
    
    /**
     * 记录时间
     */
    private LocalDateTime recordTime;
    
    /**
     * 设备编码
     */
    private String deviceCode;
    
    /**
     * 设备名称
     */
    private String deviceName;
    
    /**
     * 景区名称
     */
    private String tourismName;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 