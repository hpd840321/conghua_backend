package com.scenic.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scenic.ai.model.FlowAnalysis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 客流分析Mapper接口
 */
@Mapper
public interface FlowAnalysisMapper extends BaseMapper<FlowAnalysis> {
    
    /**
     * 获取客流量趋势数据
     */
    @Select("SELECT TO_CHAR(RECORD_TIME, 'YYYY-MM-DD HH24:MI:SS') as time, FLOW_COUNT as value " +
            "FROM CLOUDWALK.FLOW_ANALYSIS " +
            "WHERE (#{tourismName} IS NULL OR TOURISM_NAME = #{tourismName}) " +
            "AND (#{deviceCode} IS NULL OR DEVICE_CODE = #{deviceCode}) " +
            "AND RECORD_TIME BETWEEN #{startTime} AND #{endTime} " +
            "ORDER BY RECORD_TIME")
    List<Map<String, Object>> getFlowTrend(@Param("deviceCode") String deviceCode,
                                          @Param("tourismName") String tourismName,
                                          @Param("startTime") LocalDateTime startTime,
                                          @Param("endTime") LocalDateTime endTime);

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
} 