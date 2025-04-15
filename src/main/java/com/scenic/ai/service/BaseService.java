package com.scenic.ai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 基础服务接口
 * @param <T> 实体类型
 */
public interface BaseService<T> extends IService<T> {
    /**
     * 根据时间范围查询
     */
    List<T> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 删除历史数据
     */
    void deleteHistoricalData(LocalDateTime time);
} 