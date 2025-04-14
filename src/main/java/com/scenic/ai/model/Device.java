package com.scenic.ai.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 设备信息实体类
 */
@Data
@TableName("DEVICE")
public class Device {
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
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
     * 设备类型(1:摄像头 2:传感器)
     */
    @TableField("DEVICE_TYPE")
    private Integer deviceType;
    
    /**
     * 景区名称
     */
    @TableField("TOURISM_NAME")
    private String tourismName;
    
    /**
     * 安装位置
     */
    @TableField("LOCATION")
    private String location;
    
    /**
     * IP地址
     */
    @TableField("IP_ADDRESS")
    private String ipAddress;
    
    /**
     * 端口号
     */
    @TableField("PORT")
    private Integer port;
    
    /**
     * 设备状态
     */
    @TableField("STATUS")
    private String status;
    
    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;
    
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
    
    /**
     * 设备状态枚举
     */
    public static class Status {
        public static final int OFFLINE = 0;  // 离线
        public static final int ONLINE = 1;   // 在线
        public static final int FAULT = 2;    // 故障
    }
} 