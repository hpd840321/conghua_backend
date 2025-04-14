package com.scenic.ai.domain.model.visitor;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrendData {
    private LocalDateTime timestamp;
    private Integer count;
    private Double occupancyRate;
    private Integer entranceFlow;
    private Integer exitFlow;
} 