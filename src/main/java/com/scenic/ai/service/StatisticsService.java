package com.scenic.ai.service;

import java.time.LocalDateTime;

/**
 * 统计服务接口
 * @param <T> 领域模型类型
 * @param <ID> ID类型
 */
public interface StatisticsService<T, ID> extends BaseService<T, ID> {
    /**
     * 计算平均值
     */
    Double calculateAverage(ID id, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 查询最大值
     */
    Integer findMax(ID id, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 计算同比增长率
     */
    Double calculateGrowthRate(ID id, LocalDateTime compareTime);
    
    /**
     * 计算环比增长率
     */
    Double calculateChainGrowthRate(ID id, LocalDateTime compareTime);
} 