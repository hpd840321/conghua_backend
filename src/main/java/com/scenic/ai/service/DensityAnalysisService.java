package com.scenic.ai.service;

import com.scenic.ai.domain.model.AlertDomain;
import com.scenic.ai.dto.DensityTrendDTO;
import com.scenic.ai.entity.DensityAnalysis;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 密度分析服务
 */
public interface DensityAnalysisService {

        /**
         * 查找超过阈值的密度记录并生成告警
         */
        List<AlertDomain> listExceedThresholdDensities();

        /**
         * 分析指定时间范围内的密度趋势
         *
         * @param areaId    区域ID
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @param interval  时间间隔(分钟)
         * @return 密度趋势数据列表
         */
        List<DensityTrendDTO> analyzeDensityTrend(String areaId, LocalDateTime startTime, LocalDateTime endTime,
                        Integer interval);

        /**
         * 根据条件查询密度分析记录
         *
         * @param areaId    区域ID
         * @param deviceId  设备ID
         * @param algName   算法名称
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 密度分析记录列表
         */
        List<DensityAnalysis> listByConditions(String areaId, String deviceId, String algName, LocalDateTime startTime,
                        LocalDateTime endTime);

        /**
         * 统计指定时间范围内的记录数
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 记录数
         */
        long countByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 获取密度分布统计
         *
         * @param areaId    区域ID
         * @param deviceId  设备ID
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 密度分布统计结果
         */
        Map<String, Object> getDensityDistribution(String areaId, String deviceId, LocalDateTime startTime,
                        LocalDateTime endTime);

        /**
         * 获取时间分布统计
         *
         * @param areaId    区域ID
         * @param deviceId  设备ID
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 时间分布统计结果
         */
        Map<String, Object> getTimeDistribution(String areaId, String deviceId, LocalDateTime startTime,
                        LocalDateTime endTime);
}