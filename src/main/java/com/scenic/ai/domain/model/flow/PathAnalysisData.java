package com.scenic.ai.domain.model.flow;

import lombok.Data;

import java.util.List;

@Data
public class PathAnalysisData {
    private String areaId;
    private String areaName;
    private List<PathSegment> paths;
    private Integer totalVisitors;
    
    @Data
    public static class PathSegment {
        private String fromLocation;
        private String toLocation;
        private Integer visitorCount;
        private Double averageDuration;
        private Double percentage;
    }
} 