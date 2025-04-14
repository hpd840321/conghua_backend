package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.dao.AlertHandlingRecordMapper;
import com.scenic.ai.exception.BusinessException;
import com.scenic.ai.model.AlertHandlingRecord;
import com.scenic.ai.service.AlertHandlingRecordService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class AlertHandlingRecordServiceImpl extends ServiceImpl<AlertHandlingRecordMapper, AlertHandlingRecord> 
        implements AlertHandlingRecordService {
    
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
                throw new BusinessException("创建处理记录失败");
            }
            
            log.info("创建告警处理记录成功: recordId={}", record.getId());
        } catch (Exception e) {
            log.error("创建告警处理记录失败: alertId={}, error={}", alertId, e.getMessage(), e);
            throw new BusinessException("创建处理记录失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<AlertHandlingRecord> getHandlingRecords(Long alertId) {
        log.info("查询告警处理记录: alertId={}", alertId);
        
        if (alertId == null) {
            throw new IllegalArgumentException("告警ID不能为空");
        }
        
        try {
            List<AlertHandlingRecord> records = baseMapper.selectByAlertId(alertId);
            log.info("查询告警处理记录成功: alertId={}, count={}", alertId, records.size());
            return records;
        } catch (Exception e) {
            log.error("查询告警处理记录失败: alertId={}, error={}", alertId, e.getMessage(), e);
            throw new BusinessException("查询处理记录失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public AlertHandlingRecord getById(Long id) {
        log.info("根据ID查询处理记录: id={}", id);
        
        if (id == null) {
            throw new IllegalArgumentException("记录ID不能为空");
        }
        
        try {
            AlertHandlingRecord record = super.getById(id);
            if (record == null) {
                log.warn("处理记录不存在: id={}", id);
                throw new BusinessException("处理记录不存在");
            }
            return record;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("查询处理记录失败: id={}, error={}", id, e.getMessage(), e);
            throw new BusinessException("查询处理记录失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(AlertHandlingRecord record) {
        log.info("更新处理记录: id={}", record.getId());
        
        if (record.getId() == null) {
            throw new IllegalArgumentException("记录ID不能为空");
        }
        
        try {
            record.setUpdateTime(LocalDateTime.now());
            boolean success = super.updateById(record);
            if (success) {
                log.info("更新处理记录成功: id={}", record.getId());
            } else {
                log.warn("更新处理记录失败: id={}", record.getId());
            }
            return success;
        } catch (Exception e) {
            log.error("更新处理记录失败: id={}, error={}", record.getId(), e.getMessage(), e);
            throw new BusinessException("更新处理记录失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Long id) {
        log.info("删除处理记录: id={}", id);
        
        if (id == null) {
            throw new IllegalArgumentException("记录ID不能为空");
        }
        
        try {
            boolean success = super.removeById(id);
            if (success) {
                log.info("删除处理记录成功: id={}", id);
            } else {
                log.warn("删除处理记录失败: id={}", id);
            }
            return success;
        } catch (Exception e) {
            log.error("删除处理记录失败: id={}, error={}", id, e.getMessage(), e);
            throw new BusinessException("删除处理记录失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<AlertHandlingRecord> listByAlertId(Long alertId) {
        return getHandlingRecords(alertId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(AlertHandlingRecord record) {
        log.info("保存处理记录: alertId={}", record.getAlertId());
        
        if (record.getAlertId() == null) {
            throw new IllegalArgumentException("告警ID不能为空");
        }
        
        try {
            if (record.getCreateTime() == null) {
                record.setCreateTime(LocalDateTime.now());
            }
            if (record.getHandleTime() == null) {
                record.setHandleTime(LocalDateTime.now());
            }
            
            boolean success = super.save(record);
            if (success) {
                log.info("保存处理记录成功: id={}", record.getId());
            } else {
                log.warn("保存处理记录失败");
            }
            return success;
        } catch (Exception e) {
            log.error("保存处理记录失败: alertId={}, error={}", record.getAlertId(), e.getMessage(), e);
            throw new BusinessException("保存处理记录失败: " + e.getMessage(), e);
        }
    }
} 