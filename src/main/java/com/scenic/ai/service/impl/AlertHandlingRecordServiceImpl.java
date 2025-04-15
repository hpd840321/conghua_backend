package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.dao.AlertHandlingRecordMapper;
import com.scenic.ai.exception.BusinessException;
import com.scenic.ai.exception.ErrorCode;
import com.scenic.ai.model.AlertHandlingRecord;
import com.scenic.ai.service.AlertHandlingRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 告警处理记录服务实现类
 * 
 * @author scenic
 */
@Service
public class AlertHandlingRecordServiceImpl extends ServiceImpl<AlertHandlingRecordMapper, AlertHandlingRecord> 
        implements AlertHandlingRecordService {
    
    private static final Logger log = LoggerFactory.getLogger(AlertHandlingRecordServiceImpl.class);
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createRecord(Long alertId, String handler, String description) {
        log.info("创建告警处理记录: alertId={}, handler={}", alertId, handler);
        
        // 参数校验
        if (alertId == null) {
            throw new IllegalArgumentException("告警ID不能为空");
        }
        if (!StringUtils.hasText(handler)) {
            throw new IllegalArgumentException("处理人不能为空");
        }
        if (!StringUtils.hasText(description)) {
            throw new IllegalArgumentException("处理说明不能为空");
        }
        
        try {
            // 创建处理记录
            AlertHandlingRecord record = new AlertHandlingRecord();
            record.setAlertId(alertId);
            record.setHandler(handler);
            record.setDescription(description);
            record.setHandleTime(LocalDateTime.now());
            record.setCreateTime(LocalDateTime.now());
            
            // 保存记录
            if (!save(record)) {
                throw new BusinessException(ErrorCode.SAVE_ERROR, "创建处理记录失败");
            }
            
            log.info("创建告警处理记录成功: recordId={}", record.getId());
        } catch (Exception e) {
            log.error("创建告警处理记录失败: alertId={}, error={}", alertId, e.getMessage(), e);
            throw new BusinessException(ErrorCode.SAVE_ERROR, "创建处理记录失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<AlertHandlingRecord> getHandlingRecords(Long alertId) {
        try {
            if (alertId == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "告警ID不能为空");
            }
            return listByAlertId(alertId);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取告警处理记录失败: alertId={}, error={}", alertId, e.getMessage(), e);
            throw new BusinessException(ErrorCode.QUERY_ERROR, "获取告警处理记录失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public AlertHandlingRecord getById(Long id) {
        try {
            if (id == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "记录ID不能为空");
            }
            return super.getById(id);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取告警处理记录失败: id={}, error={}", id, e.getMessage(), e);
            throw new BusinessException(ErrorCode.QUERY_ERROR, "获取告警处理记录失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(AlertHandlingRecord record) {
        try {
            if (record == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "记录不能为空");
            }
            if (record.getId() == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "记录ID不能为空");
            }
            record.setUpdateTime(LocalDateTime.now());
            return super.updateById(record);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("更新告警处理记录失败: record={}, error={}", record, e.getMessage(), e);
            throw new BusinessException(ErrorCode.UPDATE_ERROR, "更新告警处理记录失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Long id) {
        try {
            if (id == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "记录ID不能为空");
            }
            return super.removeById(id);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("删除告警处理记录失败: id={}, error={}", id, e.getMessage(), e);
            throw new BusinessException(ErrorCode.DELETE_ERROR, "删除告警处理记录失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<AlertHandlingRecord> listByAlertId(Long alertId) {
        return lambdaQuery().eq(AlertHandlingRecord::getAlertId, alertId).list();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(AlertHandlingRecord record) {
        try {
            if (record == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "记录不能为空");
            }
            record.setCreateTime(LocalDateTime.now());
            record.setUpdateTime(LocalDateTime.now());
            return super.save(record);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("保存告警处理记录失败: record={}, error={}", record, e.getMessage(), e);
            throw new BusinessException(ErrorCode.SAVE_ERROR, "保存告警处理记录失败: " + e.getMessage(), e);
        }
    }
} 