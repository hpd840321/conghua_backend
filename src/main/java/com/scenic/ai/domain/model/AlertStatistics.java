package com.scenic.ai.domain.model;

import java.util.Map;
import java.util.Objects;

/**
 * 告警统计信息领域模型
 */
public class AlertStatistics {
    private Integer totalCount;
    private Integer handledCount;
    private Integer pendingCount;
    private Map<String, Integer> typeDistribution;
    private Map<String, Integer> timeDistribution;
    
    public AlertStatistics() {
    }
    
    public AlertStatistics(Integer totalCount, Integer handledCount, Integer pendingCount, 
                          Map<String, Integer> typeDistribution, Map<String, Integer> timeDistribution) {
        this.totalCount = totalCount;
        this.handledCount = handledCount;
        this.pendingCount = pendingCount;
        this.typeDistribution = typeDistribution;
        this.timeDistribution = timeDistribution;
    }
    
    public Integer getTotalCount() {
        return totalCount;
    }
    
    public AlertStatistics setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        return this;
    }
    
    public Integer getHandledCount() {
        return handledCount;
    }
    
    public AlertStatistics setHandledCount(Integer handledCount) {
        this.handledCount = handledCount;
        return this;
    }
    
    public Integer getPendingCount() {
        return pendingCount;
    }
    
    public AlertStatistics setPendingCount(Integer pendingCount) {
        this.pendingCount = pendingCount;
        return this;
    }
    
    public Map<String, Integer> getTypeDistribution() {
        return typeDistribution;
    }
    
    public void setTypeDistribution(Map<String, Integer> typeDistribution) {
        this.typeDistribution = typeDistribution;
    }
    
    public Map<String, Integer> getTimeDistribution() {
        return timeDistribution;
    }
    
    public void setTimeDistribution(Map<String, Integer> timeDistribution) {
        this.timeDistribution = timeDistribution;
    }
    
    public void validate() {
        Objects.requireNonNull(totalCount, "总数不能为空");
        Objects.requireNonNull(handledCount, "已处理数不能为空");
        Objects.requireNonNull(pendingCount, "待处理数不能为空");
        if (totalCount < 0 || handledCount < 0 || pendingCount < 0) {
            throw new IllegalArgumentException("计数不能为负数");
        }
        if (totalCount != (handledCount + pendingCount)) {
            throw new IllegalArgumentException("总数必须等于已处理数与待处理数之和");
        }
    }
    
    public static AlertStatistics create(Map<String, Integer> statistics) {
        AlertStatistics alertStatistics = new AlertStatistics();
        alertStatistics.setTotalCount(statistics.get("total"));
        alertStatistics.setHandledCount(statistics.get("handled"));
        alertStatistics.setPendingCount(statistics.get("pending"));
        alertStatistics.validate();
        return alertStatistics;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlertStatistics that = (AlertStatistics) o;
        return Objects.equals(totalCount, that.totalCount) &&
               Objects.equals(handledCount, that.handledCount) &&
               Objects.equals(pendingCount, that.pendingCount) &&
               Objects.equals(typeDistribution, that.typeDistribution) &&
               Objects.equals(timeDistribution, that.timeDistribution);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(totalCount, handledCount, pendingCount, typeDistribution, timeDistribution);
    }
    
    @Override
    public String toString() {
        return "AlertStatistics{" +
               "totalCount=" + totalCount +
               ", handledCount=" + handledCount +
               ", pendingCount=" + pendingCount +
               ", typeDistribution=" + typeDistribution +
               ", timeDistribution=" + timeDistribution +
               '}';
    }
} 