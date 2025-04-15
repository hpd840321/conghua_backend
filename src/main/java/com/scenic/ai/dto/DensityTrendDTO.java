package com.scenic.ai.dto;

import java.time.LocalDateTime;

/**
 * 密度趋势数据传输对象
 */
public class DensityTrendDTO {
    /**
     * 设备编码
     */
    private String deviceCode;
    
    /**
     * 设备名称
     */
    private String deviceName;
    
    /**
     * 密度值
     */
    private Double density;
    
    /**
     * 记录时间
     */
    private LocalDateTime recordTime;

    private DensityTrendDTO() {
    }

    public static Builder builder() {
        return new Builder();
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

    public Double getDensity() {
        return density;
    }

    public void setDensity(Double density) {
        this.density = density;
    }

    public LocalDateTime getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(LocalDateTime recordTime) {
        this.recordTime = recordTime;
    }

    public static class Builder {
        private final DensityTrendDTO dto;

        private Builder() {
            dto = new DensityTrendDTO();
        }

        public Builder deviceCode(String deviceCode) {
            dto.setDeviceCode(deviceCode);
            return this;
        }

        public Builder deviceName(String deviceName) {
            dto.setDeviceName(deviceName);
            return this;
        }

        public Builder density(Double density) {
            dto.setDensity(density);
            return this;
        }

        public Builder recordTime(LocalDateTime recordTime) {
            dto.setRecordTime(recordTime);
            return this;
        }

        public DensityTrendDTO build() {
            return dto;
        }
    }
} 