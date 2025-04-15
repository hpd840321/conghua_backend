package com.scenic.ai.dto;

import java.time.LocalDateTime;

/**
 * 第三方数据传输对象
 * 用于接收和处理第三方系统提供的数据
 *
 * @author scenic-AI
 * @version 1.0
 */
public class ThirdPartyDataDTO {
    
    /**
     * 唯一标识符
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
     * 告警事件
     */
    private String alarmEvent;
    
    /**
     * 图片数据
     */
    private String image;
    
    /**
     * 人数统计
     */
    private Integer count;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getTourismName() {
        return tourismName;
    }

    public void setTourismName(String tourismName) {
        this.tourismName = tourismName;
    }

    public String getAlarmEvent() {
        return alarmEvent;
    }

    public void setAlarmEvent(String alarmEvent) {
        this.alarmEvent = alarmEvent;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
} 