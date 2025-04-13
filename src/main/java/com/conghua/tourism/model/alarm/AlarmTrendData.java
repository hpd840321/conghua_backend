package com.conghua.tourism.model.alarm;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 告警趋势数据类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmTrendData {
    
    /**
     * 时间点
     */
    private LocalDateTime timestamp;
    
    /**
     * 总告警数
     */
    private int total;
    
    /**
     * 新增告警数
     */
    private int newCount;
    
    /**
     * 处理告警数
     */
    private int handledCount;
    
    /**
     * 各级别告警数量
     */
    private Map<AlarmLevel, Integer> levelCounts;
    
    /**
     * 各类型告警数量
     */
    private Map<AlarmType, Integer> typeCounts;
    
    /**
     * 各状态告警数量
     */
    private Map<AlarmStatus, Integer> statusCounts;
    
    /**
     * 各区域告警数量
     */
    private Map<String, Integer> areaCounts;
    
    /**
     * 告警处理率
     */
    private double handleRate;
    
    /**
     * 平均处理时长（分钟）
     */
    private double avgHandleTime;
} 