package com.scenic.ai.model;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 重试日志实体类
 */
@Data
@TableName("RETRY_LOG")
@Schema(description = "重试日志")
public class RetryLog {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 业务类型
     */
    @TableField("BUSINESS_TYPE")
    @Schema(description = "业务类型")
    private String businessType;

    /**
     * 业务ID
     */
    @TableField("BUSINESS_ID")
    @Schema(description = "业务ID")
    private String businessId;

    /**
     * 重试次数
     */
    @TableField("RETRY_COUNT")
    @Schema(description = "重试次数")
    private Integer retryCount;

    /**
     * 最大重试次数
     */
    @TableField("MAX_RETRY_COUNT")
    @Schema(description = "最大重试次数")
    private Integer maxRetryCount;

    /**
     * 状态
     */
    @TableField("STATUS")
    @Schema(description = "状态")
    private String status;

    /**
     * 错误信息
     */
    @TableField("ERROR_MESSAGE")
    @Schema(description = "错误信息")
    private String errorMessage;

    /**
     * 下次重试时间
     */
    @TableField("NEXT_RETRY_TIME")
    @Schema(description = "下次重试时间")
    private LocalDateTime nextRetryTime;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 