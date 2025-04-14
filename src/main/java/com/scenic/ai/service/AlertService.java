package com.scenic.ai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scenic.ai.model.Alert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 告警服务接口
 * 
 * @author scenic
 * @date 2024-03-20
 */
public interface AlertService extends IService<Alert> {
    
    /**
     * 创建告警
     */
    boolean createAlert(Alert alert);
    
    /**
     * 更新告警状态
     */
    boolean updateStatus(Long id, Integer status);
    
    /**
     * 批量更新告警状态
     */
    boolean batchUpdateStatus(List<Long> ids, Integer status);
    
    /**
     * 分页查询告警信息
     */
    IPage<Alert> page(Integer pageNum, Integer pageSize, String tourismName, String deviceCode,
            String alertType, Integer alertLevel, Integer alertStatus,
            LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取告警时段分布
     */
    List<Map<String, Object>> getTimeDistribution(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取告警类型分布
     */
    List<Map<String, Object>> getTypeDistribution(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取告警概览统计
     */
    Map<String, Object> getOverview(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取设备最新告警
     */
    Alert getLatestByDevice(String deviceCode);
    
    /**
     * 处理告警
     */
    void handleAlert(Long alertId, String description);
    
    /**
     * 批量处理告警
     */
    boolean batchHandleAlerts(List<Long> ids);
    
    /**
     * 统计待处理告警数量
     */
    int countPendingAlerts(String tourismName);
    
    /**
     * 获取设备告警列表
     */
    List<Alert> getByDevice(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取景区告警列表
     */
    List<Alert> getByTourism(String tourismName, LocalDateTime startTime, LocalDateTime endTime);
} 