package com.scenic.ai.common.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper<T> {
    /**
     * 插入一条记录
     */
    int insert(T entity);

    /**
     * 根据ID更新
     */
    int updateById(T entity);

    /**
     * 根据ID删除
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据ID查询
     */
    T selectById(@Param("id") Long id);

    /**
     * 查询所有记录
     */
    List<T> selectList();

    /**
     * 根据条件查询
     */
    List<T> selectByCondition(T condition);
} 