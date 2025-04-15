package com.scenic.ai.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 调度器配置类
 */
@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(SchedulerConfig.class);
    
    public void configureTasks(@NonNull ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10); // Adjust based on your needs
        scheduler.setThreadNamePrefix("scheduled-task-");
        scheduler.setErrorHandler(t -> log.error("Error in scheduled task", t));
        scheduler.initialize();
        taskRegistrar.setTaskScheduler(scheduler);
    }
}
