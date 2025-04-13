package com.conghua.tourism.service.flow;

import com.conghua.tourism.model.flow.FlowAnalysis;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 流量分析服务接口
 */
public interface FlowAnalysisService {
    
    /**
     * 获取区域实时流量分析
     *
     * @param areaId 区域ID
     * @return 流量分析数据
     */
    FlowAnalysis getRealTimeAnalysis(String areaId);
    
    /**
     * 获取区域流量趋势
     *
     * @param areaId 区域ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param interval 时间间隔(如 %Y-%m-%d, %Y-%m-%d %H:00:00)
     * @return 趋势数据
     */
    List<FlowAnalysis.FlowTrendPoint> getFlowTrend(String areaId, LocalDateTime startTime, LocalDateTime endTime, String interval);
    
    /**
     * 获取区域热力图数据
     *
     * @param areaId 区域ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 热力图数据
     */
    List<FlowAnalysis.HeatMapPoint> getHeatMapData(String areaId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取区域路径分析
     *
     * @param areaId 区域ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param minCount 最小路径数量
     * @return 路径分析数据
     */
    List<FlowAnalysis.PathAnalysisData> getPathAnalysis(String areaId, LocalDateTime startTime, LocalDateTime endTime, int minCount);
    
    /**
     * 获取区域拥堵预警
     *
     * @param areaId 区域ID
     * @param threshold 拥堵阈值(0-1之间)
     * @return 拥堵预警数据
     */
    List<FlowAnalysis.CongestionWarning> getCongestionWarning(String areaId, double threshold);
    
    /**
     * 获取区域流量预测
     *
     * @param areaId 区域ID
     * @param startTime 预测开始时间
     * @return 流量预测数据
     */
    List<FlowAnalysis.FlowForecast> getFlowForecast(String areaId, LocalDateTime startTime);
} 