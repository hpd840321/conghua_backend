package com.scenic.ai.common.core.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页查询参数
 * 
 * @author AI
 * @date 2023-05-20
 */
public class PageQuery {
    /**
     * 当前页码
     */
    private Integer pageNum = 1;

    /**
     * 每页显示记录数
     */
    private Integer pageSize = 10;

    /**
     * 查询参数
     */
    private Map<String, Object> params = new HashMap<>();

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
} 