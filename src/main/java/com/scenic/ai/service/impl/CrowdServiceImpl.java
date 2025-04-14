package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.mapper.CrowdStatisticsMapper;
import com.scenic.ai.domain.model.CrowdStatistics;
import com.scenic.ai.service.CrowdService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CrowdServiceImpl extends ServiceImpl<CrowdStatisticsMapper, CrowdStatistics> implements CrowdService {

    @Override
    public IPage<CrowdStatistics> pageStatistics(Page<CrowdStatistics> page, String tourismName, String deviceCode, 
            String algName, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<CrowdStatistics> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.isNotBlank(tourismName)) {
            wrapper.eq(CrowdStatistics::getTourismName, tourismName);
        }
        if (StringUtils.isNotBlank(deviceCode)) {
            wrapper.eq(CrowdStatistics::getDeviceCode, deviceCode);
        }
        if (StringUtils.isNotBlank(algName)) {
            wrapper.eq(CrowdStatistics::getAlgName, algName);
        }
        if (startTime != null) {
            wrapper.ge(CrowdStatistics::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(CrowdStatistics::getCreateTime, endTime);
        }
        
        wrapper.orderByDesc(CrowdStatistics::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public Map<String, Object> getOverview(String tourismName, String deviceCode, 
            LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<CrowdStatistics> wrapper = buildBaseWrapper(tourismName, deviceCode, startTime, endTime);
        
        List<CrowdStatistics> statistics = list(wrapper);
        Map<String, Object> result = new HashMap<>();
        
        // 计算当前人数（最新一条记录）
        if (!statistics.isEmpty()) {
            result.put("currentCount", statistics.get(0).getCount());
        }
        
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
        
        return result;
    }

    @Override
    public Map<String, Object> getTrend(String tourismName, String deviceCode, 
            LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<CrowdStatistics> wrapper = buildBaseWrapper(tourismName, deviceCode, startTime, endTime);
        wrapper.orderByAsc(CrowdStatistics::getCreateTime);
        
        List<CrowdStatistics> statistics = list(wrapper);
        Map<String, Object> result = new HashMap<>();
        
        List<String> timeList = statistics.stream()
                .map(stat -> stat.getCreateTime().toString())
                .collect(Collectors.toList());
        
        List<Integer> countList = statistics.stream()
                .map(CrowdStatistics::getCount)
                .collect(Collectors.toList());
        
        result.put("timeList", timeList);
        result.put("countList", countList);
        
        return result;
    }

    @Override
    public Map<String, Object> getHeatmap(String tourismName, String deviceCode, 
            LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<CrowdStatistics> wrapper = buildBaseWrapper(tourismName, deviceCode, startTime, endTime);
        
        List<CrowdStatistics> statistics = list(wrapper);
        Map<String, Object> result = new HashMap<>();
        
        List<Map<String, Object>> heatmapData = statistics.stream()
                .map(stat -> {
                    Map<String, Object> point = new HashMap<>();
                    point.put("deviceCode", stat.getDeviceCode());
                    point.put("deviceName", stat.getDeviceName());
                    point.put("count", stat.getCount());
                    return point;
                })
                .collect(Collectors.toList());
        
        result.put("heatmapData", heatmapData);
        
        return result;
    }

    @Override
    public Map<String, Object> processStatistics(List<CrowdStatistics> statistics) {
        // 当前人数（最新一条记录）
        int currentCount = statistics.isEmpty() ? 0 : statistics.get(0).getCount();
        
        // 计算平均人数
        double avgCount = statistics.stream()
                .mapToInt(CrowdStatistics::getCount)
                .average()
                .orElse(0.0);
        
        // 计算最大人数
        int maxCount = statistics.stream()
                .mapToInt(CrowdStatistics::getCount)
                .max()
                .orElse(0);
        
        // 计算总人数
        int totalCount = statistics.stream()
                .mapToInt(CrowdStatistics::getCount)
                .sum();
        
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
        
        Map<String, Object> result = new HashMap<>();
        result.put("currentCount", currentCount);
        result.put("avgCount", avgCount);
        result.put("maxCount", maxCount);
        result.put("totalCount", totalCount);
        result.put("heatMapData", heatMapData);
        
        return result;
    }

    private LambdaQueryWrapper<CrowdStatistics> buildBaseWrapper(String tourismName, String deviceCode, 
            LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<CrowdStatistics> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.isNotBlank(tourismName)) {
            wrapper.eq(CrowdStatistics::getTourismName, tourismName);
        }
        if (StringUtils.isNotBlank(deviceCode)) {
            wrapper.eq(CrowdStatistics::getDeviceCode, deviceCode);
        }
        if (startTime != null) {
            wrapper.ge(CrowdStatistics::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(CrowdStatistics::getCreateTime, endTime);
        }
        
        return wrapper;
    }
} 