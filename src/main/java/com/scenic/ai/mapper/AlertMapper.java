package com.scenic.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scenic.ai.model.Alert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 告警信息Mapper接口
 */
@Mapper
public interface AlertMapper extends BaseMapper<Alert> {
    /**
     * 插入告警信息
     *
     * @param alert 告警信息
     * @return 影响行数
     */
    int insert(Alert alert);
    
    /**
     * 更新告警信息
     *
     * @param alert 告警信息
     * @return 影响行数
     */
    int update(Alert alert);
    
    /**
     * 根据ID查询告警信息
     *
     * @param id 告警ID
     * @return 告警信息
     */
    Alert findById(@Param("id") Long id);
    
    /**
     * 根据状态查询告警信息
     *
     * @param status 告警状态
     * @return 告警列表
     */
    List<Alert> findByStatus(@Param("status") Integer status);
    
    /**
     * 根据级别和状态查询告警信息
     *
     * @param level 告警级别
     * @param status 告警状态
     * @return 告警列表
     */
    List<Alert> findByLevelAndStatus(@Param("level") Integer level, @Param("status") Integer status);
    
    /**
     * 统计时间范围内的告警数量
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    Map<String, Object> countByTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计告警类型分布
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    List<Map<String, Object>> countByType(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计告警时段分布
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    List<Map<String, Object>> countByHour(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    
    /**
     * 批量更新告警状态
     *
     * @param ids ID列表
     * @param status 状态
     * @return 影响行数
     */
    int batchUpdateStatus(@Param("ids") List<Long> ids, @Param("status") Integer status);
    
    /**
     * 条件分页查询
     *
     * @param params 查询参数
     * @return 告警列表
     */
    List<Alert> findByConditions(@Param("params") Map<String, Object> params);
    
    /**
     * 分页查询
     *
     * @param tourismName 景区名称
     * @param deviceCode 设备编码
     * @param alertType 告警类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 告警列表
     */
    List<Alert> selectPage(
            @Param("tourismName") String tourismName,
            @Param("deviceCode") String deviceCode,
            @Param("alertType") String alertType,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 根据设备编码查询告警列表
     *
     * @param deviceCode 设备编码
     * @return 告警列表
     */
    List<Alert> selectByDeviceCode(@Param("deviceCode") String deviceCode);
    
    /**
     * 根据景区名称查询告警列表
     *
     * @param tourismName 景区名称
     * @return 告警列表
     */
    List<Alert> selectByTourismName(@Param("tourismName") String tourismName);
    
    /**
     * 根据告警类型查询告警列表
     *
     * @param alertType 告警类型
     * @return 告警列表
     */
    List<Alert> selectByAlertType(@Param("alertType") String alertType);
    
    /**
     * 根据告警等级查询告警列表
     *
     * @param alertLevel 告警等级
     * @return 告警列表
     */
    List<Alert> selectByAlertLevel(@Param("alertLevel") Integer alertLevel);
    
    /**
     * 根据状态查询告警列表
     *
     * @param status 状态
     * @return 告警列表
     */
    List<Alert> selectByStatus(@Param("status") Integer status);
    
    /**
     * 查询告警总数
     *
     * @param params 查询参数
     * @return 告警总数
     */
    Long countAlerts(@Param("params") Map<String, Object> params);
    
    /**
     * 统计各告警类型数量
     *
     * @return 统计结果
     */
    List<Map<String, Object>> countByAlertType();
    
    /**
     * 统计各告警等级数量
     *
     * @return 统计结果
     */
    List<Map<String, Object>> countByAlertLevel();
    
    /**
     * 统计各景区告警数量
     *
     * @return 统计结果
     */
    List<Map<String, Object>> countByTourism();
    
    /**
     * 根据设备编码查询告警信息
     *
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 告警信息列表
     */
    List<Alert> selectByDeviceCode(
            @Param("deviceCode") String deviceCode,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 根据景区名称查询告警信息
     *
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 告警信息列表
     */
    List<Alert> selectByTourismName(
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计时段告警分布
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    List<Map<String, Object>> countByHour(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计告警类型分布
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    List<Map<String, Object>> countByType(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计告警级别分布
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    List<Map<String, Object>> countByLevel(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询记录总数
     *
     * @param params 查询参数
     * @return 记录总数
     */
    Long countRecords(@Param("params") Map<String, Object> params);
    
    /**
     * 获取未处理的告警信息
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @return 未处理的告警信息列表
     */
    List<Alert> selectUnhandled(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName);
} 