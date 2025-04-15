package com.scenic.ai.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 人群统计实体类
 * 
 * @author AI
 * @date 2024-04-15
 */
@Data
@TableName("crowd_statistics")
public class CrowdStatistics {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 设备编号
     */
    private String deviceCode;

    /**
     * 景区名称
     */
    private String tourismName;

    /**
     * 人群数量
     */
    private Integer crowdCount;

    /**
     * 人群密度
     */
    private Double crowdDensity;

    /**
     * 统计时间
     */
    private LocalDateTime statisticsTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;
}