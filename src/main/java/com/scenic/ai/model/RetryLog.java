package com.scenic.ai.model;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

/**
 * 重试日志实体类
 */
@TableName("RETRY_LOG")
public class RetryLog {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 业务类型
     */
    @TableField("BUSINESS_TYPE")
    private String businessType;

    /**
     * 业务ID
     */
    @TableField("BUSINESS_ID")
    private String businessId;

    /**
     * 重试次数
     */
    @TableField("RETRY_COUNT")
    private Integer retryCount;

    /**
     * 最大重试次数
     */
    @TableField("MAX_RETRY_COUNT")
    private Integer maxRetryCount;

    /**
     * 状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 错误信息
     */
    @TableField("ERROR_MESSAGE")
    private String errorMessage;

    /**
     * 下次重试时间
     */
    @TableField("NEXT_RETRY_TIME")
    private LocalDateTime nextRetryTime;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 构造函数
    public RetryLog() {
    }

    public RetryLog(Long id, String businessType, String businessId, Integer retryCount,
                   Integer maxRetryCount, String status, String errorMessage,
                   LocalDateTime nextRetryTime, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.businessType = businessType;
        this.businessId = businessId;
        this.retryCount = retryCount;
        this.maxRetryCount = maxRetryCount;
        this.status = status;
        this.errorMessage = errorMessage;
        this.nextRetryTime = nextRetryTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // Getter方法
    public Long getId() {
        return id;
    }

    public String getBusinessType() {
        return businessType;
    }

    public String getBusinessId() {
        return businessId;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public Integer getMaxRetryCount() {
        return maxRetryCount;
    }

    public String getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getNextRetryTime() {
        return nextRetryTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    // Setter方法
    public void setId(Long id) {
        this.id = id;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public void setMaxRetryCount(Integer maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setNextRetryTime(LocalDateTime nextRetryTime) {
        this.nextRetryTime = nextRetryTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
} 