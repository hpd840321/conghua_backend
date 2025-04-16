package com.scenic.ai.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.common.core.domain.AjaxResult;
import com.scenic.ai.entity.CrowdStatistics;
import com.scenic.ai.service.ICrowdStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

/**
 * 人群统计控制器
 */
@Controller
@RequestMapping("/crowd-statistics")
public class CrowdStatisticsController {

    @Autowired
    private ICrowdStatisticsService crowdStatisticsService;

    /**
     * 人群统计页面
     */
    @GetMapping("/list")
    public String list() {
        return "crowd/list";
    }

    /**
     * 人群统计详情页面
     */
    @GetMapping("/detail")
    public String detail() {
        return "crowd/detail";
    }

    /**
     * 分页查询人群统计数据
     */
    @GetMapping("/page")
    @ResponseBody
    public AjaxResult page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        try {
            Page<CrowdStatistics> page = new Page<>(pageNum, pageSize);
            return AjaxResult.success(
                    crowdStatisticsService.pageByConditions(page, deviceCode, tourismName, startTime, endTime));
        } catch (Exception e) {
            return AjaxResult.error("查询人群统计数据失败：" + e.getMessage());
        }
    }

    /**
     * 获取总人数统计
     */
    @GetMapping("/total")
    @ResponseBody
    public AjaxResult getTotalCount(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        try {
            return AjaxResult.success(crowdStatisticsService.countTotalCrowd(startTime, endTime));
        } catch (Exception e) {
            return AjaxResult.error("获取总人数统计失败：" + e.getMessage());
        }
    }

    /**
     * 获取平均密度
     */
    @GetMapping("/average-density")
    @ResponseBody
    public AjaxResult getAverageDensity(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        try {
            return AjaxResult.success(crowdStatisticsService.getAverageDensity(startTime, endTime));
        } catch (Exception e) {
            return AjaxResult.error("获取平均密度失败：" + e.getMessage());
        }
    }

    /**
     * 获取最大人数
     */
    @GetMapping("/max-count")
    @ResponseBody
    public AjaxResult getMaxCount(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        try {
            return AjaxResult.success(crowdStatisticsService.getMaxCrowdCount(startTime, endTime));
        } catch (Exception e) {
            return AjaxResult.error("获取最大人数失败：" + e.getMessage());
        }
    }

    /**
     * 获取最小人数
     */
    @GetMapping("/min-count")
    @ResponseBody
    public AjaxResult getMinCount(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        try {
            return AjaxResult.success(crowdStatisticsService.getMinCrowdCount(startTime, endTime));
        } catch (Exception e) {
            return AjaxResult.error("获取最小人数失败：" + e.getMessage());
        }
    }

    /**
     * 获取景区分布
     */
    @GetMapping("/tourism-distribution")
    @ResponseBody
    public AjaxResult getTourismDistribution(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        try {
            return AjaxResult.success(crowdStatisticsService.getTourismDistribution(startTime, endTime));
        } catch (Exception e) {
            return AjaxResult.error("获取景区分布失败：" + e.getMessage());
        }
    }

    /**
     * 获取设备分布
     */
    @GetMapping("/device-distribution")
    @ResponseBody
    public AjaxResult getDeviceDistribution(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        try {
            return AjaxResult.success(crowdStatisticsService.getDeviceDistribution(startTime, endTime));
        } catch (Exception e) {
            return AjaxResult.error("获取设备分布失败：" + e.getMessage());
        }
    }

    /**
     * 获取时间趋势
     */
    @GetMapping("/time-trend")
    @ResponseBody
    public AjaxResult getTimeTrend(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @RequestParam(defaultValue = "1") Integer interval) {
        try {
            return AjaxResult.success(crowdStatisticsService.getTimeTrend(startTime, endTime, interval));
        } catch (Exception e) {
            return AjaxResult.error("获取时间趋势失败：" + e.getMessage());
        }
    }
}