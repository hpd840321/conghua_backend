package com.scenic.ai.service.visitor;

import com.scenic.ai.common.PageResult;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 访客统计服务接口
 * 
 * 该接口定义了景区访客统计相关的核心业务功能，包括：
 * 1. 实时访客数量监控
 * 2. 访客流量趋势分析
 * 3. 区域概览数据统计
 * 4. 区域密度分布分析
 * 5. 历史数据查询和统计
 *
 * 所有方法返回Map<String, Object>格式数据，确保数据结构的灵活性和可扩展性
 * 支持多区域并行统计，通过areaId参数区分不同区域
 */
public interface VisitorStatisticsService {
    
    /**
     * 获取指定区域的实时访客数量统计
     * 
     * @param areaId 区域ID
     * @return 包含以下数据的Map：
     *         - count: 当前访客数量
     *         - density: 当前密度
     *         - updateTime: 数据更新时间
     *         - areaId: 区域ID
     */
    Map<String, Object> getRealtimeCount(String areaId);
    
    /**
     * 获取指定时间范围内的访客趋势数据
     * 
     * @param areaId 区域ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param interval 统计时间间隔，支持：hourly（按小时）、daily（按天）、weekly（按周）、monthly（按月）
     * @return 包含以下数据的Map：
     *         - trends: 趋势数据列表，每个元素包含time、count、density
     *         - averageCount: 平均访客数
     *         - maxCount: 最大访客数
     *         - startTime: 统计开始时间
     *         - endTime: 统计结束时间
     *         - interval: 统计间隔
     */
    Map<String, Object> getTrends(String areaId, LocalDateTime startTime, LocalDateTime endTime, String interval);
    
    /**
     * 获取区域概览数据，包含当日的主要统计指标
     * 
     * @param areaId 区域ID
     * @return 包含以下数据的Map：
     *         - currentCount: 当前访客数量
     *         - todayAverage: 今日平均访客数
     *         - todayMax: 今日最大访客数
     *         - currentDensity: 当前密度
     *         - updateTime: 数据更新时间
     *         - areaId: 区域ID
     */
    Map<String, Object> getAreaOverview(String areaId);
    
    /**
     * 获取区域密度分布数据，包含实时密度和高密度区域信息
     * 
     * @param areaId 区域ID
     * @return 包含以下数据的Map：
     *         - currentDensity: 当前密度
     *         - panoramicImage: 全景图片URL
     *         - updateTime: 数据更新时间
     *         - count: 当前访客数量
     *         - highDensityAreas: 高密度区域列表，每个元素包含areaId、count、density、time
     */
    Map<String, Object> getDensityDistribution(String areaId);
    
    /**
     * 获取历史统计数据，支持分页查询
     * 
     * @param areaId 区域ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNum 页码，从1开始
     * @param pageSize 每页记录数
     * @return 分页结果，包含以下数据：
     *         - total: 总记录数
     *         - records: 记录列表，每个记录包含count、density、time、panoramicImage、areaId
     */
    PageResult<Map<String, Object>> getHistoryStats(String areaId, LocalDateTime startTime, LocalDateTime endTime, 
                                                   Integer pageNum, Integer pageSize);
} 