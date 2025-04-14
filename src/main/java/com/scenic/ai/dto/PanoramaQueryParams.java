package com.scenic.ai.dto;

import com.scenic.ai.common.model.PageRequest;
import com.scenic.ai.common.validation.ValidDeviceCode;
import com.scenic.ai.common.validation.ValidationGroups.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 全景图查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PanoramaQueryParams extends PageRequest {
    /**
     * 关键词搜索
     */
    private String keyword;

    /**
     * 设备编码
     */
    @ValidDeviceCode(groups = Query.class)
    private String deviceCode;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 景区名称
     */
    private String tourismName;
} 