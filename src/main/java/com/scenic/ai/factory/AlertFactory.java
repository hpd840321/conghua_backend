package com.scenic.ai.factory;

import com.scenic.ai.model.Alert;
import com.scenic.ai.model.enums.AlertLevel;
import com.scenic.ai.model.enums.AlertType;

import java.time.LocalDateTime;

/**
 * 告警工厂类
 */
public class AlertFactory {
    
    /**
     * 创建告警
     */
    public static Alert createAlert(String deviceCode, String deviceName, String tourismName,
            String alertType, Integer alertLevel, String description, String imageUrl) {
        Alert alert = new Alert();
        alert.setDeviceCode(deviceCode)
             .setDeviceName(deviceName)
             .setTourismName(tourismName)
             .setAlertType(alertType)
             .setAlertLevel(alertLevel)
             .setDescription(description)
             .setImageUrl(imageUrl)
             .setAlertStatus(0)
             .setRecordTime(LocalDateTime.now())
             .setCreateTime(LocalDateTime.now())
             .setUpdateTime(LocalDateTime.now());
        return alert;
    }
    
    /**
     * 创建人群密度告警
     */
    public static Alert createDensityAlert(String deviceCode, String deviceName, String tourismName,
            String imageUrl, double density) {
        String description = String.format("当前人群密度为%.2f，超过预警阈值", density);
        return createAlert(deviceCode, deviceName, tourismName,
                AlertType.CROWD_DENSITY.getCode(),
                AlertLevel.HIGH.getValue(),
                description, imageUrl);
    }
    
    /**
     * 创建人群数量告警
     */
    public static Alert createCountAlert(String deviceCode, String deviceName, String tourismName,
            String imageUrl, int count) {
        String description = String.format("当前人群数量为%d，超过预警阈值", count);
        return createAlert(deviceCode, deviceName, tourismName,
                AlertType.CROWD_COUNT.getCode(),
                AlertLevel.HIGH.getValue(),
                description, imageUrl);
    }
} 