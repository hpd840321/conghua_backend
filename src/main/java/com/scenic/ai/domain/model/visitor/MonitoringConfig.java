package com.scenic.ai.domain.model.visitor;

import lombok.Data;

import java.util.Map;

@Data
public class MonitoringConfig {
    private String areaId;
    private Integer maxCapacity;
    private Double crowdedThreshold;
    private Map<String, Integer> areaCapacities;
    private Map<String, Double> alertThresholds;
    private Boolean enableAlert;
    private Integer alertInterval;
    private String alertMethod;
} 