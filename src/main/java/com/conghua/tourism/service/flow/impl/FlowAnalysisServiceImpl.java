package com.conghua.tourism.service.flow.impl;

import com.conghua.tourism.service.flow.FlowAnalysisService;
import com.conghua.tourism.model.flow.FlowAnalysis;
import com.conghua.tourism.mapper.flow.FlowAnalysisMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 流量分析服务实现类
 */
@Service
public class FlowAnalysisServiceImpl implements FlowAnalysisService {

    @Autowired
    private FlowAnalysisMapper flowAnalysisMapper;
    
    @Override
    public FlowAnalysis getRealTimeAnalysis(String areaId) {
        LocalDateTime now = LocalDateTime.now();
        
        // 构建分析数据
        return FlowAnalysis.builder()
            .areaId(areaId)
            .currentFlow(flowAnalysisMapper.getCurrentFlow(now, areaId))
            .flowTrend(getFlowTrend(areaId, now.minusHours(24), now, "%Y-%m-%d %H:00:00"))
            .heatMap(getHeatMapData(areaId, now.minusHours(1), now))
            .pathAnalysis(getPathAnalysis(areaId, now.minusHours(24), now, 10))
            .congestionWarnings(getCongestionWarning(areaId, 0.7))
            .flowForecasts(getFlowForecast(areaId, now))
            .analysisTime(now)
            .build();
    }
    
    @Override
    public List<FlowAnalysis.FlowTrendPoint> getFlowTrend(String areaId, LocalDateTime startTime, LocalDateTime endTime, String interval) {
        return flowAnalysisMapper.getFlowTrend(startTime, endTime, areaId, interval)
            .stream()
            .map(m -> FlowAnalysis.FlowTrendPoint.builder()
                .timestamp(LocalDateTime.parse(m.get("timestamp").toString()))
                .avgFlow(((Number)m.get("avg_flow")).intValue())
                .maxFlow(((Number)m.get("max_flow")).intValue())
                .minFlow(((Number)m.get("min_flow")).intValue())
                .build())
            .collect(Collectors.toList());
    }
    
    @Override
    public List<FlowAnalysis.HeatMapPoint> getHeatMapData(String areaId, LocalDateTime startTime, LocalDateTime endTime) {
        return flowAnalysisMapper.getHeatMapData(startTime, endTime, areaId)
            .stream()
            .map(m -> FlowAnalysis.HeatMapPoint.builder()
                .locationX(((Number)m.get("location_x")).doubleValue())
                .locationY(((Number)m.get("location_y")).doubleValue())
                .heatValue(((Number)m.get("heat_value")).intValue())
                .build())
            .collect(Collectors.toList());
    }
    
    @Override
    public List<FlowAnalysis.PathAnalysisData> getPathAnalysis(String areaId, LocalDateTime startTime, LocalDateTime endTime, int minCount) {
        return flowAnalysisMapper.getPathAnalysis(startTime, endTime, areaId, minCount)
            .stream()
            .map(m -> FlowAnalysis.PathAnalysisData.builder()
                .fromSpotId(m.get("from_spot_id").toString())
                .toSpotId(m.get("to_spot_id").toString())
                .pathCount(((Number)m.get("path_count")).intValue())
                .build())
            .collect(Collectors.toList());
    }
    
    @Override
    public List<FlowAnalysis.CongestionWarning> getCongestionWarning(String areaId, double threshold) {
        return flowAnalysisMapper.getCongestionWarning(LocalDateTime.now(), areaId, threshold)
            .stream()
            .map(m -> FlowAnalysis.CongestionWarning.builder()
                .spotId(m.get("spot_id").toString())
                .currentFlow(((Number)m.get("current_flow")).intValue())
                .maxCapacity(((Number)m.get("max_capacity")).intValue())
                .congestionRate(((Number)m.get("congestion_rate")).doubleValue())
                .congestionLevel(m.get("congestion_level").toString())
                .build())
            .collect(Collectors.toList());
    }
    
    @Override
    public List<FlowAnalysis.FlowForecast> getFlowForecast(String areaId, LocalDateTime startTime) {
        return flowAnalysisMapper.getFlowForecast(startTime, areaId)
            .stream()
            .map(m -> FlowAnalysis.FlowForecast.builder()
                .forecastTime(LocalDateTime.parse(m.get("forecast_time").toString()))
                .predictedFlow(((Number)m.get("predicted_flow")).intValue())
                .confidenceLevel(((Number)m.get("confidence_level")).doubleValue())
                .build())
            .collect(Collectors.toList());
    }
} 