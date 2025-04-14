package com.scenic.ai.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 人群计数实体类
 */
@Data
@Accessors(chain = true)
@TableName("CROWD_STATISTICS")
public class CrowdCount {
    
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    
    @TableField("DEVICE_CODE")
    private String deviceCode;
    
    @TableField("DEVICE_NAME")
    private String deviceName;
    
    @TableField("TOURISM_NAME")
    private String tourismName;
    
    @TableField("COUNT")
    private Integer count;
    
    @TableField("DENSITY")
    private Double density;
    
    @TableField("ALG_NAME")
    private String algName;
    
    @TableField("TASK_CODE")
    private String taskCode;
    
    @TableField("IMAGE_URL")
    private String imageUrl;
    
    @TableField("RECORD_TIME")
    private LocalDateTime recordTime;
    
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;
    
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;
    
    /**
     * 验证数据有效性
     */
    public void validate() {
        if (deviceCode == null || deviceCode.trim().isEmpty()) {
            throw new IllegalArgumentException("设备编码不能为空");
        }
        if (count == null) {
            throw new IllegalArgumentException("计数不能为空");
        }
        if (density == null) {
            throw new IllegalArgumentException("密度不能为空");
        }
        if (recordTime == null) {
            throw new IllegalArgumentException("记录时间不能为空");
        }
    }
    
    /**
     * 创建新的人群计数记录
     */
    public static CrowdCount create(String deviceCode, String deviceName, String tourismName,
                                  Integer count, Double density, String algName, 
                                  String taskCode, String imageUrl) {
        CrowdCount crowdCount = new CrowdCount()
            .setDeviceCode(deviceCode)
            .setDeviceName(deviceName)
            .setTourismName(tourismName)
            .setCount(count)
            .setDensity(density)
            .setAlgName(algName)
            .setTaskCode(taskCode)
            .setImageUrl(imageUrl)
            .setRecordTime(LocalDateTime.now());
            
        crowdCount.validate();
        return crowdCount;
    }
    
    /**
     * 计算增长率
     */
    public double calculateGrowthRate(CrowdCount previous) {
        if (previous == null || previous.getCount() == 0) {
            return 0.0;
        }
        return ((double) this.count - previous.getCount()) / previous.getCount() * 100;
    }
    
    /**
     * 判断是否超过阈值
     */
    public boolean isOverThreshold(int threshold) {
        return this.count > threshold;
    }
    
    @Override
    public String toString() {
        return String.format("CrowdCount{deviceCode='%s', deviceName='%s', count=%d, density=%.2f}", 
                           deviceCode, deviceName, count, density);
    }
} 