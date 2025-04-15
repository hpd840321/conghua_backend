package com.scenic.ai.common.core.domain;

import java.util.List;

/**
 * 分页结果
 * 
 * @author AI
 * @date 2023-05-20
 */
public class PageResult<T> {
    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页显示记录数
     */
    private Integer pageSize;

    /**
     * 数据列表
     */
    private List<T> list;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

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

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
} 