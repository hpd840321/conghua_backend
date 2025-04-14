package com.scenic.ai.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * 设备实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {
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
     * 设备类型(1:摄像头 2:传感器)
     */
    private Integer deviceType;

    /**
     * 景区名称
     */
    private String tourismName;

    /**
     * 安装位置
     */
    private String location;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 设备状态(0:离线 1:在线)
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 