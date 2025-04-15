package com.scenic.ai.service;

import java.time.LocalDateTime;

/**
 * 统计服务接口
 */
public interface StatisticsService {
    /**
     * 计算平均值
     */
    Double calculateAverage(String id, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 查询最大值
     */
    Integer findMax(String id, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 计算同比增长率
     */
    Double calculateGrowthRate(String id, LocalDateTime compareTime);
    
    /**
     * 计算环比增长率
     */
    Double calculateChainGrowthRate(String id, LocalDateTime compareTime);
} 