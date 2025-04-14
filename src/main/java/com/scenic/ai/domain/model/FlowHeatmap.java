package com.scenic.ai.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 客流热力图领域模型
 * 表示景区内某个时间点的客流密度分布
 */
public class FlowHeatmap {
    @Getter
    private final LocalDateTime timestamp;
    
    @Getter
    private final String heatmapData;
    
    @Getter
    private final Double maxDensity;
    
    @Getter
    private final Double minDensity;
    
    private FlowHeatmap(LocalDateTime timestamp, String heatmapData, Double maxDensity, Double minDensity) {
        this.timestamp = timestamp;
        this.heatmapData = heatmapData;
        this.maxDensity = maxDensity;
        this.minDensity = minDensity;
        validate();
    }
    
    /**
     * 创建热力图实例
     */
    public static FlowHeatmap create(LocalDateTime timestamp, String heatmapData, Double maxDensity, Double minDensity) {
        return new FlowHeatmap(timestamp, heatmapData, maxDensity, minDensity);
    }
    
    /**
     * 验证热力图数据的有效性
     */
    private void validate() {
        if (timestamp == null) {
            throw new IllegalArgumentException("时间戳不能为空");
        }
        if (heatmapData == null || heatmapData.trim().isEmpty()) {
            throw new IllegalArgumentException("热力图数据不能为空");
        }
        if (maxDensity == null || minDensity == null) {
            throw new IllegalArgumentException("密度值不能为空");
        }
        if (maxDensity < minDensity) {
            throw new IllegalArgumentException("最大密度不能小于最小密度");
        }
    }
    
    /**
     * 计算平均密度
     */
    public double calculateAverageDensity() {
        return (maxDensity + minDensity) / 2.0;
    }
    
    /**
     * 判断是否为高密度区域
     */
    public boolean isHighDensityArea(double threshold) {
        return maxDensity >= threshold;
    }
    
    /**
     * 计算密度变化率
     */
    public double calculateDensityChangeRate(FlowHeatmap other) {
        if (other == null || other.calculateAverageDensity() == 0) {
            return 0.0;
        }
        return (this.calculateAverageDensity() - other.calculateAverageDensity()) * 100.0 / other.calculateAverageDensity();
    }
} 