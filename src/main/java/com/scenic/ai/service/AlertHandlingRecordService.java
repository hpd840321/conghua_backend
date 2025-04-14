package com.scenic.ai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scenic.ai.model.AlertHandlingRecord;

import java.util.List;

/**
 * 告警处理记录服务接口
 */
public interface AlertHandlingRecordService extends IService<AlertHandlingRecord> {
    
    /**
     * 添加告警处理记录
     * @param record 处理记录信息
     * @return 是否添加成功
     */
    boolean save(AlertHandlingRecord record);
    
    /**
     * 根据告警ID查询处理记录列表
     * @param alertId 告警ID
     * @return 处理记录列表，按创建时间降序排序
     */
    List<AlertHandlingRecord> listByAlertId(Long alertId);
    
    /**
     * 更新告警处理记录
     * @param record 处理记录信息
     * @return 是否更新成功
     */
    boolean updateById(AlertHandlingRecord record);
    
    /**
     * 删除告警处理记录
     * @param id 记录ID
     * @return 是否删除成功
     */
    boolean removeById(Long id);
} 