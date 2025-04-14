package com.scenic.ai.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 人群统计实体类
 * 
 * @author scenic
 * @date 2024-03-19
 */
@Data
@Accessors(chain = true)
@TableName("CROWD_STATISTICS")
public class CrowdStatistics {
    
    /**
     * 主键ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
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
} 