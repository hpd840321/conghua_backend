package com.scenic.ai.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 密度记录领域模型
 */
public class DensityRecord {
    
    /**
     * 记录ID
     */
    private Long id;
    
    /**
     * 区域ID
     */
    private String areaId;
    
    /**
     * 区域名称
     */
    private String areaName;
    
    /**
     * 密度值
     */
    private Double density;
    
    /**
     * 密度等级(LOW/MEDIUM/HIGH)
     */
    private String level;
    
    /**
     * 记录时间
     */
    private LocalDateTime recordTime;
    
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
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    public DensityRecord() {
    }
    
    public Long getId() {
        return id;
    }
    
    public DensityRecord setId(Long id) {
        this.id = id;
        return this;
    }
    
    public String getAreaId() {
        return areaId;
    }
    
    public DensityRecord setAreaId(String areaId) {
        this.areaId = areaId;
        return this;
    }
    
    public String getAreaName() {
        return areaName;
    }
    
    public DensityRecord setAreaName(String areaName) {
        this.areaName = areaName;
        return this;
    }
    
    public Double getDensity() {
        return density;
    }
    
    public DensityRecord setDensity(Double density) {
        this.density = density;
        return this;
    }
    
    public String getLevel() {
        return level;
    }
    
    public DensityRecord setLevel(String level) {
        this.level = level;
        return this;
    }
    
    public LocalDateTime getRecordTime() {
        return recordTime;
    }
    
    public DensityRecord setRecordTime(LocalDateTime recordTime) {
        this.recordTime = recordTime;
        return this;
    }
    
    public String getDeviceCode() {
        return deviceCode;
    }
    
    public DensityRecord setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
        return this;
    }
    
    public String getDeviceName() {
        return deviceName;
    }
    
    public DensityRecord setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }
    
    public String getTourismName() {
        return tourismName;
    }
    
    public DensityRecord setTourismName(String tourismName) {
        this.tourismName = tourismName;
        return this;
    }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    
    public DensityRecord setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }
    
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    
    public DensityRecord setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    
    /**
     * 验证数据有效性
     */
    public void validate() {
        Objects.requireNonNull(areaId, "区域ID不能为空");
        Objects.requireNonNull(areaName, "区域名称不能为空");
        Objects.requireNonNull(density, "密度值不能为空");
        if (density < 0) {
            throw new IllegalArgumentException("密度值不能为负数");
        }
        Objects.requireNonNull(level, "密度等级不能为空");
        if (!level.equals("LOW") && !level.equals("MEDIUM") && !level.equals("HIGH")) {
            throw new IllegalArgumentException("密度等级必须是LOW、MEDIUM或HIGH");
        }
        Objects.requireNonNull(recordTime, "记录时间不能为空");
        Objects.requireNonNull(deviceCode, "设备编码不能为空");
    }
    
    /**
     * 创建新的密度记录
     */
    public static DensityRecord create(String areaId, String areaName, Double density,
                                     String level, String deviceCode, String deviceName,
                                     String tourismName) {
        DensityRecord record = new DensityRecord()
            .setAreaId(areaId)
            .setAreaName(areaName)
            .setDensity(density)
            .setLevel(level)
            .setDeviceCode(deviceCode)
            .setDeviceName(deviceName)
            .setTourismName(tourismName)
            .setRecordTime(LocalDateTime.now());
            
        record.validate();
        return record;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DensityRecord that = (DensityRecord) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(areaId, that.areaId) &&
               Objects.equals(areaName, that.areaName) &&
               Objects.equals(density, that.density) &&
               Objects.equals(level, that.level) &&
               Objects.equals(recordTime, that.recordTime) &&
               Objects.equals(deviceCode, that.deviceCode) &&
               Objects.equals(deviceName, that.deviceName) &&
               Objects.equals(tourismName, that.tourismName) &&
               Objects.equals(createTime, that.createTime) &&
               Objects.equals(updateTime, that.updateTime);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, areaId, areaName, density, level, recordTime, deviceCode,
                           deviceName, tourismName, createTime, updateTime);
    }
    
    @Override
    public String toString() {
        return "DensityRecord{" +
               "id=" + id +
               ", areaId='" + areaId + '\'' +
               ", areaName='" + areaName + '\'' +
               ", density=" + density +
               ", level='" + level + '\'' +
               ", recordTime=" + recordTime +
               ", deviceCode='" + deviceCode + '\'' +
               ", deviceName='" + deviceName + '\'' +
               ", tourismName='" + tourismName + '\'' +
               ", createTime=" + createTime +
               ", updateTime=" + updateTime +
               '}';
    }
} 