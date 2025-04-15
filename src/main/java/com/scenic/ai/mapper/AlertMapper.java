package com.scenic.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.model.Alert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 告警信息Mapper接口
 * 
 * @author AI
 * @date 2023-05-20
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
     * 分页查询告警信息
     *
     * @param page 分页参数
     * @param tourismName 景区名称
     * @param deviceCode 设备编码
     * @param alertType 告警类型
     * @param alertLevel 告警级别
     * @param alertStatus 告警状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 告警分页结果
     */
    IPage<Alert> selectAlertPage(Page<Alert> page, 
                                @Param("tourismName") String tourismName,
                                @Param("deviceCode") String deviceCode,
                                @Param("alertType") String alertType,
                                @Param("alertLevel") Integer alertLevel,
                                @Param("alertStatus") Integer alertStatus,
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

    /**
     * 查询告警列表
     * 
     * @param alert 告警信息
     * @return 告警集合
     */
    List<Alert> selectAlertList(Alert alert);
    
    /**
     * 查询告警信息
     * 
     * @param id 告警ID
     * @return 告警信息
     */
    Alert selectAlertById(Long id);
    
    /**
     * 新增告警
     * 
     * @param alert 告警信息
     * @return 结果
     */
    int insertAlert(Alert alert);
    
    /**
     * 修改告警
     * 
     * @param alert 告警信息
     * @return 结果
     */
    int updateAlert(Alert alert);
    
    /**
     * 删除告警
     * 
     * @param id 告警ID
     * @return 结果
     */
    int deleteAlertById(Long id);
    
    /**
     * 批量删除告警
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAlertByIds(Long[] ids);
    
    /**
     * 统计告警数量
     */
    Long selectAlertCount(Map<String, Object> params);
    
    /**
     * 根据设备编码查询告警
     * 
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 告警列表
     */
    List<Alert> selectByDevice(@Param("deviceCode") String deviceCode,
                              @Param("startTime") LocalDateTime startTime,
                              @Param("endTime") LocalDateTime endTime);
    
    /**
     * 根据景区名称查询告警
     * 
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 告警列表
     */
    List<Alert> selectByTourism(@Param("tourismName") String tourismName,
                               @Param("startTime") LocalDateTime startTime,
                               @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计未处理告警数量
     * 
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @return 未处理告警数量
     */
    Long countUnhandledAlerts(@Param("deviceCode") String deviceCode,
                             @Param("tourismName") String tourismName);
    
    /**
     * 获取告警时段分布
     */
    List<Map<String, Object>> getAlertTimeDistribution(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 获取告警类型分布
     */
    List<Map<String, Object>> getAlertTypeDistribution(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 获取告警级别分布
     */
    List<Map<String, Object>> getAlertLevelDistribution(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 获取告警趋势
     */
    List<Map<String, Object>> getAlertTrend(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询告警时段分布
     */
    List<Map<String, Object>> selectTimeDistribution(@Param("tourismName") String tourismName,
                                                    @Param("deviceCode") String deviceCode,
                                                    @Param("startTime") LocalDateTime startTime,
                                                    @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询告警类型分布
     */
    List<Map<String, Object>> selectTypeDistribution(@Param("tourismName") String tourismName,
                                                    @Param("deviceCode") String deviceCode,
                                                    @Param("startTime") LocalDateTime startTime,
                                                    @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询告警级别分布
     */
    List<Map<String, Object>> selectLevelDistribution(@Param("tourismName") String tourismName,
                                                     @Param("deviceCode") String deviceCode,
                                                     @Param("startTime") LocalDateTime startTime,
                                                     @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询告警趋势
     */
    List<Map<String, Object>> selectTrend(@Param("tourismName") String tourismName,
                                         @Param("deviceCode") String deviceCode,
                                         @Param("startTime") LocalDateTime startTime,
                                         @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询告警概览统计
     */
    Map<String, Object> selectOverview(@Param("tourismName") String tourismName,
                                      @Param("deviceCode") String deviceCode,
                                      @Param("startTime") LocalDateTime startTime,
                                      @Param("endTime") LocalDateTime endTime);
    
    /**
     * 批量更新告警状态
     */
    int updateStatusBatch(@Param("ids") List<Long> ids,
                         @Param("status") Integer status);

    /**
     * 删除历史数据
     */
    int deleteHistoricalData(@Param("beforeTime") LocalDateTime beforeTime);

    /**
     * 批量插入告警数据
     */
    int insertBatch(@Param("list") List<Alert> list);
    
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
     * 获取告警时段分布
     *
     * @param tourismName 景区名称
     * @param deviceCode 设备编码
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
     * @param tourismName 景区名称
     * @param deviceCode 设备编码
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
     * @param tourismName 景区名称
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 级别分布数据
     */
    List<Map<String, Object>> getLevelDistribution(
            @Param("tourismName") String tourismName,
            @Param("deviceCode") String deviceCode,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
} 