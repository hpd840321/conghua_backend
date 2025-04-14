package com.scenic.ai.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.common.Result;
import com.scenic.ai.domain.model.CrowdCount;
import com.scenic.ai.service.CrowdCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 人群统计控制器
 */
@RestController
@RequestMapping("/client/third")
public class CrowdCountController {
    
    @Autowired
    private CrowdCountService crowdCountService;
    
    /**
     * 分页查询
     */
    @GetMapping("/getPage")
    public Result<Page<CrowdCount>> getPage(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String tourismName,
            @RequestParam(required = false) String deviceCode,
            @RequestParam(required = false) String algName,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime searchBeginDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime searchEndDate) {
        
        Page<CrowdCount> page = new Page<>(pageNo, pageSize);
        page = crowdCountService.getPage(page, tourismName, deviceCode, algName, 
                                       searchBeginDate, searchEndDate);
        return Result.ok(page);
    }
    
    /**
     * 获取详情分页
     */
    @GetMapping("/getDetailsPage")
    public Result<Page<CrowdCount>> getDetailsPage(
            @RequestParam String deviceCode,
            @RequestParam String algName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime recordBeginDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime recordEndDate,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        
        Page<CrowdCount> page = new Page<>(pageNo, pageSize);
        page = crowdCountService.getDetailsPage(page, deviceCode, algName, 
                                              recordBeginDate, recordEndDate);
        return Result.ok(page);
    }
} 