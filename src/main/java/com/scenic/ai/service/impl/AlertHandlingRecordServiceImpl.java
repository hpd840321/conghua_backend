package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.mapper.AlertHandlingRecordMapper;
import com.scenic.ai.model.AlertHandlingRecord;
import com.scenic.ai.service.AlertHandlingRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 告警处理记录Service实现类
 */
@Service
public class AlertHandlingRecordServiceImpl extends ServiceImpl<AlertHandlingRecordMapper, AlertHandlingRecord> implements AlertHandlingRecordService {

    @Override
    public List<AlertHandlingRecord> getHandlingRecordsByAlertId(Long alertId) {
        LambdaQueryWrapper<AlertHandlingRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AlertHandlingRecord::getAlertId, alertId)
                .orderByDesc(AlertHandlingRecord::getHandlingTime);
        return list(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addHandlingRecord(AlertHandlingRecord record) {
        return save(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateHandlingRecord(AlertHandlingRecord record) {
        return updateById(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteHandlingRecord(Long id) {
        return removeById(id);
    }
} 