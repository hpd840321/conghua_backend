package com.scenic.ai.controller.visitor;

import com.scenic.ai.model.visitor.VisitorStats;
import com.scenic.ai.service.visitor.VisitorStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 访客统计控制器
 */
@RestController
@RequestMapping("/api/v1/visitor-stats")
public class VisitorStatsController {

    @Autowired
    private VisitorStatsService visitorStatsService;
    
    /**
     * 获取实时访客统计
     */
    @GetMapping("/{areaId}/realtime")
    public VisitorStats getRealTimeStats(@PathVariable String areaId) {
        return visitorStatsService.getRealTimeStats(areaId);
    }
    
    /**
     * 获取访客趋势
     */
    @GetMapping("/{areaId}/trend")
    public List<Map<String,Object>> getVisitorTrend(
        @PathVariable String areaId,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
        @RequestParam(defaultValue = "%Y-%m-%d %H:00:00") String interval
    ) {
        return visitorStatsService.getVisitorTrend(areaId, startTime, endTime, interval);
    }
    
    /**
     * 获取访客来源分布
     */
    @GetMapping("/{areaId}/source-distribution")
    public List<Map<String,Object>> getSourceDistribution(
        @PathVariable String areaId,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
        @RequestParam(defaultValue = "10") int limit
    ) {
        return visitorStatsService.getSourceDistribution(areaId, startTime, endTime, limit);
    }
    
    /**
     * 获取访客年龄分布
     */
    @GetMapping("/{areaId}/age-distribution")
    public List<Map<String,Object>> getAgeDistribution(
        @PathVariable String areaId,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime
    ) {
        return visitorStatsService.getAgeDistribution(areaId, startTime, endTime);
    }
    
    /**
     * 获取访客性别分布
     */
    @GetMapping("/{areaId}/gender-distribution")
    public List<Map<String,Object>> getGenderDistribution(
        @PathVariable String areaId,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime
    ) {
        return visitorStatsService.getGenderDistribution(areaId, startTime, endTime);
    }
    
    /**
     * 获取热门景点排行
     */
    @GetMapping("/{areaId}/hot-spots")
    public List<Map<String,Object>> getHotSpotRanking(
        @PathVariable String areaId,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
        @RequestParam(defaultValue = "10") int limit
    ) {
        return visitorStatsService.getHotSpotRanking(areaId, startTime, endTime, limit);
    }
    
    /**
     * 获取停留时长分布
     */
    @GetMapping("/{areaId}/stay-duration")
    public List<Map<String,Object>> getStayDurationDistribution(
        @PathVariable String areaId,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime
    ) {
        return visitorStatsService.getStayDurationDistribution(areaId, startTime, endTime);
    }
} 