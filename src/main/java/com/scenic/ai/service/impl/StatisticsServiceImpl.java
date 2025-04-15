package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scenic.ai.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 统计服务实现类
 */
@Service
public abstract class StatisticsServiceImpl extends BaseServiceImpl<BaseMapper<Object>, Object> implements StatisticsService {
    
    private static final Logger log = LoggerFactory.getLogger(StatisticsServiceImpl.class);
    
    @Override
    public Double calculateAverage(String id, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("计算平均值: id={}, startTime={}, endTime={}", id, startTime, endTime);
        return doCalculateAverage(id, startTime, endTime);
    }
    
    @Override
    public Integer findMax(String id, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询最大值: id={}, startTime={}, endTime={}", id, startTime, endTime);
        return doFindMax(id, startTime, endTime);
    }
    
    @Override
    public Double calculateGrowthRate(String id, LocalDateTime compareTime) {
        log.info("计算同比增长率: id={}, compareTime={}", id, compareTime);
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime lastYear = compareTime.minus(1, ChronoUnit.YEARS);
        return doCalculateGrowthRate(id, currentTime, lastYear);
    }
    
    @Override
    public Double calculateChainGrowthRate(String id, LocalDateTime compareTime) {
        log.info("计算环比增长率: id={}, compareTime={}", id, compareTime);
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime lastPeriod = compareTime.minus(1, ChronoUnit.MONTHS);
        return doCalculateGrowthRate(id, currentTime, lastPeriod);
    }
    
    /**
     * 实际的平均值计算操作
     */
    protected abstract Double doCalculateAverage(String id, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 实际的最大值查询操作
     */
    protected abstract Integer doFindMax(String id, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 实际的增长率计算操作
     */
    protected abstract Double doCalculateGrowthRate(String id, LocalDateTime currentTime, LocalDateTime compareTime);
} 