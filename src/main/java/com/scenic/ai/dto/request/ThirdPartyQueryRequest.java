package com.scenic.ai.dto.request;

import lombok.Data;

/**
 * 第三方接口查询请求DTO
 */
@Data
public class ThirdPartyQueryRequest {
    /**
     * 页码(默认1)
     */
    private Integer pageNo = 1;

    /**
     * 分页大小(默认10)
     */
    private Integer pageSize = 10;

    /**
     * 景区名称
     */
    private String tourismName;

    /**
     * 数据来源
     */
    private String odsSource;

    /**
     * 算法类型
     */
    private String algName;

    /**
     * 设备编码
     */
    private String deviceCode;

    /**
     * 搜索开始日期(yyyy-MM-dd)
     */
    private String searchBeginDate;

    /**
     * 搜索结束日期(yyyy-MM-dd)
     */
    private String searchEndDate;
} 