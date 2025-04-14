package com.scenic.ai.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 告警信息实体类
 * 
 * @author scenic
 * @date 2024-03-19
 */
@Data
@Accessors(chain = true)
@TableName("ALERT")
public class Alert {
    
    /**
     * 主键ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    
    /**
     * 设备编码
     */
    @TableField("DEVICE_CODE")
    private String deviceCode;
    
    /**
     * 设备名称
     */
    @TableField("DEVICE_NAME")
    private String deviceName;
    
    /**
     * 景区名称
     */
    @TableField("TOURISM_NAME")
    private String tourismName;
    
    /**
     * 告警类型
     */
    @TableField("ALERT_TYPE")
    private String alertType;
    
    /**
     * 告警级别：1-低，2-中，3-高
     */
    @TableField("ALERT_LEVEL")
    private Integer alertLevel;
    
    /**
     * 告警状态：0-待处理，1-已处理
     */
    @TableField("ALERT_STATUS")
    private Integer alertStatus;
    
    /**
     * 告警描述
     */
    @TableField("DESCRIPTION")
    private String description;
    
    /**
     * 全景图URL
     */
    @TableField("IMAGE_URL")
    private String imageUrl;
    
    /**
     * 记录时间
     */
    @TableField("RECORD_TIME")
    private LocalDateTime recordTime;
    
    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
} 