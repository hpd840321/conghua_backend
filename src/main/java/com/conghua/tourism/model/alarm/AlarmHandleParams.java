package com.conghua.tourism.model.alarm;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Map;

/**
 * 告警处理参数类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmHandleParams {
    
    /**
     * 告警ID
     */
    private String id;
    
    /**
     * 目标状态
     */
    private AlarmStatus targetStatus;
    
    /**
     * 处理人
     */
    private String handler;
    
    /**
     * 处理备注
     */
    private String remark;
    
    /**
     * 处理时间
     */
    private String handleTime;
    
    /**
     * 是否需要通知
     */
    private Boolean needNotify;
    
    /**
     * 通知渠道列表
     */
    private List<String> notificationChannels;
    
    /**
     * 附加信息
     */
    private Map<String, Object> extraInfo;
} 