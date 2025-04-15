package com.scenic.ai.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页控制器
 */
@Controller
@RequestMapping("/")
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    
    @GetMapping
    public String index() {
        log.info("访问首页");
        return "index";
    }
} 