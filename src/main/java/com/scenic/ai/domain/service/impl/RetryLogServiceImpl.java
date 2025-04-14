package com.scenic.ai.domain.service.impl;

import com.scenic.ai.domain.mapper.RetryLogMapper;
import com.scenic.ai.domain.model.RetryLog;
import com.scenic.ai.domain.service.RetryLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RetryLogServiceImpl implements RetryLogService {

    @Autowired
    private RetryLogMapper retryLogMapper;

    @Override
    @Transactional
    public void logRetry(RetryLog log) {
        log.setCreateTime(LocalDateTime.now());
        log.setUpdateTime(LocalDateTime.now());
        retryLogMapper.insert(log);
    }

    @Override
    @Transactional
    public void updateLog(RetryLog log) {
        log.setUpdateTime(LocalDateTime.now());
        retryLogMapper.update(log);
    }

    @Override
    @Transactional
    public void deleteLog(Long id) {
        retryLogMapper.deleteById(id);
    }

    @Override
    public RetryLog getLogById(Long id) {
        return retryLogMapper.selectById(id);
    }

    @Override
    public List<RetryLog> getTaskRetryLogs(Long taskId) {
        return retryLogMapper.selectByTaskId(taskId);
    }

    @Override
    public List<RetryLog> getFailedLogs() {
        return retryLogMapper.selectFailedLogs();
    }

    @Override
    public List<RetryLog> getMaxRetriedLogs() {
        return retryLogMapper.selectMaxRetriedLogs();
    }

    @Override
    public List<RetryLog> searchLogs(Long taskId, String status,
                                   LocalDateTime startTime, LocalDateTime endTime) {
        return retryLogMapper.selectByCondition(taskId, status, startTime, endTime);
    }

    @Override
    @Transactional
    public void cleanHistoricalLogs(LocalDateTime beforeTime) {
        retryLogMapper.deleteHistoricalLogs(beforeTime);
    }

    @Override
    public double analyzeRetrySuccessRate(String taskType,
                                        LocalDateTime startTime, LocalDateTime endTime) {
        List<RetryLog> logs = retryLogMapper.selectByCondition(null, null, startTime, endTime);
        if (logs.isEmpty()) {
            return 0.0;
        }
        
        long successCount = logs.stream()
                .filter(log -> "SUCCESS".equals(log.getStatus()))
                .count();
        
        return (double) successCount / logs.size();
    }
} 