package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.mapper.RetryLogMapper;
import com.scenic.ai.model.RetryLog;
import com.scenic.ai.service.RetryLogService;
import org.springframework.stereotype.Service;

/**
 * 重试日志服务实现类
 */
@Service
public class RetryLogServiceImpl extends ServiceImpl<RetryLogMapper, RetryLog> implements RetryLogService {
    
    @Override
    public RetryLog getByBusinessTypeAndId(String businessType, String businessId) {
        return lambdaQuery()
                .eq(RetryLog::getBusinessType, businessType)
                .eq(RetryLog::getBusinessId, businessId)
                .one();
    }
} 