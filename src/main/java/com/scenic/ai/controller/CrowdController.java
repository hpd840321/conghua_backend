package com.scenic.ai.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.common.Result;
import com.scenic.ai.domain.model.CrowdStatistics;
import com.scenic.ai.service.CrowdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 人群分析控制器
 */
@Tag(name = "人群分析")
@Slf4j
@Controller
@RequestMapping("/crowd")
public class CrowdController {

    @Autowired
    private CrowdService crowdService;

    @Operation(summary = "人群分析首页")
    @GetMapping("/index")
    public String index() {
        log.info("访问人群分析首页");
        return "crowd/index";
    }

    /**
     * 分页查询监控数据
     */
    @GetMapping("/list")
    @ResponseBody
    public Result<IPage<CrowdStatistics>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            String tourismName,
            String deviceCode,
            String algName,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        
        IPage<CrowdStatistics> pageResult = crowdService.pageStatistics(
            new Page<>(page, limit),
            tourismName,
            deviceCode,
            algName,
            startTime,
            endTime
        );
        return Result.ok(pageResult);
    }

    /**
     * 获取概览数据
     */
    @GetMapping("/overview")
    @ResponseBody
    public Result<Map<String, Object>> getOverview(
            String tourismName,
            String deviceCode,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        
        Map<String, Object> data = crowdService.getOverview(tourismName, deviceCode, startTime, endTime);
        return Result.ok(data);
    }

    /**
     * 获取人群数量趋势
     */
    @GetMapping("/trend")
    @ResponseBody
    public Result<Map<String, Object>> getTrend(
            String tourismName,
            String deviceCode,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        
        Map<String, Object> data = crowdService.getTrend(tourismName, deviceCode, startTime, endTime);
        return Result.ok(data);
    }

    /**
     * 获取客流分布热力图数据
     */
    @GetMapping("/heatmap")
    @ResponseBody
    public Result<Map<String, Object>> getHeatmap(
            String tourismName,
            String deviceCode,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        
        Map<String, Object> data = crowdService.getHeatmap(tourismName, deviceCode, startTime, endTime);
        return Result.ok(data);
    }
} 