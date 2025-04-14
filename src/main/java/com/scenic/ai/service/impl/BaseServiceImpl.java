package com.scenic.ai.service.impl;

import com.scenic.ai.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 基础服务实现类
 * @param <T> 领域模型类型
 * @param <ID> ID类型
 */
@Slf4j
public abstract class BaseServiceImpl<T, ID> implements BaseService<T, ID> {
    
    @Override
    @Transactional
    public void save(T entity) {
        log.info("保存实体: {}", entity);
        doSave(entity);
    }
    
    @Override
    @Transactional
    public int batchSave(List<T> entities) {
        if (entities == null || entities.isEmpty()) {
            return 0;
        }
        log.info("批量保存实体, 数量: {}", entities.size());
        return doBatchSave(entities);
    }
    
    @Override
    public List<T> findByTimeRange(ID id, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询时间范围内的记录: id={}, startTime={}, endTime={}", id, startTime, endTime);
        return doFindByTimeRange(id, startTime, endTime);
    }
    
    @Override
    @Transactional
    public int deleteHistoricalData(LocalDateTime time) {
        log.info("删除历史数据: time={}", time);
        return doDeleteHistoricalData(time);
    }
    
    /**
     * 实际的保存操作
     */
    protected abstract void doSave(T entity);
    
    /**
     * 实际的批量保存操作
     */
    protected abstract int doBatchSave(List<T> entities);
    
    /**
     * 实际的时间范围查询操作
     */
    protected abstract List<T> doFindByTimeRange(ID id, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 实际的历史数据删除操作
     */
    protected abstract int doDeleteHistoricalData(LocalDateTime time);
} 