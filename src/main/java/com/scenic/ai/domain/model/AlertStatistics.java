package com.scenic.ai.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 告警统计信息领域模型
 */
@Data
@Builder
public class AlertStatistics {
    private Integer totalCount;
    private Integer handledCount;
    private Integer pendingCount;
    private Map<String, Integer> typeDistribution;
    private Map<String, Integer> timeDistribution;
    
    public static AlertStatistics create(Map<String, Integer> statistics) {
        return AlertStatistics.builder()
                .totalCount(statistics.get("total"))
                .handledCount(statistics.get("handled"))
                .pendingCount(statistics.get("pending"))
                .build();
    }
    
    public void setTypeDistribution(Map<String, Integer> typeDistribution) {
        this.typeDistribution = typeDistribution;
    }
    
    public void setTimeDistribution(Map<String, Integer> timeDistribution) {
        this.timeDistribution = timeDistribution;
    }
} 