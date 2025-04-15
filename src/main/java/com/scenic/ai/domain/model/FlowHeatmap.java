package com.scenic.ai.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 客流热力图领域模型
 * 表示景区内某个时间点的客流密度分布
 */
public class FlowHeatmap {
    private final LocalDateTime timestamp;
    private final String heatmapData;
    private final Double maxDensity;
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
        Objects.requireNonNull(timestamp, "时间戳不能为空");
        Objects.requireNonNull(heatmapData, "热力图数据不能为空");
        if (heatmapData.trim().isEmpty()) {
            throw new IllegalArgumentException("热力图数据不能为空");
        }
        Objects.requireNonNull(maxDensity, "最大密度值不能为空");
        Objects.requireNonNull(minDensity, "最小密度值不能为空");
        if (maxDensity < minDensity) {
            throw new IllegalArgumentException("最大密度值不能小于最小密度值");
        }
        if (maxDensity < 0 || minDensity < 0) {
            throw new IllegalArgumentException("密度值不能为负数");
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
        if (other == null) {
            throw new IllegalArgumentException("比较的热力图不能为空");
        }
        double currentAvg = this.calculateAverageDensity();
        double otherAvg = other.calculateAverageDensity();
        if (otherAvg == 0) {
            return currentAvg > 0 ? 1.0 : 0.0;
        }
        return (currentAvg - otherAvg) / otherAvg;
    }
    
    /**
     * Getter方法
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public String getHeatmapData() {
        return heatmapData;
    }
    
    public Double getMaxDensity() {
        return maxDensity;
    }
    
    public Double getMinDensity() {
        return minDensity;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlowHeatmap that = (FlowHeatmap) o;
        return Objects.equals(timestamp, that.timestamp) &&
               Objects.equals(heatmapData, that.heatmapData) &&
               Objects.equals(maxDensity, that.maxDensity) &&
               Objects.equals(minDensity, that.minDensity);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(timestamp, heatmapData, maxDensity, minDensity);
    }
    
    @Override
    public String toString() {
        return "FlowHeatmap{" +
               "timestamp=" + timestamp +
               ", heatmapData='" + heatmapData + '\'' +
               ", maxDensity=" + maxDensity +
               ", minDensity=" + minDensity +
               '}';
    }
} 