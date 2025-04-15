package com.scenic.ai.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 告警处理记录实体类
 * 
 * @author scenic
 */
@TableName("ALERT_HANDLING_RECORD")
public class AlertHandlingRecord {
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 告警ID
     */
    private Long alertId;
    
    /**
     * 处理人
     */
    private String handler;
    
    /**
     * 处理说明
     */
    private String description;
    
    /**
     * 处理时间
     */
    private LocalDateTime handleTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    // 构造函数
    public AlertHandlingRecord() {
    }

    public AlertHandlingRecord(Long id, Long alertId, String handler, String description,
                             LocalDateTime handleTime, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.alertId = alertId;
        this.handler = handler;
        this.description = description;
        this.handleTime = handleTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // Getter方法
    public Long getId() {
        return id;
    }

    public Long getAlertId() {
        return alertId;
    }

    public String getHandler() {
        return handler;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getHandleTime() {
        return handleTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    // Setter方法
    public void setId(Long id) {
        this.id = id;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHandleTime(LocalDateTime handleTime) {
        this.handleTime = handleTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
} 