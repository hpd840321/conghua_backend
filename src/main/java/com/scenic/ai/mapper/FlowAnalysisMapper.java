package com.scenic.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scenic.ai.model.FlowAnalysis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客流分析Mapper接口
 * 
 * @author AI
 * @date 2023-05-20
 */
@Mapper
public interface FlowAnalysisMapper extends BaseMapper<FlowAnalysis> {
    /**
     * 查询客流分析列表
     * 
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 客流分析列表
     */
    List<FlowAnalysis> selectFlowAnalysisList(@Param("deviceCode") String deviceCode,
                                             @Param("tourismName") String tourismName,
                                             @Param("startTime") Date startTime,
                                             @Param("endTime") Date endTime);

    /**
     * 新增客流分析
     * 
     * @param flowAnalysis 客流分析信息
     * @return 结果
     */
    int insertFlowAnalysis(FlowAnalysis flowAnalysis);

    /**
     * 修改客流分析
     * 
     * @param flowAnalysis 客流分析信息
     * @return 结果
     */
    int updateFlowAnalysis(FlowAnalysis flowAnalysis);

    /**
     * 删除客流分析
     * 
     * @param id 客流分析ID
     * @return 结果
     */
    int deleteFlowAnalysisById(Long id);

    /**
     * 批量删除客流分析
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteFlowAnalysisByIds(Long[] ids);

    /**
     * 查询客流分析详细信息
     * 
     * @param id 客流分析ID
     * @return 客流分析信息
     */
    FlowAnalysis selectFlowAnalysisById(Long id);

    /**
     * 统计指定时间范围内的客流总量
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 客流总量
     */
    Integer selectTotalFlowCount(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 统计指定时间范围内的各方向客流数量
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 各方向客流数量统计
     */
    List<FlowAnalysis> selectFlowDirectionStats(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 根据设备编码查询客流数据
     *
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 客流数据列表
     */
    List<FlowAnalysis> selectByDeviceCode(
            @Param("deviceCode") String deviceCode,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 根据景区名称查询客流数据
     *
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 客流数据列表
     */
    List<FlowAnalysis> selectByTourismName(
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计时段客流分布
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
     * 统计流向分布
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    List<Map<String, Object>> countByDirection(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 获取客流趋势
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 趋势数据
     */
    List<Map<String, Object>> getFlowTrend(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 条件分页查询
     *
     * @param params 查询参数
     * @return 客流数据列表
     */
    List<FlowAnalysis> findByConditions(@Param("params") Map<String, Object> params);
    
    /**
     * 查询记录总数
     *
     * @param params 查询参数
     * @return 记录总数
     */
    Long countRecords(@Param("params") Map<String, Object> params);

    /**
     * 获取客流方向分布数据
     */
    @Select("SELECT FLOW_DIRECTION as direction, " +
            "ROUND(AVG(FLOW_COUNT), 2) as count " +
            "FROM CLOUDWALK.FLOW_ANALYSIS " +
            "WHERE (#{tourismName} IS NULL OR TOURISM_NAME = #{tourismName}) " +
            "AND (#{deviceCode} IS NULL OR DEVICE_CODE = #{deviceCode}) " +
            "AND RECORD_TIME BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY FLOW_DIRECTION")
    List<Map<String, Object>> getFlowDirectionDistribution(@Param("deviceCode") String deviceCode,
                                                          @Param("tourismName") String tourismName,
                                                          @Param("startTime") LocalDateTime startTime,
                                                          @Param("endTime") LocalDateTime endTime);

    /**
     * 获取客流高峰时段数据
     */
    @Select("SELECT TO_CHAR(RECORD_TIME, 'HH24') as hour, " +
            "ROUND(AVG(FLOW_COUNT), 2) as count " +
            "FROM CLOUDWALK.FLOW_ANALYSIS " +
            "WHERE (#{tourismName} IS NULL OR TOURISM_NAME = #{tourismName}) " +
            "AND (#{deviceCode} IS NULL OR DEVICE_CODE = #{deviceCode}) " +
            "AND RECORD_TIME BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY TO_CHAR(RECORD_TIME, 'HH24') " +
            "ORDER BY hour")
    List<Map<String, Object>> getPeakHours(@Param("deviceCode") String deviceCode,
                                          @Param("startTime") LocalDateTime startTime,
                                          @Param("endTime") LocalDateTime endTime);

    /**
     * 获取总客流量
     */
    @Select("SELECT TO_CHAR(RECORD_TIME, 'YYYY-MM-DD') as date, " +
            "ROUND(AVG(FLOW_COUNT), 2) as count " +
            "FROM CLOUDWALK.FLOW_ANALYSIS " +
            "WHERE (#{tourismName} IS NULL OR TOURISM_NAME = #{tourismName}) " +
            "AND (#{deviceCode} IS NULL OR DEVICE_CODE = #{deviceCode}) " +
            "AND RECORD_TIME BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY TO_CHAR(RECORD_TIME, 'YYYY-MM-DD') " +
            "ORDER BY date")
    int getTotalFlowCount(@Param("deviceCode") String deviceCode,
                         @Param("startTime") LocalDateTime startTime,
                         @Param("endTime") LocalDateTime endTime);

    /**
     * 根据时间范围和景区名称查询客流分析数据
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param tourismName 景区名称
     * @return 客流分析数据列表
     */
    List<FlowAnalysis> selectByTimeRangeAndTourism(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("tourismName") String tourismName);
    
    /**
     * 根据设备编码查询最新的客流分析数据
     *
     * @param deviceCode 设备编码
     * @return 客流分析数据
     */
    FlowAnalysis selectLatestByDeviceCode(@Param("deviceCode") String deviceCode);

    /**
     * 查询客流分析信息
     * 
     * @param id 客流分析ID
     * @return 客流分析信息
     */
    FlowAnalysis getFlowAnalysisById(Long id);

    /**
     * 分页查询客流分析数据
     * 
     * @param params 查询参数
     * @return 客流分析数据列表
     */
    List<FlowAnalysis> selectFlowAnalysisPage(@Param("params") Map<String, Object> params);
    
    /**
     * 查询客流分析记录总数
     * 
     * @param params 查询参数
     * @return 记录总数
     */
    Long selectFlowAnalysisCount(@Param("params") Map<String, Object> params);
} 