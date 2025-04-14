package com.scenic.ai.service.impl;

import com.scenic.ai.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 统计服务实现类
 * @param <T> 领域模型类型
 * @param <ID> ID类型
 */
@Slf4j
public abstract class StatisticsServiceImpl<T, ID> extends BaseServiceImpl<T, ID> implements StatisticsService<T, ID> {
    
    @Override
    public Double calculateAverage(ID id, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("计算平均值: id={}, startTime={}, endTime={}", id, startTime, endTime);
        return doCalculateAverage(id, startTime, endTime);
    }
    
    @Override
    public Integer findMax(ID id, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询最大值: id={}, startTime={}, endTime={}", id, startTime, endTime);
        return doFindMax(id, startTime, endTime);
    }
    
    @Override
    public Double calculateGrowthRate(ID id, LocalDateTime compareTime) {
        log.info("计算同比增长率: id={}, compareTime={}", id, compareTime);
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime lastYear = compareTime.minus(1, ChronoUnit.YEARS);
        return doCalculateGrowthRate(id, currentTime, lastYear);
    }
    
    @Override
    public Double calculateChainGrowthRate(ID id, LocalDateTime compareTime) {
        log.info("计算环比增长率: id={}, compareTime={}", id, compareTime);
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime lastPeriod = compareTime.minus(1, ChronoUnit.MONTHS);
        return doCalculateGrowthRate(id, currentTime, lastPeriod);
    }
    
    /**
     * 实际的平均值计算操作
     */
    protected abstract Double doCalculateAverage(ID id, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 实际的最大值查询操作
     */
    protected abstract Integer doFindMax(ID id, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 实际的增长率计算操作
     */
    protected abstract Double doCalculateGrowthRate(ID id, LocalDateTime currentTime, LocalDateTime compareTime);
} 