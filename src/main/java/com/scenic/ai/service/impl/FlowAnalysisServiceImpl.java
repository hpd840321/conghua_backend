package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.mapper.FlowAnalysisMapper;
import com.scenic.ai.model.FlowAnalysis;
import com.scenic.ai.service.FlowAnalysisService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 客流分析服务实现类
 */
@Service
public class FlowAnalysisServiceImpl extends ServiceImpl<FlowAnalysisMapper, FlowAnalysis> implements FlowAnalysisService {

    @Override
    public IPage<FlowAnalysis> pageFlowAnalysis(IPage<FlowAnalysis> page, String tourismName,
                                               String deviceCode, String algName,
                                               LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<FlowAnalysis> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(tourismName), FlowAnalysis::getTourismName, tourismName)
               .eq(StringUtils.hasText(deviceCode), FlowAnalysis::getDeviceCode, deviceCode)
               .eq(StringUtils.hasText(algName), FlowAnalysis::getAlgName, algName)
               .ge(startTime != null, FlowAnalysis::getRecordTime, startTime)
               .le(endTime != null, FlowAnalysis::getRecordTime, endTime)
               .orderByDesc(FlowAnalysis::getRecordTime);
        return this.page(page, wrapper);
    }

    @Override
    public List<Map<String, Object>> getFlowTrend(String deviceCode, 
                                                String tourismName,
                                                LocalDateTime startTime, 
                                                LocalDateTime endTime) {
        return this.baseMapper.getFlowTrend(deviceCode, tourismName, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getFlowDirectionDistribution(String deviceCode, 
                                                                String tourismName,
                                                                LocalDateTime startTime, 
                                                                LocalDateTime endTime) {
        return this.baseMapper.getFlowDirectionDistribution(deviceCode, tourismName, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getPeakHours(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        return this.baseMapper.getPeakHours(deviceCode, startTime, endTime);
    }

    @Override
    public int getTotalFlowCount(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        return this.baseMapper.getTotalFlowCount(deviceCode, startTime, endTime);
    }

    @Override
    public FlowAnalysis getPeakFlowRecord(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<FlowAnalysis> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FlowAnalysis::getDeviceCode, deviceCode)
               .ge(FlowAnalysis::getRecordTime, startTime)
               .le(FlowAnalysis::getRecordTime, endTime)
               .orderByDesc(FlowAnalysis::getFlowCount)
               .last("LIMIT 1");
        return this.getOne(wrapper);
    }
} 