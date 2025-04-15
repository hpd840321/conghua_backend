package com.scenic.ai.factory;

import com.scenic.ai.entity.Alert;
import com.scenic.ai.model.enums.AlertLevel;
import com.scenic.ai.model.enums.AlertType;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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
        alert.setDeviceCode(deviceCode);
        alert.setDeviceName(deviceName);
        alert.setTourismName(tourismName);
        alert.setAlertType(alertType);
        alert.setAlertLevel(alertLevel);
        alert.setDescription(description);
        alert.setImageUrl(imageUrl);
        alert.setAlertStatus(0);
        LocalDateTime now = LocalDateTime.now();
        alert.setRecordTime(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
        alert.setCreateTime(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
        alert.setUpdateTime(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
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