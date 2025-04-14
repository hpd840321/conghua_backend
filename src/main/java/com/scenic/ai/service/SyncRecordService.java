package com.scenic.ai.service;

import com.scenic.ai.mapper.SyncRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 同步记录服务类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SyncRecordService {
    
    private final SyncRecordMapper syncRecordMapper;

    @Transactional(readOnly = true)
    public Map<String, Object> getLatestSyncRecord(String syncType) {
        return syncRecordMapper.findLatestByType(syncType);
    }

    @Transactional
    public int createSyncRecord(String syncType, Integer status, String errorMessage, Integer processedCount) {
        Map<String, Object> record = new HashMap<>();
        record.put("syncType", syncType);
        record.put("syncTime", LocalDateTime.now());
        record.put("status", status);
        record.put("errorMessage", errorMessage);
        record.put("processedCount", processedCount);
        
        return syncRecordMapper.insert(record);
    }
} 