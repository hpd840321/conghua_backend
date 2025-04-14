package com.scenic.ai.domain.model.alarm;

import lombok.Data;

import java.util.List;

@Data
public class BatchOperationResult {
    private Integer totalCount;
    private Integer successCount;
    private Integer failCount;
    private List<String> failedIds;
    private List<String> failedReasons;
} 