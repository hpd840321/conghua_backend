package com.scenic.ai.scheduler;

import com.scenic.ai.service.SyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 同步调度器
 * <p>
 * 该组件负责定时调度数据同步任务。
 * 同步任务的执行频率可通过配置文件进行自定义。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SyncScheduler {
    private final SyncService syncService;

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
