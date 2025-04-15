package com.scenic.ai.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * 人群统计领域模型
 */
public class CrowdStatisticsDomain {
    
    private final String id;
    private final String deviceCode;
    private final String deviceName;
    private final String tourismName;
    private final Integer count;
    private final BigDecimal density;
    private final String algName;
    private final String taskCode;
    private final String imageUrl;
    private final LocalDateTime recordTime;
    private final LocalDateTime createTime;
    
    /**
     * 构造函数
     */
    public CrowdStatisticsDomain(String id, String deviceCode, String deviceName, 
                               String tourismName, Integer count, BigDecimal density,
                               String algName, String taskCode, String imageUrl,
                               LocalDateTime recordTime, LocalDateTime createTime) {
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
        
        validate();
    }
    
    /**
     * 创建新的统计记录
     */
    public static CrowdStatisticsDomain create(String deviceCode, String deviceName,
                                             String tourismName, Integer count,
                                             BigDecimal density, String algName,
                                             String taskCode, String imageUrl) {
        String id = generateId();
        LocalDateTime now = LocalDateTime.now();
        
        return new CrowdStatisticsDomain(id, deviceCode, deviceName, tourismName,
                                       count, density, algName, taskCode, imageUrl,
                                       now, now);
    }
    
    /**
     * 生成唯一ID
     */
    private static String generateId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * 验证数据有效性
     */
    private void validate() {
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
     * 判断是否为高密度
     */
    public boolean isHighDensity() {
        return density != null && density.compareTo(new BigDecimal("0.8")) >= 0;
    }
    
    /**
     * 获取密度等级
     */
    public String getDensityLevel() {
        if (density == null) {
            return "未知";
        }
        
        if (density.compareTo(new BigDecimal("0.3")) < 0) {
            return "低密度";
        } else if (density.compareTo(new BigDecimal("0.7")) < 0) {
            return "中密度";
        } else {
            return "高密度";
        }
    }
    
    /**
     * Getter方法
     */
    public String getId() {
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
    
    @Override
    public String toString() {
        return "CrowdStatisticsDomain{" +
               "id='" + id + '\'' +
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
               '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrowdStatisticsDomain that = (CrowdStatisticsDomain) o;
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
               Objects.equals(createTime, that.createTime);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, deviceCode, deviceName, tourismName, count, density, algName,
                           taskCode, imageUrl, recordTime, createTime);
    }
} 