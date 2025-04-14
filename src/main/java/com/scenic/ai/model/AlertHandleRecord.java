package com.scenic.ai.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 告警处理记录实体类
 */
@Data
@TableName("ALERT_HANDLE_RECORD")
public class AlertHandleRecord {
    
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 告警ID
     */
    @TableField("ALERT_ID")
    private Long alertId;

    /**
     * 处理人
     */
    @TableField("HANDLER")
    private String handler;

    /**
     * 处理时间
     */
    @TableField("HANDLE_TIME")
    private LocalDateTime handleTime;

    /**
     * 处理前状态
     */
    @TableField("BEFORE_STATUS")
    private Integer beforeStatus;

    /**
     * 处理后状态
     */
    @TableField("AFTER_STATUS")
    private Integer afterStatus;

    /**
     * 处理备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;
} 