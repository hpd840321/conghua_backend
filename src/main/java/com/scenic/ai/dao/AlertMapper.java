package com.scenic.ai.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scenic.ai.model.Alert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 告警信息数据访问接口
 * 提供告警信息的增删改查和统计分析功能
 *
 * @author AI
 * @date 2024-03-20
 */
@Mapper
public interface AlertMapper extends BaseMapper<Alert> {

    /**
     * 获取告警时段分布
     *
     * @param tourismName 景区名称（可选）
     * @param deviceCode 设备编码（可选）
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 时段分布数据
     */
    List<Map<String, Object>> getTimeDistribution(
            @Param("tourismName") String tourismName,
            @Param("deviceCode") String deviceCode,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 获取告警类型分布
     *
     * @param tourismName 景区名称（可选）
     * @param deviceCode 设备编码（可选）
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 类型分布数据
     */
    List<Map<String, Object>> getTypeDistribution(
            @Param("tourismName") String tourismName,
            @Param("deviceCode") String deviceCode,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 获取告警级别分布
     *
     * @param tourismName 景区名称（可选）
     * @param deviceCode 设备编码（可选）
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 级别分布数据
     */
    List<Map<String, Object>> getLevelDistribution(
            @Param("tourismName") String tourismName,
            @Param("deviceCode") String deviceCode,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 获取告警趋势
     *
     * @param tourismName 景区名称（可选）
     * @param deviceCode 设备编码（可选）
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 趋势数据
     */
    List<Map<String, Object>> getTrend(
            @Param("tourismName") String tourismName,
            @Param("deviceCode") String deviceCode,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 获取告警设备分布
     *
     * @param tourismName 景区名称（可选）
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 设备分布数据
     */
    List<Map<String, Object>> getDeviceDistribution(
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 获取告警景区分布
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 景区分布数据
     */
    List<Map<String, Object>> getTourismDistribution(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 获取告警类型列表
     *
     * @return 告警类型列表
     */
    List<Map<String, Object>> getAlertTypes();

    /**
     * 批量更新告警状态
     *
     * @param ids 告警ID列表
     * @param status 状态（0-待处理，1-已处理）
     * @return 影响行数
     */
    int updateStatusBatch(@Param("ids") List<Long> ids, @Param("status") Integer status);

    /**
     * 删除历史数据
     *
     * @param beforeTime 时间点
     * @return 影响行数
     */
    int deleteHistoricalData(@Param("beforeTime") LocalDateTime beforeTime);

    /**
     * 批量插入告警数据
     *
     * @param list 告警数据列表
     * @return 影响行数
     */
    int insertBatch(@Param("list") List<Alert> list);

    /**
     * 获取设备最新告警
     *
     * @param deviceCode 设备编码
     * @return 最新告警
     */
    Alert getLatestByDevice(@Param("deviceCode") String deviceCode);

    /**
     * 根据设备编码查询告警信息
     *
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 告警列表
     */
    List<Alert> getByDevice(
            @Param("deviceCode") String deviceCode,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 根据景区名称查询告警信息
     *
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 告警列表
     */
    List<Alert> getByTourism(
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 查询未处理告警数量
     *
     * @param deviceCode 设备编码（可选）
     * @param tourismName 景区名称（可选）
     * @return 未处理告警数量
     */
    Long countUnhandledAlerts(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName);

    /**
     * 查询告警时段分布
     */
    List<Map<String, Object>> selectTimeDistribution(@Param("params") Map<String, Object> params);
    
    /**
     * 查询告警类型分布
     */
    List<Map<String, Object>> selectTypeDistribution(@Param("params") Map<String, Object> params);
    
    /**
     * 查询告警景区分布
     */
    List<Map<String, Object>> selectTourismDistribution(@Param("params") Map<String, Object> params);
    
    /**
     * 查询未处理的告警数量（按参数）
     */
    int countUnhandledAlertsByParams(@Param("tourismName") String tourismName, @Param("deviceCode") String deviceCode);

    /**
     * 根据条件查询告警数量
     */
    int selectAlertCount(@Param("params") Map<String, Object> params);
    
    /**
     * 查询未处理的告警
     */
    List<Alert> selectUnhandled(@Param("tourismName") String tourismName, 
                               @Param("deviceCode") String deviceCode);
} 