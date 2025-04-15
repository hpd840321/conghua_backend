package com.scenic.ai.controller;

import com.scenic.ai.common.AjaxResult;
import com.scenic.ai.domain.model.AlertHandleRecord;
import com.scenic.ai.service.AlertHandleRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 告警处理记录控制器
 */
@RestController
@RequestMapping("/api/alert-handle-record")
public class AlertHandleRecordController {

    private static final Logger log = LoggerFactory.getLogger(AlertHandleRecordController.class);

    private final AlertHandleRecordService alertHandleRecordService;

    @Autowired
    public AlertHandleRecordController(AlertHandleRecordService alertHandleRecordService) {
        this.alertHandleRecordService = alertHandleRecordService;
    }

    /**
     * 根据告警ID查询处理记录
     *
     * @param alertId 告警ID
     * @return 处理记录列表
     */
    @GetMapping("/by-alert/{alertId}")
    public AjaxResult listByAlertId(@PathVariable Long alertId) {
        try {
            List<AlertHandleRecord> records = alertHandleRecordService.listByAlertId(alertId);
            return AjaxResult.success(records);
        } catch (Exception e) {
            log.error("查询告警处理记录失败, alertId: {}", alertId, e);
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 根据处理人查询处理记录
     *
     * @param handler 处理人
     * @return 处理记录列表
     */
    @GetMapping("/by-handler/{handler}")
    public AjaxResult listByHandler(@PathVariable String handler) {
        try {
            List<AlertHandleRecord> records = alertHandleRecordService.listByHandler(handler);
            return AjaxResult.success(records);
        } catch (Exception e) {
            log.error("查询告警处理记录失败, handler: {}", handler, e);
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 统计处理方式分布
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 处理方式统计结果
     */
    @GetMapping("/count-by-method")
    public AjaxResult countByHandleMethod(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String endTime) {
        try {
            List<Map<String, Object>> result = alertHandleRecordService.countByHandleMethod(startTime, endTime);
            return AjaxResult.success(result);
        } catch (Exception e) {
            log.error("统计处理方式分布失败, startTime: {}, endTime: {}", startTime, endTime, e);
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 统计处理结果分布
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 处理结果统计结果
     */
    @GetMapping("/count-by-result")
    public AjaxResult countByHandleResult(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String endTime) {
        try {
            List<Map<String, Object>> result = alertHandleRecordService.countByHandleResult(startTime, endTime);
            return AjaxResult.success(result);
        } catch (Exception e) {
            log.error("统计处理结果分布失败, startTime: {}, endTime: {}", startTime, endTime, e);
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 分页查询处理记录
     *
     * @param pageNum      页码
     * @param pageSize     每页大小
     * @param alertId      告警ID
     * @param handler      处理人
     * @param handleMethod 处理方式
     * @param handleResult 处理结果
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @return 处理记录列表
     */
    @GetMapping("/page")
    public AjaxResult page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long alertId,
            @RequestParam(required = false) String handler,
            @RequestParam(required = false) String handleMethod,
            @RequestParam(required = false) String handleResult,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String endTime) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("pageNum", pageNum);
            params.put("pageSize", pageSize);
            params.put("alertId", alertId);
            params.put("handler", handler);
            params.put("handleMethod", handleMethod);
            params.put("handleResult", handleResult);
            params.put("startTime", startTime);
            params.put("endTime", endTime);

            List<AlertHandleRecord> records = alertHandleRecordService.pageByConditions(params);
            return AjaxResult.success(records);
        } catch (Exception e) {
            log.error("分页查询告警处理记录失败", e);
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 新增处理记录
     *
     * @param record 处理记录
     * @return 操作结果
     */
    @PostMapping
    public AjaxResult save(@RequestBody AlertHandleRecord record) {
        try {
            boolean result = alertHandleRecordService.save(record);
            return result ? AjaxResult.success() : AjaxResult.error("新增处理记录失败");
        } catch (Exception e) {
            log.error("新增告警处理记录失败, record: {}", record, e);
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 更新处理记录
     *
     * @param record 处理记录
     * @return 操作结果
     */
    @PutMapping
    public AjaxResult update(@RequestBody AlertHandleRecord record) {
        try {
            boolean result = alertHandleRecordService.update(record);
            return result ? AjaxResult.success() : AjaxResult.error("更新处理记录失败");
        } catch (Exception e) {
            log.error("更新告警处理记录失败, record: {}", record, e);
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 删除处理记录
     *
     * @param id 记录ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable Long id) {
        try {
            boolean result = alertHandleRecordService.delete(id);
            return result ? AjaxResult.success() : AjaxResult.error("删除处理记录失败");
        } catch (Exception e) {
            log.error("删除告警处理记录失败, id: {}", id, e);
            return AjaxResult.error(e.getMessage());
        }
    }
}