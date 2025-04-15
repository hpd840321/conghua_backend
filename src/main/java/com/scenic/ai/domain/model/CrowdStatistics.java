package com.scenic.ai.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 人群统计实体类
 *
 * @author scenic
 * @date 2024-03-19
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
     * 人数
     */
    private Integer count;
    
    /**
     * 密度
     */
    private BigDecimal density;
    
    /**
     * 算法类型
     */
    private String algName;
    
    /**
     * 任务编码
     */
    private String taskCode;
    
    /**
     * 全景图URL
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
    
    public CrowdStatistics() {
    }
    
    public Long getId() {
        return id;
    }
    
    public CrowdStatistics setId(Long id) {
        this.id = id;
        return this;
    }
    
    public String getDeviceCode() {
        return deviceCode;
    }
    
    public CrowdStatistics setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
        return this;
    }
    
    public String getDeviceName() {
        return deviceName;
    }
    
    public CrowdStatistics setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }
    
    public String getTourismName() {
        return tourismName;
    }
    
    public CrowdStatistics setTourismName(String tourismName) {
        this.tourismName = tourismName;
        return this;
    }
    
    public Integer getCount() {
        return count;
    }
    
    public CrowdStatistics setCount(Integer count) {
        this.count = count;
        return this;
    }
    
    public BigDecimal getDensity() {
        return density;
    }
    
    public CrowdStatistics setDensity(BigDecimal density) {
        this.density = density;
        return this;
    }
    
    public String getAlgName() {
        return algName;
    }
    
    public CrowdStatistics setAlgName(String algName) {
        this.algName = algName;
        return this;
    }
    
    public String getTaskCode() {
        return taskCode;
    }
    
    public CrowdStatistics setTaskCode(String taskCode) {
        this.taskCode = taskCode;
        return this;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public CrowdStatistics setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
    
    public LocalDateTime getRecordTime() {
        return recordTime;
    }
    
    public CrowdStatistics setRecordTime(LocalDateTime recordTime) {
        this.recordTime = recordTime;
        return this;
    }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    
    public CrowdStatistics setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }
    
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    
    public CrowdStatistics setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    
    /**
     * 验证数据有效性
     */
    public void validate() {
        Objects.requireNonNull(deviceCode, "设备编码不能为空");
        Objects.requireNonNull(count, "人数不能为空");
        if (count < 0) {
            throw new IllegalArgumentException("人数不能为负数");
        }
        if (density != null && density.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("密度不能为负数");
        }
        if (recordTime == null) {
            throw new IllegalArgumentException("记录时间不能为空");
        }
    }
    
    /**
     * 创建新的人群统计记录
     */
    public static CrowdStatistics create(String deviceCode, String deviceName, String tourismName,
                                       Integer count, BigDecimal density, String algName,
                                       String taskCode, String imageUrl) {
        CrowdStatistics statistics = new CrowdStatistics()
            .setDeviceCode(deviceCode)
            .setDeviceName(deviceName)
            .setTourismName(tourismName)
            .setCount(count)
            .setDensity(density)
            .setAlgName(algName)
            .setTaskCode(taskCode)
            .setImageUrl(imageUrl)
            .setRecordTime(LocalDateTime.now());
            
        statistics.validate();
        return statistics;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrowdStatistics that = (CrowdStatistics) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(deviceCode, that.deviceCode) &&
               Objects.equals(deviceName, that.deviceName) &&
               Objects.equals(tourismName, that.tourismName) &&
               Objects.equals(count, that.count) &&
               Objects.equals(density, that.density) &&
               Objects.equals(algName, that.algName) &&
               Objects.equals(taskCode, that.taskCode) &&
               Objects.equals(imageUrl, that.imageUrl) &&
               Objects.equals(recordTime, that.recordTime) &&
               Objects.equals(createTime, that.createTime) &&
               Objects.equals(updateTime, that.updateTime);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, deviceCode, deviceName, tourismName, count, density, algName,
                           taskCode, imageUrl, recordTime, createTime, updateTime);
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