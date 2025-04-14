package com.scenic.ai.domain.model.flow;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class FlowData {
    private String areaId;
    private String areaName;
    private Integer currentFlow;
    private Integer maxCapacity;
    private Double occupancyRate;
    private Map<String, Integer> entranceFlow;
    private Map<String, Integer> exitFlow;
    private Map<String, Double> densityDistribution;
    private LocalDateTime updateTime;
} 