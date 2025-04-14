package com.scenic.ai.service.flow;

import com.scenic.ai.domain.model.flow.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 流量分析服务接口
 */
public interface FlowAnalysisService {
    
    /**
     * 获取实时流量分析
     */
    FlowAnalysis getRealTimeAnalysis(String areaId);
    
    /**
     * 获取流量趋势
     */
    List<FlowAnalysis.FlowTrendPoint> getFlowTrend(String areaId, LocalDateTime startTime, LocalDateTime endTime, String interval);
    
    /**
     * 获取热力图数据
     */
    List<FlowAnalysis.HeatMapPoint> getHeatMapData(String areaId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取路径分析
     */
    List<FlowAnalysis.PathAnalysisData> getPathAnalysis(String areaId, LocalDateTime startTime, LocalDateTime endTime, int minCount);
    
    /**
     * 获取拥堵预警
     */
    List<FlowAnalysis.CongestionWarning> getCongestionWarning(String areaId, double threshold);
    
    /**
     * 获取流量预测
     */
    List<FlowAnalysis.FlowForecast> getFlowForecast(String areaId, LocalDateTime startTime);
    
    /**
     * 获取实时流量数据
     */
    FlowData getRealtimeFlow(String areaId);
    
    /**
     * 获取流量热力图
     */
    HeatmapData getFlowHeatmap(String areaId, String startTime, String endTime);
    
    /**
     * 获取路径分析数据
     */
    PathAnalysisData getPathAnalysis(String areaId, String startTime, String endTime);
    
    /**
     * 获取驻留时间分析
     */
    StayTimeAnalysis getStayTimeAnalysis(String areaId, String startTime, String endTime);
    
    /**
     * 获取区域流量对比
     */
    List<FlowComparison> getFlowComparison(List<String> areaIds, String startTime, String endTime);
    
    /**
     * 获取流量预测数据
     */
    FlowForecast getFlowForecast(String areaId, Integer forecastHours);
    
    /**
     * 导出流量分析报告
     */
    String exportFlowReport(String areaId, String startTime, String endTime, String reportType);
} 