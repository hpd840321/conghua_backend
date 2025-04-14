package com.scenic.ai.dto.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class PanoramaQueryParams {
    private String keyword;
    private String deviceCode;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    
    private Integer pageSize = 10;
    private Integer current = 1;
} 