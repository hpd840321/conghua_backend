package com.scenic.ai.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 人群统计实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrowdStatistics {
    
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
     * 计数
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
} 