package com.scenic.ai.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 客流分析实体类
 * 
 * @author scenic
 * @date 2024-03-19
 */
@Data
@Accessors(chain = true)
@TableName("FLOW_ANALYSIS")
public class FlowAnalysis {
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
     * 客流数量
     */
    @TableField("FLOW_COUNT")
    private Integer flowCount;

    /**
     * 流动方向
     */
    @TableField("FLOW_DIRECTION")
    private String flowDirection;

    /**
     * 算法类型
     */
    @TableField("ALG_NAME")
    private String algName;

    /**
     * 任务编码
     */
    @TableField("TASK_CODE")
    private String taskCode;

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