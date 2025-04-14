package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.mapper.CrowdStatisticsMapper;
import com.scenic.ai.domain.model.CrowdStatistics;
import com.scenic.ai.service.CrowdStatisticsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 人群统计服务实现类
 */
@Service
public class CrowdStatisticsServiceImpl extends ServiceImpl<CrowdStatisticsMapper, CrowdStatistics> implements CrowdStatisticsService {

    @Override
    public IPage<CrowdStatistics> pageStatistics(IPage<CrowdStatistics> page, String tourismName, 
                                                String deviceCode, String algName,
                                                LocalDateTime startTime, LocalDateTime endTime) {
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
            wrapper.ge(CrowdStatistics::getRecordTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(CrowdStatistics::getRecordTime, endTime);
        }
        wrapper.orderByDesc(CrowdStatistics::getRecordTime);
        return this.page(page, wrapper);
    }

    @Override
    public List<Map<String, Object>> getDensityTrendBasic(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        return this.baseMapper.getDensityTrend(deviceCode, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getCountTrend(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        return this.baseMapper.getCountTrend(deviceCode, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getHeatMapData(String tourismName, LocalDateTime recordTime) {
        return this.baseMapper.getHeatMapData(tourismName, recordTime);
    }

    @Override
    public double getAverageCount(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        return this.baseMapper.getAverageCount(deviceCode, startTime, endTime);
    }

    @Override
    public CrowdStatistics getPeakDensityRecord(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<CrowdStatistics> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CrowdStatistics::getDeviceCode, deviceCode)
               .ge(CrowdStatistics::getRecordTime, startTime)
               .le(CrowdStatistics::getRecordTime, endTime)
               .orderByDesc(CrowdStatistics::getDensity)
               .last("LIMIT 1");
        return this.getOne(wrapper);
    }

    @Override
    public Map<String, Object> getStatistics(String tourismName, String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
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
    public Map<String, Object> getDensityTrendWithDetails(String tourismName, String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<CrowdStatistics> wrapper = buildBaseWrapper(tourismName, deviceCode, startTime, endTime);
        wrapper.orderByAsc(CrowdStatistics::getRecordTime);
        
        List<CrowdStatistics> statistics = list(wrapper);
        Map<String, Object> result = new HashMap<>();
        
        List<String> timeList = statistics.stream()
                .map(stat -> stat.getRecordTime().toString())
                .collect(Collectors.toList());
        
        List<BigDecimal> densityList = statistics.stream()
                .map(stat -> BigDecimal.valueOf(stat.getDensity()))
                .collect(Collectors.toList());
        
        result.put("timeList", timeList);
        result.put("densityList", densityList);
        
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
            wrapper.ge(CrowdStatistics::getRecordTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(CrowdStatistics::getRecordTime, endTime);
        }
        
        return wrapper;
    }
} 