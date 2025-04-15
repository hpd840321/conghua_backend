package com.scenic.ai.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 全景图实体类
 */
public class Panorama implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 图片URL
     */
    private String imageUrl;

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
     * 图片宽度
     */
    private Integer width;

    /**
     * 图片高度
     */
    private Integer height;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 获取主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取图片URL
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置图片URL
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 获取设备编码
     */
    public String getDeviceCode() {
        return deviceCode;
    }

    /**
     * 设置设备编码
     */
    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    /**
     * 获取设备名称
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * 设置设备名称
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * 获取景区名称
     */
    public String getTourismName() {
        return tourismName;
    }

    /**
     * 设置景区名称
     */
    public void setTourismName(String tourismName) {
        this.tourismName = tourismName;
    }

    /**
     * 获取图片宽度
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * 设置图片宽度
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * 获取图片高度
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * 设置图片高度
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * 获取描述信息
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述信息
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取创建时间
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}