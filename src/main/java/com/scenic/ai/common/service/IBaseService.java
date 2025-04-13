package com.conghua.tourism.common.service;

import java.util.List;

public interface IBaseService<T> {
    /**
     * 新增
     */
    int create(T entity);

    /**
     * 更新
     */
    int update(T entity);

    /**
     * 删除
     */
    int delete(Long id);

    /**
     * 根据ID查询
     */
    T getById(Long id);

    /**
     * 查询所有
     */
    List<T> listAll();

    /**
     * 条件查询
     */
    List<T> listByCondition(T condition);
} 