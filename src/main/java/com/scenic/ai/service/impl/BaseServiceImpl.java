package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 基础服务实现类
 * @param <M> Mapper类型
 * @param <T> 实体类型
 */
@Transactional(rollbackFor = Exception.class)
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    private static final Logger log = LoggerFactory.getLogger(BaseServiceImpl.class);
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(T entity) {
        log.info("保存实体: {}", entity);
        return super.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        log.info("删除实体: id={}", id);
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteHistoricalData(LocalDateTime beforeTime) {
        log.info("删除历史数据: beforeTime={}", beforeTime);
        doDeleteHistoricalData(beforeTime);
    }

    @Override
    public List<T> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询时间范围内的记录: startTime={}, endTime={}", startTime, endTime);
        return doFindByTimeRange(startTime, endTime);
    }
    
    /**
     * 具体的历史数据删除实现
     * @param beforeTime 删除该时间之前的数据
     */
    protected abstract void doDeleteHistoricalData(LocalDateTime beforeTime);
    
    /**
     * 实际的时间范围查询操作
     */
    protected abstract List<T> doFindByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
} 