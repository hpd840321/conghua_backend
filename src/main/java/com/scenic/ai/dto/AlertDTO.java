package com.scenic.ai.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 告警数据传输对象
 * 用于在系统各层之间传递告警相关信息
 *
 * @author scenic-AI
 * @version 1.0
 */
@Data
public class AlertDTO {
    /**
     * 告警ID
     */
    private Long id;

    /**
     * 设备编码，用于标识告警来源设备
     */
    private String deviceCode;

    /**
     * 告警类型，如：人群密度过高、异常聚集等
     */
    private String alertType;

    /**
     * 告警级别，如：INFO、WARN、ERROR等
     */
    private String alertLevel;

    /**
     * 告警内容描述
     */
    private String alertContent;

    /**
     * 告警状态，如：未处理、已处理、已关闭等
     */
    private String status;

    /**
     * 告警发生时间
     */
    private LocalDateTime alertTime;

    /**
     * 告警处理时间
     */
    private LocalDateTime handleTime;

    /**
     * 处理人员
     */
    private String handler;

    /**
     * 处理结果描述
     */
    private String handleResult;

    /**
     * 关联的全景图URL
     */
    private String panoramaUrl;

    /**
     * 记录创建时间
     */
    private LocalDateTime createTime;

    /**
     * 记录更新时间
     */
    private LocalDateTime updateTime;
} 