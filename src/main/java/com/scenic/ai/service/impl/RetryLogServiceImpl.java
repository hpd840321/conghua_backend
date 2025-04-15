package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.mapper.RetryLogMapper;
import com.scenic.ai.model.RetryLog;
import com.scenic.ai.service.RetryLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 重试日志服务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RetryLogServiceImpl extends ServiceImpl<RetryLogMapper, RetryLog> implements RetryLogService {
    
    private static final Logger log = LoggerFactory.getLogger(RetryLogServiceImpl.class);
    
    @Override
    public RetryLog createRetryLog(String businessType, String businessId, int maxRetryCount, String errorMessage) {
        RetryLog retryLog = new RetryLog();
        retryLog.setBusinessType(businessType);
        retryLog.setBusinessId(businessId);
        retryLog.setMaxRetryCount(maxRetryCount);
        retryLog.setRetryCount(0);
        retryLog.setStatus("PENDING");
        retryLog.setErrorMessage(errorMessage);
        retryLog.setNextRetryTime(LocalDateTime.now().plusMinutes(1)); // 默认1分钟后重试
        
        baseMapper.insert(retryLog);
        return retryLog;
    }
    
    @Override
    public boolean updateRetryInfo(Long id, int retryCount, LocalDateTime nextRetryTime, String errorMessage) {
        try {
            // 先更新重试次数和下次重试时间
            int rows = baseMapper.updateRetryInfo(id, retryCount, nextRetryTime);
            if (rows > 0 && errorMessage != null) {
                // 如果有错误信息，则更新错误信息
                baseMapper.updateStatus(id, "PENDING", errorMessage);
            }
            return rows > 0;
        } catch (Exception e) {
            log.error("更新重试信息失败: id={}, retryCount={}", id, retryCount, e);
            return false;
        }
    }
    
    @Override
    public boolean updateStatus(Long id, String status, String errorMessage) {
        try {
            int rows = baseMapper.updateStatus(id, status, errorMessage);
            return rows > 0;
        } catch (Exception e) {
            log.error("更新状态失败: id={}, status={}", id, status, e);
            return false;
        }
    }
    
    @Override
    public List<RetryLog> findRetryTasks(LocalDateTime nextRetryTime, String status) {
        return baseMapper.findRetryTasks(nextRetryTime, status);
    }
    
    @Override
    public RetryLog findByBusinessTypeAndId(String businessType, String businessId) {
        return baseMapper.findByBusinessTypeAndId(businessType, businessId);
    }
    
    @Override
    public List<RetryLog> findByStatus(String status) {
        return baseMapper.findByStatus(status);
    }
    
    @Override
    public List<Map<String, Object>> countByBusinessType(LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.countByBusinessType(startTime, endTime);
    }
    
    @Override
    public List<Map<String, Object>> countByStatus(LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.countByStatus(startTime, endTime);
    }
    
    @Override
    public List<RetryLog> findByConditions(Map<String, Object> params) {
        return baseMapper.findByConditions(params);
    }
    
    @Override
    public Long countRecords(Map<String, Object> params) {
        return baseMapper.countRecords(params);
    }
    
    @Override
    public int cleanSuccessLogs(LocalDateTime beforeTime) {
        try {
            return lambdaUpdate()
                    .eq(RetryLog::getStatus, "SUCCESS")
                    .lt(RetryLog::getCreateTime, beforeTime)
                    .remove() ? 1 : 0;
        } catch (Exception e) {
            log.error("清理成功的重试日志失败", e);
            return 0;
        }
    }
} 