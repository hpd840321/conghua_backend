package com.scenic.ai.dto;

import com.scenic.ai.common.model.PageRequest;
import com.scenic.ai.common.validation.ValidDeviceCode;
import com.scenic.ai.common.validation.ValidationGroups.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceQueryParams extends PageRequest {
    
    /**
     * 设备编码
     */
    @ValidDeviceCode(groups = Query.class)
    private String deviceCode;
    
    /**
     * 设备名称
     */
    private String deviceName;
    
    /**
     * 设备状态
     */
    private String status;
    
    /**
     * 景区名称
     */
    private String tourismName;
    
    /**
     * 开始时间
     */
    private String startTime;
    
    /**
     * 结束时间
     */
    private String endTime;
} 