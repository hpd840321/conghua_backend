package com.scenic.ai.model;

import java.time.LocalDateTime;

/**
 * 密度分析实体类
 */
public class DensityAnalysis {
    private Long id;
    private String deviceCode;
    private String deviceName;
    private String tourismName;
    private Integer densityCount;
    private String densityLevel;
    private String algName;
    private String taskCode;
    private String imageUrl;
    private LocalDateTime recordTime;
    private LocalDateTime createTime;
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