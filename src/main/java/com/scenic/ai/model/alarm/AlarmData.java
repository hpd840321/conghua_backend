package com.conghua.tourism.model.alarm;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 告警数据模型
 * 用于表示系统中的告警信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmData {
    
    /**
     * 告警ID
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
     * 告警状态
     */
    private AlarmStatus status;
    
    /**
     * 告警信息
     */
    private String message;
    
    /**
     * 告警发生时间
     */
    private LocalDateTime occurTime;
    
    /**
     * 告警区域ID
     */
    private String areaId;
    
    /**
     * 告警区域名称
     */
    private String areaName;
    
    /**
     * 设备ID
     */
    private String deviceId;
    
    /**
     * 设备名称
     */
    private String deviceName;
    
    /**
     * 处理时间
     */
    private LocalDateTime handleTime;
    
    /**
     * 处理人
     */
    private String handler;
    
    /**
     * 处理备注
     */
    private String handleRemark;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 