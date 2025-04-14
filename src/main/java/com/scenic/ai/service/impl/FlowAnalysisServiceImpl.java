package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.mapper.FlowAnalysisMapper;
import com.scenic.ai.model.FlowAnalysis;
import com.scenic.ai.service.FlowAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 客流分析服务实现类
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class FlowAnalysisServiceImpl extends ServiceImpl<FlowAnalysisMapper, FlowAnalysis> implements FlowAnalysisService {

    private final FlowAnalysisMapper flowAnalysisMapper;

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
    public List<FlowAnalysis> getByDeviceCode(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        return flowAnalysisMapper.selectByDeviceCode(deviceCode, startTime, endTime);
    }

    @Override
    public List<FlowAnalysis> getByTourismName(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        return flowAnalysisMapper.selectByTourismName(tourismName, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getHourlyDistribution(String deviceCode, String tourismName,
                                                         LocalDateTime startTime, LocalDateTime endTime) {
        return flowAnalysisMapper.countByHour(deviceCode, tourismName, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getFlowDirectionDistribution(String deviceCode, String tourismName,
                                                            LocalDateTime startTime, LocalDateTime endTime) {
        return flowAnalysisMapper.countByDirection(deviceCode, tourismName, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getFlowTrend(String deviceCode, String tourismName,
                                                 LocalDateTime startTime, LocalDateTime endTime) {
        return flowAnalysisMapper.getFlowTrend(deviceCode, tourismName, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getPeakHours(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        return flowAnalysisMapper.getPeakHours(deviceCode, startTime, endTime);
    }

    @Override
    public List<FlowAnalysis> findByConditions(Map<String, Object> params) {
        return flowAnalysisMapper.findByConditions(params);
    }

    @Override
    public Long countRecords(Map<String, Object> params) {
        return flowAnalysisMapper.countRecords(params);
    }

    @Override
    public List<FlowAnalysis> getByTimeRangeAndTourism(LocalDateTime startTime, LocalDateTime endTime, String tourismName) {
        return flowAnalysisMapper.selectByTimeRangeAndTourism(startTime, endTime, tourismName);
    }

    @Override
    public FlowAnalysis getLatestByDeviceCode(String deviceCode) {
        return flowAnalysisMapper.selectLatestByDeviceCode(deviceCode);
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

    @Override
    public List<FlowAnalysis> getFlowByDevice(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.selectByDeviceCode(deviceCode, startTime, endTime);
    }

    @Override
    public List<FlowAnalysis> getFlowByTourism(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.selectByTourismName(tourismName, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getHourDistribution(String deviceCode, String tourismName, 
            LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.countByHour(deviceCode, tourismName, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getDirectionDistribution(String deviceCode, String tourismName, 
            LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.countByDirection(deviceCode, tourismName, startTime, endTime);
    }

    @Override
    public FlowAnalysis getLatestFlowByDevice(String deviceCode) {
        return baseMapper.selectLatestByDeviceCode(deviceCode);
    }
} 