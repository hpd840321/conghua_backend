package com.scenic.ai.domain.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 告警处理记录模型
 */
@Data
public class AlertHandleRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
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
     * 处理时间
     */
    private LocalDateTime handleTime;

    /**
     * 处理前状态
     */
    private Integer beforeStatus;

    /**
     * 处理后状态
     */
    private Integer afterStatus;

    /**
     * 处理方式
     */
    private String handleMethod;

    /**
     * 处理结果
     */
    private String handleResult;

    /**
     * 处理描述
     */
    private String handleDesc;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}