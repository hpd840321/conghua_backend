package com.scenic.ai.domain.service;

import com.scenic.ai.domain.model.Alert;

import java.time.LocalDateTime;
import java.util.List;

public interface AlertService {

    void createAlert(Alert alert);
    
    Alert getAlertById(Long id);
    
    List<Alert> getAlertsByStatus(String status);
    
    List<Alert> getAlertsByLevel(String level);
    
    int getAlertCount(LocalDateTime startTime, LocalDateTime endTime);
    
    int getAlertCountByType(String type, LocalDateTime startTime, LocalDateTime endTime);
    
    void batchUpdateAlertStatus(List<Long> ids, String status, String handler);
    
    List<Alert> searchAlerts(String type, String level, String status, 
                           LocalDateTime startTime, LocalDateTime endTime);
} 