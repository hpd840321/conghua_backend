package com.scenic.ai.common.converter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 通用DTO转换接口
 * @param <D> DTO类型
 * @param <E> 实体类型
 */
public interface BaseConverter<D, E> {
    /**
     * 实体转DTO
     */
    D toDTO(E entity);

    /**
     * DTO转实体
     */
    E toEntity(D dto);

    /**
     * 默认的批量实体转DTO实现
     */
    default List<D> toDTOList(List<E> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * 默认的批量DTO转实体实现
     */
    default List<E> toEntityList(List<D> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
} 