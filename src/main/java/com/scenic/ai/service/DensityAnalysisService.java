package com.scenic.ai.service;

import com.scenic.ai.domain.model.Alert;
import com.scenic.ai.dto.DensityTrendDTO;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 密度分析服务
 */
public interface DensityAnalysisService {
    
    /**
     * 查找超过阈值的密度记录并生成告警
     */
    List<Alert> findExceedThresholdDensities();

    /**
     * 分析指定时间范围内的密度趋势
     *
     * @param areaId 区域ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param interval 时间间隔(分钟)
     * @return 密度趋势数据列表
     */
    List<DensityTrendDTO> analyzeDensityTrend(String areaId, LocalDateTime startTime, LocalDateTime endTime, Integer interval);
} 