package com.conghua.tourism.service.alarm;

import com.conghua.tourism.model.alarm.*;
import com.conghua.tourism.common.PageResult;
import java.util.List;

/**
 * 告警管理服务接口
 * 定义告警相关的业务操作
 */
public interface AlarmService {
    
    /**
     * 获取告警列表
     * @param queryParams 查询参数
     * @return 告警列表分页数据
     */
    PageResult<AlarmData> getAlarmList(AlarmQueryParams queryParams);
    
    /**
     * 获取告警统计信息
     * @return 告警统计数据
     */
    AlarmStats getAlarmStats();
    
    /**
     * 获取告警配置列表
     * @return 告警配置列表
     */
    List<AlarmConfigResponse> getAlarmConfigs();
    
    /**
     * 更新告警配置
     * @param id 配置ID
     * @param config 告警配置
     */
    void updateAlarmConfig(String id, AlarmConfig config);
    
    /**
     * 处理告警
     * @param id 告警ID
     * @param params 处理参数
     */
    void handleAlarm(String id, AlarmHandleParams params);
    
    /**
     * 批量处理告警
     * @param params 批量处理参数列表
     * @return 批量处理结果
     */
    BatchOperationResult batchHandleAlarms(List<AlarmHandleParams> params);
    
    /**
     * 删除告警
     * @param id 告警ID
     */
    void deleteAlarm(String id);
    
    /**
     * 批量删除告警
     * @param ids 告警ID列表
     * @return 批量删除结果
     */
    BatchOperationResult batchDeleteAlarms(List<String> ids);
    
    /**
     * 获取实时告警数据
     * @param areaId 区域ID
     * @return 实时告警数据列表
     */
    List<AlarmData> getRealtimeAlarms(String areaId);
    
    /**
     * 获取告警趋势数据
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param interval 时间间隔
     * @return 告警趋势数据列表
     */
    List<AlarmTrendData> getAlarmTrends(String startTime, String endTime, String interval);
} 