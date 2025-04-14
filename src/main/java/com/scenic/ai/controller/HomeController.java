package com.scenic.ai.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "首页")
@Slf4j
@Controller
public class HomeController {

    @Operation(summary = "首页")
    @GetMapping("/")
    public String index() {
        log.info("访问系统首页");
        return "redirect:/crowd/statistics/index";
    }
} 