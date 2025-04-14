package com.scenic.ai.domain.model.flow;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlowAnalysis {
    private String areaId;
    private String areaName;
    private Integer currentFlow;
    private Integer maxCapacity;
    private Double occupancyRate;
    private LocalDateTime updateTime;
    
    @Data
    public static class FlowTrendPoint {
        private LocalDateTime timestamp;
        private Integer flow;
        private Double occupancyRate;
    }
    
    @Data
    public static class HeatMapPoint {
        private Double x;
        private Double y;
        private Integer value;
        private String locationName;
    }
    
    @Data
    public static class PathAnalysisData {
        private String fromLocation;
        private String toLocation;
        private Integer count;
        private Double averageDuration;
    }
    
    @Data
    public static class CongestionWarning {
        private String locationId;
        private String locationName;
        private Double occupancyRate;
        private String warningLevel;
        private LocalDateTime warningTime;
    }
    
    @Data
    public static class FlowForecast {
        private LocalDateTime timestamp;
        private Integer predictedFlow;
        private Double confidence;
    }
} 