package com.scenic.ai.service.impl;

import com.scenic.ai.domain.model.Alert;
import com.scenic.ai.dto.DensityTrendDTO;
import com.scenic.ai.service.DensityAnalysisService;
import com.scenic.ai.service.ThirdPartyApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DensityAnalysisServiceImpl implements DensityAnalysisService {

    private final ThirdPartyApiService thirdPartyApiService;
    
    @Value("${density.threshold:0.75}")
    private double densityThreshold;

    @Override
    public List<Alert> findExceedThresholdDensities() {
        log.info("开始检查密度阈值");
        List<Alert> alerts = new ArrayList<>();
        
        try {
            // 获取最近5分钟的密度数据
            LocalDateTime endTime = LocalDateTime.now();
            LocalDateTime startTime = endTime.minusMinutes(5);
            
            // 复用ThirdPartyApiService获取密度数据
            List<Map<String, Object>> densityData = thirdPartyApiService.getDetailedData(
                null, // deviceCode，null表示所有设备
                "density", // 密度分析算法类型
                startTime,
                endTime
            );
            
            // 处理密度数据
            for (Map<String, Object> data : densityData) {
                Double density = (Double) data.get("value");
                if (density != null && density > densityThreshold) {
                    alerts.add(createAlert(data));
                }
            }
            
            log.info("密度检查完成，发现{}个超阈值记录", alerts.size());
            return alerts;
        } catch (Exception e) {
            log.error("密度检查失败", e);
            return alerts;
        }
    }

    @Override
    public List<DensityTrendDTO> analyzeDensityTrend(String areaId, LocalDateTime startTime, LocalDateTime endTime, Integer interval) {
        log.info("开始分析区域[{}]的密度趋势, 时间范围: {} - {}, 间隔: {}分钟", areaId, startTime, endTime, interval);
        
        try {
            // 获取指定时间范围的密度数据
            List<Map<String, Object>> densityData = thirdPartyApiService.getDetailedData(
                areaId,
                "density",
                startTime,
                endTime
            );
            
            // 转换为DTO对象
            List<DensityTrendDTO> trendData = densityData.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            
            log.info("密度趋势分析完成，共获取到{}条数据", trendData.size());
            return trendData;
        } catch (Exception e) {
            log.error("密度趋势分析失败", e);
            return new ArrayList<>();
        }
    }
    
    /**
     * 创建告警对象
     */
    private Alert createAlert(Map<String, Object> data) {
        return Alert.createWithValue(
            "DENSITY",
            "HIGH",
            (String) data.get("deviceCode"),
            (String) data.get("deviceName"),
            (String) data.get("tourismName"),
            String.format("区域密度超过阈值: %s", data.get("value")),
            ((Number) data.get("value")).doubleValue()
        );
    }
    
    /**
     * 将Map数据转换为DTO对象
     */
    private DensityTrendDTO convertToDTO(Map<String, Object> data) {
        DensityTrendDTO dto = new DensityTrendDTO();
        dto.setAreaId((String) data.get("deviceCode"));
        dto.setAreaName((String) data.get("deviceName"));
        dto.setDensity(((Number) data.get("value")).doubleValue());
        dto.setTimestamp(LocalDateTime.parse((String) data.get("timestamp")));
        dto.setThreshold(densityThreshold);
        
        // 根据密度值设置等级
        double density = dto.getDensity();
        if (density >= densityThreshold) {
            dto.setLevel("HIGH");
        } else if (density >= densityThreshold * 0.7) {
            dto.setLevel("MEDIUM");
        } else {
            dto.setLevel("LOW");
        }
        
        return dto;
    }
} 