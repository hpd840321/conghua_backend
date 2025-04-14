package com.scenic.ai.service.impl;

import com.scenic.ai.domain.model.AlertDomain;
import com.scenic.ai.domain.model.CrowdCount;
import com.scenic.ai.dto.DensityTrendDTO;
import com.scenic.ai.mapper.CrowdCountMapper;
import com.scenic.ai.service.DensityAnalysisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 密度分析服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DensityAnalysisServiceImpl implements DensityAnalysisService {

    private static final double DENSITY_THRESHOLD = 0.8;
    private final CrowdCountMapper crowdCountMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AlertDomain> findExceedThresholdDensities() {
        List<CrowdCount> highDensityAreas = crowdCountMapper.findHighDensityAreas(null, 0, 
            LocalDateTime.now().minusHours(1), LocalDateTime.now());
            
        return highDensityAreas.stream()
            .filter(area -> area.getDensity() > DENSITY_THRESHOLD)
            .map(area -> AlertDomain.createWithValue(
                "DENSITY",  // 告警类型
                area.getDensity() > DENSITY_THRESHOLD * 1.5 ? "HIGH" : "MEDIUM",  // 告警级别
                area.getDeviceCode(),
                area.getDeviceName(),
                area.getTourismName(),
                String.format("区域人群密度超过阈值：%.2f", area.getDensity()),
                area.getDensity()
            ))
            .collect(Collectors.toList());
    }

    @Override
    public List<DensityTrendDTO> analyzeDensityTrend(String areaId, LocalDateTime startTime,
                                                    LocalDateTime endTime, Integer interval) {
        List<CrowdCount> records = crowdCountMapper.findByTimeRange(areaId, startTime, endTime);
        
        return records.stream()
            .map(record -> DensityTrendDTO.builder()
                .deviceCode(record.getDeviceCode())
                .deviceName(record.getDeviceName())
                .density(record.getDensity())
                .recordTime(record.getRecordTime())
                .build())
            .collect(Collectors.toList());
    }
} 