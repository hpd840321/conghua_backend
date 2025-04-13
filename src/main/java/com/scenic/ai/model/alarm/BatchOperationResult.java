package com.conghua.tourism.model.alarm;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 批量操作结果类
 */
@Data
@NoArgsConstructor
public class BatchOperationResult {
    
    /**
     * 成功的ID列表
     */
    private List<String> successIds = new ArrayList<>();
    
    /**
     * 失败的ID及原因
     */
    private Map<String, String> failures = new HashMap<>();
    
    /**
     * 添加成功记录
     */
    public void addSuccess(String id) {
        successIds.add(id);
    }
    
    /**
     * 添加失败记录
     */
    public void addFailure(String id, String reason) {
        failures.put(id, reason);
    }
    
    /**
     * 是否全部成功
     */
    public boolean isAllSuccess() {
        return failures.isEmpty();
    }
    
    /**
     * 获取成功数量
     */
    public int getSuccessCount() {
        return successIds.size();
    }
    
    /**
     * 获取失败数量
     */
    public int getFailureCount() {
        return failures.size();
    }
    
    /**
     * 获取总处理数量
     */
    public int getTotalCount() {
        return getSuccessCount() + getFailureCount();
    }
} 