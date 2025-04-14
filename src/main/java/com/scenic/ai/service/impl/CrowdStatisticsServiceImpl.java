package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.mapper.CrowdStatisticsMapper;
import com.scenic.ai.model.CrowdStatistics;
import com.scenic.ai.service.CrowdStatisticsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 人群统计服务实现类
 */
@Service
public class CrowdStatisticsServiceImpl extends ServiceImpl<CrowdStatisticsMapper, CrowdStatistics> 
        implements CrowdStatisticsService {

    @Override
    public List<CrowdStatistics> getCrowdByDevice(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.selectByDeviceCode(deviceCode, startTime, endTime);
    }

    @Override
    public List<CrowdStatistics> getCrowdByTourism(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.selectByTourismName(tourismName, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getHourDistribution(String deviceCode, String tourismName, 
            LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.countByHour(deviceCode, tourismName, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getDensityDistribution(String deviceCode, String tourismName, 
            LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.countByDensity(deviceCode, tourismName, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getCrowdTrend(String deviceCode, String tourismName, 
            LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.getCrowdTrend(deviceCode, tourismName, startTime, endTime);
    }

    @Override
    public List<CrowdStatistics> pageCrowdStatistics(Map<String, Object> params) {
        return baseMapper.findByConditions(params);
    }

    @Override
    public Long countCrowdStatistics(Map<String, Object> params) {
        return baseMapper.countRecords(params);
    }

    @Override
    public List<Map<String, Object>> getHighDensityStats(String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime, BigDecimal densityThreshold) {
        return baseMapper.getHighDensityStats(deviceCode, tourismName, startTime, endTime, densityThreshold);
    }

    @Override
    public List<CrowdStatistics> getCrowdByTimeRangeAndTourism(LocalDateTime startTime, LocalDateTime endTime, 
            String tourismName) {
        return baseMapper.selectByTimeRangeAndTourism(startTime, endTime, tourismName);
    }

    @Override
    public CrowdStatistics getLatestCrowdByDevice(String deviceCode) {
        return baseMapper.selectLatestByDeviceCode(deviceCode);
    }

    @Override
    public IPage<CrowdStatistics> pageCrowdStatistics(IPage<CrowdStatistics> page, String tourismName,
            String deviceCode, String algName, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<CrowdStatistics> wrapper = new LambdaQueryWrapper<>();
        
        // 添加查询条件
        if (StringUtils.hasText(tourismName)) {
            wrapper.eq(CrowdStatistics::getTourismName, tourismName);
        }
        if (StringUtils.hasText(deviceCode)) {
            wrapper.eq(CrowdStatistics::getDeviceCode, deviceCode);
        }
        if (StringUtils.hasText(algName)) {
            wrapper.eq(CrowdStatistics::getAlgName, algName);
        }
        if (startTime != null) {
            wrapper.ge(CrowdStatistics::getRecordTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(CrowdStatistics::getRecordTime, endTime);
        }

        // 按记录时间降序排序
        wrapper.orderByDesc(CrowdStatistics::getRecordTime);
        
        return page(page, wrapper);
    }
}