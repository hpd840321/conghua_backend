package com.conghua.tourism.service.alarm.impl;

import com.conghua.tourism.model.alarm.*;
import com.conghua.tourism.service.alarm.AlarmService;
import com.conghua.tourism.common.PageResult;
import com.conghua.tourism.repository.alarm.AlarmRepository;
import com.conghua.tourism.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 告警管理服务实现类
 */
@Slf4j
@Service
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    @Override
    public PageResult<AlarmData> getAlarmList(AlarmQueryParams queryParams) {
        log.info("查询告警列表，参数：{}", queryParams);
        return alarmRepository.findAlarms(queryParams);
    }

    @Override
    public AlarmStats getAlarmStats() {
        log.info("获取告警统计信息");
        return alarmRepository.getAlarmStats();
    }

    @Override
    public List<AlarmConfigResponse> getAlarmConfigs() {
        log.info("获取告警配置列表");
        return alarmRepository.findAllConfigs()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateAlarmConfig(String id, AlarmConfig config) {
        log.info("更新告警配置，ID：{}，配置：{}", id, config);
        validateAlarmConfig(config);
        alarmRepository.updateConfig(id, config);
    }

    @Override
    @Transactional
    public void handleAlarm(String id, AlarmHandleParams params) {
        log.info("处理告警，ID：{}，参数：{}", id, params);
        validateHandleParams(params);
        alarmRepository.handleAlarm(id, params);
    }

    @Override
    @Transactional
    public BatchOperationResult batchHandleAlarms(List<AlarmHandleParams> params) {
        log.info("批量处理告警，参数：{}", params);
        BatchOperationResult result = new BatchOperationResult();
        
        params.forEach(param -> {
            try {
                validateHandleParams(param);
                alarmRepository.handleAlarm(param.getId(), param);
                result.addSuccess(param.getId());
            } catch (Exception e) {
                log.error("处理告警失败，ID：{}，原因：{}", param.getId(), e.getMessage());
                result.addFailure(param.getId(), e.getMessage());
            }
        });
        
        return result;
    }

    @Override
    @Transactional
    public void deleteAlarm(String id) {
        log.info("删除告警，ID：{}", id);
        alarmRepository.deleteAlarm(id);
    }

    @Override
    @Transactional
    public BatchOperationResult batchDeleteAlarms(List<String> ids) {
        log.info("批量删除告警，ID列表：{}", ids);
        BatchOperationResult result = new BatchOperationResult();
        
        ids.forEach(id -> {
            try {
                alarmRepository.deleteAlarm(id);
                result.addSuccess(id);
            } catch (Exception e) {
                log.error("删除告警失败，ID：{}，原因：{}", id, e.getMessage());
                result.addFailure(id, e.getMessage());
            }
        });
        
        return result;
    }

    @Override
    public List<AlarmData> getRealtimeAlarms(String areaId) {
        log.info("获取实时告警数据，区域ID：{}", areaId);
        return alarmRepository.findRealtimeAlarms(areaId);
    }

    @Override
    public List<AlarmTrendData> getAlarmTrends(String startTime, String endTime, String interval) {
        log.info("获取告警趋势数据，开始时间：{}，结束时间：{}，间隔：{}", 
                startTime, endTime, interval);
        return alarmRepository.getAlarmTrends(startTime, endTime, interval);
    }

    private AlarmConfigResponse convertToResponse(AlarmConfig config) {
        AlarmConfigResponse response = new AlarmConfigResponse();
        // 转换配置对象为响应对象
        return response;
    }

    private void validateAlarmConfig(AlarmConfig config) {
        if (config == null) {
            throw new BusinessException("告警配置不能为空");
        }
        // 验证配置参数
    }

    private void validateHandleParams(AlarmHandleParams params) {
        if (params == null) {
            throw new BusinessException("处理参数不能为空");
        }
        // 验证处理参数
    }
} 