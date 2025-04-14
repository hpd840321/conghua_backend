package com.scenic.ai.domain.model.visitor;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class VisitorStats {
    private String areaId;
    private String areaName;
    private LocalDateTime statisticsTime;
    private Integer totalCount;
    private Double averageStayTime;
    private Map<String, Integer> ageDistribution;
    private Map<String, Integer> genderDistribution;
    private Map<String, Integer> sourceDistribution;
    private Map<String, Double> stayTimeDistribution;
    private Map<String, Integer> peakHourDistribution;
} 