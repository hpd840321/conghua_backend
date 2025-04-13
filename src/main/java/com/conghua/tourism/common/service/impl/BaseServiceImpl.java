package com.conghua.tourism.common.service.impl;

import com.conghua.tourism.common.mapper.BaseMapper;
import com.conghua.tourism.common.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> implements IBaseService<T> {
    
    @Autowired
    protected M baseMapper;

    @Override
    public int create(T entity) {
        return baseMapper.insert(entity);
    }

    @Override
    public int update(T entity) {
        return baseMapper.updateById(entity);
    }

    @Override
    public int delete(Long id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public T getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public List<T> listAll() {
        return baseMapper.selectList();
    }

    @Override
    public List<T> listByCondition(T condition) {
        return baseMapper.selectByCondition(condition);
    }
} 