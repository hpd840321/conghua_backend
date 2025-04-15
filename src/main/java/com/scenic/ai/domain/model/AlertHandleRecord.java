package com.scenic.ai.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 告警处理记录领域模型
 * 表示系统中的一个告警处理记录
 */
public class AlertHandleRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long id;
    private final Long alertId;
    private final String handler;
    private final LocalDateTime handleTime;
    private final String beforeStatus;
    private final String afterStatus;
    private final String handleMethod;
    private final String handleResult;
    private final String handleDesc;
    private final LocalDateTime createTime;
    private final LocalDateTime updateTime;

    private AlertHandleRecord(Long id, Long alertId, String handler, LocalDateTime handleTime,
            String beforeStatus, String afterStatus, String handleMethod,
            String handleResult, String handleDesc, LocalDateTime createTime,
            LocalDateTime updateTime) {
        this.id = id;
        this.alertId = alertId;
        this.handler = handler;
        this.handleTime = handleTime;
        this.beforeStatus = beforeStatus;
        this.afterStatus = afterStatus;
        this.handleMethod = handleMethod;
        this.handleResult = handleResult;
        this.handleDesc = handleDesc;
        this.createTime = createTime;
        this.updateTime = updateTime;
        validate();
    }

    /**
     * 创建新的告警处理记录
     */
    public static AlertHandleRecord create(Long alertId, String handler, String beforeStatus,
            String afterStatus, String handleMethod,
            String handleResult, String handleDesc) {
        LocalDateTime now = LocalDateTime.now();
        return new AlertHandleRecord(null, alertId, handler, now, beforeStatus, afterStatus,
                handleMethod, handleResult, handleDesc, now, now);
    }

    /**
     * 验证告警处理记录数据的有效性
     */
    private void validate() {
        if (alertId == null) {
            throw new IllegalArgumentException("告警ID不能为空");
        }
        if (handler == null || handler.trim().isEmpty()) {
            throw new IllegalArgumentException("处理人不能为空");
        }
        if (handleTime == null) {
            throw new IllegalArgumentException("处理时间不能为空");
        }
        if (beforeStatus == null || beforeStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("处理前状态不能为空");
        }
        if (afterStatus == null || afterStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("处理后状态不能为空");
        }
        if (handleMethod == null || handleMethod.trim().isEmpty()) {
            throw new IllegalArgumentException("处理方式不能为空");
        }
        if (handleResult == null || handleResult.trim().isEmpty()) {
            throw new IllegalArgumentException("处理结果不能为空");
        }
        if (handleDesc == null || handleDesc.trim().isEmpty()) {
            throw new IllegalArgumentException("处理描述不能为空");
        }
        if (createTime == null) {
            throw new IllegalArgumentException("创建时间不能为空");
        }
        if (updateTime == null) {
            throw new IllegalArgumentException("更新时间不能为空");
        }
    }

    /**
     * 判断处理是否成功
     */
    public boolean isSuccessful() {
        return "SUCCESS".equals(handleResult);
    }

    /**
     * 判断是否为紧急处理
     */
    public boolean isUrgent() {
        return "URGENT".equals(handleMethod);
    }

    /**
     * 获取告警处理记录ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 获取告警ID
     */
    public Long getAlertId() {
        return alertId;
    }

    /**
     * 获取处理人
     */
    public String getHandler() {
        return handler;
    }

    /**
     * 获取处理时间
     */
    public LocalDateTime getHandleTime() {
        return handleTime;
    }

    /**
     * 获取处理前状态
     */
    public String getBeforeStatus() {
        return beforeStatus;
    }

    /**
     * 获取处理后状态
     */
    public String getAfterStatus() {
        return afterStatus;
    }

    /**
     * 获取处理方式
     */
    public String getHandleMethod() {
        return handleMethod;
    }

    /**
     * 获取处理结果
     */
    public String getHandleResult() {
        return handleResult;
    }

    /**
     * 获取处理描述
     */
    public String getHandleDesc() {
        return handleDesc;
    }

    /**
     * 获取创建时间
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * 获取更新时间
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    @Override
    public String toString() {
        return String.format(
                "AlertHandleRecord{id=%d, alertId=%d, handler='%s', handleTime=%s, beforeStatus='%s', afterStatus='%s', handleMethod='%s', handleResult='%s'}",
                id, alertId, handler, handleTime, beforeStatus, afterStatus, handleMethod, handleResult);
    }
}