package com.scenic.ai.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 人群统计实体类
 */
@Data
@Accessors(chain = true)
@TableName("CLOUDWALK.CROWD_STATISTICS")
public class CrowdStatistics {
    
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
} 