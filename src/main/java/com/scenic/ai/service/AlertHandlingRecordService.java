package com.scenic.ai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scenic.ai.model.AlertHandlingRecord;

import java.util.List;

/**
 * 告警处理记录Service接口
 */
public interface AlertHandlingRecordService extends IService<AlertHandlingRecord> {
    
    /**
     * 根据告警ID获取处理记录列表
     *
     * @param alertId 告警ID
     * @return 处理记录列表
     */
    List<AlertHandlingRecord> getHandlingRecordsByAlertId(Long alertId);
    
    /**
     * 添加处理记录
     *
     * @param record 处理记录
     * @return 是否成功
     */
    boolean addHandlingRecord(AlertHandlingRecord record);
    
    /**
     * 更新处理记录
     *
     * @param record 处理记录
     * @return 是否成功
     */
    boolean updateHandlingRecord(AlertHandlingRecord record);
    
    /**
     * 删除处理记录
     *
     * @param id 记录ID
     * @return 是否成功
     */
    boolean deleteHandlingRecord(Long id);
} 