package com.scenic.ai.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 客流分析实体类
 */
@Data
@TableName("FLOW_ANALYSIS")
public class FlowAnalysis {
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
     * 客流数量
     */
    private Integer flowCount;

    /**
     * 流动方向
     */
    private String flowDirection;

    /**
     * 算法类型
     */
    private String algName;

    /**
     * 任务编码
     */
    private String taskCode;

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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
} 