package com.scenic.ai.domain.model.flow;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FlowComparison {
    private String areaId;
    private String areaName;
    private List<FlowPoint> flowData;
    private Statistics statistics;
    
    @Data
    public static class FlowPoint {
        private LocalDateTime timestamp;
        private Integer flow;
        private Double occupancyRate;
    }
    
    @Data
    public static class Statistics {
        private Integer maxFlow;
        private Integer minFlow;
        private Double averageFlow;
        private LocalDateTime peakTime;
        private LocalDateTime valleyTime;
    }
} 