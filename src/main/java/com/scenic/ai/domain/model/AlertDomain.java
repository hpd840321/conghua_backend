package com.scenic.ai.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 告警领域模型
 * 表示系统中的一个告警实体
 */
@Getter
public class AlertDomain {
    private final String id;
    private final String type;
    private final String level;
    private final String deviceCode;
    private final String deviceName;
    private final String tourismName;
    private final String description;
    private final Double value;
    private final LocalDateTime createTime;
    private final String status;
    
    
    private AlertDomain(String id, String type, String level, String deviceCode, 
                String deviceName, String tourismName, String description, 
                Double value, LocalDateTime createTime, String status) {
        this.id = id;
        this.type = type;
        this.level = level;
        this.deviceCode = deviceCode;
        this.deviceName = deviceName;
        this.tourismName = tourismName;
        this.description = description;
        this.value = value;
        this.createTime = createTime;
        this.status = status;
        validate();
    }
    
    /**
     * 创建新的告警
     */
    public static AlertDomain create(String type, String level, String deviceCode, 
                             String deviceName, String tourismName, String description) {
        String id = generateId();
        return new AlertDomain(id, type, level, deviceCode, deviceName, tourismName, 
                        description, null, LocalDateTime.now(), "NEW");
    }
    
    /**
     * 创建带有数值的告警
     */
    public static AlertDomain createWithValue(String type, String level, String deviceCode, 
                                      String deviceName, String tourismName,
                                      String description, Double value) {
        String id = generateId();
        return new AlertDomain(id, type, level, deviceCode, deviceName, tourismName,
                        description, value, LocalDateTime.now(), "NEW");
    }
    
    /**
     * 生成告警ID
     */
    private static String generateId() {
        return java.util.UUID.randomUUID().toString();
    }
    
    /**
     * 验证告警数据的有效性
     */
    private void validate() {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("告警类型不能为空");
        }
        if (level == null || level.trim().isEmpty()) {
            throw new IllegalArgumentException("告警级别不能为空");
        }
        if (deviceCode == null || deviceCode.trim().isEmpty()) {
            throw new IllegalArgumentException("设备编码不能为空");
        }
        if (deviceName == null || deviceName.trim().isEmpty()) {
            throw new IllegalArgumentException("设备名称不能为空");
        }
        if (tourismName == null || tourismName.trim().isEmpty()) {
            throw new IllegalArgumentException("景区名称不能为空");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("告警描述不能为空");
        }
        if (createTime == null) {
            throw new IllegalArgumentException("创建时间不能为空");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("告警状态不能为空");
        }
    }
    
    /**
     * 判断是否为高级别告警
     */
    public boolean isHighLevel() {
        return "HIGH".equals(level);
    }
    
    /**
     * 判断是否为新告警
     */
    public boolean isNew() {
        return "NEW".equals(status);
    }
    
    /**
     * 判断是否需要立即处理
     */
    public boolean needsImmediateAttention() {
        return isHighLevel() && isNew();
    }
    
    /**
     * 创建已处理的告警副本
     */
    public AlertDomain createHandled() {
        return new AlertDomain(this.id, this.type, this.level, this.deviceCode,
                        this.deviceName, this.tourismName, this.description,
                        this.value, this.createTime, "HANDLED");
    }
    
    /**
     * 创建已确认的告警副本
     */
    public AlertDomain createAcknowledged() {
        return new AlertDomain(this.id, this.type, this.level, this.deviceCode,
                        this.deviceName, this.tourismName, this.description,
                        this.value, this.createTime, "ACKNOWLEDGED");
    }
    
    /**
     * 创建指定状态的告警副本
     */
    public AlertDomain createWithStatus(String newStatus) {
        return new AlertDomain(this.id, this.type, this.level, this.deviceCode,
                        this.deviceName, this.tourismName, this.description,
                        this.value, this.createTime, newStatus);
    }
    
    @Override
    public String toString() {
        return String.format("AlertDomain{id='%s', type='%s', level='%s', deviceCode='%s', deviceName='%s', tourismName='%s', status='%s'}", 
                           id, type, level, deviceCode, deviceName, tourismName, status);
    }
} 