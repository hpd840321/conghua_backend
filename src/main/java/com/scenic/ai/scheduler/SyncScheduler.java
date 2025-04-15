package com.scenic.ai.scheduler;

import com.scenic.ai.service.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 同步调度器
 * <p>
 * 该组件负责定时调度数据同步任务。
 * 同步任务的执行频率可通过配置文件进行自定义。
 */
@Component
public class SyncScheduler {

    private static final Logger log = LoggerFactory.getLogger(SyncScheduler.class);
    
    @Autowired
    private SyncService syncService;

    /**
     * 调度每日同步任务
     */
    @Scheduled(cron = "${sync.daily.cron:0 0 2 * * ?}")
    public void scheduleDailySync() {
        log.info("开始执行每日同步任务");
        syncService.syncData("DAILY");
    }

    /**
     * 调度小时级同步任务
     */
    @Scheduled(cron = "${sync.hourly.cron:0 0 * * * ?}")
    public void scheduleHourlySync() {
        log.info("开始执行小时级同步任务");
        syncService.syncData("HOURLY");
    }
}
