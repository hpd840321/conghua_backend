package com.scenic.ai.domain.model.sync;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 同步重试日志实体类
 */
@Data
public class RetryLog {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 业务类型
     */
    private String businessType;
    
    /**
     * 业务ID
     */
    private String businessId;
    
    /**
     * 重试次数
     */
    private Integer retryCount;
    
    /**
     * 最大重试次数
     */
    private Integer maxRetryCount;
    
    /**
     * 状态（0-待重试，1-重试成功，2-重试失败）
     */
    private Integer status;
    
    /**
     * 错误信息
     */
    private String errorMessage;
    
    /**
     * 下次重试时间
     */
    private LocalDateTime nextRetryTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 