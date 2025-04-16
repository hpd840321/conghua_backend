package com.scenic.ai.util;

import com.scenic.ai.domain.model.CrowdStatisticsDomain;
import com.scenic.ai.entity.CrowdStatistics;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 人群统计转换器
 */
public class CrowdStatisticsConverter {

    /**
     * 将数据库实体转换为领域模型
     */
    public static CrowdStatisticsDomain toDomain(CrowdStatistics entity) {
        if (entity == null) {
            return null;
        }
        return new CrowdStatisticsDomain(
                String.valueOf(entity.getId()),
                entity.getDeviceCode(),
                entity.getDeviceName(),
                entity.getTourismName(),
                entity.getCount(),
                entity.getDensity() != null ? BigDecimal.valueOf(entity.getDensity()) : null,
                entity.getAlgName(),
                entity.getTaskCode(),
                entity.getImageUrl(),
                entity.getRecordTime(),
                entity.getCreateTime());
    }

    /**
     * 将领域模型转换为数据库实体
     */
    public static CrowdStatistics toEntity(CrowdStatisticsDomain domain) {
        if (domain == null) {
            return null;
        }
        CrowdStatistics entity = new CrowdStatistics();
        entity.setId(Long.parseLong(domain.getId()));
        entity.setDeviceCode(domain.getDeviceCode());
        entity.setDeviceName(domain.getDeviceName());
        entity.setTourismName(domain.getTourismName());
        entity.setCount(domain.getCount());
        entity.setDensity(domain.getDensity() != null ? domain.getDensity().doubleValue() : null);
        entity.setAlgName(domain.getAlgName());
        entity.setTaskCode(domain.getTaskCode());
        entity.setImageUrl(domain.getImageUrl());
        entity.setRecordTime(domain.getRecordTime());
        entity.setCreateTime(domain.getCreateTime());
        entity.setUpdateTime(domain.getCreateTime());
        return entity;
    }

    /**
     * 批量转换为领域模型
     */
    public static List<CrowdStatisticsDomain> toDomains(List<CrowdStatistics> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(CrowdStatisticsConverter::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * 批量转换为数据库实体
     */
    public static List<CrowdStatistics> toEntities(List<CrowdStatisticsDomain> domains) {
        if (domains == null) {
            return null;
        }
        return domains.stream()
                .map(CrowdStatisticsConverter::toEntity)
                .collect(Collectors.toList());
    }
}