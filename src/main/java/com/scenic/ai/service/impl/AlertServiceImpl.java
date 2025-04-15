package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.common.exception.BusinessException;
import com.scenic.ai.common.enums.ErrorCode;
import com.scenic.ai.entity.Alert;
import com.scenic.ai.entity.AlertHandleRecord;
import com.scenic.ai.common.enums.AlertStatus;
import com.scenic.ai.mapper.AlertMapper;
import com.scenic.ai.service.IAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 告警服务实现类
 * 
 * @author AI
 * @date 2024-04-15
 */
@Service
public class AlertServiceImpl extends ServiceImpl<AlertMapper, Alert> implements IAlertService {

    private static final Logger log = LoggerFactory.getLogger(AlertServiceImpl.class);

    @Autowired
    private AlertMapper alertMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Alert alert) {
        try {
            validateAlert(alert);
            alert.setCreateTime(LocalDateTime.now());
            alert.setUpdateTime(LocalDateTime.now());
            return super.save(alert);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("创建告警失败, alert: {}", alert, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建告警失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, Integer status) {
        try {
            if (id == null || status == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "参数不能为空");
            }
            Alert alert = getById(id);
            if (alert == null) {
                throw new BusinessException(ErrorCode.DATA_NOT_FOUND, "告警不存在");
            }
            alert.setAlertStatus(status);
            alert.setUpdateTime(LocalDateTime.now());
            return updateById(alert);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("更新告警状态失败, id: {}, status: {}", id, status, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新告警状态失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatusBatch(List<Long> ids, Integer status) {
        try {
            if (ids == null || ids.isEmpty() || status == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "参数不能为空");
            }
            List<Alert> alerts = listByIds(ids);
            if (alerts.isEmpty()) {
                throw new BusinessException(ErrorCode.DATA_NOT_FOUND, "告警不存在");
            }
            LocalDateTime now = LocalDateTime.now();
            alerts.forEach(alert -> {
                alert.setAlertStatus(status);
                alert.setUpdateTime(now);
            });
            return updateBatchById(alerts);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("批量更新告警状态失败, ids: {}, status: {}", ids, status, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "批量更新告警状态失败");
        }
    }

    @Override
    public List<Alert> listByDevice(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (StringUtils.hasText(deviceCode)) {
                validateTimeRange(startTime, endTime);
                return alertMapper.listByDevice(deviceCode, startTime, endTime);
            }
            throw new BusinessException(ErrorCode.PARAM_ERROR, "设备编号不能为空");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("查询设备告警失败, deviceCode: {}, startTime: {}, endTime: {}",
                    deviceCode, startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询设备告警失败");
        }
    }

    @Override
    public List<Alert> listByTourism(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (StringUtils.hasText(tourismName)) {
                validateTimeRange(startTime, endTime);
                return alertMapper.listByTourism(tourismName, startTime, endTime);
            }
            throw new BusinessException(ErrorCode.PARAM_ERROR, "景区名称不能为空");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("查询景区告警失败, tourismName: {}, startTime: {}, endTime: {}",
                    tourismName, startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询景区告警失败");
        }
    }

    @Override
    public Map<String, Object> getTimeDistribution(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        try {
            validateTimeRange(startTime, endTime);
            List<Map<String, Object>> distribution = alertMapper.getTimeDistribution(
                    tourismName, deviceCode, startTime, endTime);
            Map<String, Object> result = new HashMap<>();
            result.put("distribution", distribution);
            return result;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取告警时段分布失败, tourismName: {}, deviceCode: {}, startTime: {}, endTime: {}",
                    tourismName, deviceCode, startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取告警时段分布失败");
        }
    }

    @Override
    public Map<String, Object> getTypeDistribution(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        try {
            validateTimeRange(startTime, endTime);
            List<Map<String, Object>> distribution = alertMapper.getTypeDistribution(
                    tourismName, deviceCode, startTime, endTime);
            Map<String, Object> result = new HashMap<>();
            result.put("distribution", distribution);
            return result;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取告警类型分布失败, tourismName: {}, deviceCode: {}, startTime: {}, endTime: {}",
                    tourismName, deviceCode, startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取告警类型分布失败");
        }
    }

    @Override
    public Map<String, Object> getLevelDistribution(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        try {
            validateTimeRange(startTime, endTime);
            List<Map<String, Object>> distribution = alertMapper.getLevelDistribution(
                    tourismName, deviceCode, startTime, endTime);
            Map<String, Object> result = new HashMap<>();
            result.put("distribution", distribution);
            return result;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取告警级别分布失败, tourismName: {}, deviceCode: {}, startTime: {}, endTime: {}",
                    tourismName, deviceCode, startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取告警级别分布失败");
        }
    }

    @Override
    public IPage<Alert> pageByConditions(Integer pageNum, Integer pageSize, String tourismName, String deviceCode,
            String alertType, Integer alertLevel, Integer alertStatus,
            LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (pageNum == null || pageSize == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "分页参数不能为空");
            }
            validateTimeRange(startTime, endTime);

            Page<Alert> page = new Page<>(pageNum, pageSize);
            QueryWrapper<Alert> wrapper = buildQueryWrapper(tourismName, deviceCode, alertType,
                    alertLevel, alertStatus, startTime, endTime);
            return page(page, wrapper);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("分页查询告警失败, pageNum: {}, pageSize: {}, tourismName: {}, deviceCode: {}",
                    pageNum, pageSize, tourismName, deviceCode, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "分页查询告警失败");
        }
    }

    @Override
    public Long count(String tourismName, String deviceCode, String alertType,
            Integer alertLevel, Integer alertStatus, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            validateTimeRange(startTime, endTime);
            QueryWrapper<Alert> wrapper = buildQueryWrapper(tourismName, deviceCode, alertType,
                    alertLevel, alertStatus, startTime, endTime);
            return count(wrapper);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("统计告警数量失败, tourismName: {}, deviceCode: {}", tourismName, deviceCode, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "统计告警数量失败");
        }
    }

    @Override
    public List<Alert> listPending(String deviceCode, String tourismName) {
        try {
            QueryWrapper<Alert> wrapper = new QueryWrapper<>();
            wrapper.eq("alert_status", AlertStatus.PENDING.getValue());
            if (StringUtils.hasText(deviceCode)) {
                wrapper.eq("device_code", deviceCode);
            }
            if (StringUtils.hasText(tourismName)) {
                wrapper.eq("tourism_name", tourismName);
            }
            return list(wrapper);
        } catch (Exception e) {
            log.error("查询待处理告警失败, deviceCode: {}, tourismName: {}", deviceCode, tourismName, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询待处理告警失败");
        }
    }

    @Override
    public Map<String, Object> getOverview(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        try {
            validateTimeRange(startTime, endTime);
            return getAlertStatistics(tourismName, startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取告警概览失败, tourismName: {}, deviceCode: {}, startTime: {}, endTime: {}",
                    tourismName, deviceCode, startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取告警概览失败");
        }
    }

    @Override
    public boolean processAlert(Long id, String description) {
        try {
            if (id == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "告警ID不能为空");
            }
            Alert alert = getById(id);
            if (alert == null) {
                throw new BusinessException(ErrorCode.DATA_NOT_FOUND, "告警不存在");
            }
            alert.setAlertStatus(AlertStatus.PROCESSED.getValue());
            alert.setDescription(description);
            alert.setUpdateTime(LocalDateTime.now());
            return updateById(alert);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("处理告警失败, id: {}, description: {}", id, description, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "处理告警失败");
        }
    }

    @Override
    public boolean processAlertBatch(List<Long> ids) {
        try {
            if (ids == null || ids.isEmpty()) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "告警ID列表不能为空");
            }
            List<Alert> alerts = listByIds(ids);
            if (alerts.isEmpty()) {
                throw new BusinessException(ErrorCode.DATA_NOT_FOUND, "告警不存在");
            }
            LocalDateTime now = LocalDateTime.now();
            alerts.forEach(alert -> {
                alert.setAlertStatus(AlertStatus.PROCESSED.getValue());
                alert.setUpdateTime(now);
            });
            return updateBatchById(alerts);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("批量处理告警失败, ids: {}", ids, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "批量处理告警失败");
        }
    }

    @Override
    public Long countPending(String tourismName) {
        try {
            QueryWrapper<Alert> wrapper = new QueryWrapper<>();
            wrapper.eq("alert_status", AlertStatus.PENDING.getValue());
            if (StringUtils.hasText(tourismName)) {
                wrapper.eq("tourism_name", tourismName);
            }
            return count(wrapper);
        } catch (Exception e) {
            log.error("统计待处理告警数量失败, tourismName: {}", tourismName, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "统计待处理告警数量失败");
        }
    }

    @Override
    public Long countByLevelAndStatus(Integer alertLevel, Integer alertStatus) {
        try {
            QueryWrapper<Alert> wrapper = new QueryWrapper<>();
            if (alertLevel != null) {
                wrapper.eq("alert_level", alertLevel);
            }
            if (alertStatus != null) {
                wrapper.eq("alert_status", alertStatus);
            }
            return count(wrapper);
        } catch (Exception e) {
            log.error("统计告警数量失败, alertLevel: {}, alertStatus: {}", alertLevel, alertStatus, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "统计告警数量失败");
        }
    }

    @Override
    public Map<String, Object> getTrend(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        try {
            validateTimeRange(startTime, endTime);
            List<Map<String, Object>> trend = alertMapper.getTrend(tourismName, deviceCode, startTime, endTime);
            Map<String, Object> result = new HashMap<>();
            result.put("trend", trend);
            return result;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取告警趋势失败, tourismName: {}, deviceCode: {}, startTime: {}, endTime: {}",
                    tourismName, deviceCode, startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取告警趋势失败");
        }
    }

    @Override
    public Alert getById(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "告警ID不能为空或小于等于0");
            }
            return alertMapper.getById(id);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("查询告警详情失败, id: {}", id, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询告警详情失败");
        }
    }

    @Override
    public Alert getLatestByDevice(String deviceCode) {
        try {
            if (StringUtils.hasText(deviceCode)) {
                LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Alert::getDeviceCode, deviceCode)
                        .eq(Alert::getAlertStatus, AlertStatus.PENDING.getValue())
                        .orderByDesc(Alert::getRecordTime)
                        .last("FETCH FIRST 1 ROWS ONLY");
                return getOne(wrapper);
            }
            throw new BusinessException(ErrorCode.PARAM_ERROR, "设备编号不能为空");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取设备最新告警失败, deviceCode: {}", deviceCode, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取设备最新告警失败");
        }
    }

    /**
     * 验证告警信息
     */
    private void validateAlert(Alert alert) {
        if (alert == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "告警信息不能为空");
        }
        if (StringUtils.hasText(alert.getDeviceCode())) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "设备编号不能为空");
        }
        if (StringUtils.hasText(alert.getAlertType())) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "告警类型不能为空");
        }
        if (alert.getAlertLevel() == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "告警等级不能为空");
        }
        if (alert.getAlertStatus() == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "告警状态不能为空");
        }
    }

    /**
     * 验证时间范围
     */
    private void validateTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间和结束时间不能为空");
        }
        if (startTime.isAfter(endTime)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间不能晚于结束时间");
        }
    }

    /**
     * 构建查询条件
     */
    private QueryWrapper<Alert> buildQueryWrapper(String tourismName, String deviceCode, String alertType,
            Integer alertLevel, Integer alertStatus, LocalDateTime startTime, LocalDateTime endTime) {
        QueryWrapper<Alert> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(tourismName)) {
            wrapper.eq("tourism_name", tourismName);
        }
        if (StringUtils.hasText(deviceCode)) {
            wrapper.eq("device_code", deviceCode);
        }
        if (StringUtils.hasText(alertType)) {
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
        return wrapper;
    }
}