package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.model.Alert;
import com.scenic.ai.mapper.AlertMapper;
import com.scenic.ai.model.enums.AlertStatus;
import com.scenic.ai.service.AlertService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * 告警信息服务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AlertServiceImpl extends ServiceImpl<AlertMapper, Alert> implements AlertService {

    @Override
    public boolean createAlert(Alert alert) {
        if (alert.getCreateTime() == null) {
            alert.setCreateTime(LocalDateTime.now());
        }
        if (alert.getRecordTime() == null) {
            alert.setRecordTime(LocalDateTime.now());
        }
        if (alert.getAlertStatus() == null) {
            alert.setAlertStatus(0); // 默认待处理
        }
        return save(alert);
    }

    @Override
    public boolean updateStatus(Long id, Integer status) {
        Alert alert = getById(id);
        if (alert != null) {
            alert.setAlertStatus(status);
            alert.setUpdateTime(LocalDateTime.now());
            return updateById(alert);
        }
        return false;
    }

    @Override
    public boolean batchUpdateStatus(List<Long> ids, Integer status) {
        if (ids == null || ids.isEmpty() || status == null) {
            return false;
        }
        return update()
                .set("alert_status", status)
                .set("update_time", LocalDateTime.now())
                .in("id", ids)
                .update();
    }

    @Override
    public List<Alert> getAlertsByDevice(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.selectByDeviceCode(deviceCode, startTime, endTime);
    }

    @Override
    public List<Alert> getAlertsByTourism(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.selectByTourismName(tourismName, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getAlertHourDistribution(String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.countByHour(deviceCode, tourismName, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getAlertTypeDistribution(String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.countByType(deviceCode, tourismName, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getAlertLevelDistribution(String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.countByLevel(deviceCode, tourismName, startTime, endTime);
    }

    @Override
    public List<Alert> pageAlerts(Map<String, Object> params) {
        return baseMapper.findByConditions(params);
    }

    @Override
    public Long countAlerts(Map<String, Object> params) {
        return baseMapper.countRecords(params);
    }

    @Override
    public List<Alert> getUnhandledAlerts(String deviceCode, String tourismName) {
        return baseMapper.selectUnhandled(deviceCode, tourismName);
    }

    @Override
    public IPage<Alert> pageAlerts(Page<Alert> page, String tourismName, String deviceCode, 
            String alertType, Integer alertLevel, Integer alertStatus, 
            LocalDateTime startTime, LocalDateTime endTime) {
        
        LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.isNotBlank(tourismName)) {
            wrapper.eq(Alert::getTourismName, tourismName);
        }
        if (StringUtils.isNotBlank(deviceCode)) {
            wrapper.eq(Alert::getDeviceCode, deviceCode);
        }
        if (StringUtils.isNotBlank(alertType)) {
            wrapper.eq(Alert::getAlertType, alertType);
        }
        if (alertLevel != null) {
            wrapper.eq(Alert::getAlertLevel, alertLevel);
        }
        if (alertStatus != null) {
            wrapper.eq(Alert::getAlertStatus, alertStatus);
        }
        if (startTime != null) {
            wrapper.ge(Alert::getRecordTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Alert::getRecordTime, endTime);
        }
        
        wrapper.orderByDesc(Alert::getRecordTime);
        
        return page(page, wrapper);
    }

    @Override
    public Map<String, Object> getAlertStatistics(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Alert> wrapper = buildBaseWrapper(tourismName, startTime, endTime);
        List<Alert> alerts = list(wrapper);
        
        Map<String, Object> result = new HashMap<>();
        
        // 总告警数
        result.put("totalAlerts", alerts.size());
        
        // 待处理告警数
        long pendingAlerts = alerts.stream()
                .filter(alert -> alert.getAlertStatus().equals(AlertStatus.PENDING.getValue()))
                .count();
        result.put("pendingAlerts", pendingAlerts);
        
        // 高优先级告警数
        long highPriorityAlerts = alerts.stream()
                .filter(alert -> alert.getAlertLevel() == 3)
                .count();
        result.put("highPriorityAlerts", highPriorityAlerts);
        
        // 今日告警数
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        long todayAlerts = alerts.stream()
                .filter(alert -> alert.getRecordTime().isAfter(today))
                .count();
        result.put("todayAlerts", todayAlerts);
        
        return result;
    }

    @Override
    public boolean handleAlert(Long id) {
        Alert alert = getById(id);
        if (alert != null) {
            alert.setAlertStatus(AlertStatus.PROCESSED.getValue());
            alert.setUpdateTime(LocalDateTime.now());
            return updateById(alert);
        }
        return false;
    }

    @Override
    public boolean batchHandleAlerts(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        
        return update()
                .set("alert_status", AlertStatus.PROCESSED.getValue())
                .set("update_time", LocalDateTime.now())
                .in("id", ids)
                .update();
    }

    @Override
    public int countPendingAlerts(String tourismName) {
        LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Alert::getAlertStatus, AlertStatus.PENDING.getValue());
        if (StringUtils.isNotBlank(tourismName)) {
            wrapper.eq(Alert::getTourismName, tourismName);
        }
        return Math.toIntExact(count(wrapper));
    }

    @Override
    public long countByLevelAndStatus(Integer alertLevel, Integer alertStatus) {
        LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
        if (alertLevel != null) {
            wrapper.eq(Alert::getAlertLevel, alertLevel);
        }
        if (alertStatus != null) {
            wrapper.eq(Alert::getAlertStatus, alertStatus);
        }
        return count(wrapper);
    }

    private LambdaQueryWrapper<Alert> buildBaseWrapper(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(tourismName)) {
            wrapper.eq(Alert::getTourismName, tourismName);
        }
        if (startTime != null) {
            wrapper.ge(Alert::getRecordTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Alert::getRecordTime, endTime);
        }
        return wrapper;
    }
}