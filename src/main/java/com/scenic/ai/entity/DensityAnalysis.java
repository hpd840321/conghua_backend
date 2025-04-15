package com.scenic.ai.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 密度分析实体类
 */
public class DensityAnalysis implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
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
     * 景区名称
     */
    private String tourismName;

    /**
     * 密度计数
     */
    private Integer densityCount;

    /**
     * 密度等级
     */
    private String densityLevel;

    /**
     * 算法名称
     */
    private String algName;

    /**
     * 任务编码
     */
    private String taskCode;

    /**
     * 图片URL
     */
    private String imageUrl;

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

    public Integer getDensityCount() {
        return densityCount;
    }

    public void setDensityCount(Integer densityCount) {
        this.densityCount = densityCount;
    }

    public String getDensityLevel() {
        return densityLevel;
    }

    public void setDensityLevel(String densityLevel) {
        this.densityLevel = densityLevel;
    }

    public String getAlgName() {
        return algName;
    }

    public void setAlgName(String algName) {
        this.algName = algName;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(LocalDateTime recordTime) {
        this.recordTime = recordTime;
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