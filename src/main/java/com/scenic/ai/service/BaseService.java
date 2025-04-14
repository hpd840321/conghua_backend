package com.scenic.ai.service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 基础服务接口
 * @param <T> 领域模型类型
 * @param <ID> ID类型
 */
public interface BaseService<T, ID> {
    /**
     * 保存单个实体
     */
    void save(T entity);
    
    /**
     * 批量保存实体
     */
    int batchSave(List<T> entities);
    
    /**
     * 根据时间范围查询
     */
    List<T> findByTimeRange(ID id, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 删除历史数据
     */
    int deleteHistoricalData(LocalDateTime time);
} 