package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.mapper.AlertHandlingRecordMapper;
import com.scenic.ai.model.AlertHandlingRecord;
import com.scenic.ai.service.AlertHandlingRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 告警处理记录服务实现类
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AlertHandlingRecordServiceImpl extends ServiceImpl<AlertHandlingRecordMapper, AlertHandlingRecord> implements AlertHandlingRecordService {

    @Override
    public boolean save(AlertHandlingRecord record) {
        return super.save(record);
    }

    @Override
    public List<AlertHandlingRecord> listByAlertId(Long alertId) {
        return lambdaQuery()
                .eq(AlertHandlingRecord::getAlertId, alertId)
                .orderByDesc(AlertHandlingRecord::getCreateTime)
                .list();
    }

    @Override
    public boolean updateById(AlertHandlingRecord record) {
        return super.updateById(record);
    }

    @Override
    public boolean removeById(Long id) {
        return super.removeById(id);
    }
} 