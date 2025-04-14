package com.scenic.ai.domain.model.visitor;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class PeopleCountData {
    private String areaId;
    private String areaName;
    private Integer currentCount;
    private Integer maxCapacity;
    private Double occupancyRate;
    private Map<String, Integer> entranceCount;
    private Map<String, Integer> exitCount;
    private Map<String, Double> densityDistribution;
    private LocalDateTime updateTime;
} 