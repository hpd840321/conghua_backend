package com.scenic.ai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.model.CrowdStatistics;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 人群统计服务接口
 */
public interface CrowdService {
    
    /**
     * 分页查询人群统计数据
     */
    IPage<CrowdStatistics> pageStatistics(Page<CrowdStatistics> page, String tourismName, 
            String deviceCode, String algName, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取概览数据
     */
    Map<String, Object> getOverview(String tourismName, String deviceCode, 
            LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取趋势数据
     */
    Map<String, Object> getTrend(String tourismName, String deviceCode, 
            LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取热力图数据
     */
    Map<String, Object> getHeatmap(String tourismName, String deviceCode, 
            LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 处理统计数据
     */
    Map<String, Object> processStatistics(List<CrowdStatistics> statistics);
} 