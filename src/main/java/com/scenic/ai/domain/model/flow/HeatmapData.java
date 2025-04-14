package com.scenic.ai.domain.model.flow;

import lombok.Data;

import java.util.List;

@Data
public class HeatmapData {
    private String areaId;
    private String areaName;
    private List<HeatPoint> points;
    private Double maxValue;
    private Double minValue;
    
    @Data
    public static class HeatPoint {
        private Double x;
        private Double y;
        private Double value;
        private String locationName;
    }
} 