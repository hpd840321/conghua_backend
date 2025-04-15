package com.scenic.ai.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 设备实体类
 *
 * @author scenic
 * @date 2024-03-19
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
     * 设备状态
     */
    private String status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    public Device() {
    }
    
    public Long getId() {
        return id;
    }
    
    public Device setId(Long id) {
        this.id = id;
        return this;
    }
    
    public String getDeviceCode() {
        return deviceCode;
    }
    
    public Device setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
        return this;
    }
    
    public String getDeviceName() {
        return deviceName;
    }
    
    public Device setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }
    
    public String getTourismName() {
        return tourismName;
    }
    
    public Device setTourismName(String tourismName) {
        this.tourismName = tourismName;
        return this;
    }
    
    public String getStatus() {
        return status;
    }
    
    public Device setStatus(String status) {
        this.status = status;
        return this;
    }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    
    public Device setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }
    
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    
    public Device setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    
    /**
     * 验证数据有效性
     */
    public void validate() {
        Objects.requireNonNull(deviceCode, "设备编码不能为空");
        Objects.requireNonNull(deviceName, "设备名称不能为空");
        Objects.requireNonNull(tourismName, "景区名称不能为空");
        Objects.requireNonNull(status, "设备状态不能为空");
        if (createTime == null) {
            createTime = LocalDateTime.now();
        }
        if (updateTime == null) {
            updateTime = LocalDateTime.now();
        }
    }
    
    /**
     * 创建新的设备记录
     */
    public static Device create(String deviceCode, String deviceName, String tourismName, String status) {
        Device device = new Device()
            .setDeviceCode(deviceCode)
            .setDeviceName(deviceName)
            .setTourismName(tourismName)
            .setStatus(status)
            .setCreateTime(LocalDateTime.now())
            .setUpdateTime(LocalDateTime.now());
            
        device.validate();
        return device;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(id, device.id) &&
               Objects.equals(deviceCode, device.deviceCode) &&
               Objects.equals(deviceName, device.deviceName) &&
               Objects.equals(tourismName, device.tourismName) &&
               Objects.equals(status, device.status) &&
               Objects.equals(createTime, device.createTime) &&
               Objects.equals(updateTime, device.updateTime);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, deviceCode, deviceName, tourismName, status, createTime, updateTime);
    }
    
    @Override
    public String toString() {
        return "Device{" +
               "id=" + id +
               ", deviceCode='" + deviceCode + '\'' +
               ", deviceName='" + deviceName + '\'' +
               ", tourismName='" + tourismName + '\'' +
               ", status='" + status + '\'' +
               ", createTime=" + createTime +
               ", updateTime=" + updateTime +
               '}';
    }
} 