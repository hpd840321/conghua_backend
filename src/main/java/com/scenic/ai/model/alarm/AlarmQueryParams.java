package com.scenic.ai.model.alarm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 告警查询参数类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmQueryParams {
    
    /**
     * 页码
     */
    private int pageNum = 1;
    
    /**
     * 每页大小
     */
    private int pageSize = 10;
    
    /**
     * 告警类型列表
     */
    private List<AlarmType> types;
    
    /**
     * 告警级别列表
     */
    private List<AlarmLevel> levels;
    
    /**
     * 告警状态列表
     */
    private List<AlarmStatus> statuses;
    
    /**
     * 区域ID列表
     */
    private List<String> areaIds;
    
    /**
     * 设备ID列表
     */
    private List<String> deviceIds;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 处理人
     */
    private String handler;
    
    /**
     * 关键词
     */
    private String keyword;
    
    /**
     * 排序字段
     */
    private String sortField;
    
    /**
     * 排序方向（asc/desc）
     */
    private String sortOrder;
} 