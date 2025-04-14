package com.scenic.ai.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 人群统计控制器
 */
@Controller
@RequestMapping("/crowd/statistics")
public class CrowdStatisticsController {

    private static final Logger log = LoggerFactory.getLogger(CrowdStatisticsController.class);
    
    @GetMapping("/index")
    public String index() {
        log.info("Accessing crowd statistics homepage");
        return "crowd/statistics/index";
    }
} 