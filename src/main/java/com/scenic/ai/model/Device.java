package com.scenic.ai.model;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

/**
 * 设备实体类
 */
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
    private Integer status;
    
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

    // 构造函数
    public Device() {
    }

    public Device(Long id, String deviceCode, String deviceName, Integer deviceType, String tourismName,
                 String location, String ipAddress, Integer port, Integer status, String remark,
                 LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.deviceCode = deviceCode;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.tourismName = tourismName;
        this.location = location;
        this.ipAddress = ipAddress;
        this.port = port;
        this.status = status;
        this.remark = remark;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // Getter方法
    public Long getId() {
        return id;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public String getTourismName() {
        return tourismName;
    }

    public String getLocation() {
        return location;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Integer getPort() {
        return port;
    }

    public Integer getStatus() {
        return status;
    }

    public String getRemark() {
        return remark;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    // Setter方法
    public void setId(Long id) {
        this.id = id;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public void setTourismName(String tourismName) {
        this.tourismName = tourismName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
} 