package com.scenic.ai.domain.model.flow;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FlowForecast {
    private String areaId;
    private String areaName;
    private List<ForecastPoint> predictions;
    private ForecastMetrics metrics;
    
    @Data
    public static class ForecastPoint {
        private LocalDateTime timestamp;
        private Integer predictedFlow;
        private Double confidence;
        private Double upperBound;
        private Double lowerBound;
    }
    
    @Data
    public static class ForecastMetrics {
        private Double accuracy;
        private Double mape;
        private Double rmse;
        private String modelVersion;
    }
} 