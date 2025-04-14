package com.scenic.ai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    
    @GetMapping("/")
    public String index() {
        return "layout/main";
    }
    
    @GetMapping("/event/list")
    public String eventList() {
        return "event/list";
    }
    
    @GetMapping("/theme/list")
    public String themeList() {
        return "theme/list";
    }
    
    @GetMapping("/traffic/civilized")
    public String trafficCivilized() {
        return "traffic/civilized";
    }
    
    @GetMapping("/traffic/realtime")
    public String trafficRealtime() {
        return "traffic/realtime";
    }
    
    @GetMapping("/video/analysis")
    public String videoAnalysis() {
        return "crowd/statistics";
    }
    
    @GetMapping("/task/list")
    public String taskList() {
        return "task/list";
    }
    
    @GetMapping("/system/settings")
    public String systemSettings() {
        return "system/settings";
    }
} 