package com.scenic.ai.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 全景图查询请求
 */
@Data
public class PanoramaQueryRequest {
    /**
     * 关键词搜索
     */
    private String keyword;
    
    /**
     * 设备编码
     */
    private String deviceCode;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 分页大小
     */
    private Integer pageSize = 10;
    
    /**
     * 当前页码
     */
    private Integer current = 1;
} 