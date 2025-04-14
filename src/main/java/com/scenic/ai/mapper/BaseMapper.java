package com.scenic.ai.mapper;

import java.io.Serializable;
import java.util.List;

/**
 * 基础Mapper接口
 * 定义通用的数据库操作方法
 * 
 * @param <T> 实体类型
 * @param <ID> 主键类型
 */
public interface BaseMapper<T, ID extends Serializable> {
    
    /**
     * 插入一条记录
     * 
     * @param entity 实体对象
     * @return 影响行数
     */
    int insert(T entity);
    
    /**
     * 根据ID删除
     * 
     * @param id 主键ID
     * @return 影响行数
     */
    int deleteById(ID id);
    
    /**
     * 根据ID批量删除
     * 
     * @param ids ID集合
     * @return 影响行数
     */
    int deleteBatchIds(List<ID> ids);
    
    /**
     * 根据ID更新
     * 
     * @param entity 实体对象
     * @return 影响行数
     */
    int updateById(T entity);
    
    /**
     * 根据ID查询
     * 
     * @param id 主键ID
     * @return 实体
     */
    T selectById(ID id);
    
    /**
     * 查询所有
     * 
     * @return 实体列表
     */
    List<T> selectAll();
    
    /**
     * 根据ID集合查询
     * 
     * @param ids ID集合
     * @return 实体列表
     */
    List<T> selectBatchIds(List<ID> ids);
    
    /**
     * 查询总记录数
     * 
     * @return 总记录数
     */
    long count();
}
