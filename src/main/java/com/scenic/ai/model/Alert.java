package com.scenic.ai.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * 警报实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 设备编号
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
     * 警报类型(1:人群密度过高 2:人群聚集 3:异常行为)
     */
    private Integer alertType;

    /**
     * 警报级别(1:一般 2:重要 3:紧急)
     */
    private Integer alertLevel;

    /**
     * 警报内容
     */
    private String alertContent;

    /**
     * 警报图片URL
     */
    private String imageUrl;

    /**
     * 处理状态(0:未处理 1:已处理)
     */
    private Integer status;

    /**
     * 处理备注
     */
    private String remark;

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