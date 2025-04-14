package com.scenic.ai.model.alarm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 告警统计数据类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmStats {
    
    /**
     * 总告警数
     */
    private int total;
    
    /**
     * 待处理告警数
     */
    private int pending;
    
    /**
     * 处理中告警数
     */
    private int processing;
    
    /**
     * 已解决告警数
     */
    private int resolved;
    
    /**
     * 已忽略告警数
     */
    private int ignored;
    
    /**
     * 自动解决告警数
     */
    private int autoResolved;
    
    /**
     * 各级别告警数量
     */
    private Map<AlarmLevel, Integer> levelCounts;
    
    /**
     * 各类型告警数量
     */
    private Map<AlarmType, Integer> typeCounts;
    
    /**
     * 各区域告警数量
     */
    private Map<String, Integer> areaCounts;
    
    /**
     * 今日新增告警数
     */
    private int todayNew;
    
    /**
     * 今日处理告警数
     */
    private int todayHandled;
    
    /**
     * 告警处理率
     */
    private double handleRate;
    
    /**
     * 平均处理时长（分钟）
     */
    private double avgHandleTime;
} 