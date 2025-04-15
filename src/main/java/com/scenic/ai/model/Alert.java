package com.scenic.ai.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.scenic.ai.model.enums.AlertLevel;
import com.scenic.ai.model.enums.AlertStatus;
import com.scenic.ai.model.enums.AlertType;

import java.time.LocalDateTime;

/**
 * 告警信息实体类
 * 
 * @author AI
 * @date 2023-05-20
 */
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
     * 告警类型
     */
    @TableField("ALERT_TYPE")
    private String alertType;
    
    /**
     * 告警类型名称
     */
    @TableField(exist = false)
    private String alertTypeName;
    
    /**
     * 告警级别：1-低，2-中，3-高
     */
    @TableField("ALERT_LEVEL")
    private Integer alertLevel;
    
    /**
     * 告警状态：0-待处理，1-已处理
     */
    @TableField("ALERT_STATUS")
    private Integer alertStatus;
    
    /**
     * 告警描述
     */
    @TableField("DESCRIPTION")
    private String description;
    
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
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;
    
    /**
     * 告警值
     */
    @TableField("VALUE")
    private Double value;
    
    /**
     * 无参构造函数
     */
    public Alert() {
    }
    
    /**
     * 全参构造函数
     */
    public Alert(Long id, String deviceCode, String deviceName, String tourismName, 
                String alertType, Integer alertLevel, Integer alertStatus, 
                String description, String imageUrl, LocalDateTime recordTime, 
                LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.deviceCode = deviceCode;
        this.deviceName = deviceName;
        this.tourismName = tourismName;
        this.alertType = alertType;
        this.alertLevel = alertLevel;
        this.alertStatus = alertStatus;
        this.description = description;
        this.imageUrl = imageUrl;
        this.recordTime = recordTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
    
    /**
     * 获取告警类型枚举
     */
    public AlertType getAlertTypeEnum() {
        if (alertType == null) {
            return null;
        }
        return AlertType.valueOf(alertType);
    }
    
    /**
     * 获取告警级别枚举
     */
    public AlertLevel getAlertLevelEnum() {
        if (alertLevel == null) {
            return null;
        }
        return AlertLevel.fromValue(alertLevel);
    }
    
    /**
     * 获取告警状态枚举
     */
    public AlertStatus getAlertStatusEnum() {
        if (alertStatus == null) {
            return null;
        }
        return AlertStatus.fromValue(alertStatus);
    }
    
    @Override
    public String toString() {
        return "Alert{" +
                "id=" + id +
                ", deviceCode='" + deviceCode + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", tourismName='" + tourismName + '\'' +
                ", alertType='" + alertType + '\'' +
                ", alertTypeName='" + alertTypeName + '\'' +
                ", alertLevel=" + alertLevel +
                ", alertStatus=" + alertStatus +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", recordTime=" + recordTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", value=" + value +
                '}';
    }
    
    /**
     * 获取主键ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * 获取设备编码
     */
    public String getDeviceCode() {
        return deviceCode;
    }
    
    /**
     * 获取设备名称
     */
    public String getDeviceName() {
        return deviceName;
    }
    
    /**
     * 获取景区名称
     */
    public String getTourismName() {
        return tourismName;
    }
    
    /**
     * 获取告警类型
     */
    public String getAlertType() {
        return alertType;
    }
    
    /**
     * 获取告警级别
     */
    public Integer getAlertLevel() {
        return alertLevel;
    }
    
    /**
     * 获取告警状态
     */
    public Integer getAlertStatus() {
        return alertStatus;
    }
    
    /**
     * 获取告警描述
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * 获取全景图URL
     */
    public String getImageUrl() {
        return imageUrl;
    }
    
    /**
     * 获取记录时间
     */
    public LocalDateTime getRecordTime() {
        return recordTime;
    }
    
    /**
     * 获取创建时间
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    
    /**
     * 获取更新时间
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    
    /**
     * 获取告警值
     */
    public Double getValue() {
        return value;
    }
    
    /**
     * 获取告警类型名称
     */
    public String getAlertTypeName() {
        return alertTypeName;
    }
    
    /**
     * 设置主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * 设置设备编码
     */
    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }
    
    /**
     * 设置设备名称
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    
    /**
     * 设置景区名称
     */
    public void setTourismName(String tourismName) {
        this.tourismName = tourismName;
    }
    
    /**
     * 设置告警类型
     */
    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }
    
    /**
     * 设置告警级别
     */
    public void setAlertLevel(Integer alertLevel) {
        this.alertLevel = alertLevel;
    }
    
    /**
     * 设置告警状态
     */
    public void setAlertStatus(Integer alertStatus) {
        this.alertStatus = alertStatus;
    }
    
    /**
     * 设置告警描述
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * 设置全景图URL
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    /**
     * 设置记录时间
     */
    public void setRecordTime(LocalDateTime recordTime) {
        this.recordTime = recordTime;
    }
    
    /**
     * 设置创建时间
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    
    /**
     * 设置更新时间
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    
    /**
     * 设置告警值
     */
    public void setValue(Double value) {
        this.value = value;
    }
    
    /**
     * 设置告警类型名称
     */
    public void setAlertTypeName(String alertTypeName) {
        this.alertTypeName = alertTypeName;
    }
} 