package com.scenic.ai.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 人群统计数据传输对象
 * 用于传递景区人群统计相关信息
 *
 * @author scenic-AI
 * @version 1.0
 */
@Data
public class CrowdStatisticsDTO {
    /**
     * 统计记录ID
     */
    private Long id;

    /**
     * 设备编码，用于标识数据来源设备
     */
    private String deviceCode;

    /**
     * 人群数量统计值
     */
    private Integer crowdCount;

    /**
     * 记录时间
     */
    private LocalDateTime recordTime;

    /**
     * 区域名称，标识统计区域
     */
    private String regionName;

    /**
     * 关联的全景图URL，用于展示统计时的场景
     */
    private String panoramaUrl;
} 