package com.scenic.ai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scenic.ai.model.Alert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 告警服务接口
 * 
 * @author scenic
 */
public interface AlertService extends IService<Alert> {
    
    /**
     * 创建告警信息
     *
     * @param alert 告警信息
     * @return 是否成功
     */
    boolean createAlert(Alert alert);
    
    /**
     * 更新告警状态
     *
     * @param id 告警ID
     * @param status 状态
     * @return 是否成功
     */
    boolean updateStatus(Long id, Integer status);
    
    /**
     * 批量更新告警状态
     *
     * @param ids ID列表
     * @param status 状态
     * @return 是否成功
     */
    boolean batchUpdateStatus(List<Long> ids, Integer status);
    
    /**
     * 根据设备编码查询告警信息
     *
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 告警信息列表
     */
    List<Alert> getAlertsByDevice(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 根据景区名称查询告警信息
     *
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 告警信息列表
     */
    List<Alert> getAlertsByTourism(String tourismName, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计时段告警分布
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    List<Map<String, Object>> getAlertHourDistribution(String deviceCode, String tourismName, 
            LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计告警类型分布
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    List<Map<String, Object>> getAlertTypeDistribution(String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计告警级别分布
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    List<Map<String, Object>> getAlertLevelDistribution(String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 分页查询告警信息
     *
     * @param params 查询参数
     * @return 告警信息列表
     */
    List<Alert> pageAlerts(Map<String, Object> params);
    
    /**
     * 查询记录总数
     *
     * @param params 查询参数
     * @return 记录总数
     */
    Long countAlerts(Map<String, Object> params);
    
    /**
     * 获取未处理的告警信息
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @return 未处理的告警信息列表
     */
    List<Alert> getUnhandledAlerts(String deviceCode, String tourismName);

    /**
     * 分页查询告警信息
     * @param page 分页参数
     * @param tourismName 景区名称
     * @param deviceCode 设备编码
     * @param alertType 告警类型
     * @param alertLevel 告警级别
     * @param alertStatus 告警状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页结果
     */
    IPage<Alert> pageAlerts(Page<Alert> page, String tourismName, String deviceCode, 
            String alertType, Integer alertLevel, Integer alertStatus, 
            LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取告警统计概览
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计概览
     */
    Map<String, Object> getAlertStatistics(String tourismName, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 处理告警
     * @param id 告警ID
     * @return 是否处理成功
     */
    boolean handleAlert(Long id);

    /**
     * 批量处理告警
     * @param ids 告警ID列表
     * @return 是否处理成功
     */
    boolean batchHandleAlerts(List<Long> ids);

    /**
     * 统计未处理告警数量
     * @param tourismName 景区名称
     * @return 未处理告警数量
     */
    int countPendingAlerts(String tourismName);

    /**
     * 根据告警级别和状态统计数量
     *
     * @param alertLevel 告警级别
     * @param alertStatus 告警状态
     * @return 统计结果
     */
    long countByLevelAndStatus(Integer alertLevel, Integer alertStatus);

    /**
     * 统计未处理告警数量
     */
    Long countUnhandledAlerts(String deviceCode, String tourismName);

    /**
     * 分页查询告警信息
     */
    IPage<Alert> page(Integer pageNum, Integer pageSize, String tourismName, String deviceCode,
                     String alertType, Integer alertLevel, Integer alertStatus,
                     LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取告警时段分布
     */
    List<Map<String, Object>> getHourDistribution(String tourismName, String deviceCode,
                                                LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取告警类型分布
     */
    List<Map<String, Object>> getTypeDistribution(String tourismName, String deviceCode,
                                                LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取告警概览统计
     */
    Map<String, Object> getOverview(String tourismName, String deviceCode,
                                  LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 处理告警
     */
    void handleAlert(Long alertId, String description);
} 