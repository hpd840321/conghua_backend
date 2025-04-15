package com.scenic.ai.model;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

/**
 * 全景图实体类
 */
@TableName("panorama")
public class Panorama {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
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
     * 描述信息
     */
    private String description;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
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
    public Panorama setId(Long id) {
        this.id = id;
        return this;
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
    public Panorama setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
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
    public Panorama setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
        return this;
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
    public Panorama setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
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
    public Panorama setTourismName(String tourismName) {
        this.tourismName = tourismName;
        return this;
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
    public Panorama setWidth(Integer width) {
        this.width = width;
        return this;
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
    public Panorama setHeight(Integer height) {
        this.height = height;
        return this;
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
    public Panorama setDescription(String description) {
        this.description = description;
        return this;
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
    public Panorama setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
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
    public Panorama setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }
} 