package com.scenic.ai.domain.model.visitor;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QueryParams {
    private String areaId;
    private String timeRange;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String groupBy;
    private String sortBy;
    private Boolean ascending;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    
    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }
} 