package com.scenic.ai.util;

import com.scenic.ai.domain.model.AlertDomain;
import com.scenic.ai.model.Alert;
import com.scenic.ai.model.enums.AlertLevel;
import com.scenic.ai.model.enums.AlertStatus;
import com.scenic.ai.model.enums.AlertType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Alert实体与领域模型转换工具类
 */
public class AlertConverter {
    
    /**
     * 将数据库实体转换为领域模型
     */
    public static AlertDomain toAlertDomain(Alert alert) {
        if (alert == null) {
            return null;
        }
        return AlertDomain.createWithValue(
            alert.getAlertTypeEnum().name(),
            alert.getAlertLevelEnum().name(),
            alert.getDeviceCode(),
            alert.getDeviceName(),
            alert.getTourismName(),
            alert.getDescription(),
            alert.getValue()
        );
    }
    
    /**
     * 将领域模型转换为数据库实体
     */
    public static Alert toAlert(AlertDomain domain) {
        if (domain == null) {
            return null;
        }
        Alert alert = new Alert();
        alert.setId(Long.parseLong(domain.getId()));
        alert.setAlertType(AlertType.valueOf(domain.getType()).getValue());
        alert.setAlertLevel(AlertLevel.valueOf(domain.getLevel()).getValue());
        alert.setDeviceCode(domain.getDeviceCode());
        alert.setDeviceName(domain.getDeviceName());
        alert.setTourismName(domain.getTourismName());
        alert.setDescription(domain.getDescription());
        alert.setValue(domain.getValue());
        alert.setAlertStatus(AlertStatus.fromValue(0).getValue());  // 默认待处理状态
        alert.setRecordTime(domain.getCreateTime());
        alert.setCreateTime(domain.getCreateTime());
        alert.setUpdateTime(domain.getCreateTime());
        return alert;
    }
    
    /**
     * 批量转换为领域模型
     */
    public static List<AlertDomain> toAlertDomains(List<Alert> alerts) {
        if (alerts == null) {
            return null;
        }
        return alerts.stream()
            .map(AlertConverter::toAlertDomain)
            .collect(Collectors.toList());
    }
    
    /**
     * 批量转换为数据库实体
     */
    public static List<Alert> toAlerts(List<AlertDomain> domains) {
        if (domains == null) {
            return null;
        }
        return domains.stream()
            .map(AlertConverter::toAlert)
            .collect(Collectors.toList());
    }
} 