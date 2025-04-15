package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.model.CrowdStatistics;
import com.scenic.ai.mapper.CrowdStatisticsMapper;
import com.scenic.ai.service.CrowdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 人群统计服务实现类
 */
@Service
public class CrowdServiceImpl implements CrowdService {

    @Autowired
    private CrowdStatisticsMapper crowdStatisticsMapper;

    /**
     * 将 LocalDateTime 转换为 Date
     */
    private Date toDate(LocalDateTime localDateTime) {
        return localDateTime != null ? Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }

    @Override
    public IPage<CrowdStatistics> pageStatistics(Page<CrowdStatistics> page, String tourismName, String deviceCode, 
            String algName, LocalDateTime startTime, LocalDateTime endTime) {
        return crowdStatisticsMapper.selectPage(page, tourismName, deviceCode, toDate(startTime), toDate(endTime));
    }

    @Override
    public Map<String, Object> getOverview(String tourismName, String deviceCode, 
            LocalDateTime startTime, LocalDateTime endTime) {
        List<CrowdStatistics> statistics = crowdStatisticsMapper.selectByTourism(tourismName, toDate(startTime), toDate(endTime));
        return processStatistics(statistics);
    }

    @Override
    public Map<String, Object> getTrend(String tourismName, String deviceCode, 
            LocalDateTime startTime, LocalDateTime endTime) {
        List<Map<String, Object>> trendData = crowdStatisticsMapper.selectTrend(deviceCode, tourismName, toDate(startTime), toDate(endTime));
        Map<String, Object> result = new HashMap<>();
        result.put("trendData", trendData);
        return result;
    }

    @Override
    public Map<String, Object> getHeatmap(String tourismName, String deviceCode, 
            LocalDateTime startTime, LocalDateTime endTime) {
        List<Map<String, Object>> heatmapData = crowdStatisticsMapper.selectHighDensity(
            deviceCode, tourismName, toDate(startTime), toDate(endTime), null);
        Map<String, Object> result = new HashMap<>();
        result.put("heatmapData", heatmapData);
        return result;
    }

    @Override
    public Map<String, Object> processStatistics(List<CrowdStatistics> statistics) {
        Map<String, Object> result = new HashMap<>();
        
        if (statistics == null || statistics.isEmpty()) {
            result.put("currentCount", 0);
            result.put("avgCount", 0.0);
            result.put("maxCount", 0);
            result.put("totalCount", 0);
            result.put("heatMapData", List.of());
            return result;
        }
        
        // 当前人数（最新一条记录）
        result.put("currentCount", statistics.get(0).getCount());
        
        // 计算平均人数
        double avgCount = statistics.stream()
                .mapToInt(CrowdStatistics::getCount)
                .average()
                .orElse(0.0);
        result.put("avgCount", avgCount);
        
        // 计算最大人数
        int maxCount = statistics.stream()
                .mapToInt(CrowdStatistics::getCount)
                .max()
                .orElse(0);
        result.put("maxCount", maxCount);
        
        // 计算总人数
        int totalCount = statistics.stream()
                .mapToInt(CrowdStatistics::getCount)
                .sum();
        result.put("totalCount", totalCount);
        
        // 构建热力图数据
        List<Map<String, Object>> heatMapData = statistics.stream()
                .map(stat -> {
                    Map<String, Object> point = new HashMap<>();
                    point.put("x", stat.getDeviceCode());
                    point.put("y", stat.getDeviceName());
                    point.put("value", stat.getCount());
                    return point;
                })
                .collect(Collectors.toList());
        result.put("heatMapData", heatMapData);
        
        return result;
    }
} 