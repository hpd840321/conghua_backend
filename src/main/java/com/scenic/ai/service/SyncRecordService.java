package com.scenic.ai.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 同步记录服务接口
 */
@Service
public interface SyncRecordService {
    
 
    


    @Transactional(readOnly = true)
    Map<String, Object> getLatestSyncRecord(String syncType);

    @Transactional
    int createSyncRecord(String syncType, Integer status, String errorMessage, Integer processedCount);
} 