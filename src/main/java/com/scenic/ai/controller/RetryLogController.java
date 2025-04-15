package com.scenic.ai.controller;

import com.scenic.ai.model.RetryLog;
import com.scenic.ai.service.RetryLogService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 重试日志控制器
 */
@RestController
@RequestMapping("/api/v1/retry-logs")
public class RetryLogController {
    
    private final RetryLogService retryLogService;
    
    public RetryLogController(RetryLogService retryLogService) {
        this.retryLogService = retryLogService;
    }
    
    /**
     * 创建重试日志
     */
    @PostMapping
    public ResponseEntity<RetryLog> createRetryLog(
            @RequestParam String businessType,
            @RequestParam String businessId,
            @RequestParam(defaultValue = "3") int maxRetryCount,
            @RequestParam(required = false) String errorMessage) {
        RetryLog retryLog = retryLogService.createRetryLog(businessType, businessId, maxRetryCount, errorMessage);
        return ResponseEntity.ok(retryLog);
    }
    
    /**
     * 更新重试信息
     */
    @PutMapping("/{id}/retry-info")
    public ResponseEntity<Boolean> updateRetryInfo(
            @PathVariable Long id,
            @RequestParam int retryCount,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime nextRetryTime,
            @RequestParam(required = false) String errorMessage) {
        boolean success = retryLogService.updateRetryInfo(id, retryCount, nextRetryTime, errorMessage);
        return ResponseEntity.ok(success);
    }
    
    /**
     * 更新状态
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Boolean> updateStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam(required = false) String errorMessage) {
        boolean success = retryLogService.updateStatus(id, status, errorMessage);
        return ResponseEntity.ok(success);
    }
    
    /**
     * 查询需要重试的任务
     */
    @GetMapping("/retry-tasks")
    public ResponseEntity<List<RetryLog>> findRetryTasks(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime nextRetryTime,
            @RequestParam String status) {
        List<RetryLog> tasks = retryLogService.findRetryTasks(nextRetryTime, status);
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * 根据业务类型和业务ID查询
     */
    @GetMapping("/by-business")
    public ResponseEntity<RetryLog> findByBusinessTypeAndId(
            @RequestParam String businessType,
            @RequestParam String businessId) {
        RetryLog retryLog = retryLogService.findByBusinessTypeAndId(businessType, businessId);
        return ResponseEntity.ok(retryLog);
    }
    
    /**
     * 根据状态查询
     */
    @GetMapping("/by-status")
    public ResponseEntity<List<RetryLog>> findByStatus(
            @RequestParam String status) {
        List<RetryLog> logs = retryLogService.findByStatus(status);
        return ResponseEntity.ok(logs);
    }
    
    /**
     * 统计各业务类型重试次数
     */
    @GetMapping("/stats/by-business-type")
    public ResponseEntity<List<Map<String, Object>>> countByBusinessType(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        List<Map<String, Object>> stats = retryLogService.countByBusinessType(startTime, endTime);
        return ResponseEntity.ok(stats);
    }
    
    /**
     * 统计重试状态分布
     */
    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Map<String, Object>>> countByStatus(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        List<Map<String, Object>> stats = retryLogService.countByStatus(startTime, endTime);
        return ResponseEntity.ok(stats);
    }
    
    /**
     * 条件分页查询
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> findByConditions(
            @RequestParam(required = false) String businessType,
            @RequestParam(required = false) String businessId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("businessType", businessType);
        params.put("businessId", businessId);
        params.put("status", status);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("offset", (pageNum - 1) * pageSize);
        params.put("limit", pageSize);
        
        List<RetryLog> records = retryLogService.findByConditions(params);
        Long total = retryLogService.countRecords(params);
        
        Map<String, Object> result = new HashMap<>();
        result.put("records", records);
        result.put("total", total);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        
        return ResponseEntity.ok(result);
    }
    
    /**
     * 清理成功的重试日志
     */
    @DeleteMapping("/clean")
    public ResponseEntity<Integer> cleanSuccessLogs(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beforeTime) {
        int count = retryLogService.cleanSuccessLogs(beforeTime);
        return ResponseEntity.ok(count);
    }
} 