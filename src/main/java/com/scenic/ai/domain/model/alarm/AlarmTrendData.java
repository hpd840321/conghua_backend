package com.scenic.ai.domain.model.alarm;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class AlarmTrendData {
    private LocalDateTime timestamp;
    private Long totalCount;
    private Map<String, Long> typeDistribution;
    private Map<String, Long> levelDistribution;
} 