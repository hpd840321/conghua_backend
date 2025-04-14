package com.scenic.ai.domain.service.impl;

import com.scenic.ai.domain.mapper.AlertMapper;
import com.scenic.ai.domain.model.Alert;
import com.scenic.ai.domain.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertServiceImpl implements AlertService {

    @Autowired
    private AlertMapper alertMapper;

    @Override
    @Transactional
    public void createAlert(Alert alert) {
        alertMapper.insert(alert);
    }

    @Override
    public Alert getAlertById(Long id) {
        return alertMapper.selectById(id);
    }

    @Override
    public List<Alert> getAlertsByStatus(String status) {
        return alertMapper.selectByStatus(status);
    }

    @Override
    public List<Alert> getAlertsByLevel(String level) {
        return alertMapper.selectByLevel(level);
    }

    @Override
    public int getAlertCount(LocalDateTime startTime, LocalDateTime endTime) {
        return alertMapper.countByTimeRange(startTime, endTime);
    }

    @Override
    public int getAlertCountByType(String type, LocalDateTime startTime, LocalDateTime endTime) {
        return alertMapper.countByTypeAndTimeRange(type, startTime, endTime);
    }

    @Override
    @Transactional
    public void batchUpdateAlertStatus(List<Long> ids, String status, String handler) {
        alertMapper.batchUpdateStatus(ids, status, handler);
    }

    @Override
    public List<Alert> searchAlerts(String type, String level, String status,
                                  LocalDateTime startTime, LocalDateTime endTime) {
        return alertMapper.selectByConditions(type, level, status, startTime, endTime);
    }
} 