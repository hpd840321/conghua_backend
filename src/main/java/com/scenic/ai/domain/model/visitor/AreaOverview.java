package com.scenic.ai.domain.model.visitor;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class AreaOverview {
    private String areaId;
    private String areaName;
    private Integer currentCount;
    private Integer todayTotal;
    private Integer weeklyTotal;
    private Integer monthlyTotal;
    private Double averageStayTime;
    private Map<String, Integer> peakHours;
    private Map<String, Double> visitorDistribution;
    private LocalDateTime updateTime;
} 