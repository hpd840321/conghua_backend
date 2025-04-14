package com.scenic.ai.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 密度趋势数据传输对象
 */
@Data
@Builder
public class DensityTrendDTO {
    /**
     * 设备编码
     */
    private String deviceCode;
    
    /**
     * 设备名称
     */
    private String deviceName;
    
    /**
     * 密度值
     */
    private Double density;
    
    /**
     * 记录时间
     */
    private LocalDateTime recordTime;
} 