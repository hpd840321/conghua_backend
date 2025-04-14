package com.scenic.ai.service.impl;

import com.scenic.ai.domain.model.Alert;
import com.scenic.ai.mapper.AlertMapper;
import com.scenic.ai.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    private final AlertMapper alertMapper;
    
    @Override
    @Transactional
    public void createAlert(Alert alert) {
        alert.setCreateTime(LocalDateTime.now());
        alert.setUpdateTime(LocalDateTime.now());
        alert.setStatus("pending");
        alertMapper.insert(alert);
    }
    
    @Override
    @Transactional
    public void updateAlert(Alert alert) {
        alert.setUpdateTime(LocalDateTime.now());
        alertMapper.update(alert);
    }
    
    @Override
    @Transactional
    public void handleAlert(Long id, String handler, String remark) {
        Alert alert = alertMapper.findById(id);
        if (alert != null) {
            alert.setStatus("handled");
            alert.setHandler(handler);
            alert.setRemark(remark);
            alert.setHandleTime(LocalDateTime.now());
            alert.setUpdateTime(LocalDateTime.now());
            alertMapper.update(alert);
        }
    }
    
    @Override
    @Transactional
    public void batchHandleAlerts(List<Long> ids, String handler, String remark) {
        alertMapper.batchUpdateStatus(ids, "handled", handler, remark);
    }
    
    @Override
    public Alert getAlertById(Long id) {
        return alertMapper.findById(id);
    }
    
    @Override
    public List<Alert> getPendingAlerts() {
        return alertMapper.findByStatus("pending");
    }
    
    @Override
    public List<Alert> getHighLevelPendingAlerts() {
        return alertMapper.findByLevelAndStatus("high", "pending");
    }
    
    @Override
    public Map<String, Long> getAlertStats(LocalDateTime startTime, LocalDateTime endTime) {
        return alertMapper.countByTimeRange(startTime, endTime);
    }
    
    @Override
    public List<Map<String, Object>> getAlertTypeDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        return alertMapper.countByType(startTime, endTime);
    }
    
    @Override
    public List<Map<String, Object>> getAlertHourDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        return alertMapper.countByHour(startTime, endTime);
    }
    
    @Override
    public List<Alert> searchAlerts(String type, String level, String status, String deviceCode,
                                  LocalDateTime startTime, LocalDateTime endTime, int page, int size) {
        int offset = (page - 1) * size;
        return alertMapper.findByConditions(type, level, status, deviceCode, 
                                         startTime, endTime, offset, size);
    }
} 