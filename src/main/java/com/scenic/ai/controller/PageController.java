package com.scenic.ai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/pages/crowd-count")
    public String crowdCount() {
        return "crowd-count";
    }
    
    @GetMapping("/pages/alert-center")
    public String alertCenter() {
        return "alert-center";
    }
} 