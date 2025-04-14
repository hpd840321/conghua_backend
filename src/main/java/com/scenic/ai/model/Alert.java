package com.scenic.ai.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 告警信息实体类
 */
@Data
@TableName("ALERT")
public class Alert {
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 设备编码
     */
    private String deviceCode;
    
    /**
     * 设备名称
     */
    private String deviceName;
    
    /**
     * 景区名称
     */
    private String tourismName;
    
    /**
     * 告警类型
     */
    private String alertType;
    
    /**
     * 告警级别：1-低，2-中，3-高
     */
    private Integer alertLevel;
    
    /**
     * 状态：0-待处理，1-已处理
     */
    private Integer alertStatus;
    
    /**
     * 告警描述
     */
    private String description;
    
    /**
     * 全景图URL
     */
    private String imageUrl;
    
    /**
     * 记录时间
     */
    private LocalDateTime recordTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 