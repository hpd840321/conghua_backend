package com.scenic.ai.service.visitor.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scenic.ai.common.PageResult;
import com.scenic.ai.domain.model.CrowdCount;
import com.scenic.ai.domain.model.visitor.MonitoringConfig;
import com.scenic.ai.domain.model.visitor.QueryParams;
import com.scenic.ai.mapper.CrowdCountMapper;
import com.scenic.ai.service.visitor.VisitorStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitorStatisticsServiceImpl implements VisitorStatisticsService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final CrowdCountMapper crowdCountMapper;

    @Override
    public Map<String, Object> getRealtimeCount(String areaId) {
        CrowdCount latestCount = crowdCountMapper.findLatestCount(areaId);
        Map<String, Object> result = new HashMap<>();
        if (latestCount != null) {
            result.put("count", latestCount.getCount());
            result.put("density", latestCount.getDensity());
            result.put("updateTime", latestCount.getCountTime());
            result.put("areaId", latestCount.getAreaId());
        }
        return result;
    }

    @Override
    public Map<String, Object> getTrends(String areaId, LocalDateTime startTime, LocalDateTime endTime, String interval) {
        List<CrowdCount> trends = crowdCountMapper.analyzeTrend(areaId, startTime, endTime);
        Double avgCount = crowdCountMapper.calculateAverageCount(areaId, startTime, endTime);
        Integer maxCount = crowdCountMapper.findMaxCount(areaId, startTime, endTime);
        
        Map<String, Object> result = new HashMap<>();
        result.put("trends", trends.stream().map(this::convertToTrendData).collect(Collectors.toList()));
        result.put("averageCount", avgCount != null ? avgCount : 0);
        result.put("maxCount", maxCount != null ? maxCount : 0);
        result.put("startTime", startTime);
        result.put("endTime", endTime);
        result.put("interval", interval);
        return result;
    }

    @Override
    public Map<String, Object> getAreaOverview(String areaId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dayStart = now.toLocalDate().atStartOfDay();
        
        CrowdCount currentCount = crowdCountMapper.findLatestCount(areaId);
        Double todayAvg = crowdCountMapper.calculateAverageCount(areaId, dayStart, now);
        Integer todayMax = crowdCountMapper.findMaxCount(areaId, dayStart, now);
        Double currentDensity = crowdCountMapper.calculateDensity(areaId, now);
        
        Map<String, Object> result = new HashMap<>();
        result.put("currentCount", currentCount != null ? currentCount.getCount() : 0);
        result.put("todayAverage", todayAvg != null ? todayAvg : 0);
        result.put("todayMax", todayMax != null ? todayMax : 0);
        result.put("currentDensity", currentDensity != null ? currentDensity : 0);
        result.put("updateTime", currentCount != null ? currentCount.getCountTime() : now);
        result.put("areaId", areaId);
        return result;
    }

    @Override
    public Map<String, Object> getDensityDistribution(String areaId) {
        LocalDateTime now = LocalDateTime.now();
        Map<String, Object> result = new HashMap<>();
        
        CrowdCount currentCount = crowdCountMapper.findLatestCount(areaId);
        if (currentCount != null) {
            result.put("currentDensity", currentCount.getDensity());
            result.put("panoramicImage", currentCount.getPanoramicImage());
            result.put("updateTime", currentCount.getCountTime());
            result.put("count", currentCount.getCount());
        }
        
        // 获取高密度区域（人流量超过100的区域）
        List<CrowdCount> highDensityAreas = crowdCountMapper.findHighDensityAreas(
            areaId, 100, now.minusHours(1), now);
            
        result.put("highDensityAreas", highDensityAreas.stream()
            .map(area -> {
                Map<String, Object> areaMap = new HashMap<>();
                areaMap.put("areaId", area.getAreaId());
                areaMap.put("count", area.getCount());
                areaMap.put("density", area.getDensity());
                areaMap.put("time", area.getCountTime());
                return areaMap;
            })
            .collect(Collectors.toList()));
        
        return result;
    }

    @Override
    @Transactional
    public void updateMonitoringConfig(String areaId, MonitoringConfig config) {
        // TODO: 实现配置更新逻辑
    }

    @Override
    public PageResult<Map<String, Object>> getHistoryStats(String areaId, LocalDateTime startTime, 
                                                         LocalDateTime endTime, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CrowdCount> records = crowdCountMapper.findByTimeRange(areaId, startTime, endTime);
        PageInfo<CrowdCount> pageInfo = new PageInfo<>(records);
        
        List<Map<String, Object>> resultList = records.stream()
            .map(record -> {
                Map<String, Object> map = new HashMap<>();
                map.put("count", record.getCount());
                map.put("density", record.getDensity());
                map.put("time", record.getCountTime());
                map.put("panoramicImage", record.getPanoramicImage());
                map.put("areaId", record.getAreaId());
                return map;
            })
            .collect(Collectors.toList());
            
        return PageResult.of(pageInfo.getTotal(), resultList);
    }

    @Override
    public String exportStats(QueryParams queryParams) {
        // TODO: 实现统计报表导出逻辑
        return "reports/visitor_stats_" + LocalDateTime.now().format(DATE_TIME_FORMATTER) + ".xlsx";
    }
    
    private Map<String, Object> convertToTrendData(CrowdCount crowdCount) {
        Map<String, Object> trendData = new HashMap<>();
        trendData.put("time", crowdCount.getCountTime());
        trendData.put("count", crowdCount.getCount());
        trendData.put("density", crowdCount.getDensity());
        return trendData;
    }
} 