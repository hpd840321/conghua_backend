package com.scenic.ai.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 告警分布信息领域模型
 */
@Data
@Builder
public class AlertDistribution {
    private List<String> labels;
    private List<Integer> values;
    private Integer total;
    
    public static AlertDistribution create(List<String> labels, List<Integer> values) {
        return AlertDistribution.builder()
                .labels(labels)
                .values(values)
                .total(values.stream().mapToInt(Integer::intValue).sum())
                .build();
    }
} 