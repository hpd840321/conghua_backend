package com.scenic.ai.domain.model.sync;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 同步任务实体类
 */
@Data
public class SyncTask {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 任务编码
     */
    private String taskCode;
    
    /**
     * 任务类型
     */
    private String taskType;
    
    /**
     * 任务状态（0-待执行，1-执行中，2-执行成功，3-执行失败）
     */
    private Integer status;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 错误信息
     */
    private String errorMessage;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 