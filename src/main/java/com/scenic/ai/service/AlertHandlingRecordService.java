package com.scenic.ai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scenic.ai.model.AlertHandlingRecord;

import java.util.List;

/**
 * 告警处理记录服务接口
 * 
 * @author scenic
 */
public interface AlertHandlingRecordService extends IService<AlertHandlingRecord> {
    
    /**
     * 创建处理记录
     */
    void createRecord(Long alertId, String handler, String description);
    
    /**
     * 获取告警处理记录
     */
    List<AlertHandlingRecord> getHandlingRecords(Long alertId);
    
    /**
     * 根据ID查询处理记录
     */
    AlertHandlingRecord getById(Long id);
    
    /**
     * 更新处理记录
     */
    boolean updateById(AlertHandlingRecord record);
    
    /**
     * 删除处理记录
     */
    boolean removeById(Long id);
    
    /**
     * 根据告警ID查询处理记录列表
     */
    List<AlertHandlingRecord> listByAlertId(Long alertId);
    
    /**
     * 保存处理记录
     */
    boolean save(AlertHandlingRecord record);
} 