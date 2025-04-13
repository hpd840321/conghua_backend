package com.conghua.tourism.model.visitor;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 访客统计数据模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitorStats {
    
    /**
     * 区域ID
     */
    private String areaId;
    
    /**
     * 区域名称
     */
    private String areaName;
    
    /**
     * 实时访客数量
     */
    private int currentVisitorCount;
    
    /**
     * 今日累计访客数
     */
    private int todayTotalVisitors;
    
    /**
     * 平均停留时长(分钟)
     */
    private double avgStayDuration;
    
    /**
     * 高峰时段访客数
     */
    private int peakVisitorCount;
    
    /**
     * 高峰时段
     */
    private LocalDateTime peakTime;
    
    /**
     * 访客来源分布
     */
    private Map<String, Integer> sourceDistribution;
    
    /**
     * 年龄分布
     */
    private Map<String, Integer> ageDistribution;
    
    /**
     * 性别分布
     */
    private Map<String, Integer> genderDistribution;
    
    /**
     * 停留时长分布
     */
    private Map<String, Integer> stayDurationDistribution;
    
    /**
     * 热门景点排名
     */
    private Map<String, Integer> hotSpotRanking;
    
    /**
     * 统计时间
     */
    private LocalDateTime statsTime;
} 