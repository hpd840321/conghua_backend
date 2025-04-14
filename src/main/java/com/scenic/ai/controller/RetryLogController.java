package com.scenic.ai.controller;

import com.scenic.ai.model.RetryLog;
import com.scenic.ai.service.RetryLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
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
@Api(tags = "重试日志管理")
@RestController
@RequestMapping("/api/v1/retry-logs")
@RequiredArgsConstructor
public class RetryLogController {
    
    private final RetryLogService retryLogService;
    
    @ApiOperation("创建重试日志")
    @PostMapping
    public ResponseEntity<RetryLog> createRetryLog(
            @ApiParam("业务类型") @RequestParam String businessType,
            @ApiParam("业务ID") @RequestParam String businessId,
            @ApiParam("最大重试次数") @RequestParam(defaultValue = "3") int maxRetryCount,
            @ApiParam("错误信息") @RequestParam(required = false) String errorMessage) {
        RetryLog retryLog = retryLogService.createRetryLog(businessType, businessId, maxRetryCount, errorMessage);
        return ResponseEntity.ok(retryLog);
    }
    
    @ApiOperation("更新重试信息")
    @PutMapping("/{id}/retry-info")
    public ResponseEntity<Boolean> updateRetryInfo(
            @ApiParam("重试日志ID") @PathVariable Long id,
            @ApiParam("重试次数") @RequestParam int retryCount,
            @ApiParam("下次重试时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime nextRetryTime,
            @ApiParam("错误信息") @RequestParam(required = false) String errorMessage) {
        boolean success = retryLogService.updateRetryInfo(id, retryCount, nextRetryTime, errorMessage);
        return ResponseEntity.ok(success);
    }
    
    @ApiOperation("更新状态")
    @PutMapping("/{id}/status")
    public ResponseEntity<Boolean> updateStatus(
            @ApiParam("重试日志ID") @PathVariable Long id,
            @ApiParam("状态") @RequestParam String status,
            @ApiParam("错误信息") @RequestParam(required = false) String errorMessage) {
        boolean success = retryLogService.updateStatus(id, status, errorMessage);
        return ResponseEntity.ok(success);
    }
    
    @ApiOperation("查询需要重试的任务")
    @GetMapping("/retry-tasks")
    public ResponseEntity<List<RetryLog>> findRetryTasks(
            @ApiParam("下次重试时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime nextRetryTime,
            @ApiParam("状态") @RequestParam String status) {
        List<RetryLog> tasks = retryLogService.findRetryTasks(nextRetryTime, status);
        return ResponseEntity.ok(tasks);
    }
    
    @ApiOperation("根据业务类型和业务ID查询")
    @GetMapping("/by-business")
    public ResponseEntity<RetryLog> findByBusinessTypeAndId(
            @ApiParam("业务类型") @RequestParam String businessType,
            @ApiParam("业务ID") @RequestParam String businessId) {
        RetryLog retryLog = retryLogService.findByBusinessTypeAndId(businessType, businessId);
        return ResponseEntity.ok(retryLog);
    }
    
    @ApiOperation("根据状态查询")
    @GetMapping("/by-status")
    public ResponseEntity<List<RetryLog>> findByStatus(
            @ApiParam("状态") @RequestParam String status) {
        List<RetryLog> logs = retryLogService.findByStatus(status);
        return ResponseEntity.ok(logs);
    }
    
    @ApiOperation("统计各业务类型重试次数")
    @GetMapping("/stats/by-business-type")
    public ResponseEntity<List<Map<String, Object>>> countByBusinessType(
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        List<Map<String, Object>> stats = retryLogService.countByBusinessType(startTime, endTime);
        return ResponseEntity.ok(stats);
    }
    
    @ApiOperation("统计重试状态分布")
    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Map<String, Object>>> countByStatus(
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        List<Map<String, Object>> stats = retryLogService.countByStatus(startTime, endTime);
        return ResponseEntity.ok(stats);
    }
    
    @ApiOperation("条件分页查询")
    @GetMapping
    public ResponseEntity<Map<String, Object>> findByConditions(
            @ApiParam("业务类型") @RequestParam(required = false) String businessType,
            @ApiParam("业务ID") @RequestParam(required = false) String businessId,
            @ApiParam("状态") @RequestParam(required = false) String status,
            @ApiParam("开始时间") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @ApiParam("页码") @RequestParam(defaultValue = "1") int pageNum,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") int pageSize) {
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
    
    @ApiOperation("清理成功的重试日志")
    @DeleteMapping("/clean")
    public ResponseEntity<Integer> cleanSuccessLogs(
            @ApiParam("指定时间之前的记录") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beforeTime) {
        int count = retryLogService.cleanSuccessLogs(beforeTime);
        return ResponseEntity.ok(count);
    }
} 