package com.scenic.ai.service.impl;

import com.scenic.ai.domain.model.DensityRecord;
import com.scenic.ai.mapper.DensityMapper;
import com.scenic.ai.service.DensityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DensityServiceImpl implements DensityService {

    private final DensityMapper densityMapper;
    
    @Override
    @Transactional
    public void recordDensity(DensityRecord record) {
        LocalDateTime now = LocalDateTime.now();
        record.setCreateTime(now);
        record.setUpdateTime(now);
        record.setLevel(calculateDensityLevel(record.getDensity()));
        densityMapper.insert(record);
    }
    
    @Override
    @Transactional
    public void batchRecordDensity(List<DensityRecord> records) {
        LocalDateTime now = LocalDateTime.now();
        records.forEach(record -> {
            record.setCreateTime(now);
            record.setUpdateTime(now);
            record.setLevel(calculateDensityLevel(record.getDensity()));
        });
        densityMapper.batchInsert(records);
    }
    
    @Override
    public DensityRecord getDensityById(Long id) {
        return densityMapper.selectById(id);
    }
    
    @Override
    public DensityRecord getLatestDensity(String areaId) {
        return densityMapper.selectLatestByAreaId(areaId);
    }
    
    @Override
    public List<DensityRecord> getHistoricalDensity(String areaId, LocalDateTime startTime, LocalDateTime endTime) {
        return densityMapper.selectByAreaIdAndTimeRange(areaId, startTime, endTime);
    }
    
    @Override
    @Transactional
    public void updateDensity(DensityRecord record) {
        record.setUpdateTime(LocalDateTime.now());
        record.setLevel(calculateDensityLevel(record.getDensity()));
        densityMapper.update(record);
    }
    
    @Override
    @Transactional
    public void deleteDensity(Long id) {
        densityMapper.deleteById(id);
    }
    
    @Override
    public String calculateDensityLevel(Double density) {
        if (density == null) {
            return "未知";
        }
        if (density < 0.3) {
            return "低";
        } else if (density < 0.6) {
            return "中";
        } else if (density < 0.8) {
            return "高";
        } else {
            return "拥挤";
        }
    }
} 