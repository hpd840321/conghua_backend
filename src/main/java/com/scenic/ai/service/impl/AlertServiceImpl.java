package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.model.Alert;
import com.scenic.ai.mapper.AlertMapper;
import com.scenic.ai.model.enums.AlertStatus;
import com.scenic.ai.service.AlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * 告警信息服务实现类
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AlertServiceImpl extends ServiceImpl<AlertMapper, Alert> implements AlertService {

    @Autowired
    private AlertMapper alertMapper;

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
    public IPage<Alert> pageAlerts(Page<Alert> page, String tourismName, String deviceCode, String alertType,
                                  Integer alertLevel, Integer alertStatus, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(tourismName)) {
            wrapper.eq(Alert::getTourismName, tourismName);
        }
        if (StringUtils.hasText(deviceCode)) {
            wrapper.eq(Alert::getDeviceCode, deviceCode);
        }
        if (StringUtils.hasText(alertType)) {
            wrapper.eq(Alert::getAlertType, alertType);
        }
        if (alertLevel != null) {
            wrapper.eq(Alert::getAlertLevel, alertLevel);
        }
        if (alertStatus != null) {
            wrapper.eq(Alert::getAlertStatus, alertStatus);
        }
        if (startTime != null) {
            wrapper.ge(Alert::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Alert::getCreateTime, endTime);
        }
        
        wrapper.orderByDesc(Alert::getCreateTime);
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
        if (StringUtils.hasText(tourismName)) {
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
        if (StringUtils.hasText(tourismName)) {
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

    @Override
    public Long countUnhandledAlerts(String deviceCode, String tourismName) {
        LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Alert::getAlertStatus, 0); // 0表示未处理状态
        
        if (StringUtils.hasText(deviceCode)) {
            wrapper.eq(Alert::getDeviceCode, deviceCode);
        }
        if (StringUtils.hasText(tourismName)) {
            wrapper.eq(Alert::getTourismName, tourismName);
        }
        
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public IPage<Alert> page(Integer pageNum, Integer pageSize, String tourismName, String deviceCode,
                           String alertType, Integer alertLevel, Integer alertStatus,
                           LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
        
        // 添加查询条件
        if (StringUtils.hasText(tourismName)) {
            wrapper.eq(Alert::getTourismName, tourismName);
        }
        if (StringUtils.hasText(deviceCode)) {
            wrapper.eq(Alert::getDeviceCode, deviceCode);
        }
        if (StringUtils.hasText(alertType)) {
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
        
        // 按记录时间倒序排序
        wrapper.orderByDesc(Alert::getRecordTime);
        
        return page(new Page<>(pageNum, pageSize), wrapper);
    }
    
    @Override
    public List<Map<String, Object>> getHourDistribution(String tourismName, String deviceCode,
                                                       LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.selectHourDistribution(tourismName, deviceCode, startTime, endTime);
    }
    
    @Override
    public List<Map<String, Object>> getTypeDistribution(String tourismName, String deviceCode,
                                                       LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.selectTypeDistribution(tourismName, deviceCode, startTime, endTime);
    }
    
    @Override
    public Map<String, Object> getOverview(String tourismName, String deviceCode,
                                         LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Alert> wrapper = createBaseWrapper(tourismName, startTime, endTime);
        
        // 统计总数
        long total = count(wrapper);
        
        // 统计待处理数
        wrapper.clear();
        wrapper = createBaseWrapper(tourismName, startTime, endTime);
        wrapper.eq(Alert::getAlertStatus, 0);
        long pending = count(wrapper);
        
        // 统计已处理数
        wrapper.clear();
        wrapper = createBaseWrapper(tourismName, startTime, endTime);
        wrapper.eq(Alert::getAlertStatus, 1);
        long handled = count(wrapper);
        
        // 统计高级别告警数
        wrapper.clear();
        wrapper = createBaseWrapper(tourismName, startTime, endTime);
        wrapper.eq(Alert::getAlertLevel, 3);
        long highLevel = count(wrapper);
        
        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("pending", pending);
        result.put("handled", handled);
        result.put("highLevel", highLevel);
        
        return result;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleAlert(Long alertId, String description) {
        // 获取告警信息
        Alert alert = getById(alertId);
        if (alert == null) {
            throw new RuntimeException("告警信息不存在");
        }
        
        // 检查告警状态
        if (alert.getAlertStatus() == 1) {
            throw new RuntimeException("告警已处理");
        }
        
        // 更新告警状态
        alert.setAlertStatus(1);
        alert.setDescription(description);
        alert.setUpdateTime(LocalDateTime.now());
        
        // 保存更新
        updateById(alert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean handleAlert(Long alertId, String handler, String description) {
        Alert alert = getById(alertId);
        if (alert == null) {
            return false;
        }
        
        // 更新告警状态
        alert.setAlertStatus(1); // 1-已处理
        alert.setUpdateTime(LocalDateTime.now());
        
        return updateById(alert);
    }

    @Override
    public List<Map<String, Object>> getTimeDistribution(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.selectTimeDistribution(tourismName, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getLevelDistribution(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.selectLevelDistribution(tourismName, startTime, endTime);
    }

    @Override
    public List<Alert> getLatestAlerts(String tourismName, int limit) {
        LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(tourismName)) {
            wrapper.eq(Alert::getTourismName, tourismName);
        }
        wrapper.orderByDesc(Alert::getRecordTime);
        wrapper.last("LIMIT " + limit);
        
        return list(wrapper);
    }

    /**
     * 创建基础查询条件
     */
    private LambdaQueryWrapper<Alert> createBaseWrapper(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(tourismName)) {
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

    @Override
    public IPage<Alert> getAlertPage(Integer pageNum, Integer pageSize, String tourismName,
            String deviceCode, String alertType, Integer alertLevel, Integer alertStatus,
            LocalDateTime startTime, LocalDateTime endTime) {
        log.info("分页查询告警信息: pageNum={}, pageSize={}, tourismName={}, deviceCode={}, alertType={}, " +
                "alertLevel={}, alertStatus={}, startTime={}, endTime={}", 
                pageNum, pageSize, tourismName, deviceCode, alertType, alertLevel, alertStatus, startTime, endTime);
        
        Page<Alert> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectAlertPage(page, tourismName, deviceCode, alertType, alertLevel, 
                alertStatus, startTime, endTime);
    }
    
    @Override
    public List<Map<String, Object>> getTimeDistribution(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询告警时段分布: tourismName={}, deviceCode={}, startTime={}, endTime={}", 
                tourismName, deviceCode, startTime, endTime);
        return baseMapper.selectTimeDistribution(tourismName, deviceCode, startTime, endTime);
    }
    
    @Override
    public List<Map<String, Object>> getTypeDistribution(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询告警类型分布: tourismName={}, deviceCode={}, startTime={}, endTime={}", 
                tourismName, deviceCode, startTime, endTime);
        return baseMapper.selectTypeDistribution(tourismName, deviceCode, startTime, endTime);
    }
    
    @Override
    public Map<String, Object> getOverview(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询告警概览统计: tourismName={}, deviceCode={}, startTime={}, endTime={}", 
                tourismName, deviceCode, startTime, endTime);
        return baseMapper.selectOverview(tourismName, deviceCode, startTime, endTime);
    }
    
    @Override
    public List<Alert> getByDevice(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("根据设备编码查询告警信息: deviceCode={}, startTime={}, endTime={}", 
                deviceCode, startTime, endTime);
        return baseMapper.selectByDevice(deviceCode, startTime, endTime);
    }

    @Override
    public List<Alert> getByTourism(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("根据景区名称查询告警信息: tourismName={}, startTime={}, endTime={}", 
                tourismName, startTime, endTime);
        return baseMapper.selectByTourism(tourismName, startTime, endTime);
    }

    @Override
    public Long countUnhandledAlerts(String deviceCode, String tourismName) {
        log.info("统计未处理告警数量: deviceCode={}, tourismName={}", deviceCode, tourismName);
        return baseMapper.countUnhandledAlerts(deviceCode, tourismName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchUpdateStatus(List<Long> ids, Integer status) {
        log.info("批量更新告警状态: ids={}, status={}", ids, status);
        return baseMapper.batchUpdateStatus(ids, status) > 0;
    }

    @Override
    public Alert getLatestByDevice(String deviceCode) {
        log.info("获取设备最新告警: deviceCode={}", deviceCode);
        return baseMapper.selectLatestByDevice(deviceCode);
    }
}