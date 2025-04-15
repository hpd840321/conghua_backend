package com.scenic.ai.service;

import com.scenic.ai.domain.model.SyncProgress;
import com.scenic.ai.exception.SyncException;
import com.scenic.ai.model.RetryLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 同步服务
 * 负责处理数据同步任务
 */
@Service
public class SyncService {
    private static final Logger log = LoggerFactory.getLogger(SyncService.class);

    private final SyncRecordService syncRecordService;
    private final ThirdPartyApiService thirdPartyApiService;
    private final StorageService storageService;
    private final RetryTemplate syncRetryTemplate;
    private final RetryLogService retryLogService;

    // 同步进度跟踪
    private final Map<String, SyncProgress> progressMap = new ConcurrentHashMap<>();
    
    /**
     * 构造函数
     * 
     * @param syncRecordService 同步记录服务
     * @param thirdPartyApiService 第三方API服务
     * @param storageService 存储服务
     * @param syncRetryTemplate 同步重试模板
     * @param retryLogService 重试日志服务
     */
    public SyncService(SyncRecordService syncRecordService,
                      ThirdPartyApiService thirdPartyApiService,
                      StorageService storageService,
                      RetryTemplate syncRetryTemplate,
                      RetryLogService retryLogService) {
        this.syncRecordService = syncRecordService;
        this.thirdPartyApiService = thirdPartyApiService;
        this.storageService = storageService;
        this.syncRetryTemplate = syncRetryTemplate;
        this.retryLogService = retryLogService;
    }

    /**
     * 执行同步任务
     */
    @Transactional
    public void syncData(String syncType) {
        String syncId = UUID.randomUUID().toString();
        SyncProgress progress = new SyncProgress(syncId);
        progressMap.put(syncId, progress);

        try {
            log.info("开始{}同步任务", syncType);
            
            // 获取上次同步记录
            Map<String, Object> lastRecord = syncRecordService.getLatestSyncRecord(syncType);
            LocalDateTime lastSyncTime = lastRecord != null ? 
                (LocalDateTime) lastRecord.get("syncTime") : null;
            
            // 获取并处理数据
            syncRetryTemplate.execute(context -> {
                // 记录重试次数
                int retryCount = context.getRetryCount();
                updateRetryLog(syncType, syncId, retryCount);
                
                // 执行同步逻辑
                List<Map<String, Object>> data = thirdPartyApiService.getStatisticsData(lastSyncTime);
                processData(data, progress);
                
                // 更新同步记录
                syncRecordService.createSyncRecord(syncType, 1, null, data.size());
                log.info("{}同步任务完成，处理{}条数据", syncType, data.size());
                
                return null;
            });
        } catch (Exception e) {
            String errorMsg = "同步任务失败: " + e.getMessage();
            log.error(errorMsg, e);
            syncRecordService.createSyncRecord(syncType, 0, errorMsg, 0);
            throw new SyncException(errorMsg, e);
        } finally {
            progressMap.remove(syncId);
        }
    }

    /**
     * 处理同步数据
     */
    private void processData(List<Map<String, Object>> dataList, SyncProgress progress) {
        if (dataList == null || dataList.isEmpty()) {
            log.info("没有新数据需要同步");
            return;
        }

        int total = dataList.size();
        int processed = 0;

        for (Map<String, Object> data : dataList) {
            try {
                processDataItem(data);
                processed++;
                progress.updateProgress((float) processed / total);
            } catch (Exception e) {
                log.error("处理数据项失败: {}", data, e);
            }
        }
    }

    /**
     * 处理单条数据
     */
    private void processDataItem(Map<String, Object> data) throws IOException {
        // 处理图片
        String imageUrl = (String) data.get("imageUrl");
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                String localPath = syncRetryTemplate.execute(context -> {
                    log.debug("尝试下载图片，重试次数：{}", context.getRetryCount());
                    byte[] imageData = thirdPartyApiService.downloadImage(imageUrl);
                    return storageService.downloadAndStore(imageUrl);
                });
                data.put("localImagePath", localPath);
            } catch (Exception e) {
                log.error("图片处理失败: {}", imageUrl, e);
                data.put("localImagePath", null);
            }
        }
    }

    /**
     * 获取同步进度
     */
    public SyncProgress getProgress(String syncId) {
        return progressMap.get(syncId);
    }

    private void updateRetryLog(String businessType, String businessId, int retryCount) {
        RetryLog log = retryLogService.findByBusinessTypeAndId(businessType, businessId);
        if (log == null) {
            log = new RetryLog();
            log.setBusinessType(businessType);
            log.setBusinessId(businessId);
            log.setMaxRetryCount(3);
            log.setStatus("RETRYING");
        }
        log.setRetryCount(retryCount);
        retryLogService.saveOrUpdate(log);
    }
    
    private void saveFailedRetryLog(String businessType, String businessId, String errorMessage) {
        RetryLog log = retryLogService.findByBusinessTypeAndId(businessType, businessId);
        if (log != null) {
            log.setStatus("FAILED");
            log.setErrorMessage(errorMessage);
            retryLogService.updateById(log);
        }
    }
}
