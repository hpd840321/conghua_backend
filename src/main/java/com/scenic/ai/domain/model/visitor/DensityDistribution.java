package com.scenic.ai.domain.model.visitor;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DensityDistribution {
    private String areaId;
    private String areaName;
    private List<DensityPoint> points;
    private Double maxDensity;
    private Double minDensity;
    private LocalDateTime updateTime;
    
    @Data
    public static class DensityPoint {
        private Double x;
        private Double y;
        private Double density;
        private String locationName;
        private String warningLevel;
    }
} 