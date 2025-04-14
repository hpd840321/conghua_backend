package com.scenic.ai.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 告警页面控制器
 */
@Tag(name = "告警页面")
@Slf4j
@Controller
@RequestMapping("/alert")
public class AlertViewController {

    @Operation(summary = "告警首页")
    @GetMapping("/index")
    public String index() {
        log.info("访问告警首页");
        return "alert/index";
    }

    @Operation(summary = "告警详情页")
    @GetMapping("/detail")
    public String detail() {
        log.info("访问告警详情页");
        return "alert/detail";
    }
} 