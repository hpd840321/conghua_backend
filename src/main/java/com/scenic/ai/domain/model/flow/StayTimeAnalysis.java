package com.scenic.ai.domain.model.flow;

import lombok.Data;

import java.util.Map;

@Data
public class StayTimeAnalysis {
    private String areaId;
    private String areaName;
    private Double averageStayTime;
    private Map<String, Integer> stayTimeDistribution;
    private Map<String, LocationStayTime> locationStayTimes;
    
    @Data
    public static class LocationStayTime {
        private String locationName;
        private Double averageStayTime;
        private Integer visitorCount;
        private Map<String, Integer> timeDistribution;
    }
} 