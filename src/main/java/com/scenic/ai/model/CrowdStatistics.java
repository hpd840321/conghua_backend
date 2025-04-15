package com.scenic.ai.model;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 人群统计实体类
 * 
 * @author AI
 * @date 2023-05-20
 */
@TableName("CROWD_STATISTICS")
public class CrowdStatistics {
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
     * 人数
     */
    @TableField("COUNT")
    private Integer count;
    
    /**
     * 密度
     */
    @TableField("DENSITY")
    private BigDecimal density;
    
    /**
     * 算法类型
     */
    @TableField("ALG_NAME")
    private String algName;
    
    /**
     * 任务编码
     */
    @TableField("TASK_CODE")
    private String taskCode;
    
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
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    /**
     * 开始时间（查询条件）
     */
    @TableField(exist = false)
    private LocalDateTime startTime;
    
    /**
     * 结束时间（查询条件）
     */
    @TableField(exist = false)
    private LocalDateTime endTime;

    // 构造函数
    public CrowdStatistics() {
    }

    public CrowdStatistics(Long id, String deviceCode, String deviceName, String tourismName,
                          Integer count, BigDecimal density, String algName, String taskCode,
                          String imageUrl, LocalDateTime recordTime, LocalDateTime createTime,
                          LocalDateTime updateTime) {
        this.id = id;
        this.deviceCode = deviceCode;
        this.deviceName = deviceName;
        this.tourismName = tourismName;
        this.count = count;
        this.density = density;
        this.algName = algName;
        this.taskCode = taskCode;
        this.imageUrl = imageUrl;
        this.recordTime = recordTime;
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

    public String getTourismName() {
        return tourismName;
    }

    public Integer getCount() {
        return count;
    }

    public BigDecimal getDensity() {
        return density;
    }

    public String getAlgName() {
        return algName;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public LocalDateTime getRecordTime() {
        return recordTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
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

    public void setTourismName(String tourismName) {
        this.tourismName = tourismName;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setDensity(BigDecimal density) {
        this.density = density;
    }

    public void setAlgName(String algName) {
        this.algName = algName;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setRecordTime(LocalDateTime recordTime) {
        this.recordTime = recordTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "CrowdStatistics{" +
                "id=" + id +
                ", deviceCode='" + deviceCode + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", tourismName='" + tourismName + '\'' +
                ", count=" + count +
                ", density=" + density +
                ", algName='" + algName + '\'' +
                ", taskCode='" + taskCode + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", recordTime=" + recordTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
} 