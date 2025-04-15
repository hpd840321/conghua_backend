package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.domain.model.AlertDomain;
import com.scenic.ai.domain.model.CrowdCount;
import com.scenic.ai.mapper.CrowdCountMapper;
import com.scenic.ai.service.CrowdCountService;
import com.scenic.ai.service.IAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 人群计数服务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CrowdCountServiceImpl implements CrowdCountService {
    
    private static final Logger log = LoggerFactory.getLogger(CrowdCountServiceImpl.class);
    
    // 密度告警阈值
    private static final double DENSITY_THRESHOLD = 0.8;
    // 人群聚集告警阈值
    private static final int GATHERING_THRESHOLD = 100;
    
    private final CrowdCountMapper crowdCountMapper;
    private final Map<String, Integer> thresholds = new ConcurrentHashMap<>();
    private final IAlertService alertService;
    
    /**
     * 构造函数注入依赖
     */
    @Autowired
    public CrowdCountServiceImpl(CrowdCountMapper crowdCountMapper, IAlertService alertService) {
        this.crowdCountMapper = crowdCountMapper;
        this.alertService = alertService;
    }
    
    @Override
    public Page<CrowdCount> getPage(Page<CrowdCount> page, String tourismName, String deviceCode,
                                   String algName, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<CrowdCount> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(tourismName)) {
            wrapper.eq(CrowdCount::getTourismName, tourismName);
        }
        if (StringUtils.hasText(deviceCode)) {
            wrapper.eq(CrowdCount::getDeviceCode, deviceCode);
        }
        if (StringUtils.hasText(algName)) {
            wrapper.eq(CrowdCount::getAlgName, algName);
        }
        if (startTime != null) {
            wrapper.ge(CrowdCount::getRecordTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(CrowdCount::getRecordTime, endTime);
        }
        wrapper.orderByDesc(CrowdCount::getRecordTime);
        return page.setRecords(crowdCountMapper.findByTimeRange(deviceCode, startTime, endTime));
    }
    
    @Override
    public Page<CrowdCount> getDetailsPage(Page<CrowdCount> page, String deviceCode, String algName,
                                         LocalDateTime recordBeginDate, LocalDateTime recordEndDate) {
        LambdaQueryWrapper<CrowdCount> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(deviceCode)) {
            wrapper.eq(CrowdCount::getDeviceCode, deviceCode);
        }
        if (StringUtils.hasText(algName)) {
            wrapper.eq(CrowdCount::getAlgName, algName);
        }
        if (recordBeginDate != null) {
            wrapper.ge(CrowdCount::getRecordTime, recordBeginDate);
        }
        if (recordEndDate != null) {
            wrapper.le(CrowdCount::getRecordTime, recordEndDate);
        }
        wrapper.orderByDesc(CrowdCount::getRecordTime);
        return page.setRecords(crowdCountMapper.findByTimeRange(deviceCode, recordBeginDate, recordEndDate));
    }
    
    @Override
    @Transactional
    public void save(CrowdCount crowdCount) {
        crowdCountMapper.insert(crowdCount);
    }
    
    @Override
    @Transactional
    public int batchSave(List<CrowdCount> crowdCounts) {
        return crowdCountMapper.batchInsert(crowdCounts);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CrowdCount> findByTimeRange(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        return crowdCountMapper.findByTimeRange(deviceCode, startTime, endTime);
    }
    
    @Override
    public Double calculateAverageCount(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        return crowdCountMapper.calculateAverageCount(deviceCode, startTime, endTime);
    }
    
    @Override
    public Integer findMaxCount(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        return crowdCountMapper.findMaxCount(deviceCode, startTime, endTime);
    }
    
    @Override
    @Transactional
    public int deleteHistoricalData(LocalDateTime time) {
        return crowdCountMapper.deleteHistoricalData(time);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AlertDomain> findExceedThresholdCounts() {
        List<CrowdCount> highDensityAreas = crowdCountMapper.findHighDensityAreas(null, GATHERING_THRESHOLD, 
            LocalDateTime.now().minusHours(1), LocalDateTime.now());
        return highDensityAreas.stream()
            .map(count -> AlertDomain.createWithValue(
                "DENSITY",  // 告警类型
                count.getDensity() > DENSITY_THRESHOLD ? "HIGH" : "MEDIUM",  // 告警级别
                count.getDeviceCode(),
                count.getDeviceName(),
                count.getTourismName(),
                String.format("区域人群密度超过阈值：%.2f", count.getDensity()),
                count.getDensity()
            ))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<CrowdCount> findHighDensityAreas(String deviceCode, int threshold,
                                                LocalDateTime startTime, LocalDateTime endTime) {
        return crowdCountMapper.findHighDensityAreas(deviceCode, threshold, startTime, endTime);
    }
    
    @Override
    public Double calculateDensity(String deviceCode, LocalDateTime time) {
        return crowdCountMapper.calculateDensity(deviceCode, time);
    }
    
    @Override
    public List<CrowdCount> analyzeTrend(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        return crowdCountMapper.analyzeTrend(deviceCode, startTime, endTime);
    }
    
    @Override
    public CrowdCount getRealTimeCount(String deviceCode) {
        return crowdCountMapper.findLatestCount(deviceCode);
    }
    
    @Override
    public void setThreshold(String deviceCode, int threshold) {
        thresholds.put(deviceCode, threshold);
    }
    
    @Override
    public Integer getThreshold(String deviceCode) {
        return thresholds.getOrDefault(deviceCode, GATHERING_THRESHOLD);
    }

    @Override
    public Double calculateAverage(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        return calculateAverageCount(deviceCode, startTime, endTime);
    }

    @Override
    public Double calculateGrowthRate(String deviceCode, LocalDateTime time) {
        CrowdCount current = getRealTimeCount(deviceCode);
        if (current == null) {
            return 0.0;
        }
        
        LocalDateTime previousTime = time.minusHours(1);
        Double previousAvg = calculateAverage(deviceCode, previousTime, time);
        if (previousAvg == null || previousAvg == 0) {
            return 0.0;
        }
        
        return (current.getCount() - previousAvg) / previousAvg * 100;
    }

    @Override
    public Double calculateChainGrowthRate(String deviceCode, LocalDateTime time) {
        CrowdCount current = getRealTimeCount(deviceCode);
        if (current == null) {
            return 0.0;
        }
        
        LocalDateTime previousDayTime = time.minusDays(1);
        Double previousDayAvg = calculateAverage(deviceCode, previousDayTime, previousDayTime.plusHours(1));
        if (previousDayAvg == null || previousDayAvg == 0) {
            return 0.0;
        }
        
        return (current.getCount() - previousDayAvg) / previousDayAvg * 100;
    }

    @Override
    public Integer findMax(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        return findMaxCount(deviceCode, startTime, endTime);
    }

    @Override
    public Double calculateAverageDensity(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        return crowdCountMapper.calculateAverageDensity(deviceCode, startTime, endTime);
    }
} 