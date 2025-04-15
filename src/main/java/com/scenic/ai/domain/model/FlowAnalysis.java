package com.scenic.ai.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 客流分析实体类
 *
 * @author scenic
 * @date 2024-03-19
 */
@TableName("FLOW_ANALYSIS")
public class FlowAnalysis {
    
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
     * 客流数量
     */
    private Integer flowCount;
    
    /**
     * 流动方向
     */
    private String flowDirection;
    
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

    public Long getId() {
        return id;
    }

    public FlowAnalysis setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public FlowAnalysis setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
        return this;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public FlowAnalysis setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }

    public String getTourismName() {
        return tourismName;
    }

    public FlowAnalysis setTourismName(String tourismName) {
        this.tourismName = tourismName;
        return this;
    }

    public Integer getFlowCount() {
        return flowCount;
    }

    public FlowAnalysis setFlowCount(Integer flowCount) {
        this.flowCount = flowCount;
        return this;
    }

    public String getFlowDirection() {
        return flowDirection;
    }

    public FlowAnalysis setFlowDirection(String flowDirection) {
        this.flowDirection = flowDirection;
        return this;
    }

    public String getAlgName() {
        return algName;
    }

    public FlowAnalysis setAlgName(String algName) {
        this.algName = algName;
        return this;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public FlowAnalysis setTaskCode(String taskCode) {
        this.taskCode = taskCode;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public FlowAnalysis setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public LocalDateTime getRecordTime() {
        return recordTime;
    }

    public FlowAnalysis setRecordTime(LocalDateTime recordTime) {
        this.recordTime = recordTime;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public FlowAnalysis setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public FlowAnalysis setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    /**
     * 验证数据有效性
     */
    public void validate() {
        if (deviceCode == null || deviceCode.trim().isEmpty()) {
            throw new IllegalArgumentException("设备编码不能为空");
        }
        if (flowCount == null) {
            throw new IllegalArgumentException("客流数量不能为空");
        }
        if (flowDirection == null || flowDirection.trim().isEmpty()) {
            throw new IllegalArgumentException("流动方向不能为空");
        }
        if (recordTime == null) {
            throw new IllegalArgumentException("记录时间不能为空");
        }
    }

    /**
     * 创建新的客流分析记录
     */
    public static FlowAnalysis create(String deviceCode, String deviceName, String tourismName,
                                    Integer flowCount, String flowDirection, String algName,
                                    String taskCode, String imageUrl) {
        FlowAnalysis flowAnalysis = new FlowAnalysis()
            .setDeviceCode(deviceCode)
            .setDeviceName(deviceName)
            .setTourismName(tourismName)
            .setFlowCount(flowCount)
            .setFlowDirection(flowDirection)
            .setAlgName(algName)
            .setTaskCode(taskCode)
            .setImageUrl(imageUrl)
            .setRecordTime(LocalDateTime.now());
            
        flowAnalysis.validate();
        return flowAnalysis;
    }
}
