package com.scenic.ai.service;

import com.scenic.ai.domain.model.Alert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface AlertService {
    
    /**
     * 创建告警
     */
    void createAlert(Alert alert);
    
    /**
     * 更新告警
     */
    void updateAlert(Alert alert);
    
    /**
     * 处理告警
     */
    void handleAlert(Long id, String handler, String remark);
    
    /**
     * 批量处理告警
     */
    void batchHandleAlerts(List<Long> ids, String handler, String remark);
    
    /**
     * 获取告警详情
     */
    Alert getAlertById(Long id);
    
    /**
     * 获取待处理告警列表
     */
    List<Alert> getPendingAlerts();
    
    /**
     * 获取高级别待处理告警
     */
    List<Alert> getHighLevelPendingAlerts();
    
    /**
     * 获取告警统计信息
     */
    Map<String, Long> getAlertStats(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取告警类型分布
     */
    List<Map<String, Object>> getAlertTypeDistribution(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取告警时段分布
     */
    List<Map<String, Object>> getAlertHourDistribution(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 分页查询告警
     */
    List<Alert> searchAlerts(String type, String level, String status, String deviceCode,
                           LocalDateTime startTime, LocalDateTime endTime, int page, int size);
} 