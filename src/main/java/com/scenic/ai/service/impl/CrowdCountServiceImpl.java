package com.scenic.ai.service.impl;

import com.scenic.ai.domain.model.CrowdCount;
import com.scenic.ai.mapper.CrowdCountMapper;
import com.scenic.ai.service.CrowdCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrowdCountServiceImpl implements CrowdCountService {

    private final CrowdCountMapper crowdCountMapper;
    
    @Override
    @Transactional
    public void recordCrowdCount(CrowdCount crowdCount) {
        crowdCount.setCountTime(LocalDateTime.now());
        crowdCountMapper.insert(crowdCount);
    }
    
    @Override
    @Transactional
    public void batchRecordCrowdCount(List<CrowdCount> crowdCounts) {
        LocalDateTime now = LocalDateTime.now();
        crowdCounts.forEach(cc -> cc.setCountTime(now));
        crowdCountMapper.batchInsert(crowdCounts);
    }
    
    @Override
    public List<CrowdCount> getAreaHistory(String areaId, LocalDateTime startTime, LocalDateTime endTime) {
        return crowdCountMapper.findByTimeRange(areaId, startTime, endTime);
    }
    
    @Override
    public Double getAreaAverageCount(String areaId, LocalDateTime startTime, LocalDateTime endTime) {
        return crowdCountMapper.calculateAverageCount(areaId, startTime, endTime);
    }
    
    @Override
    public Integer getAreaMaxCount(String areaId, LocalDateTime startTime, LocalDateTime endTime) {
        return crowdCountMapper.findMaxCount(areaId, startTime, endTime);
    }
    
    @Override
    @Transactional
    public void cleanHistoricalData(LocalDateTime beforeTime) {
        crowdCountMapper.deleteHistoricalData(beforeTime);
    }
    
    @Override
    public List<CrowdCount> getHighDensityAreas(String areaId, Integer threshold, 
                                               LocalDateTime startTime, LocalDateTime endTime) {
        return crowdCountMapper.findHighDensityAreas(areaId, threshold, startTime, endTime);
    }
    
    @Override
    public Double getAreaDensity(String areaId, LocalDateTime time) {
        return crowdCountMapper.calculateDensity(areaId, time);
    }
    
    @Override
    public List<CrowdCount> analyzeAreaTrend(String areaId, LocalDateTime startTime, LocalDateTime endTime) {
        return crowdCountMapper.analyzeTrend(areaId, startTime, endTime);
    }
    
    @Override
    public CrowdCount getAreaRealtimeCount(String areaId) {
        return crowdCountMapper.findLatestCount(areaId);
    }
    
    @Override
    public List<CrowdCount> getAllAreasRealtimeCount() {
        return crowdCountMapper.findLatestCounts();
    }
} 