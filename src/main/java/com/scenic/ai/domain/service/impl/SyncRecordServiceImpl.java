package com.scenic.ai.domain.service.impl;

import com.scenic.ai.domain.mapper.SyncRecordMapper;
import com.scenic.ai.domain.model.SyncRecord;
import com.scenic.ai.domain.service.SyncRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SyncRecordServiceImpl implements SyncRecordService {

    @Autowired
    private SyncRecordMapper syncRecordMapper;

    @Override
    @Transactional
    public void createRecord(SyncRecord record) {
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        syncRecordMapper.insert(record);
    }

    @Override
    @Transactional
    public void updateRecord(SyncRecord record) {
        record.setUpdateTime(LocalDateTime.now());
        syncRecordMapper.update(record);
    }

    @Override
    @Transactional
    public void deleteRecord(Long id) {
        syncRecordMapper.deleteById(id);
    }

    @Override
    public SyncRecord getRecordById(Long id) {
        return syncRecordMapper.selectById(id);
    }

    @Override
    public List<SyncRecord> getTaskRecords(Long taskId) {
        return syncRecordMapper.selectByTaskId(taskId);
    }

    @Override
    public List<SyncRecord> getSuccessRecords(LocalDateTime startTime, LocalDateTime endTime) {
        return syncRecordMapper.selectSuccessRecords(startTime, endTime);
    }

    @Override
    public List<SyncRecord> getFailedRecords(LocalDateTime startTime, LocalDateTime endTime) {
        return syncRecordMapper.selectFailedRecords(startTime, endTime);
    }

    @Override
    public List<SyncRecord> searchRecords(Long taskId, String status,
                                        LocalDateTime startTime, LocalDateTime endTime) {
        return syncRecordMapper.selectByCondition(taskId, status, startTime, endTime);
    }

    @Override
    public double calculateSuccessRate(LocalDateTime startTime, LocalDateTime endTime) {
        return syncRecordMapper.calculateSuccessRate(startTime, endTime);
    }

    @Override
    @Transactional
    public void cleanHistoricalRecords(LocalDateTime beforeTime) {
        syncRecordMapper.deleteHistoricalRecords(beforeTime);
    }

    @Override
    public Map<String, Object> analyzeSyncPerformance(String taskType,
                                                    LocalDateTime startTime,
                                                    LocalDateTime endTime) {
        List<SyncRecord> records = syncRecordMapper.selectByCondition(null, null, startTime, endTime);
        
        Map<String, Object> result = new HashMap<>();
        
        // 计算平均同步时长
        double avgDuration = records.stream()
                .mapToLong(SyncRecord::getDuration)
                .average()
                .orElse(0.0);
        result.put("averageDuration", avgDuration);
        
        // 计算成功率
        long successCount = records.stream()
                .filter(r -> "SUCCESS".equals(r.getStatus()))
                .count();
        double successRate = records.isEmpty() ? 0.0 : (double) successCount / records.size();
        result.put("successRate", successRate);
        
        // 计算平均同步数据量
        double avgTotalCount = records.stream()
                .mapToInt(SyncRecord::getTotalCount)
                .average()
                .orElse(0.0);
        result.put("averageTotalCount", avgTotalCount);
        
        // 统计错误类型分布
        Map<String, Long> errorTypes = records.stream()
                .filter(r -> r.getErrorMessage() != null)
                .collect(Collectors.groupingBy(
                        r -> r.getErrorMessage().split(":")[0],
                        Collectors.counting()
                ));
        result.put("errorTypes", errorTypes);
        
        return result;
    }
} 