package com.scenic.ai.common;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QueryParams {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String orderBy;
    private Boolean asc = true;
    
    private String keyword;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private String type;
    
    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }
    
    public String getOrderDirection() {
        return asc ? "ASC" : "DESC";
    }
} 