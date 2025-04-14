package com.scenic.ai.common.page;

import lombok.Data;

/**
 * 分页请求基类
 */
@Data
public class PageRequest {
    /**
     * 页码(从1开始)
     */
    private Integer pageNo = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序方向(asc/desc)
     */
    private String sortOrder;

    /**
     * 获取偏移量
     */
    public int getOffset() {
        return (pageNo - 1) * pageSize;
    }

    /**
     * 获取限制数
     */
    public int getLimit() {
        return pageSize;
    }
} 