package com.scenic.ai.service;

import com.scenic.ai.domain.model.Alert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 调度服务
 */
@Slf4j
@Service
public class SchedulerService {
    
    private final AlertService alertService;
    private final CrowdCountService crowdCountService;
    private final DensityAnalysisService densityAnalysisService;
    
    public SchedulerService(AlertService alertService,
                          CrowdCountService crowdCountService,
                          DensityAnalysisService densityAnalysisService) {
        this.alertService = alertService;
        this.crowdCountService = crowdCountService;
        this.densityAnalysisService = densityAnalysisService;
    }
    
    /**
     * 定时检查告警
     */
    @Scheduled(cron = "${scheduler.alert.check.cron:0 */5 * * * ?}")
    public void checkAlerts() {
        log.info("开始检查告警条件");
        try {
            // 检查人流量告警
            checkFlowAlerts();
            // 检查密度告警
            checkDensityAlerts();
            log.info("告警检查完成");
        } catch (Exception e) {
            log.error("告警检查失败", e);
            throw e;
        }
    }
    
    /**
     * 检查人流量告警
     */
    private void checkFlowAlerts() {
        List<Alert> alerts = crowdCountService.findExceedThresholdCounts();
        for (Alert alert : alerts) {
            alertService.createAlert(alert);
        }
    }
    
    /**
     * 检查密度告警
     */
    private void checkDensityAlerts() {
        List<Alert> alerts = densityAnalysisService.findExceedThresholdDensities();
        for (Alert alert : alerts) {
            alertService.createAlert(alert);
        }
    }
    
 
}
