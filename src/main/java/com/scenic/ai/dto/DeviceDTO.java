package com.scenic.ai.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 设备数据传输对象
 */
@Data
public class DeviceDTO {
    
    /**
     * 设备ID
     */
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
     * 设备类型
     */
    private String deviceType;
    
    /**
     * 设备状态
     */
    private String status;
    
    /**
     * 设备位置
     */
    private String location;
    
    /**
     * 视频流地址
     */
    private String streamUrl;
    
    /**
     * 景区名称
     */
    private String tourismName;
    
    /**
     * IP地址
     */
    private String ipAddress;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 备注
     */
    private String remark;
} 