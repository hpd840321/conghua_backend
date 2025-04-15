package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.dao.AlertMapper;
import com.scenic.ai.model.Alert;
import com.scenic.ai.model.enums.AlertStatus;
import com.scenic.ai.service.IAlertService;
import com.scenic.ai.exception.BusinessException;
import com.scenic.ai.exception.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 告警信息服务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AlertServiceImpl extends ServiceImpl<AlertMapper, Alert> implements IAlertService {

    private static final Logger log = LoggerFactory.getLogger(AlertServiceImpl.class);

    @Autowired
    private AlertMapper alertMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createAlert(Alert alert) {
        if (alert == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "告警信息不能为空");
        }
        if (StringUtils.isBlank(alert.getDeviceCode())) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "设备编号不能为空");
        }
        if (StringUtils.isBlank(alert.getAlertType())) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "告警类型不能为空");
        }
        if (alert.getAlertLevel() == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "告警等级不能为空");
        }
        if (alert.getAlertStatus() == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "告警状态不能为空");
        }
        if (alert.getRecordTime() == null) {
            alert.setRecordTime(LocalDateTime.now());
        }
        try {
            return save(alert);
        } catch (Exception e) {
            log.error("创建告警失败, alert: {}", alert, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建告警失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, Integer status) {
        if (id == null || status == null) {
            return false;
        }
        Alert alert = getById(id);
        if (alert == null) {
            return false;
        }
        alert.setAlertStatus(status);
        alert.setUpdateTime(LocalDateTime.now());
        return updateById(alert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchUpdateStatus(List<Long> ids, Integer status) {
        if (ids == null || ids.isEmpty() || status == null) {
            return false;
        }
        List<Alert> alerts = listByIds(ids);
        if (alerts.isEmpty()) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        alerts.forEach(alert -> {
            alert.setAlertStatus(status);
            alert.setUpdateTime(now);
        });
        return updateBatchById(alerts);
    }

    @Override
    public List<Alert> getByDevice(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.getByDevice(deviceCode, startTime, endTime);
    }

    @Override
    public List<Alert> getByTourism(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.getByTourism(tourismName, startTime, endTime);
    }

    @Override
    public Map<String, Object> getTimeDistribution(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        List<Map<String, Object>> distribution = baseMapper.getTimeDistribution(tourismName, deviceCode, startTime, endTime);
        Map<String, Object> result = new HashMap<>();
        result.put("distribution", distribution);
        return result;
    }

    @Override
    public Map<String, Object> getTypeDistribution(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        List<Map<String, Object>> distribution = baseMapper.getTypeDistribution(tourismName, deviceCode, startTime, endTime);
        Map<String, Object> result = new HashMap<>();
        result.put("distribution", distribution);
        return result;
    }

    @Override
    public Map<String, Object> getLevelDistribution(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        List<Map<String, Object>> distribution = baseMapper.getLevelDistribution(tourismName, deviceCode, startTime, endTime);
        Map<String, Object> result = new HashMap<>();
        result.put("distribution", distribution);
        return result;
    }

    @Override
    public List<Alert> pageAlerts(Map<String, Object> params) {
        try {
            // 先获取参数值
            Object alertStatus = params.get("alertStatus");
            Object deviceCode = params.get("deviceCode");
            Object tourismName = params.get("tourismName");

            // 构建查询条件
            QueryWrapper<Alert> wrapper = new QueryWrapper<>();
            if (alertStatus != null) {
                wrapper.eq("alert_status", alertStatus);
            }
            if (deviceCode != null) {
                wrapper.eq("device_code", deviceCode);
            }
            if (tourismName != null) {
                wrapper.eq("tourism_name", tourismName);
            }
            return baseMapper.selectList(wrapper);
        } catch (Exception e) {
            log.error("分页查询告警信息失败", e);
            throw new BusinessException(ErrorCode.QUERY_ERROR, "分页查询告警信息失败");
        }
    }

    @Override
    public Long countAlerts(Map<String, Object> params) {
        return baseMapper.selectCount(new QueryWrapper<Alert>().allEq(params));
    }

    public List<Alert> getUnhandledAlerts(String deviceCode, String tourismName) {
        QueryWrapper<Alert> wrapper = new QueryWrapper<>();
        wrapper.eq("alert_status", AlertStatus.PENDING.getValue());
        if (StringUtils.isNotBlank(deviceCode)) {
            wrapper.eq("device_code", deviceCode);
        }
        if (StringUtils.isNotBlank(tourismName)) {
            wrapper.eq("tourism_name", tourismName);
        }
        return baseMapper.selectList(wrapper);
    }

    @Override
    public Page<Alert> getAlertPage(Page<Alert> page, String tourismName, String deviceCode,
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
    public Map<String, Object> getOverview(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        return getAlertStatistics(tourismName, startTime, endTime);
    }

    @Override
    public boolean handleAlert(Long id, String description) {
        if (id == null) {
            return false;
        }
        Alert alert = getById(id);
        if (alert == null) {
            return false;
        }
        alert.setAlertStatus(AlertStatus.PROCESSED.getValue());
        alert.setDescription(description);
        alert.setUpdateTime(LocalDateTime.now());
        return updateById(alert);
    }

    @Override
    public boolean batchHandleAlerts(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        List<Alert> alerts = listByIds(ids);
        if (alerts.isEmpty()) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        alerts.forEach(alert -> {
            alert.setAlertStatus(AlertStatus.PROCESSED.getValue());
            alert.setUpdateTime(now);
        });
        return updateBatchById(alerts);
    }

    @Override
    public int countPendingAlerts(String tourismName) {
        QueryWrapper<Alert> wrapper = new QueryWrapper<>();
        wrapper.eq("alert_status", AlertStatus.PENDING.getValue());
        if (StringUtils.isNotBlank(tourismName)) {
            wrapper.eq("tourism_name", tourismName);
        }
              Long count = baseMapper.selectCount(wrapper);
        return count != null ? count.intValue() : 0;
    }

    public int countByLevelAndStatus(Integer alertLevel, Integer alertStatus) {
        QueryWrapper<Alert> wrapper = new QueryWrapper<>();
        if (alertLevel != null) {
            wrapper.eq("alert_level", alertLevel);
        }
        if (alertStatus != null) {
            wrapper.eq("alert_status", alertStatus);
        }
        Long count = baseMapper.selectCount(wrapper);
        return count != null ? count.intValue() : 0;
    }

    public int countUnhandledAlerts(String deviceCode, String tourismName) {
        QueryWrapper<Alert> wrapper = new QueryWrapper<>();
        wrapper.eq("alert_status", AlertStatus.PENDING.getValue());
        if (StringUtils.isNotBlank(deviceCode)) {
            wrapper.eq("device_code", deviceCode);
        }
        if (StringUtils.isNotBlank(tourismName)) {
            wrapper.eq("tourism_name", tourismName);
        }
        Long count = baseMapper.selectCount(wrapper);
        return count != null ? count.intValue() : 0;
    }

    @Override
    public IPage<Alert> page(Integer pageNum, Integer pageSize, String tourismName, String deviceCode,
            String alertType, Integer alertLevel, Integer alertStatus,
            LocalDateTime startTime, LocalDateTime endTime) {
        Page<Alert> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Alert> wrapper = new QueryWrapper<>();
        
        if (StringUtils.isNotBlank(tourismName)) {
            wrapper.eq("tourism_name", tourismName);
        }
        if (StringUtils.isNotBlank(deviceCode)) {
            wrapper.eq("device_code", deviceCode);
        }
        if (StringUtils.isNotBlank(alertType)) {
            wrapper.eq("alert_type", alertType);
        }
        if (alertLevel != null) {
            wrapper.eq("alert_level", alertLevel);
        }
        if (alertStatus != null) {
            wrapper.eq("alert_status", alertStatus);
        }
        if (startTime != null) {
            wrapper.ge("record_time", startTime);
        }
        if (endTime != null) {
            wrapper.le("record_time", endTime);
        }
        
        wrapper.orderByDesc("record_time");
        return page(page, wrapper);
    }

    @Override
    public Map<String, Object> getAlertList(String tourismName, String deviceCode, String alertType,
                                          Integer alertLevel, Integer alertStatus, LocalDateTime startTime,
                                          LocalDateTime endTime, Integer pageNum, Integer pageSize) {
        Page<Alert> page = new Page<>(pageNum, pageSize);
        IPage<Alert> alertPage = this.page(pageNum, pageSize, tourismName, deviceCode,
                alertType, alertLevel, alertStatus, startTime, endTime);
        
        Map<String, Object> result = new HashMap<>();
        result.put("total", alertPage.getTotal());
        result.put("records", alertPage.getRecords());
        return result;
    }

    @Override
    public Map<String, Object> getAlertStatistics(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        QueryWrapper<Alert> wrapper = buildBaseWrapper(tourismName, startTime, endTime);
        List<Alert> alerts = list(wrapper);
        
        Map<String, Object> result = new HashMap<>();
        result.put("totalAlerts", alerts.size());
        
        long pendingAlerts = alerts.stream()
                .filter(alert -> alert.getAlertStatus().equals(AlertStatus.PENDING.getValue()))
                .count();
        result.put("pendingAlerts", pendingAlerts);
        
        long highPriorityAlerts = alerts.stream()
                .filter(alert -> alert.getAlertLevel() == 3)
                .count();
        result.put("highPriorityAlerts", highPriorityAlerts);
        
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        long todayAlerts = alerts.stream()
                .filter(alert -> alert.getRecordTime().isAfter(today))
                .count();
        result.put("todayAlerts", todayAlerts);
        
        return result;
    }

    @Override
    public boolean processAlert(Long id) {
        if (id == null) {
            return false;
        }
        Alert alert = getById(id);
        if (alert == null) {
            return false;
        }
        alert.setAlertStatus(AlertStatus.PROCESSED.getValue());
        alert.setUpdateTime(LocalDateTime.now());
        return updateById(alert);
    }

    @Override
    public Map<String, Object> getTrend(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        List<Map<String, Object>> trend = baseMapper.getTrend(tourismName, deviceCode, startTime, endTime);
        Map<String, Object> result = new HashMap<>();
        result.put("trend", trend);
        return result;
    }

    @Override
    public List<Map<String, Object>> getAlertTypes() {
        return baseMapper.getAlertTypes();
    }

    @Override
    public Map<String, Object> getDeviceDistribution(String tourismName,
            LocalDateTime startTime, LocalDateTime endTime) {
        List<Map<String, Object>> distribution = baseMapper.getDeviceDistribution(tourismName, startTime, endTime);
        Map<String, Object> result = new HashMap<>();
        result.put("distribution", distribution);
        return result;
    }

    @Override
    public Map<String, Object> getTourismDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        List<Map<String, Object>> distribution = baseMapper.getTourismDistribution(startTime, endTime);
        Map<String, Object> result = new HashMap<>();
        result.put("distribution", distribution);
        return result;
    }

    @Override
    public Alert getAlertDetail(Long id) {
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "告警ID不能为空");
        }
        try {
            return getById(id);
        } catch (Exception e) {
            log.error("获取告警详情失败, id: {}", id, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取告警详情失败");
        }
    }

    @Override
    public Alert getLatestByDevice(String deviceCode) {
        if (StringUtils.isBlank(deviceCode)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "设备编号不能为空");
        }
        try {
            LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Alert::getDeviceCode, deviceCode)
                  .eq(Alert::getAlertStatus, AlertStatus.PENDING.getValue())
                  .orderByDesc(Alert::getRecordTime)
                  .last("LIMIT 1");
            return getOne(wrapper);
        } catch (Exception e) {
            log.error("获取设备最新告警失败, deviceCode: {}", deviceCode, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取设备最新告警失败");
        }
    }

    private QueryWrapper<Alert> buildBaseWrapper(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        QueryWrapper<Alert> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(tourismName)) {
            wrapper.eq("tourism_name", tourismName);
        }
        if (startTime != null) {
            wrapper.ge("record_time", startTime);
        }
        if (endTime != null) {
            wrapper.le("record_time", endTime);
        }
        return wrapper;
    }
}