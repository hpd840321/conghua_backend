package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.common.enums.AlertStatus;
import com.scenic.ai.common.enums.ErrorCode;
import com.scenic.ai.common.exception.BusinessException;
import com.scenic.ai.entity.Alert;
import com.scenic.ai.entity.AlertHandleRecord;
import com.scenic.ai.mapper.AlertHandleRecordMapper;
import com.scenic.ai.mapper.AlertMapper;
import com.scenic.ai.service.IAlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 告警服务实现类
 * 提供告警信息的创建、更新、查询、统计等功能
 *
 * @author AI
 * @date 2024-03-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AlertServiceImpl extends ServiceImpl<AlertMapper, Alert> implements IAlertService {

    private final AlertMapper alertMapper;
    private final AlertHandleRecordMapper alertHandleRecordMapper;

    @Autowired
    public AlertServiceImpl(AlertMapper alertMapper, AlertHandleRecordMapper alertHandleRecordMapper) {
        this.alertMapper = alertMapper;
        this.alertHandleRecordMapper = alertHandleRecordMapper;
    }

    @Override
    public boolean createAlert(Alert alert) {
        log.debug("开始创建告警信息: {}", alert);
        try {
            if (alert == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "告警信息不能为空");
            }
            if (!StringUtils.hasText(alert.getDeviceCode())) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "设备编码不能为空");
            }
            if (!StringUtils.hasText(alert.getAlertType())) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "告警类型不能为空");
            }
            alert.setCreateTime(LocalDateTime.now());
            alert.setUpdateTime(LocalDateTime.now());
            alert.setAlertStatus(AlertStatus.PENDING.getCode());
            boolean result = alertMapper.insert(alert) > 0;
            log.debug("创建告警信息完成, result: {}", result);
            return result;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("创建告警信息失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建告警信息失败");
        }
    }

    @Override
    public boolean updateStatus(Long id, Integer status) {
        log.debug("开始更新告警状态, id: {}, status: {}", id, status);
        try {
            if (id == null || status == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "告警ID和状态不能为空");
            }
            // 验证状态值是否有效
            boolean validStatus = false;
            for (AlertStatus alertStatus : AlertStatus.values()) {
                if (alertStatus.getCode() == status) {
                    validStatus = true;
                    break;
                }
            }
            if (!validStatus) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "无效的告警状态");
            }
            Alert alert = new Alert();
            alert.setId(id);
            alert.setAlertStatus(status);
            alert.setUpdateTime(LocalDateTime.now());
            boolean result = alertMapper.updateById(alert) > 0;
            log.debug("更新告警状态完成, result: {}", result);
            return result;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("更新告警状态失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新告警状态失败");
        }
    }

    @Override
    public boolean handleBatchStatus(List<Long> ids, Integer status) {
        log.debug("开始批量更新告警状态, ids: {}, status: {}", ids, status);
        try {
            if (ids == null || ids.isEmpty() || status == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "告警ID列表和状态不能为空");
            }
            // 验证状态值是否有效
            boolean validStatus = false;
            for (AlertStatus alertStatus : AlertStatus.values()) {
                if (alertStatus.getCode() == status) {
                    validStatus = true;
                    break;
                }
            }
            if (!validStatus) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "无效的告警状态");
            }
            LocalDateTime now = LocalDateTime.now();
            for (Long id : ids) {
                Alert alert = new Alert();
                alert.setId(id);
                alert.setAlertStatus(status);
                alert.setUpdateTime(now);
                alertMapper.updateById(alert);
            }
            log.debug("批量更新告警状态完成");
            return true;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("批量更新告警状态失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "批量更新告警状态失败");
        }
    }

    @Override
    public boolean processAlert(Long id) {
        log.debug("开始处理告警, id: {}", id);
        try {
            if (id == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "告警ID不能为空");
            }
            Alert alert = alertMapper.selectById(id);
            if (alert == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "告警信息不存在");
            }
            alert.setAlertStatus(AlertStatus.HANDLED.getCode());
            alert.setUpdateTime(LocalDateTime.now());
            boolean result = alertMapper.updateById(alert) > 0;
            log.debug("处理告警完成, result: {}", result);
            return result;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("处理告警失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "处理告警失败");
        }
    }

    @Override
    public boolean handleBatchAlerts(List<Long> ids) {
        log.info("开始批量处理告警，告警ID列表：{}", ids);
        try {
            if (CollectionUtils.isEmpty(ids)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "告警ID列表不能为空");
            }

            // 批量更新告警状态
            int count = alertMapper.handleBatchStatus(ids, AlertStatus.HANDLED.getCode());

            // 批量创建处理记录
            List<AlertHandleRecord> records = ids.stream()
                    .map(id -> {
                        AlertHandleRecord record = new AlertHandleRecord();
                        record.setAlertId(id);
                        record.setHandleDesc("批量处理");
                        record.setHandleResult("已处理");
                        record.setBeforeStatus(String.valueOf(AlertStatus.PENDING.getCode()));
                        record.setAfterStatus(String.valueOf(AlertStatus.HANDLED.getCode()));
                        record.setCreateTime(LocalDateTime.now());
                        record.setUpdateTime(LocalDateTime.now());
                        return record;
                    })
                    .collect(Collectors.toList());

            for (AlertHandleRecord record : records) {
                alertHandleRecordMapper.insert(record);
            }

            log.info("批量处理告警完成，处理数量：{}", count);
            return count > 0;
        } catch (Exception e) {
            log.error("批量处理告警失败，告警ID列表：{}", ids, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "批量处理告警失败");
        }
    }

    @Override
    public Map<String, Object> getOverview(String tourismName, String deviceCode, LocalDateTime startTime,
            LocalDateTime endTime) {
        Map<String, Object> overview = new HashMap<>();

        // 获取总告警数
        int total = alertMapper.countByConditions(tourismName, deviceCode, null, null, null, startTime, endTime);
        overview.put("total", total);

        // 获取待处理告警数
        int pending = alertMapper.countByConditions(tourismName, deviceCode, null, null,
                AlertStatus.PENDING.getCode(), startTime, endTime);
        overview.put("pending", pending);

        // 获取已处理告警数
        int handled = alertMapper.countByConditions(tourismName, deviceCode, null, null,
                AlertStatus.HANDLED.getCode(), startTime, endTime);
        overview.put("handled", handled);

        // 获取高优先级告警数
        int highPriority = alertMapper.countByConditions(tourismName, deviceCode, null, 3, null, startTime, endTime);
        overview.put("highPriority", highPriority);

        return overview;
    }

    @Override
    public List<Map<String, Object>> countTypeDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("统计时间范围内各类型告警数量，开始时间：{}，结束时间：{}", startTime, endTime);
        return alertMapper.countByTypeAndTimeRange(startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> countLevelDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("统计时间范围内各级别告警数量，开始时间：{}，结束时间：{}", startTime, endTime);
        return alertMapper.countByLevelAndTimeRange(startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> countDeviceDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("统计时间范围内各设备告警数量，开始时间：{}，结束时间：{}", startTime, endTime);
        return alertMapper.countByDeviceAndTimeRange(startTime, endTime);
    }

    @Override
    public Map<String, Object> getTimeDistribution(String tourismName, String deviceCode, LocalDateTime startTime,
            LocalDateTime endTime) {
        log.debug("开始获取告警时段分布数据");
        try {
            List<Map<String, Object>> distribution = alertMapper.countTimeDistribution(tourismName, deviceCode,
                    startTime,
                    endTime);
            Map<String, Object> result = new HashMap<>();
            result.put("data", distribution);
            log.debug("获取告警时段分布数据完成");
            return result;
        } catch (Exception e) {
            log.error("获取告警时段分布数据失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取告警时段分布数据失败");
        }
    }

    @Override
    public Map<String, Object> getTypeDistribution(String tourismName, String deviceCode, LocalDateTime startTime,
            LocalDateTime endTime) {
        log.debug("开始获取告警类型分布数据");
        try {
            List<Map<String, Object>> distribution = alertMapper.countTypeDistribution(tourismName, deviceCode,
                    startTime,
                    endTime);
            Map<String, Object> result = new HashMap<>();
            result.put("data", distribution);
            return result;
        } catch (Exception e) {
            log.error("获取告警类型分布数据失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取告警类型分布数据失败");
        }
    }

    @Override
    public int countByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("开始统计时间范围内告警数量, startTime: {}, endTime: {}", startTime, endTime);
        try {
            LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
            if (startTime != null) {
                wrapper.ge(Alert::getCreateTime, startTime);
            }
            if (endTime != null) {
                wrapper.le(Alert::getCreateTime, endTime);
            }
            long count = alertMapper.selectCount(wrapper);
            log.debug("统计时间范围内告警数量完成, count: {}", count);
            return (int) count;
        } catch (Exception e) {
            log.error("统计时间范围内告警数量失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "统计时间范围内告警数量失败");
        }
    }

    @Override
    public List<Map<String, Object>> countStatusDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("统计时间范围内各状态告警数量，开始时间：{}，结束时间：{}", startTime, endTime);
        return alertMapper.countByStatusAndTimeRange(startTime, endTime);
    }

    @Override
    public long countByLevelAndStatus(Integer level, Integer status) {
        log.debug("开始统计指定级别和状态的告警数量, level: {}, status: {}", level, status);
        try {
            LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
            if (level != null) {
                wrapper.eq(Alert::getAlertLevel, level);
            }
            if (status != null) {
                wrapper.eq(Alert::getAlertStatus, status);
            }
            long count = alertMapper.selectCount(wrapper);
            log.debug("统计指定级别和状态的告警数量完成, count: {}", count);
            return count;
        } catch (Exception e) {
            log.error("统计指定级别和状态的告警数量失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "统计指定级别和状态的告警数量失败");
        }
    }

    @Override
    public int countUnhandledAlerts(String deviceCode, String tourismName) {
        log.debug("开始统计未处理告警数量, deviceCode: {}, tourismName: {}", deviceCode, tourismName);
        try {
            LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Alert::getAlertStatus, AlertStatus.PENDING.getCode());
            if (StringUtils.hasText(deviceCode)) {
                wrapper.eq(Alert::getDeviceCode, deviceCode);
            }
            if (StringUtils.hasText(tourismName)) {
                wrapper.eq(Alert::getTourismName, tourismName);
            }
            return alertMapper.selectCount(wrapper).intValue();
        } catch (Exception e) {
            log.error("统计未处理告警数量失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "统计未处理告警数量失败");
        }
    }

    @Override
    public List<AlertHandleRecord> listHandleRecords(Long alertId) {
        log.debug("开始获取告警处理记录, alertId: {}", alertId);
        try {
            if (alertId == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "告警ID不能为空");
            }
            LambdaQueryWrapper<AlertHandleRecord> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(AlertHandleRecord::getAlertId, alertId);
            wrapper.orderByDesc(AlertHandleRecord::getCreateTime);
            List<AlertHandleRecord> records = alertHandleRecordMapper.selectList(wrapper);
            log.debug("获取告警处理记录完成, records size: {}", records.size());
            return records;
        } catch (Exception e) {
            log.error("获取告警处理记录失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取告警处理记录失败");
        }
    }

    @Override
    public List<Alert> listByStatus(Integer status) {
        log.debug("开始获取指定状态的告警列表, status: {}", status);
        try {
            if (status != null) {
                // 验证状态值是否有效
                boolean validStatus = false;
                for (AlertStatus alertStatus : AlertStatus.values()) {
                    if (alertStatus.getCode() == status) {
                        validStatus = true;
                        break;
                    }
                }
                if (!validStatus) {
                    throw new BusinessException(ErrorCode.PARAM_ERROR, "无效的告警状态");
                }
            }
            LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
            if (status != null) {
                wrapper.eq(Alert::getAlertStatus, status);
            }
            wrapper.orderByDesc(Alert::getCreateTime);
            List<Alert> alerts = alertMapper.selectList(wrapper);
            log.debug("获取指定状态的告警列表完成, alerts size: {}", alerts.size());
            return alerts;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取指定状态的告警列表失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取指定状态的告警列表失败");
        }
    }

    @Override
    public List<Alert> listByLevelAndStatus(Integer level, Integer status) {
        log.debug("开始获取指定级别和状态的告警列表, level: {}, status: {}", level, status);
        try {
            LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
            if (level != null) {
                wrapper.eq(Alert::getAlertLevel, level);
            }
            if (status != null) {
                wrapper.eq(Alert::getAlertStatus, status);
            }
            wrapper.orderByDesc(Alert::getCreateTime);
            List<Alert> alerts = alertMapper.selectList(wrapper);
            log.debug("获取指定级别和状态的告警列表完成, alerts size: {}", alerts.size());
            return alerts;
        } catch (Exception e) {
            log.error("获取指定级别和状态的告警列表失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取指定级别和状态的告警列表失败");
        }
    }

    @Override
    public List<Alert> listByConditions(String deviceCode, String alertType, String tourism,
            Integer level, Integer status, LocalDateTime startTime, LocalDateTime endTime) {
        log.debug(
                "开始获取告警列表, deviceCode: {}, alertType: {}, tourism: {}, level: {}, status: {}, startTime: {}, endTime: {}",
                deviceCode, alertType, tourism, level, status, startTime, endTime);
        try {
            LambdaQueryWrapper<Alert> wrapper = buildConditionWrapper(deviceCode, alertType, tourism,
                    level, status, startTime, endTime);
            wrapper.orderByDesc(Alert::getCreateTime);
            List<Alert> alerts = alertMapper.selectList(wrapper);
            log.debug("获取告警列表完成, alerts size: {}", alerts.size());
            return alerts;
        } catch (Exception e) {
            log.error("获取告警列表失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取告警列表失败");
        }
    }

    @Override
    public List<Alert> listByTourism(String tourism, LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("开始获取景区告警列表, tourism: {}, startTime: {}, endTime: {}", tourism, startTime, endTime);
        try {
            LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
            if (StringUtils.hasText(tourism)) {
                wrapper.eq(Alert::getTourismName, tourism);
            }
            if (startTime != null) {
                wrapper.ge(Alert::getCreateTime, startTime);
            }
            if (endTime != null) {
                wrapper.le(Alert::getCreateTime, endTime);
            }
            wrapper.orderByDesc(Alert::getCreateTime);
            List<Alert> alerts = alertMapper.selectList(wrapper);
            log.debug("获取景区告警列表完成, alerts size: {}", alerts.size());
            return alerts;
        } catch (Exception e) {
            log.error("获取景区告警列表失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取景区告警列表失败");
        }
    }

    @Override
    public Map<String, Object> getLevelDistribution(String tourismName, String deviceCode, LocalDateTime startTime,
            LocalDateTime endTime) {
        log.debug("开始获取告警级别分布数据");
        try {
            List<Map<String, Object>> distribution = alertMapper.countLevelDistribution(tourismName, deviceCode,
                    startTime, endTime);
            Map<String, Object> result = new HashMap<>();
            result.put("data", distribution);
            log.debug("获取告警级别分布数据完成");
            return result;
        } catch (Exception e) {
            log.error("获取告警级别分布数据失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取告警级别分布数据失败");
        }
    }

    @Override
    public Map<String, Object> getTourismDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("获取景区告警分布, startTime: {}, endTime: {}", startTime, endTime);
        try {
            List<Map<String, Object>> distribution = alertMapper.countTourismDistribution(startTime, endTime);
            Map<String, Object> result = new HashMap<>();
            result.put("distribution", distribution);
            return result;
        } catch (Exception e) {
            log.error("获取景区告警分布失败", e);
            return new HashMap<>();
        }
    }

    @Override
    public List<Alert> listByDevice(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("开始获取设备告警列表, deviceCode: {}, startTime: {}, endTime: {}", deviceCode, startTime, endTime);
        try {
            LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
            if (StringUtils.hasText(deviceCode)) {
                wrapper.eq(Alert::getDeviceCode, deviceCode);
            }
            if (startTime != null) {
                wrapper.ge(Alert::getCreateTime, startTime);
            }
            if (endTime != null) {
                wrapper.le(Alert::getCreateTime, endTime);
            }
            wrapper.orderByDesc(Alert::getCreateTime);
            List<Alert> alerts = alertMapper.selectList(wrapper);
            log.debug("获取设备告警列表完成, alerts size: {}", alerts.size());
            return alerts;
        } catch (Exception e) {
            log.error("获取设备告警列表失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取设备告警列表失败");
        }
    }

    @Override
    public Alert getLatestByDevice(String deviceCode) {
        log.debug("开始获取设备最新告警, deviceCode: {}", deviceCode);
        try {
            LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Alert::getDeviceCode, deviceCode);
            wrapper.orderByDesc(Alert::getCreateTime);
            wrapper.last("LIMIT 1");
            Alert alert = alertMapper.selectOne(wrapper);
            log.debug("获取设备最新告警完成, alert: {}", alert);
            return alert;
        } catch (Exception e) {
            log.error("获取设备最新告警失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取设备最新告警失败");
        }
    }

    @Override
    public IPage<Alert> page(Integer pageNum, Integer pageSize, String deviceCode, String alertType,
            String tourism, Integer level, Integer status, LocalDateTime startTime, LocalDateTime endTime) {
        return pageByConditions(pageNum, pageSize, deviceCode, alertType, tourism, level, status, startTime, endTime);
    }

    /**
     * 构建条件查询包装器
     */
    private LambdaQueryWrapper<Alert> buildConditionWrapper(String deviceCode, String alertType,
            String tourism, Integer level, Integer status, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(deviceCode)) {
            wrapper.eq(Alert::getDeviceCode, deviceCode);
        }
        if (StringUtils.hasText(alertType)) {
            wrapper.eq(Alert::getAlertType, alertType);
        }
        if (StringUtils.hasText(tourism)) {
            wrapper.eq(Alert::getTourismName, tourism);
        }
        if (level != null) {
            wrapper.eq(Alert::getAlertLevel, level);
        }
        if (status != null) {
            wrapper.eq(Alert::getAlertStatus, status);
        }
        if (startTime != null) {
            wrapper.ge(Alert::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Alert::getCreateTime, endTime);
        }
        return wrapper;
    }

    @Override
    public int countPendingAlerts(String deviceCode) {
        log.debug("开始统计待处理告警数量, deviceCode: {}", deviceCode);
        try {
            LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
            if (StringUtils.hasText(deviceCode)) {
                wrapper.eq(Alert::getDeviceCode, deviceCode);
            }
            wrapper.eq(Alert::getAlertStatus, AlertStatus.PENDING.getCode());
            long count = alertMapper.selectCount(wrapper);
            log.debug("统计待处理告警数量完成, count: {}", count);
            return (int) count;
        } catch (Exception e) {
            log.error("统计待处理告警数量失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "统计待处理告警数量失败");
        }
    }

    @Override
    public Map<String, Object> getTrend(String deviceCode, String alertType, LocalDateTime startTime,
            LocalDateTime endTime) {
        log.debug("开始获取告警趋势, deviceCode: {}, alertType: {}, startTime: {}, endTime: {}",
                deviceCode, alertType, startTime, endTime);
        try {
            LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();
            if (StringUtils.hasText(deviceCode)) {
                wrapper.eq(Alert::getDeviceCode, deviceCode);
            }
            if (StringUtils.hasText(alertType)) {
                wrapper.eq(Alert::getAlertType, alertType);
            }
            if (startTime != null) {
                wrapper.ge(Alert::getCreateTime, startTime);
            }
            if (endTime != null) {
                wrapper.le(Alert::getCreateTime, endTime);
            }

            // 查询告警列表
            List<Alert> alerts = alertMapper.selectList(wrapper);

            // 按小时统计趋势
            Map<String, Long> trend = new HashMap<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00");

            for (Alert alert : alerts) {
                String timeKey = alert.getCreateTime().format(formatter);
                trend.merge(timeKey, 1L, Long::sum);
            }

            log.debug("获取告警趋势完成, trend size: {}", trend.size());
            Map<String, Object> result = new HashMap<>();
            result.put("trend", trend);
            return result;

        } catch (Exception e) {
            log.error("获取告警趋势失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取告警趋势失败");
        }
    }

    @Override
    public Map<String, Object> getAlertStatistics(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("开始获取告警统计信息, deviceCode: {}, startTime: {}, endTime: {}", deviceCode, startTime, endTime);
        try {
            Map<String, Object> statistics = new HashMap<>();

            // 获取总告警数
            LambdaQueryWrapper<Alert> totalWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.hasText(deviceCode)) {
                totalWrapper.eq(Alert::getDeviceCode, deviceCode);
            }
            if (startTime != null) {
                totalWrapper.ge(Alert::getCreateTime, startTime);
            }
            if (endTime != null) {
                totalWrapper.le(Alert::getCreateTime, endTime);
            }
            Long totalCount = alertMapper.selectCount(totalWrapper);
            statistics.put("totalCount", totalCount);

            // 获取未处理告警数
            LambdaQueryWrapper<Alert> unhandledWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.hasText(deviceCode)) {
                unhandledWrapper.eq(Alert::getDeviceCode, deviceCode);
            }
            if (startTime != null) {
                unhandledWrapper.ge(Alert::getCreateTime, startTime);
            }
            if (endTime != null) {
                unhandledWrapper.le(Alert::getCreateTime, endTime);
            }
            unhandledWrapper.eq(Alert::getAlertStatus, AlertStatus.PENDING.getCode());
            Long unhandledCount = alertMapper.selectCount(unhandledWrapper);
            statistics.put("unhandledCount", unhandledCount);

            // 获取已处理告警数
            LambdaQueryWrapper<Alert> handledWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.hasText(deviceCode)) {
                handledWrapper.eq(Alert::getDeviceCode, deviceCode);
            }
            if (startTime != null) {
                handledWrapper.ge(Alert::getCreateTime, startTime);
            }
            if (endTime != null) {
                handledWrapper.le(Alert::getCreateTime, endTime);
            }
            handledWrapper.eq(Alert::getAlertStatus, AlertStatus.HANDLED.getCode());
            Long handledCount = alertMapper.selectCount(handledWrapper);
            statistics.put("handledCount", handledCount);

            log.debug("获取告警统计信息完成, statistics: {}", statistics);
            return statistics;
        } catch (Exception e) {
            log.error("获取告警统计信息失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取告警统计信息失败");
        }
    }

    @Override
    public boolean handleAlert(Long alertId, String description) {
        log.info("开始处理告警，告警ID：{}，处理描述：{}", alertId, description);
        try {
            Alert alert = alertMapper.selectById(alertId);
            if (alert == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "告警不存在");
            }

            // 更新告警状态
            alert.setAlertStatus(AlertStatus.HANDLED.getCode());
            alert.setUpdateTime(LocalDateTime.now());
            alertMapper.updateById(alert);

            // 创建处理记录
            AlertHandleRecord record = new AlertHandleRecord();
            record.setAlertId(alertId);
            record.setHandleDesc(description);
            record.setHandleResult("已处理");
            record.setBeforeStatus(String.valueOf(AlertStatus.PENDING.getCode()));
            record.setAfterStatus(String.valueOf(AlertStatus.HANDLED.getCode()));
            record.setCreateTime(LocalDateTime.now());
            record.setUpdateTime(LocalDateTime.now());
            alertHandleRecordMapper.insert(record);

            log.info("处理告警完成，告警ID：{}", alertId);
            return true;
        } catch (Exception e) {
            log.error("处理告警失败，告警ID：{}", alertId, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "处理告警失败");
        }
    }

    @Override
    public Map<String, Object> getDeviceDistribution(String tourismName, LocalDateTime startTime,
            LocalDateTime endTime) {
        log.debug("开始获取设备告警分布数据");
        try {
            List<Map<String, Object>> distribution = alertMapper.countDeviceDistribution(tourismName, startTime,
                    endTime);
            Map<String, Object> result = new HashMap<>();
            result.put("data", distribution);
            log.debug("获取设备告警分布数据完成");
            return result;
        } catch (Exception e) {
            log.error("获取设备告警分布数据失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取设备告警分布数据失败");
        }
    }

    @Override
    public int countByConditions(String tourismName, String deviceCode, String alertType,
            Integer alertLevel, Integer alertStatus, LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("开始根据条件统计告警数量");
        try {
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
            return alertMapper.selectCount(wrapper).intValue();
        } catch (Exception e) {
            log.error("根据条件统计告警数量失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "根据条件统计告警数量失败");
        }
    }

    @Override
    public List<Map<String, Object>> countTourismDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        log.debug("统计时间范围内各景区告警数量，开始时间：{}，结束时间：{}", startTime, endTime);
        return alertMapper.countTourismDistribution(startTime, endTime);
    }

    @Override
    public IPage<Alert> pageByConditions(Integer pageNum, Integer pageSize, String deviceCode,
            String alertType, String tourism, Integer level, Integer status,
            LocalDateTime startTime, LocalDateTime endTime) {
        log.debug(
                "开始分页查询告警, pageNum: {}, pageSize: {}, deviceCode: {}, alertType: {}, tourism: {}, level: {}, status: {}, startTime: {}, endTime: {}",
                pageNum, pageSize, deviceCode, alertType, tourism, level, status, startTime, endTime);
        try {
            Page<Alert> page = new Page<>(pageNum, pageSize);
            LambdaQueryWrapper<Alert> wrapper = buildConditionWrapper(deviceCode, alertType, tourism, level, status,
                    startTime, endTime);
            IPage<Alert> result = alertMapper.selectPage(page, wrapper);
            log.debug("分页查询告警完成, total: {}", result.getTotal());
            return result;
        } catch (Exception e) {
            log.error("分页查询告警失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "分页查询告警失败");
        }
    }

    @Override
    public List<Alert> listUnhandledAlerts(String deviceCode, String alertType) {
        log.debug("获取未处理告警列表，设备编码：{}，告警类型：{}", deviceCode, alertType);
        LambdaQueryWrapper<Alert> wrapper = buildConditionWrapper(null, deviceCode, alertType, null,
                AlertStatus.PENDING.getCode(), null, null);
        return list(wrapper);
    }

    @Override
    public List<Alert> listUnhandledAlertsByTourism(String deviceCode, String tourismName) {
        log.debug("获取未处理告警列表，设备编码：{}，景区名称：{}", deviceCode, tourismName);
        LambdaQueryWrapper<Alert> wrapper = buildConditionWrapper(tourismName, deviceCode, null, null,
                AlertStatus.PENDING.getCode(), null, null);
        return list(wrapper);

    }
}
