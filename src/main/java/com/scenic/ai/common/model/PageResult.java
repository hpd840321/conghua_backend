package com.scenic.ai.common.model;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 统一分页查询结果封装类
 *
 * @param <T> 数据项类型
 */
@Data
public class PageResult<T> {

    /**
     * 数据列表
     */
    private List<T> list;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页码
     */
    private Integer current;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer totalPages;

    /**
     * 是否有下一页
     */
    private Boolean hasNext;

    /**
     * 是否有上一页
     */
    private Boolean hasPrevious;

    /**
     * 创建分页结果实例
     *
     * @param list    数据列表
     * @param total   总记录数
     * @param request 分页请求参数
     * @return 分页结果实例
     */
    public static <T> PageResult<T> of(List<T> list, Long total, PageRequest request) {
        PageResult<T> result = new PageResult<>();
        result.setList(list);
        result.setTotal(total);
        result.setCurrent(request.getCurrent());
        result.setPageSize(request.getPageSize());

        // 计算总页数
        result.setTotalPages((int) Math.ceil((double) total / request.getPageSize()));

        // 计算是否有上一页和下一页
        result.setHasNext(request.getCurrent() < result.getTotalPages());
        result.setHasPrevious(request.getCurrent() > 1);

        return result;
    }

    /**
     * 创建空的分页结果实例
     *
     * @return 空的分页结果实例
     */
    public static <T> PageResult<T> empty() {
        PageResult<T> result = new PageResult<>();
        result.setList(Collections.emptyList());
        result.setTotal(0L);
        result.setCurrent(1);
        result.setPageSize(10);
        result.setTotalPages(0);
        result.setHasNext(false);
        result.setHasPrevious(false);
        return result;
    }

    /**
     * 获取下一页页码
     *
     * @return 下一页页码,如果没有下一页则返回null
     */
    public Integer getNextPage() {
        return hasNext ? current + 1 : null;
    }

    /**
     * 获取上一页页码
     *
     * @return 上一页页码,如果没有上一页则返回null
     */
    public Integer getPreviousPage() {
        return hasPrevious ? current - 1 : null;
    }

    /**
     * 是否为空结果
     *
     * @return 是否为空
     */
    public boolean isEmpty() {
        return list == null || list.isEmpty();
    }

    /**
     * 是否为第一页
     *
     * @return 是否为第一页
     */
    public boolean isFirstPage() {
        return current == 1;
    }

    /**
     * 是否为最后一页
     *
     * @return 是否为最后一页
     */
    public boolean isLastPage() {
        return current.equals(totalPages);
    }
} 