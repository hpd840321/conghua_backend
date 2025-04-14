package com.scenic.ai.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 流量分析实体类
 * <p>
 * 对应数据库表 flow_analysis
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowAnalysis {
    
    private Long id;
    
    private String areaCode;
    
    private String areaName;
    
    private Integer flowRate;
    
    private String direction;
    
    private Double speed;
    
    private Double density;
    
    private LocalDateTime analysisTime;
    
    private Integer thresholdValue;
    
    private String heatmapData;
    
    private Double maxDensity;
    
    private Double minDensity;
    
    private String status;
    
    private String remarks;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    /**
     * 获取记录时间
     * 为了兼容使用recordTime的代码，提供analysisTime的别名方法
     */
    public LocalDateTime getRecordTime() {
        return this.analysisTime;
    }
    
    /**
     * 设置记录时间
     * 为了兼容使用recordTime的代码，提供analysisTime的别名方法
     */
    public void setRecordTime(LocalDateTime recordTime) {
        this.analysisTime = recordTime;
    }
    
    /**
     * 获取区域ID
     * 为了兼容使用areaId的代码，提供areaCode的别名方法
     */
    public String getAreaId() {
        return this.areaCode;
    }
    
    /**
     * 设置区域ID
     * 为了兼容使用areaId的代码，提供areaCode的别名方法
     */
    public void setAreaId(String areaId) {
        this.areaCode = areaId;
    }
}
