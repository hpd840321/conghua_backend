package com.scenic.ai.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 告警处理记录实体类
 * 
 * @author scenic
 */
@Data
@TableName("ALERT_HANDLING_RECORD")
public class AlertHandlingRecord {
    
    /**
     * 主键ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
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
} 