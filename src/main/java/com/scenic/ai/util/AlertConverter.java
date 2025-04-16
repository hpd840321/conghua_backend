package com.scenic.ai.util;

import com.scenic.ai.common.enums.AlertLevel;
import com.scenic.ai.common.enums.AlertStatus;
import com.scenic.ai.domain.model.AlertDomain;
import com.scenic.ai.entity.Alert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 告警实体与领域模型转换器
 */
public class AlertConverter {

    /**
     * 将告警实体转换为领域模型
     */
    public static AlertDomain toAlertDomain(Alert alert) {
        if (alert == null) {
            return null;
        }

        return AlertDomain.createWithValue(
                alert.getAlertType(),
                AlertLevel.fromValue(alert.getAlertLevel()).name(),
                alert.getDeviceCode(),
                alert.getDeviceName(),
                alert.getTourismName(),
                alert.getDescription(),
                null // 由于Alert实体中没有value字段，这里传null
        );
    }

    /**
     * 将领域模型转换为告警实体
     */
    public static Alert toAlert(AlertDomain domain) {
        if (domain == null) {
            return null;
        }

        Alert alert = new Alert();
        alert.setAlertType(domain.getType());
        alert.setAlertLevel(AlertLevel.valueOf(domain.getLevel()).getValue());
        alert.setDeviceCode(domain.getDeviceCode());
        alert.setDeviceName(domain.getDeviceName());
        alert.setTourismName(domain.getTourismName());
        alert.setDescription(domain.getDescription());
        alert.setAlertStatus(AlertStatus.valueOf(domain.getStatus()).getCode());
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