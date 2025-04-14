package com.scenic.ai.common.model;

import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * 统一分页查询基础参数类
 * 所有需要分页的查询请求都应该继承此类
 */
@Data
public class PageRequest {
    /**
     * 当前页码
     */
    @Min(value = 1, message = "页码必须大于0")
    private Integer current = 1;

    /**
     * 每页大小
     */
    @Min(value = 1, message = "每页大小必须大于0")
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序方向：ascend（升序）、descend（降序）
     */
    private String sortOrder;

    /**
     * 获取偏移量
     */
    public int getOffset() {
        return (current - 1) * pageSize;
    }

    /**
     * 获取限制数
     */
    public int getLimit() {
        return pageSize;
    }

    /**
     * 是否需要排序
     */
    public boolean needSort() {
        return sortField != null && !sortField.isEmpty() && sortOrder != null && !sortOrder.isEmpty();
    }

    /**
     * 是否是升序
     */
    public boolean isAscending() {
        return "ascend".equals(sortOrder);
    }
} 