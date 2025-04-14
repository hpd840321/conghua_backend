package com.scenic.ai.model.alarm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 告警配置响应类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmConfigResponse {
    
    /**
     * 配置ID
     */
    private String id;
    
    /**
     * 告警类型
     */
    private AlarmType type;
    
    /**
     * 告警级别
     */
    private AlarmLevel level;
    
    /**
     * 区域ID
     */
    private String areaId;
    
    /**
     * 区域名称
     */
    private String areaName;
    
    /**
     * 设备ID列表
     */
    private List<String> deviceIds;
    
    /**
     * 设备名称列表
     */
    private List<String> deviceNames;
    
    /**
     * 阈值
     */
    private Double threshold;
    
    /**
     * 持续时间（秒）
     */
    private Integer duration;
    
    /**
     * 告警间隔（秒）
     */
    private Integer interval;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    /**
     * 通知渠道列表
     */
    private List<String> notificationChannels;
    
    /**
     * 通知模板
     */
    private String notificationTemplate;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 创建人
     */
    private String creator;
    
    /**
     * 创建人名称
     */
    private String creatorName;
    
    /**
     * 更新人
     */
    private String updater;
    
    /**
     * 更新人名称
     */
    private String updaterName;
    
    /**
     * 告警统计信息
     */
    private AlarmStats stats;
} 