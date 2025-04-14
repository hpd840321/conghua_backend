package com.scenic.ai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scenic.ai.model.RetryLog;

/**
 * 重试日志服务接口
 */
public interface RetryLogService extends IService<RetryLog> {
    
    /**
     * 根据业务类型和业务ID获取重试日志
     */
    RetryLog getByBusinessTypeAndId(String businessType, String businessId);
} 