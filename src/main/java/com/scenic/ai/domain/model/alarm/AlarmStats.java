package com.scenic.ai.domain.model.alarm;

import lombok.Data;

import java.util.Map;

@Data
public class AlarmStats {
    private Long totalCount;
    private Long unhandledCount;
    private Map<String, Long> typeDistribution;
    private Map<String, Long> levelDistribution;
    private Map<String, Long> areaDistribution;
} 