package com.scenic.ai.service.impl;

import com.scenic.ai.common.exception.BusinessException;
import com.scenic.ai.entity.AlertHandleRecord;
import com.scenic.ai.mapper.AlertHandleRecordMapper;
import com.scenic.ai.service.AlertHandleRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 告警处理记录服务实现类
 */
@Service
public class AlertHandleRecordServiceImpl implements AlertHandleRecordService {

    private static final Logger log = LoggerFactory.getLogger(AlertHandleRecordServiceImpl.class);

    private final AlertHandleRecordMapper alertHandleRecordMapper;

    public AlertHandleRecordServiceImpl(AlertHandleRecordMapper alertHandleRecordMapper) {
        this.alertHandleRecordMapper = alertHandleRecordMapper;
    }

    @Override
    public List<AlertHandleRecord> listByAlertId(Long alertId) {
        if (alertId == null) {
            throw new BusinessException("告警ID不能为空");
        }
        try {
            return alertHandleRecordMapper.listByAlertId(alertId);
        } catch (Exception e) {
            log.error("查询告警处理记录失败, alertId: {}", alertId, e);
            throw new BusinessException("查询告警处理记录失败: " + e.getMessage());
        }
    }

    @Override
    public List<AlertHandleRecord> listByHandler(String handler) {
        if (handler == null || handler.trim().isEmpty()) {
            throw new BusinessException("处理人不能为空");
        }
        try {
            return alertHandleRecordMapper.listByHandler(handler);
        } catch (Exception e) {
            log.error("查询告警处理记录失败, handler: {}", handler, e);
            throw new BusinessException("查询告警处理记录失败: " + e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> countByHandleMethod(String startTime, String endTime) {
        if (startTime == null || endTime == null) {
            throw new BusinessException("开始时间和结束时间不能为空");
        }
        try {
            return alertHandleRecordMapper.countByHandleMethod(startTime, endTime);
        } catch (Exception e) {
            log.error("统计处理方式分布失败, startTime: {}, endTime: {}", startTime, endTime, e);
            throw new BusinessException("统计处理方式分布失败: " + e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> countByHandleResult(String startTime, String endTime) {
        if (startTime == null || endTime == null) {
            throw new BusinessException("开始时间和结束时间不能为空");
        }
        try {
            return alertHandleRecordMapper.countByHandleResult(startTime, endTime);
        } catch (Exception e) {
            log.error("统计处理结果分布失败, startTime: {}, endTime: {}", startTime, endTime, e);
            throw new BusinessException("统计处理结果分布失败: " + e.getMessage());
        }
    }

    @Override
    public List<AlertHandleRecord> pageByConditions(Map<String, Object> params) {
        if (params == null) {
            throw new BusinessException("查询参数不能为空");
        }
        try {
            return alertHandleRecordMapper.pageByConditions(params);
        } catch (Exception e) {
            log.error("分页查询告警处理记录失败, params: {}", params, e);
            throw new BusinessException("分页查询告警处理记录失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(AlertHandleRecord record) {
        if (record == null) {
            throw new BusinessException("处理记录不能为空");
        }
        if (record.getAlertId() == null) {
            throw new BusinessException("告警ID不能为空");
        }
        if (record.getHandler() == null || record.getHandler().trim().isEmpty()) {
            throw new BusinessException("处理人不能为空");
        }
        if (record.getHandleTime() == null) {
            record.setHandleTime(LocalDateTime.now());
        }
        if (record.getCreateTime() == null) {
            record.setCreateTime(LocalDateTime.now());
        }
        record.setUpdateTime(LocalDateTime.now());

        try {
            return alertHandleRecordMapper.insert(record) > 0;
        } catch (Exception e) {
            log.error("新增告警处理记录失败, record: {}", record, e);
            throw new BusinessException("新增告警处理记录失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(AlertHandleRecord record) {
        if (record == null || record.getId() == null) {
            throw new BusinessException("处理记录ID不能为空");
        }
        record.setUpdateTime(LocalDateTime.now());

        try {
            return alertHandleRecordMapper.update(record) > 0;
        } catch (Exception e) {
            log.error("更新告警处理记录失败, record: {}", record, e);
            throw new BusinessException("更新告警处理记录失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        if (id == null) {
            throw new BusinessException("记录ID不能为空");
        }
        try {
            return alertHandleRecordMapper.delete(id) > 0;
        } catch (Exception e) {
            log.error("删除告警处理记录失败, id: {}", id, e);
            throw new BusinessException("删除告警处理记录失败: " + e.getMessage());
        }
    }
}